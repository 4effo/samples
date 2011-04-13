package bg.siv

import static org.apache.camel.Exchange.FILE_NAME
import static org.apache.camel.Exchange.HTTP_PATH
import static org.apache.camel.Exchange.HTTP_URI

import org.apache.camel.spring.SpringRouteBuilder

/**
 * This just a sample how one can do sequential transforming 
 * without using the mutlicast component. 
 * 
 * @author Stefan Ivanov
 *
 */
class SequentialRouteBuilder extends SpringRouteBuilder {
    
    void configure() {
        
        from('jetty:http://0.0.0.0:9452/serv')
                .to('direct:serv-transformer1')
                .to('mock:mock')
        
        from('direct:serv-transformer1')
                .unmarshal().string("UTF-8")
                .transform(body().append(" transformed by 1"))
                .marshal().string("UTF-8")
                .to('direct:serv-logger')
                .to('direct:serv-transformer2')
        
        from('direct:serv-transformer2')
                .unmarshal().string("UTF-8")
                .reverse()
                .setHeader(FILE_NAME, simple('${exchangeId}.txt'))
                .to('file:target/output')
                .marshal().string("UTF-8")
                .to('direct:serv-logger')
        
        from('direct:serv-logger')
                .removeHeader(HTTP_PATH)
                .removeHeader(HTTP_URI)
                .to('http://localhost:9452/logger')
    }
}
