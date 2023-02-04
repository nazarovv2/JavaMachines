package uz.limon.chatsecurity.mapper.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import uz.limon.chatsecurity.dto.custom.ChatCustomDTO;
import uz.limon.chatsecurity.mapper.ChatMapper;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class ChatCustomMapper {

    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    public ChatCustomDTO toDto(Chat chat){
        ChatCustomDTO dto = chatMapper.toCustomDto(chat);

        userRepository.findById(chat.getAuthorId()).ifPresentOrElse(
                u -> dto.setAuthor(String.format("%s %s",u.getFirstName(), u.getLastName())),
                () -> dto.setAuthor("DELETED ACCOUNT")
        );

        return dto;
    }
}
