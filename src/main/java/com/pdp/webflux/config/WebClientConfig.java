package com.pdp.webflux.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${service.client.transaction.url}") String url) {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(1000)
                .pendingAcquireMaxCount(-1)
                .pendingAcquireTimeout(Duration.ofSeconds(30))
                .maxIdleTime(Duration.ofSeconds(20))
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .resolver(DefaultAddressResolverGroup.INSTANCE)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500);

        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}