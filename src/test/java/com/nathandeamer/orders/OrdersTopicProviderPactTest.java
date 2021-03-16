package com.nathandeamer.orders;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("orders-topic")
@PactBroker(scheme = "https", host = "${ND_PACT_BROKER_HOST}", authentication = @PactBrokerAuth(token = "${ND_PACT_BROKER_TOKEN}"))
@VerificationReports(value={"console", "markdown", "json"}, reportDir = "build/pact/reports")
public class OrdersTopicProviderPactTest {

  @Autowired
  private OrdersKafkaProducer ordersKafkaProducer;

  @BeforeEach
  public void setupTestTarget(PactVerificationContext context) {
    context.setTarget(new MessageTestTarget());
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  public void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @PactVerifyProvider("a order created event")
  public MessageAndMetadata orderCreatedEvent() throws Exception {
    return generateMessageAndMetadata(ordersKafkaProducer.buildMessage(CustomerOrder.builder().id(1234).build(), "CREATED"));
  }

  private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
    HashMap<String, Object> metadata = new HashMap<String, Object>();
    message.getHeaders().forEach(metadata::put);

    return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
  }
}