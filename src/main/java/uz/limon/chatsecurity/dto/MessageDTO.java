package uz.limon.chatsecurity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uz.limon.chatsecurity.helper.AppMessages;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static uz.limon.chatsecurity.helper.AppMessages.*;

@Getter
@Setter
@ApiModel(description = "Model for transfer message info")
public class MessageDTO {

    @ApiModelProperty(notes = "Chat ID of the message", required = true)
    @NotNull(message = EMPTY_FIELD)
    private Integer chat;

    @ApiModelProperty(notes = "Author ID of the message", required = true)
    @NotNull(message = EMPTY_FIELD)
    private Integer author;

    @ApiModelProperty(notes = "Type of message, either TEXT, or IMAGE", required = true)
    @NotBlank(message = EMPTY_FIELD)
    private String type;

    @ApiModelProperty(notes = "Content of the message. " +
            "If message type is IMAGE, so content must be in BASE64 format", required = true)
    @NotBlank(message = EMPTY_FIELD)
    @Size(max = 3 * 256 * 1024 * 100, message = "Maximum image size is not higher than 100 MB")
    private String content;

    @ApiModelProperty(notes = "Extension of image. If message type is IMAGE, so extension is required")
    private String ext;

    @ApiModelProperty(notes = "Date of the message was written")
    private String createdAt;

}
