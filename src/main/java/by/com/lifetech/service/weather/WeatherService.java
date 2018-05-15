package by.com.lifetech.service.weather;

import by.com.lifetech.dto.weather.ConditionDTO;
import retrofit2.Call;

public interface WeatherService {
    Call<ConditionDTO> getWeatherConditions(String countryCode, String cityCode);
}
