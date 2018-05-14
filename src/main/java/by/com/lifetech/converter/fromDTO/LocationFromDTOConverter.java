package by.com.lifetech.converter.fromDTO;

import by.com.lifetech.dto.LocationDTOExtended;
import by.com.lifetech.model.Location;
import by.com.lifetech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationFromDTOConverter implements Converter<LocationDTOExtended, Location> {
    private UserService userService;

    @Autowired
    public LocationFromDTOConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Location convert(LocationDTOExtended locationDTO) {
        Location result = new Location();
        result.setId(locationDTO.getId());
        result.setValue(locationDTO.getValue());
        result.setDescription(locationDTO.getDescription());
        result.setAddTime(locationDTO.getAddTime());
        if(locationDTO.getUserId() != null) {
            result.setUser(userService.findById(locationDTO.getUserId()));
        }
        return result;
    }
}
