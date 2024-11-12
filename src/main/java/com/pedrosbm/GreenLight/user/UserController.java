package com.pedrosbm.GreenLight.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping()
    public List<User> getUsers() {
        return repository.findAll();
    }

    @PostMapping()
    public User createUser(@RequestBody User entity) {
        repository.save(entity);

        return entity;
    }
    
    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User entity) {
        var exists = repository.existsById(id);

        if(exists){
            repository.save(entity);
        }
        return entity;
    }
}
