package uz.limon.chatsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import uz.limon.chatsecurity.dto.MessageDTO;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.ValidatorDTO;
import uz.limon.chatsecurity.dto.custom.MessageCustomDTO;
import uz.limon.chatsecurity.exceptions.ImageNotFoundException;
import uz.limon.chatsecurity.helper.AppCode;
import uz.limon.chatsecurity.helper.AppMessages;
import uz.limon.chatsecurity.helper.MessageType;
import uz.limon.chatsecurity.helper.StringHelper;
import uz.limon.chatsecurity.mapper.MessageMapper;
import uz.limon.chatsecurity.mapper.custom.MessageCustomMapper;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.model.Message;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.MessageRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.limon.chatsecurity.helper.AppMessages.NOT_FOUND;
import static uz.limon.chatsecurity.helper.AppMessages.OK;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ValidatorService validatorService;
    private final MessageMapper messageMapper;
    private final FileService fileService;
    private final ChatRepository chatRepository;
    private final MessageCustomMapper customMapper;


    public ResponseDTO<Integer> add(MessageDTO messageDTO) throws IOException {
        List<ValidatorDTO> errors = validatorService.validateMessage(messageDTO);

        if (!errors.isEmpty())
            return new ResponseDTO<>(false, AppCode.VALIDATOR_ERROR, AppMessages.VALIDATOR_MESSAGE, null, errors);

        Message message = messageMapper.toEntity(messageDTO);
        message.setCreatedAt(new Date());

        if (messageDTO.getType().equals(MessageType.IMAGE.name())){
            message.setContent(fileService.saveFile(messageDTO.getContent(), messageDTO.getExt()));
        }

        messageRepository.save(message);

        return new ResponseDTO<>(true, AppCode.OK, AppMessages.OK, message.getId());
    }

    public ResponseDTO<List<MessageCustomDTO>> getMessagesByChatId(Integer chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isEmpty())
            return new ResponseDTO<>(false, AppCode.NOT_FOUND, NOT_FOUND, null);

        List<Message> messages = messageRepository.findAllByChatId(chatId);

        List<MessageCustomDTO> messageDtos = messages.stream()
                .map(customMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseDTO<>(true, AppCode.OK, AppMessages.OK, messageDtos);
    }

    public ResponseDTO<String> getImage(MultiValueMap<String, String> params) throws ImageNotFoundException, IOException {

            Optional<Message> message = messageRepository.findById(StringHelper.parseToInt(params.getFirst("messageId")));
            if (message.isEmpty()){
                return new ResponseDTO<>(false, AppCode.NOT_FOUND, NOT_FOUND, null);
            }

            if (!message.get().getType().equals("IMAGE")){
                return new ResponseDTO<>(false, AppCode.ERROR,  "This message is not image", null);
            }

            String base64 = fileService.getByPath(message.get().getContent(), message.get().getExt());

            return new ResponseDTO<>(true, AppCode.OK,  OK, base64);
    }
}
