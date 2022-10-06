package com.reactbackend.controller;

import com.reactbackend.model.MessageDto;
import com.reactbackend.model.Post.Post;
import com.reactbackend.model.User.User;
import com.reactbackend.model.User.UserDTO;
import com.reactbackend.model.User.UserFollowDTO;
import com.reactbackend.model.User.UserLoginRequest;
import com.reactbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserLoginRequest user){
        return userService.login(user);
    }

    @PostMapping("/profile/{username}")
    public User getProfile(@PathVariable String username, @RequestBody UserFollowDTO whoVisits){
        return userService.getProfile(username, whoVisits);
    }

    @GetMapping("/profile/{username}/posts")
    public List<Post> showPostsOfUser(@PathVariable String username){
        return userService.showPostsOfUser(username);
    }

    @PostMapping("/addFollow/{username}")
    public MessageDto followUser(@PathVariable String username, @RequestBody UserFollowDTO user){
        return userService.followUser(username, user);
    }

    @GetMapping("/list")
    public List<User> showUsers(){
        return userService.showUsers();
    }
}
