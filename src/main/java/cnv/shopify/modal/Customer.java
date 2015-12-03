/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class Customer extends ShopifyBaseModal {

    @SerializedName(value = "first_name")
    private String firstName;
    @SerializedName(value = "last_name")
    private String lastName;
    @SerializedName(value = "email")
    private String email;
    @SerializedName(value = "last_order_id")
    private long lastOrderId = -1;
    @SerializedName(value = "total_spent")
    private Float totalSpent;
    @SerializedName(value = "orders_count")
    private int totalOrders = 0;
    @SerializedName(value = "default_address")
    private Address defaultAddress;
    @SerializedName(value = "created_at")
    private Date registeredDate;

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getPhone() {
        String phone = null;
        if (defaultAddress != null) {
            phone = defaultAddress.getPhone();
        }
        return phone == null ? "" : phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(long lastOrderId) {
        this.lastOrderId = lastOrderId;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Float getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Float totalSpent) {
        this.totalSpent = totalSpent;
    }
}