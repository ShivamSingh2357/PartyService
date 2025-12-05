package com.candescent.PartyService.repository;

import com.candescent.PartyService.entities.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for PartyEntity.
 */
@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, Long> {

    /**
     * Find party by email ID.
     *
     * @param emailId the email ID
     * @return Optional containing the party if found
     */
    Optional<PartyEntity> findByEmailId(String emailId);

    /**
     * Check if party exists with given email ID.
     *
     * @param emailId the email ID
     * @return true if exists, false otherwise
     */
    boolean existsByEmailId(String emailId);
}

