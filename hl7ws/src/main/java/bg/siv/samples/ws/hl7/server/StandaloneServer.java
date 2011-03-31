package bg.siv.samples.ws.hl7.server;

import org.apache.camel.spring.Main;

/**
 * Standalone server
 * 
 * @author Stefan Ivanov
 * 
 */
public class StandaloneServer {

    public static void main(String[] args) throws Exception {
		Main.main("-ac", "/standalone-context.xml");
    }
    
}
