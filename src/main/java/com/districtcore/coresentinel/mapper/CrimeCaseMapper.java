package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.crimecase.CreateCaseDto.CreateCaseRequestDto;
import com.districtcore.coresentinel.dto.crimecase.GetAllCasesDto.GetAllCasesResponseDto;
import com.districtcore.coresentinel.dto.crimecase.GetPersonsWithTypeDto.GetPersonsWithTypeResponseDto;
import com.districtcore.coresentinel.model.CrimeCase;
import com.districtcore.coresentinel.model.CrimeReport;
import com.districtcore.coresentinel.model.Person;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.repository.CrimeReportRepository;
import com.districtcore.coresentinel.repository.PersonRepository;
import com.districtcore.coresentinel.repository.UserRepository;
import com.districtcore.coresentinel.util.IdUtils;
import com.districtcore.coresentinel.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { UserMapper.class, CrimeReportMapper.class })
public abstract class CrimeCaseMapper {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CrimeReportRepository crimeReportRepository;
    @Autowired
    protected PersonRepository personRepository;

    @Mapping(target = "assignees", source = "createCaseRequestDto.assigneesId", qualifiedByName = "mapAssignees")
    @Mapping(target = "reportedBy", source = "createCaseRequestDto.reportsId", qualifiedByName = "mapReporters")
    @Mapping(target = "createdBy", expression = "java(this.userRepository.getReferenceById(userId))")
    @Mapping(target = "persons", source = "createCaseRequestDto.personsId", qualifiedByName = "mapPersons")
    public abstract CrimeCase toEntity(CreateCaseRequestDto createCaseRequestDto, Long userId);

    @Mapping(target = "caseNumber", source = "caseId")
    @Mapping(target = "description", qualifiedByName = "mapDescription")
    @Mapping(target = "numberOfAssignees", expression = "java(crimeCase.getAssignees() != null ? crimeCase.getAssignees().size() : 0)")
    @Mapping(target = "numberOfSuspects",
            expression = "java(crimeCase.getPersons() != null ? crimeCase.getPersons().stream().filter(p -> p.getType() == com.districtcore.coresentinel.enums.PersonType.SUSPECT).count() : 0)")
    @Mapping(target = "numberOfVictims",
            expression = "java(crimeCase.getPersons() != null ? crimeCase.getPersons().stream().filter(p -> p.getType() == com.districtcore.coresentinel.enums.PersonType.VICTIM).count() : 0)")
    @Mapping(target = "numberOfWitnesses",
            expression = "java(crimeCase.getPersons() != null ? crimeCase.getPersons().stream().filter(p -> p.getType() == com.districtcore.coresentinel.enums.PersonType.WITNESS).count() : 0)")
    public abstract GetAllCasesResponseDto toGetAllCases(CrimeCase crimeCase);

    public abstract GetPersonsWithTypeResponseDto toGetPersonsWithType(Person person);

    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "personId", source = "userId")
    public abstract GetPersonsWithTypeResponseDto toGetAssignees(User user);

    @Named("mapAssignees")
    protected Set<User> mapAssignees(List<String> assignees) {
        if (assignees == null) {
            return null;
        }
        return IdUtils.unprefixIds(assignees).stream().map(this.userRepository::getReferenceById).collect(Collectors.toSet());
    }

    @Named("mapReporters")
    protected Set<CrimeReport> mapReporters(List<String> reportIds) {
        if (reportIds == null) {
            return null;
        }
        return IdUtils.unprefixIds(reportIds).stream().map(this.crimeReportRepository::getReferenceById).collect(Collectors.toSet());
    }

    @Named("mapPersons")
    protected Set<Person> mapPersons(List<String> personIds) {
        if (personIds == null) {
            return null;
        }
        return IdUtils.unprefixIds(personIds).stream().map(this.personRepository::getReferenceById).collect(Collectors.toSet());
    }

    @Named("mapDescription")
    protected String mapDescription(String description) {
        return StringUtils.truncateStringPreserveWord(description, 100);
    }

}
