/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.ShopifyUtil;
import cnv.shopify.modal.Shop;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopService extends ShopifyBaseService {

    public Shop getShop() throws IOException {
        String path = "/admin/shop.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                addParameter("fields", ShopifyUtil.getFieldsAsCsv(Shop.class)).
                build();
        Type type = new TypeToken<HashMap<String, Shop>>() {
        }.getType();
        HashMap map = ShopifyResponseParser.parser().parse(execute(build), type);
        return (Shop) (map != null ? map.get("shop") : null);
    }
}
