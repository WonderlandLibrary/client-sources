/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class JsonPrimitive
extends JsonElement {
    private final Object value;

    public JsonPrimitive(Boolean bl) {
        this.value = Objects.requireNonNull(bl);
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
        String string = this.getAsString();
        if (string.isEmpty()) {
            throw new UnsupportedOperationException("String value is empty");
        }
        return string.charAt(0);
    }

    public int hashCode() {
        if (this.value == null) {
            return 0;
        }
        if (JsonPrimitive.isIntegral(this)) {
            long l = this.getAsNumber().longValue();
            return (int)(l ^ l >>> 32);
        }
        if (this.value instanceof Number) {
            long l = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(l ^ l >>> 32);
        }
        return this.value.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive)object;
        if (this.value == null) {
            return jsonPrimitive.value == null;
        }
        if (JsonPrimitive.isIntegral(this) && JsonPrimitive.isIntegral(jsonPrimitive)) {
            return this.getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
        }
        if (this.value instanceof Number && jsonPrimitive.value instanceof Number) {
            double d;
            double d2 = this.getAsNumber().doubleValue();
            return d2 == (d = jsonPrimitive.getAsNumber().doubleValue()) || Double.isNaN(d2) && Double.isNaN(d);
        }
        return this.value.equals(jsonPrimitive.value);
    }

    private static boolean isIntegral(JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.value instanceof Number) {
            Number number = (Number)jsonPrimitive.value;
            return number instanceof BigInteger || number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte;
        }
        return true;
    }

    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}

