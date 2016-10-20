/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.ShopifyUtil;
import cnv.shopify.modal.RecurringCharge;
import cnv.shopify.modal.RecurringChargeResponse;
import cnv.shopify.modal.RecurringChargeResponse.Wrapper;
import cnv.shopify.modal.UsageCharge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class BillingService extends ShopifyBaseService {

    public RecurringChargeResponse createRecurringCharge(RecurringCharge charge) throws Exception {
        String path = "/admin/recurring_application_charges.json";
        Map objMap = new HashMap();
        objMap.put("recurring_application_charge", charge);
        HttpUriRequest build = RequestBuilder.post().setUri(baseUrl + path).
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(objMap))).
                build();
        // adding hte extra headers as they required for this api call
        build.addHeader("Content-Type", "application/json");
        RecurringCharge.Wrapper wrapper = ShopifyResponseParser.parser().parse(execute(build), RecurringCharge.Wrapper.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while creating the Shopify recurring charge: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharge();
    }

    public RecurringChargeResponse getRecurringCharge(long id) throws Exception {
        String path = "/admin/recurring_application_charges/" + id + ".json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        RecurringCharge.Wrapper wrapper = ShopifyResponseParser.parser().parse(execute(build), RecurringCharge.Wrapper.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while activating the Shopify recurring charge: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharge();
    }

    public List<RecurringChargeResponse> getAllRecurringCharges(long sinceId) throws Exception {
        String path = "/admin/recurring_application_charges.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                addParameter(new BasicNameValuePair("since_id", "" + sinceId)).
                build();
        Wrapper map = ShopifyResponseParser.parser().parse(execute(build), RecurringChargeResponse.Wrapper.class);
        return map.getCharges();
    }

    public RecurringChargeResponse activateRecurringCharge(long id) throws Exception {
        String path = "/admin/recurring_application_charges/" + id + "/activate.json";
        HttpUriRequest build = RequestBuilder.post().setUri(baseUrl + path).
                build();
        RecurringCharge.Wrapper wrapper = ShopifyResponseParser.parser().parse(execute(build), RecurringCharge.Wrapper.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while activating the Shopify recurring charge: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharge();
    }

    public boolean cancelRecurringCharge(long id) throws Exception {
        String path = "/admin/recurring_application_charges/" + id + ".json";
        HttpUriRequest build = RequestBuilder.delete().setUri(baseUrl + path).
                build();
        HashMap map = ShopifyResponseParser.parser().parse(execute(build), HashMap.class);
        if (map != null) {
            System.out.println("Error occured while cancelling the Shopify recurring charge: " + map);
        }
        // If the response is empty then the charge is successfully cancelled
        return map == null;
    }

    public UsageCharge createUsageCharge(long recurringChargeId, UsageCharge charge) throws Exception {
        String path = "/admin/recurring_application_charges/" + recurringChargeId + "/usage_charges.json";
        Map objMap = new HashMap();
        objMap.put("usage_charge", charge);
        HttpUriRequest build = RequestBuilder.post().setUri(baseUrl + path).
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(objMap))).
                build();
        // adding hte extra headers as they required for this api call
        build.addHeader("Content-Type", "application/json");
        UsageCharge.Wrapper wrapper = ShopifyResponseParser.parser().parse(execute(build), UsageCharge.Wrapper.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while creating the Shopify usage charge: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharge();
    }

    public UsageCharge getUsageCharge(long recurringChargeId, long usageChargeId) throws Exception {
        String path = "/admin/recurring_application_charges/" + recurringChargeId + "/usage_charges/" + usageChargeId + ".json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        UsageCharge.Wrapper wrapper = ShopifyResponseParser.parser().parse(execute(build), UsageCharge.Wrapper.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while getting the Shopify usage charge: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharge();
    }

    public List<UsageCharge> getAllUsageCharges(long recurringChargeId) throws Exception {
        String path = "/admin/recurring_application_charges/" + recurringChargeId + "/usage_charges.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        UsageCharge.WrapperList wrapper = ShopifyResponseParser.parser().parse(execute(build), UsageCharge.WrapperList.class);
        if (wrapper.hasErrors()) {
            System.out.println("Error occured while getting all the Shopify usage charges: " + wrapper.getErrors());
            return null;
        }
        return wrapper.getCharges();
    }
}
