package com.devsu.operations.services.impl;


import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.MovementResponseDTO;
import com.devsu.operations.dtos.request.MovementRequestDTO;
import com.devsu.operations.errorhandler.OperationGenericClientException;
import com.devsu.operations.models.Account;
import com.devsu.operations.models.Master;
import com.devsu.operations.models.Movement;
import com.devsu.operations.repositories.AccountRepository;
import com.devsu.operations.repositories.MasterRepository;
import com.devsu.operations.repositories.MovementRepository;
import com.devsu.operations.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private MovementRepository movementRepository;
    @Override
    public MovementResponseDTO createMovement(MovementRequestDTO movementRequestDTO) {
        Movement movement = Movement.builder().build();

        Account account = accountRepository.findByAccountNumber(movementRequestDTO.getAccountNumber()).orElseThrow(
                ()-> new OperationGenericClientException("account not found",HttpStatus.NOT_FOUND,null, null)
        );

        movement.setMovementTypeCode(movementRequestDTO.getMovementTypeCode());
        movement.setMovementDate(LocalDate.now());
        BigDecimal initialBalance = account.getInitialBalance();

        if (movement.getMovementTypeCode().equals(OperationConstants.MOVEMENT_TYPE_WITHDRAW)){
            BigDecimal finalBalance = account.getInitialBalance().subtract(movementRequestDTO.getAmount());
            if (finalBalance.compareTo(BigDecimal.ZERO)<OperationConstants.ZERO)
                throw new OperationGenericClientException("Saldo no disponible",HttpStatus.BAD_REQUEST,null,null);
            account.setInitialBalance(finalBalance);
            accountRepository.save(account);
        }
        if(movement.getMovementTypeCode().equals(OperationConstants.MOVEMENT_TYPE_DEPOSIT)){
            BigDecimal finalBalance = account.getInitialBalance().add(movementRequestDTO.getAmount());
            account.setInitialBalance(finalBalance);
            accountRepository.save(account);
        }
        movement.setInitialBalance(initialBalance);
        movement.setAccount(account);
        movement.setAmount(movementRequestDTO.getAmount());
        movement.setState(OperationConstants.ACTIVE_STATE);
        movement.setRegistrationDate(LocalDateTime.now());
        movementRepository.save(movement);
        Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                account.getAccountTypeCode()).orElseThrow(
                ()-> new OperationGenericClientException("Account type not found",HttpStatus.NOT_FOUND,null, null));
        Master movementType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_MOVEMENT_TYPE,
                movement.getMovementTypeCode()).orElseThrow(
                ()-> new OperationGenericClientException("Movement type not found",HttpStatus.NOT_FOUND,null, null));

        return MovementResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(accountType.getDescription())
                .initialBalance(movement.getInitialBalance())
                .state(movement.getState()==OperationConstants.ACTIVE_STATE?true:false)
                .movement(movementType.getDescription()+" de "+ movement.getAmount())
                .build();
    }

}
