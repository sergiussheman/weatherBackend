package by.com.lifetech.converter.toDTO;

import by.com.lifetech.dto.security.UserDtoExtended;
import by.com.lifetech.model.security.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDTOConverter implements Converter<User, UserDtoExtended> {
    @Override
    public UserDtoExtended convert(User user) {
        UserDtoExtended result = new UserDtoExtended();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setRoot(user.isRoot());
        result.setRole(user.getRole());
        result.setEmail(user.getEmail());
        return result;
    }
}
