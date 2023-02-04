package uz.limon.chatsecurity.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;
    @Column(name = "username")
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authorities> authorities;

    @ManyToMany(mappedBy = "users")
    private transient List<Chat> chats;

    public User(Integer id, String firstName, String lastName, String phoneNumber, String password, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(
                a -> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
