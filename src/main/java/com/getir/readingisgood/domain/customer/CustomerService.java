package com.getir.readingisgood.domain.customer;

import com.getir.readingisgood.infrastructure.security.JwtTokenProvider;
import com.getir.readingisgood.model.constants.ExceptionMessageConstants;
import com.getir.readingisgood.model.constants.Role;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.document.Customer;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.mapper.CustomerMapper;
import com.getir.readingisgood.model.mapper.OrderMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final CustomerMapper customerMapper;
  private final OrderMapper orderMapper;

  public String login(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    var customerOpt = customerRepository.findByUsername(username);
    return customerOpt.map(this::getToken).orElseThrow(
        () -> new GeneralException(ExceptionMessageConstants.INVALID_USERNAME_PASSWORD, HttpStatus.UNPROCESSABLE_ENTITY));

  }

  public String signUp(CustomerDTO customerDTO) {
    checkSignUpRules(customerDTO);
    customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
    customerDTO.setRole(Role.ROLE_CUSTOMER);
    var customer = customerRepository.save(customerMapper.toEntity(customerDTO));
    return getToken(customer);
  }

  @Transactional(readOnly = true)
  public Page<OrderDTO> getCustomerOrdersWithPaging(Integer page, Integer size) {
    int start = page != null ? page : 0;
    int limit = size != null ? size : 10;
    Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);

    var customerOpt = customerRepository.findByUsername(jwtTokenProvider.getUsername(), start, limit);

    if (!customerOpt.isPresent()) {
      throw new GeneralException(ExceptionMessageConstants.CUSTOMER_COULD_NOT_BE_FOUD, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    return customerOpt.filter(customer -> !CollectionUtils.isEmpty(customer.getOrderList()))
        .map(customer -> {
          var orderList = orderMapper.toDTOList(customer.getOrderList());
          return new PageImpl<>(orderList, pageable, customer.getOrderCount());
        })
        .orElse(new PageImpl<>(new ArrayList<>(), pageable, 0));

  }

  @Transactional(readOnly = true)
  public List<OrderDTO> getCustomerOrders() {
    var customerOpt = customerRepository.findByUsername(jwtTokenProvider.getUsername());

    customerOpt.orElseThrow(() -> new GeneralException(ExceptionMessageConstants.CUSTOMER_COULD_NOT_BE_FOUD, HttpStatus.UNPROCESSABLE_ENTITY));

    return customerOpt.filter(customer -> !CollectionUtils.isEmpty(customer.getOrderList()))
        .map(customer -> customer.getOrderList().stream())
        .orElseThrow(() -> new GeneralException(ExceptionMessageConstants.CUSTOMER_ORDER_COULD_NOT_BE_FOUD, HttpStatus.UNPROCESSABLE_ENTITY))
        .map(orderMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateCustomerOrderList(OrderDTO orderDTO) {

    var customerOpt = customerRepository.findByUsername(jwtTokenProvider.getUsername());

    customerOpt.map(customer -> {
      var customerDTO = customerMapper.toDTO(customer);
      customerDTO.addOrder(orderDTO);
      customerDTO.setOrderCount(customerDTO.getOrderList().size());
      return customerRepository.save(customerMapper.toEntity(customerDTO));
    }).orElseThrow(() -> new GeneralException(ExceptionMessageConstants.CUSTOMER_COULD_NOT_BE_FOUD, HttpStatus.UNPROCESSABLE_ENTITY));
  }

  //private methods
  private String getToken(Customer customer) {
    return jwtTokenProvider.createToken(customer.getUsername(), Collections.singletonList(customer.getRole()));
  }

  private void checkSignUpRules(CustomerDTO customerDTO) {
    Predicate<String> usernamePredicate = customerRepository::existsByUsername;
    Predicate<String> emailPredicate = customerRepository::existsByEmail;

    if (usernamePredicate.test(customerDTO.getUsername())) {
      throw new GeneralException(ExceptionMessageConstants.USER_ALREADY_IN_USE, HttpStatus.UNPROCESSABLE_ENTITY);
    } else if (emailPredicate.test(customerDTO.getEmail())) {
      throw new GeneralException(ExceptionMessageConstants.EMAIL_ALREADY_IN_USE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

}
