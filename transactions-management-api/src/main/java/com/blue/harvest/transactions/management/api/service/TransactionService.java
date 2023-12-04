package com.blue.harvest.transactions.management.api.service;


import com.blue.harvest.transactions.management.api.domain.TransactionDomain;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionCreationResponseDto;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionInfo;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionResponseDto;
import com.blue.harvest.transactions.management.api.infra.exception.TransactionNotCreatedException;
import com.blue.harvest.transactions.management.api.infra.exception.TransactionNotRetrievedException;
import com.blue.harvest.transactions.management.api.infra.jpa.entity.TransactionEntity;
import com.blue.harvest.transactions.management.api.infra.jpa.repository.TransactionDao;
import com.blue.harvest.transactions.management.api.mapper.TransactionDtoMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionService {
  private final TransactionDao transactionDao;
  private final TransactionDtoMapper transactionDtoMapper;

  public TransactionService(final TransactionDao transactionDao,
      final TransactionDtoMapper transactionDtoMapper) {
    this.transactionDao       = transactionDao;
    this.transactionDtoMapper = transactionDtoMapper;
  }

  public TransactionCreationResponseDto createNewTransaction(final TransactionDomain transactionDomain) {
    try {
      final TransactionEntity transactionInfo =
          transactionDao.buildNewTransaction(transactionDomain);
      return TransactionCreationResponseDto.builder().withTransactionInfos(
              Collections.singletonList(transactionDtoMapper.toDtoTransactionInfo(transactionInfo)))
          .withCountOfSuccess(1).build();
    } catch (TransactionNotCreatedException exception) {
      return TransactionCreationResponseDto.builder().withCountOfSuccess(0).build();
    }
  }

  public TransactionResponseDto findByTransactionIdentifier(final String customerIdentifier) {
    List<TransactionInfo> transactionDto;
    try {
      transactionDto = transactionDao.getTransactionInfo(customerIdentifier).stream()
          .map(transactionDtoMapper::toDtoTransactionInfo).toList();
      return TransactionResponseDto.builder().withTransactionInfos(transactionDto)
          .withMessage("Retrieve transaction OK").build();

    } catch (TransactionNotRetrievedException exception) {
      return TransactionResponseDto.builder().withMessage("Transactions not exists").build();
    }
  }

  public TransactionResponseDto findAll() {
    List<TransactionInfo> transactionDto;
    try {
      transactionDto = transactionDao.getAllTransactions().stream()
          .map(transactionDtoMapper::toDtoTransactionInfo).toList();

    } catch (TransactionNotRetrievedException exception) {
      return TransactionResponseDto.builder().withMessage("Transactions not exists").build();
    }
    return TransactionResponseDto.builder().withTransactionInfos(transactionDto)
        .withMessage("Retrieve transaction OK").build();
  }

}
