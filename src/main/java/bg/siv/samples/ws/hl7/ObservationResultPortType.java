package bg.siv.samples.ws.hl7;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

@WebService(targetNamespace = "urn:ws:soap:hl7v2", name = "ObservationResultConsumer_PortType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
/**
 * Web Service defines a contract for transporting HL7 v2 messages over SOAP. 
 * In general it is customization of the IHE PCD-01 transaction.
 * 
 *  @author Stefan Ivanov
 * 
 */
public interface ObservationResultPortType {

	@WebResult(name = "CommunicateDataResponse", targetNamespace = "urn:ws:soap:hl7v2", partName = "Body")
	@Action(input = "urn:ws:soap:hl7v2:CommunicateData", output = "urn:ws:soap:hl7v2:CommunicateDataResponse")
	@WebMethod(operationName = "CommunicateData", action = "urn:ws:soap:hl7v2:CommunicateData")
    public String communicate(
			@WebParam(partName = "Body", name = "CommunicateData", targetNamespace = "urn:ws:soap:hl7v2")
        String request
    );
}
