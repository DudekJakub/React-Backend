package com.reactbackend.service;

import com.reactbackend.model.MessageDto;
import com.reactbackend.model.Post.Post;
import com.reactbackend.model.Post.PostRequest;
import com.reactbackend.model.SearchTerm;
import com.reactbackend.repository.PostRepository;
import com.reactbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public Long createPost(PostRequest request){
        Post post = new Post();
        if(userRepository.findById(request.getAuthorId()).isPresent()) {
            post.setAuthor(userRepository.findById(request.getAuthorId()).get());
        } else {
            log.warn("Did not find author id: {}", request.getAuthorId());
        }
        post.setBody(request.getBody());
        post.setTitle(request.getTitle());
        post.setCreatedAt(LocalDate.now());
        Post save = postRepository.save(post);
        return save.getId();
    }
    public List<Post> showPosts(){
        List<Post> allPosts = new ArrayList<>();
        postRepository.findAll().forEach(allPosts::add);
        return allPosts;
    }

    public Post showPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Post editPost(Long id, PostRequest post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!Objects.equals(postToUpdate.getAuthor().getId(), post.getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        postToUpdate.setBody(post.getBody());
        postToUpdate.setTitle(post.getTitle());
        return postRepository.save(postToUpdate);
    }

    public MessageDto deletePost(Long id) {
        if(postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return new MessageDto("Success");
        }
        else {
            log.warn("There is no post with this id");
        }
        return new MessageDto("Fail");
    }

    public List<Post> searchForPosts(SearchTerm searchTerm) {
        System.out.println(searchTerm);
        return postRepository.findByTitleContainsIgnoreCaseOrBodyContainsIgnoreCase(searchTerm.getTerm(), searchTerm.getTerm());
    }
}
