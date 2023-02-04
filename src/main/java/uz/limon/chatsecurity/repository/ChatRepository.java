package uz.limon.chatsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.model.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    boolean existsByName(String name);
    List<Chat> findAllByIdInOrderByCreatedAt(List<Integer> ids);
}
