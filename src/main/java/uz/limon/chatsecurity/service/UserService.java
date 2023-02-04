package uz.limon.chatsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.UserDTO;
import uz.limon.chatsecurity.dto.ValidatorDTO;
import uz.limon.chatsecurity.dto.custom.UserCustomDTO;
import uz.limon.chatsecurity.helper.AppCode;
import uz.limon.chatsecurity.helper.AppMessages;
import uz.limon.chatsecurity.helper.StringHelper;
import uz.limon.chatsecurity.mapper.UserMapper;
import uz.limon.chatsecurity.mapper.custom.UserCustomMapper;
import uz.limon.chatsecurity.model.Authorities;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.redis.UserSession;
import uz.limon.chatsecurity.redis.UserSessionRedisRepository;
import uz.limon.chatsecurity.repository.AuthorityRepository;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.UserRepository;
import uz.limon.chatsecurity.security.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static uz.limon.chatsecurity.helper.AppCode.*;
import static uz.limon.chatsecurity.helper.AppCode.OK;
import static uz.limon.chatsecurity.helper.AppMessages.*;
import static uz.limon.chatsecurity.security.roles.UserPermissions.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ValidatorService validator;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRedisRepository userSessionRedisRepository;
    private final JwtUtil jwtUtil;
    private final ChatRepository chatRepository;
    private final UserCustomMapper userCustomMapper;

    public ResponseDTO<?> addUser(UserDTO userDTO) {
        List<ValidatorDTO> errors = validator.validateUser(userDTO);
        if (!errors.isEmpty())
            return new ResponseDTO<>(
                    false,
                    VALIDATOR_ERROR,
                    VALIDATOR_MESSAGE,
                    userDTO,
                    errors);

        //get authorities as user role default
        Set<Authorities> authorities = authorityRepository
                .findAllByAuthorityIn(Set.of(
                        READ.getName(),
                        POST.getName(),
                        DELETE.getName(),
                        EDIT.getName()
                ));

        String salt = StringHelper.generateSalt();
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(salt + user.getPassword()));
        user.setAuthorities(authorities);
        user.setSalt(salt);

        userRepository.save(user);

        return new ResponseDTO<>(true, OK, SAVED, user.getId());
    }

    public ResponseDTO<String> generateJWT(UserDTO userDTO, HttpServletRequest request) {
        //check if username is exists in DB
        Optional<User> _user = userRepository.findFirstByUsername(userDTO.getPassword());
        if (_user.isEmpty()){
            return new ResponseDTO<>(false, AppCode.NOT_FOUND, AppMessages.NOT_FOUND, "username");
        }
        User user = _user.get();
        if (!passwordEncoder.matches(user.getPassword(), user.getSalt() + userDTO.getPassword())) {
            return new ResponseDTO<>(false, VALIDATOR_ERROR, MISMATCH, "password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String uuid = sysGuid();
        String userString = StringHelper.toJson(user);
        UserSession userSessionForCaching = UserSession
                .builder()
                .id(uuid)
                .userInfo(userString)
                .build();
        userSessionRedisRepository.save(userSessionForCaching);
        String token = jwtUtil.generateToken(uuid);
        return new ResponseDTO<>(true, OK, AppMessages.OK, token);
    }

    private String sysGuid(){
        return UUID.randomUUID().toString().toUpperCase().replace("-","");
    }

    public ResponseDTO<UserDTO> getById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return new ResponseDTO<>(false, AppCode.NOT_FOUND, AppMessages.NOT_FOUND, null);
        }
        UserCustomDTO userDTO = userCustomMapper.toDto(user.get());

        return new ResponseDTO<>(true, OK, AppMessages.OK, userDTO);
    }
}
