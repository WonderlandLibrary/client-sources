/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.IOException;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.Escaper;

public abstract class UnicodeEscaper
implements Escaper {
    private static final int DEST_PAD = 32;
    private static final ThreadLocal<char[]> DEST_TL;
    static final boolean $assertionsDisabled;

    protected abstract char[] escape(int var1);

    protected int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        int n3;
        int n4;
        for (n3 = n; n3 < n2 && (n4 = UnicodeEscaper.codePointAt(charSequence, n3, n2)) >= 0 && this.escape(n4) == null; n3 += Character.isSupplementaryCodePoint(n4) ? 2 : 1) {
        }
        return n3;
    }

    @Override
    public String escape(String string) {
        int n = string.length();
        int n2 = this.nextEscapeIndex(string, 0, n);
        return n2 == n ? string : this.escapeSlow(string, n2);
    }

    protected final String escapeSlow(String string, int n) {
        int n2;
        int n3 = string.length();
        char[] cArray = DEST_TL.get();
        int n4 = 0;
        int n5 = 0;
        while (n < n3) {
            n2 = UnicodeEscaper.codePointAt(string, n, n3);
            if (n2 < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] cArray2 = this.escape(n2);
            if (cArray2 != null) {
                int n6 = n - n5;
                int n7 = n4 + n6 + cArray2.length;
                if (cArray.length < n7) {
                    int n8 = n7 + (n3 - n) + 32;
                    cArray = UnicodeEscaper.growBuffer(cArray, n4, n8);
                }
                if (n6 > 0) {
                    string.getChars(n5, n, cArray, n4);
                    n4 += n6;
                }
                if (cArray2.length > 0) {
                    System.arraycopy(cArray2, 0, cArray, n4, cArray2.length);
                    n4 += cArray2.length;
                }
            }
            n5 = n + (Character.isSupplementaryCodePoint(n2) ? 2 : 1);
            n = this.nextEscapeIndex(string, n5, n3);
        }
        n2 = n3 - n5;
        if (n2 > 0) {
            int n9 = n4 + n2;
            if (cArray.length < n9) {
                cArray = UnicodeEscaper.growBuffer(cArray, n4, n9);
            }
            string.getChars(n5, n3, cArray, n4);
            n4 = n9;
        }
        return new String(cArray, 0, n4);
    }

    @Override
    public Appendable escape(Appendable appendable) {
        if (!$assertionsDisabled && appendable == null) {
            throw new AssertionError();
        }
        return new Appendable(this, appendable){
            int pendingHighSurrogate;
            final char[] decodedChars;
            final Appendable val$out;
            final UnicodeEscaper this$0;
            {
                this.this$0 = unicodeEscaper;
                this.val$out = appendable;
                this.pendingHighSurrogate = -1;
                this.decodedChars = new char[2];
            }

            @Override
            public Appendable append(CharSequence charSequence) throws IOException {
                return this.append(charSequence, 0, charSequence.length());
            }

            @Override
            public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
                int n3 = n;
                if (n3 < n2) {
                    char[] cArray;
                    int n4;
                    int n5 = n3;
                    if (this.pendingHighSurrogate != -1) {
                        if (!Character.isLowSurrogate((char)(n4 = charSequence.charAt(n3++)))) {
                            throw new IllegalArgumentException("Expected low surrogate character but got " + (char)n4);
                        }
                        cArray = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, (char)n4));
                        if (cArray != null) {
                            this.outputChars(cArray, cArray.length);
                            ++n5;
                        } else {
                            this.val$out.append((char)this.pendingHighSurrogate);
                        }
                        this.pendingHighSurrogate = -1;
                    }
                    while (true) {
                        if ((n3 = this.this$0.nextEscapeIndex(charSequence, n3, n2)) > n5) {
                            this.val$out.append(charSequence, n5, n3);
                        }
                        if (n3 == n2) break;
                        n4 = UnicodeEscaper.codePointAt(charSequence, n3, n2);
                        if (n4 < 0) {
                            this.pendingHighSurrogate = -n4;
                            break;
                        }
                        cArray = this.this$0.escape(n4);
                        if (cArray != null) {
                            this.outputChars(cArray, cArray.length);
                        } else {
                            int n6 = Character.toChars(n4, this.decodedChars, 0);
                            this.outputChars(this.decodedChars, n6);
                        }
                        n5 = n3 += Character.isSupplementaryCodePoint(n4) ? 2 : 1;
                    }
                }
                return this;
            }

            @Override
            public Appendable append(char c) throws IOException {
                if (this.pendingHighSurrogate != -1) {
                    if (!Character.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
                    }
                    char[] cArray = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                    if (cArray != null) {
                        this.outputChars(cArray, cArray.length);
                    } else {
                        this.val$out.append((char)this.pendingHighSurrogate);
                        this.val$out.append(c);
                    }
                    this.pendingHighSurrogate = -1;
                } else if (Character.isHighSurrogate(c)) {
                    this.pendingHighSurrogate = c;
                } else {
                    if (Character.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
                    }
                    char[] cArray = this.this$0.escape(c);
                    if (cArray != null) {
                        this.outputChars(cArray, cArray.length);
                    } else {
                        this.val$out.append(c);
                    }
                }
                return this;
            }

            private void outputChars(char[] cArray, int n) throws IOException {
                for (int i = 0; i < n; ++i) {
                    this.val$out.append(cArray[i]);
                }
            }
        };
    }

    protected static final int codePointAt(CharSequence charSequence, int n, int n2) {
        if (n < n2) {
            char c;
            if ((c = charSequence.charAt(n++)) < '\ud800' || c > '\udfff') {
                return c;
            }
            if (c <= '\udbff') {
                if (n == n2) {
                    return -c;
                }
                char c2 = charSequence.charAt(n);
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c, c2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + n);
            }
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c + " at index " + (n - 1));
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static final char[] growBuffer(char[] cArray, int n, int n2) {
        char[] cArray2 = new char[n2];
        if (n > 0) {
            System.arraycopy(cArray, 0, cArray2, 0, n);
        }
        return cArray2;
    }

    static {
        $assertionsDisabled = !UnicodeEscaper.class.desiredAssertionStatus();
        DEST_TL = new ThreadLocal<char[]>(){

            @Override
            protected char[] initialValue() {
                return new char[1024];
            }

            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
    }
}

