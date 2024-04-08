package com.devsu.operations.errorhandler.rest;


import com.devsu.operations.errorhandler.OperationGenericClientException;
import com.devsu.operations.errorhandler.OperationGenericServerException;
import com.microsoft.applicationinsights.core.dependencies.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return (
        response.getStatusCode().series() == CLIENT_ERROR
            || response.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
    if(response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR){
      log.error("Server error on provider site path: " + url.getPath());
      throw new OperationGenericServerException("Server error on provider site path: " + url.getPath());
    } else if(response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR){
      log.error("Client error on provider site path: " + url.getPath());
      throw new OperationGenericClientException("Client error on provider site path: " + url.getPath(),
          response.getStatusCode(), ByteStreams.toByteArray(response.getBody()),null);
    }
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    StringBuilder inputStringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(),
        "UTF-8"));
    String line = bufferedReader.readLine();
    while (line != null) {
      inputStringBuilder.append(line);
      inputStringBuilder.append('\n');
      line = bufferedReader.readLine();
    }
    log.error(line);
    if(response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR){
      throw new OperationGenericServerException("Internal server error on provider site");
    }else if(response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR){
      throw new OperationGenericClientException("Client error on provider site",
          response.getStatusCode(), ByteStreams.toByteArray(response.getBody()),null);
    }
  }

}
