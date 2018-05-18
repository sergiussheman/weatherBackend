package by.com.lifetech.controller;

import by.com.lifetech.converter.fromDTO.UserFromDTOConverter;
import by.com.lifetech.converter.toDTO.UserToDTOConverter;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private ObjectMapper objectMapper;
    private UserToDTOConverter userToDTOConverter;
    private UserFromDTOConverter userFromDTOConverter;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper, UserFromDTOConverter userFromDTOConverter,
                          UserToDTOConverter userToDTOConverter) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.userToDTOConverter = userToDTOConverter;
        this.userFromDTOConverter = userFromDTOConverter;
    }

    @GetMapping
    public ResponseDTO getAllUsers() {
        LOGGER.debug("UserController.getAllUsers() method was called");

        List<UserDtoExtended> result = this.userService.findAll().stream()
                .map(userToDTOConverter::convert)
                .collect(Collectors.toList());
        return new ResponseDTO(result);
    }

    @PostMapping
    public ResponseDTO saveOrUpdateUser(@RequestBody UserDtoExtended userDto) throws JsonProcessingException {
        LOGGER.debug("UserController.saveOrUpdateUser() method was called. userDTO = {}",
                objectMapper.writeValueAsString(userDto));

        Long updatedId = this.userService.saveOrUpdate(userFromDTOConverter.convert(userDto)).getId();
        return new ResponseDTO(updatedId);
    }

    @DeleteMapping
    public ResponseDTO deleteUser(@RequestParam Long userId) {
        LOGGER.debug("UserController.deleteUser() method was called. Id = {}", userId);

        this.userService.delete(userId);
        return new ResponseDTO("OK");
    }

}
