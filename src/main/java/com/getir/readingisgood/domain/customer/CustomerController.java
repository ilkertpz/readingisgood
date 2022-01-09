package com.getir.readingisgood.domain.customer;


import com.getir.readingisgood.model.constants.URLConstants;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.response.WrapperListResponse;
import com.getir.readingisgood.model.response.WrapperResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstants.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping(URLConstants.CUSTOMER_LOGIN)
  public ResponseEntity<WrapperResponse<String>> login(@RequestParam("Username") String username,
      @RequestParam("Password") String password) {
    var token = customerService.login(username, password);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(token));
  }

  @PostMapping(URLConstants.CUSTOMER_SIGN_UP)
  public ResponseEntity<WrapperResponse<String>> signUp(@Valid @RequestBody CustomerDTO customerDTO) {
    var token = customerService.signUp(customerDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(token));
  }

  @GetMapping(URLConstants.CUSTOMER_ORDERS_PAGING)
  public ResponseEntity<WrapperResponse<Page<OrderDTO>>> getCustomersOrdersWithPaging(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
    var result = customerService.getCustomerOrdersWithPaging(page, size);
    return ResponseEntity.status(HttpStatus.OK).body(new WrapperResponse<>(result));
  }

  @GetMapping(URLConstants.CUSTOMER_ORDERS_NON_PAGING)
  public ResponseEntity<WrapperListResponse<OrderDTO>> getCustomersOrders() {
    var result = customerService.getCustomerOrders();
    return ResponseEntity.status(HttpStatus.OK).body(new WrapperListResponse<>(result));
  }

}
