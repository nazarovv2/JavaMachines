package uz.limon.chatsecurity.mapper.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.limon.chatsecurity.dto.custom.MessageCustomDTO;
import uz.limon.chatsecurity.mapper.MessageMapper;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.model.Message;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.MessageRepository;
import uz.limon.chatsecurity.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageCustomMapper {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    public MessageCustomDTO toDto(Message message){
        MessageCustomDTO dto = messageMapper.toCustomDto(message);
        Optional<User> user = userRepository.findById(message.getAuthorId());
        user.ifPresentOrElse(u -> dto.setUserName(String.format("%s %s", u.getFirstName(), u.getLastName())),
                () -> dto.setUserName("DELETED ACCOUNT"));

        Optional<Chat> chat = chatRepository.findById(message.getChatId());
        chat.ifPresentOrElse(ch -> dto.setChatName(ch.getName()),
                ()-> dto.setChatName("DELETED CHAT"));

        return dto;
    }
}
