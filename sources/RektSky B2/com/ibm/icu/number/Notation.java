package com.ibm.icu.number;

import com.ibm.icu.text.*;

public class Notation
{
    private static final ScientificNotation SCIENTIFIC;
    private static final ScientificNotation ENGINEERING;
    private static final CompactNotation COMPACT_SHORT;
    private static final CompactNotation COMPACT_LONG;
    private static final SimpleNotation SIMPLE;
    
    Notation() {
    }
    
    public static ScientificNotation scientific() {
        return Notation.SCIENTIFIC;
    }
    
    public static ScientificNotation engineering() {
        return Notation.ENGINEERING;
    }
    
    public static CompactNotation compactShort() {
        return Notation.COMPACT_SHORT;
    }
    
    public static CompactNotation compactLong() {
        return Notation.COMPACT_LONG;
    }
    
    public static SimpleNotation simple() {
        return Notation.SIMPLE;
    }
    
    static {
        SCIENTIFIC = new ScientificNotation(1, false, 1, NumberFormatter.SignDisplay.AUTO);
        ENGINEERING = new ScientificNotation(3, false, 1, NumberFormatter.SignDisplay.AUTO);
        COMPACT_SHORT = new CompactNotation(CompactDecimalFormat.CompactStyle.SHORT);
        COMPACT_LONG = new CompactNotation(CompactDecimalFormat.CompactStyle.LONG);
        SIMPLE = new SimpleNotation();
    }
}
