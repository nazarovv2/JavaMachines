package uz.limon.chatsecurity.mapper;

import org.mapstruct.Mapper;
import uz.limon.chatsecurity.dto.UserDTO;
import uz.limon.chatsecurity.dto.custom.UserCustomDTO;
import uz.limon.chatsecurity.model.User;

@Mapper(componentModel = "jakarta")
public interface UserMapper {

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    UserCustomDTO toCustomDTO(User user);

}
