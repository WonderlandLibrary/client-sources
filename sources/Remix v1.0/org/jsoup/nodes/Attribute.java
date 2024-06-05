package org.jsoup.nodes;

import org.jsoup.helper.*;
import org.jsoup.*;
import java.io.*;
import java.util.*;

public class Attribute implements Map.Entry<String, String>, Cloneable
{
    private static final String[] booleanAttributes;
    private String key;
    private String value;
    
    public Attribute(final String key, final String value) {
        Validate.notNull(key);
        Validate.notNull(value);
        this.key = key.trim();
        Validate.notEmpty(key);
        this.value = value;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        Validate.notEmpty(key);
        this.key = key.trim();
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String setValue(final String value) {
        Validate.notNull(value);
        final String old = this.value;
        this.value = value;
        return old;
    }
    
    public String html() {
        final StringBuilder accum = new StringBuilder();
        try {
            this.html(accum, new Document("").outputSettings());
        }
        catch (IOException exception) {
            throw new SerializationException(exception);
        }
        return accum.toString();
    }
    
    protected void html(final Appendable accum, final Document.OutputSettings out) throws IOException {
        accum.append(this.key);
        if (!this.shouldCollapseAttribute(out)) {
            accum.append("=\"");
            Entities.escape(accum, this.value, out, true, false, false);
            accum.append('\"');
        }
    }
    
    @Override
    public String toString() {
        return this.html();
    }
    
    public static Attribute createFromEncoded(final String unencodedKey, final String encodedValue) {
        final String value = Entities.unescape(encodedValue, true);
        return new Attribute(unencodedKey, value);
    }
    
    protected boolean isDataAttribute() {
        return this.key.startsWith("data-") && this.key.length() > "data-".length();
    }
    
    protected final boolean shouldCollapseAttribute(final Document.OutputSettings out) {
        return ("".equals(this.value) || this.value.equalsIgnoreCase(this.key)) && out.syntax() == Document.OutputSettings.Syntax.html && this.isBooleanAttribute();
    }
    
    protected boolean isBooleanAttribute() {
        return Arrays.binarySearch(Attribute.booleanAttributes, this.key) >= 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        final Attribute attribute = (Attribute)o;
        Label_0054: {
            if (this.key != null) {
                if (this.key.equals(attribute.key)) {
                    break Label_0054;
                }
            }
            else if (attribute.key == null) {
                break Label_0054;
            }
            return false;
        }
        if (this.value != null) {
            if (!this.value.equals(attribute.value)) {
                return false;
            }
        }
        else if (attribute.value != null) {
            return false;
        }
        return true;
        b = false;
        return b;
    }
    
    @Override
    public int hashCode() {
        int result = (this.key != null) ? this.key.hashCode() : 0;
        result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
        return result;
    }
    
    public Attribute clone() {
        try {
            return (Attribute)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    static {
        booleanAttributes = new String[] { "allowfullscreen", "async", "autofocus", "checked", "compact", "declare", "default", "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch" };
    }
}
