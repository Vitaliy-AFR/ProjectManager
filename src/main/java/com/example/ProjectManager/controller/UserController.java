package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserServiceImpl service;

    @PostMapping("/new_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user){
        service.addUser(user);
        return "Пользователь добавлен";
    }

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> findByName(@PathVariable String name) {
        Optional<User> user = service.findByName(name);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete_user/{name}")
    public void deleteUser(@PathVariable String name) {
        service.deleteUser(name);
    }
}
