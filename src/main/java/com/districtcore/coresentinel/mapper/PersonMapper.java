package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.person.CreatePersonDto.CreatePersonRequestDto;
import com.districtcore.coresentinel.dto.person.GetPersonsDto.GetPersonsResponseDto;
import com.districtcore.coresentinel.dto.person.UpdatePersonDto.UpdatePersonRequestDto;
import com.districtcore.coresentinel.model.Person;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(CreatePersonRequestDto createPersonRequestDto);
    GetPersonsResponseDto toGetPersonsResponse(Person person);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(UpdatePersonRequestDto updatePersonRequestDto, @MappingTarget Person person);
}
