/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class Asset extends ShopifyBaseModal {

    @SerializedName("content_type")
    private String contentType;
    @SerializedName("key")
    private String name;
    @SerializedName("value")
    private String value;
    @SerializedName("src")
    private String source;
    @SerializedName("source_key")
    private String sourceName;
    @SerializedName("attachment")
    private String attachment;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("size")
    private int size;
    @SerializedName("theme_id")
    private long themeId;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public static class AssetWraper
            implements Serializable {

        @SerializedName("asset")
        private Asset asset;

        public AssetWraper() {
        }

        public AssetWraper(Asset asset) {
            this.asset = asset;
        }

        public Asset getAsset() {
            return asset;
        }

        public void setAsset(Asset asset) {
            this.asset = asset;
        }
    }
}
