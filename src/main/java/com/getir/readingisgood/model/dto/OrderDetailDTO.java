package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrderDetailDTO extends AbstractAuditableDocument {

  @NotNull(message = "order.validation.book.can.not.be.null")
  private String bookId;

  @NotNull(message = "order.validation.book.amount.can.not.be.null")
  private Integer amount;

}
