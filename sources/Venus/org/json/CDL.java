/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CDL {
    private static String getValue(JSONTokener jSONTokener) throws JSONException {
        char c;
        while ((c = jSONTokener.next()) == ' ' || c == '\t') {
        }
        switch (c) {
            case '\u0000': {
                return null;
            }
            case '\"': 
            case '\'': {
                char c2 = c;
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    char c3;
                    if ((c = jSONTokener.next()) == c2 && (c3 = jSONTokener.next()) != '\"') {
                        if (c3 <= '\u0000') break;
                        jSONTokener.back();
                        break;
                    }
                    if (c == '\u0000' || c == '\n' || c == '\r') {
                        throw jSONTokener.syntaxError("Missing close quote '" + c2 + "'.");
                    }
                    stringBuilder.append(c);
                }
                return stringBuilder.toString();
            }
            case ',': {
                jSONTokener.back();
                return "";
            }
        }
        jSONTokener.back();
        return jSONTokener.nextTo(',');
    }

    public static JSONArray rowToJSONArray(JSONTokener jSONTokener) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        block0: while (true) {
            String string = CDL.getValue(jSONTokener);
            char c = jSONTokener.next();
            if (string == null || jSONArray.length() == 0 && string.length() == 0 && c != ',') {
                return null;
            }
            jSONArray.put(string);
            while (true) {
                if (c == ',') continue block0;
                if (c != ' ') {
                    if (c == '\n' || c == '\r' || c == '\u0000') {
                        return jSONArray;
                    }
                    throw jSONTokener.syntaxError("Bad character '" + c + "' (" + c + ").");
                }
                c = jSONTokener.next();
            }
            break;
        }
    }

    public static JSONObject rowToJSONObject(JSONArray jSONArray, JSONTokener jSONTokener) throws JSONException {
        JSONArray jSONArray2 = CDL.rowToJSONArray(jSONTokener);
        return jSONArray2 != null ? jSONArray2.toJSONObject(jSONArray) : null;
    }

    public static String rowToString(JSONArray jSONArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jSONArray.length(); ++i) {
            Object object;
            if (i > 0) {
                stringBuilder.append(',');
            }
            if ((object = jSONArray.opt(i)) == null) continue;
            String string = object.toString();
            if (string.length() > 0 && (string.indexOf(44) >= 0 || string.indexOf(10) >= 0 || string.indexOf(13) >= 0 || string.indexOf(0) >= 0 || string.charAt(0) == '\"')) {
                stringBuilder.append('\"');
                int n = string.length();
                for (int j = 0; j < n; ++j) {
                    char c = string.charAt(j);
                    if (c < ' ' || c == '\"') continue;
                    stringBuilder.append(c);
                }
                stringBuilder.append('\"');
                continue;
            }
            stringBuilder.append(string);
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return CDL.toJSONArray(new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONTokener jSONTokener) throws JSONException {
        return CDL.toJSONArray(CDL.rowToJSONArray(jSONTokener), jSONTokener);
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, String string) throws JSONException {
        return CDL.toJSONArray(jSONArray, new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, JSONTokener jSONTokener) throws JSONException {
        JSONObject jSONObject;
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        while ((jSONObject = CDL.rowToJSONObject(jSONArray, jSONTokener)) != null) {
            jSONArray2.put(jSONObject);
        }
        if (jSONArray2.length() == 0) {
            return null;
        }
        return jSONArray2;
    }

    public static String toString(JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2;
        JSONObject jSONObject = jSONArray.optJSONObject(0);
        if (jSONObject != null && (jSONArray2 = jSONObject.names()) != null) {
            return CDL.rowToString(jSONArray2) + CDL.toString(jSONArray2, jSONArray);
        }
        return null;
    }

    public static String toString(JSONArray jSONArray, JSONArray jSONArray2) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jSONArray2.length(); ++i) {
            JSONObject jSONObject = jSONArray2.optJSONObject(i);
            if (jSONObject == null) continue;
            stringBuilder.append(CDL.rowToString(jSONObject.toJSONArray(jSONArray)));
        }
        return stringBuilder.toString();
    }
}

