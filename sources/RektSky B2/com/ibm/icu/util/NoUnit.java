package com.ibm.icu.util;

public class NoUnit extends MeasureUnit
{
    private static final long serialVersionUID = 2467174286237024095L;
    public static final NoUnit BASE;
    public static final NoUnit PERCENT;
    public static final NoUnit PERMILLE;
    
    NoUnit(final String subType) {
        super("none", subType);
    }
    
    static {
        BASE = (NoUnit)MeasureUnit.internalGetInstance("none", "base");
        PERCENT = (NoUnit)MeasureUnit.internalGetInstance("none", "percent");
        PERMILLE = (NoUnit)MeasureUnit.internalGetInstance("none", "permille");
    }
}
