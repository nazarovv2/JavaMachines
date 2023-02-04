package uz.limon.chatsecurity.redis;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSession {

    @Id
    private String id;
    private String userInfo;
}
