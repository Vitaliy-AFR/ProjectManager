package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.MyUser;
import com.example.ProjectManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping("/new_user")
    public String addUser(@RequestBody MyUser user){
        service.addUser(user);
        return "Пользователь добавлен";
    }

    @GetMapping
    public List<MyUser> findAllUsers() {
        return service.findAllUsers();
    }
}
