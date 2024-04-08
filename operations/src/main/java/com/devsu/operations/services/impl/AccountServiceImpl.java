package com.devsu.operations.services.impl;


import com.devsu.operations.constants.OperationConstants;
import com.devsu.operations.dtos.AccountResponseDTO;
import com.devsu.operations.dtos.request.AccountRequestDTO;
import com.devsu.operations.errorhandler.OperationGenericClientException;
import com.devsu.operations.models.Account;
import com.devsu.operations.models.Customer;
import com.devsu.operations.models.Master;
import com.devsu.operations.repositories.AccountRepository;
import com.devsu.operations.repositories.CustomerRepository;
import com.devsu.operations.repositories.MasterRepository;
import com.devsu.operations.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(account -> {
            AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder().build();
            accountResponseDTO.setAccountNumber(account.getAccountNumber());
            Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                    account.getAccountTypeCode()).orElseThrow(
                    ()-> new OperationGenericClientException("Account type not found",HttpStatus.NOT_FOUND,null, null));
            accountResponseDTO.setAccountType(accountType.getDescription());
            accountResponseDTO.setInitialBalance(account.getInitialBalance());
            accountResponseDTO.setState(account.getState()==OperationConstants.ACTIVE_STATE?true:false);
            Customer customer = customerRepository.getCustomertById(account.getIdCustomer()).orElseThrow(
                    ()-> new OperationGenericClientException("incorrect id",HttpStatus.NOT_FOUND,null, null)
            );
            accountResponseDTO.setCustomer(customer.getNames());
            return accountResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                ()-> new OperationGenericClientException("Account not found",HttpStatus.NOT_FOUND,null, null)
        );
        Customer customer = customerRepository.getCustomertById(account.getIdCustomer()).orElseThrow(
                ()-> new OperationGenericClientException("Customer not found",HttpStatus.NOT_FOUND,null, null)
        );
        Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                account.getAccountTypeCode()).orElseThrow(
                ()-> new OperationGenericClientException("Account type not found",HttpStatus.NOT_FOUND,null, null));
        return AccountResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(accountType.getDescription())
                .initialBalance(account.getInitialBalance())
                .state(account.getState()==OperationConstants.ACTIVE_STATE?true:false)
                .customer(customer.getNames()).build();
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Account account = Account.builder().build();
        Customer customer = customerRepository.getCustomertById(accountRequestDTO.getIdCustomer()).orElseThrow(
                ()-> new OperationGenericClientException("incorrect id",HttpStatus.NOT_FOUND,null, null)
        );
        account.setIdCustomer(customer.getIdCustomer());
        account.setAccountNumber(accountRequestDTO.getAccountNumber());

        Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                accountRequestDTO.getAccountTypeCode()).orElseThrow(
                ()-> new OperationGenericClientException("id not found",HttpStatus.NOT_FOUND,null, null));

        account.setAccountTypeCode(accountType.getCode());
        account.setInitialBalance(accountRequestDTO.getInitialBalance());
        account.setState(OperationConstants.ACTIVE_STATE);
        account.setRegistrationDate(LocalDateTime.now());
        accountRepository.save(account);

        return AccountResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountTypeCode())
                .initialBalance(account.getInitialBalance())
                .state(account.getState()==OperationConstants.ACTIVE_STATE?true:false)
                .customer(customer.getNames()).build();
    }

    @Override
    public AccountResponseDTO updateAccount(AccountRequestDTO accountRequestDTO, Long idAccount) {
        Account account = accountRepository.findById(idAccount).orElseThrow(
                ()-> new OperationGenericClientException("Account not found",HttpStatus.NOT_FOUND,null,null));
        Customer customer = customerRepository.getCustomertById(account.getIdCustomer()).orElseThrow(
                ()-> new OperationGenericClientException("Customer not found",HttpStatus.NOT_FOUND,null, null)
        );
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        Master accountType =masterRepository.findByParentCodeAndCode(OperationConstants.PARENT_CODE_ACCOUNT_TYPE,
                accountRequestDTO.getAccountTypeCode()).orElseThrow(
                ()-> new OperationGenericClientException("id not found",HttpStatus.NOT_FOUND,null, null));
        account.setAccountTypeCode(accountRequestDTO.getAccountTypeCode());
        account.setInitialBalance(accountRequestDTO.getInitialBalance());
        accountRepository.save(account);

        return AccountResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(accountType.getDescription())
                .initialBalance(account.getInitialBalance())
                .state(account.getState()==OperationConstants.ACTIVE_STATE? true: false)
                .customer(customer.getNames()).build();
    }

    @Override
    public void deleteAccount(Long idAccount) {
        accountRepository.findById(idAccount).orElseThrow(
                ()-> new OperationGenericClientException("id not found",HttpStatus.NOT_FOUND,null, null)
        );
        accountRepository.deleteById(idAccount);
    }
}
