package org.kane.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentShowDTO {
    Long id;
    String username;
    String lastname;
    String firstname;
    String message;
    LocalDateTime creationDate;
}
