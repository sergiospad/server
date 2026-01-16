package org.kane.server.mappers.post;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.post.PostEditDTO;
import org.kane.server.entity.Post;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostEditMapper implements Mapper<PostEditDTO, Post> {
    @Override
    public Post map(PostEditDTO from) {
        return Post.builder()
                .id(from.getId())
                .title(from.getTitle())
                .caption(from.getCaption())
                .location(from.getLocation())
                .build();
    }

    public Post map(PostEditDTO from, Post to){
        to.setTitle(from.getTitle());
        to.setCaption(from.getCaption());
        to.setLocation(from.getLocation());
        return to;
    }
}
