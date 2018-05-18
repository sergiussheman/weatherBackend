package by.com.lifetech.service;

import by.com.lifetech.exception.WeatherException;
import by.com.lifetech.model.Location;
import by.com.lifetech.model.security.User;
import by.com.lifetech.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@Slf4j
public class LocationServiceImpl extends BaseCrudServiceImpl<Location, Long, LocationRepository>
        implements LocationService {
    private UserService userService;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, UserService userService) {
        super(locationRepository);
        this.userService = userService;
    }

    @Override
    public Location saveOrUpdate(Location location) {
        String username = getCurrentUser();
        if(StringUtils.isEmpty(username)) {
            throw new WeatherException("You have to be authorized");
        }
        User user = userService.findByName(username);
        location.setUser(user);
        location.setAddTime(new Date());
        return this.insert(location);
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

}
