package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.person.CreatePersonDto.CreatePersonRequestDto;
import com.districtcore.coresentinel.dto.person.GetPersonsDto.GetPersonsResponseDto;
import com.districtcore.coresentinel.dto.person.UpdatePersonDto.UpdatePersonRequestDto;
import com.districtcore.coresentinel.mapper.PersonMapper;
import com.districtcore.coresentinel.model.Person;
import com.districtcore.coresentinel.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public String createPerson(CreatePersonRequestDto createPersonRequestDto) {
        Person person = this.personMapper.toEntity(createPersonRequestDto);
        Person savedPerson = this.personRepository.save(person);
        return savedPerson.getPersonId();
    }

    public void deletePerson(Long id) {
        this.personRepository.deleteById(id);
    }

    public Page<GetPersonsResponseDto> getPersons(Pageable pageable) {
        Page<Person> page = this.personRepository.findAll(pageable);
        return page.map(personMapper::toGetPersonsResponse);
    }

    public void updatePersonById(UpdatePersonRequestDto updatePersonRequestDto, Long id) {
        Person person = this.personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        this.personMapper.toUpdate(updatePersonRequestDto, person);
        this.personRepository.save(person);
    }
}
