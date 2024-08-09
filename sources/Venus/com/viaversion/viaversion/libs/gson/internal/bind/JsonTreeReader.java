/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public final class JsonTreeReader
extends JsonReader {
    private static final Reader UNREADABLE_READER = new Reader(){

        @Override
        public int read(char[] cArray, int n, int n2) {
            throw new AssertionError();
        }

        @Override
        public void close() {
            throw new AssertionError();
        }
    };
    private static final Object SENTINEL_CLOSED = new Object();
    private Object[] stack = new Object[32];
    private int stackSize = 0;
    private String[] pathNames = new String[32];
    private int[] pathIndices = new int[32];

    public JsonTreeReader(JsonElement jsonElement) {
        super(UNREADABLE_READER);
        this.push(jsonElement);
    }

    @Override
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
        JsonArray jsonArray = (JsonArray)this.peekStack();
        this.push(jsonArray.iterator());
        this.pathIndices[this.stackSize - 1] = 0;
    }

    @Override
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
    }

    @Override
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
        JsonObject jsonObject = (JsonObject)this.peekStack();
        this.push(jsonObject.entrySet().iterator());
    }

    @Override
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
        this.pathNames[this.stackSize - 1] = null;
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
    }

    @Override
    public boolean hasNext() throws IOException {
        JsonToken jsonToken = this.peek();
        return jsonToken != JsonToken.END_OBJECT && jsonToken != JsonToken.END_ARRAY && jsonToken != JsonToken.END_DOCUMENT;
    }

    @Override
    public JsonToken peek() throws IOException {
        if (this.stackSize == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object object = this.peekStack();
        if (object instanceof Iterator) {
            boolean bl = this.stack[this.stackSize - 2] instanceof JsonObject;
            Iterator iterator2 = (Iterator)object;
            if (iterator2.hasNext()) {
                if (bl) {
                    return JsonToken.NAME;
                }
                this.push(iterator2.next());
                return this.peek();
            }
            return bl ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
        }
        if (object instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        }
        if (object instanceof JsonArray) {
            return JsonToken.BEGIN_ARRAY;
        }
        if (object instanceof JsonPrimitive) {
            JsonPrimitive jsonPrimitive = (JsonPrimitive)object;
            if (jsonPrimitive.isString()) {
                return JsonToken.STRING;
            }
            if (jsonPrimitive.isBoolean()) {
                return JsonToken.BOOLEAN;
            }
            if (jsonPrimitive.isNumber()) {
                return JsonToken.NUMBER;
            }
            throw new AssertionError();
        }
        if (object instanceof JsonNull) {
            return JsonToken.NULL;
        }
        if (object == SENTINEL_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw new MalformedJsonException("Custom JsonElement subclass " + object.getClass().getName() + " is not supported");
    }

    private Object peekStack() {
        return this.stack[this.stackSize - 1];
    }

    private Object popStack() {
        Object object = this.stack[--this.stackSize];
        this.stack[this.stackSize] = null;
        return object;
    }

    private void expect(JsonToken jsonToken) throws IOException {
        if (this.peek() != jsonToken) {
            throw new IllegalStateException("Expected " + (Object)((Object)jsonToken) + " but was " + (Object)((Object)this.peek()) + this.locationString());
        }
    }

    private String nextName(boolean bl) throws IOException {
        this.expect(JsonToken.NAME);
        Iterator iterator2 = (Iterator)this.peekStack();
        Map.Entry entry = (Map.Entry)iterator2.next();
        String string = (String)entry.getKey();
        this.pathNames[this.stackSize - 1] = bl ? "<skipped>" : string;
        this.push(entry.getValue());
        return string;
    }

    @Override
    public String nextName() throws IOException {
        return this.nextName(false);
    }

    @Override
    public String nextString() throws IOException {
        JsonToken jsonToken = this.peek();
        if (jsonToken != JsonToken.STRING && jsonToken != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.STRING) + " but was " + (Object)((Object)jsonToken) + this.locationString());
        }
        String string = ((JsonPrimitive)this.popStack()).getAsString();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
        return string;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        this.expect(JsonToken.BOOLEAN);
        boolean bl = ((JsonPrimitive)this.popStack()).getAsBoolean();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
        return bl;
    }

    @Override
    public void nextNull() throws IOException {
        this.expect(JsonToken.NULL);
        this.popStack();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
    }

    @Override
    public double nextDouble() throws IOException {
        JsonToken jsonToken = this.peek();
        if (jsonToken != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + (Object)((Object)jsonToken) + this.locationString());
        }
        double d = ((JsonPrimitive)this.peekStack()).getAsDouble();
        if (!this.isLenient() && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + d);
        }
        this.popStack();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
        return d;
    }

    @Override
    public long nextLong() throws IOException {
        JsonToken jsonToken = this.peek();
        if (jsonToken != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + (Object)((Object)jsonToken) + this.locationString());
        }
        long l = ((JsonPrimitive)this.peekStack()).getAsLong();
        this.popStack();
        if (this.stackSize > 0) {
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
        }
        return l;
    }

    @Override
    public int nextInt() throws IOException {
        JsonToken jsonToken = this.peek();
        if (jsonToken != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + (Object)((Object)jsonToken) + this.locationString());
        }
        int n = ((JsonPrimitive)this.peekStack()).getAsInt();
        this.popStack();
        if (this.stackSize > 0) {
            int n2 = this.stackSize - 1;
            this.pathIndices[n2] = this.pathIndices[n2] + 1;
        }
        return n;
    }

    JsonElement nextJsonElement() throws IOException {
        JsonToken jsonToken = this.peek();
        if (jsonToken == JsonToken.NAME || jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT || jsonToken == JsonToken.END_DOCUMENT) {
            throw new IllegalStateException("Unexpected " + (Object)((Object)jsonToken) + " when reading a JsonElement.");
        }
        JsonElement jsonElement = (JsonElement)this.peekStack();
        this.skipValue();
        return jsonElement;
    }

    @Override
    public void close() throws IOException {
        this.stack = new Object[]{SENTINEL_CLOSED};
        this.stackSize = 1;
    }

    @Override
    public void skipValue() throws IOException {
        JsonToken jsonToken = this.peek();
        switch (2.$SwitchMap$com$google$gson$stream$JsonToken[jsonToken.ordinal()]) {
            case 1: {
                String string = this.nextName(true);
                break;
            }
            case 2: {
                this.endArray();
                break;
            }
            case 3: {
                this.endObject();
                break;
            }
            case 4: {
                break;
            }
            default: {
                this.popStack();
                if (this.stackSize <= 0) break;
                int n = this.stackSize - 1;
                this.pathIndices[n] = this.pathIndices[n] + 1;
            }
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.locationString();
    }

    public void promoteNameToValue() throws IOException {
        this.expect(JsonToken.NAME);
        Iterator iterator2 = (Iterator)this.peekStack();
        Map.Entry entry = (Map.Entry)iterator2.next();
        this.push(entry.getValue());
        this.push(new JsonPrimitive((String)entry.getKey()));
    }

    private void push(Object object) {
        if (this.stackSize == this.stack.length) {
            int n = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, n);
            this.pathIndices = Arrays.copyOf(this.pathIndices, n);
            this.pathNames = Arrays.copyOf(this.pathNames, n);
        }
        this.stack[this.stackSize++] = object;
    }

    private String getPath(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder().append('$');
        for (int i = 0; i < this.stackSize; ++i) {
            if (this.stack[i] instanceof JsonArray) {
                if (++i >= this.stackSize || !(this.stack[i] instanceof Iterator)) continue;
                int n = this.pathIndices[i];
                if (bl && n > 0 && (i == this.stackSize - 1 || i == this.stackSize - 2)) {
                    --n;
                }
                stringBuilder.append('[').append(n).append(']');
                continue;
            }
            if (!(this.stack[i] instanceof JsonObject) || ++i >= this.stackSize || !(this.stack[i] instanceof Iterator)) continue;
            stringBuilder.append('.');
            if (this.pathNames[i] == null) continue;
            stringBuilder.append(this.pathNames[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getPreviousPath() {
        return this.getPath(true);
    }

    @Override
    public String getPath() {
        return this.getPath(false);
    }

    private String locationString() {
        return " at path " + this.getPath();
    }
}

