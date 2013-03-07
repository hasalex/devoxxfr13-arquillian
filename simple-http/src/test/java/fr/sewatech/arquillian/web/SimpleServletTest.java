package fr.sewatech.arquillian.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class SimpleServletTest {

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        return ShrinkWrap.create(WebArchive.class)
                        .addClasses(SimpleServlet.class);
    }

    @Test
    public void should_reply_OK_to_get(@ArquillianResource(SimpleServlet.class) URL baseURL) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet get = new HttpGet(new URL(baseURL, "simple").toURI());
        get.setHeader("Accept", "text/plain");
        HttpResponse response = httpClient.execute(get);
        assertEquals(200L, response.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String content = reader.readLine();

        assertEquals("OK", content);
    }

}
