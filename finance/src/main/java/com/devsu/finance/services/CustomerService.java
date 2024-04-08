package com.devsu.finance.services;

import com.devsu.finance.dtos.CustomerResponseDTO;
import com.devsu.finance.dtos.request.CustomerRequestDTO;
import java.util.List;



public interface CustomerService {
    public List<CustomerResponseDTO> getAllCustomers();
    public CustomerResponseDTO getCustomerById(Long id);
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO,Long idCustomer);
    public void deleteCustomer(Long idCustomer);

}
