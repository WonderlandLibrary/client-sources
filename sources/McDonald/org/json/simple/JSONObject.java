// 
// Decompiled by Procyon v0.5.36
// 

package org.json.simple;

import java.io.IOException;
import java.util.Iterator;
import java.io.Writer;
import java.util.Map;
import java.util.HashMap;

public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = -503443796854799292L;
    
    public JSONObject() {
    }
    
    public JSONObject(final Map map) {
        super(map);
    }
    
    public static void writeJSONString(final Map map, final Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }
        boolean first = true;
        final Iterator iter = map.entrySet().iterator();
        out.write(123);
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                out.write(44);
            }
            final Entry entry = iter.next();
            out.write(34);
            out.write(escape(String.valueOf(entry.getKey())));
            out.write(34);
            out.write(58);
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write(125);
    }
    
    public void writeJSONString(final Writer out) throws IOException {
        writeJSONString(this, out);
    }
    
    public static String toJSONString(final Map map) {
        if (map == null) {
            return "null";
        }
        final StringBuffer sb = new StringBuffer();
        boolean first = true;
        final Iterator iter = map.entrySet().iterator();
        sb.append('{');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                sb.append(',');
            }
            final Entry entry = iter.next();
            toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
        }
        sb.append('}');
        return sb.toString();
    }
    
    public String toJSONString() {
        return toJSONString(this);
    }
    
    private static String toJSONString(final String key, final Object value, final StringBuffer sb) {
        sb.append('\"');
        if (key == null) {
            sb.append("null");
        }
        else {
            JSONValue.escape(key, sb);
        }
        sb.append('\"').append(':');
        sb.append(JSONValue.toJSONString(value));
        return sb.toString();
    }
    
    public String toString() {
        return this.toJSONString();
    }
    
    public static String toString(final String key, final Object value) {
        final StringBuffer sb = new StringBuffer();
        toJSONString(key, value, sb);
        return sb.toString();
    }
    
    public static String escape(final String s) {
        return JSONValue.escape(s);
    }
}
