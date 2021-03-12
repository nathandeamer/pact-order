package com.nathandeamer.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersService ordersService;

  @GetMapping(value = "/{orderId}")
  public CustomerOrder getOrder(@PathVariable int orderId) {
    return ordersService.getOrder(orderId);
  }

}
