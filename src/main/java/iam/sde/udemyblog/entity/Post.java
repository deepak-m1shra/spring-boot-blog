package iam.sde.udemyblog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
// TODO: Note: When using @Data we were getting ModelMapper error due to usage of toString() method.. (debug)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "posted_by")
    private String postedBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy is specified in the owing side
    // maps to the owning side (post owns comments specified by post)
    private Set<Comment> comments = new HashSet<>();
}
