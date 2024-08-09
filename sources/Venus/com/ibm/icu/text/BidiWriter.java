/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import com.ibm.icu.text.UTF16;

final class BidiWriter {
    static final char LRM_CHAR = '\u200e';
    static final char RLM_CHAR = '\u200f';
    static final int MASK_R_AL = 8194;

    BidiWriter() {
    }

    private static boolean IsCombining(int n) {
        return (1 << n & 0x1C0) != 0;
    }

    private static String doWriteForward(String string, int n) {
        switch (n & 0xA) {
            case 0: {
                return string;
            }
            case 2: {
                int n2;
                StringBuffer stringBuffer = new StringBuffer(string.length());
                int n3 = 0;
                do {
                    n2 = UTF16.charAt(string, n3);
                    UTF16.append(stringBuffer, UCharacter.getMirror(n2));
                } while ((n3 += UTF16.getCharCount(n2)) < string.length());
                return stringBuffer.toString();
            }
            case 8: {
                StringBuilder stringBuilder = new StringBuilder(string.length());
                int n4 = 0;
                do {
                    char c;
                    if (Bidi.IsBidiControlChar(c = string.charAt(n4++))) continue;
                    stringBuilder.append(c);
                } while (n4 < string.length());
                return stringBuilder.toString();
            }
        }
        StringBuffer stringBuffer = new StringBuffer(string.length());
        int n5 = 0;
        do {
            int n6 = UTF16.charAt(string, n5);
            n5 += UTF16.getCharCount(n6);
            if (Bidi.IsBidiControlChar(n6)) continue;
            UTF16.append(stringBuffer, UCharacter.getMirror(n6));
        } while (n5 < string.length());
        return stringBuffer.toString();
    }

    private static String doWriteForward(char[] cArray, int n, int n2, int n3) {
        return BidiWriter.doWriteForward(new String(cArray, n, n2 - n), n3);
    }

    static String writeReverse(String string, int n) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        switch (n & 0xB) {
            case 0: {
                int n2 = string.length();
                do {
                    int n3 = n2;
                    n2 -= UTF16.getCharCount(UTF16.charAt(string, n2 - 1));
                    stringBuffer.append(string.substring(n2, n3));
                } while (n2 > 0);
                break;
            }
            case 1: {
                int n4 = string.length();
                do {
                    int n5;
                    int n6 = n4;
                    while ((n4 -= UTF16.getCharCount(n5 = UTF16.charAt(string, n4 - 1))) > 0 && BidiWriter.IsCombining(UCharacter.getType(n5))) {
                    }
                    stringBuffer.append(string.substring(n4, n6));
                } while (n4 > 0);
                break;
            }
            default: {
                int n7 = string.length();
                do {
                    int n8 = n7;
                    int n9 = UTF16.charAt(string, n7 - 1);
                    n7 -= UTF16.getCharCount(n9);
                    if ((n & 1) != 0) {
                        while (n7 > 0 && BidiWriter.IsCombining(UCharacter.getType(n9))) {
                            n9 = UTF16.charAt(string, n7 - 1);
                            n7 -= UTF16.getCharCount(n9);
                        }
                    }
                    if ((n & 8) != 0 && Bidi.IsBidiControlChar(n9)) continue;
                    int n10 = n7;
                    if ((n & 2) != 0) {
                        n9 = UCharacter.getMirror(n9);
                        UTF16.append(stringBuffer, n9);
                        n10 += UTF16.getCharCount(n9);
                    }
                    stringBuffer.append(string.substring(n10, n8));
                } while (n7 > 0);
            }
        }
        return stringBuffer.toString();
    }

    static String doWriteReverse(char[] cArray, int n, int n2, int n3) {
        return BidiWriter.writeReverse(new String(cArray, n, n2 - n), n3);
    }

    static String writeReordered(Bidi bidi, int n) {
        char[] cArray = bidi.text;
        int n2 = bidi.countRuns();
        if ((bidi.reorderingOptions & 1) != 0) {
            n |= 4;
            n &= 0xFFFFFFF7;
        }
        if ((bidi.reorderingOptions & 2) != 0) {
            n |= 8;
            n &= 0xFFFFFFFB;
        }
        if (bidi.reorderingMode != 4 && bidi.reorderingMode != 5 && bidi.reorderingMode != 6 && bidi.reorderingMode != 3) {
            n &= 0xFFFFFFFB;
        }
        StringBuilder stringBuilder = new StringBuilder((n & 4) != 0 ? bidi.length * 2 : bidi.length);
        if ((n & 0x10) == 0) {
            if ((n & 4) == 0) {
                for (int i = 0; i < n2; ++i) {
                    BidiRun bidiRun = bidi.getVisualRun(i);
                    if (bidiRun.isEvenRun()) {
                        stringBuilder.append(BidiWriter.doWriteForward(cArray, bidiRun.start, bidiRun.limit, n & 0xFFFFFFFD));
                        continue;
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(cArray, bidiRun.start, bidiRun.limit, n));
                }
            } else {
                byte[] byArray = bidi.dirProps;
                for (int i = 0; i < n2; ++i) {
                    char c;
                    BidiRun bidiRun = bidi.getVisualRun(i);
                    int n3 = 0;
                    n3 = bidi.runs[i].insertRemove;
                    if (n3 < 0) {
                        n3 = 0;
                    }
                    if (bidiRun.isEvenRun()) {
                        if (bidi.isInverse() && byArray[bidiRun.start] != 0) {
                            n3 |= 1;
                        }
                        if ((c = (n3 & 1) != 0 ? (char)'\u200e' : ((n3 & 4) != 0 ? (char)'\u200f' : '\u0000')) != '\u0000') {
                            stringBuilder.append(c);
                        }
                        stringBuilder.append(BidiWriter.doWriteForward(cArray, bidiRun.start, bidiRun.limit, n & 0xFFFFFFFD));
                        if (bidi.isInverse() && byArray[bidiRun.limit - 1] != 0) {
                            n3 |= 2;
                        }
                        if ((c = (n3 & 2) != 0 ? (char)'\u200e' : ((n3 & 8) != 0 ? (char)'\u200f' : '\u0000')) == '\u0000') continue;
                        stringBuilder.append(c);
                        continue;
                    }
                    if (bidi.isInverse() && !bidi.testDirPropFlagAt(8194, bidiRun.limit - 1)) {
                        n3 |= 4;
                    }
                    if ((c = (n3 & 1) != 0 ? (char)'\u200e' : ((n3 & 4) != 0 ? (char)'\u200f' : '\u0000')) != '\u0000') {
                        stringBuilder.append(c);
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(cArray, bidiRun.start, bidiRun.limit, n));
                    if (bidi.isInverse() && (0x2002 & Bidi.DirPropFlag(byArray[bidiRun.start])) == 0) {
                        n3 |= 8;
                    }
                    if ((c = (n3 & 2) != 0 ? (char)'\u200e' : ((n3 & 8) != 0 ? (char)'\u200f' : '\u0000')) == '\u0000') continue;
                    stringBuilder.append(c);
                }
            }
        } else if ((n & 4) == 0) {
            int n4 = n2;
            while (--n4 >= 0) {
                BidiRun bidiRun = bidi.getVisualRun(n4);
                if (bidiRun.isEvenRun()) {
                    stringBuilder.append(BidiWriter.doWriteReverse(cArray, bidiRun.start, bidiRun.limit, n & 0xFFFFFFFD));
                    continue;
                }
                stringBuilder.append(BidiWriter.doWriteForward(cArray, bidiRun.start, bidiRun.limit, n));
            }
        } else {
            byte[] byArray = bidi.dirProps;
            int n5 = n2;
            while (--n5 >= 0) {
                BidiRun bidiRun = bidi.getVisualRun(n5);
                if (bidiRun.isEvenRun()) {
                    if (byArray[bidiRun.limit - 1] != 0) {
                        stringBuilder.append('\u200e');
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(cArray, bidiRun.start, bidiRun.limit, n & 0xFFFFFFFD));
                    if (byArray[bidiRun.start] == 0) continue;
                    stringBuilder.append('\u200e');
                    continue;
                }
                if ((0x2002 & Bidi.DirPropFlag(byArray[bidiRun.start])) == 0) {
                    stringBuilder.append('\u200f');
                }
                stringBuilder.append(BidiWriter.doWriteForward(cArray, bidiRun.start, bidiRun.limit, n));
                if ((0x2002 & Bidi.DirPropFlag(byArray[bidiRun.limit - 1])) != 0) continue;
                stringBuilder.append('\u200f');
            }
        }
        return stringBuilder.toString();
    }
}

