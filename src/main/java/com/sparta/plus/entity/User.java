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
    private String nickName;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_repassword", nullable = false)
    private String rePassword;

    public User(String nickName, String password, String rePassword) {
        this.nickName = nickName;
        this.password = password;
        this.rePassword = rePassword;
    }
}
