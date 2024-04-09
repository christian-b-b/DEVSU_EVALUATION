package com.devsu.finance.dtos;

import com.devsu.finance.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerResponseDTOTest {
    private static CustomerResponseDTO customerResponseDTO;

    @BeforeAll
    static void setUp() throws IOException {
        customerResponseDTO = TestUtils
                .convertJSONFileToObject("customer/Customer.json", CustomerResponseDTO.class);
    }

    @Test
    @DisplayName("Return customer names when invoke getNames")
    void returnCustomerNamesWhenInvokeGetNames() throws IOException {

        assertEquals(customerResponseDTO.getNames(), "Christian Baldeón");
    }

    @Test
    @DisplayName("Set customer CellPhone when invoke setCellPhone")
    void setCustomerCellPhoneWhenInvokeSetCellPhone() {
        String expectedCellPhoneNumber = "956789333";
        customerResponseDTO.setCellphone(expectedCellPhoneNumber);
        assertEquals(customerResponseDTO.getCellphone(), expectedCellPhoneNumber);
    }

    @Test
    @DisplayName("Create CustomerResponseDTO when use Builder")
    void createCustomerResponseDTOWhenUseBuilder() {
        CustomerResponseDTO customerTest = CustomerResponseDTO.builder()
                .names("Paul")
                .address("Jr. Gladiolos 785")
                .cellphone("987456897")
                .password("pssWt568")
                .state(true).build();

        assertEquals(customerTest.getNames(), "Paul");
    }

}