package uz.limon.chatsecurity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static uz.limon.chatsecurity.helper.AppMessages.EMPTY_FIELD;
import static uz.limon.chatsecurity.helper.AppMessages.MISMATCH;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model for transfer user info")
public class UserDTO {

    @ApiModelProperty(notes = "ID of the user")
    protected Integer id;

    @ApiModelProperty(notes = "First name of the user. Must contains at least 3 characters",
            required = true)
    @NotBlank(message = EMPTY_FIELD)
    @Size(min = 3, message = "At least must be 3 characters")
    protected String firstName;

    @ApiModelProperty(notes = "Last name of the user. Must contains at least 3 characters",
            required = true)
    @NotBlank(message = EMPTY_FIELD)
    @Size(min = 3, message = "At least must be 3 characters")
    protected String lastName;

    @ApiModelProperty(notes = "Phone number of the user. Note that enter only valid Uzbekistan's numbers",
            required = true)
    @NotBlank(message = EMPTY_FIELD)
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$", message = MISMATCH)
    protected String phoneNumber;

    @ApiModelProperty(notes = "Unique username of the user in the system",
            required = true)
    @NotBlank(message = EMPTY_FIELD)
    protected String username;

    @ApiModelProperty(notes = "Password of the user. " +
            "Note that password must contains at least 1 upper, 1 lower and 1 numeric character")
    @NotBlank(message = EMPTY_FIELD)
    protected String password;

    public UserDTO(Integer id, String firstName, String lastName, String phoneNumber, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }
}

