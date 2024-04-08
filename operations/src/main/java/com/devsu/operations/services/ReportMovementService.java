package com.devsu.operations.services;

import com.devsu.operations.dtos.MovementReportResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface ReportMovementService {


    public List<MovementReportResponseDTO> movementReport(LocalDate startMovementDate,LocalDate endMovementDate,Long idCustomer);


}
