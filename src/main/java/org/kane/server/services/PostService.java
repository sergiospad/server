package org.kane.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.server.DTO.post.PostCreateDTO;
import org.kane.server.DTO.post.PostShowDTO;
import org.kane.server.entity.ImageModel;
import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.kane.server.exceptions.PostNotFoundException;
import org.kane.server.mappers.PostShowMapper;
import org.kane.server.repository.ImageRepository;
import org.kane.server.repository.PostRepository;
import org.kane.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.kane.server.services.ImageUploadService.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final PostShowMapper postMapper;

    @Transactional
    public PostShowDTO createPost(PostCreateDTO postDTO, Principal principal){
        var user = userRepository.getUserByPrincipal(principal);
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .caption(postDTO.getCaption())
                .location(postDTO.getLocation())
                .build();
        user.addPost(post);
        post = postRepository.save(post);
        log.info("Post created for user {}", user.getUsername());
        return Optional.of(post).map(postMapper::map).orElse(null);
    }

    public List<Long> getAllPosts(){
        return postRepository.findAllIdPosts();
    }

    public Optional<PostShowDTO> getPostById(Long id){
        return postRepository.findById(id).map(postMapper::map);
    }

    public List<Long> getPostsByCurrentUser(Principal principal){
        var user = userRepository.getUserByPrincipal(principal);
        return postRepository.findAllByUser(user.getId());
    }

    @Transactional
    public List<Long> likePost(Long postId, Long userId){
        try{
            Post post = postRepository.findById(postId)
                    .orElseThrow(()->new PostNotFoundException("Post cannot be found"));
            var user = userRepository.findById(userId)
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));
            if(post.getLikedUsers().contains(user)) {
                post.removeLikedUser(user);
            }
            else {
                post.addLikedUser(user);
            }
            post = postRepository.save(post);
            return post.getLikedUsers().stream()
                    .map(User::getId)
                    .toList();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deletePost(Long postId, Principal principal){
        var user = userRepository.getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("Post cannot be found id: %d".formatted(postId)));;
        postRepository.delete(post);
        user.removePost(post);
    }

    @Transactional
    public Long uploadImage(MultipartFile file, Principal principal, Long postId){

        Post post =postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("Post cannot be found id: %d".formatted(postId)));;
        log.info("Uploading image profile to Post: {}", postId);
        ImageModel imageModel = ImageModel.builder()
                .imageURL(saveImage(file, postId, ImagePrefix.POST.toString()))
                .build();
        imageModel = imageRepository.save(imageModel);
        post.addImage(imageModel);

        return imageModel.getId();
    }

    public  List<Long> getIdOfImages(Long postId){
        Post post =  postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("Post cannot be found"));
        return imageRepository.findAllIdByPost(post);
    }

    public Optional<ImageModel> getImageOfPost(Long postId){
        return imageRepository.findById(postId);
    }
}
