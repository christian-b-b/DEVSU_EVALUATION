package com.devsu.operations.controllers;

import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.MovementReportResponseDTO;
import com.devsu.operations.services.ReportMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(OperationConstants.API_VERSION + OperationConstants.RESOURCE_MOVEMENT_REPORT)
public class MovementReportController {
    @Autowired
    private ReportMovementService reportMovementService;

    @GetMapping()
    public ResponseEntity<List<MovementReportResponseDTO>> movementReport(@RequestParam("startDate") String startMovementDate,
                                                                          @RequestParam("endDate") String endMovementDate,
                                                                          @RequestParam("idCustomer")Long idCustomer){
        List<MovementReportResponseDTO> repor = reportMovementService.movementReport(LocalDate.parse(startMovementDate),LocalDate.parse(endMovementDate),idCustomer);
        return new ResponseEntity<>(repor, HttpStatus.OK);
    }


}
