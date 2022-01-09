package com.getir.readingisgood.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticDTO extends AbstractAuditableDocument {

  private String month;

  private int totalOrderCount;

  private int totalBookCount;

  private Double totalPurchaseAmount;

}
