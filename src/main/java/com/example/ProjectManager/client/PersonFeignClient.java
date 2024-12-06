package com.example.ProjectManager.client;

import com.example.demo.model.request.PersonFindRequest;
import com.example.demo.model.response.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "person-service", url = "http://localhost:8081")
public interface PersonFeignClient {

    @GetMapping("/srv/v1/person/{id}")
    ResponseEntity<PersonDTO> getPerson(@PathVariable String id);

    @PostMapping("/srv/v1/person/find")
    ResponseEntity<List<PersonDTO>> findPersons(@RequestBody PersonFindRequest request);

}
