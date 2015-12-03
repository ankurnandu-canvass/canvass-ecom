/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyCredentials {

    private String shopName;
    private String apiKey;
    private String password;
    private String sharedSecret;
    private String accessToken;

    private ShopifyCredentials(String shopName, String accessToken) {
        this.shopName = shopName;
        this.accessToken = accessToken;
    }

    private ShopifyCredentials(String shopName, String apiKey, String sharedSecret, String password) {
        this.shopName = shopName;
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getShopName() {
        return shopName;
    }

    public String getPassword() {
        return password;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean hasAccessToken() {
        return accessToken != null && accessToken.length() != 0;
    }

    public static ShopifyCredentials create(String shopName, String apiKey, String sharedSecret, String password) {
        return new ShopifyCredentials(shopName, apiKey, sharedSecret, password);
    }

    public static ShopifyCredentials createWithToken(String shopName, String token) {
        return new ShopifyCredentials(shopName, token);
    }
}