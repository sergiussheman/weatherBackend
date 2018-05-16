package by.com.lifetech.controller;

import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.model.QueryLog;
import by.com.lifetech.service.log.QueryLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queryLog")
public class QueryLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryLogController.class);
    private QueryLogService queryLogService;

    @Autowired
    public QueryLogController(QueryLogService queryLogService) {
        this.queryLogService = queryLogService;
    }

    @GetMapping
    public ResponseDTO getQueryLogForUser(@RequestParam("userId") Long userId) {
        LOGGER.debug("QueryLogController.getQueryLogForUser() method was called");

        List<QueryLog> result = this.queryLogService.getQueryLogsByUser(userId);
        return new ResponseDTO(result);
    }
}
