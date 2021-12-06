package nl.vz.poi.engine.dao;

import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.exception.DatabaseErrorException;

import java.util.List;

public interface OrderDao {

    int save(Order order) throws DatabaseErrorException;

    List<Order> findAll() throws DatabaseErrorException;

    List<Order> findByEmailIdAndProductId(String email, String productId) throws DatabaseErrorException;

}
