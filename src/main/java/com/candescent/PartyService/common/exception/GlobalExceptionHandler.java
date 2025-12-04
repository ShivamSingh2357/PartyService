package com.candescent.PartyService.common.exception;

import com.candescent.PartyService.common.constants.AppConstants;
import com.candescent.PartyService.dto.common.ServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<Void> handleServiceException(ServiceException ex, HttpServletRequest request) {
        log.error("Service error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ServiceResponse.fail(AppConstants.ErrorMessage.SERVICE_OPERATION_FAILED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ServiceResponse<Void> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Resource not found at {}: {}", request.getRequestURI(), ex.getMessage());
        return ServiceResponse.fail(AppConstants.ErrorMessage.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Void> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        log.error("Bad request at {}: {}", request.getRequestURI(), ex.getMessage());
        return ServiceResponse.fail(AppConstants.ErrorMessage.INVALID_REQUEST_PARAMS);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Void> handleValidationException(ValidationException ex, HttpServletRequest request) {
        log.error("Validation error at {}: {}", request.getRequestURI(), ex.getMessage());
        return ServiceResponse.fail(AppConstants.ErrorMessage.VALIDATION_FAILED + ex.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Void> handleBaseException(BaseException ex, HttpServletRequest request) {
        log.error("{} at {}: {}", ex.getClass().getSimpleName(), request.getRequestURI(), ex.getMessage());
        return ServiceResponse.fail(AppConstants.ErrorMessage.REQUEST_PROCESSING_FAILED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation failed at {}", request.getRequestURI());
        String field = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField())
                .findFirst()
                .orElse("unknown");
        return ServiceResponse.fail(AppConstants.ErrorMessage.INVALID_FIELD_VALUE + field);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<Void> handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ServiceResponse.fail(AppConstants.ErrorMessage.UNEXPECTED_ERROR);
    }
}
