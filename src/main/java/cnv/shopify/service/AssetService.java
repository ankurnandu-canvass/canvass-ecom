/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.service;

import cnv.shopify.ShopifyResponseParser;
import cnv.shopify.ShopifyUtil;
import cnv.shopify.modal.Asset;

import java.io.IOException;
import java.util.HashMap;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
/**
 *
 * @author Naga Srinivas @Canvass
 */
public class AssetService extends ShopifyBaseService {

    public Asset loadAsset(long themeId, String assetName) throws Exception {
        String path = "/admin/themes/" + themeId + "/assets.json";
        HttpUriRequest build = RequestBuilder.get().setUri(baseUrl + path).
                addParameter("asset[key]", assetName).                
                build();
        Asset.AssetWraper aw = ShopifyResponseParser.parser().parse(execute(build), Asset.AssetWraper.class);
        return aw != null ? aw.getAsset() : null;
    }

    public Asset updateAsset(long themeId, Asset asset) throws IOException {
        asset.setThemeId(themeId);
        Asset.AssetWraper wraper = new Asset.AssetWraper(asset);
        HashMap map = new HashMap();
        String path = "/admin/themes/" + themeId + "/assets.json";
        HttpUriRequest build = RequestBuilder.put().setUri(baseUrl + path).
                setHeader("Content-Type", "application/json").
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(wraper))).
                build();
        execute(build);
        return null;
    }

    public Asset copyAsset(long themeId, String assetName, String newAssetName) throws IOException {
        Asset asset = new Asset();
        asset.setName(newAssetName);
        asset.setSourceName(assetName);
        HashMap map = new HashMap();
        map.put("asset", asset);
        String path = "/admin/themes/" + themeId + "/assets.json";
        HttpUriRequest build = RequestBuilder.put().setUri(baseUrl + path).
                setHeader("Content-Type", "application/json").
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(map))).
                build();
        execute(build);
        return null;
    }

    public Asset createAsset(long themeId, String assetName, String value) throws IOException {
        Asset asset = new Asset();
        asset.setName(assetName);
        asset.setValue(value);
        HashMap map = new HashMap();
        map.put("asset", asset);
        String path = "/admin/themes/" + themeId + "/assets.json";
        HttpUriRequest build = RequestBuilder.put().setUri(baseUrl + path).
                setHeader("Content-Type", "application/json").
                setEntity(new StringEntity(ShopifyUtil.getGson().toJson(map))).
                build();
        execute(build);
        return null;
    }

    public Asset deleteAsset(long themeId, String assetKey) throws IOException {
        String path = "/admin/themes/" + themeId + "/assets.json";
        HttpUriRequest build = RequestBuilder.delete().setUri(baseUrl + path).
                addParameter("asset[key]", assetKey).
                build();
        execute(build);
        return null;
    }
}
