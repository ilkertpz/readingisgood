package com.getir.readingisgood.domain.statistics;

import com.getir.readingisgood.domain.order.OrderService;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.dto.OrderDetailDTO;
import com.getir.readingisgood.model.dto.StatisticDTO;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {

   private final OrderService orderService;


   public List<StatisticDTO> getStatistics(){

     List<StatisticDTO> resultDTO = new ArrayList<>();

     var allOrderList = orderService.findAll();

     Map<Integer, List<OrderDTO>> groupedOrderList = allOrderList.parallelStream()
         .collect(Collectors.groupingBy(orderDTO -> orderDTO.getOrderDate().getMonthOfYear()));

     composeResult(resultDTO, groupedOrderList);

     return resultDTO;

   }

   //private methods
  /**
   *
   * @param resultDTO pass-by-reference
   * @param groupedOrderList
   */
  private void composeResult(List<StatisticDTO> resultDTO, Map<Integer, List<OrderDTO>> groupedOrderList) {
    groupedOrderList.forEach((month, orderDTOList) -> {
       int totalOrderCount = orderDTOList.size();

      int totalBookCount = orderDTOList.stream()
          .flatMap(orderDTO -> orderDTO.getOrderDetailList().stream())
          .mapToInt(OrderDetailDTO::getAmount)
          .sum();

       Double totalPurchasedAmount = orderDTOList.stream()
           .mapToDouble(OrderDTO::getPurchasedAmount)
           .sum();

      String monthValue = new DateFormatSymbols().getMonths()[month-1];

      var statisticDTO = StatisticDTO.builder()
          .month(monthValue)
          .totalOrderCount(totalOrderCount)
          .totalBookCount(totalBookCount)
          .totalPurchaseAmount(totalPurchasedAmount).build();

      resultDTO.add(statisticDTO);

    });
  }


}
