package antifraud.models;

import antifraud.utils.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "system_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isLocked;

}