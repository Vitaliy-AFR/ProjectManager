package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserServiceImpl.class
})
class UserServiceImplTest {

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
    void findByName() {

        //given
        String name = "user";

        //when
        userService.findByName(name);

        //then
        verify(userRepository).findByName(name);

    }

    @Test
    void deleteUser_whenUserExist() {

        //given
        String name = "user";
        User user = User.builder().name(name).build();
        when(userRepository.findByName(name)).thenReturn(Optional.of(user));

        //when
        userService.deleteUser(name);

        //then
        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository, times(1)).findByName(name);
        inOrder.verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_whenUserNotExist() {

        //given
        String name = "user";
        User user = User.builder().name(name).build();
        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        //when
        userService.deleteUser(name);

        //then
        verify(userRepository, times(1)).findByName(name);
        verify(userRepository, never()).delete(user);
    }
}