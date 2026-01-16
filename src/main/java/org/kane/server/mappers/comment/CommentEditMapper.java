package org.kane.server.mappers.comment;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.comment.CommentEditDTO;
import org.kane.server.entity.Comment;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEditMapper implements Mapper<CommentEditDTO, Comment> {
    @Override
    public Comment map(CommentEditDTO from) {
        return Comment.builder()
                .id(from.getId())
                .message(from.getMessage())
                .build();
    }

    public Comment map(CommentEditDTO from, Comment to) {
        to.setId(from.getId());
        to.setMessage(from.getMessage());
        return to;
    }
}
