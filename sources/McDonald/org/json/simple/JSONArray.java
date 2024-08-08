// 
// Decompiled by Procyon v0.5.36
// 

package org.json.simple;

import java.io.IOException;
import java.util.Iterator;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = 3957988303675231981L;
    
    public static void writeJSONString(final List list, final Writer out) throws IOException {
        if (list == null) {
            out.write("null");
            return;
        }
        boolean first = true;
        final Iterator iter = list.iterator();
        out.write(91);
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                out.write(44);
            }
            final Object value = iter.next();
            if (value == null) {
                out.write("null");
            }
            else {
                JSONValue.writeJSONString(value, out);
            }
        }
        out.write(93);
    }
    
    public void writeJSONString(final Writer out) throws IOException {
        writeJSONString(this, out);
    }
    
    public static String toJSONString(final List list) {
        if (list == null) {
            return "null";
        }
        boolean first = true;
        final StringBuffer sb = new StringBuffer();
        final Iterator iter = list.iterator();
        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                sb.append(',');
            }
            final Object value = iter.next();
            if (value == null) {
                sb.append("null");
            }
            else {
                sb.append(JSONValue.toJSONString(value));
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
    public String toJSONString() {
        return toJSONString(this);
    }
    
    public String toString() {
        return this.toJSONString();
    }
}
