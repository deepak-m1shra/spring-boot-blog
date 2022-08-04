package iam.sde.udemyblog.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;


    // TODO: Understand this in depth [OneToOne, OneToMany, ManyToOne, ManyToMany]
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
