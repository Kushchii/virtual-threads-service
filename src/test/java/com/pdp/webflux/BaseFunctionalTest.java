package com.pdp.webflux;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.pdp.webflux.persistent.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TransactionApplication.class},
        properties = "spring.main.allow-bean-definition-overriding=true")
@DirtiesContext
@Tag("FunctionalTest")
public abstract class BaseFunctionalTest {

    private static final String JDBC_PREFIX = "jdbc";
    private static final String R2DBC_PREFIX = "r2dbc";

    private static final PostgreSQLContainer PSQL_CONTAINER = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withUsername("root")
            .withPassword("password")
            .withDatabaseName("redis_service")
            .withExposedPorts(5432);

    private static final WireMockServer MOCK_SERVER = new WireMockServer(WireMockConfiguration.wireMockConfig().port(9900));

    @Autowired
    protected TransactionRepository transactionRepository;

    @DynamicPropertySource
    protected static void registerMockServer(DynamicPropertyRegistry registry) {
        MOCK_SERVER.start();
        PSQL_CONTAINER.start();
        registry.add("spring.liquibase.url", PSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.r2dbc.url", () -> PSQL_CONTAINER.getJdbcUrl().replace(JDBC_PREFIX, R2DBC_PREFIX));
        registry.add("spring.r2dbc.username", PSQL_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", PSQL_CONTAINER::getPassword);
    }

    @BeforeEach
    void setup() {
        MOCK_SERVER.resetAll();
        transactionRepository.deleteAll();
    }

    protected StubMapping mockPost(String path, String requestBody, String responseBody, HttpStatusCode statusCode) {
        return MOCK_SERVER.stubFor(WireMock.post(path)
                .withRequestBody(WireMock.equalToJson(requestBody))
                .willReturn(WireMock.aResponse()
                        .withStatus(statusCode.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody)));
    }

    protected void mockPaymentSystem(String path, String request, String response, HttpStatus status) {
        mockPost(path,
                readAsString("/transaction/" + request),
                readAsString("/transaction/" + response),
                status
        );
    }

    protected String readAsString(String file) {
        try {
            return new String(Files.readAllBytes(new File("src/test/resources/" + file + ".json").toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
