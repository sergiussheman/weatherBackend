package by.com.lifetech.client;


import by.com.lifetech.dto.weather.ConditionDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherClient {

    @GET(value = "/api/{apiKey}/conditions/q/{country}/{city}.json")
    Call<ConditionDTO> getWeatherConditions(@Path("country") String countryCode, @Path("city") String cityCode,
                                            @Path("apiKey") String apiKey);
}
