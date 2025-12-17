package org.kane.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShowNameDTO {
    private Long userId;
    private String username;
    private String firstname;
    private String lastname;
}
