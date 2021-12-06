package nl.vz.poi.engine.dao.impl;

import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OrderDaoImplTest {

    @InjectMocks
    private OrderDaoImpl orderDaoImpl;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.orderDaoImpl = new OrderDaoImpl(jdbcTemplate);
    }

    @Test
    public void save() throws Exception {
        Order order = new Order();
        order.setLastName("");
        order.setFirstName("");
        order.setEmail("");
        order.setProductID("");
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class)).thenReturn(1);
        Assert.assertEquals(1, orderDaoImpl.save(order));
    }

    @Test
    public void saveDatabaseException() {
        Order order = new Order();
        order.setLastName("");
        order.setFirstName("");
        order.setEmail("");
        order.setProductID("");
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("sql error"));
        Assertions.assertThrows(
                DatabaseErrorException.class,
                () -> orderDaoImpl.save(order),
                "sql error"
        );
    }

    @Test
    void findAll() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper) anyObject())).thenReturn(orderList);
        Assert.assertEquals(orderList, orderDaoImpl.findAll());
    }

    @Test
    void findAllDatabaseException() {
        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper) anyObject())).thenThrow(new RuntimeException("sql error"));
        Assertions.assertThrows(
                DatabaseErrorException.class,
                () -> orderDaoImpl.findAll(),
                "sql error"
        );
    }

    @Test
    void findByEmailIdAndProductId() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper) anyObject(), anyString(), anyString())).thenReturn(orderList);
        Assert.assertEquals(orderList, orderDaoImpl.findByEmailIdAndProductId("email", "a"));
    }

    @Test
    void findByEmailIdAndProductIdDatabaseException() throws Exception {
        List<Order> orderList = new ArrayList<Order>();
        when(jdbcTemplate.query(anyString(), (BeanPropertyRowMapper) anyObject(), anyString(), anyString())).thenThrow(new RuntimeException("sql error"));
        Assertions.assertThrows(
                DatabaseErrorException.class,
                () -> orderDaoImpl.findByEmailIdAndProductId("email","1"),
                "sql error"
        );
    }
}