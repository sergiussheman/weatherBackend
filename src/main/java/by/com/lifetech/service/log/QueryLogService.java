package by.com.lifetech.service.log;

import by.com.lifetech.dto.weather.QueryStatus;
import by.com.lifetech.model.Location;
import by.com.lifetech.model.QueryLog;
import by.com.lifetech.repository.log.QueryLogRepository;
import by.com.lifetech.service.BaseCrudService;

import java.util.List;

public interface QueryLogService extends BaseCrudService<QueryLog, Long, QueryLogRepository> {
    List<QueryLog> getQueryLogsByUser(Long userId);

    void logWeatherQuery(String userName, Location location, Object response, Long duration, QueryStatus queryStatus);
}
