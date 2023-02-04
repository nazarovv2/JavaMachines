package uz.limon.chatsecurity.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRedisRepository extends CrudRepository<UserSession, String> {

}
