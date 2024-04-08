package com.devsu.operations.utils;

import com.devsu.operations.errorhandler.OperationGenericServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class OperationUtil {

    @Autowired
    private ObjectMapper objectMapper;
    OperationUtil() {}



    public Optional<URI> createURI(String urlAsString) {
        URI uri;
        try {
            uri = new URI(urlAsString);
        } catch (URISyntaxException uriEx) {
            throw new OperationGenericServerException("Error in finance URL");
        }
        return Optional.of(uri);
    }
    public static void printUriRequestJson(String location, String uri, Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(location);
            System.out.println(uri);
            System.out.println(object != null ? objectMapper.writeValueAsString(object) : "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
