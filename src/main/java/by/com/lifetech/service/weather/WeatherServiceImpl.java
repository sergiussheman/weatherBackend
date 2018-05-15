package by.com.lifetech.service.weather;

import by.com.lifetech.client.WeatherClient;
import by.com.lifetech.dto.weather.ConditionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;

@Service
public class WeatherServiceImpl implements WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);
    @Value("${JWT_KEY}")
    private String key;
    @Value("${WEATHER_BASE_URL}")
    private String baseUrl;
    @Value("${API_KEY}")
    private String apiKey;
    private WeatherClient weatherClient;

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.weatherClient = retrofit.create(WeatherClient.class);
        LOGGER.debug("WeatherClient was initialized");
    }

    @Override
    public Call<ConditionDTO> getWeatherConditions(String countryCode, String cityCode) {
        return this.weatherClient.getWeatherConditions(countryCode, cityCode, apiKey);
    }

}
