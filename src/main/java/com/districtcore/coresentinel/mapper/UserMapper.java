package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.comment.GetCommentsDto.CommentUserDto;
import com.districtcore.coresentinel.dto.person.GetUserDto.GetUserResponseDto;
import com.districtcore.coresentinel.dto.person.GetUserDto.UserIdNameRoleResponseDto;
import com.districtcore.coresentinel.dto.user.SignupUserDto.SignupUserRequestDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserRequestDto;
import com.districtcore.coresentinel.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User signupUserRequestToEntity(SignupUserRequestDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserRequestToEntity(UpdateUserRequestDto userDto, @MappingTarget User user);

    GetUserResponseDto userToGetUserDto(User user);

    @Mapping(target = "name", source = "fullName")
    UserIdNameRoleResponseDto toDto(User user);

    @Mapping(target = "name", source = "fullName")
    CommentUserDto toCommentUser(User user);
}
