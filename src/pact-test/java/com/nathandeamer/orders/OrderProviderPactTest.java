package com.nathandeamer.orders;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.AllowOverridePactUrl;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AllowOverridePactUrl
@IgnoreNoPactsToVerify
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("pact-order")
@PactBroker(url = "${PACT_BROKER_BASE_URL}", authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
@VerificationReports(value={"console", "markdown", "json"}, reportDir = "build/pact/reports")
public class OrderProviderPactTest {

  @MockBean
  private OrdersRepository ordersRepository;

  @BeforeEach
  public void setupTestTarget(PactVerificationContext context) {
    if (context != null) {
      context.setTarget(new HttpTestTarget("localhost", 8080));
    }
  }

  @State("An order exists")
  public Map<String, Object> orderExists() {
    int orderNumber = 999666;
    when(ordersRepository.findById(999666)).thenReturn(
            Optional.of(CustomerOrder.builder()
                    .id(101)
                    .items(Collections.singletonList(
                            CustomerOrder.Item.builder()
                                    .qty(1)
                                    .description("Some Description")
                                    .sku("POP")
                                    .build()))
                    .build()));

    Map<String, Object> map = new HashMap<>();
    map.put("orderNumber", orderNumber);
    return map;
  }

  // Used to verify the wiremock consumer.
  @BeforeEach
  public void setupProvider() {
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  public void pactVerificationTestTemplate(PactVerificationContext context) {
    if (context != null) {
      context.verifyInteraction();
    }
  }

}
