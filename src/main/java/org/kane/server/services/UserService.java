package org.kane.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.server.DTO.user.UserEditDTO;
import org.kane.server.DTO.request.SignupRequest;
import org.kane.server.entity.User;
import org.kane.server.exceptions.UserExistException;
import org.kane.server.mappers.user.UserMapper;
import org.kane.server.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

import static org.kane.server.services.ImageUploadService.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public User createUser(SignupRequest userIn){
        var user = User.builder()
                .firstname(userIn.getFirstname())
                .lastname(userIn.getLastname())
                .email(userIn.getEmail())
                .username(userIn.getUsername())
                .password(bCryptPasswordEncoder.encode(userIn.getPassword()))
                .build();
        try {
            log.info("Creating user {}", userIn.getUsername());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error creating user {}", userIn.getUsername(), e);
            throw new UserExistException("User %s already exists. Please check credentials".formatted(user.getUsername()));
        }
    }

    @Transactional
    public Optional<User> updateUser(UserEditDTO userDTO, Principal principal){
        return Optional.of(userRepository.getUserByPrincipal(principal))
                .map(u->userMapper.map(userDTO, u))
                .map(userRepository::save);
    }

    public User getCurrentUser(Principal principal){
        return userRepository.getUserByPrincipal(principal);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Transactional
    public void uploadAvatar(MultipartFile file, Principal principal) {
        User user = userRepository.getUserByPrincipal(principal);
        var pref = ImagePrefix.USER.toString();
        if (!ObjectUtils.isEmpty(user.getAvatar()))
            delete(user.getAvatar(),
                    user.getId(),
                   pref);
        var imgPath = saveImage(file, user.getId(), pref);
        user.setAvatar(imgPath);
        userRepository.save(user);
    }

    public Optional<String> getAvatar(Principal principal){
        return Optional.of(userRepository.getUserByPrincipal(principal).getAvatar());
    }

}