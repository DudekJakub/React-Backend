package com.reactbackend.controller;

import com.reactbackend.model.MessageDto;
import com.reactbackend.model.Post.Post;
import com.reactbackend.model.Post.PostRequest;
import com.reactbackend.model.SearchTerm;
import com.reactbackend.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@Slf4j
public class PostController {

    private PostService postService;

    @PostMapping("/create-post")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody PostRequest request){
        return postService.createPost(request);
    }

    @GetMapping("/post/{id}")
    public Post showPost(@PathVariable Long id) {
        return postService.showPost(id);
    }

    @PostMapping("/post/{id}/edit")
    public Post editPost(@PathVariable Long id, @RequestBody PostRequest post) {
        return postService.editPost(id, post);
    }

    @DeleteMapping("/post/{id}")
    public MessageDto deletePost(@PathVariable Long id){
       return postService.deletePost(id);
    }

    @PostMapping("/posts/search")
    public List<Post> searchForPosts(@RequestBody SearchTerm searchTerm){
        return postService.searchForPosts(searchTerm);
    }

    @GetMapping("/posts")
    public List<Post> showPosts(){
        return postService.showPosts();
    }


}
