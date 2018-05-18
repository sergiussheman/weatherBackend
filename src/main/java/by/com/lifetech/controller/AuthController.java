package by.com.lifetech.controller;

import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.exception.WeatherException;
import by.com.lifetech.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/login")
    public ResponseDTO login(@RequestBody UserDto userDto) throws JsonProcessingException {
        LOGGER.debug("AuthController.login() method was called. userDTO: {}", objectMapper.writeValueAsString(userDto));

        JwtAuthenticationToken token = userService.getToken(userDto);

        return new ResponseDTO(token);
    }

    @PutMapping(value = "/refreshToken")
    public ResponseEntity refreshToken(@RequestParam String token) throws WeatherException {
        LOGGER.debug("AuthController.refreshToken() method was called. Old token: {}", token);

        JwtAuthenticationToken updatedToken = userService.refreshToken(token);

        return new ResponseEntity<>(updatedToken, HttpStatus.OK);
    }
}
