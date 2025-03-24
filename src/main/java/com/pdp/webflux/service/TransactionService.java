package com.pdp.webflux.service;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionsResponse> transactions(TransactionsRequest request);
}
