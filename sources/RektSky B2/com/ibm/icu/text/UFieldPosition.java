package com.ibm.icu.text;

import java.text.*;

@Deprecated
public class UFieldPosition extends FieldPosition
{
    private int countVisibleFractionDigits;
    private long fractionDigits;
    
    @Deprecated
    public UFieldPosition() {
        super(-1);
        this.countVisibleFractionDigits = -1;
        this.fractionDigits = 0L;
    }
    
    @Deprecated
    public UFieldPosition(final int field) {
        super(field);
        this.countVisibleFractionDigits = -1;
        this.fractionDigits = 0L;
    }
    
    @Deprecated
    public UFieldPosition(final Format.Field attribute, final int fieldID) {
        super(attribute, fieldID);
        this.countVisibleFractionDigits = -1;
        this.fractionDigits = 0L;
    }
    
    @Deprecated
    public UFieldPosition(final Format.Field attribute) {
        super(attribute);
        this.countVisibleFractionDigits = -1;
        this.fractionDigits = 0L;
    }
    
    @Deprecated
    public void setFractionDigits(final int countVisibleFractionDigits, final long fractionDigits) {
        this.countVisibleFractionDigits = countVisibleFractionDigits;
        this.fractionDigits = fractionDigits;
    }
    
    @Deprecated
    public int getCountVisibleFractionDigits() {
        return this.countVisibleFractionDigits;
    }
    
    @Deprecated
    public long getFractionDigits() {
        return this.fractionDigits;
    }
}
