package org.kane.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.server.DTO.comment.CommentCreateDTO;
import org.kane.server.DTO.comment.CommentEditDTO;
import org.kane.server.DTO.comment.CommentShowDTO;
import org.kane.server.entity.Comment;
import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.kane.server.exceptions.PostNotFoundException;
import org.kane.server.mappers.comment.CommentEditMapper;
import org.kane.server.mappers.comment.CommentShowMapper;
import org.kane.server.repository.CommentRepository;
import org.kane.server.repository.PostRepository;
import org.kane.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentShowMapper commentShowMapper;
    private final CommentEditMapper commentEditMapper;

    @Transactional
    public CommentShowDTO saveComment(CommentCreateDTO commentDTO, Principal principal) {
        User user = userRepository.getUserByPrincipal(principal);
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(()->new PostNotFoundException("Post not found for username %s".formatted(user.getUsername())));
        var comment = Comment.builder()
                .post(post)
                .commentator(user)
                .message(commentDTO.getMessage())
                .build();

        log.info("Saving comment for Post {}", post.getId());
        return Optional.of(commentRepository.save(comment)).map(commentShowMapper::map).orElse(null);
    }

    public Optional<CommentShowDTO> getCommentFromId(Long commentId) {
        return commentRepository.findById(commentId).map(commentShowMapper::map);
    }

    public List<CommentShowDTO> getCommentsFromPostId(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("Post not found id: %d".formatted(postId)));
        return commentRepository.findAllByPost(post).stream()
                .map(commentShowMapper::map)
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId){
        commentRepository.findById(commentId)
                .ifPresent(commentRepository::delete);

    }

    @Transactional
    public CommentShowDTO editComment(CommentEditDTO commentDTO){
        var comment = commentRepository.findCommentById(commentDTO.getId());
        comment = commentEditMapper.map(commentDTO, comment);
        comment = commentRepository.save(comment);
        return commentShowMapper.map(comment);
    }
}
