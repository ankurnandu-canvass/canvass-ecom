/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.modal.Customer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class CustomerService extends ShopifyBaseService {

    public List<Customer> getCustomers() throws Exception {
        return getCustomers(250);
    }

    public List<Customer> getCustomers(int limit) throws Exception {
        return getCustomers(1, limit);
    }

    public List<Customer> getCustomers(int page, int limit) throws Exception {
        if (page < 1) {
            throw new IllegalArgumentException("Page should be a positive integer and > 0");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit should be a positive integer and > 0");
        }
        String path = "/admin/customers.json?";
        HttpUriRequest build = RequestBuilder.get().setUri(client.getBaseUrl() + path).
                addParameter("page", "" + page).
                addParameter("limit", "" + limit).
                build();
        Type type = new TypeToken<HashMap<String, List<Customer>>>() {
        }.getType();
        HashMap<String, List<Customer>> parse = ShopifyResponseParser.parser().parse(execute(build), type);
        return parse != null && parse.get("customers") != null ? parse.get("customers") : null;
    }

    public static void main(String[] args) {
    }
}
