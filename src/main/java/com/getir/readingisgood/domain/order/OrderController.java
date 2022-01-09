package com.getir.readingisgood.domain.order;


import com.getir.readingisgood.model.constants.URLConstants;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.response.WrapperListResponse;
import com.getir.readingisgood.model.response.WrapperResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstants.ORDER)
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping(URLConstants.CREATE)
  public ResponseEntity<WrapperResponse<OrderDTO>> create(@Valid @RequestBody OrderDTO orderDTO) {
    var response = orderService.create(orderDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(response));
  }

  @GetMapping(URLConstants.FIND_BY_ID)
  public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable("id") String id) {
    var response = orderService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new WrapperResponse<>(response));
  }

  /**
   * date formats must be yyyy-MM-ddTHH:mm:ss
   * ex: startDate: 2022-01-01T17:48:23.558 , endDate:2022-01-30T17:48:23.558
   * @param startDate :
   * @param endDate
   * @return
   */
  @GetMapping(URLConstants.ORDER_BY_ORDER_DATE)
  public ResponseEntity<WrapperListResponse<OrderDTO>> findByOrderDate(@PathVariable("startDate") String startDate,
      @PathVariable("endDate") String endDate) {
    var response = orderService.findByOrderDate(startDate, endDate);
    return ResponseEntity.status(HttpStatus.OK).body(new WrapperListResponse<>(response));
  }

}
