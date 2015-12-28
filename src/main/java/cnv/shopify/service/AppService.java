/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import java.util.HashMap;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class AppService extends ShopifyBaseService {

    /**
     * This method will uninstall the app automatically from the store
     */
    public boolean uninstallApp() throws Exception {
        String path = "/admin/oauth/revoke";
        HttpUriRequest build = RequestBuilder.delete().setUri(baseUrl + path).
                build();
        // adding hte extra headers as they required for this api call
        build.addHeader("Content-Type", "application/json");
        build.addHeader("Accept", "application/json");
        build.addHeader("Content-Length", "0");
        HashMap map = ShopifyResponseParser.parser().parse(execute(build), HashMap.class);
        return map.get("permission") != null;
    }
}
