package nl.vz.poi.engine.client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class AbstractClient {

    protected LoggingFeature loggingFeature;


    @Value("${reqres.readTimeout}")
    private String readTimeout;

    @Value("${reqres.connectionTimeout}")
    private String connectionTimeout;

    public AbstractClient() {
    }

    /**
     * Method to configure the InvocationBuilder
     *
     * @param path request path.
     * @param url  request url.
     * @return Invocation.Builder
     */
    public Invocation.Builder getInvocationBuilder(String path, String url) {
        Client client = getInvocationClient(loggingFeature);
        WebTarget webTarget = client.target(url);
        return webTarget.path(path).request(MediaType.APPLICATION_JSON);
    }

    /**
     * Method to configure the Invocation Client.
     *
     * @param loggingFeature LoggingFeature
     * @return Client
     */
    private Client getInvocationClient(LoggingFeature loggingFeature) {
        ClientConfig configuration = new ClientConfig();
        configuration = configuration.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);
        configuration = configuration.property(ClientProperties.READ_TIMEOUT, readTimeout);
        return ClientBuilder
                .newClient(configuration)
                .register(loggingFeature);
    }

}
