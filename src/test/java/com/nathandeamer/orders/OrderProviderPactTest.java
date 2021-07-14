package com.nathandeamer.orders;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("orders")
@PactBroker(scheme = "https", host = "${ND_PACT_BROKER_HOST}", authentication = @PactBrokerAuth(token = "${ND_PACT_BROKER_TOKEN}"))
@VerificationReports(value={"console", "markdown", "json"}, reportDir = "build/pact/reports")
public class OrderProviderPactTest {

  @MockBean
  private OrdersRepository ordersRepository;

  @BeforeEach
  public void setupTestTarget(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", 8080));
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  public void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @State("order exists")
  public void orderExists() {
    when(ordersRepository.findById(any())).thenReturn(
            Optional.of(CustomerOrder.builder()
                    .id(101)
                    .items(Collections.singletonList(
                            CustomerOrder.Item.builder()
                                    .qty(1)
                                    .description("Some Description")
                                    .sku("POP")
                                    .build()))
                    .build()));
  }

}
