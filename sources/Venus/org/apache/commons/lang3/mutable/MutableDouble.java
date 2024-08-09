/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.mutable.Mutable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableDouble
extends Number
implements Comparable<MutableDouble>,
Mutable<Number> {
    private static final long serialVersionUID = 1587163916L;
    private double value;

    public MutableDouble() {
    }

    public MutableDouble(double d) {
        this.value = d;
    }

    public MutableDouble(Number number) {
        this.value = number.doubleValue();
    }

    public MutableDouble(String string) throws NumberFormatException {
        this.value = Double.parseDouble(string);
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public void setValue(double d) {
        this.value = d;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.doubleValue();
    }

    public boolean isNaN() {
        return Double.isNaN(this.value);
    }

    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }

    public void increment() {
        this.value += 1.0;
    }

    public double getAndIncrement() {
        double d = this.value;
        this.value += 1.0;
        return d;
    }

    public double incrementAndGet() {
        this.value += 1.0;
        return this.value;
    }

    public void decrement() {
        this.value -= 1.0;
    }

    public double getAndDecrement() {
        double d = this.value;
        this.value -= 1.0;
        return d;
    }

    public double decrementAndGet() {
        this.value -= 1.0;
        return this.value;
    }

    public void add(double d) {
        this.value += d;
    }

    public void add(Number number) {
        this.value += number.doubleValue();
    }

    public void subtract(double d) {
        this.value -= d;
    }

    public void subtract(Number number) {
        this.value -= number.doubleValue();
    }

    public double addAndGet(double d) {
        this.value += d;
        return this.value;
    }

    public double addAndGet(Number number) {
        this.value += number.doubleValue();
        return this.value;
    }

    public double getAndAdd(double d) {
        double d2 = this.value;
        this.value += d;
        return d2;
    }

    public double getAndAdd(Number number) {
        double d = this.value;
        this.value += number.doubleValue();
        return d;
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public long longValue() {
        return (long)this.value;
    }

    @Override
    public float floatValue() {
        return (float)this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public Double toDouble() {
        return this.doubleValue();
    }

    public boolean equals(Object object) {
        return object instanceof MutableDouble && Double.doubleToLongBits(((MutableDouble)object).value) == Double.doubleToLongBits(this.value);
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.value);
        return (int)(l ^ l >>> 32);
    }

    @Override
    public int compareTo(MutableDouble mutableDouble) {
        return Double.compare(this.value, mutableDouble.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((MutableDouble)object);
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

