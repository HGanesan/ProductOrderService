package nl.vz.poi.engine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.vz.poi.engine.client.ReqresClient;
import nl.vz.poi.engine.core.client.UserData;
import nl.vz.poi.engine.core.client.UserDetails;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.ReqresClientException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class EmailCheckerServiceTest {

    @Mock
    ReqresClient reqresClient;

    @Mock
    Response response;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    EmailCheckerService emailCheckerService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.emailCheckerService = new EmailCheckerService(reqresClient);
        ReflectionTestUtils.setField(emailCheckerService, "reqresUrl", "url");
        ReflectionTestUtils.setField(emailCheckerService, "reqresPath", "path");
    }


    @Test
    public void validateUserEmailId()throws Exception {
        when(reqresClient.getUserDetails("url","path")).thenReturn(response);
        when(response.readEntity(String.class)).thenReturn("responseStr");
        emailCheckerService=spy(emailCheckerService);
        Mockito.doReturn(objectMapper).when(emailCheckerService).getObjectMapper();
        UserDetails userDetails=new UserDetails();
        List<UserData> userDataList=new ArrayList<>();
        UserData userData=new UserData();
        userData.setEmail("one@one.one");
        userDataList.add(userData);
        userDetails.setUserDataList(userDataList);
        when(objectMapper.readValue("responseStr", UserDetails.class)).thenReturn(userDetails);
        Assert.assertEquals("one@one.one",emailCheckerService.validateUserEmailId("one@one.one").getEmail());

    }

    @Test
    public void validateUserEmailId1()throws Exception {
        when(reqresClient.getUserDetails("url","path")).thenReturn(response);
        when(response.readEntity(String.class)).thenReturn("responseStr");
        emailCheckerService=spy(emailCheckerService);
        Mockito.doReturn(objectMapper).when(emailCheckerService).getObjectMapper();
        UserDetails userDetails=new UserDetails();
        List<UserData> userDataList=new ArrayList<>();
        UserData userData=new UserData();
        userData.setEmail("one@one.one");
        userDataList.add(userData);
        userDetails.setUserDataList(userDataList);
        when(objectMapper.readValue("responseStr", UserDetails.class)).thenReturn(userDetails);
        Assertions.assertThrows(
                EmailIdNotFoundException.class,
                () -> emailCheckerService.validateUserEmailId("two@one.one"),
                "No emailId match found !!"
        );

    }

    @Test
    public void validateUserEmailId2()throws Exception {
        when(reqresClient.getUserDetails("url","path")).thenThrow(new ReqresClientException("Reqres client error"));
        Assertions.assertThrows(
                ReqresClientException.class,
                () -> emailCheckerService.validateUserEmailId("two@one.one"),
                "No emailId match found !!"
        );

    }
}