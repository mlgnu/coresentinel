package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.dto.person.CreatePersonDto;
import com.districtcore.coresentinel.dto.person.CreatePersonDto.CreatePersonResponseDto;
import com.districtcore.coresentinel.dto.person.GetPersonsDto.GetPersonsResponseDto;
import com.districtcore.coresentinel.dto.person.UpdatePersonDto.UpdatePersonRequestDto;
import com.districtcore.coresentinel.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/persons")
@Tag(name = "Persons", description = "Mange people associated with crime cases")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @Operation(summary = "Add a new Person", description = "Add a new person to the system")
    public ResponseEntity<CreatePersonResponseDto> createPerson(@RequestBody @Valid CreatePersonDto.CreatePersonRequestDto createPersonRequestDto) {
       String personId = this.personService.createPerson(createPersonRequestDto);
       return ResponseEntity.created(URI.create("/" + personId)).body(new CreatePersonResponseDto(personId));
    }

    @GetMapping
    @Operation(summary = "Get all persons paginated", description = "Get all persons associated with crime cases")
    public ResponseEntity<Page<GetPersonsResponseDto>> getPersons(@ParameterObject Pageable pageable) {
        Page<GetPersonsResponseDto> response = this.personService.getPersons(pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    @Operation(summary = "Update person by id", description = "Update person details by their ID")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Investigator')")
    public ResponseEntity<Void> updatePerson(@RequestBody @Valid UpdatePersonRequestDto updatePersonRequestDto, @PrefixedId(prefix = "P") Long id){
        this.personService.updatePersonById(updatePersonRequestDto, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete person by id", description = "Delete person by their ID")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Investigator')")
    public ResponseEntity<Void> deletePerson(@PrefixedId(prefix = "P") Long id){
        this.personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
