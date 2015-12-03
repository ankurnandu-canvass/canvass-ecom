/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ScriptTag extends ShopifyBaseModal {

    @SerializedName(value = "created_at")
    private Date createdAt;
    @SerializedName(value = "event")
    private String event;
    @SerializedName(value = "src")
    private String src;
    @SerializedName(value = "updated_at")
    private Date updatedAt;

    public ScriptTag() {
    }

    public ScriptTag(String event, String src) {
        this.event = event;
        this.src = src;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}