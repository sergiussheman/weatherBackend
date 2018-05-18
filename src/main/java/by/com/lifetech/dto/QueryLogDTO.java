package by.com.lifetech.dto;

import by.com.lifetech.dto.weather.QueryStatus;
import lombok.Data;

import java.util.Date;

@Data
public class QueryLogDTO {
    private String user;
    private Date queryTime;
    private String location;
    private String ip;
    private String response;
    private Long duration;
    private QueryStatus queryStatus;
}
