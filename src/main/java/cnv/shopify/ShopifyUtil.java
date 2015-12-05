/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyUtil {

    public static Gson getGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }
}
