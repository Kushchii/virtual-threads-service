package com.pdp.webflux;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static com.pdp.webflux.BaseTest.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VirtualThreadsFunctionalTest extends BaseFunctionalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final UUID TRANSACTION_ID = UUID.fromString("221dc9a8-81e7-4bee-afc8-3cd83aae580d");
    private static final String STATUS_SUCCESS = "success";

    private TransactionsRequest createTransactionRequest() {
        var request = random(TransactionsRequest.class);
        request.setId(TRANSACTION_ID);
        request.setStatus(STATUS_SUCCESS);
        return request;
    }

    @Test
    @DisplayName("Successful transaction")
    void shouldProcessTransactionSuccessfully() {
        var transactionsRequest = createTransactionRequest();
        var expectedResponse = new TransactionsResponse("Transaction processed successfully");

        mockPaymentSystem("/process-payment", "request", "response", HttpStatus.OK);

        var headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        var entity = new HttpEntity<>(transactionsRequest, headers);

        var response = restTemplate.exchange(
                "/api/transactions",
                HttpMethod.POST,
                entity,
                TransactionsResponse.class
        );

        assertEquals(expectedResponse, response.getBody());
    }
}
