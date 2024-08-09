/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ULocale;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class CurrencyPluralInfo
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private static final char[] tripleCurrencySign = new char[]{'\u00a4', '\u00a4', '\u00a4'};
    private static final String tripleCurrencyStr = new String(tripleCurrencySign);
    private static final char[] defaultCurrencyPluralPatternChar = new char[]{'\u0000', '.', '#', '#', ' ', '\u00a4', '\u00a4', '\u00a4'};
    private static final String defaultCurrencyPluralPattern = new String(defaultCurrencyPluralPatternChar);
    private Map<String, String> pluralCountToCurrencyUnitPattern = null;
    private PluralRules pluralRules = null;
    private ULocale ulocale = null;

    public CurrencyPluralInfo() {
        this.initialize(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public CurrencyPluralInfo(Locale locale) {
        this.initialize(ULocale.forLocale(locale));
    }

    public CurrencyPluralInfo(ULocale uLocale) {
        this.initialize(uLocale);
    }

    public static CurrencyPluralInfo getInstance() {
        return new CurrencyPluralInfo();
    }

    public static CurrencyPluralInfo getInstance(Locale locale) {
        return new CurrencyPluralInfo(locale);
    }

    public static CurrencyPluralInfo getInstance(ULocale uLocale) {
        return new CurrencyPluralInfo(uLocale);
    }

    public PluralRules getPluralRules() {
        return this.pluralRules;
    }

    public String getCurrencyPluralPattern(String string) {
        String string2 = this.pluralCountToCurrencyUnitPattern.get(string);
        if (string2 == null) {
            if (!string.equals("other")) {
                string2 = this.pluralCountToCurrencyUnitPattern.get("other");
            }
            if (string2 == null) {
                string2 = defaultCurrencyPluralPattern;
            }
        }
        return string2;
    }

    public ULocale getLocale() {
        return this.ulocale;
    }

    public void setPluralRules(String string) {
        this.pluralRules = PluralRules.createRules(string);
    }

    public void setCurrencyPluralPattern(String string, String string2) {
        this.pluralCountToCurrencyUnitPattern.put(string, string2);
    }

    public void setLocale(ULocale uLocale) {
        this.ulocale = uLocale;
        this.initialize(uLocale);
    }

    public Object clone() {
        try {
            CurrencyPluralInfo currencyPluralInfo = (CurrencyPluralInfo)super.clone();
            currencyPluralInfo.ulocale = (ULocale)this.ulocale.clone();
            currencyPluralInfo.pluralCountToCurrencyUnitPattern = new HashMap<String, String>();
            for (String string : this.pluralCountToCurrencyUnitPattern.keySet()) {
                String string2 = this.pluralCountToCurrencyUnitPattern.get(string);
                currencyPluralInfo.pluralCountToCurrencyUnitPattern.put(string, string2);
            }
            return currencyPluralInfo;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        if (object instanceof CurrencyPluralInfo) {
            CurrencyPluralInfo currencyPluralInfo = (CurrencyPluralInfo)object;
            return this.pluralRules.equals(currencyPluralInfo.pluralRules) && this.pluralCountToCurrencyUnitPattern.equals(currencyPluralInfo.pluralCountToCurrencyUnitPattern);
        }
        return true;
    }

    public int hashCode() {
        return this.pluralCountToCurrencyUnitPattern.hashCode() ^ this.pluralRules.hashCode() ^ this.ulocale.hashCode();
    }

    @Deprecated
    String select(double d) {
        return this.pluralRules.select(d);
    }

    @Deprecated
    public String select(PluralRules.FixedDecimal fixedDecimal) {
        return this.pluralRules.select(fixedDecimal);
    }

    @Deprecated
    public Iterator<String> pluralPatternIterator() {
        return this.pluralCountToCurrencyUnitPattern.keySet().iterator();
    }

    private void initialize(ULocale uLocale) {
        this.ulocale = uLocale;
        this.pluralRules = PluralRules.forLocale(uLocale);
        this.setupCurrencyPluralPattern(uLocale);
    }

    private void setupCurrencyPluralPattern(ULocale uLocale) {
        this.pluralCountToCurrencyUnitPattern = new HashMap<String, String>();
        String string = NumberFormat.getPattern(uLocale, 0);
        int n = string.indexOf(";");
        String string2 = null;
        if (n != -1) {
            string2 = string.substring(n + 1);
            string = string.substring(0, n);
        }
        Map<String, String> map = CurrencyData.provider.getInstance(uLocale, true).getUnitPatterns();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string3 = entry.getKey();
            String string4 = entry.getValue();
            String string5 = string4.replace("{0}", string);
            String string6 = string5.replace("{1}", tripleCurrencyStr);
            if (n != -1) {
                String string7 = string4;
                String string8 = string7.replace("{0}", string2);
                String string9 = string8.replace("{1}", tripleCurrencyStr);
                StringBuilder stringBuilder = new StringBuilder(string6);
                stringBuilder.append(";");
                stringBuilder.append(string9);
                string6 = stringBuilder.toString();
            }
            this.pluralCountToCurrencyUnitPattern.put(string3, string6);
        }
    }
}

