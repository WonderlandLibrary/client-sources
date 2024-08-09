/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.Grouper;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.DecimalFormatSymbols;

public class MicroProps
implements Cloneable,
MicroPropsGenerator {
    public NumberFormatter.SignDisplay sign;
    public DecimalFormatSymbols symbols;
    public String nsName;
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

    public MicroProps(boolean bl) {
        this.immutable = bl;
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
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
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
    }
}

