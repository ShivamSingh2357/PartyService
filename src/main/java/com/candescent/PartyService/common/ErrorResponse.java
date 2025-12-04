package com.candescent.PartyService.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Standardized error response DTO for API error responses.
 * Follows RFC 7807 Problem Details for HTTP APIs conventions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String errorCode;
    private final String message;
    private final String detail;
    private final String path;
    private final Instant timestamp;
    private final String traceId;
    private final List<FieldErrorDetail> errors;
    private final Map<String, Object> metadata;

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.errorCode = builder.errorCode;
        this.message = builder.message;
        this.detail = builder.detail;
        this.path = builder.path;
        this.timestamp = builder.timestamp != null ? builder.timestamp : Instant.now();
        this.traceId = builder.traceId;
        this.errors = builder.errors;
        this.metadata = builder.metadata;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public int getStatus() { return status; }
    public String getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
    public String getDetail() { return detail; }
    public String getPath() { return path; }
    public Instant getTimestamp() { return timestamp; }
    public String getTraceId() { return traceId; }
    public List<FieldErrorDetail> getErrors() { return errors; }
    public Map<String, Object> getMetadata() { return metadata; }

    public static class Builder {
        private int status;
        private String errorCode;
        private String message;
        private String detail;
        private String path;
        private Instant timestamp;
        private String traceId;
        private List<FieldErrorDetail> errors;
        private Map<String, Object> metadata;

        public Builder status(int status) { this.status = status; return this; }
        public Builder errorCode(String errorCode) { this.errorCode = errorCode; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder detail(String detail) { this.detail = detail; return this; }
        public Builder path(String path) { this.path = path; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder traceId(String traceId) { this.traceId = traceId; return this; }
        public Builder errors(List<FieldErrorDetail> errors) { this.errors = errors; return this; }
        public Builder metadata(Map<String, Object> metadata) { this.metadata = metadata; return this; }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    /**
     * Represents a field-level validation error.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldErrorDetail {
        private final String field;
        private final String message;
        private final Object rejectedValue;

        private FieldErrorDetail(FieldErrorDetailBuilder builder) {
            this.field = builder.field;
            this.message = builder.message;
            this.rejectedValue = builder.rejectedValue;
        }

        public static FieldErrorDetailBuilder builder() {
            return new FieldErrorDetailBuilder();
        }

        public String getField() { return field; }
        public String getMessage() { return message; }
        public Object getRejectedValue() { return rejectedValue; }

        public static class FieldErrorDetailBuilder {
            private String field;
            private String message;
            private Object rejectedValue;

            public FieldErrorDetailBuilder field(String field) { this.field = field; return this; }
            public FieldErrorDetailBuilder message(String message) { this.message = message; return this; }
            public FieldErrorDetailBuilder rejectedValue(Object rejectedValue) { this.rejectedValue = rejectedValue; return this; }

            public FieldErrorDetail build() {
                return new FieldErrorDetail(this);
            }
        }
    }
}
