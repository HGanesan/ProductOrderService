package nl.vz.poi.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.vz.poi.engine.client.ReqresClient;
import nl.vz.poi.engine.core.client.UserData;
import nl.vz.poi.engine.core.client.UserDetails;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.ReqresClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class EmailCheckerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCheckerService.class);

    @Autowired
    private final ReqresClient reqresClient;

    @Value("${reqres.url}")
    private String reqresUrl;

    @Value("${reqres.path}")
    private String reqresPath;

    public EmailCheckerService(ReqresClient reqresClient) {
        this.reqresClient = reqresClient;
    }

    /**
     * Method to validate the emailId with the ReqRes client response.
     *
     * @param emailId- Product emailId.
     * @return UserData.
     * @throws ReqresClientException    - ReqRes client exception.
     * @throws EmailIdNotFoundException - Could not find a match for incoming emailId with Reqres client response.
     */
    public UserData validateUserEmailId(String emailId) throws ReqresClientException, EmailIdNotFoundException {

        LOGGER.debug("Validate user email-id:{}", emailId);
        try {
            Response response = reqresClient.getUserDetails(reqresUrl, reqresPath);

            String responseStr = response.readEntity(String.class);
            UserDetails userDetails = getObjectMapper().readValue(responseStr, UserDetails.class);

            return processUserDetails(userDetails, emailId);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception occurred while processing ResReq response with msg : {} ", e.getMessage());
            throw new ReqresClientException(e.getMessage());
        }
    }

    private UserData processUserDetails(UserDetails userDetails, String emailId) throws EmailIdNotFoundException {
        if (userDetails != null) {
            for (UserData user : userDetails.getUserDataList()) {
                if (user.getEmail() != null && user.getEmail().equals(emailId)) {
                    return user;
                }
            }
        }
        throw new EmailIdNotFoundException("No emailId match found !!");

    }

    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


}
