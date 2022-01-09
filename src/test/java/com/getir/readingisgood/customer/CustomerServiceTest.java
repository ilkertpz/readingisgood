package com.getir.readingisgood.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.getir.readingisgood.domain.book.BookRepository;
import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.domain.customer.CustomerRepository;
import com.getir.readingisgood.domain.customer.CustomerService;
import com.getir.readingisgood.infrastructure.security.JwtTokenProvider;
import com.getir.readingisgood.model.constants.ExceptionMessageConstants;
import com.getir.readingisgood.model.constants.Role;
import com.getir.readingisgood.model.document.Book;
import com.getir.readingisgood.model.document.Customer;
import com.getir.readingisgood.model.document.Order;
import com.getir.readingisgood.model.document.OrderDetail;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.dto.OrderDetailDTO;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.mapper.BookMapperImpl;
import com.getir.readingisgood.model.mapper.CustomerMapperImpl;
import com.getir.readingisgood.model.mapper.OrderDetailMapper;
import com.getir.readingisgood.model.mapper.OrderDetailMapperImpl;
import com.getir.readingisgood.model.mapper.OrderMapper;
import com.getir.readingisgood.model.mapper.OrderMapperImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

  @InjectMocks
  CustomerService service;

  @Mock
  CustomerRepository repository;

  @Mock
  CustomerMapperImpl mapper;

  @Mock
  OrderMapperImpl orderMapper;

  @MockBean
  OrderDetailMapperImpl orderDetailMapper;

  @Mock
  AuthenticationManager authenticationManager;

  @Mock
  JwtTokenProvider jwtTokenProvider;

  @Mock
  PasswordEncoder passwordEncoder;

  @Test
  public void loginTest() {
    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("TEST", "11"))).thenReturn(any());
    when(repository.findByUsername("TEST")).thenReturn(Optional.of(getSampleCustomer()));
    when(mapper.toDTO(any())).thenCallRealMethod();
    when(jwtTokenProvider.createToken(any(), any())).thenReturn(any());

    Exception exception = Assert.assertThrows(GeneralException.class, () -> {
      service.login("TEST", "11");
    });

    String expectedMessage = ExceptionMessageConstants.INVALID_USERNAME_PASSWORD;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));

  }

  @Test
  public void signUpTest() {
    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);
    when(repository.save(any())).thenReturn(getSampleCustomer());
    when(jwtTokenProvider.createToken("TEST", Collections.singletonList(Role.ROLE_CUSTOMER))).thenReturn("TOKEN");
    when(passwordEncoder.encode("11")).thenReturn(any());

    var result = service.signUp(getSampleCustomerDTO());

    assertNotNull(result);
  }


  //private methods
  private Customer getSampleCustomer() {

    var orderDTO = Order.builder().id("111")
        .orderDate(LocalDateTime.now())
        .purchasedAmount(50.0)
        .build();

    var orderList = Collections.singletonList(orderDTO);
    return Customer.builder()
        .username("TEST")
        .email("mail")
        .role(Role.ROLE_CUSTOMER)
        .orderList(orderList)
        .password("11").build();
  }

  private CustomerDTO getSampleCustomerDTO() {
    return CustomerDTO.builder()
        .username("TEST")
        .email("mail")
        .password("11").build();
  }

}