/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.FormatFactory;
import org.apache.commons.lang3.text.StrMatcher;

public class ExtendedMessageFormat
extends MessageFormat {
    private static final long serialVersionUID = -2362048321261811743L;
    private static final int HASH_SEED = 31;
    private static final String DUMMY_PATTERN = "";
    private static final char START_FMT = ',';
    private static final char END_FE = '}';
    private static final char START_FE = '{';
    private static final char QUOTE = '\'';
    private String toPattern;
    private final Map<String, ? extends FormatFactory> registry;
    static final boolean $assertionsDisabled = !ExtendedMessageFormat.class.desiredAssertionStatus();

    public ExtendedMessageFormat(String string) {
        this(string, Locale.getDefault());
    }

    public ExtendedMessageFormat(String string, Locale locale) {
        this(string, locale, null);
    }

    public ExtendedMessageFormat(String string, Map<String, ? extends FormatFactory> map) {
        this(string, Locale.getDefault(), map);
    }

    public ExtendedMessageFormat(String string, Locale locale, Map<String, ? extends FormatFactory> map) {
        super(DUMMY_PATTERN);
        this.setLocale(locale);
        this.registry = map;
        this.applyPattern(string);
    }

    @Override
    public String toPattern() {
        return this.toPattern;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final void applyPattern(String string) {
        int n;
        if (this.registry == null) {
            super.applyPattern(string);
            this.toPattern = super.toPattern();
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder(string.length());
        ParsePosition parsePosition = new ParsePosition(0);
        char[] cArray = string.toCharArray();
        int n2 = 0;
        block4: while (parsePosition.getIndex() < string.length()) {
            switch (cArray[parsePosition.getIndex()]) {
                case '\'': {
                    this.appendQuotedString(string, parsePosition, stringBuilder);
                    continue block4;
                }
                case '{': {
                    void object2;
                    String string2;
                    ++n2;
                    this.seekNonWs(string, parsePosition);
                    int n3 = parsePosition.getIndex();
                    n = this.readArgumentIndex(string, this.next(parsePosition));
                    stringBuilder.append('{').append(n);
                    this.seekNonWs(string, parsePosition);
                    Object object = null;
                    Object var11_13 = null;
                    if (cArray[parsePosition.getIndex()] == ',' && (object = this.getFormat(string2 = this.parseFormatDescription(string, this.next(parsePosition)))) == null) {
                        stringBuilder.append(',').append(string2);
                    }
                    arrayList.add(object);
                    arrayList2.add((String)(object == null ? null : object2));
                    Validate.isTrue(arrayList.size() == n2);
                    Validate.isTrue(arrayList2.size() == n2);
                    if (cArray[parsePosition.getIndex()] == '}') break;
                    throw new IllegalArgumentException("Unreadable format element at position " + n3);
                }
            }
            stringBuilder.append(cArray[parsePosition.getIndex()]);
            this.next(parsePosition);
        }
        super.applyPattern(stringBuilder.toString());
        this.toPattern = this.insertFormats(super.toPattern(), arrayList2);
        if (this.containsElements(arrayList)) {
            Format[] formatArray = this.getFormats();
            n = 0;
            for (Format format2 : arrayList) {
                if (format2 != null) {
                    formatArray[n] = format2;
                }
                ++n;
            }
            super.setFormats(formatArray);
        }
    }

    @Override
    public void setFormat(int n, Format format2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFormatByArgumentIndex(int n, Format format2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFormats(Format[] formatArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFormatsByArgumentIndex(Format[] formatArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        if (ObjectUtils.notEqual(this.getClass(), object.getClass())) {
            return true;
        }
        ExtendedMessageFormat extendedMessageFormat = (ExtendedMessageFormat)object;
        if (ObjectUtils.notEqual(this.toPattern, extendedMessageFormat.toPattern)) {
            return true;
        }
        return ObjectUtils.notEqual(this.registry, extendedMessageFormat.registry);
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + ObjectUtils.hashCode(this.registry);
        n = 31 * n + ObjectUtils.hashCode(this.toPattern);
        return n;
    }

    private Format getFormat(String string) {
        if (this.registry != null) {
            FormatFactory formatFactory;
            String string2 = string;
            String string3 = null;
            int n = string.indexOf(44);
            if (n > 0) {
                string2 = string.substring(0, n).trim();
                string3 = string.substring(n + 1).trim();
            }
            if ((formatFactory = this.registry.get(string2)) != null) {
                return formatFactory.getFormat(string2, string3, this.getLocale());
            }
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    private int readArgumentIndex(String var1_1, ParsePosition var2_2) {
        var3_3 = var2_2.getIndex();
        this.seekNonWs(var1_1, var2_2);
        var4_4 = new StringBuilder();
        var5_5 = false;
        while (!var5_5 && var2_2.getIndex() < var1_1.length()) {
            var6_6 = var1_1.charAt(var2_2.getIndex());
            if (!Character.isWhitespace(var6_6)) ** GOTO lbl-1000
            this.seekNonWs(var1_1, var2_2);
            var6_6 = var1_1.charAt(var2_2.getIndex());
            if (var6_6 != ',' && var6_6 != '}') {
                var5_5 = true;
            } else lbl-1000:
            // 2 sources

            {
                if ((var6_6 == ',' || var6_6 == '}') && var4_4.length() > 0) {
                    try {
                        return Integer.parseInt(var4_4.toString());
                    } catch (NumberFormatException var7_7) {
                        // empty catch block
                    }
                }
                var5_5 = Character.isDigit(var6_6) == false;
                var4_4.append(var6_6);
            }
            this.next(var2_2);
        }
        if (var5_5) {
            throw new IllegalArgumentException("Invalid format argument index at position " + var3_3 + ": " + var1_1.substring(var3_3, var2_2.getIndex()));
        }
        throw new IllegalArgumentException("Unterminated format element at position " + var3_3);
    }

    private String parseFormatDescription(String string, ParsePosition parsePosition) {
        int n = parsePosition.getIndex();
        this.seekNonWs(string, parsePosition);
        int n2 = parsePosition.getIndex();
        int n3 = 1;
        while (parsePosition.getIndex() < string.length()) {
            switch (string.charAt(parsePosition.getIndex())) {
                case '{': {
                    ++n3;
                    break;
                }
                case '}': {
                    if (--n3 != 0) break;
                    return string.substring(n2, parsePosition.getIndex());
                }
                case '\'': {
                    this.getQuotedString(string, parsePosition);
                    break;
                }
            }
            this.next(parsePosition);
        }
        throw new IllegalArgumentException("Unterminated format element at position " + n);
    }

    private String insertFormats(String string, ArrayList<String> arrayList) {
        if (!this.containsElements(arrayList)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        ParsePosition parsePosition = new ParsePosition(0);
        int n = -1;
        int n2 = 0;
        block5: while (parsePosition.getIndex() < string.length()) {
            char c = string.charAt(parsePosition.getIndex());
            switch (c) {
                case '\'': {
                    this.appendQuotedString(string, parsePosition, stringBuilder);
                    break;
                }
                case '{': {
                    String string2;
                    stringBuilder.append('{').append(this.readArgumentIndex(string, this.next(parsePosition)));
                    if (++n2 != 1 || (string2 = arrayList.get(++n)) == null) continue block5;
                    stringBuilder.append(',').append(string2);
                    break;
                }
                case '}': {
                    --n2;
                }
                default: {
                    stringBuilder.append(c);
                    this.next(parsePosition);
                }
            }
        }
        return stringBuilder.toString();
    }

    private void seekNonWs(String string, ParsePosition parsePosition) {
        int n = 0;
        char[] cArray = string.toCharArray();
        do {
            n = StrMatcher.splitMatcher().isMatch(cArray, parsePosition.getIndex());
            parsePosition.setIndex(parsePosition.getIndex() + n);
        } while (n > 0 && parsePosition.getIndex() < string.length());
    }

    private ParsePosition next(ParsePosition parsePosition) {
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        return parsePosition;
    }

    private StringBuilder appendQuotedString(String string, ParsePosition parsePosition, StringBuilder stringBuilder) {
        if (!$assertionsDisabled && string.toCharArray()[parsePosition.getIndex()] != '\'') {
            throw new AssertionError((Object)"Quoted string must start with quote character");
        }
        if (stringBuilder != null) {
            stringBuilder.append('\'');
        }
        this.next(parsePosition);
        int n = parsePosition.getIndex();
        char[] cArray = string.toCharArray();
        int n2 = n;
        for (int i = parsePosition.getIndex(); i < string.length(); ++i) {
            switch (cArray[parsePosition.getIndex()]) {
                case '\'': {
                    this.next(parsePosition);
                    return stringBuilder == null ? null : stringBuilder.append(cArray, n2, parsePosition.getIndex() - n2);
                }
            }
            this.next(parsePosition);
        }
        throw new IllegalArgumentException("Unterminated quoted string at position " + n);
    }

    private void getQuotedString(String string, ParsePosition parsePosition) {
        this.appendQuotedString(string, parsePosition, null);
    }

    private boolean containsElements(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        for (Object obj : collection) {
            if (obj == null) continue;
            return false;
        }
        return true;
    }
}

