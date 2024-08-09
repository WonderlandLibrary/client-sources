/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.scanner;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Constant;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.scanner.SimpleKey;
import org.yaml.snakeyaml.tokens.AliasToken;
import org.yaml.snakeyaml.tokens.AnchorToken;
import org.yaml.snakeyaml.tokens.BlockEndToken;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.BlockMappingStartToken;
import org.yaml.snakeyaml.tokens.BlockSequenceStartToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.DirectiveToken;
import org.yaml.snakeyaml.tokens.DocumentEndToken;
import org.yaml.snakeyaml.tokens.DocumentStartToken;
import org.yaml.snakeyaml.tokens.FlowEntryToken;
import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
import org.yaml.snakeyaml.tokens.FlowMappingStartToken;
import org.yaml.snakeyaml.tokens.FlowSequenceEndToken;
import org.yaml.snakeyaml.tokens.FlowSequenceStartToken;
import org.yaml.snakeyaml.tokens.KeyToken;
import org.yaml.snakeyaml.tokens.ScalarToken;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.StreamStartToken;
import org.yaml.snakeyaml.tokens.TagToken;
import org.yaml.snakeyaml.tokens.TagTuple;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.ValueToken;
import org.yaml.snakeyaml.util.ArrayStack;
import org.yaml.snakeyaml.util.UriEncoder;

public final class ScannerImpl
implements Scanner {
    private static final Pattern NOT_HEXA = Pattern.compile("[^0-9A-Fa-f]");
    public static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap<Character, String>();
    public static final Map<Character, Integer> ESCAPE_CODES = new HashMap<Character, Integer>();
    private final StreamReader reader;
    private boolean done = false;
    private int flowLevel = 0;
    private final List<Token> tokens;
    private Token lastToken;
    private int tokensTaken = 0;
    private int indent = -1;
    private final ArrayStack<Integer> indents;
    private final boolean parseComments;
    private final LoaderOptions loaderOptions;
    private boolean allowSimpleKey = true;
    private final Map<Integer, SimpleKey> possibleSimpleKeys;

    public ScannerImpl(StreamReader streamReader, LoaderOptions loaderOptions) {
        if (loaderOptions == null) {
            throw new NullPointerException("LoaderOptions must be provided.");
        }
        this.parseComments = loaderOptions.isProcessComments();
        this.reader = streamReader;
        this.tokens = new ArrayList<Token>(100);
        this.indents = new ArrayStack(10);
        this.possibleSimpleKeys = new LinkedHashMap<Integer, SimpleKey>();
        this.loaderOptions = loaderOptions;
        this.fetchStreamStart();
    }

    @Override
    public boolean checkToken(Token.ID ... iDArray) {
        while (this.needMoreTokens()) {
            this.fetchMoreTokens();
        }
        if (!this.tokens.isEmpty()) {
            if (iDArray.length == 0) {
                return false;
            }
            Token.ID iD = this.tokens.get(0).getTokenId();
            for (int i = 0; i < iDArray.length; ++i) {
                if (iD != iDArray[i]) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public Token peekToken() {
        while (this.needMoreTokens()) {
            this.fetchMoreTokens();
        }
        return this.tokens.get(0);
    }

    @Override
    public Token getToken() {
        ++this.tokensTaken;
        return this.tokens.remove(0);
    }

    private void addToken(Token token) {
        this.lastToken = token;
        this.tokens.add(token);
    }

    private void addToken(int n, Token token) {
        if (n == this.tokens.size()) {
            this.lastToken = token;
        }
        this.tokens.add(n, token);
    }

    private void addAllTokens(List<Token> list) {
        this.lastToken = list.get(list.size() - 1);
        this.tokens.addAll(list);
    }

    private boolean needMoreTokens() {
        if (this.done) {
            return true;
        }
        if (this.tokens.isEmpty()) {
            return false;
        }
        this.stalePossibleSimpleKeys();
        return this.nextPossibleSimpleKey() == this.tokensTaken;
    }

    private void fetchMoreTokens() {
        if (this.reader.getDocumentIndex() > this.loaderOptions.getCodePointLimit()) {
            throw new YAMLException("The incoming YAML document exceeds the limit: " + this.loaderOptions.getCodePointLimit() + " code points.");
        }
        this.scanToNextToken();
        this.stalePossibleSimpleKeys();
        this.unwindIndent(this.reader.getColumn());
        int n = this.reader.peek();
        switch (n) {
            case 0: {
                this.fetchStreamEnd();
                return;
            }
            case 37: {
                if (!this.checkDirective()) break;
                this.fetchDirective();
                return;
            }
            case 45: {
                if (this.checkDocumentStart()) {
                    this.fetchDocumentStart();
                    return;
                }
                if (!this.checkBlockEntry()) break;
                this.fetchBlockEntry();
                return;
            }
            case 46: {
                if (!this.checkDocumentEnd()) break;
                this.fetchDocumentEnd();
                return;
            }
            case 91: {
                this.fetchFlowSequenceStart();
                return;
            }
            case 123: {
                this.fetchFlowMappingStart();
                return;
            }
            case 93: {
                this.fetchFlowSequenceEnd();
                return;
            }
            case 125: {
                this.fetchFlowMappingEnd();
                return;
            }
            case 44: {
                this.fetchFlowEntry();
                return;
            }
            case 63: {
                if (!this.checkKey()) break;
                this.fetchKey();
                return;
            }
            case 58: {
                if (!this.checkValue()) break;
                this.fetchValue();
                return;
            }
            case 42: {
                this.fetchAlias();
                return;
            }
            case 38: {
                this.fetchAnchor();
                return;
            }
            case 33: {
                this.fetchTag();
                return;
            }
            case 124: {
                if (this.flowLevel != 0) break;
                this.fetchLiteral();
                return;
            }
            case 62: {
                if (this.flowLevel != 0) break;
                this.fetchFolded();
                return;
            }
            case 39: {
                this.fetchSingle();
                return;
            }
            case 34: {
                this.fetchDouble();
                return;
            }
        }
        if (this.checkPlain()) {
            this.fetchPlain();
            return;
        }
        String string = this.escapeChar(String.valueOf(Character.toChars(n)));
        if (n == 9) {
            string = string + "(TAB)";
        }
        String string2 = String.format("found character '%s' that cannot start any token. (Do not use %s for indentation)", string, string);
        throw new ScannerException("while scanning for the next token", null, string2, this.reader.getMark());
    }

    private String escapeChar(String string) {
        for (Character c : ESCAPE_REPLACEMENTS.keySet()) {
            String string2 = ESCAPE_REPLACEMENTS.get(c);
            if (!string2.equals(string)) continue;
            return "\\" + c;
        }
        return string;
    }

    private int nextPossibleSimpleKey() {
        if (!this.possibleSimpleKeys.isEmpty()) {
            return this.possibleSimpleKeys.values().iterator().next().getTokenNumber();
        }
        return 1;
    }

    private void stalePossibleSimpleKeys() {
        if (!this.possibleSimpleKeys.isEmpty()) {
            Iterator<SimpleKey> iterator2 = this.possibleSimpleKeys.values().iterator();
            while (iterator2.hasNext()) {
                SimpleKey simpleKey = iterator2.next();
                if (simpleKey.getLine() == this.reader.getLine() && this.reader.getIndex() - simpleKey.getIndex() <= 1024) continue;
                if (simpleKey.isRequired()) {
                    throw new ScannerException("while scanning a simple key", simpleKey.getMark(), "could not find expected ':'", this.reader.getMark());
                }
                iterator2.remove();
            }
        }
    }

    private void savePossibleSimpleKey() {
        boolean bl;
        boolean bl2 = bl = this.flowLevel == 0 && this.indent == this.reader.getColumn();
        if (!this.allowSimpleKey && bl) {
            throw new YAMLException("A simple key is required only if it is the first token in the current line");
        }
        if (this.allowSimpleKey) {
            this.removePossibleSimpleKey();
            int n = this.tokensTaken + this.tokens.size();
            SimpleKey simpleKey = new SimpleKey(n, bl, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark());
            this.possibleSimpleKeys.put(this.flowLevel, simpleKey);
        }
    }

    private void removePossibleSimpleKey() {
        SimpleKey simpleKey = this.possibleSimpleKeys.remove(this.flowLevel);
        if (simpleKey != null && simpleKey.isRequired()) {
            throw new ScannerException("while scanning a simple key", simpleKey.getMark(), "could not find expected ':'", this.reader.getMark());
        }
    }

    private void unwindIndent(int n) {
        if (this.flowLevel != 0) {
            return;
        }
        while (this.indent > n) {
            Mark mark = this.reader.getMark();
            this.indent = this.indents.pop();
            this.addToken(new BlockEndToken(mark, mark));
        }
    }

    private boolean addIndent(int n) {
        if (this.indent < n) {
            this.indents.push(this.indent);
            this.indent = n;
            return false;
        }
        return true;
    }

    private void fetchStreamStart() {
        Mark mark = this.reader.getMark();
        StreamStartToken streamStartToken = new StreamStartToken(mark, mark);
        this.addToken(streamStartToken);
    }

    private void fetchStreamEnd() {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        this.possibleSimpleKeys.clear();
        Mark mark = this.reader.getMark();
        StreamEndToken streamEndToken = new StreamEndToken(mark, mark);
        this.addToken(streamEndToken);
        this.done = true;
    }

    private void fetchDirective() {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        List<Token> list = this.scanDirective();
        this.addAllTokens(list);
    }

    private void fetchDocumentStart() {
        this.fetchDocumentIndicator(true);
    }

    private void fetchDocumentEnd() {
        this.fetchDocumentIndicator(false);
    }

    private void fetchDocumentIndicator(boolean bl) {
        this.unwindIndent(-1);
        this.removePossibleSimpleKey();
        this.allowSimpleKey = false;
        Mark mark = this.reader.getMark();
        this.reader.forward(3);
        Mark mark2 = this.reader.getMark();
        Token token = bl ? new DocumentStartToken(mark, mark2) : new DocumentEndToken(mark, mark2);
        this.addToken(token);
    }

    private void fetchFlowSequenceStart() {
        this.fetchFlowCollectionStart(false);
    }

    private void fetchFlowMappingStart() {
        this.fetchFlowCollectionStart(true);
    }

    private void fetchFlowCollectionStart(boolean bl) {
        this.savePossibleSimpleKey();
        ++this.flowLevel;
        this.allowSimpleKey = true;
        Mark mark = this.reader.getMark();
        this.reader.forward(1);
        Mark mark2 = this.reader.getMark();
        Token token = bl ? new FlowMappingStartToken(mark, mark2) : new FlowSequenceStartToken(mark, mark2);
        this.addToken(token);
    }

    private void fetchFlowSequenceEnd() {
        this.fetchFlowCollectionEnd(false);
    }

    private void fetchFlowMappingEnd() {
        this.fetchFlowCollectionEnd(true);
    }

    private void fetchFlowCollectionEnd(boolean bl) {
        this.removePossibleSimpleKey();
        --this.flowLevel;
        this.allowSimpleKey = false;
        Mark mark = this.reader.getMark();
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        Token token = bl ? new FlowMappingEndToken(mark, mark2) : new FlowSequenceEndToken(mark, mark2);
        this.addToken(token);
    }

    private void fetchFlowEntry() {
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        Mark mark = this.reader.getMark();
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        FlowEntryToken flowEntryToken = new FlowEntryToken(mark, mark2);
        this.addToken(flowEntryToken);
    }

    private void fetchBlockEntry() {
        Mark mark;
        if (this.flowLevel == 0) {
            if (!this.allowSimpleKey) {
                throw new ScannerException(null, null, "sequence entries are not allowed here", this.reader.getMark());
            }
            if (this.addIndent(this.reader.getColumn())) {
                mark = this.reader.getMark();
                this.addToken(new BlockSequenceStartToken(mark, mark));
            }
        }
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        mark = this.reader.getMark();
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        BlockEntryToken blockEntryToken = new BlockEntryToken(mark, mark2);
        this.addToken(blockEntryToken);
    }

    private void fetchKey() {
        Mark mark;
        if (this.flowLevel == 0) {
            if (!this.allowSimpleKey) {
                throw new ScannerException(null, null, "mapping keys are not allowed here", this.reader.getMark());
            }
            if (this.addIndent(this.reader.getColumn())) {
                mark = this.reader.getMark();
                this.addToken(new BlockMappingStartToken(mark, mark));
            }
        }
        this.allowSimpleKey = this.flowLevel == 0;
        this.removePossibleSimpleKey();
        mark = this.reader.getMark();
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        KeyToken keyToken = new KeyToken(mark, mark2);
        this.addToken(keyToken);
    }

    private void fetchValue() {
        Mark mark;
        SimpleKey simpleKey = this.possibleSimpleKeys.remove(this.flowLevel);
        if (simpleKey != null) {
            this.addToken(simpleKey.getTokenNumber() - this.tokensTaken, new KeyToken(simpleKey.getMark(), simpleKey.getMark()));
            if (this.flowLevel == 0 && this.addIndent(simpleKey.getColumn())) {
                this.addToken(simpleKey.getTokenNumber() - this.tokensTaken, new BlockMappingStartToken(simpleKey.getMark(), simpleKey.getMark()));
            }
            this.allowSimpleKey = false;
        } else {
            if (this.flowLevel == 0 && !this.allowSimpleKey) {
                throw new ScannerException(null, null, "mapping values are not allowed here", this.reader.getMark());
            }
            if (this.flowLevel == 0 && this.addIndent(this.reader.getColumn())) {
                mark = this.reader.getMark();
                this.addToken(new BlockMappingStartToken(mark, mark));
            }
            this.allowSimpleKey = this.flowLevel == 0;
            this.removePossibleSimpleKey();
        }
        mark = this.reader.getMark();
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        ValueToken valueToken = new ValueToken(mark, mark2);
        this.addToken(valueToken);
    }

    private void fetchAlias() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        Token token = this.scanAnchor(false);
        this.addToken(token);
    }

    private void fetchAnchor() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        Token token = this.scanAnchor(true);
        this.addToken(token);
    }

    private void fetchTag() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        Token token = this.scanTag();
        this.addToken(token);
    }

    private void fetchLiteral() {
        this.fetchBlockScalar('|');
    }

    private void fetchFolded() {
        this.fetchBlockScalar('>');
    }

    private void fetchBlockScalar(char c) {
        this.allowSimpleKey = true;
        this.removePossibleSimpleKey();
        List<Token> list = this.scanBlockScalar(c);
        this.addAllTokens(list);
    }

    private void fetchSingle() {
        this.fetchFlowScalar('\'');
    }

    private void fetchDouble() {
        this.fetchFlowScalar('\"');
    }

    private void fetchFlowScalar(char c) {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        Token token = this.scanFlowScalar(c);
        this.addToken(token);
    }

    private void fetchPlain() {
        this.savePossibleSimpleKey();
        this.allowSimpleKey = false;
        Token token = this.scanPlain();
        this.addToken(token);
    }

    private boolean checkDirective() {
        return this.reader.getColumn() == 0;
    }

    private boolean checkDocumentStart() {
        if (this.reader.getColumn() == 0) {
            return "---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
        }
        return true;
    }

    private boolean checkDocumentEnd() {
        if (this.reader.getColumn() == 0) {
            return "...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3));
        }
        return true;
    }

    private boolean checkBlockEntry() {
        return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }

    private boolean checkKey() {
        if (this.flowLevel != 0) {
            return false;
        }
        return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }

    private boolean checkValue() {
        if (this.flowLevel != 0) {
            return false;
        }
        return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
    }

    private boolean checkPlain() {
        int n = this.reader.peek();
        return Constant.NULL_BL_T_LINEBR.hasNo(n, "-?:,[]{}#&*!|>'\"%@`") || Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(1)) && (n == 45 || this.flowLevel == 0 && "?:".indexOf(n) != -1);
    }

    private void scanToNextToken() {
        if (this.reader.getIndex() == 0 && this.reader.peek() == 65279) {
            this.reader.forward();
        }
        boolean bl = false;
        int n = -1;
        while (!bl) {
            Object object;
            Object object2;
            Mark mark = this.reader.getMark();
            int n2 = this.reader.getColumn();
            boolean bl2 = false;
            int n3 = 0;
            while (this.reader.peek(n3) == 32) {
                ++n3;
            }
            if (n3 > 0) {
                this.reader.forward(n3);
            }
            if (this.reader.peek() == 35) {
                bl2 = true;
                if (n2 != 0 && (this.lastToken == null || this.lastToken.getTokenId() != Token.ID.BlockEntry)) {
                    object2 = CommentType.IN_LINE;
                    n = this.reader.getColumn();
                } else if (n == this.reader.getColumn()) {
                    object2 = CommentType.IN_LINE;
                } else {
                    n = -1;
                    object2 = CommentType.BLOCK;
                }
                object = this.scanComment((CommentType)((Object)object2));
                if (this.parseComments) {
                    this.addToken((Token)object);
                }
            }
            if ((object2 = this.scanLineBreak()).length() != 0) {
                if (this.parseComments && !bl2 && n2 == 0) {
                    object = this.reader.getMark();
                    this.addToken(new CommentToken(CommentType.BLANK_LINE, (String)object2, mark, (Mark)object));
                }
                if (this.flowLevel != 0) continue;
                this.allowSimpleKey = true;
                continue;
            }
            bl = true;
        }
    }

    private CommentToken scanComment(CommentType commentType) {
        Mark mark = this.reader.getMark();
        this.reader.forward();
        int n = 0;
        while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(n))) {
            ++n;
        }
        String string = this.reader.prefixForward(n);
        Mark mark2 = this.reader.getMark();
        return new CommentToken(commentType, string, mark, mark2);
    }

    private List<Token> scanDirective() {
        Mark mark;
        Mark mark2 = this.reader.getMark();
        this.reader.forward();
        String string = this.scanDirectiveName(mark2);
        List<Object> list = null;
        if ("YAML".equals(string)) {
            list = this.scanYamlDirectiveValue(mark2);
            mark = this.reader.getMark();
        } else if ("TAG".equals(string)) {
            list = this.scanTagDirectiveValue(mark2);
            mark = this.reader.getMark();
        } else {
            mark = this.reader.getMark();
            int n = 0;
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(n))) {
                ++n;
            }
            if (n > 0) {
                this.reader.forward(n);
            }
        }
        CommentToken commentToken = this.scanDirectiveIgnoredLine(mark2);
        DirectiveToken<Integer> directiveToken = new DirectiveToken<Integer>(string, list, mark2, mark);
        return this.makeTokenList(directiveToken, commentToken);
    }

    private String scanDirectiveName(Mark mark) {
        int n = 0;
        int n2 = this.reader.peek(n);
        while (Constant.ALPHA.has(n2)) {
            n2 = this.reader.peek(++n);
        }
        if (n == 0) {
            String string = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a directive", mark, "expected alphabetic or numeric character, but found " + string + "(" + n2 + ")", this.reader.getMark());
        }
        String string = this.reader.prefixForward(n);
        n2 = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(n2)) {
            String string2 = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a directive", mark, "expected alphabetic or numeric character, but found " + string2 + "(" + n2 + ")", this.reader.getMark());
        }
        return string;
    }

    private List<Integer> scanYamlDirectiveValue(Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        Integer n = this.scanYamlDirectiveNumber(mark);
        int n2 = this.reader.peek();
        if (n2 != 46) {
            String string = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a directive", mark, "expected a digit or '.', but found " + string + "(" + n2 + ")", this.reader.getMark());
        }
        this.reader.forward();
        Integer n3 = this.scanYamlDirectiveNumber(mark);
        n2 = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(n2)) {
            String string = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a directive", mark, "expected a digit or ' ', but found " + string + "(" + n2 + ")", this.reader.getMark());
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>(2);
        arrayList.add(n);
        arrayList.add(n3);
        return arrayList;
    }

    private Integer scanYamlDirectiveNumber(Mark mark) {
        int n = this.reader.peek();
        if (!Character.isDigit(n)) {
            String string = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a directive", mark, "expected a digit, but found " + string + "(" + n + ")", this.reader.getMark());
        }
        int n2 = 0;
        while (Character.isDigit(this.reader.peek(n2))) {
            ++n2;
        }
        String string = this.reader.prefixForward(n2);
        if (n2 > 3) {
            throw new ScannerException("while scanning a YAML directive", mark, "found a number which cannot represent a valid version: " + string, this.reader.getMark());
        }
        Integer n3 = Integer.parseInt(string);
        return n3;
    }

    private List<String> scanTagDirectiveValue(Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        String string = this.scanTagDirectiveHandle(mark);
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        String string2 = this.scanTagDirectivePrefix(mark);
        ArrayList<String> arrayList = new ArrayList<String>(2);
        arrayList.add(string);
        arrayList.add(string2);
        return arrayList;
    }

    private String scanTagDirectiveHandle(Mark mark) {
        String string = this.scanTagHandle("directive", mark);
        int n = this.reader.peek();
        if (n != 32) {
            String string2 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a directive", mark, "expected ' ', but found " + string2 + "(" + n + ")", this.reader.getMark());
        }
        return string;
    }

    private String scanTagDirectivePrefix(Mark mark) {
        String string = this.scanTagUri("directive", mark);
        int n = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(n)) {
            String string2 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a directive", mark, "expected ' ', but found " + string2 + "(" + n + ")", this.reader.getMark());
        }
        return string;
    }

    private CommentToken scanDirectiveIgnoredLine(Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        CommentToken commentToken = null;
        if (this.reader.peek() == 35) {
            CommentToken commentToken2 = this.scanComment(CommentType.IN_LINE);
            if (this.parseComments) {
                commentToken = commentToken2;
            }
        }
        int n = this.reader.peek();
        String string = this.scanLineBreak();
        if (string.length() == 0 && n != 0) {
            String string2 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a directive", mark, "expected a comment or a line break, but found " + string2 + "(" + n + ")", this.reader.getMark());
        }
        return commentToken;
    }

    private Token scanAnchor(boolean bl) {
        Mark mark = this.reader.getMark();
        int n = this.reader.peek();
        String string = n == 42 ? "alias" : "anchor";
        this.reader.forward();
        int n2 = 0;
        int n3 = this.reader.peek(n2);
        while (Constant.NULL_BL_T_LINEBR.hasNo(n3, ":,[]{}/.*&")) {
            n3 = this.reader.peek(++n2);
        }
        if (n2 == 0) {
            String string2 = String.valueOf(Character.toChars(n3));
            throw new ScannerException("while scanning an " + string, mark, "unexpected character found " + string2 + "(" + n3 + ")", this.reader.getMark());
        }
        String string3 = this.reader.prefixForward(n2);
        n3 = this.reader.peek();
        if (Constant.NULL_BL_T_LINEBR.hasNo(n3, "?:,]}%@`")) {
            String string4 = String.valueOf(Character.toChars(n3));
            throw new ScannerException("while scanning an " + string, mark, "unexpected character found " + string4 + "(" + n3 + ")", this.reader.getMark());
        }
        Mark mark2 = this.reader.getMark();
        Token token = bl ? new AnchorToken(string3, mark, mark2) : new AliasToken(string3, mark, mark2);
        return token;
    }

    private Token scanTag() {
        Mark mark = this.reader.getMark();
        int n = this.reader.peek(1);
        String string = null;
        String string2 = null;
        if (n == 60) {
            this.reader.forward(2);
            string2 = this.scanTagUri("tag", mark);
            n = this.reader.peek();
            if (n != 62) {
                String string3 = String.valueOf(Character.toChars(n));
                throw new ScannerException("while scanning a tag", mark, "expected '>', but found '" + string3 + "' (" + n + ")", this.reader.getMark());
            }
            this.reader.forward();
        } else if (Constant.NULL_BL_T_LINEBR.has(n)) {
            string2 = "!";
            this.reader.forward();
        } else {
            int n2 = 1;
            boolean bl = false;
            while (Constant.NULL_BL_LINEBR.hasNo(n)) {
                if (n == 33) {
                    bl = true;
                    break;
                }
                n = this.reader.peek(++n2);
            }
            if (bl) {
                string = this.scanTagHandle("tag", mark);
            } else {
                string = "!";
                this.reader.forward();
            }
            string2 = this.scanTagUri("tag", mark);
        }
        n = this.reader.peek();
        if (Constant.NULL_BL_LINEBR.hasNo(n)) {
            String string4 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a tag", mark, "expected ' ', but found '" + string4 + "' (" + n + ")", this.reader.getMark());
        }
        TagTuple tagTuple = new TagTuple(string, string2);
        Mark mark2 = this.reader.getMark();
        return new TagToken(tagTuple, mark, mark2);
    }

    private List<Token> scanBlockScalar(char c) {
        int n;
        Mark mark;
        String string;
        Object object;
        boolean bl = c == '>';
        StringBuilder stringBuilder = new StringBuilder();
        Mark mark2 = this.reader.getMark();
        this.reader.forward();
        Chomping chomping = this.scanBlockScalarIndicators(mark2);
        int n2 = chomping.getIncrement();
        CommentToken commentToken = this.scanBlockScalarIgnoredLine(mark2);
        int n3 = this.indent + 1;
        if (n3 < 1) {
            n3 = 1;
        }
        if (n2 == -1) {
            object = this.scanBlockScalarIndentation();
            string = (String)object[0];
            int n4 = (Integer)object[5];
            mark = (Mark)object[5];
            n = Math.max(n3, n4);
        } else {
            n = n3 + n2 - 1;
            object = this.scanBlockScalarBreaks(n);
            string = (String)object[0];
            mark = (Mark)object[5];
        }
        object = "";
        while (this.reader.getColumn() == n && this.reader.peek() != 0) {
            stringBuilder.append(string);
            boolean bl2 = " \t".indexOf(this.reader.peek()) == -1;
            int n5 = 0;
            while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(n5))) {
                ++n5;
            }
            stringBuilder.append(this.reader.prefixForward(n5));
            object = this.scanLineBreak();
            Object[] objectArray = this.scanBlockScalarBreaks(n);
            string = (String)objectArray[0];
            mark = (Mark)objectArray[5];
            if (this.reader.getColumn() != n || this.reader.peek() == 0) break;
            if (bl && "\n".equals(object) && bl2 && " \t".indexOf(this.reader.peek()) == -1) {
                if (string.length() != 0) continue;
                stringBuilder.append(" ");
                continue;
            }
            stringBuilder.append((String)object);
        }
        if (chomping.chompTailIsNotFalse()) {
            stringBuilder.append((String)object);
        }
        if (chomping.chompTailIsTrue()) {
            stringBuilder.append(string);
        }
        ScalarToken scalarToken = new ScalarToken(stringBuilder.toString(), false, mark2, mark, DumperOptions.ScalarStyle.createStyle(Character.valueOf(c)));
        return this.makeTokenList(commentToken, scalarToken);
    }

    private Chomping scanBlockScalarIndicators(Mark mark) {
        String string;
        Boolean bl = null;
        int n = -1;
        int n2 = this.reader.peek();
        if (n2 == 45 || n2 == 43) {
            bl = n2 == 43 ? Boolean.TRUE : Boolean.FALSE;
            this.reader.forward();
            n2 = this.reader.peek();
            if (Character.isDigit(n2)) {
                string = String.valueOf(Character.toChars(n2));
                n = Integer.parseInt(string);
                if (n == 0) {
                    throw new ScannerException("while scanning a block scalar", mark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
                }
                this.reader.forward();
            }
        } else if (Character.isDigit(n2)) {
            string = String.valueOf(Character.toChars(n2));
            n = Integer.parseInt(string);
            if (n == 0) {
                throw new ScannerException("while scanning a block scalar", mark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
            }
            this.reader.forward();
            n2 = this.reader.peek();
            if (n2 == 45 || n2 == 43) {
                bl = n2 == 43 ? Boolean.TRUE : Boolean.FALSE;
                this.reader.forward();
            }
        }
        if (Constant.NULL_BL_LINEBR.hasNo(n2 = this.reader.peek())) {
            string = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a block scalar", mark, "expected chomping or indentation indicators, but found " + string + "(" + n2 + ")", this.reader.getMark());
        }
        return new Chomping(bl, n);
    }

    private CommentToken scanBlockScalarIgnoredLine(Mark mark) {
        while (this.reader.peek() == 32) {
            this.reader.forward();
        }
        CommentToken commentToken = null;
        if (this.reader.peek() == 35) {
            commentToken = this.scanComment(CommentType.IN_LINE);
        }
        int n = this.reader.peek();
        String string = this.scanLineBreak();
        if (string.length() == 0 && n != 0) {
            String string2 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a block scalar", mark, "expected a comment or a line break, but found " + string2 + "(" + n + ")", this.reader.getMark());
        }
        return commentToken;
    }

    private Object[] scanBlockScalarIndentation() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        Mark mark = this.reader.getMark();
        while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
            if (this.reader.peek() != 32) {
                stringBuilder.append(this.scanLineBreak());
                mark = this.reader.getMark();
                continue;
            }
            this.reader.forward();
            if (this.reader.getColumn() <= n) continue;
            n = this.reader.getColumn();
        }
        return new Object[]{stringBuilder.toString(), n, mark};
    }

    private Object[] scanBlockScalarBreaks(int n) {
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        Mark mark = this.reader.getMark();
        for (n2 = this.reader.getColumn(); n2 < n && this.reader.peek() == 32; ++n2) {
            this.reader.forward();
        }
        String string = null;
        while ((string = this.scanLineBreak()).length() != 0) {
            stringBuilder.append(string);
            mark = this.reader.getMark();
            for (n2 = this.reader.getColumn(); n2 < n && this.reader.peek() == 32; ++n2) {
                this.reader.forward();
            }
        }
        return new Object[]{stringBuilder.toString(), mark};
    }

    private Token scanFlowScalar(char c) {
        boolean bl = c == '\"';
        StringBuilder stringBuilder = new StringBuilder();
        Mark mark = this.reader.getMark();
        int n = this.reader.peek();
        this.reader.forward();
        stringBuilder.append(this.scanFlowScalarNonSpaces(bl, mark));
        while (this.reader.peek() != n) {
            stringBuilder.append(this.scanFlowScalarSpaces(mark));
            stringBuilder.append(this.scanFlowScalarNonSpaces(bl, mark));
        }
        this.reader.forward();
        Mark mark2 = this.reader.getMark();
        return new ScalarToken(stringBuilder.toString(), false, mark, mark2, DumperOptions.ScalarStyle.createStyle(Character.valueOf(c)));
    }

    private String scanFlowScalarNonSpaces(boolean bl, Mark mark) {
        StringBuilder stringBuilder;
        block10: {
            String string;
            int n;
            stringBuilder = new StringBuilder();
            while (true) {
                int n2 = 0;
                while (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(n2), "'\"\\")) {
                    ++n2;
                }
                if (n2 != 0) {
                    stringBuilder.append(this.reader.prefixForward(n2));
                }
                n = this.reader.peek();
                if (!bl && n == 39 && this.reader.peek(1) == 39) {
                    stringBuilder.append("'");
                    this.reader.forward(2);
                    continue;
                }
                if (bl && n == 39 || !bl && "\"\\".indexOf(n) != -1) {
                    stringBuilder.appendCodePoint(n);
                    this.reader.forward();
                    continue;
                }
                if (!bl || n != 92) break block10;
                this.reader.forward();
                n = this.reader.peek();
                if (!Character.isSupplementaryCodePoint(n) && ESCAPE_REPLACEMENTS.containsKey(Character.valueOf((char)n))) {
                    stringBuilder.append(ESCAPE_REPLACEMENTS.get(Character.valueOf((char)n)));
                    this.reader.forward();
                    continue;
                }
                if (!Character.isSupplementaryCodePoint(n) && ESCAPE_CODES.containsKey(Character.valueOf((char)n))) {
                    n2 = ESCAPE_CODES.get(Character.valueOf((char)n));
                    this.reader.forward();
                    string = this.reader.prefix(n2);
                    if (NOT_HEXA.matcher(string).find()) {
                        throw new ScannerException("while scanning a double-quoted scalar", mark, "expected escape sequence of " + n2 + " hexadecimal numbers, but found: " + string, this.reader.getMark());
                    }
                    int n3 = Integer.parseInt(string, 16);
                    try {
                        String string2 = new String(Character.toChars(n3));
                        stringBuilder.append(string2);
                        this.reader.forward(n2);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        throw new ScannerException("while scanning a double-quoted scalar", mark, "found unknown escape character " + string, this.reader.getMark());
                    }
                }
                if (this.scanLineBreak().length() == 0) break;
                stringBuilder.append(this.scanFlowScalarBreaks(mark));
            }
            string = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a double-quoted scalar", mark, "found unknown escape character " + string + "(" + n + ")", this.reader.getMark());
        }
        return stringBuilder.toString();
    }

    private String scanFlowScalarSpaces(Mark mark) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (" \t".indexOf(this.reader.peek(n)) != -1) {
            ++n;
        }
        String string = this.reader.prefixForward(n);
        int n2 = this.reader.peek();
        if (n2 == 0) {
            throw new ScannerException("while scanning a quoted scalar", mark, "found unexpected end of stream", this.reader.getMark());
        }
        String string2 = this.scanLineBreak();
        if (string2.length() != 0) {
            String string3 = this.scanFlowScalarBreaks(mark);
            if (!"\n".equals(string2)) {
                stringBuilder.append(string2);
            } else if (string3.length() == 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string3);
        } else {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private String scanFlowScalarBreaks(Mark mark) {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String string;
            if (("---".equals(string = this.reader.prefix(3)) || "...".equals(string)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
                throw new ScannerException("while scanning a quoted scalar", mark, "found unexpected document separator", this.reader.getMark());
            }
            while (" \t".indexOf(this.reader.peek()) != -1) {
                this.reader.forward();
            }
            String string2 = this.scanLineBreak();
            if (string2.length() == 0) break;
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    private Token scanPlain() {
        Mark mark;
        StringBuilder stringBuilder = new StringBuilder();
        Mark mark2 = mark = this.reader.getMark();
        int n = this.indent + 1;
        String string = "";
        do {
            int n2;
            int n3 = 0;
            if (this.reader.peek() == 35) break;
            while (!(Constant.NULL_BL_T_LINEBR.has(n2 = this.reader.peek(n3)) || n2 == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(n3 + 1), this.flowLevel != 0 ? ",[]{}" : "") || this.flowLevel != 0 && ",?[]{}".indexOf(n2) != -1)) {
                ++n3;
            }
            if (n3 == 0) break;
            this.allowSimpleKey = false;
            stringBuilder.append(string);
            stringBuilder.append(this.reader.prefixForward(n3));
            mark2 = this.reader.getMark();
        } while ((string = this.scanPlainSpaces()).length() != 0 && this.reader.peek() != 35 && (this.flowLevel != 0 || this.reader.getColumn() >= n));
        return new ScalarToken(stringBuilder.toString(), mark, mark2, true);
    }

    private boolean atEndOfPlain() {
        int n;
        int n2 = 0;
        int n3 = this.reader.getColumn();
        while ((n = this.reader.peek(n2)) != 0 && Constant.NULL_BL_T_LINEBR.has(n)) {
            if (!(Constant.LINEBR.has(n) || n == 13 && this.reader.peek(++n2 + 1) == 10 || n == 65279)) {
                ++n3;
                continue;
            }
            n3 = 0;
        }
        if (this.reader.peek(n2) == 35 || this.reader.peek(n2 + 1) == 0 || this.flowLevel == 0 && n3 < this.indent) {
            return false;
        }
        if (this.flowLevel == 0) {
            int n4 = 1;
            while ((n = this.reader.peek(n2 + n4)) != 0 && !Constant.NULL_BL_T_LINEBR.has(n)) {
                if (n == 58 && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(n2 + n4 + 1))) {
                    return false;
                }
                ++n4;
            }
        }
        return true;
    }

    private String scanPlainSpaces() {
        int n = 0;
        while (this.reader.peek(n) == 32 || this.reader.peek(n) == 9) {
            ++n;
        }
        String string = this.reader.prefixForward(n);
        String string2 = this.scanLineBreak();
        if (string2.length() != 0) {
            StringBuilder stringBuilder;
            block8: {
                this.allowSimpleKey = true;
                String string3 = this.reader.prefix(3);
                if ("---".equals(string3) || "...".equals(string3) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
                    return "";
                }
                if (this.parseComments && this.atEndOfPlain()) {
                    return "";
                }
                stringBuilder = new StringBuilder();
                while (true) {
                    if (this.reader.peek() == 32) {
                        this.reader.forward();
                        continue;
                    }
                    String string4 = this.scanLineBreak();
                    if (string4.length() == 0) break block8;
                    stringBuilder.append(string4);
                    string3 = this.reader.prefix(3);
                    if ("---".equals(string3) || "...".equals(string3) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) break;
                }
                return "";
            }
            if (!"\n".equals(string2)) {
                return string2 + stringBuilder;
            }
            if (stringBuilder.length() == 0) {
                return " ";
            }
            return stringBuilder.toString();
        }
        return string;
    }

    private String scanTagHandle(String string, Mark mark) {
        int n = this.reader.peek();
        if (n != 33) {
            String string2 = String.valueOf(Character.toChars(n));
            throw new ScannerException("while scanning a " + string, mark, "expected '!', but found " + string2 + "(" + n + ")", this.reader.getMark());
        }
        int n2 = 1;
        n = this.reader.peek(n2);
        if (n != 32) {
            while (Constant.ALPHA.has(n)) {
                n = this.reader.peek(++n2);
            }
            if (n != 33) {
                this.reader.forward(n2);
                String string3 = String.valueOf(Character.toChars(n));
                throw new ScannerException("while scanning a " + string, mark, "expected '!', but found " + string3 + "(" + n + ")", this.reader.getMark());
            }
            ++n2;
        }
        String string4 = this.reader.prefixForward(n2);
        return string4;
    }

    private String scanTagUri(String string, Mark mark) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = this.reader.peek(n);
        while (Constant.URI_CHARS.has(n2)) {
            if (n2 == 37) {
                stringBuilder.append(this.reader.prefixForward(n));
                n = 0;
                stringBuilder.append(this.scanUriEscapes(string, mark));
            } else {
                ++n;
            }
            n2 = this.reader.peek(n);
        }
        if (n != 0) {
            stringBuilder.append(this.reader.prefixForward(n));
        }
        if (stringBuilder.length() == 0) {
            String string2 = String.valueOf(Character.toChars(n2));
            throw new ScannerException("while scanning a " + string, mark, "expected URI, but found " + string2 + "(" + n2 + ")", this.reader.getMark());
        }
        return stringBuilder.toString();
    }

    private String scanUriEscapes(String string, Mark mark) {
        int n = 1;
        while (this.reader.peek(n * 3) == 37) {
            ++n;
        }
        Mark mark2 = this.reader.getMark();
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        while (this.reader.peek() == 37) {
            this.reader.forward();
            try {
                byte by = (byte)Integer.parseInt(this.reader.prefix(2), 16);
                byteBuffer.put(by);
            } catch (NumberFormatException numberFormatException) {
                int n2 = this.reader.peek();
                String string2 = String.valueOf(Character.toChars(n2));
                int n3 = this.reader.peek(1);
                String string3 = String.valueOf(Character.toChars(n3));
                throw new ScannerException("while scanning a " + string, mark, "expected URI escape sequence of 2 hexadecimal numbers, but found " + string2 + "(" + n2 + ") and " + string3 + "(" + n3 + ")", this.reader.getMark());
            }
            this.reader.forward(2);
        }
        byteBuffer.flip();
        try {
            return UriEncoder.decode(byteBuffer);
        } catch (CharacterCodingException characterCodingException) {
            throw new ScannerException("while scanning a " + string, mark, "expected URI in UTF-8: " + characterCodingException.getMessage(), mark2);
        }
    }

    private String scanLineBreak() {
        int n = this.reader.peek();
        if (n == 13 || n == 10 || n == 133) {
            if (n == 13 && 10 == this.reader.peek(1)) {
                this.reader.forward(2);
            } else {
                this.reader.forward();
            }
            return "\n";
        }
        if (n == 8232 || n == 8233) {
            this.reader.forward();
            return String.valueOf(Character.toChars(n));
        }
        return "";
    }

    private List<Token> makeTokenList(Token ... tokenArray) {
        ArrayList<Token> arrayList = new ArrayList<Token>();
        for (int i = 0; i < tokenArray.length; ++i) {
            if (tokenArray[i] == null || !this.parseComments && tokenArray[i] instanceof CommentToken) continue;
            arrayList.add(tokenArray[i]);
        }
        return arrayList;
    }

    @Override
    public void resetDocumentIndex() {
        this.reader.resetDocumentIndex();
    }

    static {
        ESCAPE_REPLACEMENTS.put(Character.valueOf('0'), "\u0000");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('a'), "\u0007");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('b'), "\b");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('t'), "\t");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('n'), "\n");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('v'), "\u000b");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('f'), "\f");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('r'), "\r");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('e'), "\u001b");
        ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), " ");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\"'), "\"");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('\\'), "\\");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('N'), "\u0085");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('_'), "\u00a0");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('L'), "\u2028");
        ESCAPE_REPLACEMENTS.put(Character.valueOf('P'), "\u2029");
        ESCAPE_CODES.put(Character.valueOf('x'), 2);
        ESCAPE_CODES.put(Character.valueOf('u'), 4);
        ESCAPE_CODES.put(Character.valueOf('U'), 8);
    }

    private static class Chomping {
        private final Boolean value;
        private final int increment;

        public Chomping(Boolean bl, int n) {
            this.value = bl;
            this.increment = n;
        }

        public boolean chompTailIsNotFalse() {
            return this.value == null || this.value != false;
        }

        public boolean chompTailIsTrue() {
            return this.value != null && this.value != false;
        }

        public int getIncrement() {
            return this.increment;
        }
    }
}

