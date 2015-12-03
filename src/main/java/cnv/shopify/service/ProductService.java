/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.modal.Product;
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
public class ProductService extends ShopifyBaseService {

    public List<Product> getProducts(List<Long> productIds) throws Exception {
        List<Product> products = null;
        if (productIds != null && !productIds.isEmpty()) {
            String path = "/admin/orders.json?";
            StringBuilder productIdsString = new StringBuilder();
            for (int i = 0; i < productIds.size(); i++) {
                if (i != 0) {
                    productIdsString.append(",");
                }
                productIdsString.append(productIds.get(i));
            }
            RequestBuilder builder = RequestBuilder.get().setUri(client.getBaseUrl() + path).
                    addParameter("ids", productIdsString.toString());
            HttpUriRequest build = builder.build();
            Type type = new TypeToken<HashMap<String, List<Product>>>() {
            }.getType();
            HashMap<String, List<Product>> parse = ShopifyResponseParser.parser().parse(execute(build), type);
            products = (parse != null && parse.get("products") != null) ? parse.get("products") : null;
        }
        return products;
    }
}
