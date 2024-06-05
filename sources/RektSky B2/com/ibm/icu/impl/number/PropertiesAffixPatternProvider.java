package com.ibm.icu.impl.number;

public class PropertiesAffixPatternProvider implements AffixPatternProvider
{
    private final String posPrefix;
    private final String posSuffix;
    private final String negPrefix;
    private final String negSuffix;
    
    public PropertiesAffixPatternProvider(final DecimalFormatProperties properties) {
        final String ppo = AffixUtils.escape(properties.getPositivePrefix());
        final String pso = AffixUtils.escape(properties.getPositiveSuffix());
        final String npo = AffixUtils.escape(properties.getNegativePrefix());
        final String nso = AffixUtils.escape(properties.getNegativeSuffix());
        final String ppp = properties.getPositivePrefixPattern();
        final String psp = properties.getPositiveSuffixPattern();
        final String npp = properties.getNegativePrefixPattern();
        final String nsp = properties.getNegativeSuffixPattern();
        if (ppo != null) {
            this.posPrefix = ppo;
        }
        else if (ppp != null) {
            this.posPrefix = ppp;
        }
        else {
            this.posPrefix = "";
        }
        if (pso != null) {
            this.posSuffix = pso;
        }
        else if (psp != null) {
            this.posSuffix = psp;
        }
        else {
            this.posSuffix = "";
        }
        if (npo != null) {
            this.negPrefix = npo;
        }
        else if (npp != null) {
            this.negPrefix = npp;
        }
        else {
            this.negPrefix = ((ppp == null) ? "-" : ("-" + ppp));
        }
        if (nso != null) {
            this.negSuffix = nso;
        }
        else if (nsp != null) {
            this.negSuffix = nsp;
        }
        else {
            this.negSuffix = ((psp == null) ? "" : psp);
        }
    }
    
    @Override
    public char charAt(final int flags, final int i) {
        return this.getString(flags).charAt(i);
    }
    
    @Override
    public int length(final int flags) {
        return this.getString(flags).length();
    }
    
    @Override
    public String getString(final int flags) {
        final boolean prefix = (flags & 0x100) != 0x0;
        final boolean negative = (flags & 0x200) != 0x0;
        if (prefix && negative) {
            return this.negPrefix;
        }
        if (prefix) {
            return this.posPrefix;
        }
        if (negative) {
            return this.negSuffix;
        }
        return this.posSuffix;
    }
    
    @Override
    public boolean positiveHasPlusSign() {
        return AffixUtils.containsType(this.posPrefix, -2) || AffixUtils.containsType(this.posSuffix, -2);
    }
    
    @Override
    public boolean hasNegativeSubpattern() {
        return true;
    }
    
    @Override
    public boolean negativeHasMinusSign() {
        return AffixUtils.containsType(this.negPrefix, -1) || AffixUtils.containsType(this.negSuffix, -1);
    }
    
    @Override
    public boolean hasCurrencySign() {
        return AffixUtils.hasCurrencySymbols(this.posPrefix) || AffixUtils.hasCurrencySymbols(this.posSuffix) || AffixUtils.hasCurrencySymbols(this.negPrefix) || AffixUtils.hasCurrencySymbols(this.negSuffix);
    }
    
    @Override
    public boolean containsSymbolType(final int type) {
        return AffixUtils.containsType(this.posPrefix, type) || AffixUtils.containsType(this.posSuffix, type) || AffixUtils.containsType(this.negPrefix, type) || AffixUtils.containsType(this.negSuffix, type);
    }
    
    @Override
    public boolean hasBody() {
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString() + " {" + this.posPrefix + "#" + this.posSuffix + ";" + this.negPrefix + "#" + this.negSuffix + "}";
    }
}
