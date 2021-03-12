package com.nathandeamer.mobileapp.pact;

import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import com.nathandeamer.orders.CustomerOrder;
import com.nathandeamer.orders.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Provider("orders")
public class OrderProviderPactTest extends ProviderPactTest {

  @Autowired
  private OrdersRepository ordersRepository;

  @State("An order with id 1234 exists")
  public void givenAnOrderExists() {

    ordersRepository.save(CustomerOrder.builder()
            .id(1234)
            .items(Collections.singletonList(CustomerOrder.Item.builder()
                    .qty(1)
                    .description("New York City")
                    .sku("NYC")
                    .build()))
            .build());
  }

}
