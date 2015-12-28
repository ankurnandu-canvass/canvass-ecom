/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import cnv.shopify.modal.ShopifyBaseModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyUtil {

    public static Gson getGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public static <T extends ShopifyBaseModal> String getFieldsAsCsv(Class<T> cls) {
        StringBuilder csv = new StringBuilder();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SerializedName.class)) {
                SerializedName name = field.getAnnotation(SerializedName.class);
                //System.out.println(field.getType().isAssignableFrom(ShopifyBaseModal.class));
                csv.append(name.value()).append(",");
            }

        }
        return csv.toString();
    }
}
