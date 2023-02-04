package uz.limon.chatsecurity.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authority")
    private String authority;

    public Authorities(String authority) {
        this.authority = authority;
    }
}
