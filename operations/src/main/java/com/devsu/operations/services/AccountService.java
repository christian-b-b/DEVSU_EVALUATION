package com.devsu.operations.services;

import com.devsu.operations.dtos.AccountResponseDTO;
import com.devsu.operations.dtos.request.AccountRequestDTO;
import java.util.List;

public interface AccountService {
    public List<AccountResponseDTO> getAllAccounts();
    public AccountResponseDTO getAccountById(Long id);
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    public AccountResponseDTO updateAccount(AccountRequestDTO accountRequestDTO, Long idAccount);
    public void deleteAccount(Long idAccount);

}
