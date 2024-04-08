package com.devsu.finance.services.impl;

import com.devsu.finance.constants.FinanceConstants;
import com.devsu.finance.dtos.CustomerResponseDTO;
import com.devsu.finance.dtos.request.CustomerRequestDTO;
import com.devsu.finance.errorhandler.FinanceGenericClientException;
import com.devsu.finance.models.*;
import com.devsu.finance.repositories.CustomerRepository;
import com.devsu.finance.repositories.MasterRepository;
import com.devsu.finance.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll().stream().map(customer->
                CustomerResponseDTO.builder()
                        .idCustomer(customer.getIdPerson())
                        .names(customer.getNames())
                        .address(customer.getAddress())
                        .cellphone(customer.getCellphone())
                        .password(customer.getPassword())
                        .state(customer.getState()== FinanceConstants.ACTIVE_STATE ? true:false).build()
        ).collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new FinanceGenericClientException("customer not found", HttpStatus.NOT_FOUND,null,null));
        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .idCustomer(customer.getIdPerson())
                .names(customer.getNames() +" "+ customer.getFirstLastName())
                .address(customer.getAddress())
                .cellphone(customer.getCellphone())
                .password(customer.getPassword())
                .state(customer.getState()==FinanceConstants.ACTIVE_STATE?true:false).build();
        return customerResponseDTO;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = Customer.builder().build();
        customer.setNames(customerRequestDTO.getNames());
        customer.setFirstLastName(customerRequestDTO.getFirstLastName());
        customer.setSecondLastName(customerRequestDTO.getSecondLastName());
        Master gender =masterRepository.findByParentCodeAndCode(FinanceConstants.PARENT_CODE_GENDER,
                customerRequestDTO.getGenderCode()).orElseThrow(
                ()-> new FinanceGenericClientException("id not found",HttpStatus.NOT_FOUND,null, null));
        customer.setGenderCode(gender.getCode());
        customer.setBirthDate(customerRequestDTO.getBirthDate());
        Master documentType =masterRepository.findByParentCodeAndCode(FinanceConstants.PARENT_CODE_DOCUMENT_TYPE,
                customerRequestDTO.getDocumentTypeCode()).orElseThrow(
                ()-> new FinanceGenericClientException("id not found",HttpStatus.NOT_FOUND,null, null));
        customer.setDocumentTypeCode(documentType.getCode());
        customer.setDocumentNumber(customerRequestDTO.getDocumentNumber());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setCellphone(customerRequestDTO.getCellphone());
        customer.setPassword(customerRequestDTO.getPassword());
        customer.setState(FinanceConstants.ACTIVE_STATE);
        customer.setRegistrationDate(LocalDateTime.now());
        customerRepository.save(customer);

        return CustomerResponseDTO.builder()
                .idCustomer(customer.getIdPerson())
                .names(customer.getNames())
                .address(customer.getAddress())
                .cellphone(customer.getCellphone())
                .password(customer.getPassword())
                .state(customer.getState()==FinanceConstants.ACTIVE_STATE?true:false).build();
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO, Long idCustomer) {
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(
                ()-> new FinanceGenericClientException("Customer not found",HttpStatus.NOT_FOUND,null,null));
        customer.setNames(customerRequestDTO.getNames());
        customer.setFirstLastName(customerRequestDTO.getFirstLastName());
        customer.setSecondLastName(customerRequestDTO.getSecondLastName());
        customer.setGenderCode(customerRequestDTO.getGenderCode());
        customer.setBirthDate(customerRequestDTO.getBirthDate());
        customer.setDocumentTypeCode(customerRequestDTO.getDocumentTypeCode());
        customer.setDocumentNumber(customerRequestDTO.getDocumentNumber());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setCellphone(customerRequestDTO.getCellphone());
        customer.setPassword(customerRequestDTO.getPassword());
        customerRepository.save(customer);

        return CustomerResponseDTO.builder()
                .idCustomer(customer.getIdPerson())
                .names(customer.getNames())
                .address(customer.getAddress())
                .cellphone(customer.getCellphone())
                .password(customer.getPassword())
                .state(customer.getState()==FinanceConstants.ACTIVE_STATE?true:false).build();
    }

    @Override
    public void deleteCustomer(Long idCustomer) {
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(
                ()-> new FinanceGenericClientException("customer not found",HttpStatus.NOT_FOUND,null, null)
        );
        customerRepository.deleteById(idCustomer);
    }
}
