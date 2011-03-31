package bg.siv.samples.ws.hl7.cxf.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.openehealth.ipf.modules.hl7dsl.MessageAdapter;

import ca.uhn.hl7v2.HL7Exception;

/**
 * Outbound CXF interceptor to encode HL7 Message Adapter
 * 
 * 
 * @author Stefan Ivanov
 * 
 */
public class NormalizePayloadOutInterceptor extends
		AbstractPhaseInterceptor<SoapMessage> {
	
	public NormalizePayloadOutInterceptor() {
		super(Phase.POST_LOGICAL);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Message msgSoap = message.getMessage();
		MessageContentsList payload = (MessageContentsList) msgSoap
				.getContent(List.class);
		MessageAdapter msgAdapter = (MessageAdapter) payload.get(0);
		try {
			String msg = ((ca.uhn.hl7v2.model.Message) msgAdapter.getTarget())
					.encode().replaceAll("\r", "\r\n");
			payload.set(0, msg);
		} catch (HL7Exception ex) {
			ex.printStackTrace();
		}

	}

}
