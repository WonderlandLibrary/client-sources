/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.CompactNotation;
import com.ibm.icu.number.FractionPrecision;
import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.Notation;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.Scale;
import com.ibm.icu.number.ScientificNotation;
import com.ibm.icu.number.SimpleNotation;
import com.ibm.icu.number.SkeletonSyntaxException;
import com.ibm.icu.number.UnlocalizedNumberFormatter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberingSystem;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.NoUnit;
import com.ibm.icu.util.StringTrieBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

class NumberSkeletonImpl {
    static final StemEnum[] STEM_ENUM_VALUES;
    static final String SERIALIZED_STEM_TRIE;
    private static final CacheBase<String, UnlocalizedNumberFormatter, Void> cache;
    static final boolean $assertionsDisabled;

    NumberSkeletonImpl() {
    }

    static String buildStemTrie() {
        CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
        charsTrieBuilder.add("compact-short", StemEnum.STEM_COMPACT_SHORT.ordinal());
        charsTrieBuilder.add("compact-long", StemEnum.STEM_COMPACT_LONG.ordinal());
        charsTrieBuilder.add("scientific", StemEnum.STEM_SCIENTIFIC.ordinal());
        charsTrieBuilder.add("engineering", StemEnum.STEM_ENGINEERING.ordinal());
        charsTrieBuilder.add("notation-simple", StemEnum.STEM_NOTATION_SIMPLE.ordinal());
        charsTrieBuilder.add("base-unit", StemEnum.STEM_BASE_UNIT.ordinal());
        charsTrieBuilder.add("percent", StemEnum.STEM_PERCENT.ordinal());
        charsTrieBuilder.add("permille", StemEnum.STEM_PERMILLE.ordinal());
        charsTrieBuilder.add("precision-integer", StemEnum.STEM_PRECISION_INTEGER.ordinal());
        charsTrieBuilder.add("precision-unlimited", StemEnum.STEM_PRECISION_UNLIMITED.ordinal());
        charsTrieBuilder.add("precision-currency-standard", StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal());
        charsTrieBuilder.add("precision-currency-cash", StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal());
        charsTrieBuilder.add("rounding-mode-ceiling", StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal());
        charsTrieBuilder.add("rounding-mode-floor", StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal());
        charsTrieBuilder.add("rounding-mode-down", StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-up", StemEnum.STEM_ROUNDING_MODE_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-half-even", StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-down", StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-up", StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-unnecessary", StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal());
        charsTrieBuilder.add("group-off", StemEnum.STEM_GROUP_OFF.ordinal());
        charsTrieBuilder.add("group-min2", StemEnum.STEM_GROUP_MIN2.ordinal());
        charsTrieBuilder.add("group-auto", StemEnum.STEM_GROUP_AUTO.ordinal());
        charsTrieBuilder.add("group-on-aligned", StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
        charsTrieBuilder.add("group-thousands", StemEnum.STEM_GROUP_THOUSANDS.ordinal());
        charsTrieBuilder.add("latin", StemEnum.STEM_LATIN.ordinal());
        charsTrieBuilder.add("unit-width-narrow", StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal());
        charsTrieBuilder.add("unit-width-short", StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal());
        charsTrieBuilder.add("unit-width-full-name", StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal());
        charsTrieBuilder.add("unit-width-iso-code", StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal());
        charsTrieBuilder.add("unit-width-hidden", StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal());
        charsTrieBuilder.add("sign-auto", StemEnum.STEM_SIGN_AUTO.ordinal());
        charsTrieBuilder.add("sign-always", StemEnum.STEM_SIGN_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-never", StemEnum.STEM_SIGN_NEVER.ordinal());
        charsTrieBuilder.add("sign-accounting", StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
        charsTrieBuilder.add("sign-accounting-always", StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-except-zero", StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("sign-accounting-except-zero", StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("decimal-auto", StemEnum.STEM_DECIMAL_AUTO.ordinal());
        charsTrieBuilder.add("decimal-always", StemEnum.STEM_DECIMAL_ALWAYS.ordinal());
        charsTrieBuilder.add("precision-increment", StemEnum.STEM_PRECISION_INCREMENT.ordinal());
        charsTrieBuilder.add("measure-unit", StemEnum.STEM_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("per-measure-unit", StemEnum.STEM_PER_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("currency", StemEnum.STEM_CURRENCY.ordinal());
        charsTrieBuilder.add("integer-width", StemEnum.STEM_INTEGER_WIDTH.ordinal());
        charsTrieBuilder.add("numbering-system", StemEnum.STEM_NUMBERING_SYSTEM.ordinal());
        charsTrieBuilder.add("scale", StemEnum.STEM_SCALE.ordinal());
        return charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.FAST).toString();
    }

    public static UnlocalizedNumberFormatter getOrCreate(String string) {
        return cache.getInstance(string, null);
    }

    public static UnlocalizedNumberFormatter create(String string) {
        MacroProps macroProps = NumberSkeletonImpl.parseSkeleton(string);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros(macroProps);
    }

    public static String generate(MacroProps macroProps) {
        StringBuilder stringBuilder = new StringBuilder();
        NumberSkeletonImpl.generateSkeleton(macroProps, stringBuilder);
        return stringBuilder.toString();
    }

    private static MacroProps parseSkeleton(String string) {
        string = string + " ";
        MacroProps macroProps = new MacroProps();
        StringSegment stringSegment = new StringSegment(string, false);
        CharsTrie charsTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
        ParseState parseState = ParseState.STATE_NULL;
        int n = 0;
        while (n < stringSegment.length()) {
            boolean bl;
            int n2 = stringSegment.codePointAt(n);
            boolean bl2 = PatternProps.isWhiteSpace(n2);
            boolean bl3 = bl = n2 == 47;
            if (!bl2 && !bl) {
                n += Character.charCount(n2);
                if (parseState != ParseState.STATE_NULL) continue;
                charsTrie.nextForCodePoint(n2);
                continue;
            }
            if (n != 0) {
                stringSegment.setLength(n);
                if (parseState == ParseState.STATE_NULL) {
                    parseState = NumberSkeletonImpl.parseStem(stringSegment, charsTrie, macroProps);
                    charsTrie.reset();
                } else {
                    parseState = NumberSkeletonImpl.parseOption(parseState, stringSegment, macroProps);
                }
                stringSegment.resetLength();
                stringSegment.adjustOffset(n);
                n = 0;
            } else if (parseState != ParseState.STATE_NULL) {
                stringSegment.setLength(Character.charCount(n2));
                throw new SkeletonSyntaxException("Unexpected separator character", stringSegment);
            }
            if (bl && parseState == ParseState.STATE_NULL) {
                stringSegment.setLength(Character.charCount(n2));
                throw new SkeletonSyntaxException("Unexpected option separator", stringSegment);
            }
            if (bl2 && parseState != ParseState.STATE_NULL) {
                switch (parseState) {
                    case STATE_INCREMENT_PRECISION: 
                    case STATE_MEASURE_UNIT: 
                    case STATE_PER_MEASURE_UNIT: 
                    case STATE_CURRENCY_UNIT: 
                    case STATE_INTEGER_WIDTH: 
                    case STATE_NUMBERING_SYSTEM: 
                    case STATE_SCALE: {
                        stringSegment.setLength(Character.charCount(n2));
                        throw new SkeletonSyntaxException("Stem requires an option", stringSegment);
                    }
                }
                parseState = ParseState.STATE_NULL;
            }
            stringSegment.adjustOffset(Character.charCount(n2));
        }
        if (!$assertionsDisabled && parseState != ParseState.STATE_NULL) {
            throw new AssertionError();
        }
        return macroProps;
    }

    private static ParseState parseStem(StringSegment stringSegment, CharsTrie charsTrie, MacroProps macroProps) {
        switch (stringSegment.charAt(0)) {
            case '.': {
                NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                BlueprintHelpers.access$000(stringSegment, macroProps);
                return ParseState.STATE_FRACTION_PRECISION;
            }
            case '@': {
                NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                BlueprintHelpers.access$100(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
        }
        BytesTrie.Result result = charsTrie.current();
        if (result != BytesTrie.Result.INTERMEDIATE_VALUE && result != BytesTrie.Result.FINAL_VALUE) {
            throw new SkeletonSyntaxException("Unknown stem", stringSegment);
        }
        StemEnum stemEnum = STEM_ENUM_VALUES[charsTrie.getValue()];
        switch (stemEnum) {
            case STEM_COMPACT_SHORT: 
            case STEM_COMPACT_LONG: 
            case STEM_SCIENTIFIC: 
            case STEM_ENGINEERING: 
            case STEM_NOTATION_SIMPLE: {
                NumberSkeletonImpl.checkNull(macroProps.notation, stringSegment);
                macroProps.notation = StemToObject.access$200(stemEnum);
                switch (stemEnum) {
                    case STEM_SCIENTIFIC: 
                    case STEM_ENGINEERING: {
                        return ParseState.STATE_SCIENTIFIC;
                    }
                }
                return ParseState.STATE_NULL;
            }
            case STEM_BASE_UNIT: 
            case STEM_PERCENT: 
            case STEM_PERMILLE: {
                NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                macroProps.unit = StemToObject.access$300(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_PRECISION_INTEGER: 
            case STEM_PRECISION_UNLIMITED: 
            case STEM_PRECISION_CURRENCY_STANDARD: 
            case STEM_PRECISION_CURRENCY_CASH: {
                NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                macroProps.precision = StemToObject.access$400(stemEnum);
                switch (stemEnum) {
                    case STEM_PRECISION_INTEGER: {
                        return ParseState.STATE_FRACTION_PRECISION;
                    }
                }
                return ParseState.STATE_NULL;
            }
            case STEM_ROUNDING_MODE_CEILING: 
            case STEM_ROUNDING_MODE_FLOOR: 
            case STEM_ROUNDING_MODE_DOWN: 
            case STEM_ROUNDING_MODE_UP: 
            case STEM_ROUNDING_MODE_HALF_EVEN: 
            case STEM_ROUNDING_MODE_HALF_DOWN: 
            case STEM_ROUNDING_MODE_HALF_UP: 
            case STEM_ROUNDING_MODE_UNNECESSARY: {
                NumberSkeletonImpl.checkNull((Object)macroProps.roundingMode, stringSegment);
                macroProps.roundingMode = StemToObject.access$500(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_GROUP_OFF: 
            case STEM_GROUP_MIN2: 
            case STEM_GROUP_AUTO: 
            case STEM_GROUP_ON_ALIGNED: 
            case STEM_GROUP_THOUSANDS: {
                NumberSkeletonImpl.checkNull(macroProps.grouping, stringSegment);
                macroProps.grouping = StemToObject.access$600(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_LATIN: {
                NumberSkeletonImpl.checkNull(macroProps.symbols, stringSegment);
                macroProps.symbols = NumberingSystem.LATIN;
                return ParseState.STATE_NULL;
            }
            case STEM_UNIT_WIDTH_NARROW: 
            case STEM_UNIT_WIDTH_SHORT: 
            case STEM_UNIT_WIDTH_FULL_NAME: 
            case STEM_UNIT_WIDTH_ISO_CODE: 
            case STEM_UNIT_WIDTH_HIDDEN: {
                NumberSkeletonImpl.checkNull((Object)macroProps.unitWidth, stringSegment);
                macroProps.unitWidth = StemToObject.access$700(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_SIGN_AUTO: 
            case STEM_SIGN_ALWAYS: 
            case STEM_SIGN_NEVER: 
            case STEM_SIGN_ACCOUNTING: 
            case STEM_SIGN_ACCOUNTING_ALWAYS: 
            case STEM_SIGN_EXCEPT_ZERO: 
            case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO: {
                NumberSkeletonImpl.checkNull((Object)macroProps.sign, stringSegment);
                macroProps.sign = StemToObject.access$800(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_DECIMAL_AUTO: 
            case STEM_DECIMAL_ALWAYS: {
                NumberSkeletonImpl.checkNull((Object)macroProps.decimal, stringSegment);
                macroProps.decimal = StemToObject.access$900(stemEnum);
                return ParseState.STATE_NULL;
            }
            case STEM_PRECISION_INCREMENT: {
                NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                return ParseState.STATE_INCREMENT_PRECISION;
            }
            case STEM_MEASURE_UNIT: {
                NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                return ParseState.STATE_MEASURE_UNIT;
            }
            case STEM_PER_MEASURE_UNIT: {
                NumberSkeletonImpl.checkNull(macroProps.perUnit, stringSegment);
                return ParseState.STATE_PER_MEASURE_UNIT;
            }
            case STEM_CURRENCY: {
                NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                return ParseState.STATE_CURRENCY_UNIT;
            }
            case STEM_INTEGER_WIDTH: {
                NumberSkeletonImpl.checkNull(macroProps.integerWidth, stringSegment);
                return ParseState.STATE_INTEGER_WIDTH;
            }
            case STEM_NUMBERING_SYSTEM: {
                NumberSkeletonImpl.checkNull(macroProps.symbols, stringSegment);
                return ParseState.STATE_NUMBERING_SYSTEM;
            }
            case STEM_SCALE: {
                NumberSkeletonImpl.checkNull(macroProps.scale, stringSegment);
                return ParseState.STATE_SCALE;
            }
        }
        throw new AssertionError();
    }

    private static ParseState parseOption(ParseState parseState, StringSegment stringSegment, MacroProps macroProps) {
        switch (parseState) {
            case STATE_CURRENCY_UNIT: {
                BlueprintHelpers.access$1000(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_MEASURE_UNIT: {
                BlueprintHelpers.access$1100(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_PER_MEASURE_UNIT: {
                BlueprintHelpers.access$1200(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_INCREMENT_PRECISION: {
                BlueprintHelpers.access$1300(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_INTEGER_WIDTH: {
                BlueprintHelpers.access$1400(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_NUMBERING_SYSTEM: {
                BlueprintHelpers.access$1500(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
            case STATE_SCALE: {
                BlueprintHelpers.access$1600(stringSegment, macroProps);
                return ParseState.STATE_NULL;
            }
        }
        switch (parseState) {
            case STATE_SCIENTIFIC: {
                if (BlueprintHelpers.access$1700(stringSegment, macroProps)) {
                    return ParseState.STATE_SCIENTIFIC;
                }
                if (!BlueprintHelpers.access$1800(stringSegment, macroProps)) break;
                return ParseState.STATE_SCIENTIFIC;
            }
        }
        switch (parseState) {
            case STATE_FRACTION_PRECISION: {
                if (!BlueprintHelpers.access$1900(stringSegment, macroProps)) break;
                return ParseState.STATE_NULL;
            }
        }
        throw new SkeletonSyntaxException("Invalid option", stringSegment);
    }

    private static void generateSkeleton(MacroProps macroProps, StringBuilder stringBuilder) {
        if (macroProps.notation != null && GeneratorHelpers.access$2000(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.unit != null && GeneratorHelpers.access$2100(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.perUnit != null && GeneratorHelpers.access$2200(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.precision != null && GeneratorHelpers.access$2300(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.roundingMode != null && GeneratorHelpers.access$2400(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.grouping != null && GeneratorHelpers.access$2500(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.integerWidth != null && GeneratorHelpers.access$2600(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.symbols != null && GeneratorHelpers.access$2700(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.unitWidth != null && GeneratorHelpers.access$2800(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.sign != null && GeneratorHelpers.access$2900(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.decimal != null && GeneratorHelpers.access$3000(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.scale != null && GeneratorHelpers.access$3100(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.padder != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom padder");
        }
        if (macroProps.affixProvider != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom affix provider");
        }
        if (macroProps.rules != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom plural rules");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
    }

    private static void checkNull(Object object, CharSequence charSequence) {
        if (object != null) {
            throw new SkeletonSyntaxException("Duplicated setting", charSequence);
        }
    }

    private static void appendMultiple(StringBuilder stringBuilder, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            stringBuilder.appendCodePoint(n);
        }
    }

    static void access$3200(StringBuilder stringBuilder, int n, int n2) {
        NumberSkeletonImpl.appendMultiple(stringBuilder, n, n2);
    }

    static {
        $assertionsDisabled = !NumberSkeletonImpl.class.desiredAssertionStatus();
        STEM_ENUM_VALUES = StemEnum.values();
        SERIALIZED_STEM_TRIE = NumberSkeletonImpl.buildStemTrie();
        cache = new SoftCache<String, UnlocalizedNumberFormatter, Void>(){

            @Override
            protected UnlocalizedNumberFormatter createInstance(String string, Void void_) {
                return NumberSkeletonImpl.create(string);
            }

            @Override
            protected Object createInstance(Object object, Object object2) {
                return this.createInstance((String)object, (Void)object2);
            }
        };
    }

    static final class GeneratorHelpers {
        static final boolean $assertionsDisabled = !NumberSkeletonImpl.class.desiredAssertionStatus();

        GeneratorHelpers() {
        }

        private static boolean notation(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.notation instanceof CompactNotation) {
                if (macroProps.notation == Notation.compactLong()) {
                    stringBuilder.append("compact-long");
                    return false;
                }
                if (macroProps.notation == Notation.compactShort()) {
                    stringBuilder.append("compact-short");
                    return false;
                }
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom compact data");
            }
            if (macroProps.notation instanceof ScientificNotation) {
                ScientificNotation scientificNotation = (ScientificNotation)macroProps.notation;
                if (scientificNotation.engineeringInterval == 3) {
                    stringBuilder.append("engineering");
                } else {
                    stringBuilder.append("scientific");
                }
                if (scientificNotation.minExponentDigits > 1) {
                    stringBuilder.append('/');
                    BlueprintHelpers.access$3300(scientificNotation.minExponentDigits, stringBuilder);
                }
                if (scientificNotation.exponentSignDisplay != NumberFormatter.SignDisplay.AUTO) {
                    stringBuilder.append('/');
                    EnumToStemString.access$3400(scientificNotation.exponentSignDisplay, stringBuilder);
                }
                return false;
            }
            if (!$assertionsDisabled && !(macroProps.notation instanceof SimpleNotation)) {
                throw new AssertionError();
            }
            return true;
        }

        private static boolean unit(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.unit instanceof Currency) {
                stringBuilder.append("currency/");
                BlueprintHelpers.access$3500((Currency)macroProps.unit, stringBuilder);
                return false;
            }
            if (macroProps.unit instanceof NoUnit) {
                if (macroProps.unit == NoUnit.PERCENT) {
                    stringBuilder.append("percent");
                    return false;
                }
                if (macroProps.unit == NoUnit.PERMILLE) {
                    stringBuilder.append("permille");
                    return false;
                }
                if (!$assertionsDisabled && macroProps.unit != NoUnit.BASE) {
                    throw new AssertionError();
                }
                return true;
            }
            stringBuilder.append("measure-unit/");
            BlueprintHelpers.access$3600(macroProps.unit, stringBuilder);
            return false;
        }

        private static boolean perUnit(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.perUnit instanceof Currency || macroProps.perUnit instanceof NoUnit) {
                throw new UnsupportedOperationException("Cannot generate number skeleton with per-unit that is not a standard measure unit");
            }
            stringBuilder.append("per-measure-unit/");
            BlueprintHelpers.access$3600(macroProps.perUnit, stringBuilder);
            return false;
        }

        private static boolean precision(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.precision instanceof Precision.InfiniteRounderImpl) {
                stringBuilder.append("precision-unlimited");
            } else if (macroProps.precision instanceof Precision.FractionRounderImpl) {
                Precision.FractionRounderImpl fractionRounderImpl = (Precision.FractionRounderImpl)macroProps.precision;
                BlueprintHelpers.access$3700(fractionRounderImpl.minFrac, fractionRounderImpl.maxFrac, stringBuilder);
            } else if (macroProps.precision instanceof Precision.SignificantRounderImpl) {
                Precision.SignificantRounderImpl significantRounderImpl = (Precision.SignificantRounderImpl)macroProps.precision;
                BlueprintHelpers.access$3800(significantRounderImpl.minSig, significantRounderImpl.maxSig, stringBuilder);
            } else if (macroProps.precision instanceof Precision.FracSigRounderImpl) {
                Precision.FracSigRounderImpl fracSigRounderImpl = (Precision.FracSigRounderImpl)macroProps.precision;
                BlueprintHelpers.access$3700(fracSigRounderImpl.minFrac, fracSigRounderImpl.maxFrac, stringBuilder);
                stringBuilder.append('/');
                if (fracSigRounderImpl.minSig == -1) {
                    BlueprintHelpers.access$3800(1, fracSigRounderImpl.maxSig, stringBuilder);
                } else {
                    BlueprintHelpers.access$3800(fracSigRounderImpl.minSig, -1, stringBuilder);
                }
            } else if (macroProps.precision instanceof Precision.IncrementRounderImpl) {
                Precision.IncrementRounderImpl incrementRounderImpl = (Precision.IncrementRounderImpl)macroProps.precision;
                stringBuilder.append("precision-increment/");
                BlueprintHelpers.access$3900(incrementRounderImpl.increment, stringBuilder);
            } else {
                if (!$assertionsDisabled && !(macroProps.precision instanceof Precision.CurrencyRounderImpl)) {
                    throw new AssertionError();
                }
                Precision.CurrencyRounderImpl currencyRounderImpl = (Precision.CurrencyRounderImpl)macroProps.precision;
                if (currencyRounderImpl.usage == Currency.CurrencyUsage.STANDARD) {
                    stringBuilder.append("precision-currency-standard");
                } else {
                    stringBuilder.append("precision-currency-cash");
                }
            }
            return false;
        }

        private static boolean roundingMode(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.roundingMode == RoundingUtils.DEFAULT_ROUNDING_MODE) {
                return true;
            }
            EnumToStemString.access$4000(macroProps.roundingMode, stringBuilder);
            return false;
        }

        private static boolean grouping(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.grouping instanceof NumberFormatter.GroupingStrategy) {
                if (macroProps.grouping == NumberFormatter.GroupingStrategy.AUTO) {
                    return true;
                }
                EnumToStemString.access$4100((NumberFormatter.GroupingStrategy)((Object)macroProps.grouping), stringBuilder);
                return false;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom Grouper");
        }

        private static boolean integerWidth(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.integerWidth.equals(IntegerWidth.DEFAULT)) {
                return true;
            }
            stringBuilder.append("integer-width/");
            BlueprintHelpers.access$4200(macroProps.integerWidth.minInt, macroProps.integerWidth.maxInt, stringBuilder);
            return false;
        }

        private static boolean symbols(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.symbols instanceof NumberingSystem) {
                NumberingSystem numberingSystem = (NumberingSystem)macroProps.symbols;
                if (numberingSystem.getName().equals("latn")) {
                    stringBuilder.append("latin");
                } else {
                    stringBuilder.append("numbering-system/");
                    BlueprintHelpers.access$4300(numberingSystem, stringBuilder);
                }
                return false;
            }
            if (!$assertionsDisabled && !(macroProps.symbols instanceof DecimalFormatSymbols)) {
                throw new AssertionError();
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom DecimalFormatSymbols");
        }

        private static boolean unitWidth(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return true;
            }
            EnumToStemString.access$4400(macroProps.unitWidth, stringBuilder);
            return false;
        }

        private static boolean sign(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.sign == NumberFormatter.SignDisplay.AUTO) {
                return true;
            }
            EnumToStemString.access$3400(macroProps.sign, stringBuilder);
            return false;
        }

        private static boolean decimal(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.decimal == NumberFormatter.DecimalSeparatorDisplay.AUTO) {
                return true;
            }
            EnumToStemString.access$4500(macroProps.decimal, stringBuilder);
            return false;
        }

        private static boolean scale(MacroProps macroProps, StringBuilder stringBuilder) {
            if (!macroProps.scale.isValid()) {
                return true;
            }
            stringBuilder.append("scale/");
            BlueprintHelpers.access$4600(macroProps.scale, stringBuilder);
            return false;
        }

        static boolean access$2000(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.notation(macroProps, stringBuilder);
        }

        static boolean access$2100(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.unit(macroProps, stringBuilder);
        }

        static boolean access$2200(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.perUnit(macroProps, stringBuilder);
        }

        static boolean access$2300(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.precision(macroProps, stringBuilder);
        }

        static boolean access$2400(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.roundingMode(macroProps, stringBuilder);
        }

        static boolean access$2500(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.grouping(macroProps, stringBuilder);
        }

        static boolean access$2600(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.integerWidth(macroProps, stringBuilder);
        }

        static boolean access$2700(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.symbols(macroProps, stringBuilder);
        }

        static boolean access$2800(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.unitWidth(macroProps, stringBuilder);
        }

        static boolean access$2900(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.sign(macroProps, stringBuilder);
        }

        static boolean access$3000(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.decimal(macroProps, stringBuilder);
        }

        static boolean access$3100(MacroProps macroProps, StringBuilder stringBuilder) {
            return GeneratorHelpers.scale(macroProps, stringBuilder);
        }
    }

    static final class BlueprintHelpers {
        static final boolean $assertionsDisabled = !NumberSkeletonImpl.class.desiredAssertionStatus();

        BlueprintHelpers() {
        }

        private static boolean parseExponentWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            if (stringSegment.charAt(0) != '+') {
                return true;
            }
            int n2 = 0;
            for (n = 1; n < stringSegment.length() && stringSegment.charAt(n) == 'e'; ++n) {
                ++n2;
            }
            if (n < stringSegment.length()) {
                return true;
            }
            macroProps.notation = ((ScientificNotation)macroProps.notation).withMinExponentDigits(n2);
            return false;
        }

        private static void generateExponentWidthOption(int n, StringBuilder stringBuilder) {
            stringBuilder.append('+');
            NumberSkeletonImpl.access$3200(stringBuilder, 101, n);
        }

        private static boolean parseExponentSignOption(StringSegment stringSegment, MacroProps macroProps) {
            CharsTrie charsTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
            BytesTrie.Result result = charsTrie.next(stringSegment, 0, stringSegment.length());
            if (result != BytesTrie.Result.INTERMEDIATE_VALUE && result != BytesTrie.Result.FINAL_VALUE) {
                return true;
            }
            NumberFormatter.SignDisplay signDisplay = StemToObject.access$800(STEM_ENUM_VALUES[charsTrie.getValue()]);
            if (signDisplay == null) {
                return true;
            }
            macroProps.notation = ((ScientificNotation)macroProps.notation).withExponentSignDisplay(signDisplay);
            return false;
        }

        private static void parseCurrencyOption(StringSegment stringSegment, MacroProps macroProps) {
            Currency currency;
            String string = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {
                currency = Currency.getInstance(string);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new SkeletonSyntaxException("Invalid currency", stringSegment, illegalArgumentException);
            }
            macroProps.unit = currency;
        }

        private static void generateCurrencyOption(Currency currency, StringBuilder stringBuilder) {
            stringBuilder.append(currency.getCurrencyCode());
        }

        private static void parseMeasureUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            for (n = 0; n < stringSegment.length() && stringSegment.charAt(n) != '-'; ++n) {
            }
            if (n == stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid measure unit option", stringSegment);
            }
            String string = stringSegment.subSequence(0, n).toString();
            String string2 = stringSegment.subSequence(n + 1, stringSegment.length()).toString();
            Set<MeasureUnit> set = MeasureUnit.getAvailable(string);
            for (MeasureUnit measureUnit : set) {
                if (!string2.equals(measureUnit.getSubtype())) continue;
                macroProps.unit = measureUnit;
                return;
            }
            throw new SkeletonSyntaxException("Unknown measure unit", stringSegment);
        }

        private static void generateMeasureUnitOption(MeasureUnit measureUnit, StringBuilder stringBuilder) {
            stringBuilder.append(measureUnit.getType());
            stringBuilder.append("-");
            stringBuilder.append(measureUnit.getSubtype());
        }

        private static void parseMeasurePerUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            MeasureUnit measureUnit = macroProps.unit;
            BlueprintHelpers.parseMeasureUnitOption(stringSegment, macroProps);
            macroProps.perUnit = macroProps.unit;
            macroProps.unit = measureUnit;
        }

        private static void parseFractionStem(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            int n2;
            if (!$assertionsDisabled && stringSegment.charAt(0) != '.') {
                throw new AssertionError();
            }
            int n3 = 0;
            for (n2 = 1; n2 < stringSegment.length() && stringSegment.charAt(n2) == '0'; ++n2) {
                ++n3;
            }
            if (n2 < stringSegment.length()) {
                if (stringSegment.charAt(n2) == '+') {
                    n = -1;
                    ++n2;
                } else {
                    n = n3;
                    while (n2 < stringSegment.length() && stringSegment.charAt(n2) == '#') {
                        ++n;
                        ++n2;
                    }
                }
            } else {
                n = n3;
            }
            if (n2 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid fraction stem", stringSegment);
            }
            macroProps.precision = n == -1 ? Precision.minFraction(n3) : Precision.minMaxFraction(n3, n);
        }

        private static void generateFractionStem(int n, int n2, StringBuilder stringBuilder) {
            if (n == 0 && n2 == 0) {
                stringBuilder.append("precision-integer");
                return;
            }
            stringBuilder.append('.');
            NumberSkeletonImpl.access$3200(stringBuilder, 48, n);
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.access$3200(stringBuilder, 35, n2 - n);
            }
        }

        private static void parseDigitsStem(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            int n2;
            if (!$assertionsDisabled && stringSegment.charAt(0) != '@') {
                throw new AssertionError();
            }
            int n3 = 0;
            for (n2 = 0; n2 < stringSegment.length() && stringSegment.charAt(n2) == '@'; ++n2) {
                ++n3;
            }
            if (n2 < stringSegment.length()) {
                if (stringSegment.charAt(n2) == '+') {
                    n = -1;
                    ++n2;
                } else {
                    n = n3;
                    while (n2 < stringSegment.length() && stringSegment.charAt(n2) == '#') {
                        ++n;
                        ++n2;
                    }
                }
            } else {
                n = n3;
            }
            if (n2 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid significant digits stem", stringSegment);
            }
            macroProps.precision = n == -1 ? Precision.minSignificantDigits(n3) : Precision.minMaxSignificantDigits(n3, n);
        }

        private static void generateDigitsStem(int n, int n2, StringBuilder stringBuilder) {
            NumberSkeletonImpl.access$3200(stringBuilder, 64, n);
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.access$3200(stringBuilder, 35, n2 - n);
            }
        }

        private static boolean parseFracSigOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            int n2;
            if (stringSegment.charAt(0) != '@') {
                return true;
            }
            int n3 = 0;
            for (n2 = 0; n2 < stringSegment.length() && stringSegment.charAt(n2) == '@'; ++n2) {
                ++n3;
            }
            if (n2 < stringSegment.length()) {
                if (stringSegment.charAt(n2) == '+') {
                    n = -1;
                    ++n2;
                } else {
                    if (n3 > 1) {
                        throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", stringSegment);
                    }
                    n = n3;
                    while (n2 < stringSegment.length() && stringSegment.charAt(n2) == '#') {
                        ++n;
                        ++n2;
                    }
                }
            } else {
                throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", stringSegment);
            }
            if (n2 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", stringSegment);
            }
            FractionPrecision fractionPrecision = (FractionPrecision)macroProps.precision;
            macroProps.precision = n == -1 ? fractionPrecision.withMinDigits(n3) : fractionPrecision.withMaxDigits(n);
            return false;
        }

        private static void parseIncrementOption(StringSegment stringSegment, MacroProps macroProps) {
            BigDecimal bigDecimal;
            String string = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {
                bigDecimal = new BigDecimal(string);
            } catch (NumberFormatException numberFormatException) {
                throw new SkeletonSyntaxException("Invalid rounding increment", stringSegment, numberFormatException);
            }
            macroProps.precision = Precision.increment(bigDecimal);
        }

        private static void generateIncrementOption(BigDecimal bigDecimal, StringBuilder stringBuilder) {
            stringBuilder.append(bigDecimal.toPlainString());
        }

        private static void parseIntegerWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            int n2 = 0;
            int n3 = 0;
            if (stringSegment.charAt(0) == '+') {
                n = -1;
                ++n2;
            } else {
                n = 0;
            }
            while (n2 < stringSegment.length() && n != -1 && stringSegment.charAt(n2) == '#') {
                ++n;
                ++n2;
            }
            if (n2 < stringSegment.length()) {
                while (n2 < stringSegment.length() && stringSegment.charAt(n2) == '0') {
                    ++n3;
                    ++n2;
                }
            }
            if (n != -1) {
                n += n3;
            }
            if (n2 < stringSegment.length()) {
                throw new SkeletonSyntaxException("Invalid integer width stem", stringSegment);
            }
            macroProps.integerWidth = n == -1 ? IntegerWidth.zeroFillTo(n3) : IntegerWidth.zeroFillTo(n3).truncateAt(n);
        }

        private static void generateIntegerWidthOption(int n, int n2, StringBuilder stringBuilder) {
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.access$3200(stringBuilder, 35, n2 - n);
            }
            NumberSkeletonImpl.access$3200(stringBuilder, 48, n);
        }

        private static void parseNumberingSystemOption(StringSegment stringSegment, MacroProps macroProps) {
            String string = stringSegment.subSequence(0, stringSegment.length()).toString();
            NumberingSystem numberingSystem = NumberingSystem.getInstanceByName(string);
            if (numberingSystem == null) {
                throw new SkeletonSyntaxException("Unknown numbering system", stringSegment);
            }
            macroProps.symbols = numberingSystem;
        }

        private static void generateNumberingSystemOption(NumberingSystem numberingSystem, StringBuilder stringBuilder) {
            stringBuilder.append(numberingSystem.getName());
        }

        private static void parseScaleOption(StringSegment stringSegment, MacroProps macroProps) {
            BigDecimal bigDecimal;
            String string = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {
                bigDecimal = new BigDecimal(string);
            } catch (NumberFormatException numberFormatException) {
                throw new SkeletonSyntaxException("Invalid scale", stringSegment, numberFormatException);
            }
            macroProps.scale = Scale.byBigDecimal(bigDecimal);
        }

        private static void generateScaleOption(Scale scale, StringBuilder stringBuilder) {
            BigDecimal bigDecimal = scale.arbitrary;
            if (bigDecimal == null) {
                bigDecimal = BigDecimal.ONE;
            }
            bigDecimal = bigDecimal.scaleByPowerOfTen(scale.magnitude);
            stringBuilder.append(bigDecimal.toPlainString());
        }

        static void access$000(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseFractionStem(stringSegment, macroProps);
        }

        static void access$100(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseDigitsStem(stringSegment, macroProps);
        }

        static void access$1000(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseCurrencyOption(stringSegment, macroProps);
        }

        static void access$1100(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseMeasureUnitOption(stringSegment, macroProps);
        }

        static void access$1200(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseMeasurePerUnitOption(stringSegment, macroProps);
        }

        static void access$1300(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseIncrementOption(stringSegment, macroProps);
        }

        static void access$1400(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseIntegerWidthOption(stringSegment, macroProps);
        }

        static void access$1500(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseNumberingSystemOption(stringSegment, macroProps);
        }

        static void access$1600(StringSegment stringSegment, MacroProps macroProps) {
            BlueprintHelpers.parseScaleOption(stringSegment, macroProps);
        }

        static boolean access$1700(StringSegment stringSegment, MacroProps macroProps) {
            return BlueprintHelpers.parseExponentWidthOption(stringSegment, macroProps);
        }

        static boolean access$1800(StringSegment stringSegment, MacroProps macroProps) {
            return BlueprintHelpers.parseExponentSignOption(stringSegment, macroProps);
        }

        static boolean access$1900(StringSegment stringSegment, MacroProps macroProps) {
            return BlueprintHelpers.parseFracSigOption(stringSegment, macroProps);
        }

        static void access$3300(int n, StringBuilder stringBuilder) {
            BlueprintHelpers.generateExponentWidthOption(n, stringBuilder);
        }

        static void access$3500(Currency currency, StringBuilder stringBuilder) {
            BlueprintHelpers.generateCurrencyOption(currency, stringBuilder);
        }

        static void access$3600(MeasureUnit measureUnit, StringBuilder stringBuilder) {
            BlueprintHelpers.generateMeasureUnitOption(measureUnit, stringBuilder);
        }

        static void access$3700(int n, int n2, StringBuilder stringBuilder) {
            BlueprintHelpers.generateFractionStem(n, n2, stringBuilder);
        }

        static void access$3800(int n, int n2, StringBuilder stringBuilder) {
            BlueprintHelpers.generateDigitsStem(n, n2, stringBuilder);
        }

        static void access$3900(BigDecimal bigDecimal, StringBuilder stringBuilder) {
            BlueprintHelpers.generateIncrementOption(bigDecimal, stringBuilder);
        }

        static void access$4200(int n, int n2, StringBuilder stringBuilder) {
            BlueprintHelpers.generateIntegerWidthOption(n, n2, stringBuilder);
        }

        static void access$4300(NumberingSystem numberingSystem, StringBuilder stringBuilder) {
            BlueprintHelpers.generateNumberingSystemOption(numberingSystem, stringBuilder);
        }

        static void access$4600(Scale scale, StringBuilder stringBuilder) {
            BlueprintHelpers.generateScaleOption(scale, stringBuilder);
        }
    }

    static final class EnumToStemString {
        EnumToStemString() {
        }

        private static void roundingMode(RoundingMode roundingMode, StringBuilder stringBuilder) {
            switch (roundingMode) {
                case CEILING: {
                    stringBuilder.append("rounding-mode-ceiling");
                    break;
                }
                case FLOOR: {
                    stringBuilder.append("rounding-mode-floor");
                    break;
                }
                case DOWN: {
                    stringBuilder.append("rounding-mode-down");
                    break;
                }
                case UP: {
                    stringBuilder.append("rounding-mode-up");
                    break;
                }
                case HALF_EVEN: {
                    stringBuilder.append("rounding-mode-half-even");
                    break;
                }
                case HALF_DOWN: {
                    stringBuilder.append("rounding-mode-half-down");
                    break;
                }
                case HALF_UP: {
                    stringBuilder.append("rounding-mode-half-up");
                    break;
                }
                case UNNECESSARY: {
                    stringBuilder.append("rounding-mode-unnecessary");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        private static void groupingStrategy(NumberFormatter.GroupingStrategy groupingStrategy, StringBuilder stringBuilder) {
            switch (groupingStrategy) {
                case OFF: {
                    stringBuilder.append("group-off");
                    break;
                }
                case MIN2: {
                    stringBuilder.append("group-min2");
                    break;
                }
                case AUTO: {
                    stringBuilder.append("group-auto");
                    break;
                }
                case ON_ALIGNED: {
                    stringBuilder.append("group-on-aligned");
                    break;
                }
                case THOUSANDS: {
                    stringBuilder.append("group-thousands");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        private static void unitWidth(NumberFormatter.UnitWidth unitWidth, StringBuilder stringBuilder) {
            switch (unitWidth) {
                case NARROW: {
                    stringBuilder.append("unit-width-narrow");
                    break;
                }
                case SHORT: {
                    stringBuilder.append("unit-width-short");
                    break;
                }
                case FULL_NAME: {
                    stringBuilder.append("unit-width-full-name");
                    break;
                }
                case ISO_CODE: {
                    stringBuilder.append("unit-width-iso-code");
                    break;
                }
                case HIDDEN: {
                    stringBuilder.append("unit-width-hidden");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        private static void signDisplay(NumberFormatter.SignDisplay signDisplay, StringBuilder stringBuilder) {
            switch (signDisplay) {
                case AUTO: {
                    stringBuilder.append("sign-auto");
                    break;
                }
                case ALWAYS: {
                    stringBuilder.append("sign-always");
                    break;
                }
                case NEVER: {
                    stringBuilder.append("sign-never");
                    break;
                }
                case ACCOUNTING: {
                    stringBuilder.append("sign-accounting");
                    break;
                }
                case ACCOUNTING_ALWAYS: {
                    stringBuilder.append("sign-accounting-always");
                    break;
                }
                case EXCEPT_ZERO: {
                    stringBuilder.append("sign-except-zero");
                    break;
                }
                case ACCOUNTING_EXCEPT_ZERO: {
                    stringBuilder.append("sign-accounting-except-zero");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        private static void decimalSeparatorDisplay(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay, StringBuilder stringBuilder) {
            switch (decimalSeparatorDisplay) {
                case AUTO: {
                    stringBuilder.append("decimal-auto");
                    break;
                }
                case ALWAYS: {
                    stringBuilder.append("decimal-always");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        static void access$3400(NumberFormatter.SignDisplay signDisplay, StringBuilder stringBuilder) {
            EnumToStemString.signDisplay(signDisplay, stringBuilder);
        }

        static void access$4000(RoundingMode roundingMode, StringBuilder stringBuilder) {
            EnumToStemString.roundingMode(roundingMode, stringBuilder);
        }

        static void access$4100(NumberFormatter.GroupingStrategy groupingStrategy, StringBuilder stringBuilder) {
            EnumToStemString.groupingStrategy(groupingStrategy, stringBuilder);
        }

        static void access$4400(NumberFormatter.UnitWidth unitWidth, StringBuilder stringBuilder) {
            EnumToStemString.unitWidth(unitWidth, stringBuilder);
        }

        static void access$4500(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay, StringBuilder stringBuilder) {
            EnumToStemString.decimalSeparatorDisplay(decimalSeparatorDisplay, stringBuilder);
        }
    }

    static final class StemToObject {
        StemToObject() {
        }

        private static Notation notation(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_COMPACT_SHORT: {
                    return Notation.compactShort();
                }
                case STEM_COMPACT_LONG: {
                    return Notation.compactLong();
                }
                case STEM_SCIENTIFIC: {
                    return Notation.scientific();
                }
                case STEM_ENGINEERING: {
                    return Notation.engineering();
                }
                case STEM_NOTATION_SIMPLE: {
                    return Notation.simple();
                }
            }
            throw new AssertionError();
        }

        private static MeasureUnit unit(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_BASE_UNIT: {
                    return NoUnit.BASE;
                }
                case STEM_PERCENT: {
                    return NoUnit.PERCENT;
                }
                case STEM_PERMILLE: {
                    return NoUnit.PERMILLE;
                }
            }
            throw new AssertionError();
        }

        private static Precision precision(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_PRECISION_INTEGER: {
                    return Precision.integer();
                }
                case STEM_PRECISION_UNLIMITED: {
                    return Precision.unlimited();
                }
                case STEM_PRECISION_CURRENCY_STANDARD: {
                    return Precision.currency(Currency.CurrencyUsage.STANDARD);
                }
                case STEM_PRECISION_CURRENCY_CASH: {
                    return Precision.currency(Currency.CurrencyUsage.CASH);
                }
            }
            throw new AssertionError();
        }

        private static RoundingMode roundingMode(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_ROUNDING_MODE_CEILING: {
                    return RoundingMode.CEILING;
                }
                case STEM_ROUNDING_MODE_FLOOR: {
                    return RoundingMode.FLOOR;
                }
                case STEM_ROUNDING_MODE_DOWN: {
                    return RoundingMode.DOWN;
                }
                case STEM_ROUNDING_MODE_UP: {
                    return RoundingMode.UP;
                }
                case STEM_ROUNDING_MODE_HALF_EVEN: {
                    return RoundingMode.HALF_EVEN;
                }
                case STEM_ROUNDING_MODE_HALF_DOWN: {
                    return RoundingMode.HALF_DOWN;
                }
                case STEM_ROUNDING_MODE_HALF_UP: {
                    return RoundingMode.HALF_UP;
                }
                case STEM_ROUNDING_MODE_UNNECESSARY: {
                    return RoundingMode.UNNECESSARY;
                }
            }
            throw new AssertionError();
        }

        private static NumberFormatter.GroupingStrategy groupingStrategy(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_GROUP_OFF: {
                    return NumberFormatter.GroupingStrategy.OFF;
                }
                case STEM_GROUP_MIN2: {
                    return NumberFormatter.GroupingStrategy.MIN2;
                }
                case STEM_GROUP_AUTO: {
                    return NumberFormatter.GroupingStrategy.AUTO;
                }
                case STEM_GROUP_ON_ALIGNED: {
                    return NumberFormatter.GroupingStrategy.ON_ALIGNED;
                }
                case STEM_GROUP_THOUSANDS: {
                    return NumberFormatter.GroupingStrategy.THOUSANDS;
                }
            }
            return null;
        }

        private static NumberFormatter.UnitWidth unitWidth(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_UNIT_WIDTH_NARROW: {
                    return NumberFormatter.UnitWidth.NARROW;
                }
                case STEM_UNIT_WIDTH_SHORT: {
                    return NumberFormatter.UnitWidth.SHORT;
                }
                case STEM_UNIT_WIDTH_FULL_NAME: {
                    return NumberFormatter.UnitWidth.FULL_NAME;
                }
                case STEM_UNIT_WIDTH_ISO_CODE: {
                    return NumberFormatter.UnitWidth.ISO_CODE;
                }
                case STEM_UNIT_WIDTH_HIDDEN: {
                    return NumberFormatter.UnitWidth.HIDDEN;
                }
            }
            return null;
        }

        private static NumberFormatter.SignDisplay signDisplay(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_SIGN_AUTO: {
                    return NumberFormatter.SignDisplay.AUTO;
                }
                case STEM_SIGN_ALWAYS: {
                    return NumberFormatter.SignDisplay.ALWAYS;
                }
                case STEM_SIGN_NEVER: {
                    return NumberFormatter.SignDisplay.NEVER;
                }
                case STEM_SIGN_ACCOUNTING: {
                    return NumberFormatter.SignDisplay.ACCOUNTING;
                }
                case STEM_SIGN_ACCOUNTING_ALWAYS: {
                    return NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS;
                }
                case STEM_SIGN_EXCEPT_ZERO: {
                    return NumberFormatter.SignDisplay.EXCEPT_ZERO;
                }
                case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO: {
                    return NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
                }
            }
            return null;
        }

        private static NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay(StemEnum stemEnum) {
            switch (stemEnum) {
                case STEM_DECIMAL_AUTO: {
                    return NumberFormatter.DecimalSeparatorDisplay.AUTO;
                }
                case STEM_DECIMAL_ALWAYS: {
                    return NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
                }
            }
            return null;
        }

        static Notation access$200(StemEnum stemEnum) {
            return StemToObject.notation(stemEnum);
        }

        static MeasureUnit access$300(StemEnum stemEnum) {
            return StemToObject.unit(stemEnum);
        }

        static Precision access$400(StemEnum stemEnum) {
            return StemToObject.precision(stemEnum);
        }

        static RoundingMode access$500(StemEnum stemEnum) {
            return StemToObject.roundingMode(stemEnum);
        }

        static NumberFormatter.GroupingStrategy access$600(StemEnum stemEnum) {
            return StemToObject.groupingStrategy(stemEnum);
        }

        static NumberFormatter.UnitWidth access$700(StemEnum stemEnum) {
            return StemToObject.unitWidth(stemEnum);
        }

        static NumberFormatter.SignDisplay access$800(StemEnum stemEnum) {
            return StemToObject.signDisplay(stemEnum);
        }

        static NumberFormatter.DecimalSeparatorDisplay access$900(StemEnum stemEnum) {
            return StemToObject.decimalSeparatorDisplay(stemEnum);
        }
    }

    static enum StemEnum {
        STEM_COMPACT_SHORT,
        STEM_COMPACT_LONG,
        STEM_SCIENTIFIC,
        STEM_ENGINEERING,
        STEM_NOTATION_SIMPLE,
        STEM_BASE_UNIT,
        STEM_PERCENT,
        STEM_PERMILLE,
        STEM_PRECISION_INTEGER,
        STEM_PRECISION_UNLIMITED,
        STEM_PRECISION_CURRENCY_STANDARD,
        STEM_PRECISION_CURRENCY_CASH,
        STEM_ROUNDING_MODE_CEILING,
        STEM_ROUNDING_MODE_FLOOR,
        STEM_ROUNDING_MODE_DOWN,
        STEM_ROUNDING_MODE_UP,
        STEM_ROUNDING_MODE_HALF_EVEN,
        STEM_ROUNDING_MODE_HALF_DOWN,
        STEM_ROUNDING_MODE_HALF_UP,
        STEM_ROUNDING_MODE_UNNECESSARY,
        STEM_GROUP_OFF,
        STEM_GROUP_MIN2,
        STEM_GROUP_AUTO,
        STEM_GROUP_ON_ALIGNED,
        STEM_GROUP_THOUSANDS,
        STEM_LATIN,
        STEM_UNIT_WIDTH_NARROW,
        STEM_UNIT_WIDTH_SHORT,
        STEM_UNIT_WIDTH_FULL_NAME,
        STEM_UNIT_WIDTH_ISO_CODE,
        STEM_UNIT_WIDTH_HIDDEN,
        STEM_SIGN_AUTO,
        STEM_SIGN_ALWAYS,
        STEM_SIGN_NEVER,
        STEM_SIGN_ACCOUNTING,
        STEM_SIGN_ACCOUNTING_ALWAYS,
        STEM_SIGN_EXCEPT_ZERO,
        STEM_SIGN_ACCOUNTING_EXCEPT_ZERO,
        STEM_DECIMAL_AUTO,
        STEM_DECIMAL_ALWAYS,
        STEM_PRECISION_INCREMENT,
        STEM_MEASURE_UNIT,
        STEM_PER_MEASURE_UNIT,
        STEM_CURRENCY,
        STEM_INTEGER_WIDTH,
        STEM_NUMBERING_SYSTEM,
        STEM_SCALE;

    }

    static enum ParseState {
        STATE_NULL,
        STATE_SCIENTIFIC,
        STATE_FRACTION_PRECISION,
        STATE_INCREMENT_PRECISION,
        STATE_MEASURE_UNIT,
        STATE_PER_MEASURE_UNIT,
        STATE_CURRENCY_UNIT,
        STATE_INTEGER_WIDTH,
        STATE_NUMBERING_SYSTEM,
        STATE_SCALE;

    }
}

