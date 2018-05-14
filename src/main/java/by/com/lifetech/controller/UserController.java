package by.com.lifetech.controller;

import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.dto.security.UserDtoExtended;
import by.com.lifetech.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseDTO getAllUsers() {
        LOGGER.debug("UserController.getAllUsers() method was called");

        List<UserDtoExtended> result = this.userService.getAllUsers();
        return new ResponseDTO(result);
    }

    @PostMapping
    public ResponseDTO saveOrUpdateUser(@RequestBody UserDtoExtended userDto) throws JsonProcessingException {
        LOGGER.debug("UserController.saveOrUpdateUser() method was called. userDTO = {}",
                objectMapper.writeValueAsString(userDto));

        Long updatedId = this.userService.saveOrUpdateUser(userDto);
        return new ResponseDTO(updatedId);
    }

    @DeleteMapping
    public ResponseDTO deleteUser(@RequestParam Long userId) {
        LOGGER.debug("UserController.deleteUser() method was called. Id = {}", userId);

        this.userService.deleteUser(userId);
        return new ResponseDTO("OK");
    }

}
