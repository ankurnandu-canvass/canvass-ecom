/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify.modal;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class Product extends ShopifyBaseModal {

    @SerializedName("title")
    private String name;
    @SerializedName("images")
    private List<ProductImage> images;

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ProductImage extends ShopifyBaseModal {

        @SerializedName("src")
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}