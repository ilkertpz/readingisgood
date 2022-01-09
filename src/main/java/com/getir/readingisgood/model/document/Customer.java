package com.getir.readingisgood.model.document;

import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import com.getir.readingisgood.model.constants.Role;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@Document(collection = "Customer")
@NoArgsConstructor
public class Customer extends AbstractAuditableDocument {

  @Id
  private String id;

  private String username;

  private String email;

  private String password;

  private Role role;

  @DBRef
  private List<Order> orderList;

  //Created for pagination process
  private Integer orderCount=0;

}
