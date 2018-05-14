package by.com.lifetech.security;

import by.com.lifetech.dto.security.JwtAuthenticationToken;
import by.com.lifetech.exception.InvalidAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    private String key;

    public JwtAuthenticationProvider(String key) {
        this.key = key;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            return getJwtAuthentication(((JwtAuthenticationToken) authentication).getToken());
        } catch (Exception e) {
            throw new InvalidAuthenticationException("Access denied", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Authentication getJwtAuthentication(String token) {
        final Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token) .getBody();
        } catch (final SignatureException e) {
            LOGGER.error(e.getMessage(), e);
            throw new InvalidAuthenticationException("Invalid token.");
        }
        Map<String, String> authorityMap = claims.get("roles", Map.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = authorityMap.get("authority");
        if(!StringUtils.isEmpty(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return new JwtAuthenticationToken(
                authorities,
                claims.get("sub", String.class),
                token);
    }
}