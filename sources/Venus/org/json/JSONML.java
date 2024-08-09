/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONMLParserConfiguration;
import org.json.JSONObject;
import org.json.XML;
import org.json.XMLTokener;

public class JSONML {
    private static Object parse(XMLTokener xMLTokener, boolean bl, JSONArray jSONArray, boolean bl2, int n) throws JSONException {
        return JSONML.parse(xMLTokener, bl, jSONArray, bl2 ? JSONMLParserConfiguration.KEEP_STRINGS : JSONMLParserConfiguration.ORIGINAL, n);
    }

    private static Object parse(XMLTokener xMLTokener, boolean bl, JSONArray jSONArray, JSONMLParserConfiguration jSONMLParserConfiguration, int n) throws JSONException {
        String string = null;
        JSONArray jSONArray2 = null;
        JSONObject jSONObject = null;
        String string2 = null;
        while (true) {
            if (!xMLTokener.more()) {
                throw xMLTokener.syntaxError("Bad XML");
            }
            Object object = xMLTokener.nextContent();
            if (object == XML.LT) {
                object = xMLTokener.nextToken();
                if (object instanceof Character) {
                    if (object == XML.SLASH) {
                        object = xMLTokener.nextToken();
                        if (!(object instanceof String)) {
                            throw new JSONException("Expected a closing name instead of '" + object + "'.");
                        }
                        if (xMLTokener.nextToken() != XML.GT) {
                            throw xMLTokener.syntaxError("Misshaped close tag");
                        }
                        return object;
                    }
                    if (object == XML.BANG) {
                        char c = xMLTokener.next();
                        if (c == '-') {
                            if (xMLTokener.next() == '-') {
                                xMLTokener.skipPast("-->");
                                continue;
                            }
                            xMLTokener.back();
                            continue;
                        }
                        if (c == '[') {
                            object = xMLTokener.nextToken();
                            if (object.equals("CDATA") && xMLTokener.next() == '[') {
                                if (jSONArray == null) continue;
                                jSONArray.put(xMLTokener.nextCDATA());
                                continue;
                            }
                            throw xMLTokener.syntaxError("Expected 'CDATA['");
                        }
                        int n2 = 1;
                        do {
                            if ((object = xMLTokener.nextMeta()) == null) {
                                throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                            }
                            if (object == XML.LT) {
                                ++n2;
                                continue;
                            }
                            if (object != XML.GT) continue;
                            --n2;
                        } while (n2 > 0);
                        continue;
                    }
                    if (object == XML.QUEST) {
                        xMLTokener.skipPast("?>");
                        continue;
                    }
                    throw xMLTokener.syntaxError("Misshaped tag");
                }
                if (!(object instanceof String)) {
                    throw xMLTokener.syntaxError("Bad tagName '" + object + "'.");
                }
                string2 = (String)object;
                jSONArray2 = new JSONArray();
                jSONObject = new JSONObject();
                if (bl) {
                    jSONArray2.put(string2);
                    if (jSONArray != null) {
                        jSONArray.put(jSONArray2);
                    }
                } else {
                    jSONObject.put("tagName", string2);
                    if (jSONArray != null) {
                        jSONArray.put(jSONObject);
                    }
                }
                object = null;
                while (true) {
                    if (object == null) {
                        object = xMLTokener.nextToken();
                    }
                    if (object == null) {
                        throw xMLTokener.syntaxError("Misshaped tag");
                    }
                    if (!(object instanceof String)) break;
                    String string3 = (String)object;
                    if (!bl && ("tagName".equals(string3) || "childNode".equals(string3))) {
                        throw xMLTokener.syntaxError("Reserved attribute.");
                    }
                    object = xMLTokener.nextToken();
                    if (object == XML.EQ) {
                        object = xMLTokener.nextToken();
                        if (!(object instanceof String)) {
                            throw xMLTokener.syntaxError("Missing value");
                        }
                        jSONObject.accumulate(string3, jSONMLParserConfiguration.isKeepStrings() ? (String)object : XML.stringToValue((String)object));
                        object = null;
                        continue;
                    }
                    jSONObject.accumulate(string3, "");
                }
                if (bl && jSONObject.length() > 0) {
                    jSONArray2.put(jSONObject);
                }
                if (object == XML.SLASH) {
                    if (xMLTokener.nextToken() != XML.GT) {
                        throw xMLTokener.syntaxError("Misshaped tag");
                    }
                    if (jSONArray != null) continue;
                    if (bl) {
                        return jSONArray2;
                    }
                    return jSONObject;
                }
                if (object != XML.GT) {
                    throw xMLTokener.syntaxError("Misshaped tag");
                }
                if (n == jSONMLParserConfiguration.getMaxNestingDepth()) {
                    throw xMLTokener.syntaxError("Maximum nesting depth of " + jSONMLParserConfiguration.getMaxNestingDepth() + " reached");
                }
                string = (String)JSONML.parse(xMLTokener, bl, jSONArray2, jSONMLParserConfiguration, n + 1);
                if (string == null) continue;
                if (!string.equals(string2)) {
                    throw xMLTokener.syntaxError("Mismatched '" + string2 + "' and '" + string + "'");
                }
                string2 = null;
                if (!bl && jSONArray2.length() > 0) {
                    jSONObject.put("childNodes", jSONArray2);
                }
                if (jSONArray != null) continue;
                if (bl) {
                    return jSONArray2;
                }
                return jSONObject;
            }
            if (jSONArray == null) continue;
            jSONArray.put(object instanceof String ? (jSONMLParserConfiguration.isKeepStrings() ? XML.unescape((String)object) : XML.stringToValue((String)object)) : object);
        }
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, JSONMLParserConfiguration.ORIGINAL, 0);
    }

    public static JSONArray toJSONArray(String string, boolean bl) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, bl, 0);
    }

    public static JSONArray toJSONArray(String string, JSONMLParserConfiguration jSONMLParserConfiguration) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, jSONMLParserConfiguration, 0);
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener, JSONMLParserConfiguration jSONMLParserConfiguration) throws JSONException {
        return (JSONArray)JSONML.parse(xMLTokener, true, null, jSONMLParserConfiguration, 0);
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener, boolean bl) throws JSONException {
        return (JSONArray)JSONML.parse(xMLTokener, true, null, bl, 0);
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener) throws JSONException {
        return (JSONArray)JSONML.parse(xMLTokener, true, null, false, 0);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, false, 0);
    }

    public static JSONObject toJSONObject(String string, boolean bl) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, bl, 0);
    }

    public static JSONObject toJSONObject(String string, JSONMLParserConfiguration jSONMLParserConfiguration) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, jSONMLParserConfiguration, 0);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener) throws JSONException {
        return (JSONObject)JSONML.parse(xMLTokener, false, null, false, 0);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener, boolean bl) throws JSONException {
        return (JSONObject)JSONML.parse(xMLTokener, false, null, bl, 0);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener, JSONMLParserConfiguration jSONMLParserConfiguration) throws JSONException {
        return (JSONObject)JSONML.parse(xMLTokener, false, null, jSONMLParserConfiguration, 0);
    }

    public static String toString(JSONArray jSONArray) throws JSONException {
        int n;
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        String string = jSONArray.getString(0);
        XML.noSpace(string);
        string = XML.escape(string);
        stringBuilder.append('<');
        stringBuilder.append(string);
        Object object = jSONArray.opt(1);
        if (object instanceof JSONObject) {
            n2 = 2;
            JSONObject jSONObject = (JSONObject)object;
            for (String string2 : jSONObject.keySet()) {
                Object object2 = jSONObject.opt(string2);
                XML.noSpace(string2);
                if (object2 == null) continue;
                stringBuilder.append(' ');
                stringBuilder.append(XML.escape(string2));
                stringBuilder.append('=');
                stringBuilder.append('\"');
                stringBuilder.append(XML.escape(object2.toString()));
                stringBuilder.append('\"');
            }
        } else {
            n2 = 1;
        }
        if (n2 >= (n = jSONArray.length())) {
            stringBuilder.append('/');
            stringBuilder.append('>');
        } else {
            stringBuilder.append('>');
            do {
                object = jSONArray.get(n2);
                ++n2;
                if (object == null) continue;
                if (object instanceof String) {
                    stringBuilder.append(XML.escape(object.toString()));
                    continue;
                }
                if (object instanceof JSONObject) {
                    stringBuilder.append(JSONML.toString((JSONObject)object));
                    continue;
                }
                if (object instanceof JSONArray) {
                    stringBuilder.append(JSONML.toString((JSONArray)object));
                    continue;
                }
                stringBuilder.append(object.toString());
            } while (n2 < n);
            stringBuilder.append('<');
            stringBuilder.append('/');
            stringBuilder.append(string);
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        String string = jSONObject.optString("tagName");
        if (string == null) {
            return XML.escape(jSONObject.toString());
        }
        XML.noSpace(string);
        string = XML.escape(string);
        stringBuilder.append('<');
        stringBuilder.append(string);
        for (String string2 : jSONObject.keySet()) {
            if ("tagName".equals(string2) || "childNodes".equals(string2)) continue;
            XML.noSpace(string2);
            Object object = jSONObject.opt(string2);
            if (object == null) continue;
            stringBuilder.append(' ');
            stringBuilder.append(XML.escape(string2));
            stringBuilder.append('=');
            stringBuilder.append('\"');
            stringBuilder.append(XML.escape(object.toString()));
            stringBuilder.append('\"');
        }
        JSONArray jSONArray = jSONObject.optJSONArray("childNodes");
        if (jSONArray == null) {
            stringBuilder.append('/');
            stringBuilder.append('>');
        } else {
            stringBuilder.append('>');
            int n = jSONArray.length();
            for (int i = 0; i < n; ++i) {
                Object object = jSONArray.get(i);
                if (object == null) continue;
                if (object instanceof String) {
                    stringBuilder.append(XML.escape(object.toString()));
                    continue;
                }
                if (object instanceof JSONObject) {
                    stringBuilder.append(JSONML.toString((JSONObject)object));
                    continue;
                }
                if (object instanceof JSONArray) {
                    stringBuilder.append(JSONML.toString((JSONArray)object));
                    continue;
                }
                stringBuilder.append(object.toString());
            }
            stringBuilder.append('<');
            stringBuilder.append('/');
            stringBuilder.append(string);
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }
}

