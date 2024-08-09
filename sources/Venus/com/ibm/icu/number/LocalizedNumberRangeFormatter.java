/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.number.FormattedNumberRange;
import com.ibm.icu.number.NumberRangeFormatterImpl;
import com.ibm.icu.number.NumberRangeFormatterSettings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LocalizedNumberRangeFormatter
extends NumberRangeFormatterSettings<LocalizedNumberRangeFormatter> {
    private volatile NumberRangeFormatterImpl fImpl;

    LocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        super(numberRangeFormatterSettings, n, object);
    }

    public FormattedNumberRange formatRange(int n, int n2) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(n);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD2 = new DecimalQuantity_DualStorageBCD(n2);
        return this.formatImpl(decimalQuantity_DualStorageBCD, decimalQuantity_DualStorageBCD2, n == n2);
    }

    public FormattedNumberRange formatRange(double d, double d2) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD2 = new DecimalQuantity_DualStorageBCD(d2);
        return this.formatImpl(decimalQuantity_DualStorageBCD, decimalQuantity_DualStorageBCD2, d == d2);
    }

    public FormattedNumberRange formatRange(Number number, Number number2) {
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("Cannot format null values in range");
        }
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(number);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD2 = new DecimalQuantity_DualStorageBCD(number2);
        return this.formatImpl(decimalQuantity_DualStorageBCD, decimalQuantity_DualStorageBCD2, number.equals(number2));
    }

    FormattedNumberRange formatImpl(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, boolean bl) {
        if (this.fImpl == null) {
            this.fImpl = new NumberRangeFormatterImpl(this.resolve());
        }
        return this.fImpl.format(decimalQuantity, decimalQuantity2, bl);
    }

    @Override
    LocalizedNumberRangeFormatter create(int n, Object object) {
        return new LocalizedNumberRangeFormatter(this, n, object);
    }

    @Override
    NumberRangeFormatterSettings create(int n, Object object) {
        return this.create(n, object);
    }
}

