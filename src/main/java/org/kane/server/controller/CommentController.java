package org.kane.server.controller;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.comment.CommentCreateDTO;
import org.kane.server.DTO.comment.CommentShowDTO;
import org.kane.server.DTO.response.MessageResponse;
import org.kane.server.mappers.comment.CommentCreateMapper;
import org.kane.server.services.CommentService;
import org.kane.server.validations.annotations.ResponseErrorValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/comment")
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {

    private final ResponseErrorValidation responseErrorValidation;
    private final CommentService commentService;
    private final CommentCreateMapper commentCreateMapper;

    @PostMapping("/create")
    public ResponseEntity<Object> createComment(@RequestBody CommentCreateDTO commentDTO,
                                                BindingResult result,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if(!ObjectUtils.isEmpty(errors)) return errors;
        var comment = commentService.saveComment(commentDTO, principal);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("all/{postId}")
    public ResponseEntity<List<CommentShowDTO>> getAllCommentsToPost(@PathVariable Long postId) {
        var list = commentService.getCommentsFromPostId(postId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new  MessageResponse("Comment deleted successfully"));
    }
}
