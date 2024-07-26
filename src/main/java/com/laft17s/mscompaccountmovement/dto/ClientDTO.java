package com.laft17s.mscompaccountmovement.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientDTO {

    private Long clientId;
    private String pass;
    private Boolean status;

    private PersonDTO person;
}
