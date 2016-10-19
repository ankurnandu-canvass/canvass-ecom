/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class RecurringChargeResponse extends RecurringCharge {

    @SerializedName("api_client_id")
    private long apiClientId;
    @SerializedName("balance_used")
    private float balanceUsed;
    @SerializedName("balance_remaining")
    private float balanceRemaining;
    @SerializedName("risk_level")
    private float riskLevel;
    @SerializedName("decorated_return_url")
    private String decoratedReturnUrl;

    public long getApiClientId() {
        return apiClientId;
    }

    public float getBalanceRemaining() {
        return balanceRemaining;
    }

    public float getBalanceUsed() {
        return balanceUsed;
    }

    public String getDecoratedReturnUrl() {
        return decoratedReturnUrl;
    }

    public float getRiskLevel() {
        return riskLevel;
    }

    public static class Wrapper extends ShopifyWrapper {

        @SerializedName("recurring_application_charges")
        private List<RecurringChargeResponse> charges;

        public List<RecurringChargeResponse> getCharges() {
            return charges;
        }
    }
}
