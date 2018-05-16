package by.com.lifetech.controller;

import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.dto.weather.ConditionDTO;
import by.com.lifetech.service.weather.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);
    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseDTO getWeather(@RequestParam("locationId") Long locationId) throws Exception {
        LOGGER.debug("WeatherController.getWeather() method was called. locationId = {}", locationId);

        ConditionDTO result = weatherService.getWeatherConditions(locationId);
        return new ResponseDTO(result);
    }
}
