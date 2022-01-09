package com.getir.readingisgood.domain.order;

import com.getir.readingisgood.model.document.Order;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

  List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
