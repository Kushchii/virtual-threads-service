package com.pdp.webflux.api.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionsRequest {

    private UUID id;

    private String status;

    private String userId;

    private BigDecimal amount;

    private String currency;

    private String description;
}
