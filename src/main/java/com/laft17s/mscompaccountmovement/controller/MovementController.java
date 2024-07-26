package com.laft17s.mscompaccountmovement.controller;

import com.laft17s.mscompaccountmovement.dao.AccountMovementServiceDAO;
import com.laft17s.mscompaccountmovement.dto.GenericResponseDTO;
import com.laft17s.mscompaccountmovement.dto.MovementDTO;
import com.laft17s.mscompaccountmovement.entities.Account;
import com.laft17s.mscompaccountmovement.entities.Movement;
import com.laft17s.mscompaccountmovement.repository.AccountRepository;
import com.laft17s.mscompaccountmovement.repository.MovementRepository;
import com.laft17s.mscompaccountmovement.utils.AccountMovementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    //
    @Autowired
    AccountMovementServiceDAO accountMovementServiceDAO;

    @GetMapping
    public GenericResponseDTO getAll () {
        try {
            return AccountMovementUtils.mapServiceResponse(
                    HttpStatus.OK.value(),
                    GenericResponseDTO.Status.OK,
                    accountMovementServiceDAO.listMovements(),
                    "Se muestra el listado de los movimientos registradas."
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de movimientos: " + e.getMessage(),
                    e
            );
        }
    }

    @PostMapping
    public GenericResponseDTO create (@RequestBody MovementDTO movement) {

        // Validar el movimiento
        if (movement == null || movement.getAccount() == null || movement.getAccount().getAccountId() == null) {
            return GenericResponseDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(GenericResponseDTO.Status.ERROR)
                    .message("Movimiento inválido. Por favor, asegúrese de que todos los campos requeridos están completos.")
                    .build();
        }

        try {
            accountMovementServiceDAO.saveMovement(movement);
        } catch (Exception e) {
            return GenericResponseDTO.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(GenericResponseDTO.Status.ERROR)
                    .message("Error al guardar el movimiento.")
                    .build();
        }

        return GenericResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .status(GenericResponseDTO.Status.OK)
                .message("Movimiento guardado exitosamente.")
                .build();
    }

}
