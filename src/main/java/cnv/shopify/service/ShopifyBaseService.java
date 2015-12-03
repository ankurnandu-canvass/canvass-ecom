/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyClient;
import java.io.IOException;
import java.util.Scanner;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyBaseService {

    public static final boolean debug = true;
    protected ShopifyClient client;
    protected String baseUrl;

    ShopifyBaseService() {
    }

    public ShopifyClient getClient() {
        return client;
    }

    public void setClient(ShopifyClient client) {
        this.client = client;
        this.baseUrl = client.getBaseUrl();
    }

    protected String execute(HttpUriRequest request) throws IOException {
        StringBuilder buff = new StringBuilder();
        // setting the access token IFF the credentials has it 
        if (client.getCredentials().hasAccessToken()) {
            request.addHeader("X-Shopify-Access-Token", client.getCredentials().getAccessToken());
        }

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            Scanner in = new Scanner(entity.getContent());
            if (debug) {
                System.out.println("Status Code: " + request.getRequestLine());
                System.out.println("Status Code: " + response.getStatusLine());
            }
            while (in.hasNext()) {
                String line = in.nextLine();
                buff.append(line);
            }
            //System.out.println(buff.toString());
        } catch (Exception e) {
        } finally {
            httpClient.close();
        }
        return buff.toString();
    }
}
