package pl.sda.mlr.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mlr.miniblog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getById(Long postId);
}
