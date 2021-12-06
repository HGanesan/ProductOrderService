package nl.vz.poi.engine.dao.impl;

import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.dao.OrderDao;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method to insert the order details into order_requests table.
     *
     * @param order Order details
     * @return OrderId.
     * @throws DatabaseErrorException Backend DB exception.
     */
    @Override
    public int save(Order order) throws DatabaseErrorException {
        LOGGER.info("Insert order details to DB");
        try {
            jdbcTemplate.update(
                    "INSERT INTO poi.order_requests ( email, first_name, last_name, productID) VALUES (?,?,?,?)",
                    order.getEmail(), order.getFirstName(), order.getLastName(), order.getProductID());

            return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", int.class);
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing insert DB request - Order details :{}", e.getMessage());
            throw new DatabaseErrorException(e.getMessage());
        }
    }

    /**
     * Method to retrieve all the existing order records from order_requests table.
     *
     * @return List of order details.
     * @throws DatabaseErrorException Backend DB exception.
     */
    @Override
    public List<Order> findAll() throws DatabaseErrorException {
        LOGGER.info("Retrieve all order details");
        try {
            String sql = "SELECT * FROM order_requests";
            return jdbcTemplate.query(
                    sql,
                    new BeanPropertyRowMapper<>(Order.class));
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing  DB request - find all Order details :{}", e.getMessage());
            throw new DatabaseErrorException(e.getMessage());
        }
    }

    /**
     * Method to find the order details with email and productId.
     *
     * @param email     User email.
     * @param productId ProductId
     * @return List of order details.
     * @throws DatabaseErrorException Backend DB exception.
     */
    @Override
    public List<Order> findByEmailIdAndProductId(String email, String productId) throws DatabaseErrorException {
        LOGGER.info("FindByEmailIdAndProductId with:{},{}", email, productId);
        try {
            String sql = "SELECT * FROM poi.order_requests WHERE email=? AND productID=?";
            return jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(Order.class), email, productId);
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing DB request - findByEmailIdAndProductId :{}", e.getMessage());
            throw new DatabaseErrorException(e.getMessage());
        }
    }
}
