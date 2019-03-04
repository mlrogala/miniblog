package pl.sda.mlr.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mlr.miniblog.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User> -> jeśli mogłoby zwrócić dowolną liczbę wyników (w tym 0)

    //zero lub 1 wynik
    Optional<User> findByEmail(String email);
}
