package uz.limon.chatsecurity.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.UserDTO;
import uz.limon.chatsecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserResources {

    private final UserService userService;

    @ApiOperation(value = "Registration of user")
    @PostMapping("/add")
    public ResponseDTO<?> register(@RequestBody @Valid UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @ApiOperation(value = "Get token with username and password. Token expires in 3 hours")
    @PostMapping("/token")
    public ResponseDTO<String> jwt(@RequestBody UserDTO userDTO, HttpServletRequest request){
        return userService.generateJWT(userDTO, request);
    }

    @GetMapping("/{userId}")
    public ResponseDTO<UserDTO> getUserById(@PathVariable Integer userId){
        return userService.getById(userId);
    }
}
