/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.CompactData;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.MutablePatternModifier;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CompactNotation
extends Notation {
    final CompactDecimalFormat.CompactStyle compactStyle;
    final Map<String, Map<String, String>> compactCustomData;

    @Deprecated
    public static CompactNotation forCustomData(Map<String, Map<String, String>> map) {
        return new CompactNotation(map);
    }

    CompactNotation(CompactDecimalFormat.CompactStyle compactStyle) {
        this.compactCustomData = null;
        this.compactStyle = compactStyle;
    }

    CompactNotation(Map<String, Map<String, String>> map) {
        this.compactStyle = null;
        this.compactCustomData = map;
    }

    MicroPropsGenerator withLocaleData(ULocale uLocale, String string, CompactData.CompactType compactType, PluralRules pluralRules, MutablePatternModifier mutablePatternModifier, MicroPropsGenerator microPropsGenerator) {
        return new CompactHandler(this, uLocale, string, compactType, pluralRules, mutablePatternModifier, microPropsGenerator, null);
    }

    private static class CompactHandler
    implements MicroPropsGenerator {
        final PluralRules rules;
        final MicroPropsGenerator parent;
        final Map<String, MutablePatternModifier.ImmutablePatternModifier> precomputedMods;
        final CompactData data;
        static final boolean $assertionsDisabled = !CompactNotation.class.desiredAssertionStatus();

        private CompactHandler(CompactNotation compactNotation, ULocale uLocale, String string, CompactData.CompactType compactType, PluralRules pluralRules, MutablePatternModifier mutablePatternModifier, MicroPropsGenerator microPropsGenerator) {
            this.rules = pluralRules;
            this.parent = microPropsGenerator;
            this.data = new CompactData();
            if (compactNotation.compactStyle != null) {
                this.data.populate(uLocale, string, compactNotation.compactStyle, compactType);
            } else {
                this.data.populate(compactNotation.compactCustomData);
            }
            if (mutablePatternModifier != null) {
                this.precomputedMods = new HashMap<String, MutablePatternModifier.ImmutablePatternModifier>();
                this.precomputeAllModifiers(mutablePatternModifier);
            } else {
                this.precomputedMods = null;
            }
        }

        private void precomputeAllModifiers(MutablePatternModifier mutablePatternModifier) {
            HashSet<String> hashSet = new HashSet<String>();
            this.data.getUniquePatterns(hashSet);
            for (String string : hashSet) {
                PatternStringParser.ParsedPatternInfo parsedPatternInfo = PatternStringParser.parseToPatternInfo(string);
                mutablePatternModifier.setPatternInfo(parsedPatternInfo, NumberFormat.Field.COMPACT);
                this.precomputedMods.put(string, mutablePatternModifier.createImmutable());
            }
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            int n;
            MicroProps microProps = this.parent.processQuantity(decimalQuantity);
            if (!$assertionsDisabled && microProps.rounder == null) {
                throw new AssertionError();
            }
            if (decimalQuantity.isZeroish()) {
                n = 0;
                microProps.rounder.apply(decimalQuantity);
            } else {
                int n2 = microProps.rounder.chooseMultiplierAndApply(decimalQuantity, this.data);
                n = decimalQuantity.isZeroish() ? 0 : decimalQuantity.getMagnitude();
                n -= n2;
            }
            StandardPlural standardPlural = decimalQuantity.getStandardPlural(this.rules);
            String string = this.data.getPattern(n, standardPlural);
            if (string != null) {
                if (this.precomputedMods != null) {
                    MutablePatternModifier.ImmutablePatternModifier immutablePatternModifier = this.precomputedMods.get(string);
                    immutablePatternModifier.applyToMicros(microProps, decimalQuantity);
                } else {
                    if (!$assertionsDisabled && !(microProps.modMiddle instanceof MutablePatternModifier)) {
                        throw new AssertionError();
                    }
                    PatternStringParser.ParsedPatternInfo parsedPatternInfo = PatternStringParser.parseToPatternInfo(string);
                    ((MutablePatternModifier)microProps.modMiddle).setPatternInfo(parsedPatternInfo, NumberFormat.Field.COMPACT);
                }
            }
            microProps.rounder = Precision.constructPassThrough();
            return microProps;
        }

        CompactHandler(CompactNotation compactNotation, ULocale uLocale, String string, CompactData.CompactType compactType, PluralRules pluralRules, MutablePatternModifier mutablePatternModifier, MicroPropsGenerator microPropsGenerator, 1 var8_8) {
            this(compactNotation, uLocale, string, compactType, pluralRules, mutablePatternModifier, microPropsGenerator);
        }
    }
}

