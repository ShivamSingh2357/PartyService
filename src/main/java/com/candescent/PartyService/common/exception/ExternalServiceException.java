package com.candescent.PartyService.common.exception;

import com.candescent.PartyService.common.constants.AppConstants;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an external service call fails.
 */
public class ExternalServiceException extends BaseException {

    private final String serviceName;

    public ExternalServiceException(String serviceName, String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE, AppConstants.ErrorCode.EXTERNAL_SERVICE_ERROR);
        this.serviceName = serviceName;
    }

    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE, AppConstants.ErrorCode.EXTERNAL_SERVICE_ERROR, cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
