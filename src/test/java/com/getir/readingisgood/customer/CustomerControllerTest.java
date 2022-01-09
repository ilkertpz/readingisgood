package com.getir.readingisgood.customer;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.getir.readingisgood.domain.book.BookController;
import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.domain.customer.CustomerController;
import com.getir.readingisgood.domain.customer.CustomerService;
import com.getir.readingisgood.infrastructure.test.AbstractControllerStandaloneTest;
import com.getir.readingisgood.model.constants.URLConstants;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;


public class CustomerControllerTest extends AbstractControllerStandaloneTest {

  @InjectMocks
  private CustomerController controller;

  @Mock
  private CustomerService service;

  @BeforeEach
  private void setup() {
    this.setup(controller);
  }

  @Test
  public void signUpTest() throws Exception {
    var content = "{\n"
        + "  \"email\": \"mail\",\n"
        + "  \"password\": \"111\"\n"
        + "}";
    this.mockMvc
        .perform(post(URLConstants.CUSTOMER + URLConstants.CUSTOMER_SIGN_UP)
            .content(content)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void loginTest() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.CUSTOMER + URLConstants.CUSTOMER_LOGIN+"?Username=dd&Password=11212", 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void getCustomersOrdersWithPaging() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.CUSTOMER + URLConstants.CUSTOMER_ORDERS_NON_PAGING+"?page=0&size=10", 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void getCustomersOrders() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.CUSTOMER + URLConstants.CUSTOMER_ORDERS_NON_PAGING, 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

}