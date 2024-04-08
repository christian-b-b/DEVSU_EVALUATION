package com.devsu.operations.services.impl;

import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.MovementReportResponseDTO;
import com.devsu.operations.errorhandler.OperationGenericClientException;
import com.devsu.operations.models.Account;
import com.devsu.operations.models.Customer;
import com.devsu.operations.models.Master;
import com.devsu.operations.models.Movement;
import com.devsu.operations.repositories.AccountRepository;
import com.devsu.operations.repositories.CustomerRepository;
import com.devsu.operations.repositories.MasterRepository;
import com.devsu.operations.repositories.MovementRepository;
import com.devsu.operations.services.ReportMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportMovementServiceImpl implements ReportMovementService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Override
    public List<MovementReportResponseDTO> movementReport(LocalDate startMovementDate, LocalDate endMovementDate, Long idCustomer) {
        List<MovementReportResponseDTO> report = new ArrayList<>();
        Customer customer = customerRepository.getCustomertById(idCustomer).orElseThrow(
                ()-> new OperationGenericClientException("customer not found", HttpStatus.NOT_FOUND,null, null)
        );
        List<Account> accounts = accountRepository.findAccountsByIdCustomer(idCustomer);

        accounts.forEach(account -> {

            List<Movement> movements = movementRepository.findAllByAccount(account);
            movements.forEach(movement -> {
                MovementReportResponseDTO movementReportResponseDTO = MovementReportResponseDTO.builder().build();
                movementReportResponseDTO.setMovementDate(movement.getMovementDate());
                movementReportResponseDTO.setCustomer(customer.getNames());
                movementReportResponseDTO.setAccountNumber(account.getAccountNumber());
                Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                        account.getAccountTypeCode()).orElseThrow(
                        ()-> new OperationGenericClientException("Account type not found",HttpStatus.NOT_FOUND,null, null));
                movementReportResponseDTO.setAccountType(accountType.getDescription());
                movementReportResponseDTO.setInitialBalance(movement.getInitialBalance());
                movementReportResponseDTO.setState(movement.getState()==OperationConstants.ACTIVE_STATE?true:false);
                if (movement.getMovementTypeCode().equals("WITHDRAW")){
                    movementReportResponseDTO.setMovement("-"+movement.getAmount());
                    movementReportResponseDTO.setAvailableBalance(movement.getInitialBalance().subtract(movement.getAmount()));
                }else {
                    movementReportResponseDTO.setMovement(""+movement.getAmount());
                    movementReportResponseDTO.setAvailableBalance(movement.getInitialBalance().add(movement.getAmount()));
                }
                report.add(movementReportResponseDTO);

            });
        });
        return report.stream().filter(movRep->movRep.getMovementDate().compareTo(startMovementDate) >= 0 && movRep.getMovementDate().compareTo(endMovementDate)<=0).collect(Collectors.toList());
    }
}
