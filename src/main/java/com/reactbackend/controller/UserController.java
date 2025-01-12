package com.reactbackend.controller;

import com.reactbackend.model.MessageDto;
import com.reactbackend.model.Post.Post;
import com.reactbackend.model.User.*;
import com.reactbackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
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

    @PostMapping("/removeFollow/{username}")
    public MessageDto unfollowUser(@PathVariable String username, @RequestBody UserFollowDTO user){
        return userService.unfollow(username, user);
    }

    @GetMapping("/profile/{username}/followers")
    public Set<User> getFollowers(@PathVariable String username){
        return userService.getFollowers(username);
    }

    @GetMapping("/profile/{username}/following")
    public List<User> getFollowing(@PathVariable String username){
        return userService.getFollowing(username);
    }

    @PostMapping("/profile/getHomeFeed")
    public List<Post> getFeed(@RequestBody UserFollowDTO user){
        return userService.getFeed(user);
    }

    @GetMapping("/doesUsernameExist")
    public boolean doesUsernameExist(@RequestParam String username){
        return userService.doesExistByUsername(username);
    }

    @GetMapping("/doesEmailExist")
    public boolean doesEmailExist(@RequestParam String email){
        return userService.doesExistByEmail(email);
    }

    @GetMapping("/list")
    public List<User> showUsers(){
        return userService.showUsers();
    }
}
