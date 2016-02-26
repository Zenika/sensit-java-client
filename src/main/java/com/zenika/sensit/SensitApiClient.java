package com.zenika.sensit;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

class SensitApiClient {

    private static final Logger log = LoggerFactory.getLogger(SensitApiClient.class);

    private Map<String, String> defaultHeaders = new HashMap<>();
    private Client client;

    private static final String endpoint = "https://api.sensit.io/v1";

    public SensitApiClient() {
        
        log.debug("Configuring Jackson ObjectMapper");
        
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        
        log.debug("Configuring JAX-RS ws client");
        this.client = ClientBuilder.newClient(new ClientConfig(jacksonProvider));
    }

    /**
     * Add a default header that will be set for each request.
     * 
     * @param name
     *            The name of the header
     * @param value
     *            Value of the header
     */
    public void addDefaultHeader(String name, String value) {
        defaultHeaders.put(name, value);
    }

    /**
     * Retrieve a resource from the sensit api.
     * 
     * @param <T>
     *            type of the resource to retrieve.
     * @param url
     *            resource location (relative to the api {@link #endpoint})
     * @param type
     *            type of the resource to retrieve. ex : To retrieve a
     *            SensitResponse&lt;Device&gt;, instead of writing
     *            <pre>SensitResponse &lt;Device&gt;.class</pre> (which is not possible)
     *            you would write
     * 
     *            <pre>
     *            new GenericType&lt;SensitResponse&lt;Device&gt;&gt;() {}.
     *            </pre>
     * 
     * @return the response of the api
     */
    public <T> T get(String url, GenericType<T> type) {
        String targetUrl = endpoint + url;
        log.debug("Request to url " + targetUrl);
        
        Builder b = client.target(targetUrl).request(MediaType.APPLICATION_JSON);

        defaultHeaders.forEach((key, value) -> b.header(key, value));
        
        log.debug("Performing request");
        Response response = b.get();
        if (response.getStatusInfo().getStatusCode() == Status.UNAUTHORIZED.getStatusCode()) {
            throw new NotAuthenticatedException();
        }
        
        return response.readEntity(type);
    }
}
