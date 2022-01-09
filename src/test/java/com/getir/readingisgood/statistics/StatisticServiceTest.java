package com.getir.readingisgood.statistics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.getir.readingisgood.domain.order.OrderService;
import com.getir.readingisgood.domain.statistics.StatisticService;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.dto.OrderDetailDTO;
import java.util.Collections;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class StatisticServiceTest {

  @InjectMocks
  StatisticService service;

  @Mock
  OrderService orderService;


  @Test
  public void getStatisticsTest(){
    when(orderService.findAll()).thenReturn(getSampleOrderList());

    var result = service.getStatistics();

    assertNotNull(result);
  }

  //private methods
  private List<OrderDTO> getSampleOrderList() {
    var orderDetail = OrderDetailDTO.builder()
        .bookId("111")
        .amount(3)
        .build();

    var orderDTO = OrderDTO.builder().id("111")
        .orderDate(LocalDateTime.now())
        .purchasedAmount(50.0)
        .orderDetailList(Collections.singletonList(orderDetail))
        .build();

    return Collections.singletonList(orderDTO);

  }
}