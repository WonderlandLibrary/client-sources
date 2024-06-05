package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.number.*;
import java.text.*;
import com.ibm.icu.impl.*;
import java.io.*;

public class PluralFormat extends UFormat
{
    private static final long serialVersionUID = 1L;
    private ULocale ulocale;
    private PluralRules pluralRules;
    private String pattern;
    private transient MessagePattern msgPattern;
    private Map<String, String> parsedValues;
    private NumberFormat numberFormat;
    private transient double offset;
    private transient PluralSelectorAdapter pluralRulesWrapper;
    
    public PluralFormat() {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }
    
    public PluralFormat(final ULocale ulocale) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
    }
    
    public PluralFormat(final Locale locale) {
        this(ULocale.forLocale(locale));
    }
    
    public PluralFormat(final PluralRules rules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }
    
    public PluralFormat(final ULocale ulocale, final PluralRules rules) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
    }
    
    public PluralFormat(final Locale locale, final PluralRules rules) {
        this(ULocale.forLocale(locale), rules);
    }
    
    public PluralFormat(final ULocale ulocale, final PluralRules.PluralType type) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, type, ulocale, null);
    }
    
    public PluralFormat(final Locale locale, final PluralRules.PluralType type) {
        this(ULocale.forLocale(locale), type);
    }
    
    public PluralFormat(final String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(pattern);
    }
    
    public PluralFormat(final ULocale ulocale, final String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
        this.applyPattern(pattern);
    }
    
    public PluralFormat(final PluralRules rules, final String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(pattern);
    }
    
    public PluralFormat(final ULocale ulocale, final PluralRules rules, final String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, null);
        this.applyPattern(pattern);
    }
    
    public PluralFormat(final ULocale ulocale, final PluralRules.PluralType type, final String pattern) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, type, ulocale, null);
        this.applyPattern(pattern);
    }
    
    PluralFormat(final ULocale ulocale, final PluralRules.PluralType type, final String pattern, final NumberFormat numberFormat) {
        this.ulocale = null;
        this.pluralRules = null;
        this.pattern = null;
        this.parsedValues = null;
        this.numberFormat = null;
        this.offset = 0.0;
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.init(null, type, ulocale, numberFormat);
        this.applyPattern(pattern);
    }
    
    private void init(final PluralRules rules, final PluralRules.PluralType type, final ULocale locale, final NumberFormat numberFormat) {
        this.ulocale = locale;
        this.pluralRules = ((rules == null) ? PluralRules.forLocale(this.ulocale, type) : rules);
        this.resetPattern();
        this.numberFormat = ((numberFormat == null) ? NumberFormat.getInstance(this.ulocale) : numberFormat);
    }
    
    private void resetPattern() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        this.offset = 0.0;
    }
    
    public void applyPattern(final String pattern) {
        this.pattern = pattern;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parsePluralStyle(pattern);
            this.offset = this.msgPattern.getPluralOffset(0);
        }
        catch (RuntimeException e) {
            this.resetPattern();
            throw e;
        }
    }
    
    public String toPattern() {
        return this.pattern;
    }
    
    static int findSubMessage(final MessagePattern pattern, int partIndex, final PluralSelector selector, final Object context, final double number) {
        final int count = pattern.countParts();
        MessagePattern.Part part = pattern.getPart(partIndex);
        double offset;
        if (part.getType().hasNumericValue()) {
            offset = pattern.getNumericValue(part);
            ++partIndex;
        }
        else {
            offset = 0.0;
        }
        String keyword = null;
        boolean haveKeywordMatch = false;
        int msgStart = 0;
        do {
            part = pattern.getPart(partIndex++);
            final MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) {
                break;
            }
            assert type == MessagePattern.Part.Type.ARG_SELECTOR;
            if (pattern.getPartType(partIndex).hasNumericValue()) {
                part = pattern.getPart(partIndex++);
                if (number == pattern.getNumericValue(part)) {
                    return partIndex;
                }
            }
            else if (!haveKeywordMatch) {
                if (pattern.partSubstringMatches(part, "other")) {
                    if (msgStart == 0) {
                        msgStart = partIndex;
                        if (keyword != null && keyword.equals("other")) {
                            haveKeywordMatch = true;
                        }
                    }
                }
                else {
                    if (keyword == null) {
                        keyword = selector.select(context, number - offset);
                        if (msgStart != 0 && keyword.equals("other")) {
                            haveKeywordMatch = true;
                        }
                    }
                    if (!haveKeywordMatch && pattern.partSubstringMatches(part, keyword)) {
                        msgStart = partIndex;
                        haveKeywordMatch = true;
                    }
                }
            }
            partIndex = pattern.getLimitPartIndex(partIndex);
        } while (++partIndex < count);
        return msgStart;
    }
    
    public final String format(final double number) {
        return this.format(number, number);
    }
    
    @Override
    public StringBuffer format(final Object number, final StringBuffer toAppendTo, final FieldPosition pos) {
        if (!(number instanceof Number)) {
            throw new IllegalArgumentException("'" + number + "' is not a Number");
        }
        final Number numberObject = (Number)number;
        toAppendTo.append(this.format(numberObject, numberObject.doubleValue()));
        return toAppendTo;
    }
    
    private String format(final Number numberObject, final double number) {
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            return this.numberFormat.format(numberObject);
        }
        final double numberMinusOffset = number - this.offset;
        String numberString;
        PluralRules.IFixedDecimal dec;
        if (this.numberFormat instanceof DecimalFormat) {
            final LocalizedNumberFormatter f = ((DecimalFormat)this.numberFormat).toNumberFormatter();
            FormattedNumber result;
            if (this.offset == 0.0) {
                result = f.format(numberObject);
            }
            else {
                result = f.format(numberMinusOffset);
            }
            numberString = result.toString();
            dec = result.getFixedDecimal();
        }
        else {
            if (this.offset == 0.0) {
                numberString = this.numberFormat.format(numberObject);
            }
            else {
                numberString = this.numberFormat.format(numberMinusOffset);
            }
            dec = new PluralRules.FixedDecimal(numberMinusOffset);
        }
        int partIndex = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, dec, number);
        StringBuilder result2 = null;
        int prevIndex = this.msgPattern.getPart(partIndex).getLimit();
        int index;
        while (true) {
            final MessagePattern.Part part = this.msgPattern.getPart(++partIndex);
            final MessagePattern.Part.Type type = part.getType();
            index = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                break;
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || (type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode())) {
                if (result2 == null) {
                    result2 = new StringBuilder();
                }
                result2.append(this.pattern, prevIndex, index);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    result2.append(numberString);
                }
                prevIndex = part.getLimit();
            }
            else {
                if (type != MessagePattern.Part.Type.ARG_START) {
                    continue;
                }
                if (result2 == null) {
                    result2 = new StringBuilder();
                }
                result2.append(this.pattern, prevIndex, index);
                prevIndex = index;
                partIndex = this.msgPattern.getLimitPartIndex(partIndex);
                index = this.msgPattern.getPart(partIndex).getLimit();
                MessagePattern.appendReducedApostrophes(this.pattern, prevIndex, index, result2);
                prevIndex = index;
            }
        }
        if (result2 == null) {
            return this.pattern.substring(prevIndex, index);
        }
        return result2.append(this.pattern, prevIndex, index).toString();
    }
    
    public Number parse(final String text, final ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object parseObject(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
    
    String parseType(final String source, final RbnfLenientScanner scanner, final FieldPosition pos) {
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            pos.setBeginIndex(-1);
            pos.setEndIndex(-1);
            return null;
        }
        int partIndex = 0;
        final int count = this.msgPattern.countParts();
        int startingAt = pos.getBeginIndex();
        if (startingAt < 0) {
            startingAt = 0;
        }
        String keyword = null;
        String matchedWord = null;
        int matchedIndex = -1;
        while (partIndex < count) {
            final MessagePattern.Part partSelector = this.msgPattern.getPart(partIndex++);
            if (partSelector.getType() != MessagePattern.Part.Type.ARG_SELECTOR) {
                continue;
            }
            final MessagePattern.Part partStart = this.msgPattern.getPart(partIndex++);
            if (partStart.getType() != MessagePattern.Part.Type.MSG_START) {
                continue;
            }
            final MessagePattern.Part partLimit = this.msgPattern.getPart(partIndex++);
            if (partLimit.getType() != MessagePattern.Part.Type.MSG_LIMIT) {
                continue;
            }
            final String currArg = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
            int currMatchIndex;
            if (scanner != null) {
                final int[] scannerMatchResult = scanner.findText(source, currArg, startingAt);
                currMatchIndex = scannerMatchResult[0];
            }
            else {
                currMatchIndex = source.indexOf(currArg, startingAt);
            }
            if (currMatchIndex < 0 || currMatchIndex < matchedIndex || (matchedWord != null && currArg.length() <= matchedWord.length())) {
                continue;
            }
            matchedIndex = currMatchIndex;
            matchedWord = currArg;
            keyword = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
        }
        if (keyword != null) {
            pos.setBeginIndex(matchedIndex);
            pos.setEndIndex(matchedIndex + matchedWord.length());
            return keyword;
        }
        pos.setBeginIndex(-1);
        pos.setEndIndex(-1);
        return null;
    }
    
    @Deprecated
    public void setLocale(ULocale ulocale) {
        if (ulocale == null) {
            ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.init(null, PluralRules.PluralType.CARDINAL, ulocale, null);
    }
    
    public void setNumberFormat(final NumberFormat format) {
        this.numberFormat = format;
    }
    
    @Override
    public boolean equals(final Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (rhs == null || this.getClass() != rhs.getClass()) {
            return false;
        }
        final PluralFormat pf = (PluralFormat)rhs;
        return Utility.objectEquals(this.ulocale, pf.ulocale) && Utility.objectEquals(this.pluralRules, pf.pluralRules) && Utility.objectEquals(this.msgPattern, pf.msgPattern) && Utility.objectEquals(this.numberFormat, pf.numberFormat);
    }
    
    public boolean equals(final PluralFormat rhs) {
        return this.equals((Object)rhs);
    }
    
    @Override
    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("locale=" + this.ulocale);
        buf.append(", rules='" + this.pluralRules + "'");
        buf.append(", pattern='" + this.pattern + "'");
        buf.append(", format='" + this.numberFormat + "'");
        return buf.toString();
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.parsedValues = null;
        if (this.pattern != null) {
            this.applyPattern(this.pattern);
        }
    }
    
    private final class PluralSelectorAdapter implements PluralSelector
    {
        @Override
        public String select(final Object context, final double number) {
            final PluralRules.IFixedDecimal dec = (PluralRules.IFixedDecimal)context;
            return PluralFormat.this.pluralRules.select(dec);
        }
    }
    
    interface PluralSelector
    {
        String select(final Object p0, final double p1);
    }
}
