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
@ToString(of = {"id", "title", "caption", "location", "createdDate"})
@EqualsAndHashCode(of = {"id", "title", "caption", "location", "createdDate"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String caption;
    private String location;

    @ManyToMany
    @JoinTable(name = "likes_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> likedUsers = new HashSet<>();

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

    public void addLikedUser(User user){
        likedUsers.add(user);
    }

    public void removeLikedUser(User user){
        likedUsers.remove(user);
    }

    @OneToMany(
            cascade = CascadeType.REFRESH, //ALL
            fetch = FetchType.EAGER,
            mappedBy = "post",
            orphanRemoval = true
    )
    @Builder.Default
    private List<ImageModel> images = new ArrayList<>();

    public void addImage(ImageModel imageModel){
        images.add(imageModel);
        imageModel.setPost(this);
    }

    public void removeImage(ImageModel imageModel){
        images.remove(imageModel);
    }

}
