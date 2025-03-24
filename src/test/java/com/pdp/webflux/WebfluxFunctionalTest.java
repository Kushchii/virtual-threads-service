package com.pdp.webflux;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.pdp.webflux.BaseTest.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WebfluxFunctionalTest extends BaseFunctionalTest {

    private static final UUID TRANSACTION_ID = UUID.fromString("221dc9a8-81e7-4bee-afc8-3cd83aae580d");

    private TransactionsRequest createTransactionRequest() {
        var request = random(TransactionsRequest.class);
        request.setId(TRANSACTION_ID);
        return request;
    }

    @Test
    @DisplayName("Successful transaction")
    void shouldProcessTransactionSuccessfully() {
        var transactionsRequest = createTransactionRequest();
        var expectedResponse = new TransactionsResponse("Transaction processed successfully");

        var actualResponse = doPost("/api/transactions", transactionsRequest, TransactionsResponse.class);

        assertEquals(expectedResponse, actualResponse);
    }
}
