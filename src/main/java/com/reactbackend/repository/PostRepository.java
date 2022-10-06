package com.reactbackend.repository;

import com.reactbackend.model.Post.Post;
import com.reactbackend.model.User.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    long countByAuthor(User author);
    List<Post> findByAuthor_Username(String username);

    List<Post> findByTitleContainsIgnoreCaseOrBodyContainsIgnoreCase(String title, String body);






}
