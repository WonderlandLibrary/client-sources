/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.Scale;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.math.RoundingMode;
import java.util.Objects;

public class MacroProps
implements Cloneable {
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

    public void fallback(MacroProps macroProps) {
        if (this.notation == null) {
            this.notation = macroProps.notation;
        }
        if (this.unit == null) {
            this.unit = macroProps.unit;
        }
        if (this.perUnit == null) {
            this.perUnit = macroProps.perUnit;
        }
        if (this.precision == null) {
            this.precision = macroProps.precision;
        }
        if (this.roundingMode == null) {
            this.roundingMode = macroProps.roundingMode;
        }
        if (this.grouping == null) {
            this.grouping = macroProps.grouping;
        }
        if (this.padder == null) {
            this.padder = macroProps.padder;
        }
        if (this.integerWidth == null) {
            this.integerWidth = macroProps.integerWidth;
        }
        if (this.symbols == null) {
            this.symbols = macroProps.symbols;
        }
        if (this.unitWidth == null) {
            this.unitWidth = macroProps.unitWidth;
        }
        if (this.sign == null) {
            this.sign = macroProps.sign;
        }
        if (this.decimal == null) {
            this.decimal = macroProps.decimal;
        }
        if (this.affixProvider == null) {
            this.affixProvider = macroProps.affixProvider;
        }
        if (this.scale == null) {
            this.scale = macroProps.scale;
        }
        if (this.rules == null) {
            this.rules = macroProps.rules;
        }
        if (this.loc == null) {
            this.loc = macroProps.loc;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.notation, this.unit, this.perUnit, this.precision, this.roundingMode, this.grouping, this.padder, this.integerWidth, this.symbols, this.unitWidth, this.sign, this.decimal, this.affixProvider, this.scale, this.rules, this.loc});
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (!(object instanceof MacroProps)) {
            return true;
        }
        MacroProps macroProps = (MacroProps)object;
        return Objects.equals(this.notation, macroProps.notation) && Objects.equals(this.unit, macroProps.unit) && Objects.equals(this.perUnit, macroProps.perUnit) && Objects.equals(this.precision, macroProps.precision) && Objects.equals((Object)this.roundingMode, (Object)macroProps.roundingMode) && Objects.equals(this.grouping, macroProps.grouping) && Objects.equals(this.padder, macroProps.padder) && Objects.equals(this.integerWidth, macroProps.integerWidth) && Objects.equals(this.symbols, macroProps.symbols) && Objects.equals((Object)this.unitWidth, (Object)macroProps.unitWidth) && Objects.equals((Object)this.sign, (Object)macroProps.sign) && Objects.equals((Object)this.decimal, (Object)macroProps.decimal) && Objects.equals(this.affixProvider, macroProps.affixProvider) && Objects.equals(this.scale, macroProps.scale) && Objects.equals(this.rules, macroProps.rules) && Objects.equals(this.loc, macroProps.loc);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
    }
}

