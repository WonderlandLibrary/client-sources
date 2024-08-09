/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.Mutable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableByte
extends Number
implements Comparable<MutableByte>,
Mutable<Number> {
    private static final long serialVersionUID = -1585823265L;
    private byte value;

    public MutableByte() {
    }

    public MutableByte(byte by) {
        this.value = by;
    }

    public MutableByte(Number number) {
        this.value = number.byteValue();
    }

    public MutableByte(String string) throws NumberFormatException {
        this.value = Byte.parseByte(string);
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    @Override
    public void setValue(byte by) {
        this.value = by;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.byteValue();
    }

    public void increment() {
        this.value = (byte)(this.value + 1);
    }

    public byte getAndIncrement() {
        byte by = this.value;
        this.value = (byte)(this.value + 1);
        return by;
    }

    public byte incrementAndGet() {
        this.value = (byte)(this.value + 1);
        return this.value;
    }

    public void decrement() {
        this.value = (byte)(this.value - 1);
    }

    public byte getAndDecrement() {
        byte by = this.value;
        this.value = (byte)(this.value - 1);
        return by;
    }

    public byte decrementAndGet() {
        this.value = (byte)(this.value - 1);
        return this.value;
    }

    public void add(byte by) {
        this.value = (byte)(this.value + by);
    }

    public void add(Number number) {
        this.value = (byte)(this.value + number.byteValue());
    }

    public void subtract(byte by) {
        this.value = (byte)(this.value - by);
    }

    public void subtract(Number number) {
        this.value = (byte)(this.value - number.byteValue());
    }

    public byte addAndGet(byte by) {
        this.value = (byte)(this.value + by);
        return this.value;
    }

    public byte addAndGet(Number number) {
        this.value = (byte)(this.value + number.byteValue());
        return this.value;
    }

    public byte getAndAdd(byte by) {
        byte by2 = this.value;
        this.value = (byte)(this.value + by);
        return by2;
    }

    public byte getAndAdd(Number number) {
        byte by = this.value;
        this.value = (byte)(this.value + number.byteValue());
        return by;
    }

    @Override
    public byte byteValue() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public Byte toByte() {
        return this.byteValue();
    }

    public boolean equals(Object object) {
        if (object instanceof MutableByte) {
            return this.value == ((MutableByte)object).byteValue();
        }
        return true;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int compareTo(MutableByte mutableByte) {
        return NumberUtils.compare(this.value, mutableByte.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((MutableByte)object);
    }

    @Override
    public void setValue(Object object) {
        this.setValue((Number)object);
    }

    @Override
    public Object getValue() {
        return this.getValue();
    }
}

