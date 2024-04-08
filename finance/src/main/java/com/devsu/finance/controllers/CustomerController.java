package com.devsu.finance.controllers;

import com.devsu.finance.constants.FinanceConstants;
import com.devsu.finance.dtos.CustomerResponseDTO;
import com.devsu.finance.dtos.request.CustomerRequestDTO;
import com.devsu.finance.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FinanceConstants.API_VERSION + FinanceConstants.RESOURCE_CUSTOMER)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomById(@PathVariable Long id){
        CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(id);
        return new ResponseEntity<>(customerResponseDTO,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(customerRequestDTO);
        return new ResponseEntity<>(customerResponseDTO,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO){
        CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(customerRequestDTO,id);
        return new ResponseEntity<>(customerResponseDTO,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
