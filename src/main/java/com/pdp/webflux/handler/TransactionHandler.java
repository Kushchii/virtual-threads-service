package com.pdp.webflux.handler;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Validated
@RequiredArgsConstructor
public class TransactionHandler extends BaseHandler {

    private final TransactionService transactionService;

    public Mono<ServerResponse> transactions(ServerRequest request) {
        log.info("Transaction request received ");
        return request.bodyToMono(TransactionsRequest.class)
                .flatMap(transactionService::transactions)
                .flatMap(it -> toServerResponse(HttpStatus.OK, it));
    }
}
