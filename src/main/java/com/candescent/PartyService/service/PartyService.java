package com.candescent.PartyService.service;

import com.candescent.PartyService.common.exception.ConflictException;
import com.candescent.PartyService.common.exception.ResourceNotFoundException;
import com.candescent.PartyService.dto.request.PartyRequest;
import com.candescent.PartyService.dto.response.PartyResponse;
import com.candescent.PartyService.entities.PartyEntity;
import com.candescent.PartyService.mapper.PartyMapper;
import com.candescent.PartyService.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Party operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    /**
     * Create a new party.
     *
     * @param request the party request
     * @return the created party response
     */
    @Transactional
    public PartyResponse createParty(PartyRequest request) {
        log.info("Creating party with custId: {}", request.getCustId());
        PartyEntity entity = partyMapper.toEntity(request);
        PartyEntity savedEntity = partyRepository.save(entity);
        log.info("Party created successfully with id: {}", savedEntity.getId());
        return partyMapper.toResponse(savedEntity);
    }

    /**
     * Update an existing party.
     *
     * @param id      the party ID
     * @param request the party request with updated data
     * @return the updated party response
     */
    @Transactional
    public PartyResponse updateParty(Long id, PartyRequest request) {

        log.info("Updating party with id: {}", id);
        PartyEntity existingEntity = partyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + id));
        // Check for conflicts with custId
        if (!existingEntity.getCustId().equals(request.getCustId())) {
            if (partyRepository.existsByCustId(request.getCustId())) {
                throw new ConflictException("Party with custId " + request.getCustId() + " already exists");
            }
        }
        // Check for conflicts with emailId
        if (!existingEntity.getEmailId().equals(request.getEmailId())) {
            if (partyRepository.existsByEmailId(request.getEmailId())) {
                throw new ConflictException("Party with emailId " + request.getEmailId() + " already exists");
            }
        }
        partyMapper.updateEntityFromRequest(existingEntity, request);
        PartyEntity updatedEntity = partyRepository.save(existingEntity);
        log.info("Party updated successfully with id: {}", updatedEntity.getId());
        return partyMapper.toResponse(updatedEntity);
    }

    /**
     * Get party by ID.
     *
     * @param id the party ID
     * @return the party response
     */
    @Transactional(readOnly = true)
    public PartyResponse getPartyById(Long id) {
        log.debug("Fetching party with id: {}", id);

        PartyEntity entity = partyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Party not found with id: {}", id);
                    return new ResourceNotFoundException("Party not found with id: " + id);
                });

        return partyMapper.toResponse(entity);
    }

    /**
     * Get party by customer ID.
     *
     * @param custId the customer ID
     * @return the party response
     */
    @Transactional(readOnly = true)
    public PartyResponse getPartyByCustId(Long custId) {
        log.debug("Fetching party with custId: {}", custId);

        PartyEntity entity = partyRepository.findByCustId(custId)
                .orElseThrow(() -> {
                    log.error("Party not found with custId: {}", custId);
                    return new ResourceNotFoundException("Party not found with custId: " + custId);
                });

        return partyMapper.toResponse(entity);
    }
}

