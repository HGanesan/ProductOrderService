package nl.vz.poi.engine.client;

import nl.vz.poi.engine.exception.ReqresClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.when;

public class ReqresClientTest {

    @InjectMocks
    ReqresClient reqresClient;

    @Mock
    Invocation.Builder mockInvocationBuilder;

    @Mock
    Response mockResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.reqresClient = new ReqresClient() {
            public Invocation.Builder getInvocationBuilder(String path,
                                                           String url) {

                return mockInvocationBuilder;
            }
        };
        when(mockInvocationBuilder.accept(MediaType.APPLICATION_JSON)).thenReturn(mockInvocationBuilder);
        when(mockInvocationBuilder.get()).thenReturn(mockResponse);
    }

    @Test
    void getUserDetails() throws ReqresClientException {
        when(mockResponse.getStatus()).thenReturn(200);
        reqresClient.getUserDetails("", "");
    }

    @Test
    void getUserDetailsInvocationException() {
        when(mockInvocationBuilder.get()).thenThrow(new RuntimeException("client error"));
        Assertions.assertThrows(
                ReqresClientException.class,
                () -> reqresClient.getUserDetails("", ""),
                "client error"
        );
    }

    @Test
    void getUserDetailsInvalidStatusException() {
        when(mockResponse.getStatus()).thenReturn(404);
        Assertions.assertThrows(
                ReqresClientException.class,
                () -> reqresClient.getUserDetails("", ""),
                "client error"
        );
    }
}