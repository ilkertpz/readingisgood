package com.getir.readingisgood.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import com.getir.readingisgood.model.constants.Role;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

@EqualsAndHashCode(of = "id", callSuper = false)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO extends AbstractAuditableDocument {

  private String id;

  @NotNull(message = "customer.validation.username.not.null")
  private String username;

  @NotNull(message = "customer.validation.email.not.null")
  private String email;

  @NotNull(message = "customer.validation.password.not.null")
  private String password;

  @JsonIgnore
  private Role role;

  @JsonIgnore
  private List<OrderDTO> orderList;

  @JsonIgnore
  //Created for pagination process
  private Integer orderCount=0;

  public void addOrder(OrderDTO orderDTO){
    if(CollectionUtils.isEmpty(orderList)){
      orderList = new ArrayList<>();
    }
    orderList.add(orderDTO);
  }

}
