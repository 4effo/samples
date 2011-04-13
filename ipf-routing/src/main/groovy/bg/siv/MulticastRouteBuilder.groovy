package bg.siv

import static org.apache.camel.Exchange.FILE_NAME
import static org.apache.camel.Exchange.HTTP_PATH
import static org.apache.camel.Exchange.HTTP_URI

import org.apache.camel.spring.SpringRouteBuilder

class MulticastRouteBuilder extends SpringRouteBuilder {
    
    void configure() {
        
        from('jetty:http://0.0.0.0:9452/multicaster')
                .unmarshal().string("UTF-8") //'abc'
                .multicast()
                .to('direct:multicast-transformer1', 'direct:multicast-transformer2')
                .end()
                .to('mock:mock')
        
        from('direct:multicast-transformer1')
                .process{
                    println "multicast-transformer1 received: ${it.in.body}"
                    it.in.body = new String(it.in.body) //copy exchange body
                }
                .transform(body().append(" transformed by 1"))
                .to('direct:multicast-logger')
        
        from('direct:multicast-transformer2')
                .process{
                    println "multicast-transformer2 received: ${it.in.body}"
                    it.in.body = new String(it.in.body) //copy exchange body
                }
                .reverse()
                .setHeader(FILE_NAME, simple('${exchangeId}.txt'))
                .to('file:target/output')
                .to('direct:multicast-logger')
        
        from('direct:multicast-logger')
                .removeHeader(HTTP_PATH)
                .removeHeader(HTTP_URI)
                .to('http://localhost:9452/logger')
    }
}
