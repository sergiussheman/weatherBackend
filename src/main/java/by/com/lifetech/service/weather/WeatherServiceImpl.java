package by.com.lifetech.service.weather;

import by.com.lifetech.client.WeatherClient;
import by.com.lifetech.dto.weather.ConditionDTO;
import by.com.lifetech.model.Location;
import by.com.lifetech.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
    private LocationService locationService;

    @Autowired
    public WeatherServiceImpl(LocationService locationService) {
        this.locationService = locationService;
    }

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
    public ConditionDTO getWeatherConditions(Long locationId) throws Exception {
        if(locationId == null) {
            throw new Exception("You have to pass the correct locationIf");
        }

        Location location = this.locationService.findById(locationId);
        if(location == null) {
            throw new Exception("Such location doesn't exist");
        }

        Call<ConditionDTO> conditionCall = this.weatherClient.getWeatherConditions(location.getCountry(),
                location.getCity(), apiKey);

        //get weather condition in synchronous way
        ConditionDTO body;
        try {
            Response<ConditionDTO> conditions = conditionCall.execute();
            body = conditions.body();
        } catch (IOException e) {
            LOGGER.error("Error while getting weather condition.", e);
            this.logResponse();
            throw new Exception("Weather service unavailable. Try again later...");
        }

        this.logResponse();
        return body;
    }

    private void logResponse() {
        //TODO implement this method
    }

}
