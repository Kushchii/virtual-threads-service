package com.pdp.webflux.service;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import com.pdp.webflux.mapper.TransactionMapper;
import com.pdp.webflux.persistent.postgres.entity.TransactionsEntity;
import com.pdp.webflux.persistent.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final long PROCESSING_DELAY_SECONDS = 2;

    private final TransactionMapper transactionMapper;

    private final TransactionRepository transactionRepository;

    private final WebClient webClient;

    @Override
    public Mono<TransactionsResponse> transactions(TransactionsRequest request) {
        return processTransactionAndSave(request)
                .then(sendPostRequest(request));
    }

    private Mono<TransactionsEntity> processTransactionAndSave(TransactionsRequest request) {
        TransactionsEntity entity = transactionMapper.toEntity(request);
        return Mono.delay(Duration.ofSeconds(PROCESSING_DELAY_SECONDS))
                .then(transactionRepository.save(entity))
                .doOnSuccess(savedEntity -> log.info("Transaction entity saved: {}", savedEntity.getTransactionId()));
    }

    private Mono<TransactionsResponse> sendPostRequest(TransactionsRequest request) {
        return webClient.post()
                .uri("/process-payment")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionsResponse.class)
                .doOnNext(response -> log.info("External service response: {}", response))
                .doOnError(error -> log.error("Error during external POST call", error));
    }
}
