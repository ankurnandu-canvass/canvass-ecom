/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.ShopifyUtil;
import cnv.shopify.modal.ScriptTag;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
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
public class ScriptTagService extends ShopifyBaseService {

    public ScriptTag createScript(String src, String event) throws IOException {
        String path = "/admin/script_tags.json";
        ScriptTag tag = new ScriptTag(event, src);
        HashMap map = new HashMap();
        map.put("script_tag", tag);
        HttpUriRequest build = RequestBuilder.post().setUri(baseUrl + path).
                setHeader("Content-Type", "application/json").
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(map))).
                build();
        execute(build);
        return null;
    }

    public ScriptTag countScripts() throws Exception {
        String path = "/admin/script_tags/count.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        execute(build);
        return null;
    }

    public List<ScriptTag> getInstalledScripts() throws Exception {
        String path = "/admin/script_tags.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        Type type = new TypeToken<HashMap<String, List<ScriptTag>>>() {
        }.getType();
        return (List<ScriptTag>) ((HashMap) ShopifyResponseParser.parser().parse(execute(build), type)).get("script_tags");
    }

    public ScriptTag deleteScript(long scriptId) throws IOException {
        String path = "/admin/script_tags/" + scriptId + ".json";
        HttpUriRequest build = RequestBuilder.delete().setUri(baseUrl + path).
                build();
        execute(build);
        return null;
    }
}