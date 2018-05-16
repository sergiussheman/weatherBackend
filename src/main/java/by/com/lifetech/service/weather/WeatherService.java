package by.com.lifetech.service.weather;

import by.com.lifetech.dto.weather.ConditionDTO;

public interface WeatherService {
    ConditionDTO getWeatherConditions(Long locationId) throws Exception;
}
