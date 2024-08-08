// 
// Decompiled by Procyon v0.5.36
// 

package org.json.simple;

import java.util.List;
import java.util.Map;
import java.io.Writer;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.StringReader;
import org.json.simple.parser.JSONParser;
import java.io.Reader;

public class JSONValue
{
    public static Object parse(final Reader in) {
        try {
            final JSONParser parser = new JSONParser();
            return parser.parse(in);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Object parse(final String s) {
        final StringReader in = new StringReader(s);
        return parse(in);
    }
    
    public static Object parseWithException(final Reader in) throws IOException, ParseException {
        final JSONParser parser = new JSONParser();
        return parser.parse(in);
    }
    
    public static Object parseWithException(final String s) throws ParseException {
        final JSONParser parser = new JSONParser();
        return parser.parse(s);
    }
    
    public static void writeJSONString(final Object value, final Writer out) throws IOException {
        if (value == null) {
            out.write("null");
            return;
        }
        if (value instanceof String) {
            out.write(34);
            out.write(escape((String)value));
            out.write(34);
            return;
        }
        if (value instanceof Double) {
            if (((Double)value).isInfinite() || ((Double)value).isNaN()) {
                out.write("null");
            }
            else {
                out.write(value.toString());
            }
            return;
        }
        if (value instanceof Float) {
            if (((Float)value).isInfinite() || ((Float)value).isNaN()) {
                out.write("null");
            }
            else {
                out.write(value.toString());
            }
            return;
        }
        if (value instanceof Number) {
            out.write(value.toString());
            return;
        }
        if (value instanceof Boolean) {
            out.write(value.toString());
            return;
        }
        if (value instanceof JSONStreamAware) {
            ((JSONStreamAware)value).writeJSONString(out);
            return;
        }
        if (value instanceof JSONAware) {
            out.write(((JSONAware)value).toJSONString());
            return;
        }
        if (value instanceof Map) {
            JSONObject.writeJSONString((Map)value, out);
            return;
        }
        if (value instanceof List) {
            JSONArray.writeJSONString((List)value, out);
            return;
        }
        out.write(value.toString());
    }
    
    public static String toJSONString(final Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + escape((String)value) + "\"";
        }
        if (value instanceof Double) {
            if (((Double)value).isInfinite() || ((Double)value).isNaN()) {
                return "null";
            }
            return value.toString();
        }
        else if (value instanceof Float) {
            if (((Float)value).isInfinite() || ((Float)value).isNaN()) {
                return "null";
            }
            return value.toString();
        }
        else {
            if (value instanceof Number) {
                return value.toString();
            }
            if (value instanceof Boolean) {
                return value.toString();
            }
            if (value instanceof JSONAware) {
                return ((JSONAware)value).toJSONString();
            }
            if (value instanceof Map) {
                return JSONObject.toJSONString((Map)value);
            }
            if (value instanceof List) {
                return JSONArray.toJSONString((List)value);
            }
            return value.toString();
        }
    }
    
    public static String escape(final String s) {
        if (s == null) {
            return null;
        }
        final StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }
    
    static void escape(final String s, final StringBuffer sb) {
        for (int i = 0; i < s.length(); ++i) {
            final char ch = s.charAt(i);
            switch (ch) {
                case '\"': {
                    sb.append("\\\"");
                    break;
                }
                case '\\': {
                    sb.append("\\\\");
                    break;
                }
                case '\b': {
                    sb.append("\\b");
                    break;
                }
                case '\f': {
                    sb.append("\\f");
                    break;
                }
                case '\n': {
                    sb.append("\\n");
                    break;
                }
                case '\r': {
                    sb.append("\\r");
                    break;
                }
                case '\t': {
                    sb.append("\\t");
                    break;
                }
                case '/': {
                    sb.append("\\/");
                    break;
                }
                default: {
                    if ((ch >= '\0' && ch <= '\u001f') || (ch >= '\u007f' && ch <= '\u009f') || (ch >= '\u2000' && ch <= '\u20ff')) {
                        final String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); ++k) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                        break;
                    }
                    sb.append(ch);
                    break;
                }
            }
        }
    }
}
