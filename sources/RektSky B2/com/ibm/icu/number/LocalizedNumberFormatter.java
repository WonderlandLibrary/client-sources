package com.ibm.icu.number;

import java.util.concurrent.atomic.*;
import com.ibm.icu.util.*;
import java.text.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.impl.number.*;

public class LocalizedNumberFormatter extends NumberFormatterSettings<LocalizedNumberFormatter>
{
    static final AtomicLongFieldUpdater<LocalizedNumberFormatter> callCount;
    volatile long callCountInternal;
    volatile LocalizedNumberFormatter savedWithUnit;
    volatile NumberFormatterImpl compiled;
    
    LocalizedNumberFormatter(final NumberFormatterSettings<?> parent, final int key, final Object value) {
        super(parent, key, value);
    }
    
    public FormattedNumber format(final long input) {
        return this.format(new DecimalQuantity_DualStorageBCD(input));
    }
    
    public FormattedNumber format(final double input) {
        return this.format(new DecimalQuantity_DualStorageBCD(input));
    }
    
    public FormattedNumber format(final Number input) {
        return this.format(new DecimalQuantity_DualStorageBCD(input));
    }
    
    public FormattedNumber format(final Measure input) {
        final MeasureUnit unit = input.getUnit();
        final Number number = input.getNumber();
        if (Utility.equals(this.resolve().unit, unit)) {
            return this.format(number);
        }
        LocalizedNumberFormatter withUnit = this.savedWithUnit;
        if (withUnit == null || !Utility.equals(withUnit.resolve().unit, unit)) {
            withUnit = new LocalizedNumberFormatter(this, 3, unit);
            this.savedWithUnit = withUnit;
        }
        return withUnit.format(number);
    }
    
    public Format toFormat() {
        return new LocalizedNumberFormatterAsFormat(this, this.resolve().loc);
    }
    
    @Deprecated
    public FormattedNumber format(final DecimalQuantity fq) {
        final NumberStringBuilder string = new NumberStringBuilder();
        if (this.computeCompiled()) {
            this.compiled.apply(fq, string);
        }
        else {
            NumberFormatterImpl.applyStatic(this.resolve(), fq, string);
        }
        return new FormattedNumber(string, fq);
    }
    
    @Deprecated
    public String getAffixImpl(final boolean isPrefix, final boolean isNegative) {
        final NumberStringBuilder string = new NumberStringBuilder();
        final byte signum = (byte)(isNegative ? -1 : 1);
        final StandardPlural plural = StandardPlural.OTHER;
        int prefixLength;
        if (this.computeCompiled()) {
            prefixLength = this.compiled.getPrefixSuffix(signum, plural, string);
        }
        else {
            prefixLength = NumberFormatterImpl.getPrefixSuffixStatic(this.resolve(), signum, plural, string);
        }
        if (isPrefix) {
            return string.subSequence(0, prefixLength).toString();
        }
        return string.subSequence(prefixLength, string.length()).toString();
    }
    
    private boolean computeCompiled() {
        final MacroProps macros = this.resolve();
        final long currentCount = LocalizedNumberFormatter.callCount.incrementAndGet(this);
        if (currentCount == macros.threshold) {
            this.compiled = NumberFormatterImpl.fromMacros(macros);
            return true;
        }
        return this.compiled != null;
    }
    
    @Override
    LocalizedNumberFormatter create(final int key, final Object value) {
        return new LocalizedNumberFormatter(this, key, value);
    }
    
    static {
        callCount = AtomicLongFieldUpdater.newUpdater(LocalizedNumberFormatter.class, "callCountInternal");
    }
}
