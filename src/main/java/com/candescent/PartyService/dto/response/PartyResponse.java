package com.candescent.PartyService.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for Party operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartyResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("custFirstName")
    private String custFirstName;

    @JsonProperty("custLastName")
    private String custLastName;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("phoneNo")
    private String phoneNo;
}

