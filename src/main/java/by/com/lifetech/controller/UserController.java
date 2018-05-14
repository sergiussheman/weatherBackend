package by.com.lifetech.controller;

import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/check")
    public ResponseDTO check() {
        return new ResponseDTO("Hello from backend");
    }

    @PostMapping(value = "/login")
    public ResponseDTO login(@RequestBody UserDto userDto) {
        JwtAuthenticationToken token = userService.getToken(userDto);

        return new ResponseDTO(token);
    }
}
