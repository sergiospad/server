package org.kane.server.mappers;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.CommentCreateDTO;
import org.kane.server.entity.Comment;
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
