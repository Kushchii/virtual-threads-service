package com.pdp.webflux.persistent.repository;

import com.pdp.webflux.persistent.postgres.entity.TransactionsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionRepository extends R2dbcRepository<TransactionsEntity, Long> {

}

