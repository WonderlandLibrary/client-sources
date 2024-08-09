/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.SimpleModifier;
import com.ibm.icu.impl.number.range.PrefixInfixSuffixLengthHelper;
import com.ibm.icu.impl.number.range.RangeMacroProps;
import com.ibm.icu.impl.number.range.StandardPluralRanges;
import com.ibm.icu.number.FormattedNumberRange;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.NumberFormatterImpl;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.MissingResourceException;

class NumberRangeFormatterImpl {
    final NumberFormatterImpl formatterImpl1;
    final NumberFormatterImpl formatterImpl2;
    final boolean fSameFormatters;
    final NumberRangeFormatter.RangeCollapse fCollapse;
    final NumberRangeFormatter.RangeIdentityFallback fIdentityFallback;
    String fRangePattern;
    SimpleModifier fApproximatelyModifier;
    final StandardPluralRanges fPluralRanges;
    static final boolean $assertionsDisabled = !NumberRangeFormatterImpl.class.desiredAssertionStatus();

    int identity2d(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        return rangeIdentityFallback.ordinal() | rangeIdentityResult.ordinal() << 4;
    }

    private static void getNumberRangeData(ULocale uLocale, String string, NumberRangeFormatterImpl numberRangeFormatterImpl) {
        StringBuilder stringBuilder = new StringBuilder();
        NumberRangeDataSink numberRangeDataSink = new NumberRangeDataSink(stringBuilder);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        stringBuilder.append("NumberElements/");
        stringBuilder.append(string);
        stringBuilder.append("/miscPatterns");
        String string2 = stringBuilder.toString();
        try {
            iCUResourceBundle.getAllItemsWithFallback(string2, numberRangeDataSink);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        if (!numberRangeDataSink.isComplete()) {
            iCUResourceBundle.getAllItemsWithFallback("NumberElements/latn/miscPatterns", numberRangeDataSink);
        }
        numberRangeDataSink.fillInDefaults();
        numberRangeFormatterImpl.fRangePattern = numberRangeDataSink.rangePattern;
        numberRangeFormatterImpl.fApproximatelyModifier = new SimpleModifier(numberRangeDataSink.approximatelyPattern, null, false);
    }

    public NumberRangeFormatterImpl(RangeMacroProps rangeMacroProps) {
        this.formatterImpl1 = new NumberFormatterImpl(rangeMacroProps.formatter1 != null ? rangeMacroProps.formatter1.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve());
        this.formatterImpl2 = new NumberFormatterImpl(rangeMacroProps.formatter2 != null ? rangeMacroProps.formatter2.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve());
        this.fSameFormatters = rangeMacroProps.sameFormatters != 0;
        this.fCollapse = rangeMacroProps.collapse != null ? rangeMacroProps.collapse : NumberRangeFormatter.RangeCollapse.AUTO;
        this.fIdentityFallback = rangeMacroProps.identityFallback != null ? rangeMacroProps.identityFallback : NumberRangeFormatter.RangeIdentityFallback.APPROXIMATELY;
        String string = this.formatterImpl1.getRawMicroProps().nsName;
        if (string == null || !string.equals(this.formatterImpl2.getRawMicroProps().nsName)) {
            throw new IllegalArgumentException("Both formatters must have same numbering system");
        }
        NumberRangeFormatterImpl.getNumberRangeData(rangeMacroProps.loc, string, this);
        this.fPluralRanges = new StandardPluralRanges(rangeMacroProps.loc);
    }

    public FormattedNumberRange format(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, boolean bl) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        MicroProps microProps = this.formatterImpl1.preProcess(decimalQuantity);
        MicroProps microProps2 = this.fSameFormatters ? this.formatterImpl1.preProcess(decimalQuantity2) : this.formatterImpl2.preProcess(decimalQuantity2);
        if (!(microProps.modInner.semanticallyEquivalent(microProps2.modInner) && microProps.modMiddle.semanticallyEquivalent(microProps2.modMiddle) && microProps.modOuter.semanticallyEquivalent(microProps2.modOuter))) {
            this.formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
            return new FormattedNumberRange(formattedStringBuilder, decimalQuantity, decimalQuantity2, NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL);
        }
        NumberRangeFormatter.RangeIdentityResult rangeIdentityResult = bl ? NumberRangeFormatter.RangeIdentityResult.EQUAL_BEFORE_ROUNDING : (decimalQuantity.equals(decimalQuantity2) ? NumberRangeFormatter.RangeIdentityResult.EQUAL_AFTER_ROUNDING : NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL);
        switch (this.identity2d(this.fIdentityFallback, rangeIdentityResult)) {
            case 3: 
            case 19: 
            case 32: 
            case 33: 
            case 34: 
            case 35: {
                this.formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
                break;
            }
            case 2: 
            case 17: 
            case 18: {
                this.formatApproximately(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
                break;
            }
            case 0: 
            case 1: 
            case 16: {
                this.formatSingleValue(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
                break;
            }
            default: {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
            }
        }
        return new FormattedNumberRange(formattedStringBuilder, decimalQuantity, decimalQuantity2, rangeIdentityResult);
    }

    private void formatSingleValue(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, FormattedStringBuilder formattedStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0);
            NumberFormatterImpl.writeAffixes(microProps, formattedStringBuilder, 0, n);
        } else {
            this.formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
        }
    }

    private void formatApproximately(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, FormattedStringBuilder formattedStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, 0);
            n += microProps.modInner.apply(formattedStringBuilder, 0, n);
            n += microProps.modMiddle.apply(formattedStringBuilder, 0, n);
            n += this.fApproximatelyModifier.apply(formattedStringBuilder, 0, n);
            microProps.modOuter.apply(formattedStringBuilder, 0, n);
        } else {
            this.formatRange(decimalQuantity, decimalQuantity2, formattedStringBuilder, microProps, microProps2);
        }
    }

    private void formatRange(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, FormattedStringBuilder formattedStringBuilder, MicroProps microProps, MicroProps microProps2) {
        boolean bl;
        Object object;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        switch (1.$SwitchMap$com$ibm$icu$number$NumberRangeFormatter$RangeCollapse[this.fCollapse.ordinal()]) {
            case 1: 
            case 2: 
            case 3: {
                bl4 = microProps.modOuter.semanticallyEquivalent(microProps2.modOuter);
                if (!bl4) {
                    bl3 = false;
                    bl2 = false;
                    break;
                }
                bl3 = microProps.modMiddle.semanticallyEquivalent(microProps2.modMiddle);
                if (!bl3) {
                    bl2 = false;
                    break;
                }
                object = microProps.modMiddle;
                if (this.fCollapse == NumberRangeFormatter.RangeCollapse.UNIT) {
                    if (!object.containsField(NumberFormat.Field.CURRENCY) && !object.containsField(NumberFormat.Field.PERCENT)) {
                        bl3 = false;
                    }
                } else if (this.fCollapse == NumberRangeFormatter.RangeCollapse.AUTO && object.getCodePointCount() <= 1) {
                    bl3 = false;
                }
                if (!bl3 || this.fCollapse != NumberRangeFormatter.RangeCollapse.ALL) {
                    bl2 = false;
                    break;
                }
                bl2 = microProps.modInner.semanticallyEquivalent(microProps2.modInner);
                break;
            }
            default: {
                bl4 = false;
                bl3 = false;
                bl2 = false;
            }
        }
        object = new PrefixInfixSuffixLengthHelper();
        SimpleModifier.formatTwoArgPattern(this.fRangePattern, formattedStringBuilder, 0, (PrefixInfixSuffixLengthHelper)object, null);
        if (!$assertionsDisabled && ((PrefixInfixSuffixLengthHelper)object).lengthInfix <= 0) {
            throw new AssertionError();
        }
        boolean bl5 = !bl2 && microProps.modInner.getCodePointCount() > 0;
        boolean bl6 = !bl3 && microProps.modMiddle.getCodePointCount() > 0;
        boolean bl7 = bl = !bl4 && microProps.modOuter.getCodePointCount() > 0;
        if (bl5 || bl6 || bl) {
            if (!PatternProps.isWhiteSpace(formattedStringBuilder.charAt(((PrefixInfixSuffixLengthHelper)object).index1()))) {
                ((PrefixInfixSuffixLengthHelper)object).lengthInfix += formattedStringBuilder.insertCodePoint(((PrefixInfixSuffixLengthHelper)object).index1(), 32, null);
            }
            if (!PatternProps.isWhiteSpace(formattedStringBuilder.charAt(((PrefixInfixSuffixLengthHelper)object).index2() - 1))) {
                ((PrefixInfixSuffixLengthHelper)object).lengthInfix += formattedStringBuilder.insertCodePoint(((PrefixInfixSuffixLengthHelper)object).index2(), 32, null);
            }
        }
        ((PrefixInfixSuffixLengthHelper)object).length1 += NumberFormatterImpl.writeNumber(microProps, decimalQuantity, formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0());
        ((PrefixInfixSuffixLengthHelper)object).length2 += NumberFormatterImpl.writeNumber(microProps2, decimalQuantity2, formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index2());
        if (bl2) {
            Modifier modifier = this.resolveModifierPlurals(microProps.modInner, microProps2.modInner);
            ((PrefixInfixSuffixLengthHelper)object).lengthInfix += modifier.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object).length1 += microProps.modInner.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index1());
            ((PrefixInfixSuffixLengthHelper)object).length2 += microProps2.modInner.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index2(), ((PrefixInfixSuffixLengthHelper)object).index3());
        }
        if (bl3) {
            Modifier modifier = this.resolveModifierPlurals(microProps.modMiddle, microProps2.modMiddle);
            ((PrefixInfixSuffixLengthHelper)object).lengthInfix += modifier.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object).length1 += microProps.modMiddle.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index1());
            ((PrefixInfixSuffixLengthHelper)object).length2 += microProps2.modMiddle.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index2(), ((PrefixInfixSuffixLengthHelper)object).index3());
        }
        if (bl4) {
            Modifier modifier = this.resolveModifierPlurals(microProps.modOuter, microProps2.modOuter);
            ((PrefixInfixSuffixLengthHelper)object).lengthInfix += modifier.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object).length1 += microProps.modOuter.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index0(), ((PrefixInfixSuffixLengthHelper)object).index1());
            ((PrefixInfixSuffixLengthHelper)object).length2 += microProps2.modOuter.apply(formattedStringBuilder, ((PrefixInfixSuffixLengthHelper)object).index2(), ((PrefixInfixSuffixLengthHelper)object).index3());
        }
    }

    Modifier resolveModifierPlurals(Modifier modifier, Modifier modifier2) {
        Modifier.Parameters parameters = modifier.getParameters();
        if (parameters == null) {
            return modifier;
        }
        Modifier.Parameters parameters2 = modifier2.getParameters();
        if (parameters2 == null) {
            return modifier;
        }
        StandardPlural standardPlural = this.fPluralRanges.resolve(parameters.plural, parameters2.plural);
        if (!$assertionsDisabled && parameters.obj != parameters2.obj) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && parameters.signum != parameters2.signum) {
            throw new AssertionError();
        }
        Modifier modifier3 = parameters.obj.getModifier(parameters.signum, standardPlural);
        if (!$assertionsDisabled && modifier3 == null) {
            throw new AssertionError();
        }
        return modifier3;
    }

    private static final class NumberRangeDataSink
    extends UResource.Sink {
        String rangePattern;
        String approximatelyPattern;
        StringBuilder sb;

        NumberRangeDataSink(StringBuilder stringBuilder) {
            this.sb = stringBuilder;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string;
                if (key.contentEquals("range") && !this.hasRangeData()) {
                    string = value.getString();
                    this.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments(string, this.sb, 2, 2);
                }
                if (key.contentEquals("approximately") && !this.hasApproxData()) {
                    string = value.getString();
                    this.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(string, this.sb, 1, 1);
                }
                ++n;
            }
        }

        private boolean hasRangeData() {
            return this.rangePattern != null;
        }

        private boolean hasApproxData() {
            return this.approximatelyPattern != null;
        }

        public boolean isComplete() {
            return this.hasRangeData() && this.hasApproxData();
        }

        public void fillInDefaults() {
            if (!this.hasRangeData()) {
                this.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments("{0}\u2013{1}", this.sb, 2, 2);
            }
            if (!this.hasApproxData()) {
                this.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments("~{0}", this.sb, 1, 1);
            }
        }
    }
}

