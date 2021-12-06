package nl.vz.poi.engine.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ProductDetails {

    @NotNull
    @JsonProperty
    private String productID;

    @NotNull
    @JsonProperty
    private String emailId;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "productID=" + productID +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
