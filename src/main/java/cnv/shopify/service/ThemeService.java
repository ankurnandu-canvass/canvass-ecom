/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.modal.Theme;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ThemeService extends ShopifyBaseService {

    public Theme getMainTheme() throws IOException, Exception {
        Theme mainTheme = null;
        List<Theme> themes = getThemes();
        for (Theme theme : themes) {
            if (theme.isMain()) {
                mainTheme = theme;
                break;
            }
        }
        return mainTheme;
    }

    public List<Theme> getThemes() throws IOException, Exception {
        String path = "/admin/themes.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                build();
        Type type = new TypeToken<HashMap<String, List<Theme>>>() {
        }.getType();
        HashMap map = ShopifyResponseParser.parser().parse(execute(build), type);
        return (List<Theme>) (map != null ? map.get("themes") : null);
    }
}
