package org.kane.server.mappers.user;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.user.UserShowNameDTO;
import org.kane.server.entity.User;
import org.kane.server.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserShowNameMapper implements Mapper<User, UserShowNameDTO> {

    @Override
    public UserShowNameDTO map(User from) {
        return UserShowNameDTO.builder()
                .id(from.getId())
                .username(from.getUsername())
                .firstname(from.getFirstname())
                .lastname(from.getLastname())
                .lastname(from.getLastname())
                .build();
    }
}
