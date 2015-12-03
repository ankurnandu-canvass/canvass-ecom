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
public class Theme extends ShopifyBaseModal {

    @SerializedName("name")
    private String name;
    @SerializedName("role")
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * given you whether the current theme is the stores main theme are not
     *
     * @return
     */
    public boolean isMain() {
        return "main".equalsIgnoreCase(role);
    }
}
