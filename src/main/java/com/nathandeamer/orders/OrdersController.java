package com.nathandeamer.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersService ordersService;

  @GetMapping(value = "/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
  public CustomerOrder getOrder(@PathVariable int orderId) {
    return ordersService.getOrder(orderId);
  }

}
