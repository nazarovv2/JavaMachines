package uz.limon.chatsecurity.dto.custom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uz.limon.chatsecurity.dto.ChatDTO;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ChatCustomDTO extends ChatDTO {
    @ApiModelProperty(notes = "Creator of the chat")
    private String author;
}
