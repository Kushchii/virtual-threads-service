package com.pdp.webflux.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Bean(name = "transactionRestClient")
    public RestClient transactionRestClient(
            @Value("${service.client.transaction.url}") String url,
            @Value("${service.client.transaction.connect-timeout}") Duration connectTimeout,
            @Value("${service.client.transaction.read-timeout}") Duration readTimeout) {
        log.debug("Creating transactionRestClient with URL: {}, connectTimeout: {}, readTimeout: {}", url, connectTimeout, readTimeout);
        return RestClient.builder()
                .baseUrl(url)
                .requestFactory(createFactory(connectTimeout, readTimeout))
                .build();
    }

    private ClientHttpRequestFactory createFactory(Duration connectTimeout, Duration readTimeout) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }
}
