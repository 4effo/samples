package bg.siv.samples.ws.hl7

import org.apache.camel.spring.SpringRouteBuilder

class SampleRouteBuilder extends SpringRouteBuilder {

	void configure() {
		onException(Exception.class)
				.handled(true)
				.process('genericFailureProcessor')

		from('cxf:bean:soapObservationResultMessageEndpoint')
				.to('direct:input')
				.to('log:debug?showAll=true&multiline=true')


		from('direct:input')
				.unmarshal().ghl7()
				.process{ it.out.body = it.in.body.ack()}
	}
}
