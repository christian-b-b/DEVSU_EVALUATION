package com.devsu.finance.controllers;

import com.devsu.finance.dtos.CustomerResponseDTO;
import com.devsu.finance.services.CustomerService;
import com.devsu.finance.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.devsu.finance.constants.FinanceConstants.API_VERSION;
import static com.devsu.finance.constants.FinanceConstants.RESOURCE_CUSTOMER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mock;
    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("Return empty CustomerListResponse when search all customers")
    void returnEmptyCustomerListResponseWhenSearchAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());

        mock.perform(get(API_VERSION.concat(RESOURCE_CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Return CustomerListResponse when search all customers")
    void returnCustomerListResponseWhenSearchAllCustomers() throws Exception {
        List<CustomerResponseDTO> customerList = List.of(TestUtils
                .convertJSONFileToObject("customer/Customer-response-list.json",
                        CustomerResponseDTO[].class));

        when(customerService.getAllCustomers()).thenReturn(customerList);

        mock.perform(get(API_VERSION.concat(RESOURCE_CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(customerList.size()))
                .andExpect(jsonPath("$[0].names").value(customerList.get(0).getNames()));
        verify(customerService, times(1)).getAllCustomers();
    }

}