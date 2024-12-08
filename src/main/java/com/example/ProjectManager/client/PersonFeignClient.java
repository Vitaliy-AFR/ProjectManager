package com.example.ProjectManager.client;

import com.example.demo.model.request.PersonAddRequest;
import com.example.demo.model.request.PersonFindRequest;
import com.example.demo.model.response.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "person-service")//будет работать без урла благодаря эврике
public interface PersonFeignClient {

    @GetMapping("/srv/v1/person/{id}")
    ResponseEntity<PersonDTO> getPerson(@PathVariable String id);

    @PostMapping("/srv/v1/person/find")
    ResponseEntity<List<PersonDTO>> findPersons(@RequestBody PersonFindRequest request);

    @PostMapping("/srv/v1/person/save")
    ResponseEntity<PersonDTO> addPerson(@RequestBody PersonAddRequest request);

    @DeleteMapping("/srv/v1/person/delete/{id}")
    ResponseEntity<String> deletePerson(@PathVariable String id);

    @GetMapping("/srv/v1/person/findAll")
    ResponseEntity<List<PersonDTO>> findAll();

}
