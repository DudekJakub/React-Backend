package com.reactbackend.model.Post;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostRequest {
    private Long authorId;
    private String title;
    private String body;
}
