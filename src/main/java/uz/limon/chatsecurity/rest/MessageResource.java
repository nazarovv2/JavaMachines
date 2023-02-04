package uz.limon.chatsecurity.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uz.limon.chatsecurity.dto.MessageDTO;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.custom.MessageCustomDTO;
import uz.limon.chatsecurity.exceptions.ImageNotFoundException;
import uz.limon.chatsecurity.service.MessageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageResource {

    private final MessageService messageService;

    @ApiOperation(value = "Write message to a chat. Type of message must be either TEXT or IMAGE")
    @PostMapping("/add")
    public ResponseDTO<Integer> add(@RequestBody MessageDTO messageDTO) throws IOException {
        return messageService.add(messageDTO);
    }

    @ApiOperation(value = "Get all messages of the chat. It returns messages with author name and chat name")
    @GetMapping("/by-chat/{chatId}")
    public ResponseDTO<List<MessageCustomDTO>> getAll(@PathVariable Integer chatId){
        return messageService.getMessagesByChatId(chatId);
    }

    @GetMapping("/image")
    public ResponseDTO<String> getImage(@RequestParam MultiValueMap<String, String> params) throws ImageNotFoundException, IOException {
        return messageService.getImage(params);
    }
}
