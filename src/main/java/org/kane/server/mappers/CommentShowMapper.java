package org.kane.server.mappers;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.CommentShowDTO;
import org.kane.server.entity.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentShowMapper implements Mapper<Comment, CommentShowDTO>{
    @Override
    public CommentShowDTO map(Comment from) {
        return CommentShowDTO.builder()
                .id(from.getId())
                .username(from.getCommentator().getUsername())
                .lastname(from.getCommentator().getLastname())
                .firstname(from.getCommentator().getFirstname())
                .creationDate(from.getCreatedDate())
                .message(from.getMessage())
                .build();
    }
}
