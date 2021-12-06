package nl.vz.poi.engine.core.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String productID;

    public Order() {
    }

    public Order(String email, String firstName, String lastName, String productID) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.productID = productID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonIgnore
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
