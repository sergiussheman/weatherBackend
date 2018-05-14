package by.com.lifetech.dto.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collections;
import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String name;
    private String token;
    private List<GrantedAuthority> authorities;

    public JwtAuthenticationToken() {
        super(Collections.emptyList());
    }

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    public JwtAuthenticationToken(String name, String token){
        super(AuthorityUtils.NO_AUTHORITIES);
        this.name = name;
        this.token = token;
    }

    public JwtAuthenticationToken(List<GrantedAuthority> authorities, String name, String token) {
        super(authorities);
        this.authorities = authorities;
        this.name = name;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    public void setPrincipal(String principal) {
        this.name = principal;
    }

    public void setCredentials(String credentials) {
        //ignore credentials
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        JwtAuthenticationToken that = (JwtAuthenticationToken) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (token != null ? !token.equals(that.token) : that.token != null) {
            return false;
        }
        return authorities != null ? authorities.equals(that.authorities) : that.authorities == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }
}
