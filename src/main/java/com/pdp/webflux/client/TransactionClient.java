package com.pdp.webflux.client;


import com.pdp.webflux.client.dto.ClientRequest;
import com.pdp.webflux.client.dto.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class TransactionClient {

    private final RestClient client;

    public TransactionClient(@Qualifier("transactionRestClient") RestClient client) {
        this.client = client;
    }

    public ClientResponse sendPayment(ClientRequest request) {
        return client.post()
                .uri("/process-payment")
                .body(request)
                .retrieve()
                .body(ClientResponse.class);
    }
}
