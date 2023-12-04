package com.blue.harvest.transactions.management.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebClientUtils {

  public static WebClient createWebClient(final ReactiveClientRegistrationRepository repository,
      final String clientRegistrationID, final String basePath) {
    return createTransactionWebClient(repository, clientRegistrationID, basePath,
        new JsonMapper());
  }

  public static WebClient createTransactionWebClient(
      final ReactiveClientRegistrationRepository repository, final String clientRegistrationId,
      final String basePath, final ObjectMapper objectMapper) {
    final InMemoryReactiveOAuth2AuthorizedClientService clientService =
        new InMemoryReactiveOAuth2AuthorizedClientService(repository);
    final AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager auth2AuthorizedClientManager =
                                                                       new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(repository, clientService);

    final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
        new ServerOAuth2AuthorizedClientExchangeFilterFunction(auth2AuthorizedClientManager);
    oauth.setDefaultClientRegistrationId(clientRegistrationId);
    final Jackson2JsonEncoder jackson2JsonEncoder =
        new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_PROBLEM_JSON);
    final Jackson2JsonDecoder jackson2JsonDecoder =
        new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_PROBLEM_JSON);
    return WebClient.builder().
            //filter(oauth).
            filter(handleResponseError()).baseUrl(basePath)
        .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
          clientCodecConfigurer.registerDefaults(false);
          clientCodecConfigurer.customCodecs().register(jackson2JsonEncoder);
          clientCodecConfigurer.customCodecs().register(jackson2JsonDecoder);
        }).build()).build();

  }

  private static ServerOAuth2AuthorizedClientExchangeFilterFunction getoAuth2AuthorizedClientExchangeFilterFunction(
      ReactiveClientRegistrationRepository repository, String clientRegistrationId) {
    InMemoryReactiveOAuth2AuthorizedClientService clientService =
        new InMemoryReactiveOAuth2AuthorizedClientService(repository);
    AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager auth2AuthorizedClientManager =
        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(repository, clientService);
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
        new ServerOAuth2AuthorizedClientExchangeFilterFunction(auth2AuthorizedClientManager);
    oauth.setDefaultClientRegistrationId(clientRegistrationId);
    return oauth;
  }

  private WebClientUtils() {
  }

  public static ExchangeFilterFunction handleResponseError() {
    return ExchangeFilterFunction.ofResponseProcessor(
        clientResponse -> clientResponse.statusCode().isError() ?
            clientResponse.bodyToMono(Object.class).flatMap(rawResponse -> {
              String response = JsonUtils.fromObjectToString(rawResponse);
              assert response != null;
              return Mono.error(
                  WebClientResponseException.create(clientResponse.statusCode().value(),
                      clientResponse.statusCode().toString(),
                      clientResponse.headers().asHttpHeaders(),
                      response.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            }) :
            Mono.just(clientResponse));
  }
}
