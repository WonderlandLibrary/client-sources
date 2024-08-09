/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.AdoptingModifierStore;
import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.ConstantMultiFieldModifier;
import com.ibm.icu.impl.number.CurrencySpacingEnabledModifier;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.PatternStringUtils;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import java.text.Format;

public class MutablePatternModifier
implements Modifier,
AffixUtils.SymbolProvider,
MicroPropsGenerator {
    final boolean isStrong;
    AffixPatternProvider patternInfo;
    NumberFormat.Field field;
    NumberFormatter.SignDisplay signDisplay;
    boolean perMilleReplacesPercent;
    DecimalFormatSymbols symbols;
    NumberFormatter.UnitWidth unitWidth;
    Currency currency;
    PluralRules rules;
    int signum;
    StandardPlural plural;
    MicroPropsGenerator parent;
    StringBuilder currentAffix;
    static final boolean $assertionsDisabled = !MutablePatternModifier.class.desiredAssertionStatus();

    public MutablePatternModifier(boolean bl) {
        this.isStrong = bl;
    }

    public void setPatternInfo(AffixPatternProvider affixPatternProvider, NumberFormat.Field field) {
        this.patternInfo = affixPatternProvider;
        this.field = field;
    }

    public void setPatternAttributes(NumberFormatter.SignDisplay signDisplay, boolean bl) {
        this.signDisplay = signDisplay;
        this.perMilleReplacesPercent = bl;
    }

    public void setSymbols(DecimalFormatSymbols decimalFormatSymbols, Currency currency, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules) {
        if (!$assertionsDisabled && pluralRules != null != this.needsPlurals()) {
            throw new AssertionError();
        }
        this.symbols = decimalFormatSymbols;
        this.currency = currency;
        this.unitWidth = unitWidth;
        this.rules = pluralRules;
    }

    public void setNumberProperties(int n, StandardPlural standardPlural) {
        if (!$assertionsDisabled && standardPlural != null != this.needsPlurals()) {
            throw new AssertionError();
        }
        this.signum = n;
        this.plural = standardPlural;
    }

    public boolean needsPlurals() {
        return this.patternInfo.containsSymbolType(-7);
    }

    public ImmutablePatternModifier createImmutable() {
        return this.createImmutableAndChain(null);
    }

    public ImmutablePatternModifier createImmutableAndChain(MicroPropsGenerator microPropsGenerator) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        FormattedStringBuilder formattedStringBuilder2 = new FormattedStringBuilder();
        if (this.needsPlurals()) {
            AdoptingModifierStore adoptingModifierStore = new AdoptingModifierStore();
            for (StandardPlural standardPlural : StandardPlural.VALUES) {
                this.setNumberProperties(1, standardPlural);
                adoptingModifierStore.setModifier(1, standardPlural, this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
                this.setNumberProperties(0, standardPlural);
                adoptingModifierStore.setModifier(0, standardPlural, this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
                this.setNumberProperties(-1, standardPlural);
                adoptingModifierStore.setModifier(-1, standardPlural, this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2));
            }
            adoptingModifierStore.freeze();
            return new ImmutablePatternModifier(adoptingModifierStore, this.rules, microPropsGenerator);
        }
        this.setNumberProperties(1, null);
        ConstantMultiFieldModifier constantMultiFieldModifier = this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        this.setNumberProperties(0, null);
        ConstantMultiFieldModifier constantMultiFieldModifier2 = this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        this.setNumberProperties(-1, null);
        ConstantMultiFieldModifier constantMultiFieldModifier3 = this.createConstantModifier(formattedStringBuilder, formattedStringBuilder2);
        AdoptingModifierStore adoptingModifierStore = new AdoptingModifierStore(constantMultiFieldModifier, constantMultiFieldModifier2, constantMultiFieldModifier3);
        return new ImmutablePatternModifier(adoptingModifierStore, null, microPropsGenerator);
    }

    private ConstantMultiFieldModifier createConstantModifier(FormattedStringBuilder formattedStringBuilder, FormattedStringBuilder formattedStringBuilder2) {
        this.insertPrefix(formattedStringBuilder.clear(), 0);
        this.insertSuffix(formattedStringBuilder2.clear(), 0);
        if (this.patternInfo.hasCurrencySign()) {
            return new CurrencySpacingEnabledModifier(formattedStringBuilder, formattedStringBuilder2, !this.patternInfo.hasBody(), this.isStrong, this.symbols);
        }
        return new ConstantMultiFieldModifier(formattedStringBuilder, formattedStringBuilder2, !this.patternInfo.hasBody(), this.isStrong);
    }

    public MicroPropsGenerator addToChain(MicroPropsGenerator microPropsGenerator) {
        this.parent = microPropsGenerator;
        return this;
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.parent.processQuantity(decimalQuantity);
        if (this.needsPlurals()) {
            StandardPlural standardPlural = RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity);
            this.setNumberProperties(decimalQuantity.signum(), standardPlural);
        } else {
            this.setNumberProperties(decimalQuantity.signum(), null);
        }
        microProps.modMiddle = this;
        return microProps;
    }

    @Override
    public int apply(FormattedStringBuilder formattedStringBuilder, int n, int n2) {
        int n3 = this.insertPrefix(formattedStringBuilder, n);
        int n4 = this.insertSuffix(formattedStringBuilder, n2 + n3);
        int n5 = 0;
        if (!this.patternInfo.hasBody()) {
            n5 = formattedStringBuilder.splice(n + n3, n2 + n3, "", 0, 0, null);
        }
        CurrencySpacingEnabledModifier.applyCurrencySpacing(formattedStringBuilder, n, n3, n2 + n3 + n5, n4, this.symbols);
        return n3 + n5 + n4;
    }

    @Override
    public int getPrefixLength() {
        this.prepareAffix(true);
        int n = AffixUtils.unescapedCount(this.currentAffix, true, this);
        return n;
    }

    @Override
    public int getCodePointCount() {
        this.prepareAffix(true);
        int n = AffixUtils.unescapedCount(this.currentAffix, false, this);
        this.prepareAffix(false);
        return n += AffixUtils.unescapedCount(this.currentAffix, false, this);
    }

    @Override
    public boolean isStrong() {
        return this.isStrong;
    }

    @Override
    public boolean containsField(Format.Field field) {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return true;
    }

    @Override
    public Modifier.Parameters getParameters() {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return null;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return true;
    }

    private int insertPrefix(FormattedStringBuilder formattedStringBuilder, int n) {
        this.prepareAffix(true);
        int n2 = AffixUtils.unescape(this.currentAffix, formattedStringBuilder, n, this, this.field);
        return n2;
    }

    private int insertSuffix(FormattedStringBuilder formattedStringBuilder, int n) {
        this.prepareAffix(false);
        int n2 = AffixUtils.unescape(this.currentAffix, formattedStringBuilder, n, this, this.field);
        return n2;
    }

    private void prepareAffix(boolean bl) {
        if (this.currentAffix == null) {
            this.currentAffix = new StringBuilder();
        }
        PatternStringUtils.patternInfoToStringBuilder(this.patternInfo, bl, this.signum, this.signDisplay, this.plural, this.perMilleReplacesPercent, this.currentAffix);
    }

    @Override
    public CharSequence getSymbol(int n) {
        switch (n) {
            case -1: {
                return this.symbols.getMinusSignString();
            }
            case -2: {
                return this.symbols.getPlusSignString();
            }
            case -3: {
                return this.symbols.getPercentString();
            }
            case -4: {
                return this.symbols.getPerMillString();
            }
            case -5: {
                if (this.unitWidth == NumberFormatter.UnitWidth.ISO_CODE) {
                    return this.currency.getCurrencyCode();
                }
                if (this.unitWidth == NumberFormatter.UnitWidth.HIDDEN) {
                    return "";
                }
                int n2 = this.unitWidth == NumberFormatter.UnitWidth.NARROW ? 3 : 0;
                return this.currency.getName(this.symbols.getULocale(), n2, null);
            }
            case -6: {
                return this.currency.getCurrencyCode();
            }
            case -7: {
                if (!$assertionsDisabled && this.plural == null) {
                    throw new AssertionError();
                }
                return this.currency.getName(this.symbols.getULocale(), 2, this.plural.getKeyword(), null);
            }
            case -8: {
                return "\ufffd";
            }
            case -9: {
                return this.currency.getName(this.symbols.getULocale(), 3, null);
            }
        }
        throw new AssertionError();
    }

    public static class ImmutablePatternModifier
    implements MicroPropsGenerator {
        final AdoptingModifierStore pm;
        final PluralRules rules;
        final MicroPropsGenerator parent;

        ImmutablePatternModifier(AdoptingModifierStore adoptingModifierStore, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
            this.pm = adoptingModifierStore;
            this.rules = pluralRules;
            this.parent = microPropsGenerator;
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            MicroProps microProps = this.parent.processQuantity(decimalQuantity);
            this.applyToMicros(microProps, decimalQuantity);
            return microProps;
        }

        public void applyToMicros(MicroProps microProps, DecimalQuantity decimalQuantity) {
            if (this.rules == null) {
                microProps.modMiddle = this.pm.getModifierWithoutPlural(decimalQuantity.signum());
            } else {
                StandardPlural standardPlural = RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity);
                microProps.modMiddle = this.pm.getModifier(decimalQuantity.signum(), standardPlural);
            }
        }
    }
}

