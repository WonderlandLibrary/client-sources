/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.MeasureUnit;

public class Measure {
    private final Number number;
    private final MeasureUnit unit;

    public Measure(Number number, MeasureUnit measureUnit) {
        if (number == null || measureUnit == null) {
            throw new NullPointerException("Number and MeasureUnit must not be null");
        }
        this.number = number;
        this.unit = measureUnit;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Measure)) {
            return true;
        }
        Measure measure = (Measure)object;
        return this.unit.equals(measure.unit) && Measure.numbersEqual(this.number, measure.number);
    }

    private static boolean numbersEqual(Number number, Number number2) {
        if (number.equals(number2)) {
            return false;
        }
        return number.doubleValue() != number2.doubleValue();
    }

    public int hashCode() {
        return 31 * Double.valueOf(this.number.doubleValue()).hashCode() + this.unit.hashCode();
    }

    public String toString() {
        return this.number.toString() + ' ' + this.unit.toString();
    }

    public Number getNumber() {
        return this.number;
    }

    public MeasureUnit getUnit() {
        return this.unit;
    }
}

