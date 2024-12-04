package com.example.ProjectManager.controller;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.mapper.UserMapper;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.model.response.UserDTO;
import com.example.ProjectManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private static final String USER_CREATED = "Пользователь добавлен";
    private static final String USER_DELETED = "Пользователь удален";
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;

    @PostMapping("/new_user")
    public ResponseEntity<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(USER_CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok()
                        .body(userMapper.toUserDTOList(userService.findAllUsers()) );
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserDTO> findByName(@PathVariable String name) throws NotFoundException {
        return ResponseEntity.ok()
                .body(userMapper.userToDTO(userService.findByName(name).get()) );
    }

    @DeleteMapping("delete_user/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name) throws NotFoundException {
        userService.deleteUser(name);
        return ResponseEntity.ok()
                .body(USER_DELETED);
    }
}
