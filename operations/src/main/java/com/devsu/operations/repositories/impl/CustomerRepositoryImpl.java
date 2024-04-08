package com.devsu.operations.repositories.impl;

import com.devsu.operations.constants.OperationEndpointsFinance;
import com.devsu.operations.errorhandler.OperationGenericClientException;
import com.devsu.operations.errorhandler.OperationGenericServerException;
import com.devsu.operations.models.Customer;
import com.devsu.operations.repositories.CustomerRepository;
import com.devsu.operations.utils.OperationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OperationUtil operationUtil;
    @Override
    public Optional<Customer> getCustomertById(Long id) {
        URI uri = operationUtil.createURI(OperationEndpointsFinance.BASE_URI_CUSTOMER + "/"+id
                ).orElseThrow(
                () -> new OperationGenericServerException("Get Customer in finance failed"));

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
        operationUtil.printUriRequestJson("CustomerRepositoryImpl ==> getCustomertById",uri.toString(),requestEntity);

        try {
            ResponseEntity<Customer> response = restTemplate.exchange(requestEntity, Customer.class);
            return Optional.of(response.getBody());
        }catch (OperationGenericClientException ex){
            if (ex.getHttpStatus().equals(HttpStatus.NOT_FOUND) )
                throw new OperationGenericClientException("id incorrecto", HttpStatus.NOT_FOUND, null, null);
            else throw ex;
        }

    }
}
