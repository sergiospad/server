package org.kane.server.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostShowDTO {
    Long id;
    String title;
    String caption;
    String location;
    Long author;
    String authorFirstname;
    String authorLastname;
    LocalDateTime createdDate;
    List<Long> images;
    List<Long> likedUsers;
}
