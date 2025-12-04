package com.candescent.PartyService.api;

import com.candescent.PartyService.dto.common.ServiceRequest;
import com.candescent.PartyService.dto.common.ServiceResponse;
import com.candescent.PartyService.dto.request.PartyRequest;
import com.candescent.PartyService.dto.response.PartyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Party Service", description = "Party details")
@RequestMapping("/v1/party")
public interface PartyApi {

    @Operation(
            summary = "Create a new party",
            description = "Creates a new party (customer) record in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Party created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServiceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Party already exists",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    ServiceResponse<PartyResponse> createParty(@RequestBody ServiceRequest<PartyRequest> request);

    @Operation(
            summary = "Update an existing party",
            description = "Updates an existing party (customer) record in the system by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Party updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ServiceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Party not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict with existing data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/{id}")
    ServiceResponse<PartyResponse> updateParty(
            @PathVariable("id") Long id,
            @RequestBody ServiceRequest<PartyRequest> request
    );
}
