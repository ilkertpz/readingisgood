package com.getir.readingisgood.infrastructure.security;

import com.getir.readingisgood.domain.customer.CustomerRepository;
import com.getir.readingisgood.model.document.Customer;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetails implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<Customer> customerOpt = customerRepository.findByUsername(username);
    customerOpt.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

    return org.springframework.security.core.userdetails.User
        .withUsername(username)
        .password(customerOpt.get().getPassword())
        .authorities(Arrays.asList(customerOpt.get().getRole()))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }

}
