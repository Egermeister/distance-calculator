package com.bulis.project.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String error;
    private String message;
    private Integer status;
    private String timestamp;
}
