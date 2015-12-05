/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.ShopifyUtil;
import cnv.shopify.modal.Webhook;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
/**
 *
 * @author Naga Srinivas @Canvass
 */
public class WebhookService extends ShopifyBaseService {

    public List<Webhook> getWebhooks() throws Exception {
        String path = "/admin/webhooks.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        Type type = new TypeToken<HashMap<String, List<Webhook>>>() {
        }.getType();
        HashMap map = ShopifyResponseParser.parser().parse(execute(build), type);
        return (List<Webhook>) (map != null ? map.get("webhooks") : null);
    }

    public Webhook createWebhook(String event, String url) throws Exception {
        Webhook.WebhookWraper ww = new Webhook.WebhookWraper();
        ww.setWebhook(new Webhook());
        ww.getWebhook().setTopic(event);
        ww.getWebhook().setUrl(url);
        String path = "/admin/webhooks.json";
        HttpUriRequest build = RequestBuilder.post().setUri(baseUrl + path).
                setHeader("Content-Type", "application/json").
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(ww))).
                build();
        Webhook hook = ShopifyResponseParser.parser().parse(execute(build), Webhook.WebhookWraper.class).getWebhook();
        return hook;
    }

    public void deleteWebhook(long hookId) throws Exception {
        String path = "/admin/webhooks/" + hookId + ".json";
        HttpUriRequest build = RequestBuilder.delete().setUri(baseUrl + path).
                build();
        execute(build);
    }
}
