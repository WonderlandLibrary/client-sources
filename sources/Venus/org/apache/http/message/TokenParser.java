/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.BitSet;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class TokenParser {
    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char SP = ' ';
    public static final char HT = '\t';
    public static final char DQUOTE = '\"';
    public static final char ESCAPE = '\\';
    public static final TokenParser INSTANCE = new TokenParser();

    public static BitSet INIT_BITSET(int ... nArray) {
        BitSet bitSet = new BitSet();
        for (int n : nArray) {
            bitSet.set(n);
        }
        return bitSet;
    }

    public static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    public String parseToken(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        while (!parserCursor.atEnd()) {
            char c = charArrayBuffer.charAt(parserCursor.getPos());
            if (bitSet != null && bitSet.get(c)) break;
            if (TokenParser.isWhitespace(c)) {
                this.skipWhiteSpace(charArrayBuffer, parserCursor);
                bl = true;
                continue;
            }
            if (bl && stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            this.copyContent(charArrayBuffer, parserCursor, bitSet, stringBuilder);
            bl = false;
        }
        return stringBuilder.toString();
    }

    public String parseValue(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        while (!parserCursor.atEnd()) {
            char c = charArrayBuffer.charAt(parserCursor.getPos());
            if (bitSet != null && bitSet.get(c)) break;
            if (TokenParser.isWhitespace(c)) {
                this.skipWhiteSpace(charArrayBuffer, parserCursor);
                bl = true;
                continue;
            }
            if (c == '\"') {
                if (bl && stringBuilder.length() > 0) {
                    stringBuilder.append(' ');
                }
                this.copyQuotedContent(charArrayBuffer, parserCursor, stringBuilder);
                bl = false;
                continue;
            }
            if (bl && stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            this.copyUnquotedContent(charArrayBuffer, parserCursor, bitSet, stringBuilder);
            bl = false;
        }
        return stringBuilder.toString();
    }

    public void skipWhiteSpace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        char c;
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        for (int i = n2; i < n3 && TokenParser.isWhitespace(c = charArrayBuffer.charAt(i)); ++i) {
            ++n;
        }
        parserCursor.updatePos(n);
    }

    public void copyContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet, StringBuilder stringBuilder) {
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        for (int i = n2; i < n3; ++i) {
            char c = charArrayBuffer.charAt(i);
            if (bitSet != null && bitSet.get(c) || TokenParser.isWhitespace(c)) break;
            ++n;
            stringBuilder.append(c);
        }
        parserCursor.updatePos(n);
    }

    public void copyUnquotedContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, BitSet bitSet, StringBuilder stringBuilder) {
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        for (int i = n2; i < n3; ++i) {
            char c = charArrayBuffer.charAt(i);
            if (bitSet != null && bitSet.get(c) || TokenParser.isWhitespace(c) || c == '\"') break;
            ++n;
            stringBuilder.append(c);
        }
        parserCursor.updatePos(n);
    }

    public void copyQuotedContent(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor, StringBuilder stringBuilder) {
        if (parserCursor.atEnd()) {
            return;
        }
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        char c = charArrayBuffer.charAt(n);
        if (c != '\"') {
            return;
        }
        ++n;
        boolean bl = false;
        int n4 = ++n2;
        while (n4 < n3) {
            c = charArrayBuffer.charAt(n4);
            if (bl) {
                if (c != '\"' && c != '\\') {
                    stringBuilder.append('\\');
                }
                stringBuilder.append(c);
                bl = false;
            } else {
                if (c == '\"') {
                    ++n;
                    break;
                }
                if (c == '\\') {
                    bl = true;
                } else if (c != '\r' && c != '\n') {
                    stringBuilder.append(c);
                }
            }
            ++n4;
            ++n;
        }
        parserCursor.updatePos(n);
    }
}

