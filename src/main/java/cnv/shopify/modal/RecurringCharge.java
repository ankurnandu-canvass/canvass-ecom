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
public class RecurringCharge extends ShopifyBaseModal {

    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private float price;
    @SerializedName("return_url")
    private String returnUrl;
    @SerializedName("capped_amount")
    private float cappedAmount;
    @SerializedName("terms")
    private String terms;
    @SerializedName("trial_days")
    private Long trialDays;
    @SerializedName("activated_on")
    private Date activatedOn;
    @SerializedName("billing_on")
    private Date billingOn;
    @SerializedName("cancelled_on")
    private Date cancelledOn;
    @SerializedName("trial_ends_on")
    private Date trialEndsOn;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("confirmation_url")
    private String confirmationUrl;
    @SerializedName("status")
    private String status;
    @SerializedName("test")
    public boolean test;

    public RecurringCharge() {
    }

    public float getCappedAmount() {
        return cappedAmount;
    }

    public void setCappedAmount(float cappedAmount) {
        this.cappedAmount = cappedAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Date getBillingOn() {
        return billingOn;
    }

    public void setBillingOn(Date billingOn) {
        this.billingOn = billingOn;
    }

    public Date getCancelledOn() {
        return cancelledOn;
    }

    public void setCancelledOn(Date cancelledOn) {
        this.cancelledOn = cancelledOn;
    }

    public String getConfirmationUrl() {
        return confirmationUrl;
    }

    public void setConfirmationUrl(String confirmationUrl) {
        this.confirmationUrl = confirmationUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public Long getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Long trialDays) {
        this.trialDays = trialDays;
    }

    public Date getTrialEndsOn() {
        return trialEndsOn;
    }

    public void setTrialEndsOn(Date trialEndsOn) {
        this.trialEndsOn = trialEndsOn;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RecurringCharge{" + "name=" + name + ", price=" + price + ", activatedOn=" + activatedOn + ", billingOn=" + billingOn + ", cancelledOn=" + cancelledOn + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + ", test=" + test + '}';
    }

    public static class Wrapper extends ShopifyWrapper {

        @SerializedName("recurring_application_charge")
        private RecurringChargeResponse charge;

        public RecurringChargeResponse getCharge() {
            return charge;
        }

        public void setCharge(RecurringChargeResponse charge) {
            this.charge = charge;
        }
    }
}
