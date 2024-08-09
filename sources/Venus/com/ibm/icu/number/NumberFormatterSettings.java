/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.Padder;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.NumberSkeletonImpl;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.Scale;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.math.RoundingMode;

public abstract class NumberFormatterSettings<T extends NumberFormatterSettings<?>> {
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
    private final NumberFormatterSettings<?> parent;
    private final int key;
    private final Object value;
    private volatile MacroProps resolvedMacros;

    NumberFormatterSettings(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        this.parent = numberFormatterSettings;
        this.key = n;
        this.value = object;
    }

    public T notation(Notation notation) {
        return this.create(2, notation);
    }

    public T unit(MeasureUnit measureUnit) {
        return this.create(3, measureUnit);
    }

    public T perUnit(MeasureUnit measureUnit) {
        return this.create(15, measureUnit);
    }

    public T precision(Precision precision) {
        return this.create(4, precision);
    }

    public T roundingMode(RoundingMode roundingMode) {
        return this.create(5, (Object)roundingMode);
    }

    public T grouping(NumberFormatter.GroupingStrategy groupingStrategy) {
        return this.create(6, (Object)groupingStrategy);
    }

    public T integerWidth(IntegerWidth integerWidth) {
        return this.create(8, integerWidth);
    }

    public T symbols(DecimalFormatSymbols decimalFormatSymbols) {
        decimalFormatSymbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        return this.create(9, decimalFormatSymbols);
    }

    public T symbols(NumberingSystem numberingSystem) {
        return this.create(9, numberingSystem);
    }

    public T unitWidth(NumberFormatter.UnitWidth unitWidth) {
        return this.create(10, (Object)unitWidth);
    }

    public T sign(NumberFormatter.SignDisplay signDisplay) {
        return this.create(11, (Object)signDisplay);
    }

    public T decimal(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay) {
        return this.create(12, (Object)decimalSeparatorDisplay);
    }

    public T scale(Scale scale) {
        return this.create(13, scale);
    }

    @Deprecated
    public T macros(MacroProps macroProps) {
        return this.create(0, macroProps);
    }

    @Deprecated
    public T padding(Padder padder) {
        return this.create(7, padder);
    }

    @Deprecated
    public T threshold(Long l) {
        return this.create(14, l);
    }

    public String toSkeleton() {
        return NumberSkeletonImpl.generate(this.resolve());
    }

    abstract T create(int var1, Object var2);

    MacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        MacroProps macroProps = new MacroProps();
        NumberFormatterSettings<?> numberFormatterSettings = this;
        while (numberFormatterSettings != null) {
            switch (numberFormatterSettings.key) {
                case 0: {
                    macroProps.fallback((MacroProps)numberFormatterSettings.value);
                    break;
                }
                case 1: {
                    if (macroProps.loc != null) break;
                    macroProps.loc = (ULocale)numberFormatterSettings.value;
                    break;
                }
                case 2: {
                    if (macroProps.notation != null) break;
                    macroProps.notation = (Notation)numberFormatterSettings.value;
                    break;
                }
                case 3: {
                    if (macroProps.unit != null) break;
                    macroProps.unit = (MeasureUnit)numberFormatterSettings.value;
                    break;
                }
                case 4: {
                    if (macroProps.precision != null) break;
                    macroProps.precision = (Precision)numberFormatterSettings.value;
                    break;
                }
                case 5: {
                    if (macroProps.roundingMode != null) break;
                    macroProps.roundingMode = (RoundingMode)((Object)numberFormatterSettings.value);
                    break;
                }
                case 6: {
                    if (macroProps.grouping != null) break;
                    macroProps.grouping = numberFormatterSettings.value;
                    break;
                }
                case 7: {
                    if (macroProps.padder != null) break;
                    macroProps.padder = (Padder)numberFormatterSettings.value;
                    break;
                }
                case 8: {
                    if (macroProps.integerWidth != null) break;
                    macroProps.integerWidth = (IntegerWidth)numberFormatterSettings.value;
                    break;
                }
                case 9: {
                    if (macroProps.symbols != null) break;
                    macroProps.symbols = numberFormatterSettings.value;
                    break;
                }
                case 10: {
                    if (macroProps.unitWidth != null) break;
                    macroProps.unitWidth = (NumberFormatter.UnitWidth)((Object)numberFormatterSettings.value);
                    break;
                }
                case 11: {
                    if (macroProps.sign != null) break;
                    macroProps.sign = (NumberFormatter.SignDisplay)((Object)numberFormatterSettings.value);
                    break;
                }
                case 12: {
                    if (macroProps.decimal != null) break;
                    macroProps.decimal = (NumberFormatter.DecimalSeparatorDisplay)((Object)numberFormatterSettings.value);
                    break;
                }
                case 13: {
                    if (macroProps.scale != null) break;
                    macroProps.scale = (Scale)numberFormatterSettings.value;
                    break;
                }
                case 14: {
                    if (macroProps.threshold != null) break;
                    macroProps.threshold = (Long)numberFormatterSettings.value;
                    break;
                }
                case 15: {
                    if (macroProps.perUnit != null) break;
                    macroProps.perUnit = (MeasureUnit)numberFormatterSettings.value;
                    break;
                }
                default: {
                    throw new AssertionError((Object)("Unknown key: " + numberFormatterSettings.key));
                }
            }
            numberFormatterSettings = numberFormatterSettings.parent;
        }
        this.resolvedMacros = macroProps;
        return macroProps;
    }

    public int hashCode() {
        return this.resolve().hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof NumberFormatterSettings)) {
            return true;
        }
        return this.resolve().equals(((NumberFormatterSettings)object).resolve());
    }
}

