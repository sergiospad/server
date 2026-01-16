package org.kane.server.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEditDTO {
    Long id;
    String title;
    String caption;
    String location;
}
