package bg.siv.samples.ws.hl7;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = { "/standalone-context.xml",
		"/test-context.xml" // jax-ws client
})
public class SampleRouteBuilderTest {
	private static final transient Log LOG = LogFactory
			.getLog(SampleRouteBuilderTest.class.getName());

    @Autowired
    private ProducerTemplate producerTemplate;
    
	@Autowired
	@Qualifier("obsResultClient")
	private ObservationResultPortType client;

	@Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

	@Test
	public void callv26Payload() throws IOException {
		String testString = IOUtils.toString(getClass().getResourceAsStream(
				"/samples/ORU_R01_2.6.txt"));
		String result = client.communicate(testString);
		assertNotNull("Result shouldn't be NULL!", result);
		assertTrue("Result should be a HL7 v2 ACK message",
				result.contains("|ACK^R01^ACK|"));
	}

    @Test
    @Ignore
	public void callv251Payload() throws IOException {
		String testString = IOUtils.toString(getClass().getResourceAsStream(
				"/samples/ORU_R01_2.5.1.txt"));
		String result = client.communicate(testString);
		assertNotNull("Result shouldn't be NULL!", result);
		assertTrue("Result should be a HL7 v2 ACK message",
				result.contains("|ACK^R01|"));
	}

	@Test
    @Ignore
	public void prefixedPayload() throws IOException {
		String testString = "   \n\t\t"
				+ IOUtils.toString(getClass().getResourceAsStream(
						"/samples/ORU_R01_2.5.1.txt"));
		String result = client.communicate(testString);
		assertNotNull("Result shouldn't be NULL!", result);
		assertTrue("Result should be a HL7 v2 ACK message",
				result.contains("|ACK^R01|"));
	}

	@Test
	public void emptyPayload() throws IOException {
		String testString = "   \n\t\t";
		String result = client.communicate(testString);
		assertNotNull("Result shouldn't be NULL!", result);
		// assertTrue("Result should be a HL7 v2 ACK message",
		// result.contains("|ACK^R01|"));
	}

	@Test
	@Ignore
	public void directCall() throws Exception {
		producerTemplate
				.requestBody(
						"direct:input",
						"MSH|^~\\&|AcmeInc^ACDE48234567ABCD^EUI-47 64||||20090713090030+0000||ORU^R01^ORU_R01|MSGID1234|P|2.6|||NE|AL|||||IHE PCD ORU-R0148 2006^HL7^2.16.840.1.113883.9.n.m^HL7");
    }

}
