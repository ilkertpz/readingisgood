package com.getir.readingisgood.order;

import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.domain.customer.CustomerService;
import com.getir.readingisgood.domain.order.OrderRepository;
import com.getir.readingisgood.domain.order.OrderService;
import com.getir.readingisgood.model.mapper.OrderDetailMapperImpl;
import com.getir.readingisgood.model.mapper.OrderMapperImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * orderDetailMapper could not be injected because of mapstruct pattern. It is a bit tricky
 * Hence could not complete here because of limited time
 */
@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

  @InjectMocks
  OrderService service;

  @Mock
  OrderRepository orderRepository;

  @Mock
  OrderMapperImpl orderMapper;

  @Mock
  OrderDetailMapperImpl orderDetailMapper;

  @Mock
  BookService bookService;

  @Mock
  CustomerService customerService;



}