/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyResponseParser {

    private static final ShopifyResponseParser parser = new ShopifyResponseParser();
    private Gson gson;

    private ShopifyResponseParser() {
        gson = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'hh:MM:ss").
                create();
    }

    public <T> T parse(String json, Class<T> cls) throws Exception {
        T obj = gson.fromJson(json, cls);
        return obj;
    }

    public <T> T parse(String json, Type type) throws Exception {
        T obj = gson.fromJson(json, type);
        return obj;
    }

    public static ShopifyResponseParser parser() {
        return parser;
    }

    public static void main(String[] args) throws ParseException {
        String s = "2015-10-19T05:34:29-04:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'hh:MM:ss");
        System.out.println(sdf.parse(s));
    }
}