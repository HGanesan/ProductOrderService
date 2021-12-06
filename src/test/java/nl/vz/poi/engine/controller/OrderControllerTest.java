package nl.vz.poi.engine.controller;

import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.core.request.ProductDetails;
import nl.vz.poi.engine.core.response.OrderDetails;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.InvalidEmailIdException;
import nl.vz.poi.engine.service.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    ProductDetails productDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.orderController = new OrderController(orderService);
        this.productDetails = new ProductDetails();
        productDetails.setProductID("1");
        productDetails.setEmailId("");
    }

    @Test
    public void createOrder() throws Exception {

        ProductDetails productDetails1=new ProductDetails();
        productDetails1.setProductID("1");
        productDetails1.setEmailId("one@one.com");
        Order order=new Order();
        order.setId(1);
        when(orderService.processOrderDetails(productDetails1)).thenReturn(1);
        OrderDetails orderDetails=orderController.createOrder(productDetails1);
        Assert.assertEquals(1,
                orderDetails.getOrderId());
    }

    @Test
    public void createOrder1InvalidEmailException() {
        Assertions.assertThrows(
                InvalidEmailIdException.class,
                () -> orderController.createOrder(productDetails),
                "email"
        );
    }

    @Test
    public void createOrderEmailIdNotFoundException() throws Exception {

        ProductDetails productDetails1=new ProductDetails();
        productDetails1.setProductID("1");
        productDetails1.setEmailId("one@one.com");
        when(orderService.processOrderDetails(productDetails1)).thenThrow(new EmailIdNotFoundException("email"));
        Assertions.assertThrows(
                EmailIdNotFoundException.class,
                () -> orderController.createOrder(productDetails1),
                "email"
        );
    }

    @Test
    void retrieveOrderDetails()throws Exception {
        List<Order> orderList=new ArrayList<>();
        when(orderService.retrieveOrderDetails()).thenReturn(orderList);
        Assert.assertEquals(orderList,orderController.retrieveOrderDetails());
    }

    @Test
    void retrieveOrderDetailsDatabaseException()throws Exception {
        when(orderService.retrieveOrderDetails()).thenThrow(new DatabaseErrorException("error"));
        Assertions.assertThrows(
                DatabaseErrorException.class,
                () -> orderController.retrieveOrderDetails(),
                "error"
        );
    }
}