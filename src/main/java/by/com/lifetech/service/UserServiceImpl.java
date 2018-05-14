package by.com.lifetech.service;

import by.com.lifetech.converter.fromDTO.UserFromDTOConverter;
import by.com.lifetech.converter.toDTO.UserToDTOConverter;
import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.dto.security.UserDtoExtended;
import by.com.lifetech.exception.InvalidAuthenticationException;
import by.com.lifetech.model.security.User;
import by.com.lifetech.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${JWT_KEY}")
    private String key;
    private UserRepository userRepository;
    private UserToDTOConverter userToDTOConverter;
    private UserFromDTOConverter userFromDTOConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserToDTOConverter userToDTOConverter,
                           UserFromDTOConverter userFromDTOConverter) {
        this.userRepository = userRepository;
        this.userToDTOConverter = userToDTOConverter;
        this.userFromDTOConverter = userFromDTOConverter;
    }

    @Override
    public JwtAuthenticationToken getToken(UserDto user) throws InvalidAuthenticationException {
        User userFromDatabase = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userFromDatabase == null) {
            throw new InvalidAuthenticationException(user.getUsername());
        }

        GrantedAuthority role = new SimpleGrantedAuthority(userFromDatabase.getRole());
        List<GrantedAuthority> roles = new ArrayList<>(1);
        roles.add(role);
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, 30);
        String token = Jwts.builder().setSubject(userFromDatabase.getUsername())
                .claim("roles", role).setIssuedAt(new Date())
                .setExpiration(expirationDate.getTime())
                .signWith(SignatureAlgorithm.HS256, key).compact();
        return new JwtAuthenticationToken(roles, userFromDatabase.getUsername(), token);
    }

    @Override
    public List<UserDtoExtended> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(userToDTOConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Long saveOrUpdateUser(UserDtoExtended userDTO) {
        User user = userFromDTOConverter.convert(userDTO);
        User savedUser = this.userRepository.save(user);

        LOGGER.debug("New user was updated. Id of updated user is {}", savedUser.getId());
        return savedUser.getId();
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
        LOGGER.debug("User was deleted. Id of deleted user id {}", id);
    }


}
