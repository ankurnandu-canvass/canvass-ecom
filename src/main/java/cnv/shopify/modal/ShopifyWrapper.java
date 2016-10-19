/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public abstract class ShopifyWrapper {

    @SerializedName("errors")
    private Object errors;

    public Object getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return errors != null;
    }
}