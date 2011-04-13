package bg.siv

import org.apache.camel.spring.SpringRouteBuilder

class MockServerRouteBuilder extends SpringRouteBuilder {
    
    void configure() {
        //  mock target server
        from('jetty:http://0.0.0.0:9452/logger')
                .convertBodyTo(String.class)
                .process{ println "---test: ${it.in.body} " }
    }
}