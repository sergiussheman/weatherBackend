package by.com.lifetech.converter.fromDTO;

import by.com.lifetech.dto.security.UserDtoExtended;
import by.com.lifetech.model.security.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserFromDTOConverter implements Converter<UserDtoExtended, User> {
    @Override
    public User convert(UserDtoExtended userDto) {
        User result = new User();
        if(userDto.getId() != null) {
            result.setId(userDto.getId());
        }
        result.setUsername(userDto.getUsername());
        result.setPassword(userDto.getPassword());
        result.setRole(userDto.getRole());
        result.setRoot(userDto.isRoot());
        result.setEmail(userDto.getEmail());
        return result;
    }
}
