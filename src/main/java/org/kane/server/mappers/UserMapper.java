package org.kane.server.mappers;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.UserEditDTO;
import org.kane.server.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEditDTO, User> {
    @Override
    public User map(UserEditDTO from) {
        return User.builder()
                .id(from.getId())
                .firstName(from.getFirstname())
                .lastName(from.getLastname())
                .bio(from.getBio())
                .avatar(from.getImageURL())
                .build();
    }

    public User map(UserEditDTO from, User to){
        to.setBio(Optional.ofNullable(from.getBio()).orElse(to.getBio()));
        to.setFirstName(Optional.ofNullable(from.getFirstname()).orElse(to.getFirstName()));
        to.setLastName(Optional.ofNullable(from.getLastname()).orElse(to.getLastName()));

        return to;
    }
}
