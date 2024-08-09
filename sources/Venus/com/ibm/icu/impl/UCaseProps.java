/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;

public final class UCaseProps {
    private static final byte[] flagsOffset = new byte[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};
    public static final int MAX_STRING_LENGTH = 31;
    public static final int LOC_ROOT = 1;
    static final int LOC_TURKISH = 2;
    static final int LOC_LITHUANIAN = 3;
    static final int LOC_GREEK = 4;
    public static final int LOC_DUTCH = 5;
    private static final String iDot = "i\u0307";
    private static final String jDot = "j\u0307";
    private static final String iOgonekDot = "\u012f\u0307";
    private static final String iDotGrave = "i\u0307\u0300";
    private static final String iDotAcute = "i\u0307\u0301";
    private static final String iDotTilde = "i\u0307\u0303";
    static final int FOLD_CASE_OPTIONS_MASK = 7;
    public static final StringBuilder dummyStringBuilder = new StringBuilder();
    private int[] indexes;
    private String exceptions;
    private char[] unfold;
    private Trie2_16 trie;
    private static final String DATA_NAME = "ucase";
    private static final String DATA_TYPE = "icu";
    private static final String DATA_FILE_NAME = "ucase.icu";
    private static final int FMT = 1665225541;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_EXC_LENGTH = 3;
    private static final int IX_UNFOLD_LENGTH = 4;
    private static final int IX_TOP = 16;
    public static final int TYPE_MASK = 3;
    public static final int NONE = 0;
    public static final int LOWER = 1;
    public static final int UPPER = 2;
    public static final int TITLE = 3;
    static final int IGNORABLE = 4;
    private static final int EXCEPTION = 8;
    private static final int SENSITIVE = 16;
    private static final int DOT_MASK = 96;
    private static final int SOFT_DOTTED = 32;
    private static final int ABOVE = 64;
    private static final int OTHER_ACCENT = 96;
    private static final int DELTA_SHIFT = 7;
    private static final int EXC_SHIFT = 4;
    private static final int EXC_LOWER = 0;
    private static final int EXC_FOLD = 1;
    private static final int EXC_UPPER = 2;
    private static final int EXC_TITLE = 3;
    private static final int EXC_DELTA = 4;
    private static final int EXC_CLOSURE = 6;
    private static final int EXC_FULL_MAPPINGS = 7;
    private static final int EXC_DOUBLE_SLOTS = 256;
    private static final int EXC_NO_SIMPLE_CASE_FOLDING = 512;
    private static final int EXC_DELTA_IS_NEGATIVE = 1024;
    private static final int EXC_SENSITIVE = 2048;
    private static final int EXC_DOT_SHIFT = 7;
    private static final int EXC_CONDITIONAL_SPECIAL = 16384;
    private static final int EXC_CONDITIONAL_FOLD = 32768;
    private static final int FULL_LOWER = 15;
    private static final int CLOSURE_MAX_LENGTH = 15;
    private static final int UNFOLD_ROWS = 0;
    private static final int UNFOLD_ROW_WIDTH = 1;
    private static final int UNFOLD_STRING_WIDTH = 2;
    public static final UCaseProps INSTANCE;

    private UCaseProps() throws IOException {
        ByteBuffer byteBuffer = ICUBinary.getRequiredData(DATA_FILE_NAME);
        this.readData(byteBuffer);
    }

    private final void readData(ByteBuffer byteBuffer) throws IOException {
        int n;
        ICUBinary.readHeader(byteBuffer, 1665225541, new IsAcceptable(null));
        int n2 = byteBuffer.getInt();
        if (n2 < 16) {
            throw new IOException("indexes[0] too small in ucase.icu");
        }
        this.indexes = new int[n2];
        this.indexes[0] = n2;
        for (n = 1; n < n2; ++n) {
            this.indexes[n] = byteBuffer.getInt();
        }
        this.trie = Trie2_16.createFromSerialized(byteBuffer);
        n = this.indexes[2];
        int n3 = this.trie.getSerializedLength();
        if (n3 > n) {
            throw new IOException("ucase.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(byteBuffer, n - n3);
        n2 = this.indexes[3];
        if (n2 > 0) {
            this.exceptions = ICUBinary.getString(byteBuffer, n2, 0);
        }
        if ((n2 = this.indexes[4]) > 0) {
            this.unfold = ICUBinary.getChars(byteBuffer, n2, 0);
        }
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        for (Trie2.Range range : this.trie) {
            if (range.leadSurrogate) break;
            unicodeSet.add(range.startCodePoint);
        }
    }

    private static final int getExceptionsOffset(int n) {
        return n >> 4;
    }

    static final boolean propsHasException(int n) {
        return (n & 8) != 0;
    }

    private static final boolean hasSlot(int n, int n2) {
        return (n & 1 << n2) != 0;
    }

    private static final byte slotOffset(int n, int n2) {
        return flagsOffset[n & (1 << n2) - 1];
    }

    private final long getSlotValueAndOffset(int n, int n2, int n3) {
        long l;
        if ((n & 0x100) == 0) {
            l = this.exceptions.charAt(n3 += UCaseProps.slotOffset(n, n2));
        } else {
            n3 += 2 * UCaseProps.slotOffset(n, n2);
            l = this.exceptions.charAt(n3++);
            l = l << 16 | (long)this.exceptions.charAt(n3);
        }
        return l | (long)n3 << 32;
    }

    private final int getSlotValue(int n, int n2, int n3) {
        int n4;
        if ((n & 0x100) == 0) {
            n4 = this.exceptions.charAt(n3 += UCaseProps.slotOffset(n, n2));
        } else {
            n3 += 2 * UCaseProps.slotOffset(n, n2);
            n4 = this.exceptions.charAt(n3++);
            n4 = n4 << 16 | this.exceptions.charAt(n3);
        }
        return n4;
    }

    public final int tolower(int n) {
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            if (UCaseProps.isUpperOrTitleFromProps(n2)) {
                n += UCaseProps.getDelta(n2);
            }
        } else {
            char c;
            int n3 = UCaseProps.getExceptionsOffset(n2);
            if (UCaseProps.hasSlot(c = this.exceptions.charAt(n3++), 4) && UCaseProps.isUpperOrTitleFromProps(n2)) {
                int n4 = this.getSlotValue(c, 4, n3);
                return (c & 0x400) == 0 ? n + n4 : n - n4;
            }
            if (UCaseProps.hasSlot(c, 0)) {
                n = this.getSlotValue(c, 0, n3);
            }
        }
        return n;
    }

    public final int toupper(int n) {
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            if (UCaseProps.getTypeFromProps(n2) == 1) {
                n += UCaseProps.getDelta(n2);
            }
        } else {
            char c;
            int n3 = UCaseProps.getExceptionsOffset(n2);
            if (UCaseProps.hasSlot(c = this.exceptions.charAt(n3++), 4) && UCaseProps.getTypeFromProps(n2) == 1) {
                int n4 = this.getSlotValue(c, 4, n3);
                return (c & 0x400) == 0 ? n + n4 : n - n4;
            }
            if (UCaseProps.hasSlot(c, 2)) {
                n = this.getSlotValue(c, 2, n3);
            }
        }
        return n;
    }

    public final int totitle(int n) {
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            if (UCaseProps.getTypeFromProps(n2) == 1) {
                n += UCaseProps.getDelta(n2);
            }
        } else {
            int n3;
            char c;
            int n4 = UCaseProps.getExceptionsOffset(n2);
            if (UCaseProps.hasSlot(c = this.exceptions.charAt(n4++), 4) && UCaseProps.getTypeFromProps(n2) == 1) {
                int n5 = this.getSlotValue(c, 4, n4);
                return (c & 0x400) == 0 ? n + n5 : n - n5;
            }
            if (UCaseProps.hasSlot(c, 3)) {
                n3 = 3;
            } else if (UCaseProps.hasSlot(c, 2)) {
                n3 = 2;
            } else {
                return n;
            }
            n = this.getSlotValue(c, n3, n4);
        }
        return n;
    }

    public final void addCaseClosure(int n, UnicodeSet unicodeSet) {
        switch (n) {
            case 73: {
                unicodeSet.add(105);
                return;
            }
            case 105: {
                unicodeSet.add(73);
                return;
            }
            case 304: {
                unicodeSet.add(iDot);
                return;
            }
            case 305: {
                return;
            }
        }
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            int n3;
            if (UCaseProps.getTypeFromProps(n2) != 0 && (n3 = UCaseProps.getDelta(n2)) != 0) {
                unicodeSet.add(n + n3);
            }
        } else {
            int n4;
            int n5;
            int n6;
            int n7 = UCaseProps.getExceptionsOffset(n2);
            char c = this.exceptions.charAt(n7++);
            int n8 = n7;
            for (n6 = 0; n6 <= 3; ++n6) {
                if (!UCaseProps.hasSlot(c, n6)) continue;
                n7 = n8;
                n = this.getSlotValue(c, n6, n7);
                unicodeSet.add(n);
            }
            if (UCaseProps.hasSlot(c, 4)) {
                n7 = n8;
                int n9 = this.getSlotValue(c, 4, n7);
                unicodeSet.add((c & 0x400) == 0 ? n + n9 : n - n9);
            }
            if (UCaseProps.hasSlot(c, 6)) {
                n7 = n8;
                long l = this.getSlotValueAndOffset(c, 6, n7);
                n5 = (int)l & 0xF;
                n4 = (int)(l >> 32) + 1;
            } else {
                n5 = 0;
                n4 = 0;
            }
            if (UCaseProps.hasSlot(c, 7)) {
                n7 = n8;
                long l = this.getSlotValueAndOffset(c, 7, n7);
                int n10 = (int)l;
                n7 = (int)(l >> 32) + 1;
                n7 += (n10 &= 0xFFFF) & 0xF;
                int n11 = (n10 >>= 4) & 0xF;
                if (n11 != 0) {
                    unicodeSet.add(this.exceptions.substring(n7, n7 + n11));
                    n7 += n11;
                }
                n7 += (n10 >>= 4) & 0xF;
                n4 = n7 += (n10 >>= 4);
            }
            int n12 = n4 + n5;
            for (n6 = n4; n6 < n12; n6 += UTF16.getCharCount(n)) {
                n = this.exceptions.codePointAt(n6);
                unicodeSet.add(n);
            }
        }
    }

    private final int strcmpMax(String string, int n, int n2) {
        int n3 = string.length();
        n2 -= n3;
        int n4 = 0;
        do {
            char c;
            int n5 = string.charAt(n4++);
            if ((c = this.unfold[n++]) == '\u0000') {
                return 0;
            }
            if ((n5 -= c) == 0) continue;
            return n5;
        } while (--n3 > 0);
        if (n2 == 0 || this.unfold[n] == '\u0000') {
            return 1;
        }
        return -n2;
    }

    public final boolean addStringCaseClosure(String string, UnicodeSet unicodeSet) {
        if (this.unfold == null || string == null) {
            return true;
        }
        int n = string.length();
        if (n <= 1) {
            return true;
        }
        int n2 = this.unfold[0];
        char c = this.unfold[1];
        int n3 = this.unfold[2];
        if (n > n3) {
            return true;
        }
        int n4 = 0;
        int n5 = n2;
        while (n4 < n5) {
            int n6 = (n4 + n5) / 2;
            int n7 = (n6 + 1) * c;
            int n8 = this.strcmpMax(string, n7, n3);
            if (n8 == 0) {
                int n9;
                for (n6 = n3; n6 < c && this.unfold[n7 + n6] != '\u0000'; n6 += UTF16.getCharCount(n9)) {
                    n9 = UTF16.charAt(this.unfold, n7, this.unfold.length, n6);
                    unicodeSet.add(n9);
                    this.addCaseClosure(n9, unicodeSet);
                }
                return false;
            }
            if (n8 < 0) {
                n5 = n6;
                continue;
            }
            n4 = n6 + 1;
        }
        return true;
    }

    public final int getType(int n) {
        return UCaseProps.getTypeFromProps(this.trie.get(n));
    }

    public final int getTypeOrIgnorable(int n) {
        return UCaseProps.getTypeAndIgnorableFromProps(this.trie.get(n));
    }

    public final int getDotType(int n) {
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            return n2 & 0x60;
        }
        return this.exceptions.charAt(UCaseProps.getExceptionsOffset(n2)) >> 7 & 0x60;
    }

    public final boolean isSoftDotted(int n) {
        return this.getDotType(n) == 32;
    }

    public final boolean isCaseSensitive(int n) {
        int n2 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n2)) {
            return (n2 & 0x10) != 0;
        }
        return (this.exceptions.charAt(UCaseProps.getExceptionsOffset(n2)) & 0x800) != 0;
    }

    public static final int getCaseLocale(Locale locale) {
        return UCaseProps.getCaseLocale(locale.getLanguage());
    }

    public static final int getCaseLocale(ULocale uLocale) {
        return UCaseProps.getCaseLocale(uLocale.getLanguage());
    }

    private static final int getCaseLocale(String string) {
        if (string.length() == 2) {
            if (string.equals("en") || string.charAt(0) > 't') {
                return 0;
            }
            if (string.equals("tr") || string.equals("az")) {
                return 1;
            }
            if (string.equals("el")) {
                return 1;
            }
            if (string.equals("lt")) {
                return 0;
            }
            if (string.equals("nl")) {
                return 0;
            }
        } else if (string.length() == 3) {
            if (string.equals("tur") || string.equals("aze")) {
                return 1;
            }
            if (string.equals("ell")) {
                return 1;
            }
            if (string.equals("lit")) {
                return 0;
            }
            if (string.equals("nld")) {
                return 0;
            }
        }
        return 0;
    }

    private final boolean isFollowedByCasedLetter(ContextIterator contextIterator, int n) {
        int n2;
        if (contextIterator == null) {
            return true;
        }
        contextIterator.reset(n);
        while ((n2 = contextIterator.next()) >= 0) {
            int n3 = this.getTypeOrIgnorable(n2);
            if ((n3 & 4) != 0) continue;
            return n3 == 0;
        }
        return true;
    }

    private final boolean isPrecededBySoftDotted(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return true;
        }
        contextIterator.reset(-1);
        while ((n = contextIterator.next()) >= 0) {
            int n2 = this.getDotType(n);
            if (n2 == 32) {
                return false;
            }
            if (n2 == 96) continue;
            return true;
        }
        return true;
    }

    private final boolean isPrecededBy_I(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return true;
        }
        contextIterator.reset(-1);
        while ((n = contextIterator.next()) >= 0) {
            if (n == 73) {
                return false;
            }
            int n2 = this.getDotType(n);
            if (n2 == 96) continue;
            return true;
        }
        return true;
    }

    private final boolean isFollowedByMoreAbove(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return true;
        }
        contextIterator.reset(1);
        while ((n = contextIterator.next()) >= 0) {
            int n2 = this.getDotType(n);
            if (n2 == 64) {
                return false;
            }
            if (n2 == 96) continue;
            return true;
        }
        return true;
    }

    private final boolean isFollowedByDotAbove(ContextIterator contextIterator) {
        int n;
        if (contextIterator == null) {
            return true;
        }
        contextIterator.reset(1);
        while ((n = contextIterator.next()) >= 0) {
            if (n == 775) {
                return false;
            }
            int n2 = this.getDotType(n);
            if (n2 == 96) continue;
            return true;
        }
        return true;
    }

    public final int toFullLower(int n, ContextIterator contextIterator, Appendable appendable, int n2) {
        int n3 = n;
        int n4 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n4)) {
            if (UCaseProps.isUpperOrTitleFromProps(n4)) {
                n3 = n + UCaseProps.getDelta(n4);
            }
        } else {
            long l;
            int n5;
            int n6 = UCaseProps.getExceptionsOffset(n4);
            char c = this.exceptions.charAt(n6++);
            int n7 = n6;
            if ((c & 0x4000) != 0) {
                if (n2 == 3 && ((n == 73 || n == 74 || n == 302) && this.isFollowedByMoreAbove(contextIterator) || n == 204 || n == 205 || n == 296)) {
                    try {
                        switch (n) {
                            case 73: {
                                appendable.append(iDot);
                                return 2;
                            }
                            case 74: {
                                appendable.append(jDot);
                                return 2;
                            }
                            case 302: {
                                appendable.append(iOgonekDot);
                                return 2;
                            }
                            case 204: {
                                appendable.append(iDotGrave);
                                return 3;
                            }
                            case 205: {
                                appendable.append(iDotAcute);
                                return 3;
                            }
                            case 296: {
                                appendable.append(iDotTilde);
                                return 3;
                            }
                        }
                        return 0;
                    } catch (IOException iOException) {
                        throw new ICUUncheckedIOException(iOException);
                    }
                }
                if (n2 == 2 && n == 304) {
                    return 0;
                }
                if (n2 == 2 && n == 775 && this.isPrecededBy_I(contextIterator)) {
                    return 1;
                }
                if (n2 == 2 && n == 73 && !this.isFollowedByDotAbove(contextIterator)) {
                    return 0;
                }
                if (n == 304) {
                    try {
                        appendable.append(iDot);
                        return 2;
                    } catch (IOException iOException) {
                        throw new ICUUncheckedIOException(iOException);
                    }
                }
                if (n == 931 && !this.isFollowedByCasedLetter(contextIterator, 1) && this.isFollowedByCasedLetter(contextIterator, -1)) {
                    return 1;
                }
            } else if (UCaseProps.hasSlot(c, 7) && (n5 = (int)(l = this.getSlotValueAndOffset(c, 7, n6)) & 0xF) != 0) {
                n6 = (int)(l >> 32) + 1;
                try {
                    appendable.append(this.exceptions, n6, n6 + n5);
                    return n5;
                } catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.isUpperOrTitleFromProps(n4)) {
                int n8 = this.getSlotValue(c, 4, n7);
                return (c & 0x400) == 0 ? n + n8 : n - n8;
            }
            if (UCaseProps.hasSlot(c, 0)) {
                n3 = this.getSlotValue(c, 0, n7);
            }
        }
        return n3 == n ? ~n3 : n3;
    }

    private final int toUpperOrTitle(int n, ContextIterator contextIterator, Appendable appendable, int n2, boolean bl) {
        int n3 = n;
        int n4 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n4)) {
            if (UCaseProps.getTypeFromProps(n4) == 1) {
                n3 = n + UCaseProps.getDelta(n4);
            }
        } else {
            int n5;
            int n6 = UCaseProps.getExceptionsOffset(n4);
            char c = this.exceptions.charAt(n6++);
            int n7 = n6;
            if ((c & 0x4000) != 0) {
                if (n2 == 2 && n == 105) {
                    return 1;
                }
                if (n2 == 3 && n == 775 && this.isPrecededBySoftDotted(contextIterator)) {
                    return 1;
                }
            } else if (UCaseProps.hasSlot(c, 7)) {
                long l = this.getSlotValueAndOffset(c, 7, n6);
                int n8 = (int)l & 0xFFFF;
                n6 = (int)(l >> 32) + 1;
                n6 += n8 & 0xF;
                n6 += (n8 >>= 4) & 0xF;
                n8 >>= 4;
                if (bl) {
                    n8 &= 0xF;
                } else {
                    n6 += n8 & 0xF;
                    n8 = n8 >> 4 & 0xF;
                }
                if (n8 != 0) {
                    try {
                        appendable.append(this.exceptions, n6, n6 + n8);
                        return n8;
                    } catch (IOException iOException) {
                        throw new ICUUncheckedIOException(iOException);
                    }
                }
            }
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.getTypeFromProps(n4) == 1) {
                int n9 = this.getSlotValue(c, 4, n7);
                return (c & 0x400) == 0 ? n + n9 : n - n9;
            }
            if (!bl && UCaseProps.hasSlot(c, 3)) {
                n5 = 3;
            } else if (UCaseProps.hasSlot(c, 2)) {
                n5 = 2;
            } else {
                return ~n;
            }
            n3 = this.getSlotValue(c, n5, n7);
        }
        return n3 == n ? ~n3 : n3;
    }

    public final int toFullUpper(int n, ContextIterator contextIterator, Appendable appendable, int n2) {
        return this.toUpperOrTitle(n, contextIterator, appendable, n2, true);
    }

    public final int toFullTitle(int n, ContextIterator contextIterator, Appendable appendable, int n2) {
        return this.toUpperOrTitle(n, contextIterator, appendable, n2, false);
    }

    public final int fold(int n, int n2) {
        int n3 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n3)) {
            if (UCaseProps.isUpperOrTitleFromProps(n3)) {
                n += UCaseProps.getDelta(n3);
            }
        } else {
            int n4;
            char c;
            int n5 = UCaseProps.getExceptionsOffset(n3);
            if (((c = this.exceptions.charAt(n5++)) & 0x8000) != 0) {
                if ((n2 & 7) == 0) {
                    if (n == 73) {
                        return 0;
                    }
                    if (n == 304) {
                        return n;
                    }
                } else {
                    if (n == 73) {
                        return 0;
                    }
                    if (n == 304) {
                        return 0;
                    }
                }
            }
            if ((c & 0x200) != 0) {
                return n;
            }
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.isUpperOrTitleFromProps(n3)) {
                int n6 = this.getSlotValue(c, 4, n5);
                return (c & 0x400) == 0 ? n + n6 : n - n6;
            }
            if (UCaseProps.hasSlot(c, 1)) {
                n4 = 1;
            } else if (UCaseProps.hasSlot(c, 0)) {
                n4 = 0;
            } else {
                return n;
            }
            n = this.getSlotValue(c, n4, n5);
        }
        return n;
    }

    public final int toFullFolding(int n, Appendable appendable, int n2) {
        int n3 = n;
        int n4 = this.trie.get(n);
        if (!UCaseProps.propsHasException(n4)) {
            if (UCaseProps.isUpperOrTitleFromProps(n4)) {
                n3 = n + UCaseProps.getDelta(n4);
            }
        } else {
            int n5;
            int n6 = UCaseProps.getExceptionsOffset(n4);
            char c = this.exceptions.charAt(n6++);
            int n7 = n6;
            if ((c & 0x8000) != 0) {
                if ((n2 & 7) == 0) {
                    if (n == 73) {
                        return 0;
                    }
                    if (n == 304) {
                        try {
                            appendable.append(iDot);
                            return 2;
                        } catch (IOException iOException) {
                            throw new ICUUncheckedIOException(iOException);
                        }
                    }
                } else {
                    if (n == 73) {
                        return 0;
                    }
                    if (n == 304) {
                        return 0;
                    }
                }
            } else if (UCaseProps.hasSlot(c, 7)) {
                long l = this.getSlotValueAndOffset(c, 7, n6);
                int n8 = (int)l & 0xFFFF;
                n6 = (int)(l >> 32) + 1;
                n6 += n8 & 0xF;
                if ((n8 = n8 >> 4 & 0xF) != 0) {
                    try {
                        appendable.append(this.exceptions, n6, n6 + n8);
                        return n8;
                    } catch (IOException iOException) {
                        throw new ICUUncheckedIOException(iOException);
                    }
                }
            }
            if ((c & 0x200) != 0) {
                return ~n;
            }
            if (UCaseProps.hasSlot(c, 4) && UCaseProps.isUpperOrTitleFromProps(n4)) {
                int n9 = this.getSlotValue(c, 4, n7);
                return (c & 0x400) == 0 ? n + n9 : n - n9;
            }
            if (UCaseProps.hasSlot(c, 1)) {
                n5 = 1;
            } else if (UCaseProps.hasSlot(c, 0)) {
                n5 = 0;
            } else {
                return ~n;
            }
            n3 = this.getSlotValue(c, n5, n7);
        }
        return n3 == n ? ~n3 : n3;
    }

    public final boolean hasBinaryProperty(int n, int n2) {
        switch (n2) {
            case 22: {
                return 1 == this.getType(n);
            }
            case 30: {
                return 2 == this.getType(n);
            }
            case 27: {
                return this.isSoftDotted(n);
            }
            case 34: {
                return this.isCaseSensitive(n);
            }
            case 49: {
                return 0 != this.getType(n);
            }
            case 50: {
                return this.getTypeOrIgnorable(n) >> 2 != 0;
            }
            case 51: {
                dummyStringBuilder.setLength(0);
                return this.toFullLower(n, null, dummyStringBuilder, 1) >= 0;
            }
            case 52: {
                dummyStringBuilder.setLength(0);
                return this.toFullUpper(n, null, dummyStringBuilder, 1) >= 0;
            }
            case 53: {
                dummyStringBuilder.setLength(0);
                return this.toFullTitle(n, null, dummyStringBuilder, 1) >= 0;
            }
            case 55: {
                dummyStringBuilder.setLength(0);
                return this.toFullLower(n, null, dummyStringBuilder, 1) >= 0 || this.toFullUpper(n, null, dummyStringBuilder, 1) >= 0 || this.toFullTitle(n, null, dummyStringBuilder, 1) >= 0;
            }
        }
        return true;
    }

    static Trie2_16 getTrie() {
        return UCaseProps.INSTANCE.trie;
    }

    static final int getTypeFromProps(int n) {
        return n & 3;
    }

    private static final int getTypeAndIgnorableFromProps(int n) {
        return n & 7;
    }

    static final boolean isUpperOrTitleFromProps(int n) {
        return (n & 2) != 0;
    }

    static final int getDelta(int n) {
        return (short)n >> 7;
    }

    static {
        try {
            INSTANCE = new UCaseProps();
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    static final class LatinCase {
        static final char LIMIT = '\u0180';
        static final char LONG_S = '\u017f';
        static final byte EXC = -128;
        static final byte[] TO_LOWER_NORMAL = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_LOWER_TR_LT = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, -128, -128, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 32, 32, 32, 32, 32, 32, 32, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 0, 1, 0, 1, 0, -128, 0, -128, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -128, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, -121, 1, 0, 1, 0, 1, 0, -128};
        static final byte[] TO_UPPER_NORMAL = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};
        static final byte[] TO_UPPER_TR = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -32, -32, -32, -32, -32, -32, -32, -32, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, -32, 0, -32, -32, -32, -32, -32, -32, -32, 121, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -128, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, -128, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, -1, 0, -1, -128};

        LatinCase() {
        }
    }

    public static interface ContextIterator {
        public void reset(int var1);

        public int next();
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 4;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }
}

