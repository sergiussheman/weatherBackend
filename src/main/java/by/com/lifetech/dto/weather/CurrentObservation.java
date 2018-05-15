package by.com.lifetech.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentObservation {
    @JsonProperty("display_location")
    private DisplayLocation displayLocation;
    private String weather;
    @JsonProperty("temperature_string")
    private String temperature;
    @JsonProperty("feelslike_string")
    private String feelsLike;


    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class DisplayLocation {
        private String full;
        private String city;
        private String country;
    }
}

