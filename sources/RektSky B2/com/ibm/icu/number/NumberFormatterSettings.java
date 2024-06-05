package com.ibm.icu.number;

import java.math.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.number.*;
import com.ibm.icu.util.*;

public abstract class NumberFormatterSettings<T extends NumberFormatterSettings<?>>
{
    static final int KEY_MACROS = 0;
    static final int KEY_LOCALE = 1;
    static final int KEY_NOTATION = 2;
    static final int KEY_UNIT = 3;
    static final int KEY_PRECISION = 4;
    static final int KEY_ROUNDING_MODE = 5;
    static final int KEY_GROUPING = 6;
    static final int KEY_PADDER = 7;
    static final int KEY_INTEGER = 8;
    static final int KEY_SYMBOLS = 9;
    static final int KEY_UNIT_WIDTH = 10;
    static final int KEY_SIGN = 11;
    static final int KEY_DECIMAL = 12;
    static final int KEY_SCALE = 13;
    static final int KEY_THRESHOLD = 14;
    static final int KEY_PER_UNIT = 15;
    static final int KEY_MAX = 16;
    final NumberFormatterSettings<?> parent;
    final int key;
    final Object value;
    volatile MacroProps resolvedMacros;
    
    NumberFormatterSettings(final NumberFormatterSettings<?> parent, final int key, final Object value) {
        this.parent = parent;
        this.key = key;
        this.value = value;
    }
    
    public T notation(final Notation notation) {
        return this.create(2, notation);
    }
    
    public T unit(final MeasureUnit unit) {
        return this.create(3, unit);
    }
    
    public T perUnit(final MeasureUnit perUnit) {
        return this.create(15, perUnit);
    }
    
    public T precision(final Precision precision) {
        return this.create(4, precision);
    }
    
    @Deprecated
    public T rounding(final Precision rounder) {
        return this.precision(rounder);
    }
    
    public T roundingMode(final RoundingMode roundingMode) {
        return this.create(5, roundingMode);
    }
    
    public T grouping(final NumberFormatter.GroupingStrategy strategy) {
        return this.create(6, strategy);
    }
    
    public T integerWidth(final IntegerWidth style) {
        return this.create(8, style);
    }
    
    public T symbols(DecimalFormatSymbols symbols) {
        symbols = (DecimalFormatSymbols)symbols.clone();
        return this.create(9, symbols);
    }
    
    public T symbols(final NumberingSystem ns) {
        return this.create(9, ns);
    }
    
    public T unitWidth(final NumberFormatter.UnitWidth style) {
        return this.create(10, style);
    }
    
    public T sign(final NumberFormatter.SignDisplay style) {
        return this.create(11, style);
    }
    
    public T decimal(final NumberFormatter.DecimalSeparatorDisplay style) {
        return this.create(12, style);
    }
    
    public T scale(final Scale scale) {
        return this.create(13, scale);
    }
    
    @Deprecated
    public T macros(final MacroProps macros) {
        return this.create(0, macros);
    }
    
    @Deprecated
    public T padding(final Padder padder) {
        return this.create(7, padder);
    }
    
    @Deprecated
    public T threshold(final Long threshold) {
        return this.create(14, threshold);
    }
    
    public String toSkeleton() {
        return NumberSkeletonImpl.generate(this.resolve());
    }
    
    abstract T create(final int p0, final Object p1);
    
    MacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        final MacroProps macros = new MacroProps();
        for (NumberFormatterSettings<?> current = this; current != null; current = current.parent) {
            switch (current.key) {
                case 0: {
                    macros.fallback((MacroProps)current.value);
                    break;
                }
                case 1: {
                    if (macros.loc == null) {
                        macros.loc = (ULocale)current.value;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (macros.notation == null) {
                        macros.notation = (Notation)current.value;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (macros.unit == null) {
                        macros.unit = (MeasureUnit)current.value;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (macros.precision == null) {
                        macros.precision = (Precision)current.value;
                        break;
                    }
                    break;
                }
                case 5: {
                    if (macros.roundingMode == null) {
                        macros.roundingMode = (RoundingMode)current.value;
                        break;
                    }
                    break;
                }
                case 6: {
                    if (macros.grouping == null) {
                        macros.grouping = current.value;
                        break;
                    }
                    break;
                }
                case 7: {
                    if (macros.padder == null) {
                        macros.padder = (Padder)current.value;
                        break;
                    }
                    break;
                }
                case 8: {
                    if (macros.integerWidth == null) {
                        macros.integerWidth = (IntegerWidth)current.value;
                        break;
                    }
                    break;
                }
                case 9: {
                    if (macros.symbols == null) {
                        macros.symbols = current.value;
                        break;
                    }
                    break;
                }
                case 10: {
                    if (macros.unitWidth == null) {
                        macros.unitWidth = (NumberFormatter.UnitWidth)current.value;
                        break;
                    }
                    break;
                }
                case 11: {
                    if (macros.sign == null) {
                        macros.sign = (NumberFormatter.SignDisplay)current.value;
                        break;
                    }
                    break;
                }
                case 12: {
                    if (macros.decimal == null) {
                        macros.decimal = (NumberFormatter.DecimalSeparatorDisplay)current.value;
                        break;
                    }
                    break;
                }
                case 13: {
                    if (macros.scale == null) {
                        macros.scale = (Scale)current.value;
                        break;
                    }
                    break;
                }
                case 14: {
                    if (macros.threshold == null) {
                        macros.threshold = (Long)current.value;
                        break;
                    }
                    break;
                }
                case 15: {
                    if (macros.perUnit == null) {
                        macros.perUnit = (MeasureUnit)current.value;
                        break;
                    }
                    break;
                }
                default: {
                    throw new AssertionError((Object)("Unknown key: " + current.key));
                }
            }
        }
        return this.resolvedMacros = macros;
    }
    
    @Override
    public int hashCode() {
        return this.resolve().hashCode();
    }
    
    @Override
    public boolean equals(final Object other) {
        return this == other || (other != null && other instanceof NumberFormatterSettings && this.resolve().equals(((NumberFormatterSettings)other).resolve()));
    }
}
