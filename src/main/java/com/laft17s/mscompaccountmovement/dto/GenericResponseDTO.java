package com.laft17s.mscompaccountmovement.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenericResponseDTO {

    private Integer code;
    private Status status;
    private List<Object> payload;
    private String message;

    public enum Status {
        OK, ERROR, WARNING
    }
}
