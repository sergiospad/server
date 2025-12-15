package org.kane.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"posts"})
@EqualsAndHashCode(exclude = {"posts"})
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String username;

    private String firstName;
    private String lastName;

    @Column(updatable = false)
    private String email;

    private String bio;
    private String password;
    private String avatar;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(
            cascade = CascadeType.REFRESH, //ALL
            fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true
    )
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @PrePersist
    private void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
