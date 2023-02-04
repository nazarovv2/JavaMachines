package uz.limon.chatsecurity.dto.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.dto.UserDTO;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserCustomDTO extends UserDTO {
    private List<ChatCustomDTO> chats;
}
