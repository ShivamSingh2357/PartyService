package com.candescent.PartyService.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Party (Customer).
 */
@Entity
@Table(name = "party")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PartyEntity extends BaseEntity {

    @Column(name = "cust_id", nullable = false)
    private Long custId;

    @Column(name = "cust_first_name", nullable = false, length = 100)
    private String custFirstName;

    @Column(name = "cust_last_name", nullable = false, length = 100)
    private String custLastName;

    @Column(name = "email_id", nullable = false, length = 255)
    private String emailId;

    @Column(name = "phone_no", nullable = false, length = 20)
    private String phoneNo;
}

