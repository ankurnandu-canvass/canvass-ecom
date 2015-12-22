/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import cnv.shopify.modal.ShopifyCredentials;
import cnv.shopify.service.*;
import com.google.gson.Gson;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
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

    private HashMap<Class, ShopifyBaseService> services;
    private ShopifyCredentials creds;
    String baseUrl;
    private boolean debug = false;

    public void enableDebug() {
        debug = true;
    }

    public void disableDebug() {
        debug = false;
    }

    public boolean isDebugEnabled() {
        return debug;
    }

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

    /**
     * This method will create the Given Service
     *
     * @param <T>
     * @param cls Service Class
     * @return the Service Object
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
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

    /**
     * This method will check the given parameters map is valid or not using the
     * Shopify validation
     *
     * https://docs.shopify.com/api/authentication/oauth
     */
    public static boolean isValidRequest(Map<String, String> map, String secret) {
        boolean flag = false;
        SortedMap<String, String> sortedMap;

        if (map instanceof SortedMap) {
            sortedMap = (SortedMap<String, String>) map;
        } else {// if the map is not a sorteed map then convertin to sorted map
            sortedMap = new TreeMap<String, String>();
            for (String key : map.keySet()) {
                sortedMap.put(key, map.get(key));
            }
        }
        // Now removing the hmac and signature keys from the map
        String hmac = sortedMap.remove("hmac");
        String signature = sortedMap.remove("signature");

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            key = key.replace("%", "%25").replace("&", "%26").replace("=", "%3D");
            value = value.replace("%", "%25").replace("&", "%26");
            sb.append(key).append("=").append(value).append("&");
        }
        String reqString = sb.toString();
        // removing the extra '&' character from the end
        if (reqString.endsWith("&")) {
            reqString = reqString.substring(0, reqString.length() - 1);
        }
        log(reqString);
        // Now hashing with sha256 algorithem
        try {
            String algorithm = "HmacSHA256";
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes(), algorithm));
            byte[] digest = mac.doFinal(reqString.getBytes());
            log("Generated Hash: " + Hex.encodeHexString(digest));
            log("Original Hash: " + hmac);
            flag = hmac.equals(Hex.encodeHexString(digest).toString());
        } catch (Exception e) {
            log("Error while checking the request validation:" + e);
            e.printStackTrace(System.out);
        }
        return flag;
    }
}