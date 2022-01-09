package com.getir.readingisgood.order;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.getir.readingisgood.domain.customer.CustomerController;
import com.getir.readingisgood.domain.customer.CustomerService;
import com.getir.readingisgood.domain.order.OrderController;
import com.getir.readingisgood.domain.order.OrderService;
import com.getir.readingisgood.infrastructure.test.AbstractControllerStandaloneTest;
import com.getir.readingisgood.model.constants.URLConstants;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;


public class OrderControllerTest extends AbstractControllerStandaloneTest {

  @InjectMocks
  private OrderController controller;

  @Mock
  OrderService orderService;

  @BeforeEach
  private void setup() {
    this.setup(controller);
  }

  @Test
  public void createTest() throws Exception {
    var content = "{\n"
        + "  \"orderDetailList\": [\n"
        + "    {\n"
        + "      \"amount\": 0\n"
        + "    }\n"
        + "  ]\n"
        + "}";
    this.mockMvc
        .perform(post(URLConstants.ORDER + URLConstants.CREATE)
            .content(content)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByIdTest() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.ORDER + URLConstants.FIND_BY_ID+"/12312321", 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void findByOrderDateTest() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.ORDER+"/2022-01-01T17:48:23.558/2022-01-30T17:48:23.558", 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

}