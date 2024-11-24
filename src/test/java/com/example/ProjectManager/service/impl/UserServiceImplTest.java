package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserServiceImpl.class
})
class UserServiceImplTest {

    private static final String USER_NOT_EXIST = "Такого пользователя не существует";
    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl userService;

    @Test
    void addUser() {

        //given
        User user = User.builder().name("user").build();


        //when
        userService.addUser(user);

        //then
        InOrder inOrder = Mockito.inOrder(passwordEncoder, userRepository);
        inOrder.verify(passwordEncoder, times(1)).encode(user.getPassword());
        inOrder.verify(userRepository, times(1)).save(user);

    }

    @Test
    void findAllUsers() {

        //when
        userService.findAllUsers();

        //then
        verify(userRepository).findAll();

    }

    @Test
    void findByName_ifUserExist() throws NotFoundException {

        //given
        String name = "user";
        User user = User.builder().name(name).build();
        when(userRepository.findByName(name)).thenReturn(Optional.of(user));

        //when
        userService.findByName(name);

        //then
        verify(userRepository, times(2)).findByName(name);
    }

    @Test
    void findByName_ifUserNotExist() {

        //given
        String name = "user";
        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            userService.findByName(name);
        });

        //then
        verify(userRepository, times(1)).findByName(name);
        assertEquals(USER_NOT_EXIST, exception.getMessage());
    }

    @Test
    void deleteUser_whenUserExist() throws NotFoundException {

        //given
        String name = "user";
        User user = User.builder().name(name).build();
        when(userRepository.findByName(name)).thenReturn(Optional.of(user));

        //when
        userService.deleteUser(name);

        //then
        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository, times(2)).findByName(name);
        inOrder.verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_whenUserNotExist() throws NotFoundException {

        //given
        String name = "user";
        User user = User.builder().name(name).build();
        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            userService.deleteUser(name);
        });


        //then
        verify(userRepository, times(1)).findByName(name);
        verify(userRepository, never()).delete(user);
        assertEquals(USER_NOT_EXIST, exception.getMessage());
    }
}