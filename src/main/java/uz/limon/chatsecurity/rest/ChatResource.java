package uz.limon.chatsecurity.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.custom.ChatCustomDTO;
import uz.limon.chatsecurity.service.ChatService;

import java.util.List;

@ResponseStatus
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatResource {

    private final ChatService chatService;

    @ApiOperation(value = "Add new chat", nickname = "add chat")
    @PostMapping("/add")
    public ResponseDTO<Integer> addChat(@RequestBody ChatDTO chatDTO){
        return chatService.addChat(chatDTO);
    }

    @ApiOperation(value = "Add new member to the chat. You need to give chatId and userId", nickname = "add member")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'CHAT')")
    @PostMapping("/add-member")
    public ResponseDTO<String> addMember(@RequestParam Integer chatId, @RequestParam Integer userId){
        return chatService.addMember(chatId, userId);
    }

    @ApiOperation(value = "List of all chats. Returns chat's author too.")
    @GetMapping("/all-chats")
    public ResponseDTO<List<ChatCustomDTO>> allChats(){
        return chatService.getAll();
    }

    @ApiOperation(value = "Chat info with list of user IDs who are members of the chat")
    @GetMapping("/{chatId}")
    public ResponseDTO<ChatDTO> getChatById(@PathVariable Integer chatId){
        return chatService.getChatById(chatId);
    }

    @ApiOperation(value = "Get all chats with user ID")
    @GetMapping("/get-all")
    public ResponseDTO<List<ChatCustomDTO>> chatsByUserId(@RequestParam Integer userId){
        return chatService.getChatsByUserId(userId);
    }
}
