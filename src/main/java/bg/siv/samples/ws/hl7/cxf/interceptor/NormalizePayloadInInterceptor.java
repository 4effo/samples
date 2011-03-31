package bg.siv.samples.ws.hl7.cxf.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * Inbound CXF interceptor to remove leading characters from the payload.
 * 
 * @author Stefan Ivanov
 * 
 */
public class NormalizePayloadInInterceptor extends
		AbstractPhaseInterceptor<SoapMessage> {
	
	private static final String NORMALIZE_EXPR = "^\\s+|^\\t+|^\\n+";

	public NormalizePayloadInInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Message msg = message.getMessage();
		MessageContentsList payload = (MessageContentsList) msg
				.getContent(List.class);
		payload.set(0, ((String) payload.get(0)).replaceAll(NORMALIZE_EXPR,
				new String()));
	}

}
