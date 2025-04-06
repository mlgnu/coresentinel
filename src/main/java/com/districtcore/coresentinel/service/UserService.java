package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.person.GetUserDto.GetUserResponseDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserLevelRequestDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserRequestDto;
import com.districtcore.coresentinel.dto.user.UpdateUserDto.UpdateUserRolesRequestDto;
import com.districtcore.coresentinel.mapper.UserMapper;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void deleteById(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    public void updateById(UpdateUserRequestDto  userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUserRequestToEntity(userDto, user);
        userRepository.save(user);
    }

    public void updateRolesById(UpdateUserRolesRequestDto userRoles, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(userRoles.getRole());
        userRepository.save(user);
    }

    public GetUserResponseDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.userToGetUserDto(user);
    }

    public void updateLevelById(UpdateUserLevelRequestDto userLevel, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLevel(userLevel.getCriticalityLevel());
        userRepository.save(user);
    }
}
