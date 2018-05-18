package by.com.lifetech.service;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.dto.security.UserDto;
import by.com.lifetech.exception.InvalidAuthenticationException;
import by.com.lifetech.exception.WeatherException;
import by.com.lifetech.model.security.User;
import by.com.lifetech.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseCrudServiceImpl<User, Long, UserRepository> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ROLES = "roles";
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

    @Override
    public User findByName(String userName) {
        if(StringUtils.isEmpty(userName)) {
            throw new WeatherException("Such User doesn't exist");
        }
        return this.repository.findByUsername(userName);
    }

    @Override
    public JwtAuthenticationToken refreshToken(String token) throws WeatherException {
        final Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (final SignatureException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WeatherException("Token was expired");
        }
        String userName = claims.get("sub").toString();
        List<String> roles = claims.get(ROLES, List.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, 30);
        String updatedToken = Jwts.builder().setSubject(userName)
                .claim(ROLES, roles).setIssuedAt(new Date())
                .setExpiration(expirationDate.getTime())
                .signWith(SignatureAlgorithm.HS256, key).compact();
        return new JwtAuthenticationToken(
                authorities,
                claims.get("sub", String.class),
                updatedToken);
    }

}
