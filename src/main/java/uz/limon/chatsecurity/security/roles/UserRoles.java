package uz.limon.chatsecurity.security.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static uz.limon.chatsecurity.security.roles.UserPermissions.*;

public enum UserRoles {
    ADMIN(Set.of(DELETE, READ, EDIT, POST, PIN, CHAT)),
    USER(Set.of(READ, POST, EDIT, DELETE)),
    MODERATOR(Set.of(READ, POST, EDIT, DELETE, PIN));

    private final Set<UserPermissions> userPermissions;

    UserRoles(Set<UserPermissions> permissions){
        userPermissions = permissions;
    }

    public Set<GrantedAuthority> getUserPermissions() {
        Set<GrantedAuthority> set = userPermissions.stream()
                .map(s -> new SimpleGrantedAuthority(s.getName()))
                .collect(Collectors.toSet());
        set.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return set;
    }
}
