package com.blue.harvest.transactions.management.client.infra.config;

import com.blue.harvest.generated.transactions.management.client.ApiClient;
import com.blue.harvest.generated.transactions.management.client.infra.TransactionManagementResourceApi;
import com.blue.harvest.transactions.management.client.utils.WebClientUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.web.reactive.function.client.WebClient;

import static com.blue.harvest.transactions.management.client.infra.config.TransactionApiConfig.BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG;

@Configuration

@ConfigurationProperties(BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG)
public class TransactionApiConfig {
  public static final String BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG =
      "transaction-api-security-config";

  private String basePath = "http://localhost:8086";

  ReactiveClientRegistrationRepository transactionClientRegistrationRepository(
      final ClientRegistrationRepository clientRegistrationRepository) {
    final ClientRegistration clientRegistration =
        clientRegistrationRepository.findByRegistrationId(BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG);
    return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
  }

  public ApiClient transactionClientData(
      final ClientRegistrationRepository clientRegistrationRepository) {
    final ReactiveClientRegistrationRepository repository =
        transactionClientRegistrationRepository(clientRegistrationRepository);
    final WebClient webClient =
        WebClientUtils.createWebClient(repository, BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG,
            basePath);
    final ApiClient apiClient = new ApiClient(webClient);
    return apiClient;
  }


  @Bean
  public TransactionManagementResourceApi transactionManagementResourceApi(
      final ClientRegistrationRepository clientRegistrationRepository) {
    return new TransactionManagementResourceApi(
        this.transactionClientData(clientRegistrationRepository));
  }
/*

  @Bean
  public TransactionManagementResourceApi transactionManagementResourceApi() {
    return new TransactionManagementResourceApi(
        this.transactionClientData());
  }

  public ClientRegistrationRepository clientRegistrationsRepository() {
    ClientRegistration registration = ClientRegistration
        .withRegistrationId(BLUE_HARVEST_TRANSACTION_CLIENT_CONFIG)
        .tokenUri("http://localhost:8080/realms/blue-harvest-oauth2/protocol/openid-connect/token")
        .clientId("user_consult")
        .clientSecret("1234")
        .authorizationGrantType(CLIENT_CREDENTIALS)
        .scope(Collections.singletonList("infra.blue-harvest-transaction-info.v1"))
        .build();
    return new InMemoryClientRegistrationRepository(registration);
  }
*/

}
