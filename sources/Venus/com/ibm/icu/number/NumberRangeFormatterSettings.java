/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.range.RangeMacroProps;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.number.UnlocalizedNumberFormatter;
import com.ibm.icu.util.ULocale;

public abstract class NumberRangeFormatterSettings<T extends NumberRangeFormatterSettings<?>> {
    static final int KEY_MACROS = 0;
    static final int KEY_LOCALE = 1;
    static final int KEY_FORMATTER_1 = 2;
    static final int KEY_FORMATTER_2 = 3;
    static final int KEY_SAME_FORMATTERS = 4;
    static final int KEY_COLLAPSE = 5;
    static final int KEY_IDENTITY_FALLBACK = 6;
    static final int KEY_MAX = 7;
    private final NumberRangeFormatterSettings<?> parent;
    private final int key;
    private final Object value;
    private volatile RangeMacroProps resolvedMacros;

    NumberRangeFormatterSettings(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        this.parent = numberRangeFormatterSettings;
        this.key = n;
        this.value = object;
    }

    public T numberFormatterBoth(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, true)).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterFirst(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, false)).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterSecond(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, false)).create(3, unlocalizedNumberFormatter);
    }

    public T collapse(NumberRangeFormatter.RangeCollapse rangeCollapse) {
        return this.create(5, (Object)rangeCollapse);
    }

    public T identityFallback(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback) {
        return this.create(6, (Object)rangeIdentityFallback);
    }

    abstract T create(int var1, Object var2);

    RangeMacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        RangeMacroProps rangeMacroProps = new RangeMacroProps();
        NumberRangeFormatterSettings<?> numberRangeFormatterSettings = this;
        while (numberRangeFormatterSettings != null) {
            switch (numberRangeFormatterSettings.key) {
                case 0: {
                    break;
                }
                case 1: {
                    if (rangeMacroProps.loc != null) break;
                    rangeMacroProps.loc = (ULocale)numberRangeFormatterSettings.value;
                    break;
                }
                case 2: {
                    if (rangeMacroProps.formatter1 != null) break;
                    rangeMacroProps.formatter1 = (UnlocalizedNumberFormatter)numberRangeFormatterSettings.value;
                    break;
                }
                case 3: {
                    if (rangeMacroProps.formatter2 != null) break;
                    rangeMacroProps.formatter2 = (UnlocalizedNumberFormatter)numberRangeFormatterSettings.value;
                    break;
                }
                case 4: {
                    if (rangeMacroProps.sameFormatters != -1) break;
                    rangeMacroProps.sameFormatters = (Boolean)numberRangeFormatterSettings.value != false ? 1 : 0;
                    break;
                }
                case 5: {
                    if (rangeMacroProps.collapse != null) break;
                    rangeMacroProps.collapse = (NumberRangeFormatter.RangeCollapse)((Object)numberRangeFormatterSettings.value);
                    break;
                }
                case 6: {
                    if (rangeMacroProps.identityFallback != null) break;
                    rangeMacroProps.identityFallback = (NumberRangeFormatter.RangeIdentityFallback)((Object)numberRangeFormatterSettings.value);
                    break;
                }
                default: {
                    throw new AssertionError((Object)("Unknown key: " + numberRangeFormatterSettings.key));
                }
            }
            numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
        }
        if (rangeMacroProps.formatter1 != null) {
            rangeMacroProps.formatter1.resolve().loc = rangeMacroProps.loc;
        }
        if (rangeMacroProps.formatter2 != null) {
            rangeMacroProps.formatter2.resolve().loc = rangeMacroProps.loc;
        }
        this.resolvedMacros = rangeMacroProps;
        return rangeMacroProps;
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
        if (!(object instanceof NumberRangeFormatterSettings)) {
            return true;
        }
        return this.resolve().equals(((NumberRangeFormatterSettings)object).resolve());
    }
}

