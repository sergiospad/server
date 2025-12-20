package org.kane.server.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShowNameDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
}
