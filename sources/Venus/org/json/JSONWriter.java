/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class JSONWriter {
    private static final int maxdepth = 200;
    private boolean comma = false;
    protected char mode = (char)105;
    private final JSONObject[] stack = new JSONObject[200];
    private int top = 0;
    protected Appendable writer;

    public JSONWriter(Appendable appendable) {
        this.writer = appendable;
    }

    private JSONWriter append(String string) throws JSONException {
        if (string == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.append(',');
                }
                this.writer.append(string);
            } catch (IOException iOException) {
                throw new JSONException(iOException);
            }
            if (this.mode == 'o') {
                this.mode = (char)107;
            }
            this.comma = true;
            return this;
        }
        throw new JSONException("Value out of sequence.");
    }

    public JSONWriter array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push(null);
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    private JSONWriter end(char c, char c2) throws JSONException {
        if (this.mode != c) {
            throw new JSONException(c == 'a' ? "Misplaced endArray." : "Misplaced endObject.");
        }
        this.pop(c);
        try {
            this.writer.append(c2);
        } catch (IOException iOException) {
            throw new JSONException(iOException);
        }
        this.comma = true;
        return this;
    }

    public JSONWriter endArray() throws JSONException {
        return this.end('a', ']');
    }

    public JSONWriter endObject() throws JSONException {
        return this.end('k', '}');
    }

    public JSONWriter key(String string) throws JSONException {
        if (string == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                JSONObject jSONObject = this.stack[this.top - 1];
                if (jSONObject.has(string)) {
                    throw new JSONException("Duplicate key \"" + string + "\"");
                }
                jSONObject.put(string, false);
                if (this.comma) {
                    this.writer.append(',');
                }
                this.writer.append(JSONObject.quote(string));
                this.writer.append(':');
                this.comma = false;
                this.mode = (char)111;
                return this;
            } catch (IOException iOException) {
                throw new JSONException(iOException);
            }
        }
        throw new JSONException("Misplaced key.");
    }

    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = (char)111;
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push(new JSONObject());
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    private void pop(char c) throws JSONException {
        char c2;
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        }
        char c3 = c2 = this.stack[this.top - 1] == null ? (char)'a' : 'k';
        if (c2 != c) {
            throw new JSONException("Nesting error.");
        }
        --this.top;
        this.mode = (char)(this.top == 0 ? 100 : (this.stack[this.top - 1] == null ? 97 : 107));
    }

    private void push(JSONObject jSONObject) throws JSONException {
        if (this.top >= 200) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = jSONObject;
        this.mode = (char)(jSONObject == null ? 97 : 107);
        ++this.top;
    }

    public static String valueToString(Object object) throws JSONException {
        if (object == null || object.equals(null)) {
            return "null";
        }
        if (object instanceof JSONString) {
            String string;
            try {
                string = ((JSONString)object).toJSONString();
            } catch (Exception exception) {
                throw new JSONException(exception);
            }
            if (string != null) {
                return string;
            }
            throw new JSONException("Bad value from toJSONString: " + string);
        }
        if (object instanceof Number) {
            String string = JSONObject.numberToString((Number)object);
            if (JSONObject.NUMBER_PATTERN.matcher(string).matches()) {
                return string;
            }
            return JSONObject.quote(string);
        }
        if (object instanceof Boolean || object instanceof JSONObject || object instanceof JSONArray) {
            return object.toString();
        }
        if (object instanceof Map) {
            Map map = (Map)object;
            return new JSONObject(map).toString();
        }
        if (object instanceof Collection) {
            Collection collection = (Collection)object;
            return new JSONArray(collection).toString();
        }
        if (object.getClass().isArray()) {
            return new JSONArray(object).toString();
        }
        if (object instanceof Enum) {
            return JSONObject.quote(((Enum)object).name());
        }
        return JSONObject.quote(object.toString());
    }

    public JSONWriter value(boolean bl) throws JSONException {
        return this.append(bl ? "true" : "false");
    }

    public JSONWriter value(double d) throws JSONException {
        return this.value((Object)d);
    }

    public JSONWriter value(long l) throws JSONException {
        return this.append(Long.toString(l));
    }

    public JSONWriter value(Object object) throws JSONException {
        return this.append(JSONWriter.valueToString(object));
    }
}

