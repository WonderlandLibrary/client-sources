/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.MessagePattern;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PluralFormat
extends UFormat {
    private static final long serialVersionUID = 1L;
    private ULocale ulocale = null;
    private PluralRules pluralRules = null;
    private String pattern = null;
    private transient MessagePattern msgPattern;
    private Map<String, String> parsedValues = null;
    private NumberFormat numberFormat = null;
    private transient double offset = 0.0;
    private transient PluralSelectorAdapter pluralRulesWrapper = new PluralSelectorAdapter(this, null);
    static final boolean $assertionsDisabled = !PluralFormat.class.desiredAssertionStatus();

    public PluralFormat() {
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale uLocale) {
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public PluralFormat(PluralRules pluralRules) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(Locale locale, PluralRules pluralRules) {
        this(ULocale.forLocale(locale), pluralRules);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType) {
        this.init(null, pluralType, uLocale, null);
    }

    public PluralFormat(Locale locale, PluralRules.PluralType pluralType) {
        this(ULocale.forLocale(locale), pluralType);
    }

    public PluralFormat(String string) {
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale, String string) {
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
        this.applyPattern(string);
    }

    public PluralFormat(PluralRules pluralRules, String string) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules, String string) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String string) {
        this.init(null, pluralType, uLocale, null);
        this.applyPattern(string);
    }

    PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String string, NumberFormat numberFormat) {
        this.init(null, pluralType, uLocale, numberFormat);
        this.applyPattern(string);
    }

    private void init(PluralRules pluralRules, PluralRules.PluralType pluralType, ULocale uLocale, NumberFormat numberFormat) {
        this.ulocale = uLocale;
        this.pluralRules = pluralRules == null ? PluralRules.forLocale(this.ulocale, pluralType) : pluralRules;
        this.resetPattern();
        this.numberFormat = numberFormat == null ? NumberFormat.getInstance(this.ulocale) : numberFormat;
    }

    private void resetPattern() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
        this.offset = 0.0;
    }

    public void applyPattern(String string) {
        this.pattern = string;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parsePluralStyle(string);
            this.offset = this.msgPattern.getPluralOffset(0);
        } catch (RuntimeException runtimeException) {
            this.resetPattern();
            throw runtimeException;
        }
    }

    public String toPattern() {
        return this.pattern;
    }

    static int findSubMessage(MessagePattern messagePattern, int n, PluralSelector pluralSelector, Object object, double d) {
        int n2 = messagePattern.countParts();
        MessagePattern.Part part = messagePattern.getPart(n);
        double d2 = part.getType().hasNumericValue() ? messagePattern.getNumericValue(part) : 0.0;
        String string = null;
        boolean bl = false;
        int n3 = 0;
        do {
            int n4 = ++n;
            ++n;
            part = messagePattern.getPart(n4);
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_LIMIT) break;
            if (!$assertionsDisabled && type != MessagePattern.Part.Type.ARG_SELECTOR) {
                throw new AssertionError();
            }
            if (messagePattern.getPartType(n).hasNumericValue()) {
                if (d == messagePattern.getNumericValue(part = messagePattern.getPart(n++))) {
                    return n;
                }
            } else if (!bl) {
                if (messagePattern.partSubstringMatches(part, "other")) {
                    if (n3 == 0) {
                        n3 = n;
                        if (string != null && string.equals("other")) {
                            bl = true;
                        }
                    }
                } else {
                    if (string == null) {
                        string = pluralSelector.select(object, d - d2);
                        if (n3 != 0 && string.equals("other")) {
                            bl = true;
                        }
                    }
                    if (!bl && messagePattern.partSubstringMatches(part, string)) {
                        n3 = n;
                        bl = true;
                    }
                }
            }
            n = messagePattern.getLimitPartIndex(n);
        } while (++n < n2);
        return n3;
    }

    public final String format(double d) {
        return this.format(d, d);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof Number)) {
            throw new IllegalArgumentException("'" + object + "' is not a Number");
        }
        Number number = (Number)object;
        stringBuffer.append(this.format(number, number.doubleValue()));
        return stringBuffer;
    }

    private String format(Number number, double d) {
        PluralRules.IFixedDecimal iFixedDecimal;
        String string;
        CharSequence charSequence;
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            return this.numberFormat.format(number);
        }
        double d2 = d - this.offset;
        if (this.numberFormat instanceof DecimalFormat) {
            LocalizedNumberFormatter localizedNumberFormatter = ((DecimalFormat)this.numberFormat).toNumberFormatter();
            charSequence = this.offset == 0.0 ? localizedNumberFormatter.format(number) : localizedNumberFormatter.format(d2);
            string = ((FormattedNumber)charSequence).toString();
            iFixedDecimal = ((FormattedNumber)charSequence).getFixedDecimal();
        } else {
            string = this.offset == 0.0 ? this.numberFormat.format(number) : this.numberFormat.format(d2);
            iFixedDecimal = new PluralRules.FixedDecimal(d2);
        }
        int n = PluralFormat.findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, iFixedDecimal, d);
        charSequence = null;
        int n2 = this.msgPattern.getPart(n).getLimit();
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(++n);
            MessagePattern.Part.Type type = part.getType();
            int n3 = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                if (charSequence == null) {
                    return this.pattern.substring(n2, n3);
                }
                return ((StringBuilder)charSequence).append(this.pattern, n2, n3).toString();
            }
            if (type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode()) {
                if (charSequence == null) {
                    charSequence = new StringBuilder();
                }
                ((StringBuilder)charSequence).append(this.pattern, n2, n3);
                if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                    ((StringBuilder)charSequence).append(string);
                }
                n2 = part.getLimit();
                continue;
            }
            if (type != MessagePattern.Part.Type.ARG_START) continue;
            if (charSequence == null) {
                charSequence = new StringBuilder();
            }
            ((StringBuilder)charSequence).append(this.pattern, n2, n3);
            n2 = n3;
            n = this.msgPattern.getLimitPartIndex(n);
            n3 = this.msgPattern.getPart(n).getLimit();
            MessagePattern.appendReducedApostrophes(this.pattern, n2, n3, (StringBuilder)charSequence);
            n2 = n3;
        }
    }

    public Number parse(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    String parseType(String string, RbnfLenientScanner rbnfLenientScanner, FieldPosition fieldPosition) {
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            fieldPosition.setBeginIndex(-1);
            fieldPosition.setEndIndex(-1);
            return null;
        }
        int n = 0;
        int n2 = this.msgPattern.countParts();
        int n3 = fieldPosition.getBeginIndex();
        if (n3 < 0) {
            n3 = 0;
        }
        String string2 = null;
        String string3 = null;
        int n4 = -1;
        while (n < n2) {
            int n5;
            MessagePattern.Part part;
            MessagePattern.Part part2;
            MessagePattern.Part part3;
            if ((part3 = this.msgPattern.getPart(n++)).getType() != MessagePattern.Part.Type.ARG_SELECTOR || (part2 = this.msgPattern.getPart(n++)).getType() != MessagePattern.Part.Type.MSG_START || (part = this.msgPattern.getPart(n++)).getType() != MessagePattern.Part.Type.MSG_LIMIT) continue;
            String string4 = this.pattern.substring(part2.getLimit(), part.getIndex());
            if (rbnfLenientScanner != null) {
                int[] nArray = rbnfLenientScanner.findText(string, string4, n3);
                n5 = nArray[0];
            } else {
                n5 = string.indexOf(string4, n3);
            }
            if (n5 < 0 || n5 < n4 || string3 != null && string4.length() <= string3.length()) continue;
            n4 = n5;
            string3 = string4;
            string2 = this.pattern.substring(part2.getLimit(), part.getIndex());
        }
        if (string2 != null) {
            fieldPosition.setBeginIndex(n4);
            fieldPosition.setEndIndex(n4 + string3.length());
            return string2;
        }
        fieldPosition.setBeginIndex(-1);
        fieldPosition.setEndIndex(-1);
        return null;
    }

    @Deprecated
    public void setLocale(ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        PluralFormat pluralFormat = (PluralFormat)object;
        return Objects.equals(this.ulocale, pluralFormat.ulocale) && Objects.equals(this.pluralRules, pluralFormat.pluralRules) && Objects.equals(this.msgPattern, pluralFormat.msgPattern) && Objects.equals(this.numberFormat, pluralFormat.numberFormat);
    }

    public boolean equals(PluralFormat pluralFormat) {
        return this.equals((Object)pluralFormat);
    }

    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("locale=" + this.ulocale);
        stringBuilder.append(", rules='" + this.pluralRules + "'");
        stringBuilder.append(", pattern='" + this.pattern + "'");
        stringBuilder.append(", format='" + this.numberFormat + "'");
        return stringBuilder.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter(this, null);
        this.parsedValues = null;
        if (this.pattern != null) {
            this.applyPattern(this.pattern);
        }
    }

    static PluralRules access$000(PluralFormat pluralFormat) {
        return pluralFormat.pluralRules;
    }

    private final class PluralSelectorAdapter
    implements PluralSelector {
        final PluralFormat this$0;

        private PluralSelectorAdapter(PluralFormat pluralFormat) {
            this.this$0 = pluralFormat;
        }

        @Override
        public String select(Object object, double d) {
            PluralRules.IFixedDecimal iFixedDecimal = (PluralRules.IFixedDecimal)object;
            return PluralFormat.access$000(this.this$0).select(iFixedDecimal);
        }

        PluralSelectorAdapter(PluralFormat pluralFormat, 1 var2_2) {
            this(pluralFormat);
        }
    }

    static interface PluralSelector {
        public String select(Object var1, double var2);
    }
}

