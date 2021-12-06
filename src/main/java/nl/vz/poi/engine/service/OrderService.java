package nl.vz.poi.engine.service;

import nl.vz.poi.engine.core.client.UserData;
import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.core.request.ProductDetails;
import nl.vz.poi.engine.dao.impl.OrderDaoImpl;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.ProductAlreadyOrderedException;
import nl.vz.poi.engine.exception.ReqresClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private EmailCheckerService emailCheckerService;

    @Autowired
    private OrderDaoImpl orderDaoImpl;

    /**
     * Method to process and create the order details.
     *
     * @param productDetails - Incoming product details.
     * @return OrderId.
     * @throws ReqresClientException    Reqres client exception.
     * @throws EmailIdNotFoundException No match found for the incoming emailId.
     * @throws DatabaseErrorException   Backend DB exception.
     */
    public int processOrderDetails(ProductDetails productDetails) throws ReqresClientException, EmailIdNotFoundException, ProductAlreadyOrderedException, DatabaseErrorException {
        LOGGER.debug("Process order details");

        UserData data = emailCheckerService.validateUserEmailId(productDetails.getEmailId());

        List<Order> orderDetails = orderDaoImpl.findByEmailIdAndProductId(productDetails.getEmailId(), productDetails.getProductID());
        return orderDaoImpl.save(processOrderDetails(orderDetails, data, productDetails));
    }

    /**
     * Method to retrieve all order details.
     *
     * @return List of available orders.
     * @throws DatabaseErrorException Backend DB exception.
     */
    public List<Order> retrieveOrderDetails() throws DatabaseErrorException {
        LOGGER.debug("Retrieve order details");
        return orderDaoImpl.findAll();
    }

    private Order processOrderDetails(List<Order> orderDetails, UserData data, ProductDetails productDetails) throws ProductAlreadyOrderedException {
        if (!orderDetails.isEmpty()) {
            throw new ProductAlreadyOrderedException("Product is already ordered by this user");
        }
        return new Order(data.email, data.firstName, data.lastName, productDetails.getProductID());
    }
}
