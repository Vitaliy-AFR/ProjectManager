package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.User;
import com.example.demo.model.request.PersonAddRequest;
import org.springframework.stereotype.Component;

@Component
public class PersonRequestUserMapper {

    public PersonAddRequest userToPersonAddRequest(User user) {
        if (user == null) {
            return null;
        }

        String name = null;

        name = user.getName();

        if (!name.matches("^[A-Za-z0-9]{4,10}")) {
            return null;
        }

        PersonAddRequest personAddRequest = new PersonAddRequest(name);
        return personAddRequest;
    }

}
