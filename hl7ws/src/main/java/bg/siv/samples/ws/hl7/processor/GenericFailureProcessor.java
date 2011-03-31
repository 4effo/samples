/**
 * 
 */
package bg.siv.samples.ws.hl7.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openehealth.ipf.modules.hl7.AckTypeCode;
import org.openehealth.ipf.modules.hl7.HL7v2Exception;
import org.openehealth.ipf.modules.hl7.message.MessageUtils;
import org.openehealth.ipf.modules.hl7dsl.MessageAdapter;

import ca.uhn.hl7v2.model.Message;

/**
 * @author i000351
 * 
 */
public class GenericFailureProcessor implements Processor {
	private static final transient Log LOG = LogFactory
			.getLog(GenericFailureProcessor.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
				Throwable.class);
        LOG.debug("Generating default negative ACK");
        HL7v2Exception hl7e = new HL7v2Exception(caused.getMessage(), 207);
        Message nak = MessageUtils.defaultNak(hl7e, AckTypeCode.AE, "2.5.1", "hl7-adapter",
                "hl7-adapter", "ACK^R01^ACK");
        exchange.getOut().setBody(new MessageAdapter(nak));
	}

}
