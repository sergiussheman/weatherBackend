package by.com.lifetech.client;

import by.com.lifetech.dto.weather.ConditionDTO;
import by.com.lifetech.model.security.User;
import by.com.lifetech.service.UserService;
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
    @Autowired
    private UserService userService;

    @Test
    public void getWeather_OK() throws Exception {

        ConditionDTO result = this.weatherService.getWeatherConditions(1L);
        Assert.notNull(result);
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("Bob");
        user.setRole("User");
        user.setPassword("BobPassword");
        user.setEmail("bob@gmail.com");
        user.setRoot(true);
        user = this.userService.insert(user);

        Assert.notNull(user.getId());
    }
}
