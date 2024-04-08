package com.devsu.operations.controllers;


import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.MovementResponseDTO;
import com.devsu.operations.dtos.request.MovementRequestDTO;
import com.devsu.operations.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(OperationConstants.API_VERSION + OperationConstants.RESOURCE_MOVEMENT)
public class MovementController {
    @Autowired
    private MovementService movementService;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO movementRequestDTO){
        MovementResponseDTO movementResponseDTO = movementService.createMovement(movementRequestDTO);
        return new ResponseEntity<>(movementResponseDTO,HttpStatus.OK);
    }

}
