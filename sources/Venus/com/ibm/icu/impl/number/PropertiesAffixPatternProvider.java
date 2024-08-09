/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.number.AffixPatternProvider;
import com.ibm.icu.impl.number.AffixUtils;
import com.ibm.icu.impl.number.DecimalFormatProperties;

public class PropertiesAffixPatternProvider
implements AffixPatternProvider {
    private final String posPrefix;
    private final String posSuffix;
    private final String negPrefix;
    private final String negSuffix;
    private final boolean isCurrencyPattern;

    public PropertiesAffixPatternProvider(DecimalFormatProperties decimalFormatProperties) {
        String string = AffixUtils.escape(decimalFormatProperties.getPositivePrefix());
        String string2 = AffixUtils.escape(decimalFormatProperties.getPositiveSuffix());
        String string3 = AffixUtils.escape(decimalFormatProperties.getNegativePrefix());
        String string4 = AffixUtils.escape(decimalFormatProperties.getNegativeSuffix());
        String string5 = decimalFormatProperties.getPositivePrefixPattern();
        String string6 = decimalFormatProperties.getPositiveSuffixPattern();
        String string7 = decimalFormatProperties.getNegativePrefixPattern();
        String string8 = decimalFormatProperties.getNegativeSuffixPattern();
        this.posPrefix = string != null ? string : (string5 != null ? string5 : "");
        this.posSuffix = string2 != null ? string2 : (string6 != null ? string6 : "");
        if (string3 != null) {
            this.negPrefix = string3;
        } else if (string7 != null) {
            this.negPrefix = string7;
        } else {
            String string9 = this.negPrefix = string5 == null ? "-" : "-" + string5;
        }
        this.negSuffix = string4 != null ? string4 : (string8 != null ? string8 : (string6 == null ? "" : string6));
        this.isCurrencyPattern = AffixUtils.hasCurrencySymbols(string5) || AffixUtils.hasCurrencySymbols(string6) || AffixUtils.hasCurrencySymbols(string7) || AffixUtils.hasCurrencySymbols(string8);
    }

    @Override
    public char charAt(int n, int n2) {
        return this.getString(n).charAt(n2);
    }

    @Override
    public int length(int n) {
        return this.getString(n).length();
    }

    @Override
    public String getString(int n) {
        boolean bl;
        boolean bl2 = (n & 0x100) != 0;
        boolean bl3 = bl = (n & 0x200) != 0;
        if (bl2 && bl) {
            return this.negPrefix;
        }
        if (bl2) {
            return this.posPrefix;
        }
        if (bl) {
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
        return this.negSuffix != this.posSuffix || this.negPrefix.length() != this.posPrefix.length() + 1 || !this.negPrefix.regionMatches(1, this.posPrefix, 0, this.posPrefix.length()) || this.negPrefix.charAt(0) != '-';
    }

    @Override
    public boolean negativeHasMinusSign() {
        return AffixUtils.containsType(this.negPrefix, -1) || AffixUtils.containsType(this.negSuffix, -1);
    }

    @Override
    public boolean hasCurrencySign() {
        return this.isCurrencyPattern;
    }

    @Override
    public boolean containsSymbolType(int n) {
        return AffixUtils.containsType(this.posPrefix, n) || AffixUtils.containsType(this.posSuffix, n) || AffixUtils.containsType(this.negPrefix, n) || AffixUtils.containsType(this.negSuffix, n);
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    public String toString() {
        return super.toString() + " {" + this.posPrefix + "#" + this.posSuffix + ";" + this.negPrefix + "#" + this.negSuffix + "}";
    }
}

