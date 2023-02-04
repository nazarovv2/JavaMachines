package uz.limon.chatsecurity.security.jwt;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.redis.UserSession;
import uz.limon.chatsecurity.redis.UserSessionRedisRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserSessionRedisRepository userSessionRedisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String uuid = jwtUtil.validateTokenAndGetSubject(token);
                if (uuid == null){
                    throw new RuntimeException("Invalid token or cannot parse");
                }
                Optional<UserSession> userSession = userSessionRedisRepository.findById(uuid);
                if (userSession.isEmpty()) {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    throw new RuntimeException("Token is expired or invalid");
                }
                String cachedUserString = userSession.get().getUserInfo();
                User user = fromStringToUser(cachedUserString);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.warn("Invalid token with error: {}", e.getMessage());
                SecurityContextHolder.getContext().setAuthentication(null);
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request, response);
    }

    private User fromStringToUser(String userCacheString){
        return new Gson().fromJson(userCacheString, User.class);
    }

}
