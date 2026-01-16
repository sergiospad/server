package org.kane.server.controller;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.response.MessageResponse;
import org.kane.server.DTO.user.UserEditDTO;
import org.kane.server.DTO.user.UserProfileDTO;
import org.kane.server.DTO.user.UserShowNameDTO;
import org.kane.server.mappers.user.UserMapper;
import org.kane.server.mappers.user.UserProfileMapper;
import org.kane.server.mappers.user.UserShowNameMapper;
import org.kane.server.services.ImageUploadService;
import org.kane.server.services.PostService;
import org.kane.server.services.UserService;
import org.kane.server.validations.annotations.ResponseErrorValidation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;

    private final UserShowNameMapper userShowNameMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserMapper userMapper;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserShowNameDTO> getCurrentUser(Principal principal) {
        var userDTO = Optional.of(userService.getCurrentUser(principal))
                .map(userShowNameMapper::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserEditDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;
        var userUpd = userService.updateUser(userDTO, principal)
                .map(userShowNameMapper::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(userUpd);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(Principal principal){
        var user = Optional.of(userService.getCurrentUser(principal))
                .map(userProfileMapper::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/like/{postId}")
    public ResponseEntity<List<Long>> likePost(@PathVariable Long postId,
                                               Principal principal) {
        var user = userService.getCurrentUser(principal);
        var postDTO = postService.likePost(postId, user.getId());
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getUserImage(Principal principal){
        var avatar = Optional.ofNullable(userService.getCurrentUser(principal).getAvatar())
                .orElse(userService.getDefaultAvatar());
       return processAvatar(avatar);
    }
    @GetMapping("/image/{userId}")
    public ResponseEntity<byte[]> getAvatarByUserId(@PathVariable Long userId){
        var avatar = userService.getAvatarByUserId(userId)
                .orElse(userService.getDefaultAvatar());
        return processAvatar(avatar);
    }

    public ResponseEntity<byte[]> processAvatar(String avatar){
        return Optional.of(avatar)
                .flatMap(ImageUploadService::get)
                .map(img  -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(img))
                .orElse(ResponseEntity.ok(null));
    }

    @PutMapping("/image")
    public ResponseEntity<MessageResponse> uploadImage(@RequestParam MultipartFile file,
                                            Principal principal){
        userService.uploadAvatar(file, principal);
        return ResponseEntity.ok(new MessageResponse("Message uploaded successfully"));
    }
}
