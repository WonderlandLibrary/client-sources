/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;

public class PatternTokenizer {
    private UnicodeSet ignorableCharacters = new UnicodeSet();
    private UnicodeSet syntaxCharacters = new UnicodeSet();
    private UnicodeSet extraQuotingCharacters = new UnicodeSet();
    private UnicodeSet escapeCharacters = new UnicodeSet();
    private boolean usingSlash = false;
    private boolean usingQuote = false;
    private transient UnicodeSet needingQuoteCharacters = null;
    private int start;
    private int limit;
    private String pattern;
    public static final char SINGLE_QUOTE = '\'';
    public static final char BACK_SLASH = '\\';
    private static int NO_QUOTE = -1;
    private static int IN_QUOTE = -2;
    public static final int DONE = 0;
    public static final int SYNTAX = 1;
    public static final int LITERAL = 2;
    public static final int BROKEN_QUOTE = 3;
    public static final int BROKEN_ESCAPE = 4;
    public static final int UNKNOWN = 5;
    private static final int AFTER_QUOTE = -1;
    private static final int NONE = 0;
    private static final int START_QUOTE = 1;
    private static final int NORMAL_QUOTE = 2;
    private static final int SLASH_START = 3;
    private static final int HEX = 4;

    public UnicodeSet getIgnorableCharacters() {
        return (UnicodeSet)this.ignorableCharacters.clone();
    }

    public PatternTokenizer setIgnorableCharacters(UnicodeSet unicodeSet) {
        this.ignorableCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public UnicodeSet getSyntaxCharacters() {
        return (UnicodeSet)this.syntaxCharacters.clone();
    }

    public UnicodeSet getExtraQuotingCharacters() {
        return (UnicodeSet)this.extraQuotingCharacters.clone();
    }

    public PatternTokenizer setSyntaxCharacters(UnicodeSet unicodeSet) {
        this.syntaxCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setExtraQuotingCharacters(UnicodeSet unicodeSet) {
        this.extraQuotingCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public UnicodeSet getEscapeCharacters() {
        return (UnicodeSet)this.escapeCharacters.clone();
    }

    public PatternTokenizer setEscapeCharacters(UnicodeSet unicodeSet) {
        this.escapeCharacters = (UnicodeSet)unicodeSet.clone();
        return this;
    }

    public boolean isUsingQuote() {
        return this.usingQuote;
    }

    public PatternTokenizer setUsingQuote(boolean bl) {
        this.usingQuote = bl;
        this.needingQuoteCharacters = null;
        return this;
    }

    public boolean isUsingSlash() {
        return this.usingSlash;
    }

    public PatternTokenizer setUsingSlash(boolean bl) {
        this.usingSlash = bl;
        this.needingQuoteCharacters = null;
        return this;
    }

    public int getLimit() {
        return this.limit;
    }

    public PatternTokenizer setLimit(int n) {
        this.limit = n;
        return this;
    }

    public int getStart() {
        return this.start;
    }

    public PatternTokenizer setStart(int n) {
        this.start = n;
        return this;
    }

    public PatternTokenizer setPattern(CharSequence charSequence) {
        return this.setPattern(charSequence.toString());
    }

    public PatternTokenizer setPattern(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Inconsistent arguments");
        }
        this.start = 0;
        this.limit = string.length();
        this.pattern = string;
        return this;
    }

    public String quoteLiteral(CharSequence charSequence) {
        return this.quoteLiteral(charSequence.toString());
    }

    public String quoteLiteral(String string) {
        int n;
        if (this.needingQuoteCharacters == null) {
            this.needingQuoteCharacters = new UnicodeSet().addAll(this.syntaxCharacters).addAll(this.ignorableCharacters).addAll(this.extraQuotingCharacters);
            if (this.usingSlash) {
                this.needingQuoteCharacters.add(92);
            }
            if (this.usingQuote) {
                this.needingQuoteCharacters.add(39);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = NO_QUOTE;
        for (int i = 0; i < string.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(string, i);
            if (this.escapeCharacters.contains(n)) {
                if (n2 == IN_QUOTE) {
                    stringBuffer.append('\'');
                    n2 = NO_QUOTE;
                }
                this.appendEscaped(stringBuffer, n);
                continue;
            }
            if (this.needingQuoteCharacters.contains(n)) {
                if (n2 == IN_QUOTE) {
                    UTF16.append(stringBuffer, n);
                    if (!this.usingQuote || n != 39) continue;
                    stringBuffer.append('\'');
                    continue;
                }
                if (this.usingSlash) {
                    stringBuffer.append('\\');
                    UTF16.append(stringBuffer, n);
                    continue;
                }
                if (this.usingQuote) {
                    if (n == 39) {
                        stringBuffer.append('\'');
                        stringBuffer.append('\'');
                        continue;
                    }
                    stringBuffer.append('\'');
                    UTF16.append(stringBuffer, n);
                    n2 = IN_QUOTE;
                    continue;
                }
                this.appendEscaped(stringBuffer, n);
                continue;
            }
            if (n2 == IN_QUOTE) {
                stringBuffer.append('\'');
                n2 = NO_QUOTE;
            }
            UTF16.append(stringBuffer, n);
        }
        if (n2 == IN_QUOTE) {
            stringBuffer.append('\'');
        }
        return stringBuffer.toString();
    }

    private void appendEscaped(StringBuffer stringBuffer, int n) {
        if (n <= 65535) {
            stringBuffer.append("\\u").append(Utility.hex(n, 4));
        } else {
            stringBuffer.append("\\U").append(Utility.hex(n, 8));
        }
    }

    public String normalize() {
        int n = this.start;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            stringBuffer2.setLength(0);
            int n2 = this.next(stringBuffer2);
            if (n2 == 0) {
                this.start = n;
                return stringBuffer.toString();
            }
            if (n2 != 1) {
                stringBuffer.append(this.quoteLiteral(stringBuffer2));
                continue;
            }
            stringBuffer.append(stringBuffer2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public int next(StringBuffer stringBuffer) {
        int n;
        if (this.start >= this.limit) {
            return 1;
        }
        int n2 = 5;
        int n3 = 5;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        block21: for (int i = this.start; i < this.limit; i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(this.pattern, i);
            switch (n4) {
                case 3: {
                    switch (n) {
                        case 117: {
                            n4 = 4;
                            n5 = 4;
                            n6 = 0;
                            continue block21;
                        }
                        case 85: {
                            n4 = 4;
                            n5 = 8;
                            n6 = 0;
                            continue block21;
                        }
                        default: {
                            if (this.usingSlash) {
                                UTF16.append(stringBuffer, n);
                                n4 = 0;
                                continue block21;
                            }
                            stringBuffer.append('\\');
                            n4 = 0;
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    n6 <<= 4;
                    n6 += n;
                    switch (n) {
                        case 48: 
                        case 49: 
                        case 50: 
                        case 51: 
                        case 52: 
                        case 53: 
                        case 54: 
                        case 55: 
                        case 56: 
                        case 57: {
                            n6 -= 48;
                            break;
                        }
                        case 97: 
                        case 98: 
                        case 99: 
                        case 100: 
                        case 101: 
                        case 102: {
                            n6 -= 87;
                            break;
                        }
                        case 65: 
                        case 66: 
                        case 67: 
                        case 68: 
                        case 69: 
                        case 70: {
                            n6 -= 55;
                            break;
                        }
                        default: {
                            this.start = i;
                            return 1;
                        }
                    }
                    if (--n5 != 0) continue block21;
                    n4 = 0;
                    UTF16.append(stringBuffer, n6);
                    continue block21;
                }
                case -1: {
                    if (n == n3) {
                        UTF16.append(stringBuffer, n);
                        n4 = 2;
                        continue block21;
                    }
                    n4 = 0;
                    break;
                }
                case 1: {
                    if (n == n3) {
                        UTF16.append(stringBuffer, n);
                        n4 = 0;
                        continue block21;
                    }
                    UTF16.append(stringBuffer, n);
                    n4 = 2;
                    continue block21;
                }
                case 2: {
                    if (n == n3) {
                        n4 = -1;
                        continue block21;
                    }
                    UTF16.append(stringBuffer, n);
                    continue block21;
                }
            }
            if (this.ignorableCharacters.contains(n)) continue;
            if (this.syntaxCharacters.contains(n)) {
                if (n2 == 5) {
                    UTF16.append(stringBuffer, n);
                    this.start = i + UTF16.getCharCount(n);
                    return 0;
                }
                this.start = i;
                return n2;
            }
            n2 = 2;
            if (n == 92) {
                n4 = 3;
                continue;
            }
            if (this.usingQuote && n == 39) {
                n3 = n;
                n4 = 1;
                continue;
            }
            UTF16.append(stringBuffer, n);
        }
        this.start = this.limit;
        switch (n4) {
            case 4: {
                return 4;
            }
            case 3: {
                if (this.usingSlash) {
                    return 4;
                }
                stringBuffer.append('\\');
                return n2;
            }
            case 1: 
            case 2: {
                return 3;
            }
        }
        return n2;
    }
}

