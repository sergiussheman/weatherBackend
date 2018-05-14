package by.com.lifetech.converter.toDTO;

import by.com.lifetech.dto.LocationDTOExtended;
import by.com.lifetech.model.Location;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationToDTOConverter implements Converter<Location, LocationDTOExtended> {
    @Override
    public LocationDTOExtended convert(Location location) {
        LocationDTOExtended result = new LocationDTOExtended();
        result.setId(location.getId());
        result.setValue(location.getValue());
        result.setDescription(location.getDescription());
        if(location.getUser() != null) {
            result.setUserId(location.getUser().getId());
        }
        result.setAddTime(location.getAddTime());
        return result;
    }
}
