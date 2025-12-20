package org.kane.server.mappers.user;


import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.user.UserProfileDTO;
import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMapper implements Mapper<User, UserProfileDTO> {

    @Override
    public UserProfileDTO map(User from) {
        return UserProfileDTO.builder()
                .id(from.getId())
                .username(from.getUsername())
                .bio(from.getBio())
                .email(from.getEmail())
                .firstname(from.getFirstname())
                .lastname(from.getLastname())
                .build();
    }
}
