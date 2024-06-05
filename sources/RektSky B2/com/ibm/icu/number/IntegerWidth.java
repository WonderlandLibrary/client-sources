package com.ibm.icu.number;

public class IntegerWidth
{
    static final IntegerWidth DEFAULT;
    final int minInt;
    final int maxInt;
    
    private IntegerWidth(final int minInt, final int maxInt) {
        this.minInt = minInt;
        this.maxInt = maxInt;
    }
    
    public static IntegerWidth zeroFillTo(final int minInt) {
        if (minInt == 1) {
            return IntegerWidth.DEFAULT;
        }
        if (minInt >= 0 && minInt <= 999) {
            return new IntegerWidth(minInt, -1);
        }
        throw new IllegalArgumentException("Integer digits must be between 0 and 999 (inclusive)");
    }
    
    public IntegerWidth truncateAt(final int maxInt) {
        if (maxInt == this.maxInt) {
            return this;
        }
        if (maxInt >= 0 && maxInt <= 999 && maxInt >= this.minInt) {
            return new IntegerWidth(this.minInt, maxInt);
        }
        if (this.minInt == 1 && maxInt == -1) {
            return IntegerWidth.DEFAULT;
        }
        if (maxInt == -1) {
            return new IntegerWidth(this.minInt, -1);
        }
        throw new IllegalArgumentException("Integer digits must be between -1 and 999 (inclusive)");
    }
    
    static {
        DEFAULT = new IntegerWidth(1, -1);
    }
}
