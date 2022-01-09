package com.getir.readingisgood.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrderDTO extends AbstractAuditableDocument {

  private String id;

  @JsonIgnore
  private LocalDateTime orderDate;

  @JsonIgnore
  private Double purchasedAmount;

  @Valid
  @NotNull(message = "order.validation.book.can.not.be.null")
  private List<OrderDetailDTO> orderDetailList;
}
