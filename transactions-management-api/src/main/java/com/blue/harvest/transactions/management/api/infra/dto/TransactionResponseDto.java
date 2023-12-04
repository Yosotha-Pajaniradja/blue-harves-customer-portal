package com.blue.harvest.transactions.management.api.infra.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TransactionResponse", description = "Represents the transaction referential info")
public class TransactionResponseDto implements Serializable {

 private List<TransactionInfo> transactionInfos;
 private String message ;

}
