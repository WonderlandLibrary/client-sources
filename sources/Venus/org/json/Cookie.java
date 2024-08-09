/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Cookie {
    public static String escape(String string) {
        String string2 = string.trim();
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
                stringBuilder.append('%');
                stringBuilder.append(Character.forDigit((char)(c >>> 4 & 0xF), 16));
                stringBuilder.append(Character.forDigit((char)(c & 0xF), 16));
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static JSONObject toJSONObject(String string) {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(string);
        String string2 = Cookie.unescape(jSONTokener.nextTo('=').trim());
        if ("".equals(string2)) {
            throw new JSONException("Cookies must have a 'name'");
        }
        jSONObject.put("name", string2);
        jSONTokener.next('=');
        jSONObject.put("value", Cookie.unescape(jSONTokener.nextTo(';')).trim());
        jSONTokener.next();
        while (jSONTokener.more()) {
            Object object;
            string2 = Cookie.unescape(jSONTokener.nextTo("=;")).trim().toLowerCase(Locale.ROOT);
            if ("name".equalsIgnoreCase(string2)) {
                throw new JSONException("Illegal attribute name: 'name'");
            }
            if ("value".equalsIgnoreCase(string2)) {
                throw new JSONException("Illegal attribute name: 'value'");
            }
            if (jSONTokener.next() != '=') {
                object = Boolean.TRUE;
            } else {
                object = Cookie.unescape(jSONTokener.nextTo(';')).trim();
                jSONTokener.next();
            }
            if ("".equals(string2) || "".equals(object)) continue;
            jSONObject.put(string2, object);
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        String string = null;
        Object object = null;
        for (String string2 : jSONObject.keySet()) {
            if ("name".equalsIgnoreCase(string2)) {
                string = jSONObject.getString(string2).trim();
            }
            if ("value".equalsIgnoreCase(string2)) {
                object = jSONObject.getString(string2).trim();
            }
            if (string == null || object == null) continue;
            break;
        }
        if (string == null || "".equals(string.trim())) {
            throw new JSONException("Cookie does not have a name");
        }
        if (object == null) {
            object = "";
        }
        stringBuilder.append(Cookie.escape(string));
        stringBuilder.append("=");
        stringBuilder.append(Cookie.escape((String)object));
        for (String string2 : jSONObject.keySet()) {
            if ("name".equalsIgnoreCase(string2) || "value".equalsIgnoreCase(string2)) continue;
            object = jSONObject.opt(string2);
            if (object instanceof Boolean) {
                if (!Boolean.TRUE.equals(object)) continue;
                stringBuilder.append(';').append(Cookie.escape(string2));
                continue;
            }
            stringBuilder.append(';').append(Cookie.escape(string2)).append('=').append(Cookie.escape(object.toString()));
        }
        return stringBuilder.toString();
    }

    public static String unescape(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '+') {
                c = ' ';
            } else if (c == '%' && i + 2 < n) {
                int n2 = JSONTokener.dehexchar(string.charAt(i + 1));
                int n3 = JSONTokener.dehexchar(string.charAt(i + 2));
                if (n2 >= 0 && n3 >= 0) {
                    c = (char)(n2 * 16 + n3);
                    i += 2;
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}

