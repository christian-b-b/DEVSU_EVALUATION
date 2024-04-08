package com.devsu.operations.repositories;

import com.devsu.operations.models.Customer;

import java.util.Optional;

public interface CustomerRepository {
    public Optional<Customer> getCustomertById(Long id);
}
