package com.blue.harvest.customer.management.api.infra.utils;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@Configuration
public class SecurityProblemResponsesConfiguration {

  private static final String HTTP_401 = "http401";
  private static final String HTTP_403 = "http403";
  private static final String HTTP_500 = "http500";
  public static final String UNAUTHORIZED_401_NO_TOKEN_RESPONSE_REF =
      "#/components/responses/" + HTTP_401;
  public static final String INTERNAL_SERVER_ERROR_RESPONSE_REF =
      "#/components/responses/" + HTTP_500;
  public static final String FORBIDDEN_403_RESPONSE_REF = "#/components/responses/" + HTTP_403;

  private static final Problem http401NoTokenProblem;
  private static final Problem http403Problem;
  private static final Problem http500Problem;

  static {
    http401NoTokenProblem = Problem.builder().withTitle(Status.UNAUTHORIZED.getReasonPhrase())
        .withStatus(Status.UNAUTHORIZED)
        .withDetail("Full authentication is required to access this reosurce").build();
    http403Problem        =
        Problem.builder().withTitle(Status.FORBIDDEN.getReasonPhrase()).withStatus(Status.FORBIDDEN)
            .withDetail("Full authentication is required to access this reosurce").build();
    http500Problem        =
        Problem.builder().withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .withDetail("Full authentication is required to access this resource").build();
  }


  @Bean
  public GlobalOpenApiCustomizer registerProblemSchema(
      List<Map.Entry<String, ApiResponse>> responsesToRegister) {

    ResolvedSchema problemSchema =
        ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(Problem.class));
    problemSchema.schema.description("RFC 7807 problem detail , Additional properties may exist");
    problemSchema.schema.getProperties().remove("parameters");
   // problemSchema.schema.getProperties().put("status", PrimitiveType.INT.createProperty());
    RemoveUnwantedInfo(problemSchema);
    ResolvedSchema constraintViolationProblemSchema = ModelConverters.getInstance()
        .resolveAsResolvedSchema(new AnnotatedType(ConstraintViolationProblem.class));
    RemoveUnwantedInfo(constraintViolationProblemSchema);
    ResolvedSchema violationSchema =
        ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(Violation.class));
    RemoveUnwantedInfo(violationSchema);
    return openApi -> {
      openApi.getComponents().addSchemas(Problem.class.getSimpleName(), problemSchema.schema);
      openApi.getComponents().addSchemas(ConstraintViolationProblem.class.getSimpleName(),
          constraintViolationProblemSchema.schema);
      openApi.getComponents().addSchemas(Violation.class.getSimpleName(), violationSchema.schema);
      responsesToRegister.forEach(
          entry -> openApi.getComponents().addResponses(entry.getKey(), entry.getValue()));
    };
  }

  private void RemoveUnwantedInfo(ResolvedSchema resolvedSchema) {
    Map<String, Schema> properties = resolvedSchema.schema.getProperties();
    properties.put("status", PrimitiveType.INT.createProperty());
    properties.remove("cause");
    properties.remove("stackTrace");
    properties.remove("message");
    properties.remove("parameters");
    properties.remove("suppressed");
    properties.remove("localizedMessage");


  }

  @Bean
  public Map.Entry<String, ApiResponse> http403() {
    return simpleResponse(HTTP_403, "Missing Authorities.", http403Problem);
  }

  @Bean
  public Map.Entry<String, ApiResponse> http500() {
    return simpleResponse(HTTP_500, "Internal server error", http500Problem);
  }


  private Map.Entry<String, ApiResponse> simpleResponse(String code, String description,
      Problem exampleProblem) {
    ApiResponse response = new ApiResponse().description(description).content(
        new Content().addMediaType(APPLICATION_PROBLEM_JSON_VALUE,
            new MediaType().example(exampleProblem)
                .schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));
    return new AbstractMap.SimpleEntry<>(code, response);
  }

  private Map.Entry<String, ApiResponse> simpleResponse(String code, String description,
      ExampleProblem... exampleProblem) {
    Map<String, Example> examples = Arrays.stream(exampleProblem).map(
            example -> new Example().summary(example.summary).description(example.description)
                .value(example.example))
        .collect(Collectors.toMap(Example::getSummary, Function.identity()));

    ApiResponse response = new ApiResponse().description(description).content(
        new Content().addMediaType(APPLICATION_PROBLEM_JSON_VALUE, new MediaType().example(examples)
            .schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));
    return new AbstractMap.SimpleEntry<>(code, response);
  }

  @Bean
  public Map.Entry<String, ApiResponse> http401() {
    return simpleResponse(HTTP_401, "Invalid Authentication.",
        new ExampleProblem("no_token", "Authenticaion token not provide", http401NoTokenProblem),
        new ExampleProblem("bad_token", "Authenticaion token not provide", http403Problem));
  }

  @AllArgsConstructor
  @Value
  private static class ExampleProblem {
    String summary;
    String description;
    Problem example;
  }

  @Bean
  public Map.Entry<String, ApiResponse> http403Example() throws IOException {
    return simpleResponse(HTTP_403, "Forbidden");
  }

  private Map.Entry<String, ApiResponse> simpleResponse(String code, String description)
      throws IOException {
    ApiResponse response = new ApiResponse().description(description).content(
        new Content().addMediaType(APPLICATION_PROBLEM_JSON_VALUE,
            new MediaType().schema(new Schema<Problem>().$ref("#/components/schemas/Problem"))));
    return new AbstractMap.SimpleEntry<>(code, response);
  }
}
