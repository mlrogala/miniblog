package pl.sda.mlr.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mlr.miniblog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
