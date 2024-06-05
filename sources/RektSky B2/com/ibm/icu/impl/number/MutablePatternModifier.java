package com.ibm.icu.impl.number;

import com.ibm.icu.number.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.text.*;

public class MutablePatternModifier implements Modifier, AffixUtils.SymbolProvider, MicroPropsGenerator
{
    final boolean isStrong;
    AffixPatternProvider patternInfo;
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
    
    public MutablePatternModifier(final boolean isStrong) {
        this.isStrong = isStrong;
    }
    
    public void setPatternInfo(final AffixPatternProvider patternInfo) {
        this.patternInfo = patternInfo;
    }
    
    public void setPatternAttributes(final NumberFormatter.SignDisplay signDisplay, final boolean perMille) {
        this.signDisplay = signDisplay;
        this.perMilleReplacesPercent = perMille;
    }
    
    public void setSymbols(final DecimalFormatSymbols symbols, final Currency currency, final NumberFormatter.UnitWidth unitWidth, final PluralRules rules) {
        assert rules != null == this.needsPlurals();
        this.symbols = symbols;
        this.currency = currency;
        this.unitWidth = unitWidth;
        this.rules = rules;
    }
    
    public void setNumberProperties(final int signum, final StandardPlural plural) {
        assert plural != null == this.needsPlurals();
        this.signum = signum;
        this.plural = plural;
    }
    
    public boolean needsPlurals() {
        return this.patternInfo.containsSymbolType(-7);
    }
    
    public ImmutablePatternModifier createImmutable() {
        return this.createImmutableAndChain(null);
    }
    
    public ImmutablePatternModifier createImmutableAndChain(final MicroPropsGenerator parent) {
        final NumberStringBuilder a = new NumberStringBuilder();
        final NumberStringBuilder b = new NumberStringBuilder();
        if (this.needsPlurals()) {
            final ParameterizedModifier pm = new ParameterizedModifier();
            for (final StandardPlural plural : StandardPlural.VALUES) {
                this.setNumberProperties(1, plural);
                pm.setModifier(1, plural, this.createConstantModifier(a, b));
                this.setNumberProperties(0, plural);
                pm.setModifier(0, plural, this.createConstantModifier(a, b));
                this.setNumberProperties(-1, plural);
                pm.setModifier(-1, plural, this.createConstantModifier(a, b));
            }
            pm.freeze();
            return new ImmutablePatternModifier(pm, this.rules, parent);
        }
        this.setNumberProperties(1, null);
        final Modifier positive = this.createConstantModifier(a, b);
        this.setNumberProperties(0, null);
        final Modifier zero = this.createConstantModifier(a, b);
        this.setNumberProperties(-1, null);
        final Modifier negative = this.createConstantModifier(a, b);
        final ParameterizedModifier pm2 = new ParameterizedModifier(positive, zero, negative);
        return new ImmutablePatternModifier(pm2, null, parent);
    }
    
    private ConstantMultiFieldModifier createConstantModifier(final NumberStringBuilder a, final NumberStringBuilder b) {
        this.insertPrefix(a.clear(), 0);
        this.insertSuffix(b.clear(), 0);
        if (this.patternInfo.hasCurrencySign()) {
            return new CurrencySpacingEnabledModifier(a, b, !this.patternInfo.hasBody(), this.isStrong, this.symbols);
        }
        return new ConstantMultiFieldModifier(a, b, !this.patternInfo.hasBody(), this.isStrong);
    }
    
    public MicroPropsGenerator addToChain(final MicroPropsGenerator parent) {
        this.parent = parent;
        return this;
    }
    
    @Override
    public MicroProps processQuantity(final DecimalQuantity fq) {
        final MicroProps micros = this.parent.processQuantity(fq);
        if (this.needsPlurals()) {
            final DecimalQuantity copy = fq.createCopy();
            micros.rounder.apply(copy);
            this.setNumberProperties(fq.signum(), copy.getStandardPlural(this.rules));
        }
        else {
            this.setNumberProperties(fq.signum(), null);
        }
        micros.modMiddle = this;
        return micros;
    }
    
    @Override
    public int apply(final NumberStringBuilder output, final int leftIndex, final int rightIndex) {
        final int prefixLen = this.insertPrefix(output, leftIndex);
        final int suffixLen = this.insertSuffix(output, rightIndex + prefixLen);
        int overwriteLen = 0;
        if (!this.patternInfo.hasBody()) {
            overwriteLen = output.splice(leftIndex + prefixLen, rightIndex + prefixLen, "", 0, 0, null);
        }
        CurrencySpacingEnabledModifier.applyCurrencySpacing(output, leftIndex, prefixLen, rightIndex + prefixLen + overwriteLen, suffixLen, this.symbols);
        return prefixLen + overwriteLen + suffixLen;
    }
    
    @Override
    public int getPrefixLength() {
        this.prepareAffix(true);
        final int result = AffixUtils.unescapedCount(this.currentAffix, true, this);
        return result;
    }
    
    @Override
    public int getCodePointCount() {
        this.prepareAffix(true);
        int result = AffixUtils.unescapedCount(this.currentAffix, false, this);
        this.prepareAffix(false);
        result += AffixUtils.unescapedCount(this.currentAffix, false, this);
        return result;
    }
    
    @Override
    public boolean isStrong() {
        return this.isStrong;
    }
    
    private int insertPrefix(final NumberStringBuilder sb, final int position) {
        this.prepareAffix(true);
        final int length = AffixUtils.unescape(this.currentAffix, sb, position, this);
        return length;
    }
    
    private int insertSuffix(final NumberStringBuilder sb, final int position) {
        this.prepareAffix(false);
        final int length = AffixUtils.unescape(this.currentAffix, sb, position, this);
        return length;
    }
    
    private void prepareAffix(final boolean isPrefix) {
        if (this.currentAffix == null) {
            this.currentAffix = new StringBuilder();
        }
        PatternStringUtils.patternInfoToStringBuilder(this.patternInfo, isPrefix, this.signum, this.signDisplay, this.plural, this.perMilleReplacesPercent, this.currentAffix);
    }
    
    @Override
    public CharSequence getSymbol(final int type) {
        switch (type) {
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
                final int selector = (this.unitWidth == NumberFormatter.UnitWidth.NARROW) ? 3 : 0;
                return this.currency.getName(this.symbols.getULocale(), selector, null);
            }
            case -6: {
                return this.currency.getCurrencyCode();
            }
            case -7: {
                assert this.plural != null;
                return this.currency.getName(this.symbols.getULocale(), 2, this.plural.getKeyword(), null);
            }
            case -8: {
                return "\ufffd";
            }
            case -9: {
                return this.currency.getName(this.symbols.getULocale(), 3, null);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static class ImmutablePatternModifier implements MicroPropsGenerator
    {
        final ParameterizedModifier pm;
        final PluralRules rules;
        final MicroPropsGenerator parent;
        
        ImmutablePatternModifier(final ParameterizedModifier pm, final PluralRules rules, final MicroPropsGenerator parent) {
            this.pm = pm;
            this.rules = rules;
            this.parent = parent;
        }
        
        @Override
        public MicroProps processQuantity(final DecimalQuantity quantity) {
            final MicroProps micros = this.parent.processQuantity(quantity);
            this.applyToMicros(micros, quantity);
            return micros;
        }
        
        public void applyToMicros(final MicroProps micros, final DecimalQuantity quantity) {
            if (this.rules == null) {
                micros.modMiddle = this.pm.getModifier(quantity.signum());
            }
            else {
                final DecimalQuantity copy = quantity.createCopy();
                copy.roundToInfinity();
                final StandardPlural plural = copy.getStandardPlural(this.rules);
                micros.modMiddle = this.pm.getModifier(quantity.signum(), plural);
            }
        }
    }
}
