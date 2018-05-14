package by.com.lifetech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private Object body;
    private Integer responseCode;
    private Integer errorCode;
    private String message;
    private String debugMessage;

    public ResponseDTO(Object body) {
        this.body = body;
        this.responseCode = 200;
    }

    public ResponseDTO(String errorMessage, Integer errorCode) {
        this.message = errorMessage;
        this.errorCode = errorCode;
    }
}
