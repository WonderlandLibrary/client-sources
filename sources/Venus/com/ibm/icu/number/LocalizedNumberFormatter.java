/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.LocalizedNumberFormatterAsFormat;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.NumberFormatterImpl;
import com.ibm.icu.number.NumberFormatterSettings;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;
import java.text.Format;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LocalizedNumberFormatter
extends NumberFormatterSettings<LocalizedNumberFormatter> {
    static final AtomicLongFieldUpdater<LocalizedNumberFormatter> callCount = AtomicLongFieldUpdater.newUpdater(LocalizedNumberFormatter.class, "callCountInternal");
    volatile long callCountInternal;
    volatile LocalizedNumberFormatter savedWithUnit;
    volatile NumberFormatterImpl compiled;

    LocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        super(numberFormatterSettings, n, object);
    }

    public FormattedNumber format(long l) {
        return this.format(new DecimalQuantity_DualStorageBCD(l));
    }

    public FormattedNumber format(double d) {
        return this.format(new DecimalQuantity_DualStorageBCD(d));
    }

    public FormattedNumber format(Number number) {
        return this.format(new DecimalQuantity_DualStorageBCD(number));
    }

    public FormattedNumber format(Measure measure) {
        MeasureUnit measureUnit = measure.getUnit();
        Number number = measure.getNumber();
        if (Objects.equals(this.resolve().unit, measureUnit)) {
            return this.format(number);
        }
        LocalizedNumberFormatter localizedNumberFormatter = this.savedWithUnit;
        if (localizedNumberFormatter == null || !Objects.equals(localizedNumberFormatter.resolve().unit, measureUnit)) {
            this.savedWithUnit = localizedNumberFormatter = new LocalizedNumberFormatter(this, 3, measureUnit);
        }
        return localizedNumberFormatter.format(number);
    }

    public Format toFormat() {
        return new LocalizedNumberFormatterAsFormat(this, this.resolve().loc);
    }

    private FormattedNumber format(DecimalQuantity decimalQuantity) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        this.formatImpl(decimalQuantity, formattedStringBuilder);
        return new FormattedNumber(formattedStringBuilder, decimalQuantity);
    }

    @Deprecated
    public void formatImpl(DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        if (this.computeCompiled()) {
            this.compiled.format(decimalQuantity, formattedStringBuilder);
        } else {
            NumberFormatterImpl.formatStatic(this.resolve(), decimalQuantity, formattedStringBuilder);
        }
    }

    @Deprecated
    public String getAffixImpl(boolean bl, boolean bl2) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        byte by = (byte)(bl2 ? -1 : 1);
        StandardPlural standardPlural = StandardPlural.OTHER;
        int n = this.computeCompiled() ? this.compiled.getPrefixSuffix(by, standardPlural, formattedStringBuilder) : NumberFormatterImpl.getPrefixSuffixStatic(this.resolve(), by, standardPlural, formattedStringBuilder);
        if (bl) {
            return formattedStringBuilder.subSequence(0, n).toString();
        }
        return formattedStringBuilder.subSequence(n, formattedStringBuilder.length()).toString();
    }

    private boolean computeCompiled() {
        MacroProps macroProps = this.resolve();
        long l = callCount.incrementAndGet(this);
        if (l == macroProps.threshold) {
            this.compiled = new NumberFormatterImpl(macroProps);
            return false;
        }
        return this.compiled == null;
    }

    @Override
    LocalizedNumberFormatter create(int n, Object object) {
        return new LocalizedNumberFormatter(this, n, object);
    }

    @Override
    NumberFormatterSettings create(int n, Object object) {
        return this.create(n, object);
    }
}

