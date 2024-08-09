/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.range;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.MissingResourceException;

public class StandardPluralRanges {
    StandardPlural[] flatTriples;
    int numTriples = 0;

    private static void getPluralRangesData(ULocale uLocale, StandardPluralRanges standardPluralRanges) {
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "pluralRanges");
        stringBuilder.append("locales/");
        stringBuilder.append(uLocale.getLanguage());
        String string2 = stringBuilder.toString();
        try {
            string = iCUResourceBundle.getStringWithFallback(string2);
        } catch (MissingResourceException missingResourceException) {
            return;
        }
        stringBuilder.setLength(0);
        stringBuilder.append("rules/");
        stringBuilder.append(string);
        string2 = stringBuilder.toString();
        PluralRangesDataSink pluralRangesDataSink = new PluralRangesDataSink(standardPluralRanges);
        iCUResourceBundle.getAllItemsWithFallback(string2, pluralRangesDataSink);
    }

    public StandardPluralRanges(ULocale uLocale) {
        StandardPluralRanges.getPluralRangesData(uLocale, this);
    }

    private void addPluralRange(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
        this.flatTriples[3 * this.numTriples] = standardPlural;
        this.flatTriples[3 * this.numTriples + 1] = standardPlural2;
        this.flatTriples[3 * this.numTriples + 2] = standardPlural3;
        ++this.numTriples;
    }

    private void setCapacity(int n) {
        this.flatTriples = new StandardPlural[n * 3];
    }

    public StandardPlural resolve(StandardPlural standardPlural, StandardPlural standardPlural2) {
        for (int i = 0; i < this.numTriples; ++i) {
            if (standardPlural != this.flatTriples[3 * i] || standardPlural2 != this.flatTriples[3 * i + 1]) continue;
            return this.flatTriples[3 * i + 2];
        }
        return StandardPlural.OTHER;
    }

    static void access$000(StandardPluralRanges standardPluralRanges, int n) {
        standardPluralRanges.setCapacity(n);
    }

    static void access$100(StandardPluralRanges standardPluralRanges, StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
        standardPluralRanges.addPluralRange(standardPlural, standardPlural2, standardPlural3);
    }

    private static final class PluralRangesDataSink
    extends UResource.Sink {
        StandardPluralRanges output;

        PluralRangesDataSink(StandardPluralRanges standardPluralRanges) {
            this.output = standardPluralRanges;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Array array = value.getArray();
            StandardPluralRanges.access$000(this.output, array.getSize());
            int n = 0;
            while (array.getValue(n, value)) {
                UResource.Array array2 = value.getArray();
                array2.getValue(0, value);
                StandardPlural standardPlural = StandardPlural.fromString(value.getString());
                array2.getValue(1, value);
                StandardPlural standardPlural2 = StandardPlural.fromString(value.getString());
                array2.getValue(2, value);
                StandardPlural standardPlural3 = StandardPlural.fromString(value.getString());
                StandardPluralRanges.access$100(this.output, standardPlural, standardPlural2, standardPlural3);
                ++n;
            }
        }
    }
}

