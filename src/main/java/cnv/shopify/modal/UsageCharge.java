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
public class UsageCharge extends ShopifyBaseModal {

    @SerializedName("description")
    private String description;
    @SerializedName("recurring_application_charge_id")
    private float recurring_application_charge_id;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("billing_on")
    private Date billingOn;
    @SerializedName("price")
    private Float price;
    @SerializedName("balance_used")
    private Float balanceUsed;
    @SerializedName("balance_remaining")
    private Float balanceRemaining;
    @SerializedName("risk_level")
    private Float riskLevel;

    public UsageCharge() {
    }

    public UsageCharge(String description, Float price) {
        this.description = description;
        this.price = price;
    }

    public Float getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(Float balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getBalanceUsed() {
        return balanceUsed;
    }

    public void setBalanceUsed(Float balanceUsed) {
        this.balanceUsed = balanceUsed;
    }

    public Date getBillingOn() {
        return billingOn;
    }

    public void setBillingOn(Date billingOn) {
        this.billingOn = billingOn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRecurring_application_charge_id() {
        return recurring_application_charge_id;
    }

    public void setRecurring_application_charge_id(float recurring_application_charge_id) {
        this.recurring_application_charge_id = recurring_application_charge_id;
    }

    public Float getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Float riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UsageCharge{" + "description=" + description + ", recurring_application_charge_id=" + recurring_application_charge_id + ", billingOn=" + billingOn + ", balanceUsed=" + balanceUsed + ", balanceRemaining=" + balanceRemaining + '}';
    }

    public static class Wrapper extends ShopifyWrapper {

        @SerializedName("usage_charge")
        private UsageCharge charge;

        public UsageCharge getCharge() {
            return charge;
        }

        public void setCharge(UsageCharge charge) {
            this.charge = charge;
        }
    }

    public static class WrapperList extends ShopifyWrapper {

        @SerializedName("usage_charges")
        private List<UsageCharge> charges;

        public List<UsageCharge> getCharges() {
            return charges;
        }
    }
}
