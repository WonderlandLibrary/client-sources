/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public final class JsonPrimitive
extends JsonElement {
    private final Object value;

    public JsonPrimitive(Boolean bool) {
        this.value = Objects.requireNonNull(bool);
    }

    public JsonPrimitive(Number number) {
        this.value = Objects.requireNonNull(number);
    }

    public JsonPrimitive(String string) {
        this.value = Objects.requireNonNull(string);
    }

    public JsonPrimitive(Character c) {
        this.value = Objects.requireNonNull(c).toString();
    }

    @Override
    public JsonPrimitive deepCopy() {
        return this;
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    @Override
    public boolean getAsBoolean() {
        if (this.isBoolean()) {
            return (Boolean)this.value;
        }
        return Boolean.parseBoolean(this.getAsString());
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    @Override
    public Number getAsNumber() {
        if (this.value instanceof Number) {
            return (Number)this.value;
        }
        if (this.value instanceof String) {
            return new LazilyParsedNumber((String)this.value);
        }
        throw new UnsupportedOperationException("Primitive is neither a number nor a string");
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    @Override
    public String getAsString() {
        if (this.value instanceof String) {
            return (String)this.value;
        }
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (this.isBoolean()) {
            return ((Boolean)this.value).toString();
        }
        throw new AssertionError((Object)("Unexpected value type: " + this.value.getClass()));
    }

    @Override
    public double getAsDouble() {
        return this.isNumber() ? this.getAsNumber().doubleValue() : Double.parseDouble(this.getAsString());
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        return this.value instanceof BigDecimal ? (BigDecimal)this.value : new BigDecimal(this.getAsString());
    }

    @Override
    public BigInteger getAsBigInteger() {
        return this.value instanceof BigInteger ? (BigInteger)this.value : new BigInteger(this.getAsString());
    }

    @Override
    public float getAsFloat() {
        return this.isNumber() ? this.getAsNumber().floatValue() : Float.parseFloat(this.getAsString());
    }

    @Override
    public long getAsLong() {
        return this.isNumber() ? this.getAsNumber().longValue() : Long.parseLong(this.getAsString());
    }

    @Override
    public short getAsShort() {
        return this.isNumber() ? this.getAsNumber().shortValue() : Short.parseShort(this.getAsString());
    }

    @Override
    public int getAsInt() {
        return this.isNumber() ? this.getAsNumber().intValue() : Integer.parseInt(this.getAsString());
    }

    @Override
    public byte getAsByte() {
        return this.isNumber() ? this.getAsNumber().byteValue() : Byte.parseByte(this.getAsString());
    }

    @Override
    @Deprecated
    public char getAsCharacter() {
        String s = this.getAsString();
        if (s.isEmpty()) {
            throw new UnsupportedOperationException("String value is empty");
        }
        return s.charAt(0);
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (JsonPrimitive.isIntegral(this)) {
            long value = this.getAsNumber().longValue();
            return (int)(value ^ value >>> 32);
        }
        if (this.value instanceof Number) {
            long value = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(value ^ value >>> 32);
        }
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        JsonPrimitive other = (JsonPrimitive)obj;
        if (this.value == null) {
            return other.value == null;
        }
        if (JsonPrimitive.isIntegral(this) && JsonPrimitive.isIntegral(other)) {
            return this.getAsNumber().longValue() == other.getAsNumber().longValue();
        }
        if (this.value instanceof Number && other.value instanceof Number) {
            double b;
            double a = this.getAsNumber().doubleValue();
            return a == (b = other.getAsNumber().doubleValue()) || Double.isNaN(a) && Double.isNaN(b);
        }
        return this.value.equals(other.value);
    }

    private static boolean isIntegral(JsonPrimitive primitive) {
        if (primitive.value instanceof Number) {
            Number number = (Number)primitive.value;
            return number instanceof BigInteger || number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte;
        }
        return false;
    }
}

