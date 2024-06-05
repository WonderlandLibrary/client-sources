package com.ibm.icu.impl.number;

import java.math.*;
import com.ibm.icu.number.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public class MacroProps implements Cloneable
{
    public Notation notation;
    public MeasureUnit unit;
    public MeasureUnit perUnit;
    public Precision precision;
    public RoundingMode roundingMode;
    public Object grouping;
    public Padder padder;
    public IntegerWidth integerWidth;
    public Object symbols;
    public NumberFormatter.UnitWidth unitWidth;
    public NumberFormatter.SignDisplay sign;
    public NumberFormatter.DecimalSeparatorDisplay decimal;
    public Scale scale;
    public AffixPatternProvider affixProvider;
    public PluralRules rules;
    public Long threshold;
    public ULocale loc;
    
    public void fallback(final MacroProps fallback) {
        if (this.notation == null) {
            this.notation = fallback.notation;
        }
        if (this.unit == null) {
            this.unit = fallback.unit;
        }
        if (this.perUnit == null) {
            this.perUnit = fallback.perUnit;
        }
        if (this.precision == null) {
            this.precision = fallback.precision;
        }
        if (this.roundingMode == null) {
            this.roundingMode = fallback.roundingMode;
        }
        if (this.grouping == null) {
            this.grouping = fallback.grouping;
        }
        if (this.padder == null) {
            this.padder = fallback.padder;
        }
        if (this.integerWidth == null) {
            this.integerWidth = fallback.integerWidth;
        }
        if (this.symbols == null) {
            this.symbols = fallback.symbols;
        }
        if (this.unitWidth == null) {
            this.unitWidth = fallback.unitWidth;
        }
        if (this.sign == null) {
            this.sign = fallback.sign;
        }
        if (this.decimal == null) {
            this.decimal = fallback.decimal;
        }
        if (this.affixProvider == null) {
            this.affixProvider = fallback.affixProvider;
        }
        if (this.scale == null) {
            this.scale = fallback.scale;
        }
        if (this.rules == null) {
            this.rules = fallback.rules;
        }
        if (this.loc == null) {
            this.loc = fallback.loc;
        }
    }
    
    @Override
    public int hashCode() {
        return Utility.hash(this.notation, this.unit, this.perUnit, this.precision, this.roundingMode, this.grouping, this.padder, this.integerWidth, this.symbols, this.unitWidth, this.sign, this.decimal, this.affixProvider, this.scale, this.rules, this.loc);
    }
    
    @Override
    public boolean equals(final Object _other) {
        if (_other == null) {
            return false;
        }
        if (this == _other) {
            return true;
        }
        if (!(_other instanceof MacroProps)) {
            return false;
        }
        final MacroProps other = (MacroProps)_other;
        return Utility.equals(this.notation, other.notation) && Utility.equals(this.unit, other.unit) && Utility.equals(this.perUnit, other.perUnit) && Utility.equals(this.precision, other.precision) && Utility.equals(this.roundingMode, other.roundingMode) && Utility.equals(this.grouping, other.grouping) && Utility.equals(this.padder, other.padder) && Utility.equals(this.integerWidth, other.integerWidth) && Utility.equals(this.symbols, other.symbols) && Utility.equals(this.unitWidth, other.unitWidth) && Utility.equals(this.sign, other.sign) && Utility.equals(this.decimal, other.decimal) && Utility.equals(this.affixProvider, other.affixProvider) && Utility.equals(this.scale, other.scale) && Utility.equals(this.rules, other.rules) && Utility.equals(this.loc, other.loc);
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
