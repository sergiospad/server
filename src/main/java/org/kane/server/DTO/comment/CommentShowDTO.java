package org.kane.server.DTO.comment;

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
    Long postId;
    Long commentatorId;
    String username;
    String lastname;
    String firstname;
    String message;
    LocalDateTime creationDate;
}
