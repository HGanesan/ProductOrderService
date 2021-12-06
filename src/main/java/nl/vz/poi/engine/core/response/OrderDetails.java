package nl.vz.poi.engine.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetails {

    @JsonProperty("orderID")
    private int orderId;

    public OrderDetails withOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public int getOrderId() {
        return orderId;
    }
}
