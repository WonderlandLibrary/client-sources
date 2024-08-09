/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.Mutable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableLong
extends Number
implements Comparable<MutableLong>,
Mutable<Number> {
    private static final long serialVersionUID = 62986528375L;
    private long value;

    public MutableLong() {
    }

    public MutableLong(long l) {
        this.value = l;
    }

    public MutableLong(Number number) {
        this.value = number.longValue();
    }

    public MutableLong(String string) throws NumberFormatException {
        this.value = Long.parseLong(string);
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public void setValue(long l) {
        this.value = l;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.longValue();
    }

    public void increment() {
        ++this.value;
    }

    public long getAndIncrement() {
        long l = this.value++;
        return l;
    }

    public long incrementAndGet() {
        ++this.value;
        return this.value;
    }

    public void decrement() {
        --this.value;
    }

    public long getAndDecrement() {
        long l = this.value--;
        return l;
    }

    public long decrementAndGet() {
        --this.value;
        return this.value;
    }

    public void add(long l) {
        this.value += l;
    }

    public void add(Number number) {
        this.value += number.longValue();
    }

    public void subtract(long l) {
        this.value -= l;
    }

    public void subtract(Number number) {
        this.value -= number.longValue();
    }

    public long addAndGet(long l) {
        this.value += l;
        return this.value;
    }

    public long addAndGet(Number number) {
        this.value += number.longValue();
        return this.value;
    }

    public long getAndAdd(long l) {
        long l2 = this.value;
        this.value += l;
        return l2;
    }

    public long getAndAdd(Number number) {
        long l = this.value;
        this.value += number.longValue();
        return l;
    }

    @Override
    public int intValue() {
        return (int)this.value;
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

    public Long toLong() {
        return this.longValue();
    }

    public boolean equals(Object object) {
        if (object instanceof MutableLong) {
            return this.value == ((MutableLong)object).longValue();
        }
        return true;
    }

    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }

    @Override
    public int compareTo(MutableLong mutableLong) {
        return NumberUtils.compare(this.value, mutableLong.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((MutableLong)object);
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

