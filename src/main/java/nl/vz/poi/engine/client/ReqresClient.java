package nl.vz.poi.engine.client;

import nl.vz.poi.engine.exception.InvalidResponseCodeException;
import nl.vz.poi.engine.exception.ReqresClientException;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Component
public class ReqresClient extends AbstractClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReqresClient.class);

    public ReqresClient() {
        super();
        loggingFeature = new LoggingFeature(java.util.logging.Logger.getLogger("nl.vz.pos.engine.logging.reqres"),
                Level.FINE, LoggingFeature.Verbosity.PAYLOAD_ANY, 1000);
    }

    public Response getUserDetails(String url, String path) throws ReqresClientException {
        LOGGER.info("Invoking Reqres-client GET with path: {}", path);
        try {
            Invocation.Builder invocationBuilder = getInvocationBuilder(path, url);
            Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
            validateReqresResponseCode(response.getStatus());
            return response;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while processing ResReq request with path : {} : {}", path, e.getMessage());
            throw new ReqresClientException(e.getMessage());
        }
    }

    private void validateReqresResponseCode(int status) throws InvalidResponseCodeException {
        LOGGER.info("Response code from ReqRes server : {} ", status);
        if (status != 200) {
            throw new InvalidResponseCodeException("Reqres responded with Invalid status code : {} ", String.valueOf(status));
        }
    }
}
