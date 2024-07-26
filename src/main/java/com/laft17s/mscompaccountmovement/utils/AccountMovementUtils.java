package com.laft17s.mscompaccountmovement.utils;

import com.laft17s.mscompaccountmovement.dto.GenericResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AccountMovementUtils {

    public static GenericResponseDTO mapServiceResponse (Integer code, GenericResponseDTO.Status status, Object payload, String message) {

        List<Object> list;
        if (payload == null) {
            list = Collections.emptyList();
        } else if (payload instanceof List) {
            list = (List<Object>) payload;
        } else {
            list = Collections.singletonList(payload);
        }

        return GenericResponseDTO.builder()
                .code(code)
                .status(status)
                .payload(list)
                .message(message)
                .build();
    }

    public static String generateUniqueAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("00000000");
        Integer number = random.nextInt(100000000);
        accountNumber.replace(8 - String.valueOf(number).length(), 8, String.valueOf(number));
        return accountNumber.toString();
    }


}
