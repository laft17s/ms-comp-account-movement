package com.laft17s.mscompaccountmovement.controller;

import com.laft17s.mscompaccountmovement.config.ManagementServiceConfig;
import com.laft17s.mscompaccountmovement.dao.AccountMovementServiceDAO;
import com.laft17s.mscompaccountmovement.dto.*;
import com.laft17s.mscompaccountmovement.utils.AccountMovementUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    //
    @Autowired
    AccountMovementServiceDAO accountMovementServiceDAO;

    @Autowired
    MovementController movementController;


    @Autowired
    private ManagementServiceConfig managementServiceConfig;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public GenericResponseDTO getAll () {
        try {
            return AccountMovementUtils.mapServiceResponse(
                    HttpStatus.OK.value(),
                    GenericResponseDTO.Status.OK,
                    accountMovementServiceDAO.listAccounts(),
                    "Se muestra el listado de las cuentas registradas."
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la lista de cuentas: " + e.getMessage(),
                    e
            );
        }
    }

    @PostMapping
    public GenericResponseDTO create (@RequestBody RegisterClientAccountReqDTO register) {

        Long clientId  = null;

        if (!validateinitialBalanceNewAccount(register.getInitialBalance())) {
            throw new IllegalStateException("Para crear una nueva cuenta de usuario se requiere un saldo inicial mínimo de 50.00");
        }

        // URL del microservicio anterior para obtener el cliente por DNI
        String clientServiceUrl = managementServiceConfig.getFindClientByDniUrl() + "/" + register.getDni();

        // Consumir el endpoint del microservicio anterior
        GenericResponseDTO result = restTemplate.getForObject(clientServiceUrl, GenericResponseDTO.class);

        if ( result == null || result.getPayload().isEmpty()) {
            return GenericResponseDTO.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(GenericResponseDTO.Status.ERROR)
                    .message("No existe el cliente.")
                    .build();
        } else {
            // Convertir el payload a ClientDTO usando el mapper personalizado
            ClientDTO clientDTO = null;
            try {
                Map<String, Object> clientMap = (Map<String, Object>) result.getPayload().get(0);
                clientDTO = mapToClientDTO(clientMap);
                clientId = clientDTO.getClientId();
            } catch (Exception e) {
                return GenericResponseDTO.builder()
                        .code(500)
                        .status(GenericResponseDTO.Status.ERROR)
                        .message("Failed to convert payload to ClientDTO")
                        .build();
            }
        }

        String accountNumber = AccountMovementUtils.generateUniqueAccountNumber();

        AccountDTO saveAccount = AccountDTO.builder()
                .accountNumber(accountNumber)
                .type(register.getType())
                .initialBalance(register.getInitialBalance())
                .clientId(clientId)
                .status(true)
                .build();

        accountMovementServiceDAO.saveAccount(saveAccount);

        AccountDTO account = accountMovementServiceDAO.findByAccountNumber(accountNumber);

        MovementDTO savedMovement = MovementDTO.builder()
                .account(account)
                .registerDate(new Date())
                .type("Creación de cuenta de cliente +" + register.getInitialBalance())
                .initialBalance(0.00F)
                .inHand(register.getInitialBalance())
                .build();

        GenericResponseDTO movementResponse = movementController.create(savedMovement);

        log.info(movementResponse.getMessage());

        return GenericResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .status(GenericResponseDTO.Status.OK)
                .payload(null)
                .message("Se ha creado y asociado la cuenta del cliente correctamente.")
                .build();
    }

    private ClientDTO mapToClientDTO(Map<String, Object> clientMap) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(((Number) clientMap.get("clientId")).longValue());
        // Mapea otros campos según sea necesario
        return clientDTO;
    }

    private Boolean validateinitialBalanceNewAccount(Float initialBalance) {
        // Verificar que el initialBalance sea positivo y al menos 50.00
        if (initialBalance == null || initialBalance < 50) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

}
