package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void deleteUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
    }
}
