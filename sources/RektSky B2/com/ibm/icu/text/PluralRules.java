package com.ibm.icu.text;

import java.util.regex.*;
import java.text.*;
import com.ibm.icu.util.*;
import java.io.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class PluralRules implements Serializable
{
    static final UnicodeSet ALLOWED_ID;
    @Deprecated
    public static final String CATEGORY_SEPARATOR = ";  ";
    @Deprecated
    public static final String KEYWORD_RULE_SEPARATOR = ": ";
    private static final long serialVersionUID = 1L;
    private final RuleList rules;
    private final transient Set<String> keywords;
    public static final String KEYWORD_ZERO = "zero";
    public static final String KEYWORD_ONE = "one";
    public static final String KEYWORD_TWO = "two";
    public static final String KEYWORD_FEW = "few";
    public static final String KEYWORD_MANY = "many";
    public static final String KEYWORD_OTHER = "other";
    public static final double NO_UNIQUE_VALUE = -0.00123456777;
    private static final Constraint NO_CONSTRAINT;
    private static final Rule DEFAULT_RULE;
    public static final PluralRules DEFAULT;
    static final Pattern AT_SEPARATED;
    static final Pattern OR_SEPARATED;
    static final Pattern AND_SEPARATED;
    static final Pattern COMMA_SEPARATED;
    static final Pattern DOTDOT_SEPARATED;
    static final Pattern TILDE_SEPARATED;
    static final Pattern SEMI_SEPARATED;
    
    public static PluralRules parseDescription(String description) throws ParseException {
        description = description.trim();
        return (description.length() == 0) ? PluralRules.DEFAULT : new PluralRules(parseRuleChain(description));
    }
    
    public static PluralRules createRules(final String description) {
        try {
            return parseDescription(description);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private static Constraint parseConstraint(final String description) throws ParseException {
        Constraint result = null;
        final String[] or_together = PluralRules.OR_SEPARATED.split(description);
        for (int i = 0; i < or_together.length; ++i) {
            Constraint andConstraint = null;
            final String[] and_together = PluralRules.AND_SEPARATED.split(or_together[i]);
            for (int j = 0; j < and_together.length; ++j) {
                Constraint newConstraint = PluralRules.NO_CONSTRAINT;
                final String condition = and_together[j].trim();
                final String[] tokens = SimpleTokenizer.split(condition);
                int mod = 0;
                boolean inRange = true;
                boolean integersOnly = true;
                double lowBound = 9.223372036854776E18;
                double highBound = -9.223372036854776E18;
                long[] vals = null;
                int x = 0;
                String t = tokens[x++];
                boolean hackForCompatibility = false;
                Operand operand;
                try {
                    operand = FixedDecimal.getOperand(t);
                }
                catch (Exception e) {
                    throw unexpected(t, condition);
                }
                if (x < tokens.length) {
                    t = tokens[x++];
                    if ("mod".equals(t) || "%".equals(t)) {
                        mod = Integer.parseInt(tokens[x++]);
                        t = nextToken(tokens, x++, condition);
                    }
                    if ("not".equals(t)) {
                        inRange = !inRange;
                        t = nextToken(tokens, x++, condition);
                        if ("=".equals(t)) {
                            throw unexpected(t, condition);
                        }
                    }
                    else if ("!".equals(t)) {
                        inRange = !inRange;
                        t = nextToken(tokens, x++, condition);
                        if (!"=".equals(t)) {
                            throw unexpected(t, condition);
                        }
                    }
                    if ("is".equals(t) || "in".equals(t) || "=".equals(t)) {
                        hackForCompatibility = "is".equals(t);
                        if (hackForCompatibility && !inRange) {
                            throw unexpected(t, condition);
                        }
                        t = nextToken(tokens, x++, condition);
                    }
                    else {
                        if (!"within".equals(t)) {
                            throw unexpected(t, condition);
                        }
                        integersOnly = false;
                        t = nextToken(tokens, x++, condition);
                    }
                    if ("not".equals(t)) {
                        if (!hackForCompatibility && !inRange) {
                            throw unexpected(t, condition);
                        }
                        inRange = !inRange;
                        t = nextToken(tokens, x++, condition);
                    }
                    final List<Long> valueList = new ArrayList<Long>();
                    while (true) {
                        long high;
                        final long low = high = Long.parseLong(t);
                        if (x < tokens.length) {
                            t = nextToken(tokens, x++, condition);
                            if (t.equals(".")) {
                                t = nextToken(tokens, x++, condition);
                                if (!t.equals(".")) {
                                    throw unexpected(t, condition);
                                }
                                t = nextToken(tokens, x++, condition);
                                high = Long.parseLong(t);
                                if (x < tokens.length) {
                                    t = nextToken(tokens, x++, condition);
                                    if (!t.equals(",")) {
                                        throw unexpected(t, condition);
                                    }
                                }
                            }
                            else if (!t.equals(",")) {
                                throw unexpected(t, condition);
                            }
                        }
                        if (low > high) {
                            throw unexpected(low + "~" + high, condition);
                        }
                        if (mod != 0 && high >= mod) {
                            throw unexpected(high + ">mod=" + mod, condition);
                        }
                        valueList.add(low);
                        valueList.add(high);
                        lowBound = Math.min(lowBound, (double)low);
                        highBound = Math.max(highBound, (double)high);
                        if (x >= tokens.length) {
                            if (t.equals(",")) {
                                throw unexpected(t, condition);
                            }
                            if (valueList.size() == 2) {
                                vals = null;
                            }
                            else {
                                vals = new long[valueList.size()];
                                for (int k = 0; k < vals.length; ++k) {
                                    vals[k] = valueList.get(k);
                                }
                            }
                            if (lowBound != highBound && hackForCompatibility && !inRange) {
                                throw unexpected("is not <range>", condition);
                            }
                            newConstraint = new RangeConstraint(mod, inRange, operand, integersOnly, lowBound, highBound, vals);
                            break;
                        }
                        else {
                            t = nextToken(tokens, x++, condition);
                        }
                    }
                }
                if (andConstraint == null) {
                    andConstraint = newConstraint;
                }
                else {
                    andConstraint = new AndConstraint(andConstraint, newConstraint);
                }
            }
            if (result == null) {
                result = andConstraint;
            }
            else {
                result = new OrConstraint(result, andConstraint);
            }
        }
        return result;
    }
    
    private static ParseException unexpected(final String token, final String context) {
        return new ParseException("unexpected token '" + token + "' in '" + context + "'", -1);
    }
    
    private static String nextToken(final String[] tokens, final int x, final String context) throws ParseException {
        if (x < tokens.length) {
            return tokens[x];
        }
        throw new ParseException("missing token at end of '" + context + "'", -1);
    }
    
    private static Rule parseRule(String description) throws ParseException {
        if (description.length() == 0) {
            return PluralRules.DEFAULT_RULE;
        }
        description = description.toLowerCase(Locale.ENGLISH);
        final int x = description.indexOf(58);
        if (x == -1) {
            throw new ParseException("missing ':' in rule description '" + description + "'", 0);
        }
        final String keyword = description.substring(0, x).trim();
        if (!isValidKeyword(keyword)) {
            throw new ParseException("keyword '" + keyword + " is not valid", 0);
        }
        description = description.substring(x + 1).trim();
        final String[] constraintOrSamples = PluralRules.AT_SEPARATED.split(description);
        final boolean sampleFailure = false;
        FixedDecimalSamples integerSamples = null;
        FixedDecimalSamples decimalSamples = null;
        switch (constraintOrSamples.length) {
            case 1: {
                break;
            }
            case 2: {
                integerSamples = FixedDecimalSamples.parse(constraintOrSamples[1]);
                if (integerSamples.sampleType == SampleType.DECIMAL) {
                    decimalSamples = integerSamples;
                    integerSamples = null;
                    break;
                }
                break;
            }
            case 3: {
                integerSamples = FixedDecimalSamples.parse(constraintOrSamples[1]);
                decimalSamples = FixedDecimalSamples.parse(constraintOrSamples[2]);
                if (integerSamples.sampleType != SampleType.INTEGER || decimalSamples.sampleType != SampleType.DECIMAL) {
                    throw new IllegalArgumentException("Must have @integer then @decimal in " + description);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Too many samples in " + description);
            }
        }
        if (sampleFailure) {
            throw new IllegalArgumentException("Ill-formed samples\u2014'@' characters.");
        }
        final boolean isOther = keyword.equals("other");
        if (isOther != (constraintOrSamples[0].length() == 0)) {
            throw new IllegalArgumentException("The keyword 'other' must have no constraints, just samples.");
        }
        Constraint constraint;
        if (isOther) {
            constraint = PluralRules.NO_CONSTRAINT;
        }
        else {
            constraint = parseConstraint(constraintOrSamples[0]);
        }
        return new Rule(keyword, constraint, integerSamples, decimalSamples);
    }
    
    private static RuleList parseRuleChain(String description) throws ParseException {
        final RuleList result = new RuleList();
        if (description.endsWith(";")) {
            description = description.substring(0, description.length() - 1);
        }
        final String[] rules = PluralRules.SEMI_SEPARATED.split(description);
        for (int i = 0; i < rules.length; ++i) {
            final Rule rule = parseRule(rules[i].trim());
            final RuleList list = result;
            list.hasExplicitBoundingInfo |= (rule.integerSamples != null || rule.decimalSamples != null);
            result.addRule(rule);
        }
        return result.finish();
    }
    
    private static void addRange(final StringBuilder result, final double lb, final double ub, final boolean addSeparator) {
        if (addSeparator) {
            result.append(",");
        }
        if (lb == ub) {
            result.append(format(lb));
        }
        else {
            result.append(format(lb) + ".." + format(ub));
        }
    }
    
    private static String format(final double lb) {
        final long lbi = (long)lb;
        return (lb == lbi) ? String.valueOf(lbi) : String.valueOf(lb);
    }
    
    private boolean addConditional(final Set<IFixedDecimal> toAddTo, final Set<IFixedDecimal> others, final double trial) {
        final IFixedDecimal toAdd = new FixedDecimal(trial);
        boolean added;
        if (!toAddTo.contains(toAdd) && !others.contains(toAdd)) {
            others.add(toAdd);
            added = true;
        }
        else {
            added = false;
        }
        return added;
    }
    
    public static PluralRules forLocale(final ULocale locale) {
        return Factory.getDefaultFactory().forLocale(locale, PluralType.CARDINAL);
    }
    
    public static PluralRules forLocale(final Locale locale) {
        return forLocale(ULocale.forLocale(locale));
    }
    
    public static PluralRules forLocale(final ULocale locale, final PluralType type) {
        return Factory.getDefaultFactory().forLocale(locale, type);
    }
    
    public static PluralRules forLocale(final Locale locale, final PluralType type) {
        return forLocale(ULocale.forLocale(locale), type);
    }
    
    private static boolean isValidKeyword(final String token) {
        return PluralRules.ALLOWED_ID.containsAll(token);
    }
    
    private PluralRules(final RuleList rules) {
        this.rules = rules;
        this.keywords = Collections.unmodifiableSet((Set<? extends String>)rules.getKeywords());
    }
    
    @Deprecated
    @Override
    public int hashCode() {
        return this.rules.hashCode();
    }
    
    public String select(final double number) {
        return this.rules.select(new FixedDecimal(number));
    }
    
    @Deprecated
    public String select(final double number, final int countVisibleFractionDigits, final long fractionaldigits) {
        return this.rules.select(new FixedDecimal(number, countVisibleFractionDigits, fractionaldigits));
    }
    
    @Deprecated
    public String select(final IFixedDecimal number) {
        return this.rules.select(number);
    }
    
    @Deprecated
    public boolean matches(final FixedDecimal sample, final String keyword) {
        return this.rules.select(sample, keyword);
    }
    
    public Set<String> getKeywords() {
        return this.keywords;
    }
    
    public double getUniqueKeywordValue(final String keyword) {
        final Collection<Double> values = this.getAllKeywordValues(keyword);
        if (values != null && values.size() == 1) {
            return values.iterator().next();
        }
        return -0.00123456777;
    }
    
    public Collection<Double> getAllKeywordValues(final String keyword) {
        return this.getAllKeywordValues(keyword, SampleType.INTEGER);
    }
    
    @Deprecated
    public Collection<Double> getAllKeywordValues(final String keyword, final SampleType type) {
        if (!this.isLimited(keyword, type)) {
            return null;
        }
        final Collection<Double> samples = this.getSamples(keyword, type);
        return (samples == null) ? null : Collections.unmodifiableCollection((Collection<? extends Double>)samples);
    }
    
    public Collection<Double> getSamples(final String keyword) {
        return this.getSamples(keyword, SampleType.INTEGER);
    }
    
    @Deprecated
    public Collection<Double> getSamples(final String keyword, final SampleType sampleType) {
        if (!this.keywords.contains(keyword)) {
            return null;
        }
        final Set<Double> result = new TreeSet<Double>();
        if (this.rules.hasExplicitBoundingInfo) {
            final FixedDecimalSamples samples = this.rules.getDecimalSamples(keyword, sampleType);
            return (Collection<Double>)((samples == null) ? Collections.unmodifiableSet((Set<?>)result) : Collections.unmodifiableSet((Set<?>)samples.addSamples(result)));
        }
        final int maxCount = this.isLimited(keyword, sampleType) ? Integer.MAX_VALUE : 20;
        switch (sampleType) {
            case INTEGER: {
                for (int i = 0; i < 200 && this.addSample(keyword, i, maxCount, result); ++i) {}
                this.addSample(keyword, 1000000, maxCount, result);
                break;
            }
            case DECIMAL: {
                for (int i = 0; i < 2000 && this.addSample(keyword, new FixedDecimal(i / 10.0, 1), maxCount, result); ++i) {}
                this.addSample(keyword, new FixedDecimal(1000000.0, 1), maxCount, result);
                break;
            }
        }
        return (Collection<Double>)((result.size() == 0) ? null : Collections.unmodifiableSet((Set<?>)result));
    }
    
    @Deprecated
    public boolean addSample(final String keyword, final Number sample, int maxCount, final Set<Double> result) {
        final String selectedKeyword = (sample instanceof FixedDecimal) ? this.select((IFixedDecimal)sample) : this.select(sample.doubleValue());
        if (selectedKeyword.equals(keyword)) {
            result.add(sample.doubleValue());
            if (--maxCount < 0) {
                return false;
            }
        }
        return true;
    }
    
    @Deprecated
    public FixedDecimalSamples getDecimalSamples(final String keyword, final SampleType sampleType) {
        return this.rules.getDecimalSamples(keyword, sampleType);
    }
    
    public static ULocale[] getAvailableULocales() {
        return Factory.getDefaultFactory().getAvailableULocales();
    }
    
    public static ULocale getFunctionalEquivalent(final ULocale locale, final boolean[] isAvailable) {
        return Factory.getDefaultFactory().getFunctionalEquivalent(locale, isAvailable);
    }
    
    @Override
    public String toString() {
        return this.rules.toString();
    }
    
    @Override
    public boolean equals(final Object rhs) {
        return rhs instanceof PluralRules && this.equals((PluralRules)rhs);
    }
    
    public boolean equals(final PluralRules rhs) {
        return rhs != null && this.toString().equals(rhs.toString());
    }
    
    public KeywordStatus getKeywordStatus(final String keyword, final int offset, final Set<Double> explicits, final Output<Double> uniqueValue) {
        return this.getKeywordStatus(keyword, offset, explicits, uniqueValue, SampleType.INTEGER);
    }
    
    @Deprecated
    public KeywordStatus getKeywordStatus(final String keyword, final int offset, Set<Double> explicits, final Output<Double> uniqueValue, final SampleType sampleType) {
        if (uniqueValue != null) {
            uniqueValue.value = null;
        }
        if (!this.keywords.contains(keyword)) {
            return KeywordStatus.INVALID;
        }
        if (!this.isLimited(keyword, sampleType)) {
            return KeywordStatus.UNBOUNDED;
        }
        final Collection<Double> values = this.getSamples(keyword, sampleType);
        final int originalSize = values.size();
        if (explicits == null) {
            explicits = Collections.emptySet();
        }
        if (originalSize > explicits.size()) {
            if (originalSize == 1) {
                if (uniqueValue != null) {
                    uniqueValue.value = values.iterator().next();
                }
                return KeywordStatus.UNIQUE;
            }
            return KeywordStatus.BOUNDED;
        }
        else {
            final HashSet<Double> subtractedSet = new HashSet<Double>(values);
            for (final Double explicit : explicits) {
                subtractedSet.remove(explicit - offset);
            }
            if (subtractedSet.size() == 0) {
                return KeywordStatus.SUPPRESSED;
            }
            if (uniqueValue != null && subtractedSet.size() == 1) {
                uniqueValue.value = subtractedSet.iterator().next();
            }
            return (originalSize == 1) ? KeywordStatus.UNIQUE : KeywordStatus.BOUNDED;
        }
    }
    
    @Deprecated
    public String getRules(final String keyword) {
        return this.rules.getRules(keyword);
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException {
        throw new NotSerializableException();
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new NotSerializableException();
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new PluralRulesSerialProxy(this.toString());
    }
    
    @Deprecated
    public int compareTo(final PluralRules other) {
        return this.toString().compareTo(other.toString());
    }
    
    @Deprecated
    public Boolean isLimited(final String keyword) {
        return this.rules.isLimited(keyword, SampleType.INTEGER);
    }
    
    @Deprecated
    public boolean isLimited(final String keyword, final SampleType sampleType) {
        return this.rules.isLimited(keyword, sampleType);
    }
    
    @Deprecated
    public boolean computeLimited(final String keyword, final SampleType sampleType) {
        return this.rules.computeLimited(keyword, sampleType);
    }
    
    static {
        ALLOWED_ID = new UnicodeSet("[a-z]").freeze();
        NO_CONSTRAINT = new Constraint() {
            private static final long serialVersionUID = 9163464945387899416L;
            
            @Override
            public boolean isFulfilled(final IFixedDecimal n) {
                return true;
            }
            
            @Override
            public boolean isLimited(final SampleType sampleType) {
                return false;
            }
            
            @Override
            public String toString() {
                return "";
            }
        };
        DEFAULT_RULE = new Rule("other", PluralRules.NO_CONSTRAINT, null, null);
        DEFAULT = new PluralRules(new RuleList().addRule(PluralRules.DEFAULT_RULE));
        AT_SEPARATED = Pattern.compile("\\s*\\Q\\E@\\s*");
        OR_SEPARATED = Pattern.compile("\\s*or\\s*");
        AND_SEPARATED = Pattern.compile("\\s*and\\s*");
        COMMA_SEPARATED = Pattern.compile("\\s*,\\s*");
        DOTDOT_SEPARATED = Pattern.compile("\\s*\\Q..\\E\\s*");
        TILDE_SEPARATED = Pattern.compile("\\s*~\\s*");
        SEMI_SEPARATED = Pattern.compile("\\s*;\\s*");
    }
    
    @Deprecated
    public abstract static class Factory
    {
        @Deprecated
        protected Factory() {
        }
        
        @Deprecated
        public abstract PluralRules forLocale(final ULocale p0, final PluralType p1);
        
        @Deprecated
        public final PluralRules forLocale(final ULocale locale) {
            return this.forLocale(locale, PluralType.CARDINAL);
        }
        
        @Deprecated
        public abstract ULocale[] getAvailableULocales();
        
        @Deprecated
        public abstract ULocale getFunctionalEquivalent(final ULocale p0, final boolean[] p1);
        
        @Deprecated
        public static PluralRulesLoader getDefaultFactory() {
            return PluralRulesLoader.loader;
        }
        
        @Deprecated
        public abstract boolean hasOverride(final ULocale p0);
    }
    
    public enum PluralType
    {
        CARDINAL, 
        ORDINAL;
    }
    
    @Deprecated
    public enum Operand
    {
        @Deprecated
        n, 
        @Deprecated
        i, 
        @Deprecated
        f, 
        @Deprecated
        t, 
        @Deprecated
        v, 
        @Deprecated
        w, 
        @Deprecated
        j;
    }
    
    @Deprecated
    public static class FixedDecimal extends Number implements Comparable<FixedDecimal>, IFixedDecimal
    {
        private static final long serialVersionUID = -4756200506571685661L;
        final double source;
        final int visibleDecimalDigitCount;
        final int visibleDecimalDigitCountWithoutTrailingZeros;
        final long decimalDigits;
        final long decimalDigitsWithoutTrailingZeros;
        final long integerValue;
        final boolean hasIntegerValue;
        final boolean isNegative;
        private final int baseFactor;
        static final long MAX = 1000000000000000000L;
        private static final long MAX_INTEGER_PART = 1000000000L;
        
        @Deprecated
        public double getSource() {
            return this.source;
        }
        
        @Deprecated
        public int getVisibleDecimalDigitCount() {
            return this.visibleDecimalDigitCount;
        }
        
        @Deprecated
        public int getVisibleDecimalDigitCountWithoutTrailingZeros() {
            return this.visibleDecimalDigitCountWithoutTrailingZeros;
        }
        
        @Deprecated
        public long getDecimalDigits() {
            return this.decimalDigits;
        }
        
        @Deprecated
        public long getDecimalDigitsWithoutTrailingZeros() {
            return this.decimalDigitsWithoutTrailingZeros;
        }
        
        @Deprecated
        public long getIntegerValue() {
            return this.integerValue;
        }
        
        @Deprecated
        public boolean isHasIntegerValue() {
            return this.hasIntegerValue;
        }
        
        @Deprecated
        public boolean isNegative() {
            return this.isNegative;
        }
        
        @Deprecated
        public int getBaseFactor() {
            return this.baseFactor;
        }
        
        @Deprecated
        public FixedDecimal(final double n, final int v, final long f) {
            this.isNegative = (n < 0.0);
            this.source = (this.isNegative ? (-n) : n);
            this.visibleDecimalDigitCount = v;
            this.decimalDigits = f;
            this.integerValue = ((n > 1.0E18) ? 1000000000000000000L : ((long)n));
            this.hasIntegerValue = (this.source == this.integerValue);
            if (f == 0L) {
                this.decimalDigitsWithoutTrailingZeros = 0L;
                this.visibleDecimalDigitCountWithoutTrailingZeros = 0;
            }
            else {
                long fdwtz;
                int trimmedCount;
                for (fdwtz = f, trimmedCount = v; fdwtz % 10L == 0L; fdwtz /= 10L, --trimmedCount) {}
                this.decimalDigitsWithoutTrailingZeros = fdwtz;
                this.visibleDecimalDigitCountWithoutTrailingZeros = trimmedCount;
            }
            this.baseFactor = (int)Math.pow(10.0, v);
        }
        
        @Deprecated
        public FixedDecimal(final double n, final int v) {
            this(n, v, getFractionalDigits(n, v));
        }
        
        private static int getFractionalDigits(double n, final int v) {
            if (v == 0) {
                return 0;
            }
            if (n < 0.0) {
                n = -n;
            }
            final int baseFactor = (int)Math.pow(10.0, v);
            final long scaled = Math.round(n * baseFactor);
            return (int)(scaled % baseFactor);
        }
        
        @Deprecated
        public FixedDecimal(final double n) {
            this(n, decimals(n));
        }
        
        @Deprecated
        public FixedDecimal(final long n) {
            this((double)n, 0);
        }
        
        @Deprecated
        public static int decimals(double n) {
            if (Double.isInfinite(n) || Double.isNaN(n)) {
                return 0;
            }
            if (n < 0.0) {
                n = -n;
            }
            if (n == Math.floor(n)) {
                return 0;
            }
            if (n < 1.0E9) {
                final long temp = (long)(n * 1000000.0) % 1000000L;
                int mask = 10;
                for (int digits = 6; digits > 0; --digits) {
                    if (temp % mask != 0L) {
                        return digits;
                    }
                    mask *= 10;
                }
                return 0;
            }
            final String buf = String.format(Locale.ENGLISH, "%1.15e", n);
            final int ePos = buf.lastIndexOf(101);
            int expNumPos = ePos + 1;
            if (buf.charAt(expNumPos) == '+') {
                ++expNumPos;
            }
            final String exponentStr = buf.substring(expNumPos);
            final int exponent = Integer.parseInt(exponentStr);
            int numFractionDigits = ePos - 2 - exponent;
            if (numFractionDigits < 0) {
                return 0;
            }
            for (int i = ePos - 1; numFractionDigits > 0 && buf.charAt(i) == '0'; --numFractionDigits, --i) {}
            return numFractionDigits;
        }
        
        @Deprecated
        public FixedDecimal(final String n) {
            this(Double.parseDouble(n), getVisibleFractionCount(n));
        }
        
        private static int getVisibleFractionCount(String value) {
            value = value.trim();
            final int decimalPos = value.indexOf(46) + 1;
            if (decimalPos == 0) {
                return 0;
            }
            return value.length() - decimalPos;
        }
        
        @Deprecated
        @Override
        public double getPluralOperand(final Operand operand) {
            switch (operand) {
                case n: {
                    return this.source;
                }
                case i: {
                    return (double)this.integerValue;
                }
                case f: {
                    return (double)this.decimalDigits;
                }
                case t: {
                    return (double)this.decimalDigitsWithoutTrailingZeros;
                }
                case v: {
                    return this.visibleDecimalDigitCount;
                }
                case w: {
                    return this.visibleDecimalDigitCountWithoutTrailingZeros;
                }
                default: {
                    return this.source;
                }
            }
        }
        
        @Deprecated
        public static Operand getOperand(final String t) {
            return Operand.valueOf(t);
        }
        
        @Deprecated
        @Override
        public int compareTo(final FixedDecimal other) {
            if (this.integerValue != other.integerValue) {
                return (this.integerValue < other.integerValue) ? -1 : 1;
            }
            if (this.source != other.source) {
                return (this.source < other.source) ? -1 : 1;
            }
            if (this.visibleDecimalDigitCount != other.visibleDecimalDigitCount) {
                return (this.visibleDecimalDigitCount < other.visibleDecimalDigitCount) ? -1 : 1;
            }
            final long diff = this.decimalDigits - other.decimalDigits;
            if (diff != 0L) {
                return (diff < 0L) ? -1 : 1;
            }
            return 0;
        }
        
        @Deprecated
        @Override
        public boolean equals(final Object arg0) {
            if (arg0 == null) {
                return false;
            }
            if (arg0 == this) {
                return true;
            }
            if (!(arg0 instanceof FixedDecimal)) {
                return false;
            }
            final FixedDecimal other = (FixedDecimal)arg0;
            return this.source == other.source && this.visibleDecimalDigitCount == other.visibleDecimalDigitCount && this.decimalDigits == other.decimalDigits;
        }
        
        @Deprecated
        @Override
        public int hashCode() {
            return (int)(this.decimalDigits + 37 * (this.visibleDecimalDigitCount + (int)(37.0 * this.source)));
        }
        
        @Deprecated
        @Override
        public String toString() {
            return String.format("%." + this.visibleDecimalDigitCount + "f", this.source);
        }
        
        @Deprecated
        public boolean hasIntegerValue() {
            return this.hasIntegerValue;
        }
        
        @Deprecated
        @Override
        public int intValue() {
            return (int)this.integerValue;
        }
        
        @Deprecated
        @Override
        public long longValue() {
            return this.integerValue;
        }
        
        @Deprecated
        @Override
        public float floatValue() {
            return (float)this.source;
        }
        
        @Deprecated
        @Override
        public double doubleValue() {
            return this.isNegative ? (-this.source) : this.source;
        }
        
        @Deprecated
        public long getShiftedValue() {
            return this.integerValue * this.baseFactor + this.decimalDigits;
        }
        
        private void writeObject(final ObjectOutputStream out) throws IOException {
            throw new NotSerializableException();
        }
        
        private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
            throw new NotSerializableException();
        }
        
        @Deprecated
        @Override
        public boolean isNaN() {
            return Double.isNaN(this.source);
        }
        
        @Deprecated
        @Override
        public boolean isInfinite() {
            return Double.isInfinite(this.source);
        }
    }
    
    @Deprecated
    public enum SampleType
    {
        @Deprecated
        INTEGER, 
        @Deprecated
        DECIMAL;
    }
    
    @Deprecated
    public static class FixedDecimalRange
    {
        @Deprecated
        public final FixedDecimal start;
        @Deprecated
        public final FixedDecimal end;
        
        @Deprecated
        public FixedDecimalRange(final FixedDecimal start, final FixedDecimal end) {
            if (start.visibleDecimalDigitCount != end.visibleDecimalDigitCount) {
                throw new IllegalArgumentException("Ranges must have the same number of visible decimals: " + start + "~" + end);
            }
            this.start = start;
            this.end = end;
        }
        
        @Deprecated
        @Override
        public String toString() {
            return this.start + ((this.end == this.start) ? "" : ("~" + this.end));
        }
    }
    
    @Deprecated
    public static class FixedDecimalSamples
    {
        @Deprecated
        public final SampleType sampleType;
        @Deprecated
        public final Set<FixedDecimalRange> samples;
        @Deprecated
        public final boolean bounded;
        
        private FixedDecimalSamples(final SampleType sampleType, final Set<FixedDecimalRange> samples, final boolean bounded) {
            this.sampleType = sampleType;
            this.samples = samples;
            this.bounded = bounded;
        }
        
        static FixedDecimalSamples parse(String source) {
            boolean bounded2 = true;
            boolean haveBound = false;
            final Set<FixedDecimalRange> samples2 = new LinkedHashSet<FixedDecimalRange>();
            SampleType sampleType2;
            if (source.startsWith("integer")) {
                sampleType2 = SampleType.INTEGER;
            }
            else {
                if (!source.startsWith("decimal")) {
                    throw new IllegalArgumentException("Samples must start with 'integer' or 'decimal'");
                }
                sampleType2 = SampleType.DECIMAL;
            }
            source = source.substring(7).trim();
            for (final String range : PluralRules.COMMA_SEPARATED.split(source)) {
                if (range.equals("\u2026") || range.equals("...")) {
                    bounded2 = false;
                    haveBound = true;
                }
                else {
                    if (haveBound) {
                        throw new IllegalArgumentException("Can only have \u2026 at the end of samples: " + range);
                    }
                    final String[] rangeParts = PluralRules.TILDE_SEPARATED.split(range);
                    switch (rangeParts.length) {
                        case 1: {
                            final FixedDecimal sample = new FixedDecimal(rangeParts[0]);
                            checkDecimal(sampleType2, sample);
                            samples2.add(new FixedDecimalRange(sample, sample));
                            break;
                        }
                        case 2: {
                            final FixedDecimal start = new FixedDecimal(rangeParts[0]);
                            final FixedDecimal end = new FixedDecimal(rangeParts[1]);
                            checkDecimal(sampleType2, start);
                            checkDecimal(sampleType2, end);
                            samples2.add(new FixedDecimalRange(start, end));
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException("Ill-formed number range: " + range);
                        }
                    }
                }
            }
            return new FixedDecimalSamples(sampleType2, Collections.unmodifiableSet((Set<? extends FixedDecimalRange>)samples2), bounded2);
        }
        
        private static void checkDecimal(final SampleType sampleType2, final FixedDecimal sample) {
            if (sampleType2 == SampleType.INTEGER != (sample.getVisibleDecimalDigitCount() == 0)) {
                throw new IllegalArgumentException("Ill-formed number range: " + sample);
            }
        }
        
        @Deprecated
        public Set<Double> addSamples(final Set<Double> result) {
            for (final FixedDecimalRange item : this.samples) {
                final long startDouble = item.start.getShiftedValue();
                for (long endDouble = item.end.getShiftedValue(), d = startDouble; d <= endDouble; ++d) {
                    result.add(d / (double)item.start.baseFactor);
                }
            }
            return result;
        }
        
        @Deprecated
        @Override
        public String toString() {
            final StringBuilder b = new StringBuilder("@").append(this.sampleType.toString().toLowerCase(Locale.ENGLISH));
            boolean first = true;
            for (final FixedDecimalRange item : this.samples) {
                if (first) {
                    first = false;
                }
                else {
                    b.append(",");
                }
                b.append(' ').append(item);
            }
            if (!this.bounded) {
                b.append(", \u2026");
            }
            return b.toString();
        }
        
        @Deprecated
        public Set<FixedDecimalRange> getSamples() {
            return this.samples;
        }
        
        @Deprecated
        public void getStartEndSamples(final Set<FixedDecimal> target) {
            for (final FixedDecimalRange item : this.samples) {
                target.add(item.start);
                target.add(item.end);
            }
        }
    }
    
    static class SimpleTokenizer
    {
        static final UnicodeSet BREAK_AND_IGNORE;
        static final UnicodeSet BREAK_AND_KEEP;
        
        static String[] split(final String source) {
            int last = -1;
            final List<String> result = new ArrayList<String>();
            for (int i = 0; i < source.length(); ++i) {
                final char ch = source.charAt(i);
                if (SimpleTokenizer.BREAK_AND_IGNORE.contains(ch)) {
                    if (last >= 0) {
                        result.add(source.substring(last, i));
                        last = -1;
                    }
                }
                else if (SimpleTokenizer.BREAK_AND_KEEP.contains(ch)) {
                    if (last >= 0) {
                        result.add(source.substring(last, i));
                    }
                    result.add(source.substring(i, i + 1));
                    last = -1;
                }
                else if (last < 0) {
                    last = i;
                }
            }
            if (last >= 0) {
                result.add(source.substring(last));
            }
            return result.toArray(new String[result.size()]);
        }
        
        static {
            BREAK_AND_IGNORE = new UnicodeSet(new int[] { 9, 10, 12, 13, 32, 32 }).freeze();
            BREAK_AND_KEEP = new UnicodeSet(new int[] { 33, 33, 37, 37, 44, 44, 46, 46, 61, 61 }).freeze();
        }
    }
    
    private static class RangeConstraint implements Constraint, Serializable
    {
        private static final long serialVersionUID = 1L;
        private final int mod;
        private final boolean inRange;
        private final boolean integersOnly;
        private final double lowerBound;
        private final double upperBound;
        private final long[] range_list;
        private final Operand operand;
        
        RangeConstraint(final int mod, final boolean inRange, final Operand operand, final boolean integersOnly, final double lowBound, final double highBound, final long[] vals) {
            this.mod = mod;
            this.inRange = inRange;
            this.integersOnly = integersOnly;
            this.lowerBound = lowBound;
            this.upperBound = highBound;
            this.range_list = vals;
            this.operand = operand;
        }
        
        @Override
        public boolean isFulfilled(final IFixedDecimal number) {
            double n = number.getPluralOperand(this.operand);
            if ((this.integersOnly && n - (long)n != 0.0) || (this.operand == Operand.j && number.getPluralOperand(Operand.v) != 0.0)) {
                return !this.inRange;
            }
            if (this.mod != 0) {
                n %= this.mod;
            }
            boolean test = n >= this.lowerBound && n <= this.upperBound;
            if (test && this.range_list != null) {
                test = false;
                for (int i = 0; !test && i < this.range_list.length; test = (n >= this.range_list[i] && n <= this.range_list[i + 1]), i += 2) {}
            }
            return this.inRange == test;
        }
        
        @Override
        public boolean isLimited(final SampleType sampleType) {
            final boolean valueIsZero = this.lowerBound == this.upperBound && this.lowerBound == 0.0;
            final boolean hasDecimals = (this.operand == Operand.v || this.operand == Operand.w || this.operand == Operand.f || this.operand == Operand.t) && this.inRange != valueIsZero;
            switch (sampleType) {
                case INTEGER: {
                    return hasDecimals || ((this.operand == Operand.n || this.operand == Operand.i || this.operand == Operand.j) && this.mod == 0 && this.inRange);
                }
                case DECIMAL: {
                    return (!hasDecimals || this.operand == Operand.n || this.operand == Operand.j) && (this.integersOnly || this.lowerBound == this.upperBound) && this.mod == 0 && this.inRange;
                }
                default: {
                    return false;
                }
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();
            result.append(this.operand);
            if (this.mod != 0) {
                result.append(" % ").append(this.mod);
            }
            final boolean isList = this.lowerBound != this.upperBound;
            result.append(isList ? (this.integersOnly ? (this.inRange ? " = " : " != ") : (this.inRange ? " within " : " not within ")) : (this.inRange ? " = " : " != "));
            if (this.range_list != null) {
                for (int i = 0; i < this.range_list.length; i += 2) {
                    addRange(result, (double)this.range_list[i], (double)this.range_list[i + 1], i != 0);
                }
            }
            else {
                addRange(result, this.lowerBound, this.upperBound, false);
            }
            return result.toString();
        }
    }
    
    private abstract static class BinaryConstraint implements Constraint, Serializable
    {
        private static final long serialVersionUID = 1L;
        protected final Constraint a;
        protected final Constraint b;
        
        protected BinaryConstraint(final Constraint a, final Constraint b) {
            this.a = a;
            this.b = b;
        }
    }
    
    private static class AndConstraint extends BinaryConstraint
    {
        private static final long serialVersionUID = 7766999779862263523L;
        
        AndConstraint(final Constraint a, final Constraint b) {
            super(a, b);
        }
        
        @Override
        public boolean isFulfilled(final IFixedDecimal n) {
            return this.a.isFulfilled(n) && this.b.isFulfilled(n);
        }
        
        @Override
        public boolean isLimited(final SampleType sampleType) {
            return this.a.isLimited(sampleType) || this.b.isLimited(sampleType);
        }
        
        @Override
        public String toString() {
            return this.a.toString() + " and " + this.b.toString();
        }
    }
    
    private static class OrConstraint extends BinaryConstraint
    {
        private static final long serialVersionUID = 1405488568664762222L;
        
        OrConstraint(final Constraint a, final Constraint b) {
            super(a, b);
        }
        
        @Override
        public boolean isFulfilled(final IFixedDecimal n) {
            return this.a.isFulfilled(n) || this.b.isFulfilled(n);
        }
        
        @Override
        public boolean isLimited(final SampleType sampleType) {
            return this.a.isLimited(sampleType) && this.b.isLimited(sampleType);
        }
        
        @Override
        public String toString() {
            return this.a.toString() + " or " + this.b.toString();
        }
    }
    
    private static class Rule implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private final String keyword;
        private final Constraint constraint;
        private final FixedDecimalSamples integerSamples;
        private final FixedDecimalSamples decimalSamples;
        
        public Rule(final String keyword, final Constraint constraint, final FixedDecimalSamples integerSamples, final FixedDecimalSamples decimalSamples) {
            this.keyword = keyword;
            this.constraint = constraint;
            this.integerSamples = integerSamples;
            this.decimalSamples = decimalSamples;
        }
        
        public Rule and(final Constraint c) {
            return new Rule(this.keyword, new AndConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
        }
        
        public Rule or(final Constraint c) {
            return new Rule(this.keyword, new OrConstraint(this.constraint, c), this.integerSamples, this.decimalSamples);
        }
        
        public String getKeyword() {
            return this.keyword;
        }
        
        public boolean appliesTo(final IFixedDecimal n) {
            return this.constraint.isFulfilled(n);
        }
        
        public boolean isLimited(final SampleType sampleType) {
            return this.constraint.isLimited(sampleType);
        }
        
        @Override
        public String toString() {
            return this.keyword + ": " + this.constraint.toString() + ((this.integerSamples == null) ? "" : (" " + this.integerSamples.toString())) + ((this.decimalSamples == null) ? "" : (" " + this.decimalSamples.toString()));
        }
        
        @Deprecated
        @Override
        public int hashCode() {
            return this.keyword.hashCode() ^ this.constraint.hashCode();
        }
        
        public String getConstraint() {
            return this.constraint.toString();
        }
    }
    
    private static class RuleList implements Serializable
    {
        private boolean hasExplicitBoundingInfo;
        private static final long serialVersionUID = 1L;
        private final List<Rule> rules;
        
        private RuleList() {
            this.hasExplicitBoundingInfo = false;
            this.rules = new ArrayList<Rule>();
        }
        
        public RuleList addRule(final Rule nextRule) {
            final String keyword = nextRule.getKeyword();
            for (final Rule rule : this.rules) {
                if (keyword.equals(rule.getKeyword())) {
                    throw new IllegalArgumentException("Duplicate keyword: " + keyword);
                }
            }
            this.rules.add(nextRule);
            return this;
        }
        
        public RuleList finish() throws ParseException {
            Rule otherRule = null;
            final Iterator<Rule> it = this.rules.iterator();
            while (it.hasNext()) {
                final Rule rule = it.next();
                if ("other".equals(rule.getKeyword())) {
                    otherRule = rule;
                    it.remove();
                }
            }
            if (otherRule == null) {
                otherRule = parseRule("other:");
            }
            this.rules.add(otherRule);
            return this;
        }
        
        private Rule selectRule(final IFixedDecimal n) {
            for (final Rule rule : this.rules) {
                if (rule.appliesTo(n)) {
                    return rule;
                }
            }
            return null;
        }
        
        public String select(final IFixedDecimal n) {
            if (n.isInfinite() || n.isNaN()) {
                return "other";
            }
            final Rule r = this.selectRule(n);
            return r.getKeyword();
        }
        
        public Set<String> getKeywords() {
            final Set<String> result = new LinkedHashSet<String>();
            for (final Rule rule : this.rules) {
                result.add(rule.getKeyword());
            }
            return result;
        }
        
        public boolean isLimited(final String keyword, final SampleType sampleType) {
            if (this.hasExplicitBoundingInfo) {
                final FixedDecimalSamples mySamples = this.getDecimalSamples(keyword, sampleType);
                return mySamples == null || mySamples.bounded;
            }
            return this.computeLimited(keyword, sampleType);
        }
        
        public boolean computeLimited(final String keyword, final SampleType sampleType) {
            boolean result = false;
            for (final Rule rule : this.rules) {
                if (keyword.equals(rule.getKeyword())) {
                    if (!rule.isLimited(sampleType)) {
                        return false;
                    }
                    result = true;
                }
            }
            return result;
        }
        
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            for (final Rule rule : this.rules) {
                if (builder.length() != 0) {
                    builder.append(";  ");
                }
                builder.append(rule);
            }
            return builder.toString();
        }
        
        public String getRules(final String keyword) {
            for (final Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword)) {
                    return rule.getConstraint();
                }
            }
            return null;
        }
        
        public boolean select(final IFixedDecimal sample, final String keyword) {
            for (final Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword) && rule.appliesTo(sample)) {
                    return true;
                }
            }
            return false;
        }
        
        public FixedDecimalSamples getDecimalSamples(final String keyword, final SampleType sampleType) {
            for (final Rule rule : this.rules) {
                if (rule.getKeyword().equals(keyword)) {
                    return (sampleType == SampleType.INTEGER) ? rule.integerSamples : rule.decimalSamples;
                }
            }
            return null;
        }
    }
    
    public enum KeywordStatus
    {
        INVALID, 
        SUPPRESSED, 
        UNIQUE, 
        BOUNDED, 
        UNBOUNDED;
    }
    
    private interface Constraint extends Serializable
    {
        boolean isFulfilled(final IFixedDecimal p0);
        
        boolean isLimited(final SampleType p0);
    }
    
    @Deprecated
    public interface IFixedDecimal
    {
        @Deprecated
        double getPluralOperand(final Operand p0);
        
        @Deprecated
        boolean isNaN();
        
        @Deprecated
        boolean isInfinite();
    }
}
