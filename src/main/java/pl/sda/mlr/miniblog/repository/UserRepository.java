package pl.sda.mlr.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.mlr.miniblog.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User> -> jeśli mogłoby zwrócić dowolną liczbę wyników (w tym 0)

    //zero lub 1 wynik
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.firstName = :name where u.id = :userId")
    void updateFirstName(@Param("name") String firstName, @Param("userId") Long id);

    @Modifying
    @Query("update User u set u.lastName = :name where u.id = :userId")
    void updateLastName(@Param("name") String lastName, @Param("userId") Long id);
}
