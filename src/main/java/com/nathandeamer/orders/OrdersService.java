package com.nathandeamer.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

  private final OrdersRepository ordersRepository;

  public CustomerOrder getOrder(int orderId) {
    CustomerOrder order = ordersRepository.findById(orderId)
            .orElseThrow(RuntimeException::new);
    order.getItems().clear(); // Dodgy business logic.
    return order;
  }
}
