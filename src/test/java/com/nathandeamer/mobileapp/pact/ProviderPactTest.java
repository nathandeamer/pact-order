package com.nathandeamer.mobileapp.pact;


import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import com.nathandeamer.orders.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("pact")
@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactBroker(host = "${pactbroker.host}", authentication = @PactBrokerAuth(token = "${pactbroker.token}"))
@VerificationReports(value={"console", "markdown", "json"}, reportDir = "build/pact/reports")
public abstract class ProviderPactTest {
  @TestTarget
  public final Target target = new HttpTarget(8080);
}
