package uz.limon.chatsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.limon.chatsecurity.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    Optional<User> findFirstByUsername(String username);
    boolean existsAllByIdIn(List<Integer> ids);
}
