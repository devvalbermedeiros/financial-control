package com.valber.financial_control.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExceptionService {

    public ErrorResponse buildErrorResponse(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status,
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
