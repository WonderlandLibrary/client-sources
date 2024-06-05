package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;
import com.ibm.icu.number.*;

public class MicroProps implements Cloneable, MicroPropsGenerator
{
    public NumberFormatter.SignDisplay sign;
    public DecimalFormatSymbols symbols;
    public Padder padding;
    public NumberFormatter.DecimalSeparatorDisplay decimal;
    public IntegerWidth integerWidth;
    public Modifier modOuter;
    public Modifier modMiddle;
    public Modifier modInner;
    public Precision rounder;
    public Grouper grouping;
    public boolean useCurrency;
    private final boolean immutable;
    private volatile boolean exhausted;
    
    public MicroProps(final boolean immutable) {
        this.immutable = immutable;
    }
    
    @Override
    public MicroProps processQuantity(final DecimalQuantity quantity) {
        if (this.immutable) {
            return (MicroProps)this.clone();
        }
        if (this.exhausted) {
            throw new AssertionError((Object)"Cannot re-use a mutable MicroProps in the quantity chain");
        }
        this.exhausted = true;
        return this;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }
}
