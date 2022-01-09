package com.getir.readingisgood.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.getir.readingisgood.domain.book.BookRepository;
import com.getir.readingisgood.domain.customer.CustomerRepository;
import com.getir.readingisgood.model.constants.Role;
import com.getir.readingisgood.model.document.Book;
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
public class CustomerRepositoryTest {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerRepositoryTest(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Test
  @Order(1)
  public void createTest() {
    var saved = customerRepository.save(getSampleCustomer());
    Assertions.assertEquals(saved.getId(), "111");
  }

  @Test
  @Order(2)
  public void findByUsernameTest() {
    var found = customerRepository.findByUsername("TEST");
    assertEquals(found.isPresent(), true);
  }

  @Test
  @Order(3)
  public void existsByUsernameTest() {
    var result = customerRepository.existsByUsername("TEST");
    assertEquals(result, true);
  }

  @Test
  @Order(4)
  public void existsByEmail() {
    var result = customerRepository.existsByEmail("mail");
    assertEquals(result, true);
  }

  @Test
  @Order(5)
  public void findByUsernamePagingTest() {
    var found = customerRepository.findByUsername("TEST", 0, 1);
    assertEquals(found.isPresent(), true);
  }

  //private methods
  private Customer getSampleCustomer() {

    var orderDTO = com.getir.readingisgood.model.document.Order.builder().id("111")
        .orderDate(LocalDateTime.now())
        .purchasedAmount(50.0)
        .build();

    var orderList = Collections.singletonList(orderDTO);
    return Customer.builder()
        .id("111")
        .username("TEST")
        .email("mail")
        .role(Role.ROLE_CUSTOMER)
        .orderList(orderList)
        .password("11").build();
  }

}
