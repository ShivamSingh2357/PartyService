package com.candescent.PartyService.common.exception;

import com.candescent.PartyService.common.constants.AppConstants;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when access is forbidden.
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN, AppConstants.ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(String message, String errorCode) {
        super(message, HttpStatus.FORBIDDEN, errorCode);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, HttpStatus.FORBIDDEN, AppConstants.ErrorCode.FORBIDDEN, cause);
    }

    public static ForbiddenException accessDenied(String action, String resource) {
        return new ForbiddenException(
            String.format("Access denied: insufficient permissions to %s %s", action, resource),
            AppConstants.ErrorCode.ACCESS_DENIED
        );
    }
}
