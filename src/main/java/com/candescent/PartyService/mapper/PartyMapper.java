package com.candescent.PartyService.mapper;

import com.candescent.PartyService.dto.request.PartyRequest;
import com.candescent.PartyService.dto.response.PartyResponse;
import com.candescent.PartyService.entities.PartyEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Party entities and DTOs.
 */
@Component
public class PartyMapper {

    /**
     * Convert PartyRequest to PartyEntity.
     *
     * @param request the party request
     * @return the party entity
     */
    public PartyEntity toEntity(PartyRequest request) {

        if (request == null) {
            return null;
        }
        return PartyEntity.builder()
                .custFirstName(request.getCustFirstName())
                .custLastName(request.getCustLastName())
                .emailId(request.getEmailId())
                .phoneNo(request.getPhoneNo())
                .build();
    }

    /**
     * Convert PartyEntity to PartyResponse.
     *
     * @param entity the party entity
     * @return the party response
     */
    public PartyResponse toResponse(PartyEntity entity) {

        if (entity == null) {
            return null;
        }
        return PartyResponse.builder()
                .id(String.valueOf(entity.getId()))
                .custFirstName(entity.getCustFirstName())
                .custLastName(entity.getCustLastName())
                .emailId(entity.getEmailId())
                .phoneNo(entity.getPhoneNo())
                .build();
    }

    /**
     * Update existing PartyEntity with data from PartyRequest.
     *
     * @param entity  the existing party entity
     * @param request the party request with updated data
     */
    public void updateEntityFromRequest(PartyEntity entity, PartyRequest request) {

        if (entity == null || request == null) {
            return;
        }
        if (request.getCustFirstName() != null) {
            entity.setCustFirstName(request.getCustFirstName());
        }
        if (request.getCustLastName() != null) {
            entity.setCustLastName(request.getCustLastName());
        }
        if (request.getEmailId() != null) {
            entity.setEmailId(request.getEmailId());
        }
        if (request.getPhoneNo() != null) {
            entity.setPhoneNo(request.getPhoneNo());
        }
    }
}

