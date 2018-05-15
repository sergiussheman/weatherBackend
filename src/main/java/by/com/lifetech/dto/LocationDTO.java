package by.com.lifetech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private String country;
    private String city;
    private String description;
    private Long userId;
    private Date addTime;
}
