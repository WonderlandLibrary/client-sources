/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.FunctionReplacer;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Quantifier;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.StringMatcher;
import com.ibm.icu.text.StringReplacer;
import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.TransliterationRule;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.TransliteratorIDParser;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransliteratorParser {
    public List<RuleBasedTransliterator.Data> dataVector;
    public List<String> idBlockVector;
    private RuleBasedTransliterator.Data curData;
    public UnicodeSet compoundFilter;
    private int direction;
    private ParseData parseData;
    private List<Object> variablesVector;
    private Map<String, char[]> variableNames;
    private StringBuffer segmentStandins;
    private List<StringMatcher> segmentObjects;
    private char variableNext;
    private char variableLimit;
    private String undefinedVariableName;
    private int dotStandIn = -1;
    private static final String ID_TOKEN = "::";
    private static final int ID_TOKEN_LEN = 2;
    private static final char VARIABLE_DEF_OP = '=';
    private static final char FORWARD_RULE_OP = '>';
    private static final char REVERSE_RULE_OP = '<';
    private static final char FWDREV_RULE_OP = '~';
    private static final String OPERATORS = "=><\u2190\u2192\u2194";
    private static final String HALF_ENDERS = "=><\u2190\u2192\u2194;";
    private static final char QUOTE = '\'';
    private static final char ESCAPE = '\\';
    private static final char END_OF_RULE = ';';
    private static final char RULE_COMMENT_CHAR = '#';
    private static final char CONTEXT_ANTE = '{';
    private static final char CONTEXT_POST = '}';
    private static final char CURSOR_POS = '|';
    private static final char CURSOR_OFFSET = '@';
    private static final char ANCHOR_START = '^';
    private static final char KLEENE_STAR = '*';
    private static final char ONE_OR_MORE = '+';
    private static final char ZERO_OR_ONE = '?';
    private static final char DOT = '.';
    private static final String DOT_SET = "[^[:Zp:][:Zl:]\\r\\n$]";
    private static final char SEGMENT_OPEN = '(';
    private static final char SEGMENT_CLOSE = ')';
    private static final char FUNCTION = '&';
    private static final char ALT_REVERSE_RULE_OP = '\u2190';
    private static final char ALT_FORWARD_RULE_OP = '\u2192';
    private static final char ALT_FWDREV_RULE_OP = '\u2194';
    private static final char ALT_FUNCTION = '\u2206';
    private static UnicodeSet ILLEGAL_TOP = new UnicodeSet("[\\)]");
    private static UnicodeSet ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
    private static UnicodeSet ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");

    public void parse(String string, int n) {
        this.parseRules(new RuleArray(new String[]{string}), n);
    }

    void parseRules(RuleBody ruleBody, int n) {
        int n2;
        String string;
        boolean bl = true;
        int n3 = 0;
        this.dataVector = new ArrayList<RuleBasedTransliterator.Data>();
        this.idBlockVector = new ArrayList<String>();
        this.curData = null;
        this.direction = n;
        this.compoundFilter = null;
        this.variablesVector = new ArrayList<Object>();
        this.variableNames = new HashMap<String, char[]>();
        this.parseData = new ParseData(this, null);
        ArrayList<IllegalArgumentException> arrayList = new ArrayList<IllegalArgumentException>();
        int n4 = 0;
        ruleBody.reset();
        StringBuilder stringBuilder = new StringBuilder();
        this.compoundFilter = null;
        int n5 = -1;
        block4: while ((string = ruleBody.nextLine()) != null) {
            int n6 = 0;
            int n7 = string.length();
            while (n6 < n7) {
                Object object;
                char c;
                if (PatternProps.isWhiteSpace(c = string.charAt(n6++))) continue;
                if (c == '#') {
                    if ((n6 = string.indexOf("\n", n6) + 1) != 0) continue;
                    continue block4;
                }
                if (c == ';') continue;
                try {
                    ++n3;
                    if (--n6 + 2 + 1 <= n7 && string.regionMatches(n6, ID_TOKEN, 0, 1)) {
                        c = string.charAt(n6 += 2);
                        while (PatternProps.isWhiteSpace(c) && n6 < n7) {
                            c = string.charAt(++n6);
                        }
                        int[] nArray = new int[]{n6};
                        if (!bl) {
                            if (this.curData != null) {
                                if (this.direction == 0) {
                                    this.dataVector.add(this.curData);
                                } else {
                                    this.dataVector.add(0, this.curData);
                                }
                                this.curData = null;
                            }
                            bl = true;
                        }
                        object = TransliteratorIDParser.parseSingleID(string, nArray, this.direction);
                        if (nArray[0] != n6 && Utility.parseChar(string, nArray, ';')) {
                            if (this.direction == 0) {
                                stringBuilder.append(((TransliteratorIDParser.SingleID)object).canonID).append(';');
                            } else {
                                stringBuilder.insert(0, ((TransliteratorIDParser.SingleID)object).canonID + ';');
                            }
                        } else {
                            int[] nArray2 = new int[]{-1};
                            UnicodeSet unicodeSet = TransliteratorIDParser.parseGlobalFilter(string, nArray, this.direction, nArray2, null);
                            if (unicodeSet != null && Utility.parseChar(string, nArray, ';')) {
                                if (this.direction == 0 == (nArray2[0] == 0)) {
                                    if (this.compoundFilter != null) {
                                        TransliteratorParser.syntaxError("Multiple global filters", string, n6);
                                    }
                                    this.compoundFilter = unicodeSet;
                                    n5 = n3;
                                }
                            } else {
                                TransliteratorParser.syntaxError("Invalid ::ID", string, n6);
                            }
                        }
                        n6 = nArray[0];
                        continue;
                    }
                    if (bl) {
                        if (this.direction == 0) {
                            this.idBlockVector.add(stringBuilder.toString());
                        } else {
                            this.idBlockVector.add(0, stringBuilder.toString());
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                        bl = false;
                        this.curData = new RuleBasedTransliterator.Data();
                        this.setVariableRange(61440, 63743);
                    }
                    if (TransliteratorParser.resemblesPragma(string, n6, n7)) {
                        int n8 = this.parsePragma(string, n6, n7);
                        if (n8 < 0) {
                            TransliteratorParser.syntaxError("Unrecognized pragma", string, n6);
                        }
                        n6 = n8;
                        continue;
                    }
                    n6 = this.parseRule(string, n6, n7);
                } catch (IllegalArgumentException illegalArgumentException) {
                    if (n4 == 30) {
                        object = new IllegalIcuArgumentException("\nMore than 30 errors; further messages squelched");
                        ((IllegalIcuArgumentException)object).initCause(illegalArgumentException);
                        arrayList.add((IllegalArgumentException)object);
                        break block4;
                    }
                    illegalArgumentException.fillInStackTrace();
                    arrayList.add(illegalArgumentException);
                    ++n4;
                    n6 = TransliteratorParser.ruleEnd(string, n6, n7) + 1;
                }
            }
        }
        if (bl && stringBuilder.length() > 0) {
            if (this.direction == 0) {
                this.idBlockVector.add(stringBuilder.toString());
            } else {
                this.idBlockVector.add(0, stringBuilder.toString());
            }
        } else if (!bl && this.curData != null) {
            if (this.direction == 0) {
                this.dataVector.add(this.curData);
            } else {
                this.dataVector.add(0, this.curData);
            }
        }
        for (n2 = 0; n2 < this.dataVector.size(); ++n2) {
            RuleBasedTransliterator.Data data = this.dataVector.get(n2);
            data.variables = new Object[this.variablesVector.size()];
            this.variablesVector.toArray(data.variables);
            data.variableNames = new HashMap<String, char[]>();
            data.variableNames.putAll(this.variableNames);
        }
        this.variablesVector = null;
        try {
            if (this.compoundFilter != null && (this.direction == 0 && n5 != 1 || this.direction == 1 && n5 != n3)) {
                throw new IllegalIcuArgumentException("Compound filters misplaced");
            }
            for (n2 = 0; n2 < this.dataVector.size(); ++n2) {
                RuleBasedTransliterator.Data data = this.dataVector.get(n2);
                data.ruleSet.freeze();
            }
            if (this.idBlockVector.size() == 1 && this.idBlockVector.get(0).length() == 0) {
                this.idBlockVector.remove(0);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.fillInStackTrace();
            arrayList.add(illegalArgumentException);
        }
        if (arrayList.size() != 0) {
            for (int i = arrayList.size() - 1; i > 0; --i) {
                RuntimeException runtimeException = (RuntimeException)arrayList.get(i - 1);
                while (runtimeException.getCause() != null) {
                    runtimeException = (RuntimeException)runtimeException.getCause();
                }
                runtimeException.initCause((Throwable)arrayList.get(i));
            }
            throw (RuntimeException)arrayList.get(0);
        }
    }

    private int parseRule(String string, int n, int n2) {
        int n3;
        int n4 = n;
        int n5 = 0;
        this.segmentStandins = new StringBuffer();
        this.segmentObjects = new ArrayList<StringMatcher>();
        RuleHalf ruleHalf = new RuleHalf(null);
        RuleHalf ruleHalf2 = new RuleHalf(null);
        this.undefinedVariableName = null;
        if ((n = ruleHalf.parse(string, n, n2, this)) == n2 || OPERATORS.indexOf(n5 = string.charAt(--n)) < 0) {
            TransliteratorParser.syntaxError("No operator pos=" + n, string, n4);
        }
        if (n5 == 60 && ++n < n2 && string.charAt(n) == '>') {
            ++n;
            n5 = 126;
        }
        switch (n5) {
            case 8594: {
                n5 = 62;
                break;
            }
            case 8592: {
                n5 = 60;
                break;
            }
            case 8596: {
                n5 = 126;
            }
        }
        n = ruleHalf2.parse(string, n, n2, this);
        if (n < n2) {
            if (string.charAt(--n) == ';') {
                ++n;
            } else {
                TransliteratorParser.syntaxError("Unquoted operator", string, n4);
            }
        }
        if (n5 == 61) {
            if (this.undefinedVariableName == null) {
                TransliteratorParser.syntaxError("Missing '$' or duplicate definition", string, n4);
            }
            if (ruleHalf.text.length() != 1 || ruleHalf.text.charAt(0) != this.variableLimit) {
                TransliteratorParser.syntaxError("Malformed LHS", string, n4);
            }
            if (ruleHalf.anchorStart || ruleHalf.anchorEnd || ruleHalf2.anchorStart || ruleHalf2.anchorEnd) {
                TransliteratorParser.syntaxError("Malformed variable def", string, n4);
            }
            int n6 = ruleHalf2.text.length();
            char[] cArray = new char[n6];
            ruleHalf2.text.getChars(0, n6, cArray, 0);
            this.variableNames.put(this.undefinedVariableName, cArray);
            this.variableLimit = (char)(this.variableLimit + '\u0001');
            return n;
        }
        if (this.undefinedVariableName != null) {
            TransliteratorParser.syntaxError("Undefined variable $" + this.undefinedVariableName, string, n4);
        }
        if (this.segmentStandins.length() > this.segmentObjects.size()) {
            TransliteratorParser.syntaxError("Undefined segment reference", string, n4);
        }
        for (n3 = 0; n3 < this.segmentStandins.length(); ++n3) {
            if (this.segmentStandins.charAt(n3) != '\u0000') continue;
            TransliteratorParser.syntaxError("Internal error", string, n4);
        }
        for (n3 = 0; n3 < this.segmentObjects.size(); ++n3) {
            if (this.segmentObjects.get(n3) != null) continue;
            TransliteratorParser.syntaxError("Internal error", string, n4);
        }
        if (n5 != 126 && this.direction == 0 != (n5 == 62)) {
            return n;
        }
        if (this.direction == 1) {
            RuleHalf ruleHalf3 = ruleHalf;
            ruleHalf = ruleHalf2;
            ruleHalf2 = ruleHalf3;
        }
        if (n5 == 126) {
            ruleHalf2.removeContext();
            ruleHalf.cursor = -1;
            ruleHalf.cursorOffset = 0;
        }
        if (ruleHalf.ante < 0) {
            ruleHalf.ante = 0;
        }
        if (ruleHalf.post < 0) {
            ruleHalf.post = ruleHalf.text.length();
        }
        if (ruleHalf2.ante >= 0 || ruleHalf2.post >= 0 || ruleHalf.cursor >= 0 || ruleHalf2.cursorOffset != 0 && ruleHalf2.cursor < 0 || ruleHalf2.anchorStart || ruleHalf2.anchorEnd || !ruleHalf.isValidInput(this) || !ruleHalf2.isValidOutput(this) || ruleHalf.ante > ruleHalf.post) {
            TransliteratorParser.syntaxError("Malformed rule", string, n4);
        }
        UnicodeMatcher[] unicodeMatcherArray = null;
        if (this.segmentObjects.size() > 0) {
            unicodeMatcherArray = new UnicodeMatcher[this.segmentObjects.size()];
            this.segmentObjects.toArray(unicodeMatcherArray);
        }
        this.curData.ruleSet.addRule(new TransliterationRule(ruleHalf.text, ruleHalf.ante, ruleHalf.post, ruleHalf2.text, ruleHalf2.cursor, ruleHalf2.cursorOffset, unicodeMatcherArray, ruleHalf.anchorStart, ruleHalf.anchorEnd, this.curData));
        return n;
    }

    private void setVariableRange(int n, int n2) {
        if (n > n2 || n < 0 || n2 > 65535) {
            throw new IllegalIcuArgumentException("Invalid variable range " + n + ", " + n2);
        }
        this.curData.variablesBase = (char)n;
        if (this.dataVector.size() == 0) {
            this.variableNext = (char)n;
            this.variableLimit = (char)(n2 + 1);
        }
    }

    private void checkVariableRange(int n, String string, int n2) {
        if (n >= this.curData.variablesBase && n < this.variableLimit) {
            TransliteratorParser.syntaxError("Variable range character in rule", string, n2);
        }
    }

    private void pragmaMaximumBackup(int n) {
        throw new IllegalIcuArgumentException("use maximum backup pragma not implemented yet");
    }

    private void pragmaNormalizeRules(Normalizer.Mode mode) {
        throw new IllegalIcuArgumentException("use normalize rules pragma not implemented yet");
    }

    static boolean resemblesPragma(String string, int n, int n2) {
        return Utility.parsePattern(string, n, n2, "use ", null) >= 0;
    }

    private int parsePragma(String string, int n, int n2) {
        int[] nArray = new int[2];
        int n3 = Utility.parsePattern(string, n += 4, n2, "~variable range # #~;", nArray);
        if (n3 >= 0) {
            this.setVariableRange(nArray[0], nArray[1]);
            return n3;
        }
        n3 = Utility.parsePattern(string, n, n2, "~maximum backup #~;", nArray);
        if (n3 >= 0) {
            this.pragmaMaximumBackup(nArray[0]);
            return n3;
        }
        n3 = Utility.parsePattern(string, n, n2, "~nfd rules~;", null);
        if (n3 >= 0) {
            this.pragmaNormalizeRules(Normalizer.NFD);
            return n3;
        }
        n3 = Utility.parsePattern(string, n, n2, "~nfc rules~;", null);
        if (n3 >= 0) {
            this.pragmaNormalizeRules(Normalizer.NFC);
            return n3;
        }
        return 1;
    }

    static final void syntaxError(String string, String string2, int n) {
        int n2 = TransliteratorParser.ruleEnd(string2, n, string2.length());
        throw new IllegalIcuArgumentException(string + " in \"" + Utility.escape(string2.substring(n, n2)) + '\"');
    }

    static final int ruleEnd(String string, int n, int n2) {
        int n3 = Utility.quotedIndexOf(string, n, n2, ";");
        if (n3 < 0) {
            n3 = n2;
        }
        return n3;
    }

    private final char parseSet(String string, ParsePosition parsePosition) {
        UnicodeSet unicodeSet = new UnicodeSet(string, parsePosition, this.parseData);
        if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Private use variables exhausted");
        }
        unicodeSet.compact();
        return this.generateStandInFor(unicodeSet);
    }

    char generateStandInFor(Object object) {
        for (int i = 0; i < this.variablesVector.size(); ++i) {
            if (this.variablesVector.get(i) != object) continue;
            return (char)(this.curData.variablesBase + i);
        }
        if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Variable range exhausted");
        }
        this.variablesVector.add(object);
        char c = this.variableNext;
        this.variableNext = (char)(c + '\u0001');
        return c;
    }

    public char getSegmentStandin(int n) {
        char c;
        if (this.segmentStandins.length() < n) {
            this.segmentStandins.setLength(n);
        }
        if ((c = this.segmentStandins.charAt(n - 1)) == '\u0000') {
            if (this.variableNext >= this.variableLimit) {
                throw new RuntimeException("Variable range exhausted");
            }
            char c2 = this.variableNext;
            this.variableNext = (char)(c2 + '\u0001');
            c = c2;
            this.variablesVector.add(null);
            this.segmentStandins.setCharAt(n - 1, c);
        }
        return c;
    }

    public void setSegmentObject(int n, StringMatcher stringMatcher) {
        while (this.segmentObjects.size() < n) {
            this.segmentObjects.add(null);
        }
        int n2 = this.getSegmentStandin(n) - this.curData.variablesBase;
        if (this.segmentObjects.get(n - 1) != null || this.variablesVector.get(n2) != null) {
            throw new RuntimeException();
        }
        this.segmentObjects.set(n - 1, stringMatcher);
        this.variablesVector.set(n2, stringMatcher);
    }

    char getDotStandIn() {
        if (this.dotStandIn == -1) {
            this.dotStandIn = this.generateStandInFor(new UnicodeSet(DOT_SET));
        }
        return (char)this.dotStandIn;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void appendVariableDef(String string, StringBuffer stringBuffer) {
        char[] cArray = this.variableNames.get(string);
        if (cArray == null) {
            if (this.undefinedVariableName != null) throw new IllegalIcuArgumentException("Undefined variable $" + string);
            this.undefinedVariableName = string;
            if (this.variableNext >= this.variableLimit) {
                throw new RuntimeException("Private use variables exhausted");
            }
            this.variableLimit = (char)(this.variableLimit - '\u0001');
            stringBuffer.append(this.variableLimit);
            return;
        } else {
            stringBuffer.append(cArray);
        }
    }

    static Map access$000(TransliteratorParser transliteratorParser) {
        return transliteratorParser.variableNames;
    }

    static RuleBasedTransliterator.Data access$100(TransliteratorParser transliteratorParser) {
        return transliteratorParser.curData;
    }

    static List access$200(TransliteratorParser transliteratorParser) {
        return transliteratorParser.variablesVector;
    }

    static UnicodeSet access$400() {
        return ILLEGAL_TOP;
    }

    static char access$500(TransliteratorParser transliteratorParser, String string, ParsePosition parsePosition) {
        return transliteratorParser.parseSet(string, parsePosition);
    }

    static void access$600(TransliteratorParser transliteratorParser, int n, String string, int n2) {
        transliteratorParser.checkVariableRange(n, string, n2);
    }

    static UnicodeSet access$700() {
        return ILLEGAL_SEG;
    }

    static UnicodeSet access$800() {
        return ILLEGAL_FUNC;
    }

    static ParseData access$900(TransliteratorParser transliteratorParser) {
        return transliteratorParser.parseData;
    }

    static void access$1000(TransliteratorParser transliteratorParser, String string, StringBuffer stringBuffer) {
        transliteratorParser.appendVariableDef(string, stringBuffer);
    }

    private static class RuleHalf {
        public String text;
        public int cursor = -1;
        public int ante = -1;
        public int post = -1;
        public int cursorOffset = 0;
        private int cursorOffsetPos = 0;
        public boolean anchorStart = false;
        public boolean anchorEnd = false;
        private int nextSegmentNumber = 1;

        private RuleHalf() {
        }

        public int parse(String string, int n, int n2, TransliteratorParser transliteratorParser) {
            int n3 = n;
            StringBuffer stringBuffer = new StringBuffer();
            n = this.parseSection(string, n, n2, transliteratorParser, stringBuffer, TransliteratorParser.access$400(), false);
            this.text = stringBuffer.toString();
            if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
                TransliteratorParser.syntaxError("Misplaced |", string, n3);
            }
            return n;
        }

        private int parseSection(String string, int n, int n2, TransliteratorParser transliteratorParser, StringBuffer stringBuffer, UnicodeSet unicodeSet, boolean bl) {
            int n3 = n;
            ParsePosition parsePosition = null;
            int n4 = -1;
            int n5 = -1;
            int n6 = -1;
            int n7 = -1;
            int[] nArray = new int[1];
            int n8 = stringBuffer.length();
            block19: while (n < n2) {
                char c;
                if (PatternProps.isWhiteSpace(c = string.charAt(n++))) continue;
                if (TransliteratorParser.HALF_ENDERS.indexOf(c) >= 0) {
                    if (!bl) break;
                    TransliteratorParser.syntaxError("Unclosed segment", string, n3);
                    break;
                }
                if (this.anchorEnd) {
                    TransliteratorParser.syntaxError("Malformed variable reference", string, n3);
                }
                if (UnicodeSet.resemblesPattern(string, n - 1)) {
                    if (parsePosition == null) {
                        parsePosition = new ParsePosition(0);
                    }
                    parsePosition.setIndex(n - 1);
                    stringBuffer.append(TransliteratorParser.access$500(transliteratorParser, string, parsePosition));
                    n = parsePosition.getIndex();
                    continue;
                }
                if (c == '\\') {
                    if (n == n2) {
                        TransliteratorParser.syntaxError("Trailing backslash", string, n3);
                    }
                    nArray[0] = n;
                    int n9 = Utility.unescapeAt(string, nArray);
                    n = nArray[0];
                    if (n9 == -1) {
                        TransliteratorParser.syntaxError("Malformed escape", string, n3);
                    }
                    TransliteratorParser.access$600(transliteratorParser, n9, string, n3);
                    UTF16.append(stringBuffer, n9);
                    continue;
                }
                if (c == '\'') {
                    int n10 = string.indexOf(39, n);
                    if (n10 == n) {
                        stringBuffer.append(c);
                        ++n;
                        continue;
                    }
                    n4 = stringBuffer.length();
                    while (true) {
                        if (n10 < 0) {
                            TransliteratorParser.syntaxError("Unterminated quote", string, n3);
                        }
                        stringBuffer.append(string.substring(n, n10));
                        n = n10 + 1;
                        if (n >= n2 || string.charAt(n) != '\'') break;
                        n10 = string.indexOf(39, n + 1);
                    }
                    n5 = stringBuffer.length();
                    for (n10 = n4; n10 < n5; ++n10) {
                        TransliteratorParser.access$600(transliteratorParser, stringBuffer.charAt(n10), string, n3);
                    }
                    continue;
                }
                TransliteratorParser.access$600(transliteratorParser, c, string, n3);
                if (unicodeSet.contains(c)) {
                    TransliteratorParser.syntaxError("Illegal character '" + c + '\'', string, n3);
                }
                switch (c) {
                    case '^': {
                        if (stringBuffer.length() == 0 && !this.anchorStart) {
                            this.anchorStart = true;
                            break;
                        }
                        TransliteratorParser.syntaxError("Misplaced anchor start", string, n3);
                        break;
                    }
                    case '(': {
                        int n11;
                        int n12 = stringBuffer.length();
                        ++this.nextSegmentNumber;
                        n = this.parseSection(string, n, n2, transliteratorParser, stringBuffer, TransliteratorParser.access$700(), true);
                        UnicodeMatcher unicodeMatcher = new StringMatcher(stringBuffer.substring(n12), n11, TransliteratorParser.access$100(transliteratorParser));
                        transliteratorParser.setSegmentObject(n11, (StringMatcher)unicodeMatcher);
                        stringBuffer.setLength(n12);
                        stringBuffer.append(transliteratorParser.getSegmentStandin(n11));
                        break;
                    }
                    case '&': 
                    case '\u2206': {
                        Transliterator transliterator;
                        nArray[0] = n;
                        TransliteratorIDParser.SingleID singleID = TransliteratorIDParser.parseFilterID(string, nArray);
                        if (singleID == null || !Utility.parseChar(string, nArray, '(')) {
                            TransliteratorParser.syntaxError("Invalid function", string, n3);
                        }
                        if ((transliterator = singleID.getInstance()) == null) {
                            TransliteratorParser.syntaxError("Invalid function ID", string, n3);
                        }
                        int n13 = stringBuffer.length();
                        n = this.parseSection(string, nArray[0], n2, transliteratorParser, stringBuffer, TransliteratorParser.access$800(), true);
                        FunctionReplacer functionReplacer = new FunctionReplacer(transliterator, new StringReplacer(stringBuffer.substring(n13), TransliteratorParser.access$100(transliteratorParser)));
                        stringBuffer.setLength(n13);
                        stringBuffer.append(transliteratorParser.generateStandInFor(functionReplacer));
                        break;
                    }
                    case '$': {
                        if (n == n2) {
                            this.anchorEnd = true;
                            break;
                        }
                        c = string.charAt(n);
                        int n14 = UCharacter.digit(c, 10);
                        if (n14 >= 1 && n14 <= 9) {
                            nArray[0] = n;
                            n14 = Utility.parseNumber(string, nArray, 10);
                            if (n14 < 0) {
                                TransliteratorParser.syntaxError("Undefined segment reference", string, n3);
                            }
                            n = nArray[0];
                            stringBuffer.append(transliteratorParser.getSegmentStandin(n14));
                            break;
                        }
                        if (parsePosition == null) {
                            parsePosition = new ParsePosition(0);
                        }
                        parsePosition.setIndex(n);
                        String string2 = TransliteratorParser.access$900(transliteratorParser).parseReference(string, parsePosition, n2);
                        if (string2 == null) {
                            this.anchorEnd = true;
                            break;
                        }
                        n = parsePosition.getIndex();
                        n6 = stringBuffer.length();
                        TransliteratorParser.access$1000(transliteratorParser, string2, stringBuffer);
                        n7 = stringBuffer.length();
                        break;
                    }
                    case '.': {
                        stringBuffer.append(transliteratorParser.getDotStandIn());
                        break;
                    }
                    case '*': 
                    case '+': 
                    case '?': {
                        int n15;
                        UnicodeMatcher unicodeMatcher;
                        int n11;
                        if (bl && stringBuffer.length() == n8) {
                            TransliteratorParser.syntaxError("Misplaced quantifier", string, n3);
                            break;
                        }
                        if (stringBuffer.length() == n5) {
                            n15 = n4;
                            n11 = n5;
                        } else if (stringBuffer.length() == n7) {
                            n15 = n6;
                            n11 = n7;
                        } else {
                            n15 = stringBuffer.length() - 1;
                            n11 = n15 + 1;
                        }
                        try {
                            unicodeMatcher = new StringMatcher(stringBuffer.toString(), n15, n11, 0, TransliteratorParser.access$100(transliteratorParser));
                        } catch (RuntimeException runtimeException) {
                            String string3 = n < 50 ? string.substring(0, n) : "..." + string.substring(n - 50, n);
                            String string4 = n2 - n <= 50 ? string.substring(n, n2) : string.substring(n, n + 50) + "...";
                            throw new IllegalIcuArgumentException("Failure in rule: " + string3 + "$$$" + string4).initCause(runtimeException);
                        }
                        int n16 = 0;
                        int n17 = Integer.MAX_VALUE;
                        switch (c) {
                            case '+': {
                                n16 = 1;
                                break;
                            }
                            case '?': {
                                n16 = 0;
                                n17 = 1;
                            }
                        }
                        unicodeMatcher = new Quantifier(unicodeMatcher, n16, n17);
                        stringBuffer.setLength(n15);
                        stringBuffer.append(transliteratorParser.generateStandInFor(unicodeMatcher));
                        break;
                    }
                    case ')': {
                        break block19;
                    }
                    case '{': {
                        if (this.ante >= 0) {
                            TransliteratorParser.syntaxError("Multiple ante contexts", string, n3);
                        }
                        this.ante = stringBuffer.length();
                        break;
                    }
                    case '}': {
                        if (this.post >= 0) {
                            TransliteratorParser.syntaxError("Multiple post contexts", string, n3);
                        }
                        this.post = stringBuffer.length();
                        break;
                    }
                    case '|': {
                        if (this.cursor >= 0) {
                            TransliteratorParser.syntaxError("Multiple cursors", string, n3);
                        }
                        this.cursor = stringBuffer.length();
                        break;
                    }
                    case '@': {
                        if (this.cursorOffset < 0) {
                            if (stringBuffer.length() > 0) {
                                TransliteratorParser.syntaxError("Misplaced " + c, string, n3);
                            }
                            --this.cursorOffset;
                            break;
                        }
                        if (this.cursorOffset > 0) {
                            if (stringBuffer.length() != this.cursorOffsetPos || this.cursor >= 0) {
                                TransliteratorParser.syntaxError("Misplaced " + c, string, n3);
                            }
                            ++this.cursorOffset;
                            break;
                        }
                        if (this.cursor == 0 && stringBuffer.length() == 0) {
                            this.cursorOffset = -1;
                            break;
                        }
                        if (this.cursor < 0) {
                            this.cursorOffsetPos = stringBuffer.length();
                            this.cursorOffset = 1;
                            break;
                        }
                        TransliteratorParser.syntaxError("Misplaced " + c, string, n3);
                        break;
                    }
                    default: {
                        if (!(c < '!' || c > '~' || c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                            TransliteratorParser.syntaxError("Unquoted " + c, string, n3);
                        }
                        stringBuffer.append(c);
                    }
                }
            }
            return n;
        }

        void removeContext() {
            this.text = this.text.substring(this.ante < 0 ? 0 : this.ante, this.post < 0 ? this.text.length() : this.post);
            this.post = -1;
            this.ante = -1;
            this.anchorEnd = false;
            this.anchorStart = false;
        }

        public boolean isValidOutput(TransliteratorParser transliteratorParser) {
            int n;
            for (int i = 0; i < this.text.length(); i += UTF16.getCharCount(n)) {
                n = UTF16.charAt(this.text, i);
                if (TransliteratorParser.access$900(transliteratorParser).isReplacer(n)) continue;
                return true;
            }
            return false;
        }

        public boolean isValidInput(TransliteratorParser transliteratorParser) {
            int n;
            for (int i = 0; i < this.text.length(); i += UTF16.getCharCount(n)) {
                n = UTF16.charAt(this.text, i);
                if (TransliteratorParser.access$900(transliteratorParser).isMatcher(n)) continue;
                return true;
            }
            return false;
        }

        RuleHalf(1 var1_1) {
            this();
        }
    }

    private static class RuleArray
    extends RuleBody {
        String[] array;
        int i;

        public RuleArray(String[] stringArray) {
            super(null);
            this.array = stringArray;
            this.i = 0;
        }

        @Override
        public String handleNextLine() {
            return this.i < this.array.length ? this.array[this.i++] : null;
        }

        @Override
        public void reset() {
            this.i = 0;
        }
    }

    private static abstract class RuleBody {
        private RuleBody() {
        }

        String nextLine() {
            String string = this.handleNextLine();
            if (string != null && string.length() > 0 && string.charAt(string.length() - 1) == '\\') {
                StringBuilder stringBuilder = new StringBuilder(string);
                do {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    string = this.handleNextLine();
                    if (string == null) break;
                    stringBuilder.append(string);
                } while (string.length() > 0 && string.charAt(string.length() - 1) == '\\');
                string = stringBuilder.toString();
            }
            return string;
        }

        abstract void reset();

        abstract String handleNextLine();

        RuleBody(1 var1_1) {
            this();
        }
    }

    private class ParseData
    implements SymbolTable {
        final TransliteratorParser this$0;

        private ParseData(TransliteratorParser transliteratorParser) {
            this.this$0 = transliteratorParser;
        }

        @Override
        public char[] lookup(String string) {
            return (char[])TransliteratorParser.access$000(this.this$0).get(string);
        }

        @Override
        public UnicodeMatcher lookupMatcher(int n) {
            int n2 = n - TransliteratorParser.access$100((TransliteratorParser)this.this$0).variablesBase;
            if (n2 >= 0 && n2 < TransliteratorParser.access$200(this.this$0).size()) {
                return (UnicodeMatcher)TransliteratorParser.access$200(this.this$0).get(n2);
            }
            return null;
        }

        @Override
        public String parseReference(String string, ParsePosition parsePosition, int n) {
            int n2;
            int n3;
            for (n3 = n2 = parsePosition.getIndex(); n3 < n; ++n3) {
                char c = string.charAt(n3);
                if (n3 == n2 && !UCharacter.isUnicodeIdentifierStart(c) || !UCharacter.isUnicodeIdentifierPart(c)) break;
            }
            if (n3 == n2) {
                return null;
            }
            parsePosition.setIndex(n3);
            return string.substring(n2, n3);
        }

        public boolean isMatcher(int n) {
            int n2 = n - TransliteratorParser.access$100((TransliteratorParser)this.this$0).variablesBase;
            if (n2 >= 0 && n2 < TransliteratorParser.access$200(this.this$0).size()) {
                return TransliteratorParser.access$200(this.this$0).get(n2) instanceof UnicodeMatcher;
            }
            return false;
        }

        public boolean isReplacer(int n) {
            int n2 = n - TransliteratorParser.access$100((TransliteratorParser)this.this$0).variablesBase;
            if (n2 >= 0 && n2 < TransliteratorParser.access$200(this.this$0).size()) {
                return TransliteratorParser.access$200(this.this$0).get(n2) instanceof UnicodeReplacer;
            }
            return false;
        }

        ParseData(TransliteratorParser transliteratorParser, 1 var2_2) {
            this(transliteratorParser);
        }
    }
}

