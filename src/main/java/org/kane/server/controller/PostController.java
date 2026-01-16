package org.kane.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.post.PostCreateDTO;
import org.kane.server.DTO.post.PostEditDTO;
import org.kane.server.DTO.post.PostShowDTO;
import org.kane.server.DTO.response.MessageResponse;
import org.kane.server.entity.ImageModel;
import org.kane.server.exceptions.PostNotFoundException;
import org.kane.server.mappers.post.PostShowMapper;
import org.kane.server.services.ImageUploadService;
import org.kane.server.services.PostService;
import org.kane.server.services.UserService;
import org.kane.server.validations.annotations.ResponseErrorValidation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/post")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    private final ResponseErrorValidation responseErrorValidation;
    private final PostShowMapper postShowMapper;


    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostCreateDTO postDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        var res = Optional.of(postService.createPost(postDTO, principal))
                .orElseThrow(()->new PostNotFoundException("Post already exists"));

        return ResponseEntity.ok(res);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllPosts(Principal principal) {
        var posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Long>> getUserPosts(Principal principal) {
        var posts = postService.getPostsByCurrentUser(principal);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long imageId, Principal principal ){
        return postService.getImageOfPost(imageId)
                .map(ImageModel::getImageURL)
                .flatMap(ImageUploadService::get)
                .map(img  -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(img))
                .orElse(ResponseEntity.ok(null));
    }

    @PostMapping("/image/{postId}")
    public ResponseEntity<Long> uploadImage(@PathVariable Long postId,
                                                  @RequestParam MultipartFile file,
                                                  Principal principal){
        var id = postService.uploadImage(file, principal, postId);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(postId, principal);
        return ResponseEntity.ok(new MessageResponse("Post deleted"));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostShowDTO> getPostById(@PathVariable Long postId, Principal principal){
        var post = postService.getPostById(postId)
                .orElseThrow(()->new PostNotFoundException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @PutMapping("/edit")
    public ResponseEntity<PostShowDTO> editPost(@RequestBody PostEditDTO postDTO){
        var post = postService.editPost(postDTO);
        return ResponseEntity.ok(post);
    }



}
