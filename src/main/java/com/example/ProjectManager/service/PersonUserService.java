package com.example.ProjectManager.service;

import com.example.ProjectManager.client.PersonFeignClient;
import com.example.ProjectManager.mapper.PersonAddRequestUserMapper;
import com.example.ProjectManager.model.User;
import com.example.demo.model.response.PersonDTO;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PersonUserService {

    public static final String PERSON_NOT_CREATED = "Person not created";
    PersonFeignClient personFeignClient;
    PersonAddRequestUserMapper personAddRequestUserMapper;

    public void addPerson(User user) {
        ResponseEntity<PersonDTO> addPersonResponse
                = personFeignClient.addPerson(personAddRequestUserMapper.userToPersonAddRequest(user));
        if (!addPersonResponse.getStatusCode().is2xxSuccessful()){
            log.info(PERSON_NOT_CREATED);
            throw new BadRequestException(PERSON_NOT_CREATED);
        }
    }

    public List<PersonDTO> findAll() {
        ResponseEntity<List<PersonDTO>> findAllResponse =
                personFeignClient.findAll();
        if (!findAllResponse.getStatusCode().is2xxSuccessful()){
            log.info(PERSON_NOT_CREATED);
            throw new BadRequestException(PERSON_NOT_CREATED);
        } else {
            return findAllResponse.getBody();
        }
    }

}
