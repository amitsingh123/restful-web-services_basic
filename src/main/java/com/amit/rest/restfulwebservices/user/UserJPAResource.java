package com.amit.rest.restfulwebservices.user;

import com.amit.rest.restfulwebservices.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/jpa/users")
    public List<User> getAllUsers() {
        return userJPARepository.findAll();
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> saveUser(@RequestBody  @Valid User user) {
        User savedUser = userJPARepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<Optional<User>> getUser(@PathVariable int id) {
        Optional<User> user = userJPARepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id -"+id);
        EntityModel<Optional<User>> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userJPARepository.deleteById(id);
    }

    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        Optional<User> user = userJPARepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id.."+id+"not found");
        return user.get().getPosts();
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> createUserPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userJPARepository.findById(id);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("id.."+id+"not found");
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
