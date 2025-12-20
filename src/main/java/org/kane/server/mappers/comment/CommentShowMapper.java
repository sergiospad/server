package org.kane.server.mappers.comment;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.comment.CommentShowDTO;
import org.kane.server.entity.Comment;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentShowMapper implements Mapper<Comment, CommentShowDTO> {
    @Override
    public CommentShowDTO map(Comment from) {
        return CommentShowDTO.builder()
                .id(from.getId())
                .postId(from.getPost().getId())
                .username(from.getCommentator().getUsername())
                .lastname(from.getCommentator().getLastname())
                .firstname(from.getCommentator().getFirstname())
                .commentatorId(from.getCommentator().getId())
                .creationDate(from.getCreatedDate())
                .message(from.getMessage())
                .build();
    }
}
