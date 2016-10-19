/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyResponseParser {

    private static final ShopifyResponseParser parser = new ShopifyResponseParser();
    private Gson gson;

    private ShopifyResponseParser() {
        gson = ShopifyUtil.getGson();
    }

    public <T> T parse(String json, Class<T> cls) {
        T obj = null;
        try {
            obj = gson.fromJson(json, cls);
        } catch (Exception e) {
            System.out.println("Error while parsing the Shopify Reponse: " + e);
        }
        return obj;
    }

    public <T> T parse(String json, Type type) {
        T obj = null;
        try {            
            obj = gson.fromJson(json, type);
        } catch (Exception e) {
            System.out.println("Error while parsing the Shopify Reponse: " + e);
        }
        return obj;
    }

    public static ShopifyResponseParser parser() {
        return parser;
    }
}