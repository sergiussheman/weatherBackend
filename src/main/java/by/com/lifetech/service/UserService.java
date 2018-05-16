package by.com.lifetech.service;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.exception.InvalidAuthenticationException;
import by.com.lifetech.model.security.User;
import by.com.lifetech.repository.UserRepository;

public interface UserService extends BaseCrudService<User, Long, UserRepository> {
    JwtAuthenticationToken getToken(UserDto user) throws InvalidAuthenticationException;

    User findByName(String userName);
}
