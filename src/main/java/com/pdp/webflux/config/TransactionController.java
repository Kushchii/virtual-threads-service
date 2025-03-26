package com.pdp.webflux.config;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import com.pdp.webflux.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionsResponse> handleTransaction(@RequestBody TransactionsRequest request) {
        log.info("Received transaction request for user: {}", request.getUserId());
        var response = service.processTransaction(request);
        return ResponseEntity.ok(response);
    }
}
