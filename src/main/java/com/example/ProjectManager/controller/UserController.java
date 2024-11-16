package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.service.UserService;
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

    private UserService userService;

    @PostMapping("/new_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "Пользователь добавлен";
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> findByName(@PathVariable String name) {
        Optional<User> user = userService.findByName(name);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete_user/{name}")
    public void deleteUser(@PathVariable String name) {
        userService.deleteUser(name);
    }
}
