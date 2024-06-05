/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.stream;

import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class JsonReader
implements Closeable {
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_EOF = 17;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private final Reader in;
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int[] stack = new int[32];
    private int stackSize = 0;
    private String[] pathNames;
    private int[] pathIndices;

    public JsonReader(Reader in) {
        this.stack[this.stackSize++] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        this.in = in;
    }

    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p != 3) throw new IllegalStateException("Expected BEGIN_ARRAY but was " + (Object)((Object)this.peek()) + this.locationString());
        this.push(1);
        this.pathIndices[this.stackSize - 1] = 0;
        this.peeked = 0;
    }

    public void endArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p != 4) throw new IllegalStateException("Expected END_ARRAY but was " + (Object)((Object)this.peek()) + this.locationString());
        --this.stackSize;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        this.peeked = 0;
    }

    public void beginObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p != 1) throw new IllegalStateException("Expected BEGIN_OBJECT but was " + (Object)((Object)this.peek()) + this.locationString());
        this.push(3);
        this.peeked = 0;
    }

    public void endObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p != 2) throw new IllegalStateException("Expected END_OBJECT but was " + (Object)((Object)this.peek()) + this.locationString());
        --this.stackSize;
        this.pathNames[this.stackSize] = null;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        this.peeked = 0;
    }

    public boolean hasNext() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 2) return false;
        if (p == 4) return false;
        return true;
    }

    public JsonToken peek() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        switch (p) {
            case 1: {
                return JsonToken.BEGIN_OBJECT;
            }
            case 2: {
                return JsonToken.END_OBJECT;
            }
            case 3: {
                return JsonToken.BEGIN_ARRAY;
            }
            case 4: {
                return JsonToken.END_ARRAY;
            }
            case 12: 
            case 13: 
            case 14: {
                return JsonToken.NAME;
            }
            case 5: 
            case 6: {
                return JsonToken.BOOLEAN;
            }
            case 7: {
                return JsonToken.NULL;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return JsonToken.STRING;
            }
            case 15: 
            case 16: {
                return JsonToken.NUMBER;
            }
            case 17: {
                return JsonToken.END_DOCUMENT;
            }
        }
        throw new AssertionError();
    }

    int doPeek() throws IOException {
        int c;
        int peekStack;
        block43: {
            block45: {
                block44: {
                    block42: {
                        peekStack = this.stack[this.stackSize - 1];
                        if (peekStack != 1) break block42;
                        this.stack[this.stackSize - 1] = 2;
                        break block43;
                    }
                    if (peekStack != 2) break block44;
                    c = this.nextNonWhitespace(true);
                    switch (c) {
                        case 93: {
                            this.peeked = 4;
                            return 4;
                        }
                        case 59: {
                            this.checkLenient();
                        }
                        case 44: {
                            break;
                        }
                        default: {
                            throw this.syntaxError("Unterminated array");
                        }
                    }
                    break block43;
                }
                if (peekStack == 3 || peekStack == 5) {
                    int c2;
                    this.stack[this.stackSize - 1] = 4;
                    if (peekStack == 5) {
                        c2 = this.nextNonWhitespace(true);
                        switch (c2) {
                            case 125: {
                                this.peeked = 2;
                                return 2;
                            }
                            case 59: {
                                this.checkLenient();
                            }
                            case 44: {
                                break;
                            }
                            default: {
                                throw this.syntaxError("Unterminated object");
                            }
                        }
                    }
                    c2 = this.nextNonWhitespace(true);
                    switch (c2) {
                        case 34: {
                            this.peeked = 13;
                            return 13;
                        }
                        case 39: {
                            this.checkLenient();
                            this.peeked = 12;
                            return 12;
                        }
                        case 125: {
                            if (peekStack == 5) throw this.syntaxError("Expected name");
                            this.peeked = 2;
                            return 2;
                        }
                    }
                    this.checkLenient();
                    --this.pos;
                    if (!this.isLiteral((char)c2)) throw this.syntaxError("Expected name");
                    this.peeked = 14;
                    return 14;
                }
                if (peekStack != 4) break block45;
                this.stack[this.stackSize - 1] = 5;
                c = this.nextNonWhitespace(true);
                switch (c) {
                    case 58: {
                        break;
                    }
                    case 61: {
                        this.checkLenient();
                        if ((this.pos < this.limit || this.fillBuffer(1)) && this.buffer[this.pos] == '>') {
                            ++this.pos;
                            break;
                        }
                        break block43;
                    }
                    default: {
                        throw this.syntaxError("Expected ':'");
                    }
                }
                break block43;
            }
            if (peekStack == 6) {
                if (this.lenient) {
                    this.consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = 7;
            } else if (peekStack == 7) {
                c = this.nextNonWhitespace(false);
                if (c == -1) {
                    this.peeked = 17;
                    return 17;
                }
                this.checkLenient();
                --this.pos;
            } else if (peekStack == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        c = this.nextNonWhitespace(true);
        switch (c) {
            case 93: {
                if (peekStack == 1) {
                    this.peeked = 4;
                    return 4;
                }
            }
            case 44: 
            case 59: {
                if (peekStack != 1) {
                    if (peekStack != 2) throw this.syntaxError("Unexpected value");
                }
                this.checkLenient();
                --this.pos;
                this.peeked = 7;
                return 7;
            }
            case 39: {
                this.checkLenient();
                this.peeked = 8;
                return 8;
            }
            case 34: {
                this.peeked = 9;
                return 9;
            }
            case 91: {
                this.peeked = 3;
                return 3;
            }
            case 123: {
                this.peeked = 1;
                return 1;
            }
        }
        --this.pos;
        int result = this.peekKeyword();
        if (result != 0) {
            return result;
        }
        result = this.peekNumber();
        if (result != 0) {
            return result;
        }
        if (!this.isLiteral(this.buffer[this.pos])) {
            throw this.syntaxError("Expected value");
        }
        this.checkLenient();
        this.peeked = 10;
        return 10;
    }

    private int peekKeyword() throws IOException {
        int peeking;
        String keywordUpper;
        String keyword;
        char c = this.buffer[this.pos];
        if (c == 't' || c == 'T') {
            keyword = "true";
            keywordUpper = "TRUE";
            peeking = 5;
        } else if (c == 'f' || c == 'F') {
            keyword = "false";
            keywordUpper = "FALSE";
            peeking = 6;
        } else {
            if (c != 'n') {
                if (c != 'N') return 0;
            }
            keyword = "null";
            keywordUpper = "NULL";
            peeking = 7;
        }
        int length = keyword.length();
        for (int i = 1; i < length; ++i) {
            if (this.pos + i >= this.limit && !this.fillBuffer(i + 1)) {
                return 0;
            }
            c = this.buffer[this.pos + i];
            if (c == keyword.charAt(i) || c == keywordUpper.charAt(i)) continue;
            return 0;
        }
        if ((this.pos + length < this.limit || this.fillBuffer(length + 1)) && this.isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        this.peeked = peeking;
        return this.peeked;
    }

    private int peekNumber() throws IOException {
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        long value = 0L;
        boolean negative = false;
        boolean fitsInLong = true;
        int last = 0;
        int i = 0;
        block6: while (true) {
            if (p + i == l) {
                if (i == buffer.length) {
                    return 0;
                }
                if (!this.fillBuffer(i + 1)) break;
                p = this.pos;
                l = this.limit;
            }
            char c = buffer[p + i];
            switch (c) {
                case '-': {
                    if (last == 0) {
                        negative = true;
                        last = 1;
                        break;
                    }
                    if (last != 5) return 0;
                    last = 6;
                    break;
                }
                case '+': {
                    if (last != 5) return 0;
                    last = 6;
                    break;
                }
                case 'E': 
                case 'e': {
                    if (last != 2) {
                        if (last != 4) return 0;
                    }
                    last = 5;
                    break;
                }
                case '.': {
                    if (last != 2) return 0;
                    last = 3;
                    break;
                }
                default: {
                    if (c < '0' || c > '9') {
                        if (this.isLiteral(c)) return 0;
                        break block6;
                    }
                    if (last == 1 || last == 0) {
                        value = -(c - 48);
                        last = 2;
                        break;
                    }
                    if (last == 2) {
                        if (value == 0L) {
                            return 0;
                        }
                        long newValue = value * 10L - (long)(c - 48);
                        fitsInLong &= value > -922337203685477580L || value == -922337203685477580L && newValue < value;
                        value = newValue;
                        break;
                    }
                    if (last == 3) {
                        last = 4;
                        break;
                    }
                    if (last != 5 && last != 6) break;
                    last = 7;
                }
            }
            ++i;
        }
        if (!(last != 2 || !fitsInLong || value == Long.MIN_VALUE && !negative || value == 0L && negative)) {
            this.peekedLong = negative ? value : -value;
            this.pos += i;
            this.peeked = 15;
            return 15;
        }
        if (last != 2 && last != 4) {
            if (last != 7) return 0;
        }
        this.peekedNumberLength = i;
        this.peeked = 16;
        return 16;
    }

    private boolean isLiteral(char c) throws IOException {
        switch (c) {
            case '#': 
            case '/': 
            case ';': 
            case '=': 
            case '\\': {
                this.checkLenient();
            }
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': 
            case ',': 
            case ':': 
            case '[': 
            case ']': 
            case '{': 
            case '}': {
                return false;
            }
        }
        return true;
    }

    public String nextName() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 14) {
            result = this.nextUnquotedValue();
        } else if (p == 12) {
            result = this.nextQuotedValue('\'');
        } else {
            if (p != 13) throw new IllegalStateException("Expected a name but was " + (Object)((Object)this.peek()) + this.locationString());
            result = this.nextQuotedValue('\"');
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = result;
        return result;
    }

    public String nextString() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 10) {
            result = this.nextUnquotedValue();
        } else if (p == 8) {
            result = this.nextQuotedValue('\'');
        } else if (p == 9) {
            result = this.nextQuotedValue('\"');
        } else if (p == 11) {
            result = this.peekedString;
            this.peekedString = null;
        } else if (p == 15) {
            result = Long.toString(this.peekedLong);
        } else {
            if (p != 16) throw new IllegalStateException("Expected a string but was " + (Object)((Object)this.peek()) + this.locationString());
            result = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        return result;
    }

    public boolean nextBoolean() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 5) {
            this.peeked = 0;
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
            return true;
        }
        if (p != 6) throw new IllegalStateException("Expected a boolean but was " + (Object)((Object)this.peek()) + this.locationString());
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        return false;
    }

    public void nextNull() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p != 7) throw new IllegalStateException("Expected null but was " + (Object)((Object)this.peek()) + this.locationString());
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
    }

    public double nextDouble() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 15) {
            this.peeked = 0;
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
            return this.peekedLong;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == 8 || p == 9) {
            this.peekedString = this.nextQuotedValue(p == 8 ? (char)'\'' : '\"');
        } else if (p == 10) {
            this.peekedString = this.nextUnquotedValue();
        } else if (p != 11) {
            throw new IllegalStateException("Expected a double but was " + (Object)((Object)this.peek()) + this.locationString());
        }
        this.peeked = 11;
        double result = Double.parseDouble(this.peekedString);
        if (!this.lenient) {
            if (Double.isNaN(result)) throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + this.locationString());
            if (Double.isInfinite(result)) {
                throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + this.locationString());
            }
        }
        this.peekedString = null;
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        return result;
    }

    public long nextLong() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 15) {
            this.peeked = 0;
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
            return this.peekedLong;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (p != 8 && p != 9) {
                if (p != 10) throw new IllegalStateException("Expected a long but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            this.peekedString = p == 10 ? this.nextUnquotedValue() : this.nextQuotedValue(p == 8 ? (char)'\'' : '\"');
            try {
                long result = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int n = this.stackSize - 1;
                this.pathIndices[n] = this.pathIndices[n] + 1;
                return result;
            }
            catch (NumberFormatException result) {}
        }
        this.peeked = 11;
        double asDouble = Double.parseDouble(this.peekedString);
        long result = (long)asDouble;
        if ((double)result != asDouble) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        return result;
    }

    private String nextQuotedValue(char quote) throws IOException {
        int len;
        int start;
        StringBuilder builder;
        char[] buffer;
        block7: {
            buffer = this.buffer;
            builder = null;
            do {
                int p = this.pos;
                int l = this.limit;
                start = p;
                while (p < l) {
                    char c;
                    if ((c = buffer[p++]) == quote) {
                        this.pos = p;
                        len = p - start - 1;
                        if (builder == null) {
                            return new String(buffer, start, len);
                        }
                        break block7;
                    }
                    if (c == '\\') {
                        this.pos = p;
                        len = p - start - 1;
                        if (builder == null) {
                            int estimatedLength = (len + 1) * 2;
                            builder = new StringBuilder(Math.max(estimatedLength, 16));
                        }
                        builder.append(buffer, start, len);
                        builder.append(this.readEscapeCharacter());
                        p = this.pos;
                        l = this.limit;
                        start = p;
                        continue;
                    }
                    if (c != '\n') continue;
                    ++this.lineNumber;
                    this.lineStart = p;
                }
                if (builder == null) {
                    int estimatedLength = (p - start) * 2;
                    builder = new StringBuilder(Math.max(estimatedLength, 16));
                }
                builder.append(buffer, start, p - start);
                this.pos = p;
            } while (this.fillBuffer(1));
            throw this.syntaxError("Unterminated string");
        }
        builder.append(buffer, start, len);
        return builder.toString();
    }

    private String nextUnquotedValue() throws IOException {
        StringBuilder builder = null;
        int i = 0;
        block4: while (true) {
            if (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case '#': 
                    case '/': 
                    case ';': 
                    case '=': 
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': 
                    case ',': 
                    case ':': 
                    case '[': 
                    case ']': 
                    case '{': 
                    case '}': {
                        break block4;
                    }
                    default: {
                        ++i;
                        break;
                    }
                }
                continue;
            }
            if (i < this.buffer.length) {
                if (!this.fillBuffer(i + 1)) break;
                continue;
            }
            if (builder == null) {
                builder = new StringBuilder(Math.max(i, 16));
            }
            builder.append(this.buffer, this.pos, i);
            this.pos += i;
            i = 0;
            if (!this.fillBuffer(1)) break;
        }
        String result = null == builder ? new String(this.buffer, this.pos, i) : builder.append(this.buffer, this.pos, i).toString();
        this.pos += i;
        return result;
    }

    private void skipQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        do {
            int p = this.pos;
            int l = this.limit;
            while (p < l) {
                char c;
                if ((c = buffer[p++]) == quote) {
                    this.pos = p;
                    return;
                }
                if (c == '\\') {
                    this.pos = p;
                    this.readEscapeCharacter();
                    p = this.pos;
                    l = this.limit;
                    continue;
                }
                if (c != '\n') continue;
                ++this.lineNumber;
                this.lineStart = p;
            }
            this.pos = p;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    /*
     * Exception decompiling
     */
    private void skipUnquotedValue() throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous, and can't clone.
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:611)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:406)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:601)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public int nextInt() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = this.doPeek();
        }
        if (p == 15) {
            int result = (int)this.peekedLong;
            if (this.peekedLong != (long)result) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + this.locationString());
            }
            this.peeked = 0;
            int n = this.stackSize - 1;
            this.pathIndices[n] = this.pathIndices[n] + 1;
            return result;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (p != 8 && p != 9) {
                if (p != 10) throw new IllegalStateException("Expected an int but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            this.peekedString = p == 10 ? this.nextUnquotedValue() : this.nextQuotedValue(p == 8 ? (char)'\'' : '\"');
            try {
                int result = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int n = this.stackSize - 1;
                this.pathIndices[n] = this.pathIndices[n] + 1;
                return result;
            }
            catch (NumberFormatException numberFormatException) {}
        }
        this.peeked = 11;
        double asDouble = Double.parseDouble(this.peekedString);
        int result = (int)asDouble;
        if ((double)result != asDouble) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        return result;
    }

    @Override
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int count = 0;
        do {
            int p;
            if ((p = this.peeked) == 0) {
                p = this.doPeek();
            }
            if (p == 3) {
                this.push(1);
                ++count;
            } else if (p == 1) {
                this.push(3);
                ++count;
            } else if (p == 4) {
                --this.stackSize;
                --count;
            } else if (p == 2) {
                --this.stackSize;
                --count;
            } else if (p == 14 || p == 10) {
                this.skipUnquotedValue();
            } else if (p == 8 || p == 12) {
                this.skipQuotedValue('\'');
            } else if (p == 9 || p == 13) {
                this.skipQuotedValue('\"');
            } else if (p == 16) {
                this.pos += this.peekedNumberLength;
            }
            this.peeked = 0;
        } while (count != 0);
        int n = this.stackSize - 1;
        this.pathIndices[n] = this.pathIndices[n] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    private void push(int newTop) {
        if (this.stackSize == this.stack.length) {
            int newLength = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, newLength);
            this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
            this.pathNames = Arrays.copyOf(this.pathNames, newLength);
        }
        this.stack[this.stackSize++] = newTop;
    }

    private boolean fillBuffer(int minimum) throws IOException {
        char[] buffer = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int total;
            if ((total = this.in.read(buffer, this.limit, buffer.length - this.limit)) == -1) return false;
            this.limit += total;
            if (this.lineNumber != 0 || this.lineStart != 0 || this.limit <= 0 || buffer[0] != '\ufeff') continue;
            ++this.pos;
            ++this.lineStart;
            ++minimum;
        } while (this.limit < minimum);
        return true;
    }

    private int nextNonWhitespace(boolean throwOnEof) throws IOException {
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        block4: while (true) {
            char c;
            if (p == l) {
                this.pos = p;
                if (!this.fillBuffer(1)) {
                    if (!throwOnEof) return -1;
                    throw new EOFException("End of input" + this.locationString());
                }
                p = this.pos;
                l = this.limit;
            }
            if ((c = buffer[p++]) == '\n') {
                ++this.lineNumber;
                this.lineStart = p;
                continue;
            }
            if (c == ' ' || c == '\r' || c == '\t') continue;
            if (c == '/') {
                this.pos = p;
                if (p == l) {
                    --this.pos;
                    boolean charsLoaded = this.fillBuffer(2);
                    ++this.pos;
                    if (!charsLoaded) {
                        return c;
                    }
                }
                this.checkLenient();
                char peek = buffer[this.pos];
                switch (peek) {
                    case '*': {
                        ++this.pos;
                        if (!this.skipTo("*/")) {
                            throw this.syntaxError("Unterminated comment");
                        }
                        p = this.pos + 2;
                        l = this.limit;
                        continue block4;
                    }
                    case '/': {
                        ++this.pos;
                        this.skipToEndOfLine();
                        p = this.pos;
                        l = this.limit;
                        continue block4;
                    }
                }
                return c;
            }
            if (c != '#') {
                this.pos = p;
                return c;
            }
            this.pos = p;
            this.checkLenient();
            this.skipToEndOfLine();
            p = this.pos;
            l = this.limit;
        }
    }

    private void checkLenient() throws IOException {
        if (this.lenient) return;
        throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
    }

    private void skipToEndOfLine() throws IOException {
        char c;
        do {
            if (this.pos >= this.limit) {
                if (!this.fillBuffer(1)) return;
            }
            if ((c = this.buffer[this.pos++]) != '\n') continue;
            ++this.lineNumber;
            this.lineStart = this.pos;
            return;
        } while (c != '\r');
    }

    private boolean skipTo(String toFind) throws IOException {
        int length = toFind.length();
        while (true) {
            block6: {
                if (this.pos + length > this.limit) {
                    if (!this.fillBuffer(length)) return false;
                }
                if (this.buffer[this.pos] == '\n') {
                    ++this.lineNumber;
                    this.lineStart = this.pos + 1;
                } else {
                    int c = 0;
                    while (c < length) {
                        if (this.buffer[this.pos + c] == toFind.charAt(c)) {
                            ++c;
                            continue;
                        }
                        break block6;
                    }
                    return true;
                }
            }
            ++this.pos;
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + this.locationString();
    }

    String locationString() {
        int line = this.lineNumber + 1;
        int column = this.pos - this.lineStart + 1;
        return " at line " + line + " column " + column + " path " + this.getPath();
    }

    public String getPath() {
        StringBuilder result = new StringBuilder().append('$');
        int i = 0;
        int size = this.stackSize;
        while (i < size) {
            switch (this.stack[i]) {
                case 1: 
                case 2: {
                    result.append('[').append(this.pathIndices[i]).append(']');
                    break;
                }
                case 3: 
                case 4: 
                case 5: {
                    result.append('.');
                    if (this.pathNames[i] == null) break;
                    result.append(this.pathNames[i]);
                    break;
                }
            }
            ++i;
        }
        return result.toString();
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        char escaped = this.buffer[this.pos++];
        switch (escaped) {
            case 'u': {
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                char result = '\u0000';
                int i = this.pos;
                int end = i + 4;
                while (true) {
                    if (i >= end) {
                        this.pos += 4;
                        return result;
                    }
                    char c = this.buffer[i];
                    result = (char)(result << 4);
                    if (c >= '0' && c <= '9') {
                        result = (char)(result + (c - 48));
                    } else if (c >= 'a' && c <= 'f') {
                        result = (char)(result + (c - 97 + 10));
                    } else {
                        if (c < 'A') throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        if (c > 'F') throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        result = (char)(result + (c - 65 + 10));
                    }
                    ++i;
                }
            }
            case 't': {
                return '\t';
            }
            case 'b': {
                return '\b';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 'f': {
                return '\f';
            }
            case '\n': {
                ++this.lineNumber;
                this.lineStart = this.pos;
            }
            case '\"': 
            case '\'': 
            case '/': 
            case '\\': {
                return escaped;
            }
        }
        throw this.syntaxError("Invalid escape sequence");
    }

    private IOException syntaxError(String message) throws IOException {
        throw new MalformedJsonException(message + this.locationString());
    }

    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        int p = this.pos;
        if (p + 5 > this.limit && !this.fillBuffer(5)) {
            return;
        }
        char[] buf = this.buffer;
        if (buf[p] != ')') return;
        if (buf[p + 1] != ']') return;
        if (buf[p + 2] != '}') return;
        if (buf[p + 3] != '\'') return;
        if (buf[p + 4] != '\n') {
            return;
        }
        this.pos += 5;
    }

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess(){

            @Override
            public void promoteNameToValue(JsonReader reader) throws IOException {
                if (reader instanceof JsonTreeReader) {
                    ((JsonTreeReader)reader).promoteNameToValue();
                    return;
                }
                int p = reader.peeked;
                if (p == 0) {
                    p = reader.doPeek();
                }
                if (p == 13) {
                    reader.peeked = 9;
                    return;
                }
                if (p == 12) {
                    reader.peeked = 8;
                    return;
                }
                if (p != 14) throw new IllegalStateException("Expected a name but was " + (Object)((Object)reader.peek()) + reader.locationString());
                reader.peeked = 10;
            }
        };
    }
}

