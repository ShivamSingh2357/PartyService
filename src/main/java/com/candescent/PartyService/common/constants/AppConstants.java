package com.candescent.PartyService.common.constants;

/**
 * Application-wide constants.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    /**
     * Response status constants.
     */
    public static final class Status {
        public static final String SUCCESS = "SUCCESS";
        public static final String FAIL = "FAIL";

        private Status() {}
    }

    /**
     * Error code constants.
     */
    public static final class ErrorCode {
        public static final String BAD_REQUEST = "BAD_REQUEST";
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        public static final String FORBIDDEN = "FORBIDDEN";
        public static final String ACCESS_DENIED = "ACCESS_DENIED";
        public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
        public static final String CONFLICT = "CONFLICT";
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String SERVICE_ERROR = "SERVICE_ERROR";
        public static final String EXTERNAL_SERVICE_ERROR = "EXTERNAL_SERVICE_ERROR";
        public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

        private ErrorCode() {}
    }

    /**
     * Error message constants.
     */
    public static final class ErrorMessage {
        public static final String SERVICE_OPERATION_FAILED = "Service operation failed";
        public static final String RESOURCE_NOT_FOUND = "Requested resource not found";
        public static final String INVALID_REQUEST_PARAMS = "Invalid request parameters";
        public static final String REQUEST_PROCESSING_FAILED = "Request processing failed";
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
        public static final String DEFAULT_ERROR = "An error occurred";
        public static final String INVALID_FIELD_VALUE = "Invalid value for field: ";
        public static final String VALIDATION_FAILED = "Validation failed: ";

        private ErrorMessage() {}
    }

    /**
     * Validation constants.
     */
    public static final class Validation {
        public static final int MAX_ERROR_LENGTH = 100;
        public static final int DEFAULT_PAGE = 1;
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MAX_PAGE_SIZE = 100;

        private Validation() {}
    }

    /**
     * Entity field name constants for JPA queries.
     */
    public static final class EntityField {
        // Product fields
        public static final String CATEGORY = "category";
        public static final String TYPE = "type";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String CODE = "code";
        public static final String SORT_ORDER = "sortOrder";

        private EntityField() {}
    }
}

