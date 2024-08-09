/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.Mutable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableInt
extends Number
implements Comparable<MutableInt>,
Mutable<Number> {
    private static final long serialVersionUID = 512176391864L;
    private int value;

    public MutableInt() {
    }

    public MutableInt(int n) {
        this.value = n;
    }

    public MutableInt(Number number) {
        this.value = number.intValue();
    }

    public MutableInt(String string) throws NumberFormatException {
        this.value = Integer.parseInt(string);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public void setValue(int n) {
        this.value = n;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.intValue();
    }

    public void increment() {
        ++this.value;
    }

    public int getAndIncrement() {
        int n = this.value++;
        return n;
    }

    public int incrementAndGet() {
        ++this.value;
        return this.value;
    }

    public void decrement() {
        --this.value;
    }

    public int getAndDecrement() {
        int n = this.value--;
        return n;
    }

    public int decrementAndGet() {
        --this.value;
        return this.value;
    }

    public void add(int n) {
        this.value += n;
    }

    public void add(Number number) {
        this.value += number.intValue();
    }

    public void subtract(int n) {
        this.value -= n;
    }

    public void subtract(Number number) {
        this.value -= number.intValue();
    }

    public int addAndGet(int n) {
        this.value += n;
        return this.value;
    }

    public int addAndGet(Number number) {
        this.value += number.intValue();
        return this.value;
    }

    public int getAndAdd(int n) {
        int n2 = this.value;
        this.value += n;
        return n2;
    }

    public int getAndAdd(Number number) {
        int n = this.value;
        this.value += number.intValue();
        return n;
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

    public Integer toInteger() {
        return this.intValue();
    }

    public boolean equals(Object object) {
        if (object instanceof MutableInt) {
            return this.value == ((MutableInt)object).intValue();
        }
        return true;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int compareTo(MutableInt mutableInt) {
        return NumberUtils.compare(this.value, mutableInt.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((MutableInt)object);
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

