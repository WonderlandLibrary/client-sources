/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.IOException;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.Escaper;

public abstract class UnicodeEscaper
implements Escaper {
    private static final int DEST_PAD = 32;
    private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>(){

        @Override
        protected char[] initialValue() {
            return new char[1024];
        }
    };

    protected abstract char[] escape(int var1);

    protected int nextEscapeIndex(CharSequence csq, int start, int end) {
        int index = start;
        while (index < end) {
            int cp = UnicodeEscaper.codePointAt(csq, index, end);
            if (cp < 0) return index;
            if (this.escape(cp) != null) {
                return index;
            }
            index += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
        }
        return index;
    }

    @Override
    public String escape(String string) {
        String string2;
        int end = string.length();
        int index = this.nextEscapeIndex(string, 0, end);
        if (index == end) {
            string2 = string;
            return string2;
        }
        string2 = this.escapeSlow(string, index);
        return string2;
    }

    protected final String escapeSlow(String s, int index) {
        int end = s.length();
        char[] dest = DEST_TL.get();
        int destIndex = 0;
        int unescapedChunkStart = 0;
        while (index < end) {
            int cp = UnicodeEscaper.codePointAt(s, index, end);
            if (cp < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] escaped = this.escape(cp);
            if (escaped != null) {
                int charsSkipped = index - unescapedChunkStart;
                int sizeNeeded = destIndex + charsSkipped + escaped.length;
                if (dest.length < sizeNeeded) {
                    int destLength = sizeNeeded + (end - index) + 32;
                    dest = UnicodeEscaper.growBuffer(dest, destIndex, destLength);
                }
                if (charsSkipped > 0) {
                    s.getChars(unescapedChunkStart, index, dest, destIndex);
                    destIndex += charsSkipped;
                }
                if (escaped.length > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
                    destIndex += escaped.length;
                }
            }
            unescapedChunkStart = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
            index = this.nextEscapeIndex(s, unescapedChunkStart, end);
        }
        int charsSkipped = end - unescapedChunkStart;
        if (charsSkipped <= 0) return new String(dest, 0, destIndex);
        int endIndex = destIndex + charsSkipped;
        if (dest.length < endIndex) {
            dest = UnicodeEscaper.growBuffer(dest, destIndex, endIndex);
        }
        s.getChars(unescapedChunkStart, end, dest, destIndex);
        destIndex = endIndex;
        return new String(dest, 0, destIndex);
    }

    @Override
    public Appendable escape(final Appendable out) {
        if ($assertionsDisabled) return new Appendable(){
            int pendingHighSurrogate = -1;
            char[] decodedChars = new char[2];

            @Override
            public Appendable append(CharSequence csq) throws IOException {
                return this.append(csq, 0, csq.length());
            }

            @Override
            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                char[] escaped;
                int index = start;
                if (index >= end) return this;
                int unescapedChunkStart = index;
                if (this.pendingHighSurrogate != -1) {
                    char c;
                    if (!Character.isLowSurrogate(c = csq.charAt(index++))) {
                        throw new IllegalArgumentException("Expected low surrogate character but got " + c);
                    }
                    escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                    if (escaped != null) {
                        this.outputChars(escaped, escaped.length);
                        ++unescapedChunkStart;
                    } else {
                        out.append((char)this.pendingHighSurrogate);
                    }
                    this.pendingHighSurrogate = -1;
                }
                while (true) {
                    if ((index = UnicodeEscaper.this.nextEscapeIndex(csq, index, end)) > unescapedChunkStart) {
                        out.append(csq, unescapedChunkStart, index);
                    }
                    if (index == end) {
                        return this;
                    }
                    int cp = UnicodeEscaper.codePointAt(csq, index, end);
                    if (cp < 0) {
                        this.pendingHighSurrogate = -cp;
                        return this;
                    }
                    escaped = UnicodeEscaper.this.escape(cp);
                    if (escaped != null) {
                        this.outputChars(escaped, escaped.length);
                    } else {
                        int len = Character.toChars(cp, this.decodedChars, 0);
                        this.outputChars(this.decodedChars, len);
                    }
                    unescapedChunkStart = index += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
                }
            }

            @Override
            public Appendable append(char c) throws IOException {
                if (this.pendingHighSurrogate != -1) {
                    if (!Character.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
                    }
                    char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                    if (escaped != null) {
                        this.outputChars(escaped, escaped.length);
                    } else {
                        out.append((char)this.pendingHighSurrogate);
                        out.append(c);
                    }
                    this.pendingHighSurrogate = -1;
                    return this;
                }
                if (Character.isHighSurrogate(c)) {
                    this.pendingHighSurrogate = c;
                    return this;
                }
                if (Character.isLowSurrogate(c)) {
                    throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
                }
                char[] escaped = UnicodeEscaper.this.escape(c);
                if (escaped != null) {
                    this.outputChars(escaped, escaped.length);
                    return this;
                }
                out.append(c);
                return this;
            }

            private void outputChars(char[] chars, int len) throws IOException {
                int n = 0;
                while (n < len) {
                    out.append(chars[n]);
                    ++n;
                }
            }
        };
        if (out != null) return new /* invalid duplicate definition of identical inner class */;
        throw new AssertionError();
    }

    protected static final int codePointAt(CharSequence seq, int index, int end) {
        char c1;
        if (index >= end) throw new IndexOutOfBoundsException("Index exceeds specified range");
        if ((c1 = seq.charAt(index++)) < '\ud800') return c1;
        if (c1 > '\udfff') {
            return c1;
        }
        if (c1 > '\udbff') throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index - 1));
        if (index == end) {
            return -c1;
        }
        char c2 = seq.charAt(index);
        if (!Character.isLowSurrogate(c2)) throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index);
        return Character.toCodePoint(c1, c2);
    }

    private static final char[] growBuffer(char[] dest, int index, int size) {
        char[] copy = new char[size];
        if (index <= 0) return copy;
        System.arraycopy(dest, 0, copy, 0, index);
        return copy;
    }
}

