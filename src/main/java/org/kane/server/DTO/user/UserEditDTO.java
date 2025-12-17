package org.kane.server.DTO.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserEditDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String bio;
    private String imageURL;
}
