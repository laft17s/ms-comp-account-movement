package com.laft17s.mscompaccountmovement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movement")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Long movementId;

    @Column(name = "register_date", nullable = false)
    private Date registerDate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "in_hand", nullable = false)
    private Float inHand;

    @Column(name = "initial_balance", nullable = false)
    private Float initialBalance;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;


}
