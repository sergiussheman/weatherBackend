package by.com.lifetech.service;

import by.com.lifetech.model.Location;
import by.com.lifetech.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends BaseCrudServiceImpl<Location, Long, LocationRepository>
        implements LocationService {

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        super(locationRepository);
    }

}
