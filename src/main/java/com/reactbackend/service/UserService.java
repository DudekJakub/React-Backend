package com.reactbackend.service;

import com.reactbackend.model.MessageDto;
import com.reactbackend.model.Post.Post;
import com.reactbackend.model.User.*;
import com.reactbackend.repository.PostRepository;
import com.reactbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private PostRepository postRepository;

    public User saveUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.warn("User already exists");
            return null;
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User userToSave = new User(userDTO);
        return userRepository.save(userToSave);
    }

    public List<User> showUsers() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    public User login(UserLoginRequest user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (passwordEncoder.matches(user.getPassword(), userOptional.get().getPassword())) {
            return userOptional.get();
        } else {
            log.warn("Wrong password !");
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserDetails userToReturn = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
        return userToReturn;
    }

    public User getProfile(String username, UserFollowDTO whoVisits) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.setAvatar("https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128");
        User userWhoVisits = userRepository.findById(whoVisits.getId()).orElseThrow();
        if(user.getFollowers().contains(userWhoVisits)){
            user.setFollowing(true);
        }
        user.setPostCount(postRepository.countByAuthor(user));
        return user;
    }

    public List<Post> showPostsOfUser(String username) {
        return postRepository.findByAuthor_Username(username);
    }

    public MessageDto followUser(String username, UserFollowDTO userWhoFollows) {
        User user = userRepository.findById(userWhoFollows.getId()).orElseThrow();
        User userToFollow = userRepository.findByUsername(username).orElseThrow();
        if(userToFollow.getFollowers().contains(user)){
            return new MessageDto("Cant follow again");
        }
        userToFollow.getFollowers().add(user);
        userToFollow.setFollowerCount(Long.valueOf(userToFollow.getFollowers().size()));
        if(user.getFollowingCount() == null){
            user.setFollowingCount(1L);
        }
        else {
            user.setFollowingCount(user.getFollowingCount()+1);
        }
        userRepository.save(userToFollow);
        userRepository.save(user);
        return new MessageDto("Success");
    }

    private boolean alreadyFollows(User user, User userToFollow){
        System.out.println(userToFollow.getFollowers());
        return userToFollow.getFollowers().contains(user);
    }

    public MessageDto unfollow(String username, UserFollowDTO userWhoVisits) {
        User user = userRepository.findById(userWhoVisits.getId()).orElseThrow();
        User userToUnfollow = userRepository.findByUsername(username).orElseThrow();
        if(userToUnfollow.getFollowers().contains(user)){
            userToUnfollow.getFollowers().remove(user);
            userToUnfollow.setFollowerCount(Long.valueOf(userToUnfollow.getFollowers().size()));
            user.setFollowingCount(user.getFollowingCount()-1);
            userRepository.save(user);
            userRepository.save(userToUnfollow);
        }else {
            return new MessageDto("Fail");
        }
        return new MessageDto("Success");
    }

    public Set<User> getFollowers(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getFollowers();
    }

    public List<User> getFollowing(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return userRepository.findByFollowers_Username(user.getUsername());
    }

    public List<Post> getFeed(UserFollowDTO userToGetFeed) {
        User user = userRepository.findById(userToGetFeed.getId()).orElseThrow();
        return postRepository.findByAuthor_Followers_UsernameIgnoreCase(user.getUsername());
    }

    public boolean doesExist(UserExist user) {
        return userRepository.existsByUsername(user.getUsername());
    }

    public boolean doesExist(UserEmailExist user) {
        return userRepository.existsByEmail(user.getEmail());
    }
}
