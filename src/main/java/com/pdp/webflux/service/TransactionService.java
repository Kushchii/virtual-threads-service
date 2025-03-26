package com.pdp.webflux.service;

import com.pdp.webflux.api.request.TransactionsRequest;
import com.pdp.webflux.api.response.TransactionsResponse;
import com.pdp.webflux.client.TransactionClient;
import com.pdp.webflux.client.dto.ClientRequest;
import com.pdp.webflux.mapper.TransactionMapper;
import com.pdp.webflux.persistent.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final TransactionClient client;

    @Transactional
    public TransactionsResponse processTransaction(TransactionsRequest request) {
        log.info("Processing transaction for user: {}", request.getUserId());

        var entity = mapper.toEntity(request);
        var savedEntity = repository.save(entity);
        var internalRequest = new ClientRequest(savedEntity.getStatus());
        var internalResponse = client.sendPayment(internalRequest);

        return new TransactionsResponse(internalResponse.getMessage());
    }
}