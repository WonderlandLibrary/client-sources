package com.ibm.icu.util;

public class Measure
{
    private final Number number;
    private final MeasureUnit unit;
    
    public Measure(final Number number, final MeasureUnit unit) {
        if (number == null || unit == null) {
            throw new NullPointerException("Number and MeasureUnit must not be null");
        }
        this.number = number;
        this.unit = unit;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Measure)) {
            return false;
        }
        final Measure m = (Measure)obj;
        return this.unit.equals(m.unit) && numbersEqual(this.number, m.number);
    }
    
    private static boolean numbersEqual(final Number a, final Number b) {
        return a.equals(b) || a.doubleValue() == b.doubleValue();
    }
    
    @Override
    public int hashCode() {
        return 31 * Double.valueOf(this.number.doubleValue()).hashCode() + this.unit.hashCode();
    }
    
    @Override
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
