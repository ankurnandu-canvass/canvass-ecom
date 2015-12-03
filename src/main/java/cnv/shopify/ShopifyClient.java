/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import cnv.shopify.modal.*;
import cnv.shopify.modal.ScriptTag;
import cnv.shopify.service.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyClient {

    private static final String THEME_LAYOUT_NAME = "layout/theme.liquid";
    private static final String TRACKING_SNIPPET_NAME = "snippets/firecart-tracking.liquid";
    private static final String INTEGRATION_SCRIPT_NAME = "https://s3-ap-southeast-1.amazonaws.com/naga-test-bucket/res/integrations/firecart-integration.js";
    private static final String THEME_LAYOUT_CODE = "<!-- FireCart START -->{% include 'firecart-tracking' %}<!-- FireCart END -->";
    private static final String ACCESS_KEY_PLACEHOLDER = "<ACCESS-KEY>";
    private HashMap<Class, ShopifyBaseService> services;
    // Firecart Integration Access Key
    private String accessKey = "8272dcf47d96e0387d4e9f6ae87593090f2c4ce4";
    private ShopifyCredentials creds;
    String baseUrl;

    public ShopifyClient(ShopifyCredentials credentails) {
        this.creds = credentails;
        if (creds.hasAccessToken()) {
            baseUrl = "https://" + creds.getShopName() + ".myshopify.com";
        } else {
            baseUrl = "https://" + creds.getApiKey()
                    + ":" + creds.getPassword() + "@" + creds.getShopName() + ".myshopify.com";
        }
    }

    public ShopifyCredentials getCredentials() {
        return creds;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    static void log(String msg) {
        System.out.println("[Shopify] " + msg);
    }

    public void integrate() throws Exception {
        log("Installation process started");
        ThemeService themeService = getService(ThemeService.class);
        ScriptTagService scriptService = getService(ScriptTagService.class);
        AssetService assetService = getService(AssetService.class);
        WebhookService webkoohService = getService(WebhookService.class);
        
        Theme mainTheme = themeService.getMainTheme();
        if (mainTheme != null) {
            long themeId = mainTheme.getId();

            scriptService.createScript(INTEGRATION_SCRIPT_NAME, "onload");
            log("Added the integration script");

            // now adding the header page code
            StringBuilder snippet = new StringBuilder();
            Scanner in = new Scanner(System.class.getResourceAsStream("/newjson.json"));
            while (in.hasNext()) {
                snippet.append(in.nextLine());
            }
            String snipCode = snippet.toString();
            snipCode = snipCode.replace(ACCESS_KEY_PLACEHOLDER, accessKey);
            assetService.createAsset(themeId, TRACKING_SNIPPET_NAME, snipCode);
            log("Added the tracking snippet");

            Asset asset = assetService.loadAsset(themeId, THEME_LAYOUT_NAME);
            StringBuilder buff = new StringBuilder(asset.getValue());
            int index = buff.indexOf("</body>");
            System.out.println("Body Tag Index: " + index);
            buff.insert(index, THEME_LAYOUT_CODE);
            asset.setValue(buff.toString());
            assetService.updateAsset(themeId, asset);
            log("Added code snippet to theme.liquid");

            // creating weebhooks
            webkoohService.createWebhook("checkouts/update", "http://requestb.in/1h5xizs1");
            log("Created checkouts/update webhook");
        }
        log("Installation process Completed");
    }

    /**
     * This method will do the Data Sync to the firecart app
     */
    public void dataSync() throws InstantiationException, IllegalAccessException, Exception {
        CustomerService customerService = getService(CustomerService.class);
        boolean hasMoreCustomers = true;
        int page = 1;
        final int limit = 5;
        List<Long> orderIds = new ArrayList<Long>();
        do {
            List<Customer> customers = customerService.getCustomers(page, limit);
            if (customers != null) {
                for (Customer cust : customers) {
                    System.out.println("Name: " + cust.getFullName());
                    System.out.println("Registered On: " + cust.getRegisteredDate());
                    System.out.println("Mobile: " + cust.getPhone());
                    System.out.println("Email: " + cust.getEmail());
                    System.out.println("Total Orders: " + cust.getTotalOrders());
                    System.out.println("Total Spent: " + cust.getTotalSpent());
                    System.out.println("Average Spent: " + (cust.getTotalOrders() > 0 ? cust.getTotalSpent() / cust.getTotalOrders() : 0));
                    if (cust.getDefaultAddress() != null) {
                        System.out.println("City: " + cust.getDefaultAddress().getCity());
                        System.out.println("Zip: " + cust.getDefaultAddress().getPincode());
                        System.out.println("Country: " + cust.getDefaultAddress().getCountryName());
                    }
                    System.out.println("Last Order Id: " + cust.getLastOrderId());
                    if (cust.getLastOrderId() != -1) {
                        orderIds.add(cust.getLastOrderId());
                    }
                    System.out.println("----------------------------------");
                }
            }
            if (customers == null || customers.size() < limit) {
                hasMoreCustomers = false;
            } else {
                // moving to next page
                page++;
            }
        } while (hasMoreCustomers);
        syncOrders(orderIds);
    }

    public void syncOrders(List<Long> orderIds) throws InstantiationException, IllegalAccessException, Exception {
        OrderService orderService = getService(OrderService.class);
        List<Order> orders = orderService.getOrders(orderIds);
        if (orders != null) {
            for (Order order : orders) {
                System.out.println("Last Purchase Date: " + order.getProcessedAt());
                System.out.println("Last Website Visit Date: " + order.getProcessedAt());
                System.out.println("Product Name: " + order.getItems().get(0).getName());
            }
        }
    }

    /**
     * This method will uninstall the resources and code from the current shop
     */
    public void uninstal() throws Exception {
        log("Uninstallation process started");
        ThemeService themeService = getService(ThemeService.class);
        ScriptTagService scriptService = getService(ScriptTagService.class);
        AssetService assetService = getService(AssetService.class);
        WebhookService webkoohService = getService(WebhookService.class);

        Theme mainTheme = themeService.getMainTheme();
        if (mainTheme != null) {
            long themeId = mainTheme.getId();

            List<ScriptTag> scripts = scriptService.getInstalledScripts();
            for (ScriptTag script : scripts) {
                System.out.println(script.getId());
                scriptService.deleteScript(script.getId());
                log("Removed the integration script");
            }
            Asset asset = assetService.loadAsset(themeId, TRACKING_SNIPPET_NAME);
            if (asset != null) {
                System.out.println(asset.getName());
                assetService.deleteAsset(themeId, asset.getName());
                log("Removed the tracking snippet");
            }
            Asset themeAsset = assetService.loadAsset(themeId, THEME_LAYOUT_NAME);
            if (themeAsset != null) {
                String value = themeAsset.getValue();
                value = value.replace(THEME_LAYOUT_CODE, "");
                themeAsset.setValue(value);
                assetService.updateAsset(themeId, themeAsset);
                log("Removed code snippet from theme.liquid");
            }
            List<Webhook> hooks = webkoohService.getWebhooks();
            for (Webhook hook : hooks) {
                webkoohService.deleteWebhook(hook.getId());
                log("Removed " + hook.getTopic() + " webhook");
            }
        }
        log("Uninstallation process completed");
    }

    public <T extends ShopifyBaseService> T getService(Class<T> cls) throws InstantiationException, IllegalAccessException {
        T obj = cls.newInstance();
        obj.setClient(this);
        return obj;
    }

    /**
     * This method will request for the access token for the given shop
     *
     * @param shopName shop name
     * @param appId client app id
     * @param appSecret client app secret
     * @param code one time access code
     * @return access token
     */
    public static String requestAccessToken(String shopName, String appId, String appSecret, String code) {
        String accessToken = null;
        try {
            String url = "https://" + shopName + ".myshopify.com/admin/oauth/access_token";
            HttpUriRequest tokenRequest = RequestBuilder.post().
                    setUri(url).
                    addParameter("client_id", appId).
                    addParameter("client_secret", appSecret).
                    addParameter("code", code).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse tokenRes = httpClient.execute(tokenRequest);
            Scanner scanner = new Scanner(tokenRes.getEntity().getContent());
            StringBuilder buff = new StringBuilder();
            while (scanner.hasNext()) {
                buff.append(scanner.nextLine());
            }
            scanner.close();

            Gson gson = new Gson();
            HashMap tokenObj = gson.fromJson(buff.toString(), HashMap.class);
            accessToken = (String) tokenObj.get("access_token");
        } catch (Exception e) {
            log("Error occured while requesting for the shopify access token for " + shopName + ": " + e);
            e.printStackTrace(System.err);
        } finally {
        }
        return accessToken;
    }

    public static void main(String[] args) throws Exception {
        ShopifyCredentials creds = ShopifyCredentials.createWithToken("vikram2", "1bcbb7f0803053c29c4406faa391bc69");
        ShopifyClient client = new ShopifyClient(creds);
        //client.dataSync();
        //client.integrate();
        String token = requestAccessToken("vikram2", "c678b48ae749dff6a25523a04427f24e", "dcb74ddbfaa090b0b79a7f830e111ce8", "232323");
        System.out.println("Access Token: " + token);
    }

    public static void main2(String[] args) throws Exception {
        ShopifyCredentials creds = ShopifyCredentials.create("vikram2", "4ce75e852b8e58786afc40c8254f0dcd",
                "0dbd21b69459e2ae0f3627525f09cafa", "525ecbde679e8d3900649c146e9941c6");
        ShopifyClient client = new ShopifyClient(creds);
        OrderService service = client.getService(OrderService.class);
        //ThemeService service = client.getService(ThemeService.class);
        //Theme theme = service.getMainTheme();

    }
}
