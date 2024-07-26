package com.laft17s.mscompaccountmovement.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {

    private Long accountId;
    private String accountNumber;
    private String type;
    private Float initialBalance;
    private Boolean status;
    private Long clientId;

}
