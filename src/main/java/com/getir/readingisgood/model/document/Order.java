package com.getir.readingisgood.model.document;

import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@Document(collection = "Order")
@NoArgsConstructor
public class Order extends AbstractAuditableDocument {

  @Id
  private String id;

  private LocalDateTime orderDate;

  private Double purchasedAmount;

  private List<OrderDetail> orderDetailList;

}
