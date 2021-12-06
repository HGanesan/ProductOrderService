package nl.vz.poi.engine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.vz.poi.engine.core.data.Order;
import nl.vz.poi.engine.core.request.ProductDetails;
import nl.vz.poi.engine.core.response.OrderDetails;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.InvalidEmailIdException;
import nl.vz.poi.engine.exception.ProductAlreadyOrderedException;
import nl.vz.poi.engine.exception.ReqresClientException;
import nl.vz.poi.engine.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static nl.vz.poi.engine.utilities.PoiUtilities.validateRequest;
import static nl.vz.poi.engine.utilities.RequestMapping.API;
import static nl.vz.poi.engine.utilities.RequestMapping.CREATE_ORDER;
import static nl.vz.poi.engine.utilities.RequestMapping.RETRIEVE_ORDERS;


@RestController
@RequestMapping(API)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "This is to create the order with user details and store it in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully created the order",
                    content = @Content(schema = @Schema(implementation = OrderDetails.class))),
            @ApiResponse(responseCode = "404",
                    description = "EmailId not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid emailId",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Product already ordered by the user",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Something went wrong with the service/database while processing the request",
                    content = @Content)
    })
    @PostMapping(value = CREATE_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails createOrder(@RequestBody @NotNull @Valid ProductDetails productDetails) throws ReqresClientException,
            EmailIdNotFoundException,
            InvalidEmailIdException,
            ProductAlreadyOrderedException,
            DatabaseErrorException {
        LOGGER.info("Process to create order request for product : {}", productDetails.getProductID());
        validateRequest(productDetails);

        OrderDetails orderDetails = new OrderDetails();
        return orderDetails.withOrderId(orderService.processOrderDetails(productDetails));
    }


    @Operation(summary = "This is to retrieve all the order records with user details from the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved all the orders",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDetails.class)))),
            @ApiResponse(responseCode = "500",
                    description = "Something went wrong with the service/database while processing the request",
                    content = @Content)
    })
    @GetMapping(value = RETRIEVE_ORDERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> retrieveOrderDetails() throws DatabaseErrorException {

        LOGGER.info("Process to retrieve all the order details");
        return orderService.retrieveOrderDetails();
    }
}
