/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

import cnv.shopify.modal.Order;
import cnv.shopify.modal.ShopifyBaseModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class ShopifyUtil {

    private static final String[] DATE_FORMATS = new String[]{
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd"
    };
    private static final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer());

    public static Gson getGson() {
        //return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gsonBuilder.create();
    }

    public static <T extends ShopifyBaseModal> String getFieldsAsCsv(Class<T> cls) {
        StringBuilder csv = new StringBuilder();
        if (cls == ShopifyBaseModal.class) {
        } else {
            csv.append(getFieldsAsCsv((Class<ShopifyBaseModal>) cls.getSuperclass()));
        }
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

    private static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }

    public static void main(String[] args) {
        System.out.println(getFieldsAsCsv(Order.class));
    }
}
