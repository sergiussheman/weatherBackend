package by.com.lifetech.controller;

import by.com.lifetech.converter.fromDTO.LocationFromDTOConverter;
import by.com.lifetech.converter.toDTO.LocationToDTOConverter;
import by.com.lifetech.dto.LocationDTOExtended;
import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
    private LocationService locationService;
    private ObjectMapper objectMapper;
    private LocationToDTOConverter locationToDTOConverter;
    private LocationFromDTOConverter locationFromDTOConverter;

    @Autowired
    public LocationController(LocationService locationService, ObjectMapper objectMapper, 
                              LocationFromDTOConverter locationFromDTOConverter, 
                              LocationToDTOConverter locationToDTOConverter) {
        this.locationService = locationService;
        this.objectMapper = objectMapper;
        this.locationFromDTOConverter = locationFromDTOConverter;
        this.locationToDTOConverter = locationToDTOConverter;
    }

    @GetMapping
    public ResponseDTO getAllLocations() {
        LOGGER.debug("LocationController.getAllLocations() method was called");

        List<LocationDTOExtended> result = this.locationService.findAll().stream()
                .map(locationToDTOConverter::convert)
                .collect(Collectors.toList());
        return new ResponseDTO(result);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO saveOrUpdateLocation(@RequestBody LocationDTOExtended locationDTO) throws JsonProcessingException {
        LOGGER.debug("LocationController.saveOrUpdateLocation() method was called. LocationDTO = {}",
                objectMapper.writeValueAsString(locationDTO));

        Long updatedId = this.locationService.saveOrUpdate(locationFromDTOConverter.convert(locationDTO)).getId();
        return new ResponseDTO(updatedId);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseDTO deleteLocation(@RequestParam Long locationId) {
        LOGGER.debug("LocationController.deleteLocation() method was called. Id = {}", locationId);

        this.locationService.delete(locationId);
        return new ResponseDTO("OK");
    }
}
