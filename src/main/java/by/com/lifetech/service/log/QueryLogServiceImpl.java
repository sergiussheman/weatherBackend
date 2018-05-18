package by.com.lifetech.service.log;

import by.com.lifetech.dto.weather.QueryStatus;
import by.com.lifetech.exception.WeatherException;
import by.com.lifetech.model.Location;
import by.com.lifetech.model.QueryLog;
import by.com.lifetech.model.security.User;
import by.com.lifetech.repository.log.QueryLogRepository;
import by.com.lifetech.service.BaseCrudServiceImpl;
import by.com.lifetech.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class QueryLogServiceImpl extends BaseCrudServiceImpl<QueryLog, Long, QueryLogRepository>
        implements QueryLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryLogServiceImpl.class);
    private UserService userService;
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;


    @Autowired
    public QueryLogServiceImpl(QueryLogRepository queryLogRepository, UserService userService, ObjectMapper objectMapper) {
        super(queryLogRepository);
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<QueryLog> getQueryLogsByUser(Long userId) {
        User user = userService.findById(userId);
        if(user == null) {
            throw new WeatherException("Such user doesn't exist");
        }

        List<QueryLog> result = repository.findByUser(user);
        result.sort(Comparator.comparing(QueryLog::getQueryTime).reversed());
        return result;
    }


    //TODO: refactor this method to use Spring AOP
    @Override
    public void logWeatherQuery(String userName, Location location, Object response, Long duration, QueryStatus queryStatus){
        QueryLog queryLog = new QueryLog();
        queryLog.setUser(userService.findByName(userName));
        queryLog.setQueryTime(new Date());
        queryLog.setLocation(location);
        queryLog.setIp(getClientIp());
        queryLog.setDuration(duration);
        queryLog.setQueryStatus(queryStatus);

        try {
            queryLog.setResponse(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception while serializing query response. ", e);
        }

        this.repository.save(queryLog);
    }

    private String getClientIp() {
        String remoteAddr = "";

        if (httpServletRequest != null) {
            remoteAddr = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpServletRequest.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest request) {
        this.httpServletRequest = request;
    }
}
