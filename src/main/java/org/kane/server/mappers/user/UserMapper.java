package org.kane.server.mappers.user;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.user.UserEditDTO;
import org.kane.server.entity.User;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEditDTO, User> {
    @Override
    public User map(UserEditDTO from) {
        return User.builder()
                .id(from.getId())
                .firstname(from.getFirstname())
                .lastname(from.getLastname())
                .bio(from.getBio())
                .build();
    }

    public User map(UserEditDTO from, User to){
        to.setBio(Optional.ofNullable(from.getBio()).orElse(to.getBio()));
        to.setFirstname(Optional.ofNullable(from.getFirstname()).orElse(to.getFirstname()));
        to.setLastname(Optional.ofNullable(from.getLastname()).orElse(to.getLastname()));

        return to;
    }
}
