package bg.siv;

import static org.junit.Assert.assertEquals;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = { "/context.xml" })
public class VariantsRouteTest {


    @Autowired
    private ProducerTemplate producerTemplate;
	private HttpClient client;
    
    @Before
    public void setUp() throws Exception {
        client = new HttpClient();
    }

    @After
    public void tearDown() throws Exception {

    	
    }
   
    @Test
    public void testReverse() throws Exception {
        assertEquals("cba",
            producerTemplate.requestBody("direct:serv-transformer2", "abc", String.class));
    }
    
    @Test
    public void testPostActualServer() throws Exception {
        PostMethod post = new PostMethod("http://localhost:9452/serv");
        StringRequestEntity requestEntity = new StringRequestEntity("abc", "text/xml", null);     
        post.setRequestEntity(requestEntity);
        
        assertEquals(200, client.executeMethod(post));
        String handle = post.getResponseBodyAsString();
        assertEquals("1 yb demrofsnart cba", handle);
        post.releaseConnection();
    }
    
    @Test
    public void testPostMulticastingServer() throws Exception {
        PostMethod post = new PostMethod("http://localhost:9452/multicaster");
        StringRequestEntity requestEntity = new StringRequestEntity("abc", "text/xml", null);
        post.setRequestEntity(requestEntity);
        
        assertEquals(200, client.executeMethod(post));
        String handle = post.getResponseBodyAsString();
        assertEquals("cba", handle);
        post.releaseConnection();
    }
    
    @Test
    public void testPostToLogger() throws Exception {
        PostMethod post = new PostMethod("http://localhost:9452/logger");
        StringRequestEntity requestEntity = new StringRequestEntity("abc", "text/xml", null);
        post.setRequestEntity(requestEntity);
        
        assertEquals(200, client.executeMethod(post));
        // String handle = post.getResponseBodyAsString();
        // assertEquals("abc", handle);
        // post.releaseConnection();
    }

}
