package uz.limon.chatsecurity.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.ValidatorDTO;
import uz.limon.chatsecurity.exceptions.ImageNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidatorDTO>> handle(MethodArgumentNotValidException e, WebRequest request) {
        List<ValidatorDTO> errors = e.getBindingResult().getFieldErrors().stream()
                .map(ee -> ValidatorDTO.builder()
                        .fieldName(ee.getField())
                        .error(ee.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIO(IOException e){
        return ResponseEntity.status(500).body("Error while reading/saving file " + e.getMessage());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleImageNotFound(ImageNotFoundException e){
        return ResponseEntity.status(404).body(
                ResponseDTO.builder()
                .code(-1)
                .message("Image not found")
                .success(false)
                .data(e.getMessage())
                .build()
        );
    }

}
