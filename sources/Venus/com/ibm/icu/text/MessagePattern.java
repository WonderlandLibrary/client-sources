/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.util.ArrayList;
import java.util.Locale;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class MessagePattern
implements Cloneable,
Freezable<MessagePattern> {
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8;
    private static final int MAX_PREFIX_LENGTH = 24;
    private ApostropheMode aposMode;
    private String msg;
    private ArrayList<Part> parts = new ArrayList();
    private ArrayList<Double> numericValues;
    private boolean hasArgNames;
    private boolean hasArgNumbers;
    private boolean needsAutoQuoting;
    private volatile boolean frozen;
    private static final ApostropheMode defaultAposMode;
    private static final ArgType[] argTypes;
    static final boolean $assertionsDisabled;

    public MessagePattern() {
        this.aposMode = defaultAposMode;
    }

    public MessagePattern(ApostropheMode apostropheMode) {
        this.aposMode = apostropheMode;
    }

    public MessagePattern(String string) {
        this.aposMode = defaultAposMode;
        this.parse(string);
    }

    public MessagePattern parse(String string) {
        this.preParse(string);
        this.parseMessage(0, 0, 0, ArgType.NONE);
        this.postParse();
        return this;
    }

    public MessagePattern parseChoiceStyle(String string) {
        this.preParse(string);
        this.parseChoiceStyle(0, 0);
        this.postParse();
        return this;
    }

    public MessagePattern parsePluralStyle(String string) {
        this.preParse(string);
        this.parsePluralOrSelectStyle(ArgType.PLURAL, 0, 0);
        this.postParse();
        return this;
    }

    public MessagePattern parseSelectStyle(String string) {
        this.preParse(string);
        this.parsePluralOrSelectStyle(ArgType.SELECT, 0, 0);
        this.postParse();
        return this;
    }

    public void clear() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
        }
        this.msg = null;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }

    public void clearPatternAndSetApostropheMode(ApostropheMode apostropheMode) {
        this.clear();
        this.aposMode = apostropheMode;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        MessagePattern messagePattern = (MessagePattern)object;
        return this.aposMode.equals((Object)messagePattern.aposMode) && (this.msg == null ? messagePattern.msg == null : this.msg.equals(messagePattern.msg)) && this.parts.equals(messagePattern.parts);
    }

    public int hashCode() {
        return (this.aposMode.hashCode() * 37 + (this.msg != null ? this.msg.hashCode() : 0)) * 37 + this.parts.hashCode();
    }

    public ApostropheMode getApostropheMode() {
        return this.aposMode;
    }

    boolean jdkAposMode() {
        return this.aposMode == ApostropheMode.DOUBLE_REQUIRED;
    }

    public String getPatternString() {
        return this.msg;
    }

    public boolean hasNamedArguments() {
        return this.hasArgNames;
    }

    public boolean hasNumberedArguments() {
        return this.hasArgNumbers;
    }

    public String toString() {
        return this.msg;
    }

    public static int validateArgumentName(String string) {
        if (!PatternProps.isIdentifier(string)) {
            return 1;
        }
        return MessagePattern.parseArgNumber(string, 0, string.length());
    }

    public String autoQuoteApostropheDeep() {
        int n;
        if (!this.needsAutoQuoting) {
            return this.msg;
        }
        StringBuilder stringBuilder = null;
        int n2 = n = this.countParts();
        while (n2 > 0) {
            Part part;
            if ((part = this.getPart(--n2)).getType() != Part.Type.INSERT_CHAR) continue;
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder(this.msg.length() + 10).append(this.msg);
            }
            stringBuilder.insert(Part.access$000(part), (char)Part.access$100(part));
        }
        if (stringBuilder == null) {
            return this.msg;
        }
        return stringBuilder.toString();
    }

    public int countParts() {
        return this.parts.size();
    }

    public Part getPart(int n) {
        return this.parts.get(n);
    }

    public Part.Type getPartType(int n) {
        return Part.access$200(this.parts.get(n));
    }

    public int getPatternIndex(int n) {
        return Part.access$000(this.parts.get(n));
    }

    public String getSubstring(Part part) {
        int n = Part.access$000(part);
        return this.msg.substring(n, n + Part.access$300(part));
    }

    public boolean partSubstringMatches(Part part, String string) {
        return Part.access$300(part) == string.length() && this.msg.regionMatches(Part.access$000(part), string, 0, Part.access$300(part));
    }

    public double getNumericValue(Part part) {
        Part.Type type = Part.access$200(part);
        if (type == Part.Type.ARG_INT) {
            return Part.access$100(part);
        }
        if (type == Part.Type.ARG_DOUBLE) {
            return this.numericValues.get(Part.access$100(part));
        }
        return -1.23456789E8;
    }

    public double getPluralOffset(int n) {
        Part part = this.parts.get(n);
        if (Part.access$200(part).hasNumericValue()) {
            return this.getNumericValue(part);
        }
        return 0.0;
    }

    public int getLimitPartIndex(int n) {
        int n2 = Part.access$400(this.parts.get(n));
        if (n2 < n) {
            return n;
        }
        return n2;
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public MessagePattern cloneAsThawed() {
        MessagePattern messagePattern;
        try {
            messagePattern = (MessagePattern)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
        messagePattern.parts = (ArrayList)this.parts.clone();
        if (this.numericValues != null) {
            messagePattern.numericValues = (ArrayList)this.numericValues.clone();
        }
        messagePattern.frozen = false;
        return messagePattern;
    }

    @Override
    public MessagePattern freeze() {
        this.frozen = true;
        return this;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    private void preParse(String string) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to parse(" + MessagePattern.prefix(string) + ") on frozen MessagePattern instance.");
        }
        this.msg = string;
        this.hasArgNumbers = false;
        this.hasArgNames = false;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }

    private void postParse() {
    }

    private int parseMessage(int n, int n2, int n3, ArgType argType) {
        if (n3 > Short.MAX_VALUE) {
            throw new IndexOutOfBoundsException();
        }
        int n4 = this.parts.size();
        this.addPart(Part.Type.MSG_START, n, n2, n3);
        n += n2;
        block0: while (n < this.msg.length()) {
            char c;
            if ((c = this.msg.charAt(n++)) == '\'') {
                if (n == this.msg.length()) {
                    this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                    this.needsAutoQuoting = true;
                    continue;
                }
                c = this.msg.charAt(n);
                if (c == '\'') {
                    this.addPart(Part.Type.SKIP_SYNTAX, n++, 1, 0);
                    continue;
                }
                if (this.aposMode == ApostropheMode.DOUBLE_REQUIRED || c == '{' || c == '}' || argType == ArgType.CHOICE && c == '|' || argType.hasPluralStyle() && c == '#') {
                    this.addPart(Part.Type.SKIP_SYNTAX, n - 1, 1, 0);
                    while ((n = this.msg.indexOf(39, n + 1)) >= 0) {
                        if (n + 1 < this.msg.length() && this.msg.charAt(n + 1) == '\'') {
                            this.addPart(Part.Type.SKIP_SYNTAX, ++n, 1, 0);
                            continue;
                        }
                        this.addPart(Part.Type.SKIP_SYNTAX, n++, 1, 0);
                        continue block0;
                    }
                    n = this.msg.length();
                    this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                    this.needsAutoQuoting = true;
                    continue;
                }
                this.addPart(Part.Type.INSERT_CHAR, n, 0, 39);
                this.needsAutoQuoting = true;
                continue;
            }
            if (argType.hasPluralStyle() && c == '#') {
                this.addPart(Part.Type.REPLACE_NUMBER, n - 1, 1, 0);
                continue;
            }
            if (c == '{') {
                n = this.parseArg(n - 1, 1, n3);
                continue;
            }
            if ((n3 <= 0 || c != '}') && (argType != ArgType.CHOICE || c != '|')) continue;
            int n5 = argType == ArgType.CHOICE && c == '}' ? 0 : 1;
            this.addLimitPart(n4, Part.Type.MSG_LIMIT, n - 1, n5, n3);
            if (argType == ArgType.CHOICE) {
                return n - 1;
            }
            return n;
        }
        if (n3 > 0 && !this.inTopLevelChoiceMessage(n3, argType)) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
        }
        this.addLimitPart(n4, Part.Type.MSG_LIMIT, n, 0, n3);
        return n;
    }

    private int parseArg(int n, int n2, int n3) {
        ArgType argType;
        int n4;
        block28: {
            int n5;
            int n6;
            int n7;
            int n8;
            block30: {
                block29: {
                    n4 = this.parts.size();
                    argType = ArgType.NONE;
                    this.addPart(Part.Type.ARG_START, n, n2, argType.ordinal());
                    n8 = n = this.skipWhiteSpace(n + n2);
                    if (n == this.msg.length()) {
                        throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
                    }
                    int n9 = this.parseArgNumber(n8, n = this.skipIdentifier(n));
                    if (n9 >= 0) {
                        n7 = n - n8;
                        if (n7 > 65535 || n9 > Short.MAX_VALUE) {
                            throw new IndexOutOfBoundsException("Argument number too large: " + this.prefix(n8));
                        }
                        this.hasArgNumbers = true;
                        this.addPart(Part.Type.ARG_NUMBER, n8, n7, n9);
                    } else if (n9 == -1) {
                        n7 = n - n8;
                        if (n7 > 65535) {
                            throw new IndexOutOfBoundsException("Argument name too long: " + this.prefix(n8));
                        }
                        this.hasArgNames = true;
                        this.addPart(Part.Type.ARG_NAME, n8, n7, 0);
                    } else {
                        throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(n8));
                    }
                    n = this.skipWhiteSpace(n);
                    if (n == this.msg.length()) {
                        throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
                    }
                    n7 = this.msg.charAt(n);
                    if (n7 == 125) break block28;
                    if (n7 != 44) {
                        throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(n8));
                    }
                    n6 = n = this.skipWhiteSpace(n + 1);
                    while (n < this.msg.length() && MessagePattern.isArgTypeChar(this.msg.charAt(n))) {
                        ++n;
                    }
                    n5 = n - n6;
                    if ((n = this.skipWhiteSpace(n)) == this.msg.length()) {
                        throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
                    }
                    if (n5 == 0) break block29;
                    char c = this.msg.charAt(n);
                    n7 = c;
                    if (c == ',' || n7 == 125) break block30;
                }
                throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(n8));
            }
            if (n5 > 65535) {
                throw new IndexOutOfBoundsException("Argument type name too long: " + this.prefix(n8));
            }
            argType = ArgType.SIMPLE;
            if (n5 == 6) {
                if (this.isChoice(n6)) {
                    argType = ArgType.CHOICE;
                } else if (this.isPlural(n6)) {
                    argType = ArgType.PLURAL;
                } else if (this.isSelect(n6)) {
                    argType = ArgType.SELECT;
                }
            } else if (n5 == 13 && this.isSelect(n6) && this.isOrdinal(n6 + 6)) {
                argType = ArgType.SELECTORDINAL;
            }
            Part.access$102(this.parts.get(n4), (short)argType.ordinal());
            if (argType == ArgType.SIMPLE) {
                this.addPart(Part.Type.ARG_TYPE, n6, n5, 0);
            }
            if (n7 == 125) {
                if (argType != ArgType.SIMPLE) {
                    throw new IllegalArgumentException("No style field for complex argument: " + this.prefix(n8));
                }
            } else {
                ++n;
                n = argType == ArgType.SIMPLE ? this.parseSimpleStyle(n) : (argType == ArgType.CHOICE ? this.parseChoiceStyle(n, n3) : this.parsePluralOrSelectStyle(argType, n, n3));
            }
        }
        this.addLimitPart(n4, Part.Type.ARG_LIMIT, n, 1, argType.ordinal());
        return n + 1;
    }

    private int parseSimpleStyle(int n) {
        int n2 = n;
        int n3 = 0;
        while (n < this.msg.length()) {
            int n4;
            char c;
            if ((c = this.msg.charAt(n++)) == '\'') {
                if ((n = this.msg.indexOf(39, n)) < 0) {
                    throw new IllegalArgumentException("Quoted literal argument style text reaches to the end of the message: " + this.prefix(n2));
                }
                ++n;
                continue;
            }
            if (c == '{') {
                ++n3;
                continue;
            }
            if (c != '}') continue;
            if (n3 > 0) {
                --n3;
                continue;
            }
            if ((n4 = --n - n2) > 65535) {
                throw new IndexOutOfBoundsException("Argument style text too long: " + this.prefix(n2));
            }
            this.addPart(Part.Type.ARG_STYLE, n2, n4, 0);
            return n;
        }
        throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
    }

    private int parseChoiceStyle(int n, int n2) {
        int n3 = n;
        if ((n = this.skipWhiteSpace(n)) == this.msg.length() || this.msg.charAt(n) == '}') {
            throw new IllegalArgumentException("Missing choice argument pattern in " + this.prefix());
        }
        while (true) {
            int n4 = n;
            int n5 = (n = this.skipDouble(n)) - n4;
            if (n5 == 0) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
            }
            if (n5 > 65535) {
                throw new IndexOutOfBoundsException("Choice number too long: " + this.prefix(n4));
            }
            this.parseDouble(n4, n, true);
            n = this.skipWhiteSpace(n);
            if (n == this.msg.length()) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
            }
            char c = this.msg.charAt(n);
            if (c != '#' && c != '<' && c != '\u2264') {
                throw new IllegalArgumentException("Expected choice separator (#<\u2264) instead of '" + c + "' in choice pattern " + this.prefix(n3));
            }
            this.addPart(Part.Type.ARG_SELECTOR, n, 1, 0);
            ++n;
            n = this.parseMessage(n, 0, n2 + 1, ArgType.CHOICE);
            if (n == this.msg.length()) {
                return n;
            }
            if (this.msg.charAt(n) == '}') {
                if (!this.inMessageFormatPattern(n2)) {
                    throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
                }
                return n;
            }
            n = this.skipWhiteSpace(n + 1);
        }
    }

    private int parsePluralOrSelectStyle(ArgType argType, int n, int n2) {
        int n3 = n;
        boolean bl = true;
        boolean bl2 = false;
        while (true) {
            int n4;
            boolean bl3;
            boolean bl4 = bl3 = (n = this.skipWhiteSpace(n)) == this.msg.length();
            if (bl3 || this.msg.charAt(n) == '}') {
                if (bl3 == this.inMessageFormatPattern(n2)) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                }
                if (!bl2) {
                    throw new IllegalArgumentException("Missing 'other' keyword in " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern in " + this.prefix());
                }
                return n;
            }
            int n5 = n;
            if (argType.hasPluralStyle() && this.msg.charAt(n5) == '=') {
                n4 = (n = this.skipDouble(n + 1)) - n5;
                if (n4 == 1) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                }
                if (n4 > 65535) {
                    throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(n5));
                }
                this.addPart(Part.Type.ARG_SELECTOR, n5, n4, 0);
                this.parseDouble(n5 + 1, n, false);
            } else {
                n4 = (n = this.skipIdentifier(n)) - n5;
                if (n4 == 0) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                }
                if (argType.hasPluralStyle() && n4 == 6 && n < this.msg.length() && this.msg.regionMatches(n5, "offset:", 0, 0)) {
                    if (!bl) {
                        throw new IllegalArgumentException("Plural argument 'offset:' (if present) must precede key-message pairs: " + this.prefix(n3));
                    }
                    int n6 = this.skipWhiteSpace(n + 1);
                    if ((n = this.skipDouble(n6)) == n6) {
                        throw new IllegalArgumentException("Missing value for plural 'offset:' " + this.prefix(n3));
                    }
                    if (n - n6 > 65535) {
                        throw new IndexOutOfBoundsException("Plural offset value too long: " + this.prefix(n6));
                    }
                    this.parseDouble(n6, n, false);
                    bl = false;
                    continue;
                }
                if (n4 > 65535) {
                    throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(n5));
                }
                this.addPart(Part.Type.ARG_SELECTOR, n5, n4, 0);
                if (this.msg.regionMatches(n5, "other", 0, n4)) {
                    bl2 = true;
                }
            }
            n = this.skipWhiteSpace(n);
            if (n == this.msg.length() || this.msg.charAt(n) != '{') {
                throw new IllegalArgumentException("No message fragment after " + argType.toString().toLowerCase(Locale.ENGLISH) + " selector: " + this.prefix(n5));
            }
            n = this.parseMessage(n, 1, n2 + 1, argType);
            bl = false;
        }
    }

    private static int parseArgNumber(CharSequence charSequence, int n, int n2) {
        boolean bl;
        int n3;
        char c;
        if (n >= n2) {
            return 1;
        }
        if ((c = charSequence.charAt(n++)) == '0') {
            if (n == n2) {
                return 1;
            }
            n3 = 0;
            bl = true;
        } else if ('1' <= c && c <= '9') {
            n3 = c - 48;
            bl = false;
        } else {
            return 1;
        }
        while (n < n2) {
            if ('0' <= (c = charSequence.charAt(n++)) && c <= '9') {
                if (n3 >= 0xCCCCCCC) {
                    bl = true;
                }
                n3 = n3 * 10 + (c - 48);
                continue;
            }
            return 1;
        }
        if (bl) {
            return 1;
        }
        return n3;
    }

    private int parseArgNumber(int n, int n2) {
        return MessagePattern.parseArgNumber(this.msg, n, n2);
    }

    private void parseDouble(int n, int n2, boolean bl) {
        block10: {
            char c;
            int n3;
            int n4;
            int n5;
            block11: {
                block9: {
                    if (!$assertionsDisabled && n >= n2) {
                        throw new AssertionError();
                    }
                    n5 = 0;
                    n4 = 0;
                    n3 = n;
                    if ((c = this.msg.charAt(n3++)) != '-') break block9;
                    n4 = 1;
                    if (n3 == n2) break block10;
                    c = this.msg.charAt(n3++);
                    break block11;
                }
                if (c != '+') break block11;
                if (n3 == n2) break block10;
                c = this.msg.charAt(n3++);
            }
            if (c == '\u221e') {
                if (bl && n3 == n2) {
                    this.addArgDoublePart(n4 != 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, n, n2 - n);
                    return;
                }
            } else {
                while ('0' <= c && c <= '9' && (n5 = n5 * 10 + (c - 48)) <= Short.MAX_VALUE + n4) {
                    if (n3 == n2) {
                        this.addPart(Part.Type.ARG_INT, n, n2 - n, n4 != 0 ? -n5 : n5);
                        return;
                    }
                    c = this.msg.charAt(n3++);
                }
                double d = Double.parseDouble(this.msg.substring(n, n2));
                this.addArgDoublePart(d, n, n2 - n);
                return;
            }
        }
        throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(n, n2));
    }

    static void appendReducedApostrophes(String string, int n, int n2, StringBuilder stringBuilder) {
        int n3 = -1;
        while (true) {
            int n4;
            if ((n4 = string.indexOf(39, n)) < 0 || n4 >= n2) break;
            if (n4 == n3) {
                stringBuilder.append('\'');
                ++n;
                n3 = -1;
                continue;
            }
            stringBuilder.append(string, n, n4);
            n3 = n = n4 + 1;
        }
        stringBuilder.append(string, n, n2);
    }

    private int skipWhiteSpace(int n) {
        return PatternProps.skipWhiteSpace(this.msg, n);
    }

    private int skipIdentifier(int n) {
        return PatternProps.skipIdentifier(this.msg, n);
    }

    private int skipDouble(int n) {
        char c;
        while (!(n >= this.msg.length() || (c = this.msg.charAt(n)) < '0' && "+-.".indexOf(c) < 0 || c > '9' && c != 'e' && c != 'E' && c != '\u221e')) {
            ++n;
        }
        return n;
    }

    private static boolean isArgTypeChar(int n) {
        return 97 <= n && n <= 122 || 65 <= n && n <= 90;
    }

    private boolean isChoice(int n) {
        char c;
        return !((c = this.msg.charAt(n++)) != 'c' && c != 'C' || (c = this.msg.charAt(n++)) != 'h' && c != 'H' || (c = this.msg.charAt(n++)) != 'o' && c != 'O' || (c = this.msg.charAt(n++)) != 'i' && c != 'I' || (c = this.msg.charAt(n++)) != 'c' && c != 'C' || (c = this.msg.charAt(n)) != 'e' && c != 'E');
    }

    private boolean isPlural(int n) {
        char c;
        return !((c = this.msg.charAt(n++)) != 'p' && c != 'P' || (c = this.msg.charAt(n++)) != 'l' && c != 'L' || (c = this.msg.charAt(n++)) != 'u' && c != 'U' || (c = this.msg.charAt(n++)) != 'r' && c != 'R' || (c = this.msg.charAt(n++)) != 'a' && c != 'A' || (c = this.msg.charAt(n)) != 'l' && c != 'L');
    }

    private boolean isSelect(int n) {
        char c;
        return !((c = this.msg.charAt(n++)) != 's' && c != 'S' || (c = this.msg.charAt(n++)) != 'e' && c != 'E' || (c = this.msg.charAt(n++)) != 'l' && c != 'L' || (c = this.msg.charAt(n++)) != 'e' && c != 'E' || (c = this.msg.charAt(n++)) != 'c' && c != 'C' || (c = this.msg.charAt(n)) != 't' && c != 'T');
    }

    private boolean isOrdinal(int n) {
        char c;
        return !((c = this.msg.charAt(n++)) != 'o' && c != 'O' || (c = this.msg.charAt(n++)) != 'r' && c != 'R' || (c = this.msg.charAt(n++)) != 'd' && c != 'D' || (c = this.msg.charAt(n++)) != 'i' && c != 'I' || (c = this.msg.charAt(n++)) != 'n' && c != 'N' || (c = this.msg.charAt(n++)) != 'a' && c != 'A' || (c = this.msg.charAt(n)) != 'l' && c != 'L');
    }

    private boolean inMessageFormatPattern(int n) {
        return n > 0 || Part.access$200(this.parts.get(0)) == Part.Type.MSG_START;
    }

    private boolean inTopLevelChoiceMessage(int n, ArgType argType) {
        return n == 1 && argType == ArgType.CHOICE && Part.access$200(this.parts.get(0)) != Part.Type.MSG_START;
    }

    private void addPart(Part.Type type, int n, int n2, int n3) {
        this.parts.add(new Part(type, n, n2, n3, null));
    }

    private void addLimitPart(int n, Part.Type type, int n2, int n3, int n4) {
        Part.access$402(this.parts.get(n), this.parts.size());
        this.addPart(type, n2, n3, n4);
    }

    private void addArgDoublePart(double d, int n, int n2) {
        int n3;
        if (this.numericValues == null) {
            this.numericValues = new ArrayList();
            n3 = 0;
        } else {
            n3 = this.numericValues.size();
            if (n3 > Short.MAX_VALUE) {
                throw new IndexOutOfBoundsException("Too many numeric values");
            }
        }
        this.numericValues.add(d);
        this.addPart(Part.Type.ARG_DOUBLE, n, n2, n3);
    }

    private static String prefix(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder(44);
        if (n == 0) {
            stringBuilder.append("\"");
        } else {
            stringBuilder.append("[at pattern index ").append(n).append("] \"");
        }
        int n2 = string.length() - n;
        if (n2 <= 24) {
            stringBuilder.append(n == 0 ? string : string.substring(n));
        } else {
            int n3 = n + 24 - 4;
            if (Character.isHighSurrogate(string.charAt(n3 - 1))) {
                --n3;
            }
            stringBuilder.append(string, n, n3).append(" ...");
        }
        return stringBuilder.append("\"").toString();
    }

    private static String prefix(String string) {
        return MessagePattern.prefix(string, 0);
    }

    private String prefix(int n) {
        return MessagePattern.prefix(this.msg, n);
    }

    private String prefix() {
        return MessagePattern.prefix(this.msg, 0);
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static ArgType[] access$500() {
        return argTypes;
    }

    static {
        $assertionsDisabled = !MessagePattern.class.desiredAssertionStatus();
        defaultAposMode = ApostropheMode.valueOf(ICUConfig.get("com.ibm.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL"));
        argTypes = ArgType.values();
    }

    public static enum ArgType {
        NONE,
        SIMPLE,
        CHOICE,
        PLURAL,
        SELECT,
        SELECTORDINAL;


        public boolean hasPluralStyle() {
            return this == PLURAL || this == SELECTORDINAL;
        }
    }

    public static final class Part {
        private static final int MAX_LENGTH = 65535;
        private static final int MAX_VALUE = Short.MAX_VALUE;
        private final Type type;
        private final int index;
        private final char length;
        private short value;
        private int limitPartIndex;

        private Part(Type type, int n, int n2, int n3) {
            this.type = type;
            this.index = n;
            this.length = (char)n2;
            this.value = (short)n3;
        }

        public Type getType() {
            return this.type;
        }

        public int getIndex() {
            return this.index;
        }

        public int getLength() {
            return this.length;
        }

        public int getLimit() {
            return this.index + this.length;
        }

        public int getValue() {
            return this.value;
        }

        public ArgType getArgType() {
            Type type = this.getType();
            if (type == Type.ARG_START || type == Type.ARG_LIMIT) {
                return MessagePattern.access$500()[this.value];
            }
            return ArgType.NONE;
        }

        public String toString() {
            String string = this.type == Type.ARG_START || this.type == Type.ARG_LIMIT ? this.getArgType().name() : Integer.toString(this.value);
            return this.type.name() + "(" + string + ")@" + this.index;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Part part = (Part)object;
            return this.type.equals((Object)part.type) && this.index == part.index && this.length == part.length && this.value == part.value && this.limitPartIndex == part.limitPartIndex;
        }

        public int hashCode() {
            return ((this.type.hashCode() * 37 + this.index) * 37 + this.length) * 37 + this.value;
        }

        static int access$000(Part part) {
            return part.index;
        }

        static short access$100(Part part) {
            return part.value;
        }

        static Type access$200(Part part) {
            return part.type;
        }

        static char access$300(Part part) {
            return part.length;
        }

        static int access$400(Part part) {
            return part.limitPartIndex;
        }

        static short access$102(Part part, short s) {
            part.value = s;
            return part.value;
        }

        Part(Type type, int n, int n2, int n3, 1 var5_5) {
            this(type, n, n2, n3);
        }

        static int access$402(Part part, int n) {
            part.limitPartIndex = n;
            return part.limitPartIndex;
        }

        public static enum Type {
            MSG_START,
            MSG_LIMIT,
            SKIP_SYNTAX,
            INSERT_CHAR,
            REPLACE_NUMBER,
            ARG_START,
            ARG_LIMIT,
            ARG_NUMBER,
            ARG_NAME,
            ARG_TYPE,
            ARG_STYLE,
            ARG_SELECTOR,
            ARG_INT,
            ARG_DOUBLE;


            public boolean hasNumericValue() {
                return this == ARG_INT || this == ARG_DOUBLE;
            }
        }
    }

    public static enum ApostropheMode {
        DOUBLE_OPTIONAL,
        DOUBLE_REQUIRED;

    }
}

