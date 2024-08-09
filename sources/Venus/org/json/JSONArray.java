/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.JSONPointerException;
import org.json.JSONString;
import org.json.JSONTokener;

public class JSONArray
implements Iterable<Object> {
    private final ArrayList<Object> myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
    }

    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        }
        char c = jSONTokener.nextClean();
        if (c == '\u0000') {
            throw jSONTokener.syntaxError("Expected a ',' or ']'");
        }
        if (c != ']') {
            jSONTokener.back();
            block5: while (true) {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.myArrayList.add(JSONObject.NULL);
                } else {
                    jSONTokener.back();
                    this.myArrayList.add(jSONTokener.nextValue());
                }
                switch (jSONTokener.nextClean()) {
                    case '\u0000': {
                        throw jSONTokener.syntaxError("Expected a ',' or ']'");
                    }
                    case ',': {
                        c = jSONTokener.nextClean();
                        if (c == '\u0000') {
                            throw jSONTokener.syntaxError("Expected a ',' or ']'");
                        }
                        if (c == ']') {
                            return;
                        }
                        jSONTokener.back();
                        continue block5;
                    }
                    case ']': {
                        return;
                    }
                }
                break;
            }
            throw jSONTokener.syntaxError("Expected a ',' or ']'");
        }
    }

    public JSONArray(String string) throws JSONException {
        this(new JSONTokener(string));
    }

    public JSONArray(Collection<?> collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList();
        } else {
            this.myArrayList = new ArrayList(collection.size());
            this.addAll(collection, true);
        }
    }

    public JSONArray(Iterable<?> iterable) {
        this();
        if (iterable == null) {
            return;
        }
        this.addAll(iterable, true);
    }

    public JSONArray(JSONArray jSONArray) {
        this.myArrayList = jSONArray == null ? new ArrayList() : new ArrayList<Object>(jSONArray.myArrayList);
    }

    public JSONArray(Object object) throws JSONException {
        this();
        if (!object.getClass().isArray()) {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
        this.addAll(object, true);
    }

    public JSONArray(int n) throws JSONException {
        if (n < 0) {
            throw new JSONException("JSONArray initial capacity cannot be negative.");
        }
        this.myArrayList = new ArrayList(n);
    }

    @Override
    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }

    public Object get(int n) throws JSONException {
        Object object = this.opt(n);
        if (object == null) {
            throw new JSONException("JSONArray[" + n + "] not found.");
        }
        return object;
    }

    public boolean getBoolean(int n) throws JSONException {
        Object object = this.get(n);
        if (object.equals(Boolean.FALSE) || object instanceof String && ((String)object).equalsIgnoreCase("false")) {
            return true;
        }
        if (object.equals(Boolean.TRUE) || object instanceof String && ((String)object).equalsIgnoreCase("true")) {
            return false;
        }
        throw JSONArray.wrongValueFormatException(n, "boolean", object, null);
    }

    public double getDouble(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        try {
            return Double.parseDouble(object.toString());
        } catch (Exception exception) {
            throw JSONArray.wrongValueFormatException(n, "double", object, exception);
        }
    }

    public float getFloat(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof Number) {
            return ((Number)object).floatValue();
        }
        try {
            return Float.parseFloat(object.toString());
        } catch (Exception exception) {
            throw JSONArray.wrongValueFormatException(n, "float", object, exception);
        }
    }

    public Number getNumber(int n) throws JSONException {
        Object object = this.get(n);
        try {
            if (object instanceof Number) {
                return (Number)object;
            }
            return JSONObject.stringToNumber(object.toString());
        } catch (Exception exception) {
            throw JSONArray.wrongValueFormatException(n, "number", object, exception);
        }
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, int n) throws JSONException {
        E e = this.optEnum(clazz, n);
        if (e == null) {
            throw JSONArray.wrongValueFormatException(n, "enum of type " + JSONObject.quote(clazz.getSimpleName()), this.opt(n), null);
        }
        return e;
    }

    public BigDecimal getBigDecimal(int n) throws JSONException {
        Object object = this.get(n);
        BigDecimal bigDecimal = JSONObject.objectToBigDecimal(object, null);
        if (bigDecimal == null) {
            throw JSONArray.wrongValueFormatException(n, "BigDecimal", object, null);
        }
        return bigDecimal;
    }

    public BigInteger getBigInteger(int n) throws JSONException {
        Object object = this.get(n);
        BigInteger bigInteger = JSONObject.objectToBigInteger(object, null);
        if (bigInteger == null) {
            throw JSONArray.wrongValueFormatException(n, "BigInteger", object, null);
        }
        return bigInteger;
    }

    public int getInt(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception exception) {
            throw JSONArray.wrongValueFormatException(n, "int", object, exception);
        }
    }

    public JSONArray getJSONArray(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw JSONArray.wrongValueFormatException(n, "JSONArray", object, null);
    }

    public JSONObject getJSONObject(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw JSONArray.wrongValueFormatException(n, "JSONObject", object, null);
    }

    public long getLong(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        try {
            return Long.parseLong(object.toString());
        } catch (Exception exception) {
            throw JSONArray.wrongValueFormatException(n, "long", object, exception);
        }
    }

    public String getString(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof String) {
            return (String)object;
        }
        throw JSONArray.wrongValueFormatException(n, "String", object, null);
    }

    public boolean isNull(int n) {
        return JSONObject.NULL.equals(this.opt(n));
    }

    public String join(String string) throws JSONException {
        int n = this.length();
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
        for (int i = 1; i < n; ++i) {
            stringBuilder.append(string).append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return stringBuilder.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public void clear() {
        this.myArrayList.clear();
    }

    public Object opt(int n) {
        return n < 0 || n >= this.length() ? null : this.myArrayList.get(n);
    }

    public boolean optBoolean(int n) {
        return this.optBoolean(n, true);
    }

    public boolean optBoolean(int n, boolean bl) {
        try {
            return this.getBoolean(n);
        } catch (Exception exception) {
            return bl;
        }
    }

    public double optDouble(int n) {
        return this.optDouble(n, Double.NaN);
    }

    public double optDouble(int n, double d) {
        Number number = this.optNumber(n, null);
        if (number == null) {
            return d;
        }
        double d2 = number.doubleValue();
        return d2;
    }

    public float optFloat(int n) {
        return this.optFloat(n, Float.NaN);
    }

    public float optFloat(int n, float f) {
        Number number = this.optNumber(n, null);
        if (number == null) {
            return f;
        }
        float f2 = number.floatValue();
        return f2;
    }

    public int optInt(int n) {
        return this.optInt(n, 0);
    }

    public int optInt(int n, int n2) {
        Number number = this.optNumber(n, null);
        if (number == null) {
            return n2;
        }
        return number.intValue();
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int n) {
        return this.optEnum(clazz, n, null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int n, E e) {
        try {
            Object object = this.opt(n);
            if (JSONObject.NULL.equals(object)) {
                return e;
            }
            if (clazz.isAssignableFrom(object.getClass())) {
                Enum enum_ = (Enum)object;
                return (E)enum_;
            }
            return Enum.valueOf(clazz, object.toString());
        } catch (IllegalArgumentException illegalArgumentException) {
            return e;
        } catch (NullPointerException nullPointerException) {
            return e;
        }
    }

    public BigInteger optBigInteger(int n, BigInteger bigInteger) {
        Object object = this.opt(n);
        return JSONObject.objectToBigInteger(object, bigInteger);
    }

    public BigDecimal optBigDecimal(int n, BigDecimal bigDecimal) {
        Object object = this.opt(n);
        return JSONObject.objectToBigDecimal(object, bigDecimal);
    }

    public JSONArray optJSONArray(int n) {
        Object object = this.opt(n);
        return object instanceof JSONArray ? (JSONArray)object : null;
    }

    public JSONObject optJSONObject(int n) {
        Object object = this.opt(n);
        return object instanceof JSONObject ? (JSONObject)object : null;
    }

    public long optLong(int n) {
        return this.optLong(n, 0L);
    }

    public long optLong(int n, long l) {
        Number number = this.optNumber(n, null);
        if (number == null) {
            return l;
        }
        return number.longValue();
    }

    public Number optNumber(int n) {
        return this.optNumber(n, null);
    }

    public Number optNumber(int n, Number number) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return number;
        }
        if (object instanceof Number) {
            return (Number)object;
        }
        if (object instanceof String) {
            try {
                return JSONObject.stringToNumber((String)object);
            } catch (Exception exception) {
                return number;
            }
        }
        return number;
    }

    public String optString(int n) {
        return this.optString(n, "");
    }

    public String optString(int n, String string) {
        Object object = this.opt(n);
        return JSONObject.NULL.equals(object) ? string : object.toString();
    }

    public JSONArray put(boolean bl) {
        return this.put(bl ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(Collection<?> collection) {
        return this.put(new JSONArray(collection));
    }

    public JSONArray put(double d) throws JSONException {
        return this.put((Object)d);
    }

    public JSONArray put(float f) throws JSONException {
        return this.put(Float.valueOf(f));
    }

    public JSONArray put(int n) {
        return this.put((Object)n);
    }

    public JSONArray put(long l) {
        return this.put((Object)l);
    }

    public JSONArray put(Map<?, ?> map) {
        return this.put(new JSONObject(map));
    }

    public JSONArray put(Object object) {
        JSONObject.testValidity(object);
        this.myArrayList.add(object);
        return this;
    }

    public JSONArray put(int n, boolean bl) throws JSONException {
        return this.put(n, bl ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(int n, Collection<?> collection) throws JSONException {
        return this.put(n, new JSONArray(collection));
    }

    public JSONArray put(int n, double d) throws JSONException {
        return this.put(n, (Object)d);
    }

    public JSONArray put(int n, float f) throws JSONException {
        return this.put(n, Float.valueOf(f));
    }

    public JSONArray put(int n, int n2) throws JSONException {
        return this.put(n, (Object)n2);
    }

    public JSONArray put(int n, long l) throws JSONException {
        return this.put(n, (Object)l);
    }

    public JSONArray put(int n, Map<?, ?> map) throws JSONException {
        this.put(n, new JSONObject(map));
        return this;
    }

    public JSONArray put(int n, Object object) throws JSONException {
        if (n < 0) {
            throw new JSONException("JSONArray[" + n + "] not found.");
        }
        if (n < this.length()) {
            JSONObject.testValidity(object);
            this.myArrayList.set(n, object);
            return this;
        }
        if (n == this.length()) {
            return this.put(object);
        }
        this.myArrayList.ensureCapacity(n + 1);
        while (n != this.length()) {
            this.myArrayList.add(JSONObject.NULL);
        }
        return this.put(object);
    }

    public JSONArray putAll(Collection<?> collection) {
        this.addAll(collection, false);
        return this;
    }

    public JSONArray putAll(Iterable<?> iterable) {
        this.addAll(iterable, false);
        return this;
    }

    public JSONArray putAll(JSONArray jSONArray) {
        this.myArrayList.addAll(jSONArray.myArrayList);
        return this;
    }

    public JSONArray putAll(Object object) throws JSONException {
        this.addAll(object, false);
        return this;
    }

    public Object query(String string) {
        return this.query(new JSONPointer(string));
    }

    public Object query(JSONPointer jSONPointer) {
        return jSONPointer.queryFrom(this);
    }

    public Object optQuery(String string) {
        return this.optQuery(new JSONPointer(string));
    }

    public Object optQuery(JSONPointer jSONPointer) {
        try {
            return jSONPointer.queryFrom(this);
        } catch (JSONPointerException jSONPointerException) {
            return null;
        }
    }

    public Object remove(int n) {
        return n >= 0 && n < this.length() ? this.myArrayList.remove(n) : null;
    }

    public boolean similar(Object object) {
        if (!(object instanceof JSONArray)) {
            return true;
        }
        int n = this.length();
        if (n != ((JSONArray)object).length()) {
            return true;
        }
        for (int i = 0; i < n; ++i) {
            Object object2;
            Object object3 = this.myArrayList.get(i);
            if (object3 == (object2 = ((JSONArray)object).myArrayList.get(i))) continue;
            if (object3 == null) {
                return true;
            }
            if (!(object3 instanceof JSONObject ? !((JSONObject)object3).similar(object2) : (object3 instanceof JSONArray ? !((JSONArray)object3).similar(object2) : (object3 instanceof Number && object2 instanceof Number ? !JSONObject.isNumberSimilar((Number)object3, (Number)object2) : (object3 instanceof JSONString && object2 instanceof JSONString ? !((JSONString)object3).toJSONString().equals(((JSONString)object2).toJSONString()) : !object3.equals(object2)))))) continue;
            return true;
        }
        return false;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.isEmpty() || this.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); ++i) {
            jSONObject.put(jSONArray.getString(i), this.opt(i));
        }
        return jSONObject;
    }

    public String toString() {
        try {
            return this.toString(0);
        } catch (Exception exception) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString(int n) throws JSONException {
        StringWriter stringWriter = new StringWriter();
        StringBuffer stringBuffer = stringWriter.getBuffer();
        synchronized (stringBuffer) {
            return this.write(stringWriter, n, 0).toString();
        }
    }

    public Writer write(Writer writer) throws JSONException {
        return this.write(writer, 0, 0);
    }

    public Writer write(Writer writer, int n, int n2) throws JSONException {
        try {
            boolean bl = false;
            int n3 = this.length();
            writer.write(91);
            if (n3 == 1) {
                try {
                    JSONObject.writeValue(writer, this.myArrayList.get(0), n, n2);
                } catch (Exception exception) {
                    throw new JSONException("Unable to write JSONArray value at index: 0", exception);
                }
            }
            if (n3 != 0) {
                int n4 = n2 + n;
                for (int i = 0; i < n3; ++i) {
                    if (bl) {
                        writer.write(44);
                    }
                    if (n > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, n4);
                    try {
                        JSONObject.writeValue(writer, this.myArrayList.get(i), n, n4);
                    } catch (Exception exception) {
                        throw new JSONException("Unable to write JSONArray value at index: " + i, exception);
                    }
                    bl = true;
                }
                if (n > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, n2);
            }
            writer.write(93);
            return writer;
        } catch (IOException iOException) {
            throw new JSONException(iOException);
        }
    }

    public List<Object> toList() {
        ArrayList<Object> arrayList = new ArrayList<Object>(this.myArrayList.size());
        for (Object object : this.myArrayList) {
            if (object == null || JSONObject.NULL.equals(object)) {
                arrayList.add(null);
                continue;
            }
            if (object instanceof JSONArray) {
                arrayList.add(((JSONArray)object).toList());
                continue;
            }
            if (object instanceof JSONObject) {
                arrayList.add(((JSONObject)object).toMap());
                continue;
            }
            arrayList.add(object);
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }

    private void addAll(Collection<?> collection, boolean bl) {
        this.myArrayList.ensureCapacity(this.myArrayList.size() + collection.size());
        if (bl) {
            for (Object obj : collection) {
                this.put(JSONObject.wrap(obj));
            }
        } else {
            for (Object obj : collection) {
                this.put(obj);
            }
        }
    }

    private void addAll(Iterable<?> iterable, boolean bl) {
        if (bl) {
            for (Object obj : iterable) {
                this.put(JSONObject.wrap(obj));
            }
        } else {
            for (Object obj : iterable) {
                this.put(obj);
            }
        }
    }

    private void addAll(Object object, boolean bl) throws JSONException {
        if (object.getClass().isArray()) {
            int n = Array.getLength(object);
            this.myArrayList.ensureCapacity(this.myArrayList.size() + n);
            if (bl) {
                for (int i = 0; i < n; ++i) {
                    this.put(JSONObject.wrap(Array.get(object, i)));
                }
            } else {
                for (int i = 0; i < n; ++i) {
                    this.put(Array.get(object, i));
                }
            }
        } else if (object instanceof JSONArray) {
            this.myArrayList.addAll(((JSONArray)object).myArrayList);
        } else if (object instanceof Collection) {
            this.addAll((Collection)object, bl);
        } else if (object instanceof Iterable) {
            this.addAll((Iterable)object, bl);
        } else {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
    }

    private static JSONException wrongValueFormatException(int n, String string, Object object, Throwable throwable) {
        if (object == null) {
            return new JSONException("JSONArray[" + n + "] is not a " + string + " (null).", throwable);
        }
        if (object instanceof Map || object instanceof Iterable || object instanceof JSONObject) {
            return new JSONException("JSONArray[" + n + "] is not a " + string + " (" + object.getClass() + ").", throwable);
        }
        return new JSONException("JSONArray[" + n + "] is not a " + string + " (" + object.getClass() + " : " + object + ").", throwable);
    }
}

