package com.devsu.operations.controllers;


import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.AccountResponseDTO;
import com.devsu.operations.dtos.request.AccountRequestDTO;
import com.devsu.operations.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OperationConstants.API_VERSION + OperationConstants.RESOURCE_ACCOUNT)
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts(){
        List<AccountResponseDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id){
        AccountResponseDTO accountResponseDTO = accountService.getAccountById(id);
        return new ResponseEntity<>(accountResponseDTO,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.createAccount(accountRequestDTO);
        return new ResponseEntity<>(accountResponseDTO,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDTO accountRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.updateAccount(accountRequestDTO,id);
        return new ResponseEntity<>(accountResponseDTO,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
