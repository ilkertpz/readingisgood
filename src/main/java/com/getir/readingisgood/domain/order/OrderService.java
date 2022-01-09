package com.getir.readingisgood.domain.order;

import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.domain.customer.CustomerService;
import com.getir.readingisgood.model.constants.ExceptionMessageConstants;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.mapper.OrderMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final CustomerService customerService;
  private final BookService bookService;


  public List<OrderDTO> findAll() {
    return orderMapper.toDTOList(orderRepository.findAll());
  }

  @Transactional
  public OrderDTO create(OrderDTO orderDTO) {
    bookService.checkStockAmount(orderDTO.getOrderDetailList());

    Double totalPrice = getTotalPrice(orderDTO);
    orderDTO.setPurchasedAmount(totalPrice);
    orderDTO.setOrderDate(LocalDateTime.now());

    var order = orderRepository.save(orderMapper.toEntity(orderDTO));
    customerService.updateCustomerOrderList(orderMapper.toDTO(order));
    return orderMapper.toDTO(order);
  }

  public OrderDTO findById(String id) {
    var orderOpt = orderRepository.findById(id);
    return orderOpt.map(orderMapper::toDTO).orElseThrow(
        () -> new GeneralException(ExceptionMessageConstants.ORDER_COULD_NOT_BE_FOUND, HttpStatus.UNPROCESSABLE_ENTITY));
  }

  @Transactional(readOnly = true)
  public List<OrderDTO> findByOrderDate(String startDate, String endDate) {
    var orderList = orderRepository.findByOrderDateBetween(LocalDateTime.parse(startDate),
        LocalDateTime.parse(endDate));
    return orderMapper.toDTOList(orderList);

  }

  //private
  private Double getTotalPrice(OrderDTO orderDTO) {
    var orderDetailList = orderDTO.getOrderDetailList();
    return orderDetailList.stream()
        .mapToDouble(orderDetail ->  {
          var bookDTO = bookService.updateCount(orderDetail.getBookId(), orderDetail.getAmount());
          return orderDetail.getAmount()*bookDTO.getPrice();
        }).sum();
  }
}
