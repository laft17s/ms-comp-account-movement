package com.laft17s.mscompaccountmovement.exceptions;

import com.laft17s.mscompaccountmovement.dto.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GenericResponseDTO> handleNotFoundException(ResponseStatusException ex) {
        GenericResponseDTO response = GenericResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(GenericResponseDTO.Status.ERROR)
                .message(ex.getReason() != null ? ex.getReason() : "Resource not found")
                .payload(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericResponseDTO> handleGlobalException(Exception ex) {
        GenericResponseDTO response = GenericResponseDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(GenericResponseDTO.Status.ERROR)
                .message(ex.getMessage() != null ? ex.getMessage() : "Internal server error")
                .payload(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
