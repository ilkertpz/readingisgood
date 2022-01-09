package com.getir.readingisgood.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.getir.readingisgood.domain.customer.CustomerRepository;
import com.getir.readingisgood.domain.order.OrderRepository;
import com.getir.readingisgood.model.constants.Role;
import com.getir.readingisgood.model.document.Customer;
import java.util.Collections;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryTest {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderRepositoryTest(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Test
  @Order(1)
  public void createTest() {
    var saved = orderRepository.save(getSampleOrder());
    Assertions.assertEquals(saved.getId(), "111");
  }

  @Test
  @Order(2)
  public void findByOrderDateBetween() {
    var startDate = LocalDateTime.parse("2022-01-01T17:48:23.558");
    var endDate = LocalDateTime.parse("2022-01-30T17:48:23.558");

    var result = orderRepository.findByOrderDateBetween(startDate, endDate);
    Assertions.assertEquals(result.size(), 1);
  }

  //private methods
  private com.getir.readingisgood.model.document.Order getSampleOrder() {

    return com.getir.readingisgood.model.document.Order.builder().id("111")
        .orderDate(LocalDateTime.now())
        .purchasedAmount(50.0)
        .build();

  }

}
