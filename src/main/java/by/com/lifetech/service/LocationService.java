package by.com.lifetech.service;

import by.com.lifetech.model.Location;
import by.com.lifetech.repository.LocationRepository;

import java.util.Collection;

public interface LocationService extends BaseCrudService<Location, Long, LocationRepository> {
    Collection<Location> getAllLocations();
}
