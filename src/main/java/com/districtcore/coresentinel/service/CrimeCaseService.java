package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.crimecase.CreateCaseDto.CreateCaseRequestDto;
import com.districtcore.coresentinel.dto.crimecase.GetAllCasesDto.GetAllCasesResponseDto;
import com.districtcore.coresentinel.dto.crimecase.GetPersonsWithTypeDto.GetPersonsWithTypeResponseDto;
import com.districtcore.coresentinel.enums.CriticalityLevel;
import com.districtcore.coresentinel.enums.PersonType;
import com.districtcore.coresentinel.enums.UserRole;
import com.districtcore.coresentinel.mapper.CrimeCaseMapper;
import com.districtcore.coresentinel.model.CrimeCase;
import com.districtcore.coresentinel.model.Person;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.repository.CrimeCaseRepository;
import com.districtcore.coresentinel.repository.PersonRepository;
import com.districtcore.coresentinel.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CrimeCaseService {
    private final CrimeCaseRepository crimeCaseRepository;
    private final UserRepository userRepository;
    private final CrimeCaseMapper crimeCaseMapper;
    private final EntityManager entityManager;
    private final PersonRepository personRepository;

    public CrimeCaseService(CrimeCaseRepository crimeCaseRepository, UserRepository userRepository,
                            CrimeCaseMapper crimeCaseMapper, EntityManager entityManager, PersonRepository personRepository) {
        this.crimeCaseRepository = crimeCaseRepository;
        this.userRepository = userRepository;
        this.crimeCaseMapper = crimeCaseMapper;
        this.entityManager = entityManager;
        this.personRepository = personRepository;
    }

    public String createCase(CreateCaseRequestDto createCaseRequestDto, Long userId) throws BadRequestException {
        canAssignCase(createCaseRequestDto.getLevel(), createCaseRequestDto.getAssigneesId());

        CrimeCase crimeCase = crimeCaseMapper.toEntity(createCaseRequestDto, userId);
        CrimeCase savedCrimeCase = this.crimeCaseRepository.save(crimeCase);
        return savedCrimeCase.getCaseId();
    }

    public Page<GetAllCasesResponseDto> getAllCases(Pageable pageable) {
        entityManager.clear();
        Page<CrimeCase> page = this.crimeCaseRepository.findAll(pageable);
        return page.map(this.crimeCaseMapper::toGetAllCases);
    }

    public void canAssignCase(CriticalityLevel caseLevel, List<String> assigneesIds) throws BadRequestException {
        List<Long> numericIds = assigneesIds.stream()
                .map(id -> Long.parseLong(id.substring(1)))
                .toList();

        List<User> users = userRepository.findAllById(numericIds);
        List<String> errors = new ArrayList<>();

        Map<Boolean, List<User>> partitionedUsers = users.stream().collect(Collectors.partitioningBy(
                user -> user.getRole().equals(UserRole.OFFICER)));

        String nonOfficersUserIds = partitionedUsers.get(false).stream()
                .map(User::getUserId).collect(Collectors.joining(", "));

        String nonMatchedLevelUserIds = partitionedUsers.get(true).stream()
                .filter(user -> user.getLevel().getCode() < caseLevel.getCode())
                .map(User::getUserId).collect(Collectors.joining(", "));

        if (!nonOfficersUserIds.isEmpty()) {
            errors.add("Cases cannot be assigned to non-officers, the following users aren't officers: " + nonOfficersUserIds);
        }
        if (!nonMatchedLevelUserIds.isEmpty()) {
            errors.add("Cases cannot be assigned to users with lower level than the case, the following users have lower level: " + nonMatchedLevelUserIds);
        }

        if (!errors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join("; ", errors));
        }
    }

    public List<String> getUrlsByCaseId(Long id) {
        return this.crimeCaseRepository.findUrlsByCaseId(id);
    }

    public List<GetPersonsWithTypeResponseDto> getWitnessesWithTypeByCaseId(Long id, PersonType personType) {
        List<Person> persons = personRepository.findByCrimeCasesAndType(crimeCaseRepository.getReferenceById(id), personType);
        return persons.stream()
                .map(crimeCaseMapper::toGetPersonsWithType)
                .collect(Collectors.toList());
    }

    public List<GetPersonsWithTypeResponseDto> getAssignees(Long id) {
        CrimeCase crimeCase = this.crimeCaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crime Case Not Found"));
        return crimeCase.getAssignees().stream().map(crimeCaseMapper::toGetAssignees).toList();
    }
}
