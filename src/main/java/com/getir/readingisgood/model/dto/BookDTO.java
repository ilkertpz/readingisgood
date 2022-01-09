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
public class BookDTO extends AbstractAuditableDocument {

  private String id;

  @NotNull(message = "book.validation.name.can.not.be.null")
  private String name;

  private Double price;

  @NotNull(message = "book.validation.amount.can.not.be.null")
  private Integer stockAmount;

  long version;
}
