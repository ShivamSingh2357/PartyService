package com.candescent.PartyService.controller;

import com.candescent.PartyService.api.PartyApi;
import com.candescent.PartyService.common.exception.ServiceException;
import com.candescent.PartyService.common.exception.ValidationException;
import com.candescent.PartyService.dto.common.ServiceRequest;
import com.candescent.PartyService.dto.common.ServiceResponse;
import com.candescent.PartyService.dto.request.PartyRequest;
import com.candescent.PartyService.dto.response.PartyResponse;
import com.candescent.PartyService.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Party operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PartyController implements PartyApi {

    private final PartyService partyService;

    @Override
    public ServiceResponse<PartyResponse> createParty(ServiceRequest<PartyRequest> request) {

        try {
            log.info("Received request to create party");
            PartyRequest partyRequest = request.getPartyData();
            validatePartyRequest(partyRequest);
            PartyResponse partyResponse = partyService.createParty(partyRequest);
            log.info("Party created successfully with id: {}", partyResponse.getId());
            return ServiceResponse.success(partyResponse, "Party created successfully");
        } catch (Exception e) {
            log.error("Failed to create party: {}", e.getMessage(), e);
            throw new ServiceException("Unable to create party");
        }
    }

    @Override
    public ServiceResponse<PartyResponse> updateParty(Long id, ServiceRequest<PartyRequest> request) {

        try {
            log.info("Received request to update party with id: {}", id);
            PartyRequest partyRequest = request.getPartyData();
            validatePartyRequest(partyRequest);
            PartyResponse partyResponse = partyService.updateParty(id, partyRequest);
            log.info("Party updated successfully with id: {}", partyResponse.getId());
            return ServiceResponse.success(partyResponse, "Party updated successfully");
        } catch (Exception e) {
            log.error("Failed to update party: {}", e.getMessage(), e);
            throw new ServiceException("Unable to update party");
        }
    }

    /**
     * Validate party request.
     *
     * @param request the party request
     */
    private void validatePartyRequest(PartyRequest request) {

        if (request == null) {
            throw new ValidationException("Party request cannot be null");
        }
        if (request.getCustId() == null) {
            throw new ValidationException("Customer ID is required");
        }
        if (request.getCustFirstName() == null || request.getCustFirstName().trim().isEmpty()) {
            throw new ValidationException("First name is required");
        }
        if (request.getCustLastName() == null || request.getCustLastName().trim().isEmpty()) {
            throw new ValidationException("Last name is required");
        }
        if (request.getEmailId() == null || request.getEmailId().trim().isEmpty()) {
            throw new ValidationException("Email ID is required");
        }
        if (!isValidEmail(request.getEmailId())) {
            throw new ValidationException("Invalid email format");
        }
        if (request.getPhoneNo() == null || request.getPhoneNo().trim().isEmpty()) {
            throw new ValidationException("Phone number is required");
        }
        if (!isValidPhoneNumber(request.getPhoneNo())) {
            throw new ValidationException("Invalid phone number format");
        }
    }

    /**
     * Validate email format.
     *
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate phone number format.
     *
     * @param phoneNo the phone number to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidPhoneNumber(String phoneNo) {

        if (phoneNo == null) {
            return false;
        }
        // Allow digits, spaces, hyphens, parentheses, and plus sign
        String phoneRegex = "^[0-9+\\-\\s()]{7,20}$";
        return phoneNo.matches(phoneRegex);
    }
}
