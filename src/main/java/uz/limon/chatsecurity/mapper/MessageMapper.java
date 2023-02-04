package uz.limon.chatsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.limon.chatsecurity.dto.MessageDTO;
import uz.limon.chatsecurity.dto.custom.MessageCustomDTO;
import uz.limon.chatsecurity.model.Message;

@Mapper(componentModel = "jakarta")
public interface MessageMapper {

    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "chat", source = "chatId")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    MessageDTO toDto(Message message);

    @Mapping(target = "authorId", source = "author")
    @Mapping(target = "chatId", source = "chat")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Message toEntity(MessageDTO messageDTO);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    MessageCustomDTO toCustomDto(Message message);

}
