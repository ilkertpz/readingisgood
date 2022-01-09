package com.getir.readingisgood.domain.customer;

import com.getir.readingisgood.model.document.Customer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

  Optional<Customer> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  @Query(value = "{}", fields = "{'orderList': {$slice: [?1, ?2]}}")
  Optional<Customer> findByUsername(String username, int start, int limit);

}
