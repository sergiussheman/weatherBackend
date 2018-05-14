package by.com.lifetech.service;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.exception.InvalidAuthenticationException;

public interface UserService {
    JwtAuthenticationToken getToken(UserDto user) throws InvalidAuthenticationException;
}
