/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive
extends JsonElement {
    private final Object value;

    public JsonPrimitive(Boolean bool) {
        this.value = $Gson$Preconditions.checkNotNull(bool);
    }

    public JsonPrimitive(Number number) {
        this.value = $Gson$Preconditions.checkNotNull(number);
    }

    public JsonPrimitive(String string) {
        this.value = $Gson$Preconditions.checkNotNull(string);
    }

    public JsonPrimitive(Character c) {
        this.value = $Gson$Preconditions.checkNotNull(c).toString();
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
        if (!this.isBoolean()) return Boolean.parseBoolean(this.getAsString());
        return (Boolean)this.value;
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    @Override
    public Number getAsNumber() {
        Number number;
        if (this.value instanceof String) {
            number = new LazilyParsedNumber((String)this.value);
            return number;
        }
        number = (Number)this.value;
        return number;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (!this.isBoolean()) return (String)this.value;
        return ((Boolean)this.value).toString();
    }

    @Override
    public double getAsDouble() {
        double d;
        if (this.isNumber()) {
            d = this.getAsNumber().doubleValue();
            return d;
        }
        d = Double.parseDouble(this.getAsString());
        return d;
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        BigDecimal bigDecimal;
        if (this.value instanceof BigDecimal) {
            bigDecimal = (BigDecimal)this.value;
            return bigDecimal;
        }
        bigDecimal = new BigDecimal(this.value.toString());
        return bigDecimal;
    }

    @Override
    public BigInteger getAsBigInteger() {
        BigInteger bigInteger;
        if (this.value instanceof BigInteger) {
            bigInteger = (BigInteger)this.value;
            return bigInteger;
        }
        bigInteger = new BigInteger(this.value.toString());
        return bigInteger;
    }

    @Override
    public float getAsFloat() {
        float f;
        if (this.isNumber()) {
            f = this.getAsNumber().floatValue();
            return f;
        }
        f = Float.parseFloat(this.getAsString());
        return f;
    }

    @Override
    public long getAsLong() {
        long l;
        if (this.isNumber()) {
            l = this.getAsNumber().longValue();
            return l;
        }
        l = Long.parseLong(this.getAsString());
        return l;
    }

    @Override
    public short getAsShort() {
        short s;
        if (this.isNumber()) {
            s = this.getAsNumber().shortValue();
            return s;
        }
        s = Short.parseShort(this.getAsString());
        return s;
    }

    @Override
    public int getAsInt() {
        int n;
        if (this.isNumber()) {
            n = this.getAsNumber().intValue();
            return n;
        }
        n = Integer.parseInt(this.getAsString());
        return n;
    }

    @Override
    public byte getAsByte() {
        byte by;
        if (this.isNumber()) {
            by = this.getAsNumber().byteValue();
            return by;
        }
        by = Byte.parseByte(this.getAsString());
        return by;
    }

    @Override
    public char getAsCharacter() {
        return this.getAsString().charAt(0);
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (JsonPrimitive.isIntegral(this)) {
            long value = this.getAsNumber().longValue();
            return (int)(value ^ value >>> 32);
        }
        if (!(this.value instanceof Number)) return this.value.hashCode();
        long value = Double.doubleToLongBits(this.getAsNumber().doubleValue());
        return (int)(value ^ value >>> 32);
    }

    public boolean equals(Object obj) {
        double b;
        if (this == obj) {
            return true;
        }
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        JsonPrimitive other = (JsonPrimitive)obj;
        if (this.value == null) {
            if (other.value != null) return false;
            return true;
        }
        if (JsonPrimitive.isIntegral(this) && JsonPrimitive.isIntegral(other)) {
            if (this.getAsNumber().longValue() != other.getAsNumber().longValue()) return false;
            return true;
        }
        if (!(this.value instanceof Number)) return this.value.equals(other.value);
        if (!(other.value instanceof Number)) return this.value.equals(other.value);
        double a = this.getAsNumber().doubleValue();
        if (a == (b = other.getAsNumber().doubleValue())) return true;
        if (!Double.isNaN(a)) return false;
        if (!Double.isNaN(b)) return false;
        return true;
    }

    private static boolean isIntegral(JsonPrimitive primitive) {
        if (!(primitive.value instanceof Number)) return false;
        Number number = (Number)primitive.value;
        if (number instanceof BigInteger) return true;
        if (number instanceof Long) return true;
        if (number instanceof Integer) return true;
        if (number instanceof Short) return true;
        if (number instanceof Byte) return true;
        return false;
    }
}

