package com.sparta.plus.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_nickname",unique = true)
    private String Nickname;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_repassword", nullable = false)
    private String rePassword;

    public User(String nickname, String password) {
        Nickname = nickname;
        this.password = password;
    }
}
