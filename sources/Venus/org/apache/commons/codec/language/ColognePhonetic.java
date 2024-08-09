/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class ColognePhonetic
implements StringEncoder {
    private static final char[] AEIJOUY = new char[]{'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
    private static final char[] SCZ = new char[]{'S', 'C', 'Z'};
    private static final char[] WFPV = new char[]{'W', 'F', 'P', 'V'};
    private static final char[] GKQ = new char[]{'G', 'K', 'Q'};
    private static final char[] CKQ = new char[]{'C', 'K', 'Q'};
    private static final char[] AHKLOQRUX = new char[]{'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
    private static final char[] SZ = new char[]{'S', 'Z'};
    private static final char[] AHOUKQX = new char[]{'A', 'H', 'O', 'U', 'K', 'Q', 'X'};
    private static final char[] TDX = new char[]{'T', 'D', 'X'};
    private static final char[][] PREPROCESS_MAP = new char[][]{{'\u00c4', 'A'}, {'\u00dc', 'U'}, {'\u00d6', 'O'}, {'\u00df', 'S'}};

    private static boolean arrayContains(char[] cArray, char c) {
        for (char c2 : cArray) {
            if (c2 != c) continue;
            return false;
        }
        return true;
    }

    public String colognePhonetic(String string) {
        if (string == null) {
            return null;
        }
        string = this.preprocess(string);
        CologneOutputBuffer cologneOutputBuffer = new CologneOutputBuffer(this, string.length() * 2);
        CologneInputBuffer cologneInputBuffer = new CologneInputBuffer(this, string.toCharArray());
        char c = '-';
        int n = 47;
        int n2 = cologneInputBuffer.length();
        while (n2 > 0) {
            int n3;
            char c2 = cologneInputBuffer.removeNext();
            n2 = cologneInputBuffer.length();
            char c3 = n2 > 0 ? (char)cologneInputBuffer.getNextChar() : (char)'-';
            if (ColognePhonetic.arrayContains(AEIJOUY, c2)) {
                n3 = 48;
            } else if (c2 == 'H' || c2 < 'A' || c2 > 'Z') {
                if (n == 47) continue;
                n3 = 45;
            } else if (c2 == 'B' || c2 == 'P' && c3 != 'H') {
                n3 = 49;
            } else if (!(c2 != 'D' && c2 != 'T' || ColognePhonetic.arrayContains(SCZ, c3))) {
                n3 = 50;
            } else if (ColognePhonetic.arrayContains(WFPV, c2)) {
                n3 = 51;
            } else if (ColognePhonetic.arrayContains(GKQ, c2)) {
                n3 = 52;
            } else if (c2 == 'X' && !ColognePhonetic.arrayContains(CKQ, c)) {
                n3 = 52;
                cologneInputBuffer.addLeft('S');
                ++n2;
            } else {
                n3 = c2 == 'S' || c2 == 'Z' ? 56 : (c2 == 'C' ? (n == 47 ? (ColognePhonetic.arrayContains(AHKLOQRUX, c3) ? 52 : 56) : (ColognePhonetic.arrayContains(SZ, c) || !ColognePhonetic.arrayContains(AHOUKQX, c3) ? 56 : 52)) : (ColognePhonetic.arrayContains(TDX, c2) ? 56 : (c2 == 'R' ? 55 : (c2 == 'L' ? 53 : (c2 == 'M' || c2 == 'N' ? 54 : (int)c2)))));
            }
            if (n3 != 45 && (n != n3 && (n3 != 48 || n == 47) || n3 < 48 || n3 > 56)) {
                cologneOutputBuffer.addRight((char)n3);
            }
            c = c2;
            n = n3;
        }
        return cologneOutputBuffer.toString();
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + object.getClass().getName() + ".");
        }
        return this.encode((String)object);
    }

    @Override
    public String encode(String string) {
        return this.colognePhonetic(string);
    }

    public boolean isEncodeEqual(String string, String string2) {
        return this.colognePhonetic(string).equals(this.colognePhonetic(string2));
    }

    private String preprocess(String string) {
        string = string.toUpperCase(Locale.GERMAN);
        char[] cArray = string.toCharArray();
        block0: for (int i = 0; i < cArray.length; ++i) {
            if (cArray[i] <= 'Z') continue;
            for (char[] cArray2 : PREPROCESS_MAP) {
                if (cArray[i] != cArray2[0]) continue;
                cArray[i] = cArray2[1];
                continue block0;
            }
        }
        return new String(cArray);
    }

    private class CologneInputBuffer
    extends CologneBuffer {
        final ColognePhonetic this$0;

        public CologneInputBuffer(ColognePhonetic colognePhonetic, char[] cArray) {
            this.this$0 = colognePhonetic;
            super(colognePhonetic, cArray);
        }

        public void addLeft(char c) {
            ++this.length;
            this.data[this.getNextPos()] = c;
        }

        @Override
        protected char[] copyData(int n, int n2) {
            char[] cArray = new char[n2];
            System.arraycopy(this.data, this.data.length - this.length + n, cArray, 0, n2);
            return cArray;
        }

        public char getNextChar() {
            return this.data[this.getNextPos()];
        }

        protected int getNextPos() {
            return this.data.length - this.length;
        }

        public char removeNext() {
            char c = this.getNextChar();
            --this.length;
            return c;
        }
    }

    private class CologneOutputBuffer
    extends CologneBuffer {
        final ColognePhonetic this$0;

        public CologneOutputBuffer(ColognePhonetic colognePhonetic, int n) {
            this.this$0 = colognePhonetic;
            super(colognePhonetic, n);
        }

        public void addRight(char c) {
            this.data[this.length] = c;
            ++this.length;
        }

        @Override
        protected char[] copyData(int n, int n2) {
            char[] cArray = new char[n2];
            System.arraycopy(this.data, n, cArray, 0, n2);
            return cArray;
        }
    }

    private abstract class CologneBuffer {
        protected final char[] data;
        protected int length;
        final ColognePhonetic this$0;

        public CologneBuffer(ColognePhonetic colognePhonetic, char[] cArray) {
            this.this$0 = colognePhonetic;
            this.length = 0;
            this.data = cArray;
            this.length = cArray.length;
        }

        public CologneBuffer(ColognePhonetic colognePhonetic, int n) {
            this.this$0 = colognePhonetic;
            this.length = 0;
            this.data = new char[n];
            this.length = 0;
        }

        protected abstract char[] copyData(int var1, int var2);

        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(this.copyData(0, this.length));
        }
    }
}

