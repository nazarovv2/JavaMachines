package uz.limon.chatsecurity.mapper.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.limon.chatsecurity.dto.custom.UserCustomDTO;
import uz.limon.chatsecurity.mapper.UserMapper;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.model.ChatUsers;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.ChatUsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserCustomMapper {

    private final UserMapper userMapper;
    private final ChatCustomMapper chatMapper;
    private final ChatUsersRepository chatUsersRepository;
    private final ChatRepository chatRepository;

    public UserCustomDTO toDto(User user){
        UserCustomDTO userCustomDTO = userMapper.toCustomDTO(user);

        List<Integer> chatIds = chatUsersRepository.findAllByUserId(user.getId())
                .stream()
                .map(ChatUsers::getChatId)
                .collect(Collectors.toList());

        userCustomDTO.setChats(
                chatRepository.findAllByIdInOrderByCreatedAt(chatIds).stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList())
        );

        return userCustomDTO;
    }
}
