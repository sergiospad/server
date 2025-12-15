package org.kane.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"comments", "user"})
@EqualsAndHashCode(of = {"id", "title", "caption"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String caption;
    private String location;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Long> likedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER,
            mappedBy = "post",
            orphanRemoval = true
    )
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }

    public void addLikedUser(Long id){
        likedUsers.add(id);
    }

    public void removeLikedUser(Long id){
        likedUsers.remove(id);
    }

    @OneToMany(
            cascade = CascadeType.REFRESH, //ALL
            fetch = FetchType.LAZY,
            mappedBy = "post",
            orphanRemoval = true
    )
    @Builder.Default
    private List<ImageModel> images = new ArrayList<>();

}
