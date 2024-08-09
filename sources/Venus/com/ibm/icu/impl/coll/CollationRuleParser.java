/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import java.text.ParseException;
import java.util.ArrayList;

public final class CollationRuleParser {
    static final Position[] POSITION_VALUES;
    static final char POS_LEAD = '\ufffe';
    static final char POS_BASE = '\u2800';
    private static final int UCOL_DEFAULT = -1;
    private static final int UCOL_OFF = 0;
    private static final int UCOL_ON = 1;
    private static final int STRENGTH_MASK = 15;
    private static final int STARRED_FLAG = 16;
    private static final int OFFSET_SHIFT = 8;
    private static final String BEFORE = "[before";
    private final StringBuilder rawBuilder = new StringBuilder();
    private static final String[] positions;
    private static final String[] gSpecialReorderCodes;
    private static final int U_PARSE_CONTEXT_LEN = 16;
    private Normalizer2 nfd = Normalizer2.getNFDInstance();
    private Normalizer2 nfc = Normalizer2.getNFCInstance();
    private String rules;
    private final CollationData baseData;
    private CollationSettings settings;
    private Sink sink;
    private Importer importer;
    private int ruleIndex;
    static final boolean $assertionsDisabled;

    CollationRuleParser(CollationData collationData) {
        this.baseData = collationData;
    }

    void setSink(Sink sink) {
        this.sink = sink;
    }

    void setImporter(Importer importer) {
        this.importer = importer;
    }

    void parse(String string, CollationSettings collationSettings) throws ParseException {
        this.settings = collationSettings;
        this.parse(string);
    }

    private void parse(String string) throws ParseException {
        this.rules = string;
        this.ruleIndex = 0;
        block7: while (this.ruleIndex < this.rules.length()) {
            char c = this.rules.charAt(this.ruleIndex);
            if (PatternProps.isWhiteSpace(c)) {
                ++this.ruleIndex;
                continue;
            }
            switch (c) {
                case '&': {
                    this.parseRuleChain();
                    continue block7;
                }
                case '[': {
                    this.parseSetting();
                    continue block7;
                }
                case '#': {
                    this.ruleIndex = this.skipComment(this.ruleIndex + 1);
                    continue block7;
                }
                case '@': {
                    this.settings.setFlag(2048, false);
                    ++this.ruleIndex;
                    continue block7;
                }
                case '!': {
                    ++this.ruleIndex;
                    continue block7;
                }
            }
            this.setParseError("expected a reset or setting or comment");
        }
    }

    private void parseRuleChain() throws ParseException {
        int n = this.parseResetAndPosition();
        boolean bl = true;
        while (true) {
            int n2;
            if ((n2 = this.parseRelationOperator()) < 0) {
                if (this.ruleIndex < this.rules.length() && this.rules.charAt(this.ruleIndex) == '#') {
                    this.ruleIndex = this.skipComment(this.ruleIndex + 1);
                    continue;
                }
                if (bl) {
                    this.setParseError("reset not followed by a relation");
                }
                return;
            }
            int n3 = n2 & 0xF;
            if (n < 15) {
                if (bl) {
                    if (n3 != n) {
                        this.setParseError("reset-before strength differs from its first relation");
                        return;
                    }
                } else if (n3 < n) {
                    this.setParseError("reset-before strength followed by a stronger relation");
                    return;
                }
            }
            int n4 = this.ruleIndex + (n2 >> 8);
            if ((n2 & 0x10) == 0) {
                this.parseRelationStrings(n3, n4);
            } else {
                this.parseStarredCharacters(n3, n4);
            }
            bl = false;
        }
    }

    private int parseResetAndPosition() throws ParseException {
        int n;
        char c;
        int n2;
        int n3 = this.skipWhiteSpace(this.ruleIndex + 1);
        if (this.rules.regionMatches(n3, BEFORE, 0, 0) && (n2 = n3 + 7) < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(n2)) && (n2 = this.skipWhiteSpace(n2 + 1)) + 1 < this.rules.length() && '1' <= (c = this.rules.charAt(n2)) && c <= '3' && this.rules.charAt(n2 + 1) == ']') {
            n = 0 + (c - 49);
            n3 = this.skipWhiteSpace(n2 + 2);
        } else {
            n = 15;
        }
        if (n3 >= this.rules.length()) {
            this.setParseError("reset without position");
            return 1;
        }
        n3 = this.rules.charAt(n3) == '[' ? this.parseSpecialPosition(n3, this.rawBuilder) : this.parseTailoringString(n3, this.rawBuilder);
        try {
            this.sink.addReset(n, this.rawBuilder);
        } catch (Exception exception) {
            this.setParseError("adding reset failed", exception);
            return 1;
        }
        this.ruleIndex = n3;
        return n;
    }

    private int parseRelationOperator() {
        int n;
        this.ruleIndex = this.skipWhiteSpace(this.ruleIndex);
        if (this.ruleIndex >= this.rules.length()) {
            return 1;
        }
        int n2 = this.ruleIndex;
        char c = this.rules.charAt(n2++);
        switch (c) {
            case '<': {
                if (n2 < this.rules.length() && this.rules.charAt(n2) == '<') {
                    if (++n2 < this.rules.length() && this.rules.charAt(n2) == '<') {
                        if (++n2 < this.rules.length() && this.rules.charAt(n2) == '<') {
                            ++n2;
                            n = 3;
                        } else {
                            n = 2;
                        }
                    } else {
                        n = 1;
                    }
                } else {
                    n = 0;
                }
                if (n2 >= this.rules.length() || this.rules.charAt(n2) != '*') break;
                ++n2;
                n |= 0x10;
                break;
            }
            case ';': {
                n = 1;
                break;
            }
            case ',': {
                n = 2;
                break;
            }
            case '=': {
                n = 15;
                if (n2 >= this.rules.length() || this.rules.charAt(n2) != '*') break;
                ++n2;
                n |= 0x10;
                break;
            }
            default: {
                return 1;
            }
        }
        return n2 - this.ruleIndex << 8 | n;
    }

    private void parseRelationStrings(int n, int n2) throws ParseException {
        char c;
        String string = "";
        CharSequence charSequence = "";
        char c2 = c = (n2 = this.parseTailoringString(n2, this.rawBuilder)) < this.rules.length() ? this.rules.charAt(n2) : (char)'\u0000';
        if (c == '|') {
            string = this.rawBuilder.toString();
            char c3 = c = (n2 = this.parseTailoringString(n2 + 1, this.rawBuilder)) < this.rules.length() ? this.rules.charAt(n2) : (char)'\u0000';
        }
        if (c == '/') {
            StringBuilder stringBuilder = new StringBuilder();
            n2 = this.parseTailoringString(n2 + 1, stringBuilder);
            charSequence = stringBuilder;
        }
        if (string.length() != 0) {
            int n3 = string.codePointAt(0);
            int n4 = this.rawBuilder.codePointAt(0);
            if (!this.nfc.hasBoundaryBefore(n3) || !this.nfc.hasBoundaryBefore(n4)) {
                this.setParseError("in 'prefix|str', prefix and str must each start with an NFC boundary");
                return;
            }
        }
        try {
            this.sink.addRelation(n, string, this.rawBuilder, charSequence);
        } catch (Exception exception) {
            this.setParseError("adding relation failed", exception);
            return;
        }
        this.ruleIndex = n2;
    }

    private void parseStarredCharacters(int n, int n2) throws ParseException {
        String string = "";
        n2 = this.parseString(this.skipWhiteSpace(n2), this.rawBuilder);
        if (this.rawBuilder.length() == 0) {
            this.setParseError("missing starred-relation string");
            return;
        }
        int n3 = -1;
        int n4 = 0;
        while (true) {
            int n5;
            if (n4 < this.rawBuilder.length()) {
                n5 = this.rawBuilder.codePointAt(n4);
                if (!this.nfd.isInert(n5)) {
                    this.setParseError("starred-relation string is not all NFD-inert");
                    return;
                }
                try {
                    this.sink.addRelation(n, string, UTF16.valueOf(n5), string);
                } catch (Exception exception) {
                    this.setParseError("adding relation failed", exception);
                    return;
                }
                n4 += Character.charCount(n5);
                n3 = n5;
                continue;
            }
            if (n2 >= this.rules.length() || this.rules.charAt(n2) != '-') break;
            if (n3 < 0) {
                this.setParseError("range without start in starred-relation string");
                return;
            }
            n2 = this.parseString(n2 + 1, this.rawBuilder);
            if (this.rawBuilder.length() == 0) {
                this.setParseError("range without end in starred-relation string");
                return;
            }
            n5 = this.rawBuilder.codePointAt(0);
            if (n5 < n3) {
                this.setParseError("range start greater than end in starred-relation string");
                return;
            }
            while (++n3 <= n5) {
                if (!this.nfd.isInert(n3)) {
                    this.setParseError("starred-relation string range is not all NFD-inert");
                    return;
                }
                if (CollationRuleParser.isSurrogate(n3)) {
                    this.setParseError("starred-relation string range contains a surrogate");
                    return;
                }
                if (65533 <= n3 && n3 <= 65535) {
                    this.setParseError("starred-relation string range contains U+FFFD, U+FFFE or U+FFFF");
                    return;
                }
                try {
                    this.sink.addRelation(n, string, UTF16.valueOf(n3), string);
                } catch (Exception exception) {
                    this.setParseError("adding relation failed", exception);
                    return;
                }
            }
            n3 = -1;
            n4 = Character.charCount(n5);
        }
        this.ruleIndex = this.skipWhiteSpace(n2);
    }

    private int parseTailoringString(int n, StringBuilder stringBuilder) throws ParseException {
        n = this.parseString(this.skipWhiteSpace(n), stringBuilder);
        if (stringBuilder.length() == 0) {
            this.setParseError("missing relation string");
        }
        return this.skipWhiteSpace(n);
    }

    private int parseString(int n, StringBuilder stringBuilder) throws ParseException {
        int n2;
        char c;
        stringBuilder.setLength(0);
        block0: while (n < this.rules.length()) {
            if (CollationRuleParser.isSyntaxChar(c = this.rules.charAt(n++))) {
                if (c == '\'') {
                    if (n < this.rules.length() && this.rules.charAt(n) == '\'') {
                        stringBuilder.append('\'');
                        ++n;
                        continue;
                    }
                    while (true) {
                        if (n == this.rules.length()) {
                            this.setParseError("quoted literal text missing terminating apostrophe");
                            return n;
                        }
                        if ((c = (char)this.rules.charAt(n++)) == '\'') {
                            if (n >= this.rules.length() || this.rules.charAt(n) != '\'') continue block0;
                            ++n;
                        }
                        stringBuilder.append(c);
                    }
                }
                if (c == '\\') {
                    if (n == this.rules.length()) {
                        this.setParseError("backslash escape at the end of the rule string");
                        return n;
                    }
                    n2 = this.rules.codePointAt(n);
                    stringBuilder.appendCodePoint(n2);
                    n += Character.charCount(n2);
                    continue;
                }
                --n;
                break;
            }
            if (PatternProps.isWhiteSpace(c)) {
                --n;
                break;
            }
            stringBuilder.append(c);
        }
        for (c = '\u0000'; c < stringBuilder.length(); c += Character.charCount(n2)) {
            n2 = stringBuilder.codePointAt(c);
            if (CollationRuleParser.isSurrogate(n2)) {
                this.setParseError("string contains an unpaired surrogate");
                return n;
            }
            if (65533 > n2 || n2 > 65535) continue;
            this.setParseError("string contains U+FFFD, U+FFFE or U+FFFF");
            return n;
        }
        return n;
    }

    private static final boolean isSurrogate(int n) {
        return (n & 0xFFFFF800) == 55296;
    }

    private int parseSpecialPosition(int n, StringBuilder stringBuilder) throws ParseException {
        int n2 = this.readWords(n + 1, this.rawBuilder);
        if (n2 > n && this.rules.charAt(n2) == ']' && this.rawBuilder.length() != 0) {
            ++n2;
            String string = this.rawBuilder.toString();
            stringBuilder.setLength(0);
            for (int i = 0; i < positions.length; ++i) {
                if (!string.equals(positions[i])) continue;
                stringBuilder.append('\ufffe').append((char)(10240 + i));
                return n2;
            }
            if (string.equals("top")) {
                stringBuilder.append('\ufffe').append((char)(10240 + Position.LAST_REGULAR.ordinal()));
                return n2;
            }
            if (string.equals("variable top")) {
                stringBuilder.append('\ufffe').append((char)(10240 + Position.LAST_VARIABLE.ordinal()));
                return n2;
            }
        }
        this.setParseError("not a valid special reset position");
        return n;
    }

    private void parseSetting() throws ParseException {
        int n = this.ruleIndex + 1;
        int n2 = this.readWords(n, this.rawBuilder);
        if (n2 <= n || this.rawBuilder.length() == 0) {
            this.setParseError("expected a setting/option at '['");
        }
        String string = this.rawBuilder.toString();
        if (this.rules.charAt(n2) == ']') {
            String string2;
            ++n2;
            if (string.startsWith("reorder") && (string.length() == 7 || string.charAt(7) == ' ')) {
                this.parseReordering(string);
                this.ruleIndex = n2;
                return;
            }
            if (string.equals("backwards 2")) {
                this.settings.setFlag(2048, false);
                this.ruleIndex = n2;
                return;
            }
            int n3 = string.lastIndexOf(32);
            if (n3 >= 0) {
                string2 = string.substring(n3 + 1);
                string = string.substring(0, n3);
            } else {
                string2 = "";
            }
            if (string.equals("strength") && string2.length() == 1) {
                int n4 = -1;
                char c = string2.charAt(0);
                if ('1' <= c && c <= '4') {
                    n4 = 0 + (c - 49);
                } else if (c == 'I') {
                    n4 = 15;
                }
                if (n4 != -1) {
                    this.settings.setStrength(n4);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("alternate")) {
                int n5 = -1;
                if (string2.equals("non-ignorable")) {
                    n5 = 0;
                } else if (string2.equals("shifted")) {
                    n5 = 1;
                }
                if (n5 != -1) {
                    this.settings.setAlternateHandlingShifted(n5 > 0);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("maxVariable")) {
                int n6 = -1;
                if (string2.equals("space")) {
                    n6 = 0;
                } else if (string2.equals("punct")) {
                    n6 = 1;
                } else if (string2.equals("symbol")) {
                    n6 = 2;
                } else if (string2.equals("currency")) {
                    n6 = 3;
                }
                if (n6 != -1) {
                    this.settings.setMaxVariable(n6, 0);
                    this.settings.variableTop = this.baseData.getLastPrimaryForGroup(4096 + n6);
                    if (!$assertionsDisabled && this.settings.variableTop == 0L) {
                        throw new AssertionError();
                    }
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("caseFirst")) {
                int n7 = -1;
                if (string2.equals("off")) {
                    n7 = 0;
                } else if (string2.equals("lower")) {
                    n7 = 512;
                } else if (string2.equals("upper")) {
                    n7 = 768;
                }
                if (n7 != -1) {
                    this.settings.setCaseFirst(n7);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("caseLevel")) {
                int n8 = CollationRuleParser.getOnOffValue(string2);
                if (n8 != -1) {
                    this.settings.setFlag(1024, n8 > 0);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("normalization")) {
                int n9 = CollationRuleParser.getOnOffValue(string2);
                if (n9 != -1) {
                    this.settings.setFlag(1, n9 > 0);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("numericOrdering")) {
                int n10 = CollationRuleParser.getOnOffValue(string2);
                if (n10 != -1) {
                    this.settings.setFlag(2, n10 > 0);
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("hiraganaQ")) {
                int n11 = CollationRuleParser.getOnOffValue(string2);
                if (n11 != -1) {
                    if (n11 == 1) {
                        this.setParseError("[hiraganaQ on] is not supported");
                    }
                    this.ruleIndex = n2;
                    return;
                }
            } else if (string.equals("import")) {
                ULocale uLocale;
                try {
                    uLocale = new ULocale.Builder().setLanguageTag(string2).build();
                } catch (Exception exception) {
                    this.setParseError("expected language tag in [import langTag]", exception);
                    return;
                }
                String string3 = uLocale.getBaseName();
                String string4 = uLocale.getKeywordValue("collation");
                if (this.importer == null) {
                    this.setParseError("[import langTag] is not supported");
                } else {
                    String string5;
                    try {
                        string5 = this.importer.getRules(string3, string4 != null ? string4 : "standard");
                    } catch (Exception exception) {
                        this.setParseError("[import langTag] failed", exception);
                        return;
                    }
                    String string6 = this.rules;
                    int n12 = this.ruleIndex;
                    try {
                        this.parse(string5);
                    } catch (Exception exception) {
                        this.ruleIndex = n12;
                        this.setParseError("parsing imported rules failed", exception);
                    }
                    this.rules = string6;
                    this.ruleIndex = n2;
                }
                return;
            }
        } else if (this.rules.charAt(n2) == '[') {
            UnicodeSet unicodeSet = new UnicodeSet();
            n2 = this.parseUnicodeSet(n2, unicodeSet);
            if (string.equals("optimize")) {
                try {
                    this.sink.optimize(unicodeSet);
                } catch (Exception exception) {
                    this.setParseError("[optimize set] failed", exception);
                }
                this.ruleIndex = n2;
                return;
            }
            if (string.equals("suppressContractions")) {
                try {
                    this.sink.suppressContractions(unicodeSet);
                } catch (Exception exception) {
                    this.setParseError("[suppressContractions set] failed", exception);
                }
                this.ruleIndex = n2;
                return;
            }
        }
        this.setParseError("not a valid setting/option");
    }

    private void parseReordering(CharSequence charSequence) throws ParseException {
        int n = 7;
        if (n == charSequence.length()) {
            this.settings.resetReordering();
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (n < charSequence.length()) {
            int n2;
            for (n2 = ++n; n2 < charSequence.length() && charSequence.charAt(n2) != ' '; ++n2) {
            }
            String string = charSequence.subSequence(n, n2).toString();
            int n3 = CollationRuleParser.getReorderCode(string);
            if (n3 < 0) {
                this.setParseError("unknown script or reorder code");
                return;
            }
            arrayList.add(n3);
            n = n2;
        }
        if (arrayList.isEmpty()) {
            this.settings.resetReordering();
        } else {
            int[] nArray = new int[arrayList.size()];
            int n4 = 0;
            for (Integer n5 : arrayList) {
                nArray[n4++] = n5;
            }
            this.settings.setReordering(this.baseData, nArray);
        }
    }

    public static int getReorderCode(String string) {
        int n;
        for (n = 0; n < gSpecialReorderCodes.length; ++n) {
            if (!string.equalsIgnoreCase(gSpecialReorderCodes[n])) continue;
            return 4096 + n;
        }
        try {
            n = UCharacter.getPropertyValueEnum(4106, string);
            if (n >= 0) {
                return n;
            }
        } catch (IllegalIcuArgumentException illegalIcuArgumentException) {
            // empty catch block
        }
        if (string.equalsIgnoreCase("others")) {
            return 0;
        }
        return 1;
    }

    private static int getOnOffValue(String string) {
        if (string.equals("on")) {
            return 0;
        }
        if (string.equals("off")) {
            return 1;
        }
        return 1;
    }

    private int parseUnicodeSet(int n, UnicodeSet unicodeSet) throws ParseException {
        int n2 = 0;
        int n3 = n;
        while (true) {
            char c;
            if (n3 == this.rules.length()) {
                this.setParseError("unbalanced UnicodeSet pattern brackets");
                return n3;
            }
            if ((c = this.rules.charAt(n3++)) == '[') {
                ++n2;
                continue;
            }
            if (c == ']' && --n2 == 0) break;
        }
        try {
            unicodeSet.applyPattern(this.rules.substring(n, n3));
        } catch (Exception exception) {
            this.setParseError("not a valid UnicodeSet pattern: " + exception.getMessage());
        }
        n3 = this.skipWhiteSpace(n3);
        if (n3 == this.rules.length() || this.rules.charAt(n3) != ']') {
            this.setParseError("missing option-terminating ']' after UnicodeSet pattern");
            return n3;
        }
        return ++n3;
    }

    private int readWords(int n, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        n = this.skipWhiteSpace(n);
        while (n < this.rules.length()) {
            char c = this.rules.charAt(n);
            if (CollationRuleParser.isSyntaxChar(c) && c != '-' && c != '_') {
                if (stringBuilder.length() == 0) {
                    return n;
                }
                int n2 = stringBuilder.length() - 1;
                if (stringBuilder.charAt(n2) == ' ') {
                    stringBuilder.setLength(n2);
                }
                return n;
            }
            if (PatternProps.isWhiteSpace(c)) {
                stringBuilder.append(' ');
                n = this.skipWhiteSpace(n + 1);
                continue;
            }
            stringBuilder.append(c);
            ++n;
        }
        return 1;
    }

    private int skipComment(int n) {
        char c;
        while (n < this.rules.length() && (c = this.rules.charAt(n++)) != '\n' && c != '\f' && c != '\r' && c != '\u0085' && c != '\u2028' && c != '\u2029') {
        }
        return n;
    }

    private void setParseError(String string) throws ParseException {
        throw this.makeParseException(string);
    }

    private void setParseError(String string, Exception exception) throws ParseException {
        ParseException parseException = this.makeParseException(string + ": " + exception.getMessage());
        parseException.initCause(exception);
        throw parseException;
    }

    private ParseException makeParseException(String string) {
        return new ParseException(this.appendErrorContext(string), this.ruleIndex);
    }

    private String appendErrorContext(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append(" at index ").append(this.ruleIndex);
        stringBuilder.append(" near \"");
        int n = this.ruleIndex - 15;
        if (n < 0) {
            n = 0;
        } else if (n > 0 && Character.isLowSurrogate(this.rules.charAt(n))) {
            ++n;
        }
        stringBuilder.append(this.rules, n, this.ruleIndex);
        stringBuilder.append('!');
        int n2 = this.rules.length() - this.ruleIndex;
        if (n2 >= 16 && Character.isHighSurrogate(this.rules.charAt(this.ruleIndex + (n2 = 15) - 1))) {
            --n2;
        }
        stringBuilder.append(this.rules, this.ruleIndex, this.ruleIndex + n2);
        return stringBuilder.append('\"').toString();
    }

    private static boolean isSyntaxChar(int n) {
        return 33 <= n && n <= 126 && (n <= 47 || 58 <= n && n <= 64 || 91 <= n && n <= 96 || 123 <= n);
    }

    private int skipWhiteSpace(int n) {
        while (n < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(n))) {
            ++n;
        }
        return n;
    }

    static {
        $assertionsDisabled = !CollationRuleParser.class.desiredAssertionStatus();
        POSITION_VALUES = Position.values();
        positions = new String[]{"first tertiary ignorable", "last tertiary ignorable", "first secondary ignorable", "last secondary ignorable", "first primary ignorable", "last primary ignorable", "first variable", "last variable", "first regular", "last regular", "first implicit", "last implicit", "first trailing", "last trailing"};
        gSpecialReorderCodes = new String[]{"space", "punct", "symbol", "currency", "digit"};
    }

    static interface Importer {
        public String getRules(String var1, String var2);
    }

    static abstract class Sink {
        Sink() {
        }

        abstract void addReset(int var1, CharSequence var2);

        abstract void addRelation(int var1, CharSequence var2, CharSequence var3, CharSequence var4);

        void suppressContractions(UnicodeSet unicodeSet) {
        }

        void optimize(UnicodeSet unicodeSet) {
        }
    }

    static enum Position {
        FIRST_TERTIARY_IGNORABLE,
        LAST_TERTIARY_IGNORABLE,
        FIRST_SECONDARY_IGNORABLE,
        LAST_SECONDARY_IGNORABLE,
        FIRST_PRIMARY_IGNORABLE,
        LAST_PRIMARY_IGNORABLE,
        FIRST_VARIABLE,
        LAST_VARIABLE,
        FIRST_REGULAR,
        LAST_REGULAR,
        FIRST_IMPLICIT,
        LAST_IMPLICIT,
        FIRST_TRAILING,
        LAST_TRAILING;

    }
}

