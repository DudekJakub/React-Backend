package com.reactbackend.model.Post;

import com.reactbackend.model.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String body;
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;
}
