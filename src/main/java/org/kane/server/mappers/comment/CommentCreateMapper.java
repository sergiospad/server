package org.kane.server.mappers.comment;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.comment.CommentCreateDTO;
import org.kane.server.entity.Comment;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateMapper implements Mapper<CommentCreateDTO, Comment> {

    @Override
    public Comment map(CommentCreateDTO from) {
        return Comment.builder()
                .message(from.getMessage())
                .build();
    }
}
