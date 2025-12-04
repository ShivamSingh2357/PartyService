package com.candescent.PartyService.dto.common;

import com.candescent.PartyService.common.constants.AppConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic wrapper for API response payloads.
 *
 * @param <T> the type of the response payload
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errorDescription")
    private String errorDescription;

    @JsonProperty("partyData")
    private T partyData;

    public static <T> ServiceResponse<T> success(T partyData) {
        return ServiceResponse.<T>builder()
                .status(AppConstants.Status.SUCCESS)
                .partyData(partyData)
                .build();
    }

    public static <T> ServiceResponse<T> success(T partyData, String message) {
        return ServiceResponse.<T>builder()
                .status(AppConstants.Status.SUCCESS)
                .message(message)
                .partyData(partyData)
                .build();
    }

    public static <T> ServiceResponse<T> fail(String errorDescription) {
        return ServiceResponse.<T>builder()
                .status(AppConstants.Status.FAIL)
                .errorDescription(truncate(errorDescription))
                .build();
    }

    private static String truncate(String message) {
        if (message == null) {
            return AppConstants.ErrorMessage.DEFAULT_ERROR;
        }
        String trimmed = message.trim();
        if (trimmed.length() <= AppConstants.Validation.MAX_ERROR_LENGTH) {
            return trimmed;
        }
        return trimmed.substring(0, AppConstants.Validation.MAX_ERROR_LENGTH - 3) + "...";
    }
}
