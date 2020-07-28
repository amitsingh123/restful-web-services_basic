package com.amit.rest.restfulwebservices.user;

import com.amit.rest.restfulwebservices.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> saveUser(@RequestBody  @Valid User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id) {
        User user = userRepository.findOne(id);
        if(user == null)
            throw new UserNotFoundException("id -"+id);
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User user = userRepository.delete(id);
        if(user == null) {
           throw new UserNotFoundException("id -"+id);
           //return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
