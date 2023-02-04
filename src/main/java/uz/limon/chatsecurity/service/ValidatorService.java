package uz.limon.chatsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.dto.MessageDTO;
import uz.limon.chatsecurity.dto.UserDTO;
import uz.limon.chatsecurity.dto.ValidatorDTO;
import uz.limon.chatsecurity.helper.AppMessages;
import uz.limon.chatsecurity.helper.MessageType;
import uz.limon.chatsecurity.helper.StringHelper;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.ChatUsersRepository;
import uz.limon.chatsecurity.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static uz.limon.chatsecurity.helper.MessageType.IMAGE;
import static uz.limon.chatsecurity.helper.MessageType.TEXT;

@RequiredArgsConstructor
public class ValidatorService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatUsersRepository chatUsersRepository;

    public List<ValidatorDTO> validateUser(UserDTO user){
        List<ValidatorDTO> errors = new ArrayList<>();

        if (userRepository.existsByUsername(user.getUsername()))
            addError("username", AppMessages.NOT_UNIQUE, errors);

        if (!StringHelper.isValidPassword(user.getPassword()))
            addError("password", "Must contains at least 1 upper, 1 lower and 1 numeric character", errors);


        return errors;
    }

    public List<ValidatorDTO> validateChat(ChatDTO chatDTO, List<User> users_) {
        List<ValidatorDTO> errors = new ArrayList<>();

        if (chatRepository.existsByName(chatDTO.getName()))
            addError("name", AppMessages.NOT_UNIQUE, errors);

        //add all not founded users
        List<Integer> users = users_
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());

        if (users.size() != chatDTO.getUserIds().size()) {
            errors.addAll(chatDTO.getUserIds().stream()
                    .filter(id -> !users.contains(id))
                    .map(id -> ValidatorDTO.builder()
                            .fieldName("ID")
                            .error(String.format("User with %d ID is not exists", id))
                            .build())
                    .collect(Collectors.toList()));
        }
        return errors;
    }

    public List<ValidatorDTO> checkUserAndChat(Integer chatId, Integer userId) {
        List<ValidatorDTO> errors = new ArrayList<>();

        if (!userRepository.existsById(userId))
            addError("user", AppMessages.NOT_FOUND, errors);

        if (!chatRepository.existsById(chatId))
            addError("chat", AppMessages.NOT_FOUND, errors);

        if (chatUsersRepository.existsByChatIdAndUserId(chatId, userId))
            addError("userId", "This user is already exists in this chat", errors);

        return errors;
    }

    public List<ValidatorDTO> validateMessage(MessageDTO messageDTO) {
        List<ValidatorDTO> errors = new ArrayList<>();

        if (!userRepository.existsById(messageDTO.getAuthor())){
            addError("author", AppMessages.NOT_FOUND, errors);
        }

        if (!chatRepository.existsById(messageDTO.getChat())){
            addError("chat", AppMessages.NOT_FOUND, errors);
        }

        if (!Arrays.asList(IMAGE.name(), TEXT.name()).contains(messageDTO.getType())){
            addError("type", AppMessages.INCORRECT_TYPE, errors);
        }

        return errors;
    }

    private void addError(String fieldName, String error, List<ValidatorDTO> list){
        list.add(ValidatorDTO.builder()
                .error(error)
                .fieldName(fieldName)
                .build());
    }
}
