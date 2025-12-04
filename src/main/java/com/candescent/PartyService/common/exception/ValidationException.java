package com.candescent.PartyService.common.exception;

import com.candescent.PartyService.common.constants.AppConstants;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Exception thrown when validation fails.
 */
public class ValidationException extends BaseException {

    private final List<String> errors;

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, AppConstants.ErrorCode.VALIDATION_ERROR);
        this.errors = List.of(message);
    }

    public ValidationException(String message, List<String> errors) {
        super(message, HttpStatus.BAD_REQUEST, AppConstants.ErrorCode.VALIDATION_ERROR);
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, AppConstants.ErrorCode.VALIDATION_ERROR, cause);
        this.errors = List.of(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
