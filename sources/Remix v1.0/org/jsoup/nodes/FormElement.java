package org.jsoup.nodes;

import org.jsoup.select.*;
import org.jsoup.parser.*;
import org.jsoup.*;
import org.jsoup.helper.*;
import java.util.*;

public class FormElement extends Element
{
    private final Elements elements;
    
    public FormElement(final Tag tag, final String baseUri, final Attributes attributes) {
        super(tag, baseUri, attributes);
        this.elements = new Elements();
    }
    
    public Elements elements() {
        return this.elements;
    }
    
    public FormElement addElement(final Element element) {
        this.elements.add(element);
        return this;
    }
    
    public Connection submit() {
        final String action = this.hasAttr("action") ? this.absUrl("action") : this.baseUri();
        Validate.notEmpty(action, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        final Connection.Method method = this.attr("method").toUpperCase().equals("POST") ? Connection.Method.POST : Connection.Method.GET;
        return Jsoup.connect(action).data(this.formData()).method(method);
    }
    
    public List<Connection.KeyVal> formData() {
        final ArrayList<Connection.KeyVal> data = new ArrayList<Connection.KeyVal>();
        for (final Element el : this.elements) {
            if (!el.tag().isFormSubmittable()) {
                continue;
            }
            if (el.hasAttr("disabled")) {
                continue;
            }
            final String name = el.attr("name");
            if (name.length() == 0) {
                continue;
            }
            final String type = el.attr("type");
            if ("select".equals(el.tagName())) {
                final Elements options = el.select("option[selected]");
                boolean set = false;
                for (final Element option : options) {
                    data.add(HttpConnection.KeyVal.create(name, option.val()));
                    set = true;
                }
                if (set) {
                    continue;
                }
                final Element option2 = el.select("option").first();
                if (option2 == null) {
                    continue;
                }
                data.add(HttpConnection.KeyVal.create(name, option2.val()));
            }
            else if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
                if (!el.hasAttr("checked")) {
                    continue;
                }
                final String val = (el.val().length() > 0) ? el.val() : "on";
                data.add(HttpConnection.KeyVal.create(name, val));
            }
            else {
                data.add(HttpConnection.KeyVal.create(name, el.val()));
            }
        }
        return data;
    }
}
