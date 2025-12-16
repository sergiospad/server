package org.kane.server.mappers;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.PostShowDTO;
import org.kane.server.entity.Comment;
import org.kane.server.entity.ImageModel;
import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostShowMapper implements Mapper<Post, PostShowDTO>{
    @Override
    public PostShowDTO map(Post from) {
        var img = from.getImages().stream()
                .map(ImageModel::getId)
                .toList();
        var likedUsers = from.getLikedUsers().stream()
                .map(User::getId)
                .toList();
        var comments = from.getComments().stream()
                .map(Comment::getId)
                .toList();
        return PostShowDTO.builder()
                .id(from.getId())
                .title(from.getTitle())
                .caption(from.getCaption())
                .location(from.getLocation())
                .author(from.getUser().getId())
                .createdDate(from.getCreatedDate())
                .likedUsers(likedUsers)
                .comments(comments)
                .images(img)
                .build();
    }
}
