/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.RBNFPostProcessor;
import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.text.RbnfLenientScannerProvider;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class RuleBasedNumberFormat
extends NumberFormat {
    static final long serialVersionUID = -7664252765575395068L;
    public static final int SPELLOUT = 1;
    public static final int ORDINAL = 2;
    public static final int DURATION = 3;
    public static final int NUMBERING_SYSTEM = 4;
    private transient NFRuleSet[] ruleSets = null;
    private transient Map<String, NFRuleSet> ruleSetsMap = null;
    private transient NFRuleSet defaultRuleSet = null;
    private ULocale locale = null;
    private int roundingMode = 7;
    private transient RbnfLenientScannerProvider scannerProvider = null;
    private transient boolean lookedForScanner;
    private transient DecimalFormatSymbols decimalFormatSymbols = null;
    private transient DecimalFormat decimalFormat = null;
    private transient NFRule defaultInfinityRule = null;
    private transient NFRule defaultNaNRule = null;
    private boolean lenientParse = false;
    private transient String lenientParseRules;
    private transient String postProcessRules;
    private transient RBNFPostProcessor postProcessor;
    private Map<String, String[]> ruleSetDisplayNames;
    private String[] publicRuleSetNames;
    private boolean capitalizationInfoIsSet = false;
    private boolean capitalizationForListOrMenu = false;
    private boolean capitalizationForStandAlone = false;
    private transient BreakIterator capitalizationBrkIter = null;
    private static final boolean DEBUG = ICUDebug.enabled("rbnf");
    private static final String[] rulenames = new String[]{"SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules"};
    private static final String[] locnames = new String[]{"SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations"};
    private static final com.ibm.icu.math.BigDecimal MAX_VALUE = com.ibm.icu.math.BigDecimal.valueOf(Long.MAX_VALUE);
    private static final com.ibm.icu.math.BigDecimal MIN_VALUE = com.ibm.icu.math.BigDecimal.valueOf(Long.MIN_VALUE);

    public RuleBasedNumberFormat(String string) {
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(string, null);
    }

    public RuleBasedNumberFormat(String string, String[][] stringArray) {
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(string, stringArray);
    }

    public RuleBasedNumberFormat(String string, Locale locale) {
        this(string, ULocale.forLocale(locale));
    }

    public RuleBasedNumberFormat(String string, ULocale uLocale) {
        this.locale = uLocale;
        this.init(string, null);
    }

    public RuleBasedNumberFormat(String string, String[][] stringArray, ULocale uLocale) {
        this.locale = uLocale;
        this.init(string, stringArray);
    }

    public RuleBasedNumberFormat(Locale locale, int n) {
        this(ULocale.forLocale(locale), n);
    }

    public RuleBasedNumberFormat(ULocale uLocale, int n) {
        ICUResourceBundle iCUResourceBundle;
        this.locale = uLocale;
        ICUResourceBundle iCUResourceBundle2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/rbnf", uLocale);
        ULocale uLocale2 = iCUResourceBundle2.getULocale();
        this.setLocale(uLocale2, uLocale2);
        StringBuilder stringBuilder = new StringBuilder();
        String[][] stringArray = null;
        try {
            iCUResourceBundle = iCUResourceBundle2.getWithFallback("RBNFRules/" + rulenames[n - 1]);
            UResourceBundleIterator uResourceBundleIterator = iCUResourceBundle.getIterator();
            while (uResourceBundleIterator.hasNext()) {
                stringBuilder.append(uResourceBundleIterator.nextString());
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        if ((iCUResourceBundle = iCUResourceBundle2.findTopLevel(locnames[n - 1])) != null) {
            stringArray = new String[iCUResourceBundle.getSize()][];
            for (int i = 0; i < stringArray.length; ++i) {
                stringArray[i] = iCUResourceBundle.get(i).getStringArray();
            }
        }
        this.init(stringBuilder.toString(), stringArray);
    }

    public RuleBasedNumberFormat(int n) {
        this(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RuleBasedNumberFormat)) {
            return true;
        }
        RuleBasedNumberFormat ruleBasedNumberFormat = (RuleBasedNumberFormat)object;
        if (!this.locale.equals(ruleBasedNumberFormat.locale) || this.lenientParse != ruleBasedNumberFormat.lenientParse) {
            return true;
        }
        if (this.ruleSets.length != ruleBasedNumberFormat.ruleSets.length) {
            return true;
        }
        for (int i = 0; i < this.ruleSets.length; ++i) {
            if (this.ruleSets[i].equals(ruleBasedNumberFormat.ruleSets[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (NFRuleSet nFRuleSet : this.ruleSets) {
            stringBuilder.append(nFRuleSet.toString());
        }
        return stringBuilder.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.toString());
        objectOutputStream.writeObject(this.locale);
        objectOutputStream.writeInt(this.roundingMode);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        ULocale uLocale;
        String string = objectInputStream.readUTF();
        try {
            uLocale = (ULocale)objectInputStream.readObject();
        } catch (Exception exception) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        try {
            this.roundingMode = objectInputStream.readInt();
        } catch (Exception exception) {
            // empty catch block
        }
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(string, uLocale);
        this.ruleSets = ruleBasedNumberFormat.ruleSets;
        this.ruleSetsMap = ruleBasedNumberFormat.ruleSetsMap;
        this.defaultRuleSet = ruleBasedNumberFormat.defaultRuleSet;
        this.publicRuleSetNames = ruleBasedNumberFormat.publicRuleSetNames;
        this.decimalFormatSymbols = ruleBasedNumberFormat.decimalFormatSymbols;
        this.decimalFormat = ruleBasedNumberFormat.decimalFormat;
        this.locale = ruleBasedNumberFormat.locale;
        this.defaultInfinityRule = ruleBasedNumberFormat.defaultInfinityRule;
        this.defaultNaNRule = ruleBasedNumberFormat.defaultNaNRule;
    }

    public String[] getRuleSetNames() {
        return (String[])this.publicRuleSetNames.clone();
    }

    public ULocale[] getRuleSetDisplayNameLocales() {
        if (this.ruleSetDisplayNames != null) {
            Set<String> set = this.ruleSetDisplayNames.keySet();
            String[] stringArray = set.toArray(new String[set.size()]);
            Arrays.sort(stringArray, String.CASE_INSENSITIVE_ORDER);
            ULocale[] uLocaleArray = new ULocale[stringArray.length];
            for (int i = 0; i < stringArray.length; ++i) {
                uLocaleArray[i] = new ULocale(stringArray[i]);
            }
            return uLocaleArray;
        }
        return null;
    }

    private String[] getNameListForLocale(ULocale uLocale) {
        if (uLocale != null && this.ruleSetDisplayNames != null) {
            String[] stringArray;
            for (String string : stringArray = new String[]{uLocale.getBaseName(), ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName()}) {
                while (string.length() > 0) {
                    String[] stringArray2 = this.ruleSetDisplayNames.get(string);
                    if (stringArray2 != null) {
                        return stringArray2;
                    }
                    string = ULocale.getFallback(string);
                }
            }
        }
        return null;
    }

    public String[] getRuleSetDisplayNames(ULocale uLocale) {
        String[] stringArray = this.getNameListForLocale(uLocale);
        if (stringArray != null) {
            return (String[])stringArray.clone();
        }
        stringArray = this.getRuleSetNames();
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = stringArray[i].substring(1);
        }
        return stringArray;
    }

    public String[] getRuleSetDisplayNames() {
        return this.getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getRuleSetDisplayName(String string, ULocale uLocale) {
        String[] stringArray = this.publicRuleSetNames;
        for (int i = 0; i < stringArray.length; ++i) {
            if (!stringArray[i].equals(string)) continue;
            String[] stringArray2 = this.getNameListForLocale(uLocale);
            if (stringArray2 != null) {
                return stringArray2[i];
            }
            return stringArray[i].substring(1);
        }
        throw new IllegalArgumentException("unrecognized rule set name: " + string);
    }

    public String getRuleSetDisplayName(String string) {
        return this.getRuleSetDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String format(double d, String string) throws IllegalArgumentException {
        if (string.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.adjustForContext(this.format(d, this.findRuleSet(string)));
    }

    public String format(long l, String string) throws IllegalArgumentException {
        if (string.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.adjustForContext(this.format(l, this.findRuleSet(string)));
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (stringBuffer.length() == 0) {
            stringBuffer.append(this.adjustForContext(this.format(d, this.defaultRuleSet)));
        } else {
            stringBuffer.append(this.format(d, this.defaultRuleSet));
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (stringBuffer.length() == 0) {
            stringBuffer.append(this.adjustForContext(this.format(l, this.defaultRuleSet)));
        } else {
            stringBuffer.append(this.format(l, this.defaultRuleSet));
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(new com.ibm.icu.math.BigDecimal(bigInteger), stringBuffer, fieldPosition);
    }

    @Override
    public StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(new com.ibm.icu.math.BigDecimal(bigDecimal), stringBuffer, fieldPosition);
    }

    @Override
    public StringBuffer format(com.ibm.icu.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (MIN_VALUE.compareTo(bigDecimal) > 0 || MAX_VALUE.compareTo(bigDecimal) < 0) {
            return this.getDecimalFormat().format(bigDecimal, stringBuffer, fieldPosition);
        }
        if (bigDecimal.scale() == 0) {
            return this.format(bigDecimal.longValue(), stringBuffer, fieldPosition);
        }
        return this.format(bigDecimal.doubleValue(), stringBuffer, fieldPosition);
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        String string2 = string.substring(parsePosition.getIndex());
        ParsePosition parsePosition2 = new ParsePosition(0);
        Number number = null;
        Number number2 = NFRule.ZERO;
        ParsePosition parsePosition3 = new ParsePosition(parsePosition2.getIndex());
        for (int i = this.ruleSets.length - 1; i >= 0; --i) {
            if (!this.ruleSets[i].isPublic() || !this.ruleSets[i].isParseable()) continue;
            number = this.ruleSets[i].parse(string2, parsePosition2, Double.MAX_VALUE, 0);
            if (parsePosition2.getIndex() > parsePosition3.getIndex()) {
                number2 = number;
                parsePosition3.setIndex(parsePosition2.getIndex());
            }
            if (parsePosition3.getIndex() == string2.length()) break;
            parsePosition2.setIndex(0);
        }
        parsePosition.setIndex(parsePosition.getIndex() + parsePosition3.getIndex());
        return number2;
    }

    public void setLenientParseMode(boolean bl) {
        this.lenientParse = bl;
    }

    public boolean lenientParseEnabled() {
        return this.lenientParse;
    }

    public void setLenientScannerProvider(RbnfLenientScannerProvider rbnfLenientScannerProvider) {
        this.scannerProvider = rbnfLenientScannerProvider;
    }

    public RbnfLenientScannerProvider getLenientScannerProvider() {
        if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
            try {
                this.lookedForScanner = true;
                Class<?> clazz = Class.forName("com.ibm.icu.impl.text.RbnfScannerProviderImpl");
                RbnfLenientScannerProvider rbnfLenientScannerProvider = (RbnfLenientScannerProvider)clazz.newInstance();
                this.setLenientScannerProvider(rbnfLenientScannerProvider);
            } catch (Exception exception) {
                // empty catch block
            }
        }
        return this.scannerProvider;
    }

    public void setDefaultRuleSet(String string) {
        if (string == null) {
            if (this.publicRuleSetNames.length > 0) {
                this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
            } else {
                this.defaultRuleSet = null;
                int n = this.ruleSets.length;
                while (--n >= 0) {
                    String string2 = this.ruleSets[n].getName();
                    if (!string2.equals("%spellout-numbering") && !string2.equals("%digits-ordinal") && !string2.equals("%duration")) continue;
                    this.defaultRuleSet = this.ruleSets[n];
                    return;
                }
                n = this.ruleSets.length;
                while (--n >= 0) {
                    if (!this.ruleSets[n].isPublic()) continue;
                    this.defaultRuleSet = this.ruleSets[n];
                    break;
                }
            }
        } else {
            if (string.startsWith("%%")) {
                throw new IllegalArgumentException("cannot use private rule set: " + string);
            }
            this.defaultRuleSet = this.findRuleSet(string);
        }
    }

    public String getDefaultRuleSetName() {
        if (this.defaultRuleSet != null && this.defaultRuleSet.isPublic()) {
            return this.defaultRuleSet.getName();
        }
        return "";
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        if (decimalFormatSymbols != null) {
            this.decimalFormatSymbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            if (this.decimalFormat != null) {
                this.decimalFormat.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
            if (this.defaultInfinityRule != null) {
                this.defaultInfinityRule = null;
                this.getDefaultInfinityRule();
            }
            if (this.defaultNaNRule != null) {
                this.defaultNaNRule = null;
                this.getDefaultNaNRule();
            }
            for (NFRuleSet nFRuleSet : this.ruleSets) {
                nFRuleSet.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
        }
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (!(this.capitalizationInfoIsSet || displayContext != DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && displayContext != DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.locale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }

    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }

    @Override
    public void setRoundingMode(int n) {
        if (n < 0 || n > 7) {
            throw new IllegalArgumentException("Invalid rounding mode: " + n);
        }
        this.roundingMode = n;
    }

    NFRuleSet getDefaultRuleSet() {
        return this.defaultRuleSet;
    }

    RbnfLenientScanner getLenientScanner() {
        RbnfLenientScannerProvider rbnfLenientScannerProvider;
        if (this.lenientParse && (rbnfLenientScannerProvider = this.getLenientScannerProvider()) != null) {
            return rbnfLenientScannerProvider.get(this.locale, this.lenientParseRules);
        }
        return null;
    }

    DecimalFormatSymbols getDecimalFormatSymbols() {
        if (this.decimalFormatSymbols == null) {
            this.decimalFormatSymbols = new DecimalFormatSymbols(this.locale);
        }
        return this.decimalFormatSymbols;
    }

    DecimalFormat getDecimalFormat() {
        if (this.decimalFormat == null) {
            String string = RuleBasedNumberFormat.getPattern(this.locale, 0);
            this.decimalFormat = new DecimalFormat(string, this.getDecimalFormatSymbols());
        }
        return this.decimalFormat;
    }

    PluralFormat createPluralFormat(PluralRules.PluralType pluralType, String string) {
        return new PluralFormat(this.locale, pluralType, string, this.getDecimalFormat());
    }

    NFRule getDefaultInfinityRule() {
        if (this.defaultInfinityRule == null) {
            this.defaultInfinityRule = new NFRule(this, "Inf: " + this.getDecimalFormatSymbols().getInfinity());
        }
        return this.defaultInfinityRule;
    }

    NFRule getDefaultNaNRule() {
        if (this.defaultNaNRule == null) {
            this.defaultNaNRule = new NFRule(this, "NaN: " + this.getDecimalFormatSymbols().getNaN());
        }
        return this.defaultNaNRule;
    }

    private String extractSpecial(StringBuilder stringBuilder, String string) {
        String string2 = null;
        int n = stringBuilder.indexOf(string);
        if (n != -1 && (n == 0 || stringBuilder.charAt(n - 1) == ';')) {
            int n2;
            int n3 = stringBuilder.indexOf(";%", n);
            if (n3 == -1) {
                n3 = stringBuilder.length() - 1;
            }
            for (n2 = n + string.length(); n2 < n3 && PatternProps.isWhiteSpace(stringBuilder.charAt(n2)); ++n2) {
            }
            string2 = stringBuilder.substring(n2, n3);
            stringBuilder.delete(n, n3 + 1);
        }
        return string2;
    }

    private void init(String string, String[][] stringArray) {
        int n;
        this.initLocalizations(stringArray);
        StringBuilder stringBuilder = this.stripWhitespace(string);
        this.lenientParseRules = this.extractSpecial(stringBuilder, "%%lenient-parse:");
        this.postProcessRules = this.extractSpecial(stringBuilder, "%%post-process:");
        int n2 = 1;
        int n3 = 0;
        while ((n3 = stringBuilder.indexOf(";%", n3)) != -1) {
            ++n2;
            n3 += 2;
        }
        this.ruleSets = new NFRuleSet[n2];
        this.ruleSetsMap = new HashMap<String, NFRuleSet>(n2 * 2 + 1);
        this.defaultRuleSet = null;
        int n4 = 0;
        String[] stringArray2 = new String[n2];
        int n5 = 0;
        for (int i = 0; i < this.ruleSets.length; ++i) {
            NFRuleSet nFRuleSet;
            n3 = stringBuilder.indexOf(";%", n5);
            if (n3 < 0) {
                n3 = stringBuilder.length() - 1;
            }
            stringArray2[i] = stringBuilder.substring(n5, n3 + 1);
            this.ruleSets[i] = nFRuleSet = new NFRuleSet(this, stringArray2, i);
            String string2 = nFRuleSet.getName();
            this.ruleSetsMap.put(string2, nFRuleSet);
            if (!string2.startsWith("%%")) {
                ++n4;
                if (this.defaultRuleSet == null && string2.equals("%spellout-numbering") || string2.equals("%digits-ordinal") || string2.equals("%duration")) {
                    this.defaultRuleSet = nFRuleSet;
                }
            }
            n5 = n3 + 1;
        }
        if (this.defaultRuleSet == null) {
            for (int i = this.ruleSets.length - 1; i >= 0; --i) {
                if (this.ruleSets[i].getName().startsWith("%%")) continue;
                this.defaultRuleSet = this.ruleSets[i];
                break;
            }
        }
        if (this.defaultRuleSet == null) {
            this.defaultRuleSet = this.ruleSets[this.ruleSets.length - 1];
        }
        for (int i = 0; i < this.ruleSets.length; ++i) {
            this.ruleSets[i].parseRules(stringArray2[i]);
        }
        String[] stringArray3 = new String[n4];
        n4 = 0;
        for (n = this.ruleSets.length - 1; n >= 0; --n) {
            if (this.ruleSets[n].getName().startsWith("%%")) continue;
            stringArray3[n4++] = this.ruleSets[n].getName();
        }
        if (this.publicRuleSetNames != null) {
            block5: for (n = 0; n < this.publicRuleSetNames.length; ++n) {
                String string3 = this.publicRuleSetNames[n];
                for (int i = 0; i < stringArray3.length; ++i) {
                    if (string3.equals(stringArray3[i])) continue block5;
                }
                throw new IllegalArgumentException("did not find public rule set: " + string3);
            }
            this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
        } else {
            this.publicRuleSetNames = stringArray3;
        }
    }

    private void initLocalizations(String[][] stringArray) {
        if (stringArray != null) {
            this.publicRuleSetNames = (String[])stringArray[0].clone();
            HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
            for (int i = 1; i < stringArray.length; ++i) {
                String[] stringArray2 = stringArray[i];
                String string = stringArray2[0];
                String[] stringArray3 = new String[stringArray2.length - 1];
                if (stringArray3.length != this.publicRuleSetNames.length) {
                    throw new IllegalArgumentException("public name length: " + this.publicRuleSetNames.length + " != localized names[" + i + "] length: " + stringArray3.length);
                }
                System.arraycopy(stringArray2, 1, stringArray3, 0, stringArray3.length);
                hashMap.put(string, stringArray3);
            }
            if (!hashMap.isEmpty()) {
                this.ruleSetDisplayNames = hashMap;
            }
        }
    }

    private void initCapitalizationContextInfo(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        try {
            ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.getWithFallback("contextTransforms/number-spellout");
            int[] nArray = iCUResourceBundle2.getIntVector();
            if (nArray.length >= 2) {
                this.capitalizationForListOrMenu = nArray[0] != 0;
                this.capitalizationForStandAlone = nArray[1] != 0;
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    private StringBuilder stripWhitespace(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        int n2 = 0;
        while (n2 < n) {
            while (n2 < n && PatternProps.isWhiteSpace(string.charAt(n2))) {
                ++n2;
            }
            if (n2 < n && string.charAt(n2) == ';') {
                ++n2;
                continue;
            }
            int n3 = string.indexOf(59, n2);
            if (n3 == -1) {
                stringBuilder.append(string.substring(n2));
                break;
            }
            if (n3 >= n) break;
            stringBuilder.append(string.substring(n2, n3 + 1));
            n2 = n3 + 1;
        }
        return stringBuilder;
    }

    private String format(double d, NFRuleSet nFRuleSet) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getRoundingMode() != 7 && !Double.isNaN(d) && !Double.isInfinite(d)) {
            d = new com.ibm.icu.math.BigDecimal(Double.toString(d)).setScale(this.getMaximumFractionDigits(), this.roundingMode).doubleValue();
        }
        nFRuleSet.format(d, stringBuilder, 0, 0);
        this.postProcess(stringBuilder, nFRuleSet);
        return stringBuilder.toString();
    }

    private String format(long l, NFRuleSet nFRuleSet) {
        StringBuilder stringBuilder = new StringBuilder();
        if (l == Long.MIN_VALUE) {
            stringBuilder.append(this.getDecimalFormat().format(Long.MIN_VALUE));
        } else {
            nFRuleSet.format(l, stringBuilder, 0, 0);
        }
        this.postProcess(stringBuilder, nFRuleSet);
        return stringBuilder.toString();
    }

    private void postProcess(StringBuilder stringBuilder, NFRuleSet nFRuleSet) {
        if (this.postProcessRules != null) {
            if (this.postProcessor == null) {
                int n = this.postProcessRules.indexOf(";");
                if (n == -1) {
                    n = this.postProcessRules.length();
                }
                String string = this.postProcessRules.substring(0, n).trim();
                try {
                    Class<?> clazz = Class.forName(string);
                    this.postProcessor = (RBNFPostProcessor)clazz.newInstance();
                    this.postProcessor.init(this, this.postProcessRules);
                } catch (Exception exception) {
                    if (DEBUG) {
                        System.out.println("could not locate " + string + ", error " + exception.getClass().getName() + ", " + exception.getMessage());
                    }
                    this.postProcessor = null;
                    this.postProcessRules = null;
                    return;
                }
            }
            this.postProcessor.process(stringBuilder, nFRuleSet);
        }
    }

    private String adjustForContext(String string) {
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        if (displayContext != DisplayContext.CAPITALIZATION_NONE && string != null && string.length() > 0 && UCharacter.isLowerCase(string.codePointAt(0)) && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)) {
            if (this.capitalizationBrkIter == null) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
            return UCharacter.toTitleCase(this.locale, string, this.capitalizationBrkIter, 768);
        }
        return string;
    }

    NFRuleSet findRuleSet(String string) throws IllegalArgumentException {
        NFRuleSet nFRuleSet = this.ruleSetsMap.get(string);
        if (nFRuleSet == null) {
            throw new IllegalArgumentException("No rule set named " + string);
        }
        return nFRuleSet;
    }
}

