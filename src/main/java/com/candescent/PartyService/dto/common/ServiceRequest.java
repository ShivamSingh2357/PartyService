package com.candescent.PartyService.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic wrapper for API request payloads.
 *
 * @param <T> the type of the request payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRequest<T> {

    @JsonProperty("partyData")
    private T partyData;

    public static <T> ServiceRequest<T> of(T partyData) {
        return new ServiceRequest<>(partyData);
    }
}
