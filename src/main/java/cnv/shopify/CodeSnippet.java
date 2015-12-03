/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cnv.shopify;

/**
 *
 * @author Naga Srinivas @Canvass
 */
public class CodeSnippet {

    private StringBuilder buff = new StringBuilder();

    public CodeSnippet comment(String comment) {
        buff.append("\n<!-- ").append(comment).append(" -->");
        return this;
    }

    public CodeSnippet m(String comment) {
        buff.append("\n<!-- ").append(comment).append(" -->");
        return this;
    }

    public String code() {
        return buff.toString();
    }

    public CodeSnippet code(String code) {
        buff.append("\n").append(code);
        return this;
    }

    public CodeSnippet c(String code) {
        buff.append("\n").append(code);
        return this;
    }

    public static CodeSnippet create() {
        return new CodeSnippet();
    }

    public static CodeSnippet headSnippet() {
        CodeSnippet code = CodeSnippet.create().
                m("Canvass Code for firecart Integration -- START").
                c("<script type='text/javascript'>").
                c("window.dispatchEvent(new Event('firecart-ready'))").
                c("firecartWebAPI.init(\"1eb9b7098aa3c3c159fc014900011bbd13c44cfe\").ready(function(){").
                c("var ME = this;").
                c("{% if customer %}").
                c("var cust = {").
                c("'name':'{{ customer.name }}',").
                c("'email':'{{ customer.email }}',").
                c("'mobile':'{{ customer.default_address.phone }}',").
                c("'city':'{{ customer.default_address.city }}',").
                c("'country':'{{ customer.default_address.country }}',").
                c("'pincode':'{{ customer.default_address.zip }}'").
                c("};").
                c("ME.setUser(cust);").
                c("{% endif%}").
                c("});").
                c("</script>").
                m("Canvass Code for firecart Integration -- END");
        return code;
    }

    public static void main(String[] args) {
        System.out.println(headSnippet().code());
    }
}
