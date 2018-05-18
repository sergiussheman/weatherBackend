package by.com.lifetech.controller;

import by.com.lifetech.converter.toDTO.QueryLogToDTOConverter;
import by.com.lifetech.dto.QueryLogDTO;
import by.com.lifetech.dto.ResponseDTO;
import by.com.lifetech.service.log.QueryLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/queryLog")
public class QueryLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryLogController.class);
    private QueryLogService queryLogService;
    private QueryLogToDTOConverter queryLogConverter;

    @Autowired
    public QueryLogController(QueryLogService queryLogService, QueryLogToDTOConverter queryLogConverter) {
        this.queryLogService = queryLogService;
        this.queryLogConverter = queryLogConverter;
    }

    @GetMapping
    public ResponseDTO getQueryLogForUser(@RequestParam("userId") Long userId) {
        LOGGER.debug("QueryLogController.getQueryLogForUser() method was called");

        List<QueryLogDTO> result = this.queryLogService.getQueryLogsByUser(userId).stream()
                .map(queryLogConverter::convert)
                .collect(Collectors.toList());
        return new ResponseDTO(result);
    }
}
