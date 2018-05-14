package by.com.lifetech.service;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.dto.security.UserDtoExtended;
import by.com.lifetech.exception.InvalidAuthenticationException;

import java.util.List;

public interface UserService {
    JwtAuthenticationToken getToken(UserDto user) throws InvalidAuthenticationException;

    List<UserDtoExtended> getAllUsers();

    Long saveOrUpdateUser(UserDtoExtended userDTO);

    void deleteUser(Long id);
}
