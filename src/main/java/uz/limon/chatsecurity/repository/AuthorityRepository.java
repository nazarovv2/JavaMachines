package uz.limon.chatsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.limon.chatsecurity.model.Authorities;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authorities, Integer> {
    Set<Authorities> findAllByAuthorityIn(Set<String> auth);
}
