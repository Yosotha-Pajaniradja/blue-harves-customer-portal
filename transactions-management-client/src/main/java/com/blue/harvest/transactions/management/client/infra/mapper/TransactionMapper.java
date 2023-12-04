package com.blue.harvest.transactions.management.client.infra.mapper;


import com.blue.harvest.generated.transactions.management.client.infra.model.Transaction;
import com.blue.harvest.transactions.management.client.domain.TransactionCreation;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
 public Transaction fromDomainToInfra(TransactionCreation creation)
 {
   Transaction transaction = new Transaction();
   transaction.setAccountNumberSource(creation.getAccountNumberSource());
   transaction.setAccountNumberTarget(creation.getAccountNumberTarget());
   transaction.setInitialCredit(creation.getInitialCredit());
   transaction.setCustomerAccountIdentifier(creation.getCustomerAccountIdentifier());
   return transaction;
 }
}
