package uz.limon.chatsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.dto.custom.ChatCustomDTO;
import uz.limon.chatsecurity.model.Chat;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ChatDTO toDto(Chat chat);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Chat toEntity(ChatDTO chatDTO);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ChatCustomDTO toCustomDto(Chat chat);
}
