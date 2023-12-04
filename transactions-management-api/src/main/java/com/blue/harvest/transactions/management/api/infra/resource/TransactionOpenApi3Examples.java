package com.blue.harvest.transactions.management.api.infra.resource;


import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.Problem;

import java.util.AbstractMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@Configuration
public class TransactionOpenApi3Examples {

  public static final String COMPONENTS_EXAMPLES = "#/components/examples/";
  public static final String TRANSACTION_BAD_REQUEST = "http400BadRequestExample";

  public static final String TRANSACTION_HTTP_BAD_REQUEST_EXAMPLE =
      COMPONENTS_EXAMPLES + TRANSACTION_BAD_REQUEST;

  @Bean
  public Map.Entry<String, Example> http400BadRequestExample() {
    final Example http400Example = new Example();
    final Map.Entry<String, Example> entry =
        new AbstractMap.SimpleEntry<>(TRANSACTION_HTTP_BAD_REQUEST_EXAMPLE, http400Example);
    final Problem badRequest = Problem.builder().withTitle("Bad Request").build();
    http400Example.setValue(badRequest);
    //return simpleResponse(TRANSACTION_HTTP_BAD_REQUEST_EXAMPLE, "Internal server error",
        //badRequest);
    return entry;
  }


  private Map.Entry<String, ApiResponse> simpleResponse(String code, String description,
      Problem exampleProblem) {
    ApiResponse response = new ApiResponse().description(description).content(
        new Content().addMediaType(APPLICATION_PROBLEM_JSON_VALUE,
            new MediaType().example(exampleProblem)
                .schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));
    return new AbstractMap.SimpleEntry<>(code, response);
  }

}
