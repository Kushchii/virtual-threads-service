package com.pdp.webflux.persistent.repository;

import com.pdp.webflux.persistent.postgres.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Long> {
}

