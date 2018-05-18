package by.com.lifetech.converter.toDTO;

import by.com.lifetech.dto.QueryLogDTO;
import by.com.lifetech.model.QueryLog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QueryLogToDTOConverter implements Converter<QueryLog, QueryLogDTO> {

    @Override
    public QueryLogDTO convert(QueryLog queryLog) {
        QueryLogDTO result = new QueryLogDTO();
        result.setUser(queryLog.getUser().getUsername());
        result.setQueryTime(queryLog.getQueryTime());
        result.setLocation(getLocationLabel(queryLog));
        result.setIp(queryLog.getIp());
        result.setResponse(queryLog.getResponse());
        result.setDuration(queryLog.getDuration());
        result.setQueryStatus(queryLog.getQueryStatus());
        return result;
    }

    private String getLocationLabel(QueryLog queryLog) {
        if(queryLog != null && queryLog.getLocation() != null) {
            return queryLog.getLocation().getCity() + "(" + queryLog.getLocation().getCountry() + ")";
        }
        return "";
    }
}
