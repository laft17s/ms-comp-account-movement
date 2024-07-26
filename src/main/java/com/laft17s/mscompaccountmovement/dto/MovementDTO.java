package com.laft17s.mscompaccountmovement.dto;

import com.laft17s.mscompaccountmovement.entities.Account;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovementDTO {
    private Long movementId;
    private Date registerDate;
    private String type;
    private Float inHand;
    private Float initialBalance;
    private AccountDTO account;
}
