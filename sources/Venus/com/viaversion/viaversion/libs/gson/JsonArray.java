/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.internal.NonNullElementWrapperList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class JsonArray
extends JsonElement
implements Iterable<JsonElement> {
    private final ArrayList<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList();
    }

    public JsonArray(int n) {
        this.elements = new ArrayList(n);
    }

    @Override
    public JsonArray deepCopy() {
        if (!this.elements.isEmpty()) {
            JsonArray jsonArray = new JsonArray(this.elements.size());
            for (JsonElement jsonElement : this.elements) {
                jsonArray.add(jsonElement.deepCopy());
            }
            return jsonArray;
        }
        return new JsonArray();
    }

    public void add(Boolean bl) {
        this.elements.add(bl == null ? JsonNull.INSTANCE : new JsonPrimitive(bl));
    }

    public void add(Character c) {
        this.elements.add(c == null ? JsonNull.INSTANCE : new JsonPrimitive(c));
    }

    public void add(Number number) {
        this.elements.add(number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void add(String string) {
        this.elements.add(string == null ? JsonNull.INSTANCE : new JsonPrimitive(string));
    }

    public void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.elements.add(jsonElement);
    }

    public void addAll(JsonArray jsonArray) {
        this.elements.addAll(jsonArray.elements);
    }

    public JsonElement set(int n, JsonElement jsonElement) {
        return this.elements.set(n, jsonElement == null ? JsonNull.INSTANCE : jsonElement);
    }

    public boolean remove(JsonElement jsonElement) {
        return this.elements.remove(jsonElement);
    }

    public JsonElement remove(int n) {
        return this.elements.remove(n);
    }

    public boolean contains(JsonElement jsonElement) {
        return this.elements.contains(jsonElement);
    }

    public int size() {
        return this.elements.size();
    }

    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.elements.iterator();
    }

    public JsonElement get(int n) {
        return this.elements.get(n);
    }

    private JsonElement getAsSingleElement() {
        int n = this.elements.size();
        if (n == 1) {
            return this.elements.get(0);
        }
        throw new IllegalStateException("Array must have size 1, but has size " + n);
    }

    @Override
    public Number getAsNumber() {
        return this.getAsSingleElement().getAsNumber();
    }

    @Override
    public String getAsString() {
        return this.getAsSingleElement().getAsString();
    }

    @Override
    public double getAsDouble() {
        return this.getAsSingleElement().getAsDouble();
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        return this.getAsSingleElement().getAsBigDecimal();
    }

    @Override
    public BigInteger getAsBigInteger() {
        return this.getAsSingleElement().getAsBigInteger();
    }

    @Override
    public float getAsFloat() {
        return this.getAsSingleElement().getAsFloat();
    }

    @Override
    public long getAsLong() {
        return this.getAsSingleElement().getAsLong();
    }

    @Override
    public int getAsInt() {
        return this.getAsSingleElement().getAsInt();
    }

    @Override
    public byte getAsByte() {
        return this.getAsSingleElement().getAsByte();
    }

    @Override
    @Deprecated
    public char getAsCharacter() {
        return this.getAsSingleElement().getAsCharacter();
    }

    @Override
    public short getAsShort() {
        return this.getAsSingleElement().getAsShort();
    }

    @Override
    public boolean getAsBoolean() {
        return this.getAsSingleElement().getAsBoolean();
    }

    public List<JsonElement> asList() {
        return new NonNullElementWrapperList<JsonElement>(this.elements);
    }

    public boolean equals(Object object) {
        return object == this || object instanceof JsonArray && ((JsonArray)object).elements.equals(this.elements);
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}

