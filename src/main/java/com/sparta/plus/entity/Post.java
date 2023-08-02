package com.sparta.plus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long PostId;

    @Column(name = "post_title",  nullable = false, unique = true, length =  100)
    private String title;

    @Column(name = "post_contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "post_author", length = 100)
    private String author;

    //setAuthor
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.author = user.getNickName();
        this.user = user;
    }


}
