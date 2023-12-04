package com.blue.harvest.customer.management.api.infra;

import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.infra.dto.AccountsDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerDto;
import com.blue.harvest.customer.management.api.infra.jpa.repository.CustomerDao;
import com.blue.harvest.customer.management.api.infra.resource.CustomerResource;
import com.blue.harvest.customer.management.api.mapper.CustomerDtoMapper;
import com.blue.harvest.customer.management.api.service.CustomerService;
import com.blue.harvest.customer.management.api.service.CustomerTransactionService;
import com.blue.harvest.customer.management.api.service.TransactionResponse;
import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionCreationResponse;
import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionInfo;
import com.blue.harvest.transactions.management.client.service.TransactionAccessDao;
import com.blue.harvest.transactions.management.client.service.TransactionClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerResource.class)
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IntegrationTestsCustomerResource {
  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;
  @SpyBean CustomerService customerService;
  @MockBean CustomerDao customerDao;
  @SpyBean CustomerDtoMapper customerDtoMapper;
  @SpyBean CustomerTransactionService customerTransactionService;
  @MockBean TransactionClientService transactionClientService;
  @MockBean TransactionAccessDao accessDao;

  @Test
  void create_new_account_and_initiate_transaction_for_existing_customer() throws Exception {

    final TransactionCreationResponse creationResponse = new TransactionCreationResponse();
    final TransactionInfo             transactionInfo  = new TransactionInfo();
    transactionInfo.setTransactionCreationDate(LocalDate.now());
    transactionInfo.setAccountNumberTarget("targetAccountNumber");
    transactionInfo.setInitialCredit(BigDecimal.ONE);
    creationResponse.addTransactionInfosItem(transactionInfo);
    creationResponse.setCountOfSuccess(1);

    when(customerDao.getCustomerInfo(any(String.class))).thenReturn(
        CustomerDomain.builder().withNoOfValidAccounts(2).withIdCustomer("Identifier").build());

    when(transactionClientService.createNewTransaction(any())).thenReturn(
        CompletableFuture.completedFuture(creationResponse));

    final MvcResult mvcResult = mockMvc.perform(post("/api/v1/customers/createAccount").with(
                jwt().authorities(List.of(new SimpleGrantedAuthority("admin"),
                        new SimpleGrantedAuthority("ROLE_AUTHORIZED_PERSONNEL")))
                    .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy")))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(buildCustomerDetails())))
        .andExpect(status().isCreated()).andReturn();
    final String responseDto = mvcResult.getResponse().getContentAsString();

    assertThat(responseDto.contains("Success"));
  }

  @Test
  void create_new_account_should_throw_forbidden() throws Exception {
    when(customerDao.getCustomerInfo(any(String.class))).thenReturn(
        CustomerDomain.builder().build());
    doReturn(CompletableFuture.completedFuture(List.of(
        TransactionResponse.builder().withAccountNumberTarget("target")
            .withAccountNumberSource("source").withAmount(BigDecimal.ONE)
            .withCustomerAccountIdentifier("Identifier").build()))).when(transactionClientService)
        .createNewTransaction(any());
    mockMvc.perform(post("/api/v1/customers/createAccount").with(anonymous())
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(buildCustomerDetails())))
        .andExpect(status().isForbidden()).andReturn();
  }

  @Test
  void create_new_account_should_throw_not_authorized() throws Exception {
    when(customerDao.getCustomerInfo(any(String.class))).thenReturn(
        CustomerDomain.builder().build());
    doReturn(CompletableFuture.completedFuture(List.of(
        TransactionResponse.builder().withAccountNumberTarget("target")
            .withAccountNumberSource("source").withAmount(BigDecimal.ONE)
            .withCustomerAccountIdentifier("Identifier").build()))).when(transactionClientService)
        .createNewTransaction(any());
    mockMvc.perform(post("/api/v1/customers/createAccount").contentType(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(buildCustomerDetails())))
        .andExpect(status().isUnauthorized()).andReturn();
  }

  public static CustomerDto buildCustomerDetails() {
    return CustomerDto.builder().withCustomerIdentifier("35345455435435").withCurrency("EUR")
        .withListOfAccounts(List.of(AccountsDto.builder().build())).withIdentifier("Identifier").withFirstName("firstName")
        .withLastName("lastname").build();
  }

}
