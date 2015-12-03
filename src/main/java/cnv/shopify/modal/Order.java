/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class Order extends ShopifyBaseModal {

    @SerializedName("currency")
    private String currency;
    @SerializedName("customer")
    private Customer customer;
    @SerializedName("email")
    private String email;
    @SerializedName("total_price")
    private float totalPrice;
    @SerializedName("line_items")
    private List<Item> items;
    @SerializedName("processed_at")
    private Date processedAt;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    /**
     * Inner class that represents an Item in an Order
     */
    public static class Item extends ShopifyBaseModal {

        @SerializedName("name")
        private String name;
        @SerializedName("product_id")
        private long productId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }
    }
}
