package by.com.lifetech.client;

import by.com.lifetech.dto.weather.ConditionDTO;
import by.com.lifetech.service.weather.WeatherService;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WeatherClientTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void getWeather_OK() throws Exception {

        ConditionDTO result = this.weatherService.getWeatherConditions(1L);
        Assert.notNull(result);
    }


}
