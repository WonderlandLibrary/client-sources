package com.ibm.icu.text;

import com.ibm.icu.math.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;

public class RuleBasedNumberFormat extends NumberFormat
{
    static final long serialVersionUID = -7664252765575395068L;
    public static final int SPELLOUT = 1;
    public static final int ORDINAL = 2;
    public static final int DURATION = 3;
    public static final int NUMBERING_SYSTEM = 4;
    private transient NFRuleSet[] ruleSets;
    private transient Map<String, NFRuleSet> ruleSetsMap;
    private transient NFRuleSet defaultRuleSet;
    private ULocale locale;
    private int roundingMode;
    private transient RbnfLenientScannerProvider scannerProvider;
    private transient boolean lookedForScanner;
    private transient DecimalFormatSymbols decimalFormatSymbols;
    private transient DecimalFormat decimalFormat;
    private transient NFRule defaultInfinityRule;
    private transient NFRule defaultNaNRule;
    private boolean lenientParse;
    private transient String lenientParseRules;
    private transient String postProcessRules;
    private transient RBNFPostProcessor postProcessor;
    private Map<String, String[]> ruleSetDisplayNames;
    private String[] publicRuleSetNames;
    private boolean capitalizationInfoIsSet;
    private boolean capitalizationForListOrMenu;
    private boolean capitalizationForStandAlone;
    private transient BreakIterator capitalizationBrkIter;
    private static final boolean DEBUG;
    private static final String[] rulenames;
    private static final String[] locnames;
    private static final BigDecimal MAX_VALUE;
    private static final BigDecimal MIN_VALUE;
    
    public RuleBasedNumberFormat(final String description) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(description, null);
    }
    
    public RuleBasedNumberFormat(final String description, final String[][] localizations) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(description, localizations);
    }
    
    public RuleBasedNumberFormat(final String description, final Locale locale) {
        this(description, ULocale.forLocale(locale));
    }
    
    public RuleBasedNumberFormat(final String description, final ULocale locale) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        this.init(description, null);
    }
    
    public RuleBasedNumberFormat(final String description, final String[][] localizations, final ULocale locale) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        this.init(description, localizations);
    }
    
    public RuleBasedNumberFormat(final Locale locale, final int format) {
        this(ULocale.forLocale(locale), format);
    }
    
    public RuleBasedNumberFormat(final ULocale locale, final int format) {
        this.ruleSets = null;
        this.ruleSetsMap = null;
        this.defaultRuleSet = null;
        this.locale = null;
        this.roundingMode = 7;
        this.scannerProvider = null;
        this.decimalFormatSymbols = null;
        this.decimalFormat = null;
        this.defaultInfinityRule = null;
        this.defaultNaNRule = null;
        this.lenientParse = false;
        this.capitalizationInfoIsSet = false;
        this.capitalizationForListOrMenu = false;
        this.capitalizationForStandAlone = false;
        this.capitalizationBrkIter = null;
        this.locale = locale;
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/rbnf", locale);
        final ULocale uloc = bundle.getULocale();
        this.setLocale(uloc, uloc);
        final StringBuilder description = new StringBuilder();
        String[][] localizations = null;
        try {
            final ICUResourceBundle rules = bundle.getWithFallback("RBNFRules/" + RuleBasedNumberFormat.rulenames[format - 1]);
            final UResourceBundleIterator it = rules.getIterator();
            while (it.hasNext()) {
                description.append(it.nextString());
            }
        }
        catch (MissingResourceException ex) {}
        final UResourceBundle locNamesBundle = bundle.findTopLevel(RuleBasedNumberFormat.locnames[format - 1]);
        if (locNamesBundle != null) {
            localizations = new String[locNamesBundle.getSize()][];
            for (int i = 0; i < localizations.length; ++i) {
                localizations[i] = locNamesBundle.get(i).getStringArray();
            }
        }
        this.init(description.toString(), localizations);
    }
    
    public RuleBasedNumberFormat(final int format) {
        this(ULocale.getDefault(ULocale.Category.FORMAT), format);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object that) {
        if (!(that instanceof RuleBasedNumberFormat)) {
            return false;
        }
        final RuleBasedNumberFormat that2 = (RuleBasedNumberFormat)that;
        if (!this.locale.equals(that2.locale) || this.lenientParse != that2.lenientParse) {
            return false;
        }
        if (this.ruleSets.length != that2.ruleSets.length) {
            return false;
        }
        for (int i = 0; i < this.ruleSets.length; ++i) {
            if (!this.ruleSets[i].equals(that2.ruleSets[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Deprecated
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (final NFRuleSet ruleSet : this.ruleSets) {
            result.append(ruleSet.toString());
        }
        return result.toString();
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeUTF(this.toString());
        out.writeObject(this.locale);
        out.writeInt(this.roundingMode);
    }
    
    private void readObject(final ObjectInputStream in) throws IOException {
        final String description = in.readUTF();
        ULocale loc;
        try {
            loc = (ULocale)in.readObject();
        }
        catch (Exception e) {
            loc = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        try {
            this.roundingMode = in.readInt();
        }
        catch (Exception ex) {}
        final RuleBasedNumberFormat temp = new RuleBasedNumberFormat(description, loc);
        this.ruleSets = temp.ruleSets;
        this.ruleSetsMap = temp.ruleSetsMap;
        this.defaultRuleSet = temp.defaultRuleSet;
        this.publicRuleSetNames = temp.publicRuleSetNames;
        this.decimalFormatSymbols = temp.decimalFormatSymbols;
        this.decimalFormat = temp.decimalFormat;
        this.locale = temp.locale;
        this.defaultInfinityRule = temp.defaultInfinityRule;
        this.defaultNaNRule = temp.defaultNaNRule;
    }
    
    public String[] getRuleSetNames() {
        return this.publicRuleSetNames.clone();
    }
    
    public ULocale[] getRuleSetDisplayNameLocales() {
        if (this.ruleSetDisplayNames != null) {
            final Set<String> s = this.ruleSetDisplayNames.keySet();
            final String[] locales = s.toArray(new String[s.size()]);
            Arrays.sort(locales, String.CASE_INSENSITIVE_ORDER);
            final ULocale[] result = new ULocale[locales.length];
            for (int i = 0; i < locales.length; ++i) {
                result[i] = new ULocale(locales[i]);
            }
            return result;
        }
        return null;
    }
    
    private String[] getNameListForLocale(final ULocale loc) {
        if (loc != null && this.ruleSetDisplayNames != null) {
            final String[] array;
            final String[] localeNames = array = new String[] { loc.getBaseName(), ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName() };
            for (String lname : array) {
                while (lname.length() > 0) {
                    final String[] names = this.ruleSetDisplayNames.get(lname);
                    if (names != null) {
                        return names;
                    }
                    lname = ULocale.getFallback(lname);
                }
            }
        }
        return null;
    }
    
    public String[] getRuleSetDisplayNames(final ULocale loc) {
        String[] names = this.getNameListForLocale(loc);
        if (names != null) {
            return names.clone();
        }
        names = this.getRuleSetNames();
        for (int i = 0; i < names.length; ++i) {
            names[i] = names[i].substring(1);
        }
        return names;
    }
    
    public String[] getRuleSetDisplayNames() {
        return this.getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String getRuleSetDisplayName(final String ruleSetName, final ULocale loc) {
        final String[] rsnames = this.publicRuleSetNames;
        int ix = 0;
        while (ix < rsnames.length) {
            if (rsnames[ix].equals(ruleSetName)) {
                final String[] names = this.getNameListForLocale(loc);
                if (names != null) {
                    return names[ix];
                }
                return rsnames[ix].substring(1);
            }
            else {
                ++ix;
            }
        }
        throw new IllegalArgumentException("unrecognized rule set name: " + ruleSetName);
    }
    
    public String getRuleSetDisplayName(final String ruleSetName) {
        return this.getRuleSetDisplayName(ruleSetName, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public String format(final double number, final String ruleSet) throws IllegalArgumentException {
        if (ruleSet.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.adjustForContext(this.format(number, this.findRuleSet(ruleSet)));
    }
    
    public String format(final long number, final String ruleSet) throws IllegalArgumentException {
        if (ruleSet.startsWith("%%")) {
            throw new IllegalArgumentException("Can't use internal rule set");
        }
        return this.adjustForContext(this.format(number, this.findRuleSet(ruleSet)));
    }
    
    @Override
    public StringBuffer format(final double number, final StringBuffer toAppendTo, final FieldPosition ignore) {
        if (toAppendTo.length() == 0) {
            toAppendTo.append(this.adjustForContext(this.format(number, this.defaultRuleSet)));
        }
        else {
            toAppendTo.append(this.format(number, this.defaultRuleSet));
        }
        return toAppendTo;
    }
    
    @Override
    public StringBuffer format(final long number, final StringBuffer toAppendTo, final FieldPosition ignore) {
        if (toAppendTo.length() == 0) {
            toAppendTo.append(this.adjustForContext(this.format(number, this.defaultRuleSet)));
        }
        else {
            toAppendTo.append(this.format(number, this.defaultRuleSet));
        }
        return toAppendTo;
    }
    
    @Override
    public StringBuffer format(final BigInteger number, final StringBuffer toAppendTo, final FieldPosition pos) {
        return this.format(new BigDecimal(number), toAppendTo, pos);
    }
    
    @Override
    public StringBuffer format(final java.math.BigDecimal number, final StringBuffer toAppendTo, final FieldPosition pos) {
        return this.format(new BigDecimal(number), toAppendTo, pos);
    }
    
    @Override
    public StringBuffer format(final BigDecimal number, final StringBuffer toAppendTo, final FieldPosition pos) {
        if (RuleBasedNumberFormat.MIN_VALUE.compareTo(number) > 0 || RuleBasedNumberFormat.MAX_VALUE.compareTo(number) < 0) {
            return this.getDecimalFormat().format(number, toAppendTo, pos);
        }
        if (number.scale() == 0) {
            return this.format(number.longValue(), toAppendTo, pos);
        }
        return this.format(number.doubleValue(), toAppendTo, pos);
    }
    
    @Override
    public Number parse(final String text, final ParsePosition parsePosition) {
        final String workingText = text.substring(parsePosition.getIndex());
        final ParsePosition workingPos = new ParsePosition(0);
        Number tempResult = null;
        Number result = NFRule.ZERO;
        final ParsePosition highWaterMark = new ParsePosition(workingPos.getIndex());
        for (int i = this.ruleSets.length - 1; i >= 0; --i) {
            if (this.ruleSets[i].isPublic()) {
                if (this.ruleSets[i].isParseable()) {
                    tempResult = this.ruleSets[i].parse(workingText, workingPos, Double.MAX_VALUE, 0);
                    if (workingPos.getIndex() > highWaterMark.getIndex()) {
                        result = tempResult;
                        highWaterMark.setIndex(workingPos.getIndex());
                    }
                    if (highWaterMark.getIndex() == workingText.length()) {
                        break;
                    }
                    workingPos.setIndex(0);
                }
            }
        }
        parsePosition.setIndex(parsePosition.getIndex() + highWaterMark.getIndex());
        return result;
    }
    
    public void setLenientParseMode(final boolean enabled) {
        this.lenientParse = enabled;
    }
    
    public boolean lenientParseEnabled() {
        return this.lenientParse;
    }
    
    public void setLenientScannerProvider(final RbnfLenientScannerProvider scannerProvider) {
        this.scannerProvider = scannerProvider;
    }
    
    public RbnfLenientScannerProvider getLenientScannerProvider() {
        if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
            try {
                this.lookedForScanner = true;
                final Class<?> cls = Class.forName("com.ibm.icu.impl.text.RbnfScannerProviderImpl");
                final RbnfLenientScannerProvider provider = (RbnfLenientScannerProvider)cls.newInstance();
                this.setLenientScannerProvider(provider);
            }
            catch (Exception ex) {}
        }
        return this.scannerProvider;
    }
    
    public void setDefaultRuleSet(final String ruleSetName) {
        if (ruleSetName == null) {
            if (this.publicRuleSetNames.length > 0) {
                this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
            }
            else {
                this.defaultRuleSet = null;
                int n = this.ruleSets.length;
                while (--n >= 0) {
                    final String currentName = this.ruleSets[n].getName();
                    if (currentName.equals("%spellout-numbering") || currentName.equals("%digits-ordinal") || currentName.equals("%duration")) {
                        this.defaultRuleSet = this.ruleSets[n];
                        return;
                    }
                }
                n = this.ruleSets.length;
                while (--n >= 0) {
                    if (this.ruleSets[n].isPublic()) {
                        this.defaultRuleSet = this.ruleSets[n];
                        break;
                    }
                }
            }
        }
        else {
            if (ruleSetName.startsWith("%%")) {
                throw new IllegalArgumentException("cannot use private rule set: " + ruleSetName);
            }
            this.defaultRuleSet = this.findRuleSet(ruleSetName);
        }
    }
    
    public String getDefaultRuleSetName() {
        if (this.defaultRuleSet != null && this.defaultRuleSet.isPublic()) {
            return this.defaultRuleSet.getName();
        }
        return "";
    }
    
    public void setDecimalFormatSymbols(final DecimalFormatSymbols newSymbols) {
        if (newSymbols != null) {
            this.decimalFormatSymbols = (DecimalFormatSymbols)newSymbols.clone();
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
            for (final NFRuleSet ruleSet : this.ruleSets) {
                ruleSet.setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
        }
    }
    
    @Override
    public void setContext(final DisplayContext context) {
        super.setContext(context);
        if (!this.capitalizationInfoIsSet && (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.locale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu) || (context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone))) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }
    
    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }
    
    @Override
    public void setRoundingMode(final int roundingMode) {
        if (roundingMode < 0 || roundingMode > 7) {
            throw new IllegalArgumentException("Invalid rounding mode: " + roundingMode);
        }
        this.roundingMode = roundingMode;
    }
    
    NFRuleSet getDefaultRuleSet() {
        return this.defaultRuleSet;
    }
    
    RbnfLenientScanner getLenientScanner() {
        if (this.lenientParse) {
            final RbnfLenientScannerProvider provider = this.getLenientScannerProvider();
            if (provider != null) {
                return provider.get(this.locale, this.lenientParseRules);
            }
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
            final String pattern = NumberFormat.getPattern(this.locale, 0);
            this.decimalFormat = new DecimalFormat(pattern, this.getDecimalFormatSymbols());
        }
        return this.decimalFormat;
    }
    
    PluralFormat createPluralFormat(final PluralRules.PluralType pluralType, final String pattern) {
        return new PluralFormat(this.locale, pluralType, pattern, this.getDecimalFormat());
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
    
    private String extractSpecial(final StringBuilder description, final String specialName) {
        String result = null;
        final int lp = description.indexOf(specialName);
        if (lp != -1 && (lp == 0 || description.charAt(lp - 1) == ';')) {
            int lpEnd = description.indexOf(";%", lp);
            if (lpEnd == -1) {
                lpEnd = description.length() - 1;
            }
            int lpStart;
            for (lpStart = lp + specialName.length(); lpStart < lpEnd && PatternProps.isWhiteSpace(description.charAt(lpStart)); ++lpStart) {}
            result = description.substring(lpStart, lpEnd);
            description.delete(lp, lpEnd + 1);
        }
        return result;
    }
    
    private void init(final String description, final String[][] localizations) {
        this.initLocalizations(localizations);
        final StringBuilder descBuf = this.stripWhitespace(description);
        this.lenientParseRules = this.extractSpecial(descBuf, "%%lenient-parse:");
        this.postProcessRules = this.extractSpecial(descBuf, "%%post-process:");
        int numRuleSets = 1;
        for (int p = 0; (p = descBuf.indexOf(";%", p)) != -1; p += 2) {
            ++numRuleSets;
        }
        this.ruleSets = new NFRuleSet[numRuleSets];
        this.ruleSetsMap = new HashMap<String, NFRuleSet>(numRuleSets * 2 + 1);
        this.defaultRuleSet = null;
        int publicRuleSetCount = 0;
        final String[] ruleSetDescriptions = new String[numRuleSets];
        int curRuleSet = 0;
        int start = 0;
        while (curRuleSet < this.ruleSets.length) {
            int p = descBuf.indexOf(";%", start);
            if (p < 0) {
                p = descBuf.length() - 1;
            }
            ruleSetDescriptions[curRuleSet] = descBuf.substring(start, p + 1);
            final NFRuleSet ruleSet = new NFRuleSet(this, ruleSetDescriptions, curRuleSet);
            this.ruleSets[curRuleSet] = ruleSet;
            final String currentName = ruleSet.getName();
            this.ruleSetsMap.put(currentName, ruleSet);
            if (!currentName.startsWith("%%")) {
                ++publicRuleSetCount;
                if ((this.defaultRuleSet == null && currentName.equals("%spellout-numbering")) || currentName.equals("%digits-ordinal") || currentName.equals("%duration")) {
                    this.defaultRuleSet = ruleSet;
                }
            }
            ++curRuleSet;
            start = p + 1;
        }
        if (this.defaultRuleSet == null) {
            for (int i = this.ruleSets.length - 1; i >= 0; --i) {
                if (!this.ruleSets[i].getName().startsWith("%%")) {
                    this.defaultRuleSet = this.ruleSets[i];
                    break;
                }
            }
        }
        if (this.defaultRuleSet == null) {
            this.defaultRuleSet = this.ruleSets[this.ruleSets.length - 1];
        }
        for (int i = 0; i < this.ruleSets.length; ++i) {
            this.ruleSets[i].parseRules(ruleSetDescriptions[i]);
        }
        final String[] publicRuleSetTemp = new String[publicRuleSetCount];
        publicRuleSetCount = 0;
        for (int j = this.ruleSets.length - 1; j >= 0; --j) {
            if (!this.ruleSets[j].getName().startsWith("%%")) {
                publicRuleSetTemp[publicRuleSetCount++] = this.ruleSets[j].getName();
            }
        }
        if (this.publicRuleSetNames != null) {
            int j = 0;
        Label_0462:
            while (j < this.publicRuleSetNames.length) {
                final String name = this.publicRuleSetNames[j];
                for (int k = 0; k < publicRuleSetTemp.length; ++k) {
                    if (name.equals(publicRuleSetTemp[k])) {
                        ++j;
                        continue Label_0462;
                    }
                }
                throw new IllegalArgumentException("did not find public rule set: " + name);
            }
            this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
        }
        else {
            this.publicRuleSetNames = publicRuleSetTemp;
        }
    }
    
    private void initLocalizations(final String[][] localizations) {
        if (localizations != null) {
            this.publicRuleSetNames = localizations[0].clone();
            final Map<String, String[]> m = new HashMap<String, String[]>();
            for (int i = 1; i < localizations.length; ++i) {
                final String[] data = localizations[i];
                final String loc = data[0];
                final String[] names = new String[data.length - 1];
                if (names.length != this.publicRuleSetNames.length) {
                    throw new IllegalArgumentException("public name length: " + this.publicRuleSetNames.length + " != localized names[" + i + "] length: " + names.length);
                }
                System.arraycopy(data, 1, names, 0, names.length);
                m.put(loc, names);
            }
            if (!m.isEmpty()) {
                this.ruleSetDisplayNames = m;
            }
        }
    }
    
    private void initCapitalizationContextInfo(final ULocale theLocale) {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", theLocale);
        try {
            final ICUResourceBundle rdb = rb.getWithFallback("contextTransforms/number-spellout");
            final int[] intVector = rdb.getIntVector();
            if (intVector.length >= 2) {
                this.capitalizationForListOrMenu = (intVector[0] != 0);
                this.capitalizationForStandAlone = (intVector[1] != 0);
            }
        }
        catch (MissingResourceException ex) {}
    }
    
    private StringBuilder stripWhitespace(final String description) {
        final StringBuilder result = new StringBuilder();
        final int descriptionLength = description.length();
        int start = 0;
        while (start < descriptionLength) {
            while (start < descriptionLength && PatternProps.isWhiteSpace(description.charAt(start))) {
                ++start;
            }
            if (start < descriptionLength && description.charAt(start) == ';') {
                ++start;
            }
            else {
                final int p = description.indexOf(59, start);
                if (p == -1) {
                    result.append(description.substring(start));
                    break;
                }
                if (p >= descriptionLength) {
                    break;
                }
                result.append(description.substring(start, p + 1));
                start = p + 1;
            }
        }
        return result;
    }
    
    private String format(double number, final NFRuleSet ruleSet) {
        final StringBuilder result = new StringBuilder();
        if (this.getRoundingMode() != 7 && !Double.isNaN(number) && !Double.isInfinite(number)) {
            number = new BigDecimal(Double.toString(number)).setScale(this.getMaximumFractionDigits(), this.roundingMode).doubleValue();
        }
        ruleSet.format(number, result, 0, 0);
        this.postProcess(result, ruleSet);
        return result.toString();
    }
    
    private String format(final long number, final NFRuleSet ruleSet) {
        final StringBuilder result = new StringBuilder();
        if (number == Long.MIN_VALUE) {
            result.append(this.getDecimalFormat().format(Long.MIN_VALUE));
        }
        else {
            ruleSet.format(number, result, 0, 0);
        }
        this.postProcess(result, ruleSet);
        return result.toString();
    }
    
    private void postProcess(final StringBuilder result, final NFRuleSet ruleSet) {
        if (this.postProcessRules != null) {
            if (this.postProcessor == null) {
                int ix = this.postProcessRules.indexOf(";");
                if (ix == -1) {
                    ix = this.postProcessRules.length();
                }
                final String ppClassName = this.postProcessRules.substring(0, ix).trim();
                try {
                    final Class<?> cls = Class.forName(ppClassName);
                    (this.postProcessor = (RBNFPostProcessor)cls.newInstance()).init(this, this.postProcessRules);
                }
                catch (Exception e) {
                    if (RuleBasedNumberFormat.DEBUG) {
                        System.out.println("could not locate " + ppClassName + ", error " + e.getClass().getName() + ", " + e.getMessage());
                    }
                    this.postProcessor = null;
                    this.postProcessRules = null;
                    return;
                }
            }
            this.postProcessor.process(result, ruleSet);
        }
    }
    
    private String adjustForContext(final String result) {
        final DisplayContext capitalization = this.getContext(DisplayContext.Type.CAPITALIZATION);
        if (capitalization != DisplayContext.CAPITALIZATION_NONE && result != null && result.length() > 0 && UCharacter.isLowerCase(result.codePointAt(0)) && (capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || (capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu) || (capitalization == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone))) {
            if (this.capitalizationBrkIter == null) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
            return UCharacter.toTitleCase(this.locale, result, this.capitalizationBrkIter, 768);
        }
        return result;
    }
    
    NFRuleSet findRuleSet(final String name) throws IllegalArgumentException {
        final NFRuleSet result = this.ruleSetsMap.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No rule set named " + name);
        }
        return result;
    }
    
    static {
        DEBUG = ICUDebug.enabled("rbnf");
        rulenames = new String[] { "SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules" };
        locnames = new String[] { "SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations" };
        MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
        MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);
    }
}
