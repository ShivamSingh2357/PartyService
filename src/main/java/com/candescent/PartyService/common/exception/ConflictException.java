package com.candescent.PartyService.common.exception;

import com.candescent.PartyService.common.constants.AppConstants;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when there is a conflict with the current state.
 */
public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT, AppConstants.ErrorCode.CONFLICT);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, HttpStatus.CONFLICT, AppConstants.ErrorCode.CONFLICT, cause);
    }
}
