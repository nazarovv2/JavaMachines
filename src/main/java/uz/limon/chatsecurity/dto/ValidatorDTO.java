package uz.limon.chatsecurity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatorDTO {
    private String fieldName;
    private String error;
}
