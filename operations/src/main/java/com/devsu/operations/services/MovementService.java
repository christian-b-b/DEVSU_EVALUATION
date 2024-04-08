package com.devsu.operations.services;

import com.devsu.operations.dtos.MovementResponseDTO;
import com.devsu.operations.dtos.request.MovementRequestDTO;

public interface MovementService {

    public MovementResponseDTO createMovement(MovementRequestDTO movementRequestDTO);
}
