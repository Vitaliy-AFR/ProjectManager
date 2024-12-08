package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.client.PersonFeignClient;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.PersonUserService;
import com.example.ProjectManager.service.UserService;
import com.example.demo.model.response.PersonDTO;
import com.example.demo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_EXIST = "Такого пользователя не существует";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PersonUserService personUserService;

    @Override
    @Transactional
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        personUserService.addPerson(user); //сделать проверку что статус ок
    }

    @Override
    public List<PersonDTO> findAllUsers(){
        return personUserService.findAll();
    }

    @Override
    public Optional<User> findByName(String name) throws NotFoundException {
        if (userRepository.findByName(name).isEmpty()) {
            throw new NotFoundException(USER_NOT_EXIST);
        }
        return userRepository.findByName(name);
    }

    @Override
    public void deleteUser(String name) throws NotFoundException {
        if (userRepository.findByName(name).isEmpty()) {
            throw new NotFoundException(USER_NOT_EXIST);
        }
        userRepository.delete(userRepository.findByName(name).get());
    }
}
