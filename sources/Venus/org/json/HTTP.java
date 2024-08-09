/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.util.Locale;
import org.json.HTTPTokener;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTP {
    public static final String CRLF = "\r\n";

    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        HTTPTokener hTTPTokener = new HTTPTokener(string);
        String string2 = hTTPTokener.nextToken();
        if (string2.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
            jSONObject.put("HTTP-Version", string2);
            jSONObject.put("Status-Code", hTTPTokener.nextToken());
            jSONObject.put("Reason-Phrase", hTTPTokener.nextTo('\u0000'));
            hTTPTokener.next();
        } else {
            jSONObject.put("Method", string2);
            jSONObject.put("Request-URI", hTTPTokener.nextToken());
            jSONObject.put("HTTP-Version", hTTPTokener.nextToken());
        }
        while (hTTPTokener.more()) {
            String string3 = hTTPTokener.nextTo(':');
            hTTPTokener.next(':');
            jSONObject.put(string3, hTTPTokener.nextTo('\u0000'));
            hTTPTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        if (jSONObject.has("Status-Code") && jSONObject.has("Reason-Phrase")) {
            stringBuilder.append(jSONObject.getString("HTTP-Version"));
            stringBuilder.append(' ');
            stringBuilder.append(jSONObject.getString("Status-Code"));
            stringBuilder.append(' ');
            stringBuilder.append(jSONObject.getString("Reason-Phrase"));
        } else if (jSONObject.has("Method") && jSONObject.has("Request-URI")) {
            stringBuilder.append(jSONObject.getString("Method"));
            stringBuilder.append(' ');
            stringBuilder.append('\"');
            stringBuilder.append(jSONObject.getString("Request-URI"));
            stringBuilder.append('\"');
            stringBuilder.append(' ');
            stringBuilder.append(jSONObject.getString("HTTP-Version"));
        } else {
            throw new JSONException("Not enough material for an HTTP header.");
        }
        stringBuilder.append(CRLF);
        for (String string : jSONObject.keySet()) {
            String string2 = jSONObject.optString(string);
            if ("HTTP-Version".equals(string) || "Status-Code".equals(string) || "Reason-Phrase".equals(string) || "Method".equals(string) || "Request-URI".equals(string) || JSONObject.NULL.equals(string2)) continue;
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(jSONObject.optString(string));
            stringBuilder.append(CRLF);
        }
        stringBuilder.append(CRLF);
        return stringBuilder.toString();
    }
}

