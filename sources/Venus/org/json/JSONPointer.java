/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

public class JSONPointer {
    private static final String ENCODING = "utf-8";
    private final List<String> refTokens;

    public static Builder builder() {
        return new Builder();
    }

    public JSONPointer(String string) {
        String string2;
        if (string == null) {
            throw new NullPointerException("pointer cannot be null");
        }
        if (string.isEmpty() || string.equals("#")) {
            this.refTokens = Collections.emptyList();
            return;
        }
        if (string.startsWith("#/")) {
            string2 = string.substring(2);
            try {
                string2 = URLDecoder.decode(string2, ENCODING);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException(unsupportedEncodingException);
            }
        } else if (string.startsWith("/")) {
            string2 = string.substring(1);
        } else {
            throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
        }
        this.refTokens = new ArrayList<String>();
        int n = -1;
        int n2 = 0;
        do {
            String string3;
            if ((n2 = n + 1) == (n = string2.indexOf(47, n2)) || n2 == string2.length()) {
                this.refTokens.add("");
                continue;
            }
            if (n >= 0) {
                string3 = string2.substring(n2, n);
                this.refTokens.add(JSONPointer.unescape(string3));
                continue;
            }
            string3 = string2.substring(n2);
            this.refTokens.add(JSONPointer.unescape(string3));
        } while (n >= 0);
    }

    public JSONPointer(List<String> list) {
        this.refTokens = new ArrayList<String>(list);
    }

    private static String unescape(String string) {
        return string.replace("~1", "/").replace("~0", "~");
    }

    public Object queryFrom(Object object) throws JSONPointerException {
        if (this.refTokens.isEmpty()) {
            return object;
        }
        Object object2 = object;
        for (String string : this.refTokens) {
            if (object2 instanceof JSONObject) {
                object2 = ((JSONObject)object2).opt(JSONPointer.unescape(string));
                continue;
            }
            if (object2 instanceof JSONArray) {
                object2 = JSONPointer.readByIndexToken(object2, string);
                continue;
            }
            throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", object2, string));
        }
        return object2;
    }

    private static Object readByIndexToken(Object object, String string) throws JSONPointerException {
        try {
            int n = Integer.parseInt(string);
            JSONArray jSONArray = (JSONArray)object;
            if (n >= jSONArray.length()) {
                throw new JSONPointerException(String.format("index %s is out of bounds - the array has %d elements", string, jSONArray.length()));
            }
            try {
                return jSONArray.get(n);
            } catch (JSONException jSONException) {
                throw new JSONPointerException("Error reading value at index position " + n, jSONException);
            }
        } catch (NumberFormatException numberFormatException) {
            throw new JSONPointerException(String.format("%s is not an array index", string), numberFormatException);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (String string : this.refTokens) {
            stringBuilder.append('/').append(JSONPointer.escape(string));
        }
        return stringBuilder.toString();
    }

    private static String escape(String string) {
        return string.replace("~", "~0").replace("/", "~1");
    }

    public String toURIFragment() {
        try {
            StringBuilder stringBuilder = new StringBuilder("#");
            for (String string : this.refTokens) {
                stringBuilder.append('/').append(URLEncoder.encode(string, ENCODING));
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static class Builder {
        private final List<String> refTokens = new ArrayList<String>();

        public JSONPointer build() {
            return new JSONPointer(this.refTokens);
        }

        public Builder append(String string) {
            if (string == null) {
                throw new NullPointerException("token cannot be null");
            }
            this.refTokens.add(string);
            return this;
        }

        public Builder append(int n) {
            this.refTokens.add(String.valueOf(n));
            return this;
        }
    }
}

