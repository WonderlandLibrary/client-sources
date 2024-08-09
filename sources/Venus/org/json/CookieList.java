/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import org.json.Cookie;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CookieList {
    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(string);
        while (jSONTokener.more()) {
            String string2 = Cookie.unescape(jSONTokener.nextTo('='));
            jSONTokener.next('=');
            jSONObject.put(string2, Cookie.unescape(jSONTokener.nextTo(';')));
            jSONTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        boolean bl = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : jSONObject.keySet()) {
            Object object = jSONObject.opt(string);
            if (JSONObject.NULL.equals(object)) continue;
            if (bl) {
                stringBuilder.append(';');
            }
            stringBuilder.append(Cookie.escape(string));
            stringBuilder.append("=");
            stringBuilder.append(Cookie.escape(object.toString()));
            bl = true;
        }
        return stringBuilder.toString();
    }
}

