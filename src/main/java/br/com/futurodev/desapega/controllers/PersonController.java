package br.com.futurodev.desapega.controllers;

import br.com.futurodev.desapega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desapega.models.transport.CreatePersonForm;
import br.com.futurodev.desapega.models.transport.PersonDto;
import br.com.futurodev.desapega.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/persons")
public class  PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid CreatePersonForm createPersonForm,
                                                  UriComponentsBuilder uriComponentsBuilder) throws UserAlreadyRegisteredException {
        PersonDto response = this.personService.createPerson(createPersonForm);

        URI uri = uriComponentsBuilder
                .path("/persons/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}

