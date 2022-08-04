package iam.sde.udemyblog.repository;

import iam.sde.udemyblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
