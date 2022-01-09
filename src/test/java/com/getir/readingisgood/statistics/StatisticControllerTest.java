package com.getir.readingisgood.statistics;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.getir.readingisgood.domain.order.OrderController;
import com.getir.readingisgood.domain.order.OrderService;
import com.getir.readingisgood.domain.statistics.StatisticController;
import com.getir.readingisgood.domain.statistics.StatisticService;
import com.getir.readingisgood.infrastructure.test.AbstractControllerStandaloneTest;
import com.getir.readingisgood.model.constants.URLConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;


public class StatisticControllerTest extends AbstractControllerStandaloneTest {

  @InjectMocks
  private StatisticController controller;

  @Mock
  StatisticService statisticService;

  @BeforeEach
  private void setup() {
    this.setup(controller);
  }

  @Test
  public void getStatisticTest() throws Exception {
    this.mockMvc
        .perform(get(URLConstants.STATISTIC + URLConstants.STATISTIC_GET, 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

}