/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.Mutable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableShort
extends Number
implements Comparable<MutableShort>,
Mutable<Number> {
    private static final long serialVersionUID = -2135791679L;
    private short value;

    public MutableShort() {
    }

    public MutableShort(short s) {
        this.value = s;
    }

    public MutableShort(Number number) {
        this.value = number.shortValue();
    }

    public MutableShort(String string) throws NumberFormatException {
        this.value = Short.parseShort(string);
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    @Override
    public void setValue(short s) {
        this.value = s;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.shortValue();
    }

    public void increment() {
        this.value = (short)(this.value + 1);
    }

    public short getAndIncrement() {
        short s = this.value;
        this.value = (short)(this.value + 1);
        return s;
    }

    public short incrementAndGet() {
        this.value = (short)(this.value + 1);
        return this.value;
    }

    public void decrement() {
        this.value = (short)(this.value - 1);
    }

    public short getAndDecrement() {
        short s = this.value;
        this.value = (short)(this.value - 1);
        return s;
    }

    public short decrementAndGet() {
        this.value = (short)(this.value - 1);
        return this.value;
    }

    public void add(short s) {
        this.value = (short)(this.value + s);
    }

    public void add(Number number) {
        this.value = (short)(this.value + number.shortValue());
    }

    public void subtract(short s) {
        this.value = (short)(this.value - s);
    }

    public void subtract(Number number) {
        this.value = (short)(this.value - number.shortValue());
    }

    public short addAndGet(short s) {
        this.value = (short)(this.value + s);
        return this.value;
    }

    public short addAndGet(Number number) {
        this.value = (short)(this.value + number.shortValue());
        return this.value;
    }

    public short getAndAdd(short s) {
        short s2 = this.value;
        this.value = (short)(this.value + s);
        return s2;
    }

    public short getAndAdd(Number number) {
        short s = this.value;
        this.value = (short)(this.value + number.shortValue());
        return s;
    }

    @Override
    public short shortValue() {
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

    public Short toShort() {
        return this.shortValue();
    }

    public boolean equals(Object object) {
        if (object instanceof MutableShort) {
            return this.value == ((MutableShort)object).shortValue();
        }
        return true;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int compareTo(MutableShort mutableShort) {
        return NumberUtils.compare(this.value, mutableShort.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((MutableShort)object);
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

