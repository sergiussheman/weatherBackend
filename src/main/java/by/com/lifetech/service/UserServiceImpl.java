package by.com.lifetech.service;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
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

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Long, UserRepository> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${JWT_KEY}")
    private String key;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public JwtAuthenticationToken getToken(UserDto user) throws InvalidAuthenticationException {
        LOGGER.debug("Creating token for user = {}", user.getUsername());
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

}
