package iam.sde.udemyblog.repository;

import iam.sde.udemyblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailOrUsername(String email, String username);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);
}
