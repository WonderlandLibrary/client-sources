/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray
extends JsonElement
implements Iterable<JsonElement> {
    private final List<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList<JsonElement>();
    }

    public JsonArray(int capacity) {
        this.elements = new ArrayList<JsonElement>(capacity);
    }

    @Override
    public JsonArray deepCopy() {
        if (this.elements.isEmpty()) return new JsonArray();
        JsonArray result = new JsonArray(this.elements.size());
        Iterator<JsonElement> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            result.add(element.deepCopy());
        }
        return result;
    }

    public void add(Boolean bool) {
        this.elements.add(bool == null ? JsonNull.INSTANCE : new JsonPrimitive(bool));
    }

    public void add(Character character) {
        this.elements.add(character == null ? JsonNull.INSTANCE : new JsonPrimitive(character));
    }

    public void add(Number number) {
        this.elements.add(number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void add(String string) {
        this.elements.add(string == null ? JsonNull.INSTANCE : new JsonPrimitive(string));
    }

    public void add(JsonElement element) {
        if (element == null) {
            element = JsonNull.INSTANCE;
        }
        this.elements.add(element);
    }

    public void addAll(JsonArray array) {
        this.elements.addAll(array.elements);
    }

    public JsonElement set(int index, JsonElement element) {
        return this.elements.set(index, element);
    }

    public boolean remove(JsonElement element) {
        return this.elements.remove(element);
    }

    public JsonElement remove(int index) {
        return this.elements.remove(index);
    }

    public boolean contains(JsonElement element) {
        return this.elements.contains(element);
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

    public JsonElement get(int i) {
        return this.elements.get(i);
    }

    @Override
    public Number getAsNumber() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsNumber();
    }

    @Override
    public String getAsString() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsString();
    }

    @Override
    public double getAsDouble() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsDouble();
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsBigDecimal();
    }

    @Override
    public BigInteger getAsBigInteger() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsBigInteger();
    }

    @Override
    public float getAsFloat() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsFloat();
    }

    @Override
    public long getAsLong() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsLong();
    }

    @Override
    public int getAsInt() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsInt();
    }

    @Override
    public byte getAsByte() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsByte();
    }

    @Override
    public char getAsCharacter() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsCharacter();
    }

    @Override
    public short getAsShort() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsShort();
    }

    @Override
    public boolean getAsBoolean() {
        if (this.elements.size() != 1) throw new IllegalStateException();
        return this.elements.get(0).getAsBoolean();
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof JsonArray)) return false;
        if (!((JsonArray)o).elements.equals(this.elements)) return false;
        return true;
    }

    public int hashCode() {
        return this.elements.hashCode();
    }
}

