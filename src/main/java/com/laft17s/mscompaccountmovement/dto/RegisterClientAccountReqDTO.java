package com.laft17s.mscompaccountmovement.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterClientAccountReqDTO {
    private String dni;
    private String type;
    private Float initialBalance;
}
