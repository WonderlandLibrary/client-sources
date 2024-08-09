/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodFormatterFactory;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodFormatter;
import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;

class BasicPeriodFormatter
implements PeriodFormatter {
    private BasicPeriodFormatterFactory factory;
    private String localeName;
    private PeriodFormatterData data;
    private BasicPeriodFormatterFactory.Customizations customs;

    BasicPeriodFormatter(BasicPeriodFormatterFactory basicPeriodFormatterFactory, String string, PeriodFormatterData periodFormatterData, BasicPeriodFormatterFactory.Customizations customizations) {
        this.factory = basicPeriodFormatterFactory;
        this.localeName = string;
        this.data = periodFormatterData;
        this.customs = customizations;
    }

    @Override
    public String format(Period period) {
        if (!period.isSet()) {
            throw new IllegalArgumentException("period is not set");
        }
        return this.format(period.timeLimit, period.inFuture, period.counts);
    }

    @Override
    public PeriodFormatter withLocale(String string) {
        if (!this.localeName.equals(string)) {
            PeriodFormatterData periodFormatterData = this.factory.getData(string);
            return new BasicPeriodFormatter(this.factory, string, periodFormatterData, this.customs);
        }
        return this;
    }

    private String format(int n, boolean bl, int[] nArray) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8 = 0;
        for (n7 = 0; n7 < nArray.length; ++n7) {
            if (nArray[n7] <= 0) continue;
            n8 |= 1 << n7;
        }
        if (!this.data.allowZero()) {
            n7 = 0;
            n6 = 1;
            while (n7 < nArray.length) {
                if ((n8 & n6) != 0 && nArray[n7] == 1) {
                    n8 &= ~n6;
                }
                ++n7;
                n6 <<= 1;
            }
            if (n8 == 0) {
                return null;
            }
        }
        n7 = 0;
        if (this.data.useMilliseconds() != 0 && (n8 & 1 << TimeUnit.MILLISECOND.ordinal) != 0) {
            n6 = TimeUnit.SECOND.ordinal;
            n5 = TimeUnit.MILLISECOND.ordinal;
            n4 = 1 << n6;
            n3 = 1 << n5;
            switch (this.data.useMilliseconds()) {
                case 2: {
                    if ((n8 & n4) == 0) break;
                    int n9 = n6;
                    nArray[n9] = nArray[n9] + (nArray[n5] - 1) / 1000;
                    n8 &= ~n3;
                    n7 = 1;
                    break;
                }
                case 1: {
                    if ((n8 & n4) == 0) {
                        n8 |= n4;
                        nArray[n6] = 1;
                    }
                    int n10 = n6;
                    nArray[n10] = nArray[n10] + (nArray[n5] - 1) / 1000;
                    n8 &= ~n3;
                    n7 = 1;
                }
            }
        }
        n5 = nArray.length - 1;
        for (n6 = 0; n6 < nArray.length && (n8 & 1 << n6) == 0; ++n6) {
        }
        while (n5 > n6 && (n8 & 1 << n5) == 0) {
            --n5;
        }
        n4 = 1;
        for (n3 = n6; n3 <= n5; ++n3) {
            if ((n8 & 1 << n3) == 0 || nArray[n3] <= 1) continue;
            n4 = 0;
            break;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.customs.displayLimit || n4 != 0) {
            n = 0;
        }
        int n11 = !this.customs.displayDirection || n4 != 0 ? 0 : (bl ? 2 : 1);
        boolean bl2 = this.data.appendPrefix(n, n11, stringBuffer);
        boolean bl3 = n6 != n5;
        boolean bl4 = true;
        boolean bl5 = false;
        boolean bl6 = this.customs.separatorVariant != 0;
        int n12 = n2 = n6;
        while (n2 <= n5) {
            if (bl5) {
                this.data.appendSkippedUnit(stringBuffer);
                bl5 = false;
                bl4 = true;
            }
            while (++n12 < n5 && (n8 & 1 << n12) == 0) {
                bl5 = true;
            }
            TimeUnit timeUnit = TimeUnit.units[n2];
            int n13 = nArray[n2] - 1;
            int n14 = this.customs.countVariant;
            if (n2 == n5) {
                if (n7 != 0) {
                    n14 = 5;
                }
            } else {
                n14 = 0;
            }
            boolean bl7 = n2 == n5;
            boolean bl8 = this.data.appendUnit(timeUnit, n13, n14, this.customs.unitVariant, bl6, bl2, bl3, bl7, bl4, stringBuffer);
            bl5 |= bl8;
            bl4 = false;
            if (this.customs.separatorVariant != 0 && n12 <= n5) {
                boolean bl9 = n2 == n6;
                boolean bl10 = n12 == n5;
                boolean bl11 = this.customs.separatorVariant == 2;
                bl2 = this.data.appendUnitSeparator(timeUnit, bl11, bl9, bl10, stringBuffer);
            } else {
                bl2 = false;
            }
            n2 = n12;
        }
        this.data.appendSuffix(n, n11, stringBuffer);
        return stringBuffer.toString();
    }
}

