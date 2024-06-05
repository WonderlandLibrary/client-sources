package com.ibm.icu.number;

import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import java.util.*;
import java.math.*;
import com.ibm.icu.impl.number.*;
import com.ibm.icu.text.*;

class NumberSkeletonImpl
{
    static final StemEnum[] STEM_ENUM_VALUES;
    static final String SERIALIZED_STEM_TRIE;
    private static final CacheBase<String, UnlocalizedNumberFormatter, Void> cache;
    
    static String buildStemTrie() {
        final CharsTrieBuilder b = new CharsTrieBuilder();
        b.add("compact-short", StemEnum.STEM_COMPACT_SHORT.ordinal());
        b.add("compact-long", StemEnum.STEM_COMPACT_LONG.ordinal());
        b.add("scientific", StemEnum.STEM_SCIENTIFIC.ordinal());
        b.add("engineering", StemEnum.STEM_ENGINEERING.ordinal());
        b.add("notation-simple", StemEnum.STEM_NOTATION_SIMPLE.ordinal());
        b.add("base-unit", StemEnum.STEM_BASE_UNIT.ordinal());
        b.add("percent", StemEnum.STEM_PERCENT.ordinal());
        b.add("permille", StemEnum.STEM_PERMILLE.ordinal());
        b.add("precision-integer", StemEnum.STEM_PRECISION_INTEGER.ordinal());
        b.add("precision-unlimited", StemEnum.STEM_PRECISION_UNLIMITED.ordinal());
        b.add("precision-currency-standard", StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal());
        b.add("precision-currency-cash", StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal());
        b.add("rounding-mode-ceiling", StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal());
        b.add("rounding-mode-floor", StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal());
        b.add("rounding-mode-down", StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal());
        b.add("rounding-mode-up", StemEnum.STEM_ROUNDING_MODE_UP.ordinal());
        b.add("rounding-mode-half-even", StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal());
        b.add("rounding-mode-half-down", StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal());
        b.add("rounding-mode-half-up", StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal());
        b.add("rounding-mode-unnecessary", StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal());
        b.add("group-off", StemEnum.STEM_GROUP_OFF.ordinal());
        b.add("group-min2", StemEnum.STEM_GROUP_MIN2.ordinal());
        b.add("group-auto", StemEnum.STEM_GROUP_AUTO.ordinal());
        b.add("group-on-aligned", StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
        b.add("group-thousands", StemEnum.STEM_GROUP_THOUSANDS.ordinal());
        b.add("latin", StemEnum.STEM_LATIN.ordinal());
        b.add("unit-width-narrow", StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal());
        b.add("unit-width-short", StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal());
        b.add("unit-width-full-name", StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal());
        b.add("unit-width-iso-code", StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal());
        b.add("unit-width-hidden", StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal());
        b.add("sign-auto", StemEnum.STEM_SIGN_AUTO.ordinal());
        b.add("sign-always", StemEnum.STEM_SIGN_ALWAYS.ordinal());
        b.add("sign-never", StemEnum.STEM_SIGN_NEVER.ordinal());
        b.add("sign-accounting", StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
        b.add("sign-accounting-always", StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
        b.add("sign-except-zero", StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
        b.add("sign-accounting-except-zero", StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
        b.add("decimal-auto", StemEnum.STEM_DECIMAL_AUTO.ordinal());
        b.add("decimal-always", StemEnum.STEM_DECIMAL_ALWAYS.ordinal());
        b.add("precision-increment", StemEnum.STEM_PRECISION_INCREMENT.ordinal());
        b.add("measure-unit", StemEnum.STEM_MEASURE_UNIT.ordinal());
        b.add("per-measure-unit", StemEnum.STEM_PER_MEASURE_UNIT.ordinal());
        b.add("currency", StemEnum.STEM_CURRENCY.ordinal());
        b.add("integer-width", StemEnum.STEM_INTEGER_WIDTH.ordinal());
        b.add("numbering-system", StemEnum.STEM_NUMBERING_SYSTEM.ordinal());
        b.add("scale", StemEnum.STEM_SCALE.ordinal());
        return b.buildCharSequence(StringTrieBuilder.Option.FAST).toString();
    }
    
    public static UnlocalizedNumberFormatter getOrCreate(final String skeletonString) {
        return NumberSkeletonImpl.cache.getInstance(skeletonString, null);
    }
    
    public static UnlocalizedNumberFormatter create(final String skeletonString) {
        final MacroProps macros = parseSkeleton(skeletonString);
        return NumberFormatter.with().macros(macros);
    }
    
    public static String generate(final MacroProps macros) {
        final StringBuilder sb = new StringBuilder();
        generateSkeleton(macros, sb);
        return sb.toString();
    }
    
    private static MacroProps parseSkeleton(String skeletonString) {
        skeletonString += " ";
        final MacroProps macros = new MacroProps();
        final StringSegment segment = new StringSegment(skeletonString, false);
        final CharsTrie stemTrie = new CharsTrie(NumberSkeletonImpl.SERIALIZED_STEM_TRIE, 0);
        ParseState stem = ParseState.STATE_NULL;
        int offset = 0;
        while (offset < segment.length()) {
            final int cp = segment.codePointAt(offset);
            final boolean isTokenSeparator = PatternProps.isWhiteSpace(cp);
            final boolean isOptionSeparator = cp == 47;
            if (!isTokenSeparator && !isOptionSeparator) {
                offset += Character.charCount(cp);
                if (stem != ParseState.STATE_NULL) {
                    continue;
                }
                stemTrie.nextForCodePoint(cp);
            }
            else {
                if (offset != 0) {
                    segment.setLength(offset);
                    if (stem == ParseState.STATE_NULL) {
                        stem = parseStem(segment, stemTrie, macros);
                        stemTrie.reset();
                    }
                    else {
                        stem = parseOption(stem, segment, macros);
                    }
                    segment.resetLength();
                    segment.adjustOffset(offset);
                    offset = 0;
                }
                else if (stem != ParseState.STATE_NULL) {
                    segment.setLength(Character.charCount(cp));
                    throw new SkeletonSyntaxException("Unexpected separator character", segment);
                }
                if (isOptionSeparator && stem == ParseState.STATE_NULL) {
                    segment.setLength(Character.charCount(cp));
                    throw new SkeletonSyntaxException("Unexpected option separator", segment);
                }
                if (isTokenSeparator && stem != ParseState.STATE_NULL) {
                    switch (stem) {
                        case STATE_INCREMENT_PRECISION:
                        case STATE_MEASURE_UNIT:
                        case STATE_PER_MEASURE_UNIT:
                        case STATE_CURRENCY_UNIT:
                        case STATE_INTEGER_WIDTH:
                        case STATE_NUMBERING_SYSTEM:
                        case STATE_SCALE: {
                            segment.setLength(Character.charCount(cp));
                            throw new SkeletonSyntaxException("Stem requires an option", segment);
                        }
                        default: {
                            stem = ParseState.STATE_NULL;
                            break;
                        }
                    }
                }
                segment.adjustOffset(Character.charCount(cp));
            }
        }
        assert stem == ParseState.STATE_NULL;
        return macros;
    }
    
    private static ParseState parseStem(final StringSegment segment, final CharsTrie stemTrie, final MacroProps macros) {
        switch (segment.charAt(0)) {
            case '.': {
                checkNull(macros.precision, segment);
                parseFractionStem(segment, macros);
                return ParseState.STATE_FRACTION_PRECISION;
            }
            case '@': {
                checkNull(macros.precision, segment);
                parseDigitsStem(segment, macros);
                return ParseState.STATE_NULL;
            }
            default: {
                final BytesTrie.Result stemResult = stemTrie.current();
                if (stemResult != BytesTrie.Result.INTERMEDIATE_VALUE && stemResult != BytesTrie.Result.FINAL_VALUE) {
                    throw new SkeletonSyntaxException("Unknown stem", segment);
                }
                final StemEnum stem = NumberSkeletonImpl.STEM_ENUM_VALUES[stemTrie.getValue()];
                switch (stem) {
                    case STEM_COMPACT_SHORT:
                    case STEM_COMPACT_LONG:
                    case STEM_SCIENTIFIC:
                    case STEM_ENGINEERING:
                    case STEM_NOTATION_SIMPLE: {
                        checkNull(macros.notation, segment);
                        macros.notation = notation(stem);
                        switch (stem) {
                            case STEM_SCIENTIFIC:
                            case STEM_ENGINEERING: {
                                return ParseState.STATE_SCIENTIFIC;
                            }
                            default: {
                                return ParseState.STATE_NULL;
                            }
                        }
                        break;
                    }
                    case STEM_BASE_UNIT:
                    case STEM_PERCENT:
                    case STEM_PERMILLE: {
                        checkNull(macros.unit, segment);
                        macros.unit = unit(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_PRECISION_INTEGER:
                    case STEM_PRECISION_UNLIMITED:
                    case STEM_PRECISION_CURRENCY_STANDARD:
                    case STEM_PRECISION_CURRENCY_CASH: {
                        checkNull(macros.precision, segment);
                        macros.precision = precision(stem);
                        switch (stem) {
                            case STEM_PRECISION_INTEGER: {
                                return ParseState.STATE_FRACTION_PRECISION;
                            }
                            default: {
                                return ParseState.STATE_NULL;
                            }
                        }
                        break;
                    }
                    case STEM_ROUNDING_MODE_CEILING:
                    case STEM_ROUNDING_MODE_FLOOR:
                    case STEM_ROUNDING_MODE_DOWN:
                    case STEM_ROUNDING_MODE_UP:
                    case STEM_ROUNDING_MODE_HALF_EVEN:
                    case STEM_ROUNDING_MODE_HALF_DOWN:
                    case STEM_ROUNDING_MODE_HALF_UP:
                    case STEM_ROUNDING_MODE_UNNECESSARY: {
                        checkNull(macros.roundingMode, segment);
                        macros.roundingMode = roundingMode(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_GROUP_OFF:
                    case STEM_GROUP_MIN2:
                    case STEM_GROUP_AUTO:
                    case STEM_GROUP_ON_ALIGNED:
                    case STEM_GROUP_THOUSANDS: {
                        checkNull(macros.grouping, segment);
                        macros.grouping = groupingStrategy(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_LATIN: {
                        checkNull(macros.symbols, segment);
                        macros.symbols = NumberingSystem.LATIN;
                        return ParseState.STATE_NULL;
                    }
                    case STEM_UNIT_WIDTH_NARROW:
                    case STEM_UNIT_WIDTH_SHORT:
                    case STEM_UNIT_WIDTH_FULL_NAME:
                    case STEM_UNIT_WIDTH_ISO_CODE:
                    case STEM_UNIT_WIDTH_HIDDEN: {
                        checkNull(macros.unitWidth, segment);
                        macros.unitWidth = unitWidth(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_SIGN_AUTO:
                    case STEM_SIGN_ALWAYS:
                    case STEM_SIGN_NEVER:
                    case STEM_SIGN_ACCOUNTING:
                    case STEM_SIGN_ACCOUNTING_ALWAYS:
                    case STEM_SIGN_EXCEPT_ZERO:
                    case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO: {
                        checkNull(macros.sign, segment);
                        macros.sign = signDisplay(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_DECIMAL_AUTO:
                    case STEM_DECIMAL_ALWAYS: {
                        checkNull(macros.decimal, segment);
                        macros.decimal = decimalSeparatorDisplay(stem);
                        return ParseState.STATE_NULL;
                    }
                    case STEM_PRECISION_INCREMENT: {
                        checkNull(macros.precision, segment);
                        return ParseState.STATE_INCREMENT_PRECISION;
                    }
                    case STEM_MEASURE_UNIT: {
                        checkNull(macros.unit, segment);
                        return ParseState.STATE_MEASURE_UNIT;
                    }
                    case STEM_PER_MEASURE_UNIT: {
                        checkNull(macros.perUnit, segment);
                        return ParseState.STATE_PER_MEASURE_UNIT;
                    }
                    case STEM_CURRENCY: {
                        checkNull(macros.unit, segment);
                        return ParseState.STATE_CURRENCY_UNIT;
                    }
                    case STEM_INTEGER_WIDTH: {
                        checkNull(macros.integerWidth, segment);
                        return ParseState.STATE_INTEGER_WIDTH;
                    }
                    case STEM_NUMBERING_SYSTEM: {
                        checkNull(macros.symbols, segment);
                        return ParseState.STATE_NUMBERING_SYSTEM;
                    }
                    case STEM_SCALE: {
                        checkNull(macros.scale, segment);
                        return ParseState.STATE_SCALE;
                    }
                    default: {
                        throw new AssertionError();
                    }
                }
                break;
            }
        }
    }
    
    private static ParseState parseOption(final ParseState stem, final StringSegment segment, final MacroProps macros) {
        switch (stem) {
            case STATE_CURRENCY_UNIT: {
                parseCurrencyOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_MEASURE_UNIT: {
                parseMeasureUnitOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_PER_MEASURE_UNIT: {
                parseMeasurePerUnitOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_INCREMENT_PRECISION: {
                parseIncrementOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_INTEGER_WIDTH: {
                parseIntegerWidthOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_NUMBERING_SYSTEM: {
                parseNumberingSystemOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            case STATE_SCALE: {
                parseScaleOption(segment, macros);
                return ParseState.STATE_NULL;
            }
            default: {
                switch (stem) {
                    case STATE_SCIENTIFIC: {
                        if (parseExponentWidthOption(segment, macros)) {
                            return ParseState.STATE_SCIENTIFIC;
                        }
                        if (parseExponentSignOption(segment, macros)) {
                            return ParseState.STATE_SCIENTIFIC;
                        }
                        break;
                    }
                }
                switch (stem) {
                    case STATE_FRACTION_PRECISION: {
                        if (parseFracSigOption(segment, macros)) {
                            return ParseState.STATE_NULL;
                        }
                        break;
                    }
                }
                throw new SkeletonSyntaxException("Invalid option", segment);
            }
        }
    }
    
    private static void generateSkeleton(final MacroProps macros, final StringBuilder sb) {
        if (macros.notation != null && notation(macros, sb)) {
            sb.append(' ');
        }
        if (macros.unit != null && unit(macros, sb)) {
            sb.append(' ');
        }
        if (macros.perUnit != null && perUnit(macros, sb)) {
            sb.append(' ');
        }
        if (macros.precision != null && precision(macros, sb)) {
            sb.append(' ');
        }
        if (macros.roundingMode != null && roundingMode(macros, sb)) {
            sb.append(' ');
        }
        if (macros.grouping != null && grouping(macros, sb)) {
            sb.append(' ');
        }
        if (macros.integerWidth != null && integerWidth(macros, sb)) {
            sb.append(' ');
        }
        if (macros.symbols != null && symbols(macros, sb)) {
            sb.append(' ');
        }
        if (macros.unitWidth != null && unitWidth(macros, sb)) {
            sb.append(' ');
        }
        if (macros.sign != null && sign(macros, sb)) {
            sb.append(' ');
        }
        if (macros.decimal != null && decimal(macros, sb)) {
            sb.append(' ');
        }
        if (macros.scale != null && scale(macros, sb)) {
            sb.append(' ');
        }
        if (macros.padder != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom padder");
        }
        if (macros.affixProvider != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom affix provider");
        }
        if (macros.rules != null) {
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom plural rules");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }
    
    private static void checkNull(final Object value, final CharSequence content) {
        if (value != null) {
            throw new SkeletonSyntaxException("Duplicated setting", content);
        }
    }
    
    private static void appendMultiple(final StringBuilder sb, final int cp, final int count) {
        for (int i = 0; i < count; ++i) {
            sb.appendCodePoint(cp);
        }
    }
    
    static {
        STEM_ENUM_VALUES = StemEnum.values();
        SERIALIZED_STEM_TRIE = buildStemTrie();
        cache = new SoftCache<String, UnlocalizedNumberFormatter, Void>() {
            @Override
            protected UnlocalizedNumberFormatter createInstance(final String skeletonString, final Void unused) {
                return NumberSkeletonImpl.create(skeletonString);
            }
        };
    }
    
    enum ParseState
    {
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
    
    enum StemEnum
    {
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
    
    static final class StemToObject
    {
        private static Notation notation(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static MeasureUnit unit(final StemEnum stem) {
            switch (stem) {
                case STEM_BASE_UNIT: {
                    return NoUnit.BASE;
                }
                case STEM_PERCENT: {
                    return NoUnit.PERCENT;
                }
                case STEM_PERMILLE: {
                    return NoUnit.PERMILLE;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static Precision precision(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static RoundingMode roundingMode(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static NumberFormatter.GroupingStrategy groupingStrategy(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    return null;
                }
            }
        }
        
        private static NumberFormatter.UnitWidth unitWidth(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    return null;
                }
            }
        }
        
        private static NumberFormatter.SignDisplay signDisplay(final StemEnum stem) {
            switch (stem) {
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
                default: {
                    return null;
                }
            }
        }
        
        private static NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay(final StemEnum stem) {
            switch (stem) {
                case STEM_DECIMAL_AUTO: {
                    return NumberFormatter.DecimalSeparatorDisplay.AUTO;
                }
                case STEM_DECIMAL_ALWAYS: {
                    return NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
                }
                default: {
                    return null;
                }
            }
        }
    }
    
    static final class EnumToStemString
    {
        private static void roundingMode(final RoundingMode value, final StringBuilder sb) {
            switch (value) {
                case CEILING: {
                    sb.append("rounding-mode-ceiling");
                    break;
                }
                case FLOOR: {
                    sb.append("rounding-mode-floor");
                    break;
                }
                case DOWN: {
                    sb.append("rounding-mode-down");
                    break;
                }
                case UP: {
                    sb.append("rounding-mode-up");
                    break;
                }
                case HALF_EVEN: {
                    sb.append("rounding-mode-half-even");
                    break;
                }
                case HALF_DOWN: {
                    sb.append("rounding-mode-half-down");
                    break;
                }
                case HALF_UP: {
                    sb.append("rounding-mode-half-up");
                    break;
                }
                case UNNECESSARY: {
                    sb.append("rounding-mode-unnecessary");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static void groupingStrategy(final NumberFormatter.GroupingStrategy value, final StringBuilder sb) {
            switch (value) {
                case OFF: {
                    sb.append("group-off");
                    break;
                }
                case MIN2: {
                    sb.append("group-min2");
                    break;
                }
                case AUTO: {
                    sb.append("group-auto");
                    break;
                }
                case ON_ALIGNED: {
                    sb.append("group-on-aligned");
                    break;
                }
                case THOUSANDS: {
                    sb.append("group-thousands");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static void unitWidth(final NumberFormatter.UnitWidth value, final StringBuilder sb) {
            switch (value) {
                case NARROW: {
                    sb.append("unit-width-narrow");
                    break;
                }
                case SHORT: {
                    sb.append("unit-width-short");
                    break;
                }
                case FULL_NAME: {
                    sb.append("unit-width-full-name");
                    break;
                }
                case ISO_CODE: {
                    sb.append("unit-width-iso-code");
                    break;
                }
                case HIDDEN: {
                    sb.append("unit-width-hidden");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static void signDisplay(final NumberFormatter.SignDisplay value, final StringBuilder sb) {
            switch (value) {
                case AUTO: {
                    sb.append("sign-auto");
                    break;
                }
                case ALWAYS: {
                    sb.append("sign-always");
                    break;
                }
                case NEVER: {
                    sb.append("sign-never");
                    break;
                }
                case ACCOUNTING: {
                    sb.append("sign-accounting");
                    break;
                }
                case ACCOUNTING_ALWAYS: {
                    sb.append("sign-accounting-always");
                    break;
                }
                case EXCEPT_ZERO: {
                    sb.append("sign-except-zero");
                    break;
                }
                case ACCOUNTING_EXCEPT_ZERO: {
                    sb.append("sign-accounting-except-zero");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static void decimalSeparatorDisplay(final NumberFormatter.DecimalSeparatorDisplay value, final StringBuilder sb) {
            switch (value) {
                case AUTO: {
                    sb.append("decimal-auto");
                    break;
                }
                case ALWAYS: {
                    sb.append("decimal-always");
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
    }
    
    static final class BlueprintHelpers
    {
        private static boolean parseExponentWidthOption(final StringSegment segment, final MacroProps macros) {
            if (segment.charAt(0) != '+') {
                return false;
            }
            int offset = 1;
            int minExp = 0;
            while (offset < segment.length() && segment.charAt(offset) == 'e') {
                ++minExp;
                ++offset;
            }
            if (offset < segment.length()) {
                return false;
            }
            macros.notation = ((ScientificNotation)macros.notation).withMinExponentDigits(minExp);
            return true;
        }
        
        private static void generateExponentWidthOption(final int minExponentDigits, final StringBuilder sb) {
            sb.append('+');
            appendMultiple(sb, 101, minExponentDigits);
        }
        
        private static boolean parseExponentSignOption(final StringSegment segment, final MacroProps macros) {
            final CharsTrie tempStemTrie = new CharsTrie(NumberSkeletonImpl.SERIALIZED_STEM_TRIE, 0);
            final BytesTrie.Result result = tempStemTrie.next(segment, 0, segment.length());
            if (result != BytesTrie.Result.INTERMEDIATE_VALUE && result != BytesTrie.Result.FINAL_VALUE) {
                return false;
            }
            final NumberFormatter.SignDisplay sign = signDisplay(NumberSkeletonImpl.STEM_ENUM_VALUES[tempStemTrie.getValue()]);
            if (sign == null) {
                return false;
            }
            macros.notation = ((ScientificNotation)macros.notation).withExponentSignDisplay(sign);
            return true;
        }
        
        private static void parseCurrencyOption(final StringSegment segment, final MacroProps macros) {
            final String currencyCode = segment.subSequence(0, segment.length()).toString();
            Currency currency;
            try {
                currency = Currency.getInstance(currencyCode);
            }
            catch (IllegalArgumentException e) {
                throw new SkeletonSyntaxException("Invalid currency", segment, e);
            }
            macros.unit = currency;
        }
        
        private static void generateCurrencyOption(final Currency currency, final StringBuilder sb) {
            sb.append(currency.getCurrencyCode());
        }
        
        private static void parseMeasureUnitOption(final StringSegment segment, final MacroProps macros) {
            int firstHyphen;
            for (firstHyphen = 0; firstHyphen < segment.length() && segment.charAt(firstHyphen) != '-'; ++firstHyphen) {}
            if (firstHyphen == segment.length()) {
                throw new SkeletonSyntaxException("Invalid measure unit option", segment);
            }
            final String type = segment.subSequence(0, firstHyphen).toString();
            final String subType = segment.subSequence(firstHyphen + 1, segment.length()).toString();
            final Set<MeasureUnit> units = MeasureUnit.getAvailable(type);
            for (final MeasureUnit unit : units) {
                if (subType.equals(unit.getSubtype())) {
                    macros.unit = unit;
                    return;
                }
            }
            throw new SkeletonSyntaxException("Unknown measure unit", segment);
        }
        
        private static void generateMeasureUnitOption(final MeasureUnit unit, final StringBuilder sb) {
            sb.append(unit.getType());
            sb.append("-");
            sb.append(unit.getSubtype());
        }
        
        private static void parseMeasurePerUnitOption(final StringSegment segment, final MacroProps macros) {
            final MeasureUnit numerator = macros.unit;
            parseMeasureUnitOption(segment, macros);
            macros.perUnit = macros.unit;
            macros.unit = numerator;
        }
        
        private static void parseFractionStem(final StringSegment segment, final MacroProps macros) {
            assert segment.charAt(0) == '.';
            int offset = 1;
            int minFrac = 0;
            while (offset < segment.length() && segment.charAt(offset) == '0') {
                ++minFrac;
                ++offset;
            }
            int maxFrac;
            if (offset < segment.length()) {
                if (segment.charAt(offset) == '+') {
                    maxFrac = -1;
                    ++offset;
                }
                else {
                    maxFrac = minFrac;
                    while (offset < segment.length() && segment.charAt(offset) == '#') {
                        ++maxFrac;
                        ++offset;
                    }
                }
            }
            else {
                maxFrac = minFrac;
            }
            if (offset < segment.length()) {
                throw new SkeletonSyntaxException("Invalid fraction stem", segment);
            }
            if (maxFrac == -1) {
                macros.precision = Precision.minFraction(minFrac);
            }
            else {
                macros.precision = Precision.minMaxFraction(minFrac, maxFrac);
            }
        }
        
        private static void generateFractionStem(final int minFrac, final int maxFrac, final StringBuilder sb) {
            if (minFrac == 0 && maxFrac == 0) {
                sb.append("precision-integer");
                return;
            }
            sb.append('.');
            appendMultiple(sb, 48, minFrac);
            if (maxFrac == -1) {
                sb.append('+');
            }
            else {
                appendMultiple(sb, 35, maxFrac - minFrac);
            }
        }
        
        private static void parseDigitsStem(final StringSegment segment, final MacroProps macros) {
            assert segment.charAt(0) == '@';
            int offset = 0;
            int minSig = 0;
            while (offset < segment.length() && segment.charAt(offset) == '@') {
                ++minSig;
                ++offset;
            }
            int maxSig;
            if (offset < segment.length()) {
                if (segment.charAt(offset) == '+') {
                    maxSig = -1;
                    ++offset;
                }
                else {
                    maxSig = minSig;
                    while (offset < segment.length() && segment.charAt(offset) == '#') {
                        ++maxSig;
                        ++offset;
                    }
                }
            }
            else {
                maxSig = minSig;
            }
            if (offset < segment.length()) {
                throw new SkeletonSyntaxException("Invalid significant digits stem", segment);
            }
            if (maxSig == -1) {
                macros.precision = Precision.minSignificantDigits(minSig);
            }
            else {
                macros.precision = Precision.minMaxSignificantDigits(minSig, maxSig);
            }
        }
        
        private static void generateDigitsStem(final int minSig, final int maxSig, final StringBuilder sb) {
            appendMultiple(sb, 64, minSig);
            if (maxSig == -1) {
                sb.append('+');
            }
            else {
                appendMultiple(sb, 35, maxSig - minSig);
            }
        }
        
        private static boolean parseFracSigOption(final StringSegment segment, final MacroProps macros) {
            if (segment.charAt(0) != '@') {
                return false;
            }
            int offset = 0;
            int minSig = 0;
            while (offset < segment.length() && segment.charAt(offset) == '@') {
                ++minSig;
                ++offset;
            }
            if (offset >= segment.length()) {
                throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", segment);
            }
            int maxSig;
            if (segment.charAt(offset) == '+') {
                maxSig = -1;
                ++offset;
            }
            else {
                if (minSig > 1) {
                    throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", segment);
                }
                maxSig = minSig;
                while (offset < segment.length() && segment.charAt(offset) == '#') {
                    ++maxSig;
                    ++offset;
                }
            }
            if (offset < segment.length()) {
                throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", segment);
            }
            final FractionPrecision oldRounder = (FractionPrecision)macros.precision;
            if (maxSig == -1) {
                macros.precision = oldRounder.withMinDigits(minSig);
            }
            else {
                macros.precision = oldRounder.withMaxDigits(maxSig);
            }
            return true;
        }
        
        private static void parseIncrementOption(final StringSegment segment, final MacroProps macros) {
            final String str = segment.subSequence(0, segment.length()).toString();
            BigDecimal increment;
            try {
                increment = new BigDecimal(str);
            }
            catch (NumberFormatException e) {
                throw new SkeletonSyntaxException("Invalid rounding increment", segment, e);
            }
            macros.precision = Precision.increment(increment);
        }
        
        private static void generateIncrementOption(final BigDecimal increment, final StringBuilder sb) {
            sb.append(increment.toPlainString());
        }
        
        private static void parseIntegerWidthOption(final StringSegment segment, final MacroProps macros) {
            int offset = 0;
            int minInt = 0;
            int maxInt;
            if (segment.charAt(0) == '+') {
                maxInt = -1;
                ++offset;
            }
            else {
                maxInt = 0;
            }
            while (offset < segment.length() && segment.charAt(offset) == '#') {
                ++maxInt;
                ++offset;
            }
            if (offset < segment.length()) {
                while (offset < segment.length() && segment.charAt(offset) == '0') {
                    ++minInt;
                    ++offset;
                }
            }
            if (maxInt != -1) {
                maxInt += minInt;
            }
            if (offset < segment.length()) {
                throw new SkeletonSyntaxException("Invalid integer width stem", segment);
            }
            if (maxInt == -1) {
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt);
            }
            else {
                macros.integerWidth = IntegerWidth.zeroFillTo(minInt).truncateAt(maxInt);
            }
        }
        
        private static void generateIntegerWidthOption(final int minInt, final int maxInt, final StringBuilder sb) {
            if (maxInt == -1) {
                sb.append('+');
            }
            else {
                appendMultiple(sb, 35, maxInt - minInt);
            }
            appendMultiple(sb, 48, minInt);
        }
        
        private static void parseNumberingSystemOption(final StringSegment segment, final MacroProps macros) {
            final String nsName = segment.subSequence(0, segment.length()).toString();
            final NumberingSystem ns = NumberingSystem.getInstanceByName(nsName);
            if (ns == null) {
                throw new SkeletonSyntaxException("Unknown numbering system", segment);
            }
            macros.symbols = ns;
        }
        
        private static void generateNumberingSystemOption(final NumberingSystem ns, final StringBuilder sb) {
            sb.append(ns.getName());
        }
        
        private static void parseScaleOption(final StringSegment segment, final MacroProps macros) {
            final String str = segment.subSequence(0, segment.length()).toString();
            BigDecimal bd;
            try {
                bd = new BigDecimal(str);
            }
            catch (NumberFormatException e) {
                throw new SkeletonSyntaxException("Invalid scale", segment, e);
            }
            macros.scale = Scale.byBigDecimal(bd);
        }
        
        private static void generateScaleOption(final Scale scale, final StringBuilder sb) {
            BigDecimal bd = scale.arbitrary;
            if (bd == null) {
                bd = BigDecimal.ONE;
            }
            bd = bd.scaleByPowerOfTen(scale.magnitude);
            sb.append(bd.toPlainString());
        }
    }
    
    static final class GeneratorHelpers
    {
        private static boolean notation(final MacroProps macros, final StringBuilder sb) {
            if (macros.notation instanceof CompactNotation) {
                if (macros.notation == Notation.compactLong()) {
                    sb.append("compact-long");
                    return true;
                }
                if (macros.notation == Notation.compactShort()) {
                    sb.append("compact-short");
                    return true;
                }
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom compact data");
            }
            else {
                if (macros.notation instanceof ScientificNotation) {
                    final ScientificNotation impl = (ScientificNotation)macros.notation;
                    if (impl.engineeringInterval == 3) {
                        sb.append("engineering");
                    }
                    else {
                        sb.append("scientific");
                    }
                    if (impl.minExponentDigits > 1) {
                        sb.append('/');
                        generateExponentWidthOption(impl.minExponentDigits, sb);
                    }
                    if (impl.exponentSignDisplay != NumberFormatter.SignDisplay.AUTO) {
                        sb.append('/');
                        signDisplay(impl.exponentSignDisplay, sb);
                    }
                    return true;
                }
                assert macros.notation instanceof SimpleNotation;
                return false;
            }
        }
        
        private static boolean unit(final MacroProps macros, final StringBuilder sb) {
            if (macros.unit instanceof Currency) {
                sb.append("currency/");
                generateCurrencyOption((Currency)macros.unit, sb);
                return true;
            }
            if (!(macros.unit instanceof NoUnit)) {
                sb.append("measure-unit/");
                generateMeasureUnitOption(macros.unit, sb);
                return true;
            }
            if (macros.unit == NoUnit.PERCENT) {
                sb.append("percent");
                return true;
            }
            if (macros.unit == NoUnit.PERMILLE) {
                sb.append("permille");
                return true;
            }
            assert macros.unit == NoUnit.BASE;
            return false;
        }
        
        private static boolean perUnit(final MacroProps macros, final StringBuilder sb) {
            if (macros.perUnit instanceof Currency || macros.perUnit instanceof NoUnit) {
                throw new UnsupportedOperationException("Cannot generate number skeleton with per-unit that is not a standard measure unit");
            }
            sb.append("per-measure-unit/");
            generateMeasureUnitOption(macros.perUnit, sb);
            return true;
        }
        
        private static boolean precision(final MacroProps macros, final StringBuilder sb) {
            if (macros.precision instanceof Precision.InfiniteRounderImpl) {
                sb.append("precision-unlimited");
            }
            else if (macros.precision instanceof Precision.FractionRounderImpl) {
                final Precision.FractionRounderImpl impl = (Precision.FractionRounderImpl)macros.precision;
                generateFractionStem(impl.minFrac, impl.maxFrac, sb);
            }
            else if (macros.precision instanceof Precision.SignificantRounderImpl) {
                final Precision.SignificantRounderImpl impl2 = (Precision.SignificantRounderImpl)macros.precision;
                generateDigitsStem(impl2.minSig, impl2.maxSig, sb);
            }
            else if (macros.precision instanceof Precision.FracSigRounderImpl) {
                final Precision.FracSigRounderImpl impl3 = (Precision.FracSigRounderImpl)macros.precision;
                generateFractionStem(impl3.minFrac, impl3.maxFrac, sb);
                sb.append('/');
                if (impl3.minSig == -1) {
                    generateDigitsStem(1, impl3.maxSig, sb);
                }
                else {
                    generateDigitsStem(impl3.minSig, -1, sb);
                }
            }
            else if (macros.precision instanceof Precision.IncrementRounderImpl) {
                final Precision.IncrementRounderImpl impl4 = (Precision.IncrementRounderImpl)macros.precision;
                sb.append("precision-increment/");
                generateIncrementOption(impl4.increment, sb);
            }
            else {
                assert macros.precision instanceof Precision.CurrencyRounderImpl;
                final Precision.CurrencyRounderImpl impl5 = (Precision.CurrencyRounderImpl)macros.precision;
                if (impl5.usage == Currency.CurrencyUsage.STANDARD) {
                    sb.append("precision-currency-standard");
                }
                else {
                    sb.append("precision-currency-cash");
                }
            }
            return true;
        }
        
        private static boolean roundingMode(final MacroProps macros, final StringBuilder sb) {
            if (macros.roundingMode == RoundingUtils.DEFAULT_ROUNDING_MODE) {
                return false;
            }
            roundingMode(macros.roundingMode, sb);
            return true;
        }
        
        private static boolean grouping(final MacroProps macros, final StringBuilder sb) {
            if (!(macros.grouping instanceof NumberFormatter.GroupingStrategy)) {
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom Grouper");
            }
            if (macros.grouping == NumberFormatter.GroupingStrategy.AUTO) {
                return false;
            }
            groupingStrategy((NumberFormatter.GroupingStrategy)macros.grouping, sb);
            return true;
        }
        
        private static boolean integerWidth(final MacroProps macros, final StringBuilder sb) {
            if (macros.integerWidth.equals(IntegerWidth.DEFAULT)) {
                return false;
            }
            sb.append("integer-width/");
            generateIntegerWidthOption(macros.integerWidth.minInt, macros.integerWidth.maxInt, sb);
            return true;
        }
        
        private static boolean symbols(final MacroProps macros, final StringBuilder sb) {
            if (macros.symbols instanceof NumberingSystem) {
                final NumberingSystem ns = (NumberingSystem)macros.symbols;
                if (ns.getName().equals("latn")) {
                    sb.append("latin");
                }
                else {
                    sb.append("numbering-system/");
                    generateNumberingSystemOption(ns, sb);
                }
                return true;
            }
            assert macros.symbols instanceof DecimalFormatSymbols;
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom DecimalFormatSymbols");
        }
        
        private static boolean unitWidth(final MacroProps macros, final StringBuilder sb) {
            if (macros.unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return false;
            }
            unitWidth(macros.unitWidth, sb);
            return true;
        }
        
        private static boolean sign(final MacroProps macros, final StringBuilder sb) {
            if (macros.sign == NumberFormatter.SignDisplay.AUTO) {
                return false;
            }
            signDisplay(macros.sign, sb);
            return true;
        }
        
        private static boolean decimal(final MacroProps macros, final StringBuilder sb) {
            if (macros.decimal == NumberFormatter.DecimalSeparatorDisplay.AUTO) {
                return false;
            }
            decimalSeparatorDisplay(macros.decimal, sb);
            return true;
        }
        
        private static boolean scale(final MacroProps macros, final StringBuilder sb) {
            if (!macros.scale.isValid()) {
                return false;
            }
            sb.append("scale/");
            generateScaleOption(macros.scale, sb);
            return true;
        }
    }
}
