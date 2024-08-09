/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.FilteredNormalizer2;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.nio.CharBuffer;
import java.text.CharacterIterator;

public final class Normalizer
implements Cloneable {
    private UCharacterIterator text;
    private Normalizer2 norm2;
    private Mode mode;
    private int options;
    private int currentIndex;
    private int nextIndex;
    private StringBuilder buffer;
    private int bufferPos;
    @Deprecated
    public static final int UNICODE_3_2 = 32;
    @Deprecated
    public static final int DONE = -1;
    @Deprecated
    public static final Mode NONE = new NONEMode(null);
    @Deprecated
    public static final Mode NFD = new NFDMode(null);
    @Deprecated
    public static final Mode NFKD = new NFKDMode(null);
    @Deprecated
    public static final Mode NFC;
    @Deprecated
    public static final Mode DEFAULT;
    @Deprecated
    public static final Mode NFKC;
    @Deprecated
    public static final Mode FCD;
    @Deprecated
    public static final Mode NO_OP;
    @Deprecated
    public static final Mode COMPOSE;
    @Deprecated
    public static final Mode COMPOSE_COMPAT;
    @Deprecated
    public static final Mode DECOMP;
    @Deprecated
    public static final Mode DECOMP_COMPAT;
    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final QuickCheckResult NO;
    public static final QuickCheckResult YES;
    public static final QuickCheckResult MAYBE;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int INPUT_IS_FCD = 131072;
    public static final int COMPARE_IGNORE_CASE = 65536;
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    @Deprecated
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
    private static final int COMPARE_EQUIV = 524288;

    @Deprecated
    public Normalizer(String string, Mode mode, int n) {
        this.text = UCharacterIterator.getInstance(string);
        this.mode = mode;
        this.options = n;
        this.norm2 = mode.getNormalizer2(n);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(CharacterIterator characterIterator, Mode mode, int n) {
        this.text = UCharacterIterator.getInstance((CharacterIterator)characterIterator.clone());
        this.mode = mode;
        this.options = n;
        this.norm2 = mode.getNormalizer2(n);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(UCharacterIterator uCharacterIterator, Mode mode, int n) {
        try {
            this.text = (UCharacterIterator)uCharacterIterator.clone();
            this.mode = mode;
            this.options = n;
            this.norm2 = mode.getNormalizer2(n);
            this.buffer = new StringBuilder();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    public Object clone() {
        try {
            Normalizer normalizer = (Normalizer)super.clone();
            normalizer.text = (UCharacterIterator)this.text.clone();
            normalizer.mode = this.mode;
            normalizer.options = this.options;
            normalizer.norm2 = this.norm2;
            normalizer.buffer = new StringBuilder(this.buffer);
            normalizer.bufferPos = this.bufferPos;
            normalizer.currentIndex = this.currentIndex;
            normalizer.nextIndex = this.nextIndex;
            return normalizer;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    private static final Normalizer2 getComposeNormalizer2(boolean bl, int n) {
        return (bl ? NFKC : NFC).getNormalizer2(n);
    }

    private static final Normalizer2 getDecomposeNormalizer2(boolean bl, int n) {
        return (bl ? NFKD : NFD).getNormalizer2(n);
    }

    @Deprecated
    public static String compose(String string, boolean bl) {
        return Normalizer.compose(string, bl, 0);
    }

    @Deprecated
    public static String compose(String string, boolean bl, int n) {
        return Normalizer.getComposeNormalizer2(bl, n).normalize(string);
    }

    @Deprecated
    public static int compose(char[] cArray, char[] cArray2, boolean bl, int n) {
        return Normalizer.compose(cArray, 0, cArray.length, cArray2, 0, cArray2.length, bl, n);
    }

    @Deprecated
    public static int compose(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4, boolean bl, int n5) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2 - n);
        CharsAppendable charsAppendable = new CharsAppendable(cArray2, n3, n4);
        Normalizer.getComposeNormalizer2(bl, n5).normalize((CharSequence)charBuffer, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String decompose(String string, boolean bl) {
        return Normalizer.decompose(string, bl, 0);
    }

    @Deprecated
    public static String decompose(String string, boolean bl, int n) {
        return Normalizer.getDecomposeNormalizer2(bl, n).normalize(string);
    }

    @Deprecated
    public static int decompose(char[] cArray, char[] cArray2, boolean bl, int n) {
        return Normalizer.decompose(cArray, 0, cArray.length, cArray2, 0, cArray2.length, bl, n);
    }

    @Deprecated
    public static int decompose(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4, boolean bl, int n5) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2 - n);
        CharsAppendable charsAppendable = new CharsAppendable(cArray2, n3, n4);
        Normalizer.getDecomposeNormalizer2(bl, n5).normalize((CharSequence)charBuffer, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String normalize(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).normalize(string);
    }

    @Deprecated
    public static String normalize(String string, Mode mode) {
        return Normalizer.normalize(string, mode, 0);
    }

    @Deprecated
    public static int normalize(char[] cArray, char[] cArray2, Mode mode, int n) {
        return Normalizer.normalize(cArray, 0, cArray.length, cArray2, 0, cArray2.length, mode, n);
    }

    @Deprecated
    public static int normalize(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4, Mode mode, int n5) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2 - n);
        CharsAppendable charsAppendable = new CharsAppendable(cArray2, n3, n4);
        mode.getNormalizer2(n5).normalize((CharSequence)charBuffer, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String normalize(int n, Mode mode, int n2) {
        if (mode == NFD && n2 == 0) {
            String string = Normalizer2.getNFCInstance().getDecomposition(n);
            if (string == null) {
                string = UTF16.valueOf(n);
            }
            return string;
        }
        return Normalizer.normalize(UTF16.valueOf(n), mode, n2);
    }

    @Deprecated
    public static String normalize(int n, Mode mode) {
        return Normalizer.normalize(n, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String string, Mode mode) {
        return Normalizer.quickCheck(string, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).quickCheck(string);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] cArray, Mode mode, int n) {
        return Normalizer.quickCheck(cArray, 0, cArray.length, mode, n);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] cArray, int n, int n2, Mode mode, int n3) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2 - n);
        return mode.getNormalizer2(n3).quickCheck(charBuffer);
    }

    @Deprecated
    public static boolean isNormalized(char[] cArray, int n, int n2, Mode mode, int n3) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2 - n);
        return mode.getNormalizer2(n3).isNormalized(charBuffer);
    }

    @Deprecated
    public static boolean isNormalized(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).isNormalized(string);
    }

    @Deprecated
    public static boolean isNormalized(int n, Mode mode, int n2) {
        return Normalizer.isNormalized(UTF16.valueOf(n), mode, n2);
    }

    public static int compare(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4, int n5) {
        if (cArray == null || n < 0 || n2 < 0 || cArray2 == null || n3 < 0 || n4 < 0 || n2 < n || n4 < n3) {
            throw new IllegalArgumentException();
        }
        return Normalizer.internalCompare(CharBuffer.wrap(cArray, n, n2 - n), CharBuffer.wrap(cArray2, n3, n4 - n3), n5);
    }

    public static int compare(String string, String string2, int n) {
        return Normalizer.internalCompare(string, string2, n);
    }

    public static int compare(char[] cArray, char[] cArray2, int n) {
        return Normalizer.internalCompare(CharBuffer.wrap(cArray), CharBuffer.wrap(cArray2), n);
    }

    public static int compare(int n, int n2, int n3) {
        return Normalizer.internalCompare(UTF16.valueOf(n), UTF16.valueOf(n2), n3 | 0x20000);
    }

    public static int compare(int n, String string, int n2) {
        return Normalizer.internalCompare(UTF16.valueOf(n), string, n2);
    }

    @Deprecated
    public static int concatenate(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4, char[] cArray3, int n5, int n6, Mode mode, int n7) {
        if (cArray3 == null) {
            throw new IllegalArgumentException();
        }
        if (cArray2 == cArray3 && n3 < n6 && n5 < n4) {
            throw new IllegalArgumentException("overlapping right and dst ranges");
        }
        StringBuilder stringBuilder = new StringBuilder(n2 - n + n4 - n3 + 16);
        stringBuilder.append(cArray, n, n2 - n);
        CharBuffer charBuffer = CharBuffer.wrap(cArray2, n3, n4 - n3);
        mode.getNormalizer2(n7).append(stringBuilder, charBuffer);
        int n8 = stringBuilder.length();
        if (n8 <= n6 - n5) {
            stringBuilder.getChars(0, n8, cArray3, n5);
            return n8;
        }
        throw new IndexOutOfBoundsException(Integer.toString(n8));
    }

    @Deprecated
    public static String concatenate(char[] cArray, char[] cArray2, Mode mode, int n) {
        StringBuilder stringBuilder = new StringBuilder(cArray.length + cArray2.length + 16).append(cArray);
        return mode.getNormalizer2(n).append(stringBuilder, CharBuffer.wrap(cArray2)).toString();
    }

    @Deprecated
    public static String concatenate(String string, String string2, Mode mode, int n) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + string2.length() + 16).append(string);
        return mode.getNormalizer2(n).append(stringBuilder, string2).toString();
    }

    @Deprecated
    public static int getFC_NFKC_Closure(int n, char[] cArray) {
        String string = Normalizer.getFC_NFKC_Closure(n);
        int n2 = string.length();
        if (n2 != 0 && cArray != null && n2 <= cArray.length) {
            string.getChars(0, n2, cArray, 0);
        }
        return n2;
    }

    @Deprecated
    public static String getFC_NFKC_Closure(int n) {
        Object object;
        Normalizer2 normalizer2 = ModeImpl.access$300(NFKCModeImpl.access$1000());
        UCaseProps uCaseProps = UCaseProps.INSTANCE;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = uCaseProps.toFullFolding(n, stringBuilder, 0);
        if (n2 < 0) {
            object = ((Norm2AllModes.Normalizer2WithImpl)normalizer2).impl;
            if (((Normalizer2Impl)object).getCompQuickCheck(((Normalizer2Impl)object).getNorm16(n)) != 0) {
                return "";
            }
            stringBuilder.appendCodePoint(n);
        } else if (n2 > 31) {
            stringBuilder.appendCodePoint(n2);
        }
        object = normalizer2.normalize(stringBuilder);
        String string = normalizer2.normalize(UCharacter.foldCase((String)object, 0));
        if (((String)object).equals(string)) {
            return "";
        }
        return string;
    }

    @Deprecated
    public int current() {
        if (this.bufferPos < this.buffer.length() || this.nextNormalize()) {
            return this.buffer.codePointAt(this.bufferPos);
        }
        return 1;
    }

    @Deprecated
    public int next() {
        if (this.bufferPos < this.buffer.length() || this.nextNormalize()) {
            int n = this.buffer.codePointAt(this.bufferPos);
            this.bufferPos += Character.charCount(n);
            return n;
        }
        return 1;
    }

    @Deprecated
    public int previous() {
        if (this.bufferPos > 0 || this.previousNormalize()) {
            int n = this.buffer.codePointBefore(this.bufferPos);
            this.bufferPos -= Character.charCount(n);
            return n;
        }
        return 1;
    }

    @Deprecated
    public void reset() {
        this.text.setToStart();
        this.nextIndex = 0;
        this.currentIndex = 0;
        this.clearBuffer();
    }

    @Deprecated
    public void setIndexOnly(int n) {
        this.text.setIndex(n);
        this.currentIndex = this.nextIndex = n;
        this.clearBuffer();
    }

    @Deprecated
    public int setIndex(int n) {
        this.setIndexOnly(n);
        return this.current();
    }

    @Deprecated
    public int getBeginIndex() {
        return 1;
    }

    @Deprecated
    public int getEndIndex() {
        return this.endIndex();
    }

    @Deprecated
    public int first() {
        this.reset();
        return this.next();
    }

    @Deprecated
    public int last() {
        this.text.setToLimit();
        this.currentIndex = this.nextIndex = this.text.getIndex();
        this.clearBuffer();
        return this.previous();
    }

    @Deprecated
    public int getIndex() {
        if (this.bufferPos < this.buffer.length()) {
            return this.currentIndex;
        }
        return this.nextIndex;
    }

    @Deprecated
    public int startIndex() {
        return 1;
    }

    @Deprecated
    public int endIndex() {
        return this.text.getLength();
    }

    @Deprecated
    public void setMode(Mode mode) {
        this.mode = mode;
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public Mode getMode() {
        return this.mode;
    }

    @Deprecated
    public void setOption(int n, boolean bl) {
        this.options = bl ? (this.options |= n) : (this.options &= ~n);
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public int getOption(int n) {
        if ((this.options & n) != 0) {
            return 0;
        }
        return 1;
    }

    @Deprecated
    public int getText(char[] cArray) {
        return this.text.getText(cArray);
    }

    @Deprecated
    public int getLength() {
        return this.text.getLength();
    }

    @Deprecated
    public String getText() {
        return this.text.getText();
    }

    @Deprecated
    public void setText(StringBuffer stringBuffer) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(stringBuffer);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        this.reset();
    }

    @Deprecated
    public void setText(char[] cArray) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(cArray);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        this.reset();
    }

    @Deprecated
    public void setText(String string) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(string);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        this.reset();
    }

    @Deprecated
    public void setText(CharacterIterator characterIterator) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(characterIterator);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        this.reset();
    }

    @Deprecated
    public void setText(UCharacterIterator uCharacterIterator) {
        try {
            UCharacterIterator uCharacterIterator2 = (UCharacterIterator)uCharacterIterator.clone();
            if (uCharacterIterator2 == null) {
                throw new IllegalStateException("Could not create a new UCharacterIterator");
            }
            this.text = uCharacterIterator2;
            this.reset();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Could not clone the UCharacterIterator", cloneNotSupportedException);
        }
    }

    private void clearBuffer() {
        this.buffer.setLength(0);
        this.bufferPos = 0;
    }

    private boolean nextNormalize() {
        this.clearBuffer();
        this.currentIndex = this.nextIndex;
        this.text.setIndex(this.nextIndex);
        int n = this.text.nextCodePoint();
        if (n < 0) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder().appendCodePoint(n);
        while ((n = this.text.nextCodePoint()) >= 0) {
            if (this.norm2.hasBoundaryBefore(n)) {
                this.text.moveCodePointIndex(-1);
                break;
            }
            stringBuilder.appendCodePoint(n);
        }
        this.nextIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence)stringBuilder, this.buffer);
        return this.buffer.length() != 0;
    }

    private boolean previousNormalize() {
        int n;
        this.clearBuffer();
        this.nextIndex = this.currentIndex;
        this.text.setIndex(this.currentIndex);
        StringBuilder stringBuilder = new StringBuilder();
        while ((n = this.text.previousCodePoint()) >= 0) {
            if (n <= 65535) {
                stringBuilder.insert(0, (char)n);
            } else {
                stringBuilder.insert(0, Character.toChars(n));
            }
            if (!this.norm2.hasBoundaryBefore(n)) continue;
        }
        this.currentIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence)stringBuilder, this.buffer);
        this.bufferPos = this.buffer.length();
        return this.buffer.length() != 0;
    }

    private static int internalCompare(CharSequence charSequence, CharSequence charSequence2, int n) {
        int n2 = n >>> 20;
        if (((n |= 0x80000) & 0x20000) == 0 || (n & 1) != 0) {
            StringBuilder stringBuilder;
            Normalizer2 normalizer2 = (n & 1) != 0 ? NFD.getNormalizer2(n2) : FCD.getNormalizer2(n2);
            int n3 = normalizer2.spanQuickCheckYes(charSequence);
            int n4 = normalizer2.spanQuickCheckYes(charSequence2);
            if (n3 < charSequence.length()) {
                stringBuilder = new StringBuilder(charSequence.length() + 16).append(charSequence, 0, n3);
                charSequence = normalizer2.normalizeSecondAndAppend(stringBuilder, charSequence.subSequence(n3, charSequence.length()));
            }
            if (n4 < charSequence2.length()) {
                stringBuilder = new StringBuilder(charSequence2.length() + 16).append(charSequence2, 0, n4);
                charSequence2 = normalizer2.normalizeSecondAndAppend(stringBuilder, charSequence2.subSequence(n4, charSequence2.length()));
            }
        }
        return Normalizer.cmpEquivFold(charSequence, charSequence2, n);
    }

    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[]{new CmpEquivLevel(null), new CmpEquivLevel(null)};
    }

    static int cmpEquivFold(CharSequence charSequence, CharSequence charSequence2, int n) {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        UCaseProps uCaseProps;
        CmpEquivLevel[] cmpEquivLevelArray = null;
        CmpEquivLevel[] cmpEquivLevelArray2 = null;
        Normalizer2Impl normalizer2Impl = (n & 0x80000) != 0 ? Norm2AllModes.getNFCInstance().impl : null;
        if ((n & 0x10000) != 0) {
            uCaseProps = UCaseProps.INSTANCE;
            stringBuilder2 = new StringBuilder();
            stringBuilder = new StringBuilder();
        } else {
            uCaseProps = null;
            stringBuilder = null;
            stringBuilder2 = null;
        }
        int n2 = 0;
        int n3 = charSequence.length();
        int n4 = 0;
        int n5 = charSequence2.length();
        int n6 = 0;
        int n7 = 0;
        int n8 = -1;
        int n9 = -1;
        while (true) {
            String string;
            String string2;
            int n10;
            char c;
            if (n9 < 0) {
                while (true) {
                    if (n2 == n3) {
                        if (n7 == 0) {
                            n9 = -1;
                            break;
                        }
                    } else {
                        n9 = charSequence.charAt(n2++);
                        break;
                    }
                    while ((charSequence = cmpEquivLevelArray[--n7].cs) == null) {
                    }
                    n2 = cmpEquivLevelArray[n7].s;
                    n3 = charSequence.length();
                }
            }
            if (n8 < 0) {
                while (true) {
                    if (n4 == n5) {
                        if (n6 == 0) {
                            n8 = -1;
                            break;
                        }
                    } else {
                        n8 = charSequence2.charAt(n4++);
                        break;
                    }
                    while ((charSequence2 = cmpEquivLevelArray2[--n6].cs) == null) {
                    }
                    n4 = cmpEquivLevelArray2[n6].s;
                    n5 = charSequence2.length();
                }
            }
            if (n9 == n8) {
                if (n9 < 0) {
                    return 1;
                }
                n8 = -1;
                n9 = -1;
                continue;
            }
            if (n9 < 0) {
                return 1;
            }
            if (n8 < 0) {
                return 0;
            }
            int n11 = n9;
            if (UTF16.isSurrogate((char)n9)) {
                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n9)) {
                    if (n2 != n3 && Character.isLowSurrogate(c = charSequence.charAt(n2))) {
                        n11 = Character.toCodePoint((char)n9, c);
                    }
                } else if (0 <= n2 - 2 && Character.isHighSurrogate(c = charSequence.charAt(n2 - 2))) {
                    n11 = Character.toCodePoint(c, (char)n9);
                }
            }
            int n12 = n8;
            if (UTF16.isSurrogate((char)n8)) {
                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n8)) {
                    if (n4 != n5 && Character.isLowSurrogate(c = charSequence2.charAt(n4))) {
                        n12 = Character.toCodePoint((char)n8, c);
                    }
                } else if (0 <= n4 - 2 && Character.isHighSurrogate(c = charSequence2.charAt(n4 - 2))) {
                    n12 = Character.toCodePoint(c, (char)n8);
                }
            }
            if (n7 == 0 && (n & 0x10000) != 0 && (n10 = uCaseProps.toFullFolding(n11, stringBuilder2, n)) >= 0) {
                if (UTF16.isSurrogate((char)n9)) {
                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n9)) {
                        ++n2;
                    } else {
                        n8 = charSequence2.charAt(--n4 - 1);
                    }
                }
                if (cmpEquivLevelArray == null) {
                    cmpEquivLevelArray = Normalizer.createCmpEquivLevelStack();
                }
                cmpEquivLevelArray[0].cs = charSequence;
                cmpEquivLevelArray[0].s = n2;
                ++n7;
                if (n10 <= 31) {
                    stringBuilder2.delete(0, stringBuilder2.length() - n10);
                } else {
                    stringBuilder2.setLength(0);
                    stringBuilder2.appendCodePoint(n10);
                }
                charSequence = stringBuilder2;
                n2 = 0;
                n3 = stringBuilder2.length();
                n9 = -1;
                continue;
            }
            if (n6 == 0 && (n & 0x10000) != 0 && (n10 = uCaseProps.toFullFolding(n12, stringBuilder, n)) >= 0) {
                if (UTF16.isSurrogate((char)n8)) {
                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n8)) {
                        ++n4;
                    } else {
                        n9 = charSequence.charAt(--n2 - 1);
                    }
                }
                if (cmpEquivLevelArray2 == null) {
                    cmpEquivLevelArray2 = Normalizer.createCmpEquivLevelStack();
                }
                cmpEquivLevelArray2[0].cs = charSequence2;
                cmpEquivLevelArray2[0].s = n4;
                ++n6;
                if (n10 <= 31) {
                    stringBuilder.delete(0, stringBuilder.length() - n10);
                } else {
                    stringBuilder.setLength(0);
                    stringBuilder.appendCodePoint(n10);
                }
                charSequence2 = stringBuilder;
                n4 = 0;
                n5 = stringBuilder.length();
                n8 = -1;
                continue;
            }
            if (n7 < 2 && (n & 0x80000) != 0 && (string2 = normalizer2Impl.getDecomposition(n11)) != null) {
                if (UTF16.isSurrogate((char)n9)) {
                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n9)) {
                        ++n2;
                    } else {
                        n8 = charSequence2.charAt(--n4 - 1);
                    }
                }
                if (cmpEquivLevelArray == null) {
                    cmpEquivLevelArray = Normalizer.createCmpEquivLevelStack();
                }
                cmpEquivLevelArray[n7].cs = charSequence;
                cmpEquivLevelArray[n7].s = n2;
                if (++n7 < 2) {
                    cmpEquivLevelArray[n7++].cs = null;
                }
                charSequence = string2;
                n2 = 0;
                n3 = string2.length();
                n9 = -1;
                continue;
            }
            if (n6 >= 2 || (n & 0x80000) == 0 || (string = normalizer2Impl.getDecomposition(n12)) == null) break;
            if (UTF16.isSurrogate((char)n8)) {
                if (Normalizer2Impl.UTF16Plus.isSurrogateLead(n8)) {
                    ++n4;
                } else {
                    n9 = charSequence.charAt(--n2 - 1);
                }
            }
            if (cmpEquivLevelArray2 == null) {
                cmpEquivLevelArray2 = Normalizer.createCmpEquivLevelStack();
            }
            cmpEquivLevelArray2[n6].cs = charSequence2;
            cmpEquivLevelArray2[n6].s = n4;
            if (++n6 < 2) {
                cmpEquivLevelArray2[n6++].cs = null;
            }
            charSequence2 = string;
            n4 = 0;
            n5 = string.length();
            n8 = -1;
        }
        if (n9 >= 55296 && n8 >= 55296 && (n & 0x8000) != 0) {
            if (!(n9 <= 56319 && n2 != n3 && Character.isLowSurrogate(charSequence.charAt(n2)) || Character.isLowSurrogate((char)n9) && 0 != n2 - 1 && Character.isHighSurrogate(charSequence.charAt(n2 - 2)))) {
                n9 -= 10240;
            }
            if (!(n8 <= 56319 && n4 != n5 && Character.isLowSurrogate(charSequence2.charAt(n4)) || Character.isLowSurrogate((char)n8) && 0 != n4 - 1 && Character.isHighSurrogate(charSequence2.charAt(n4 - 2)))) {
                n8 -= 10240;
            }
        }
        return n9 - n8;
    }

    static {
        DEFAULT = NFC = new NFCMode(null);
        NFKC = new NFKCMode(null);
        FCD = new FCDMode(null);
        NO_OP = NONE;
        COMPOSE = NFC;
        COMPOSE_COMPAT = NFKC;
        DECOMP = NFD;
        DECOMP_COMPAT = NFKD;
        NO = new QuickCheckResult(0, null);
        YES = new QuickCheckResult(1, null);
        MAYBE = new QuickCheckResult(2, null);
    }

    private static final class CharsAppendable
    implements Appendable {
        private final char[] chars;
        private final int start;
        private final int limit;
        private int offset;

        public CharsAppendable(char[] cArray, int n, int n2) {
            this.chars = cArray;
            this.start = this.offset = n;
            this.limit = n2;
        }

        public int length() {
            int n = this.offset - this.start;
            if (this.offset <= this.limit) {
                return n;
            }
            throw new IndexOutOfBoundsException(Integer.toString(n));
        }

        @Override
        public Appendable append(char c) {
            if (this.offset < this.limit) {
                this.chars[this.offset] = c;
            }
            ++this.offset;
            return this;
        }

        @Override
        public Appendable append(CharSequence charSequence) {
            return this.append(charSequence, 0, charSequence.length());
        }

        @Override
        public Appendable append(CharSequence charSequence, int n, int n2) {
            int n3 = n2 - n;
            if (n3 <= this.limit - this.offset) {
                while (n < n2) {
                    this.chars[this.offset++] = charSequence.charAt(n++);
                }
            } else {
                this.offset += n3;
            }
            return this;
        }
    }

    private static final class CmpEquivLevel {
        CharSequence cs;
        int s;

        private CmpEquivLevel() {
        }

        CmpEquivLevel(1 var1_1) {
            this();
        }
    }

    public static final class QuickCheckResult {
        private QuickCheckResult(int n) {
        }

        QuickCheckResult(int n, 1 var2_2) {
            this(n);
        }
    }

    private static final class FCDMode
    extends Mode {
        private FCDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return (n & 0x20) != 0 ? ModeImpl.access$300(FCD32ModeImpl.access$1100()) : ModeImpl.access$300(FCDModeImpl.access$1200());
        }

        FCDMode(1 var1_1) {
            this();
        }
    }

    private static final class NFKCMode
    extends Mode {
        private NFKCMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return (n & 0x20) != 0 ? ModeImpl.access$300(NFKC32ModeImpl.access$900()) : ModeImpl.access$300(NFKCModeImpl.access$1000());
        }

        NFKCMode(1 var1_1) {
            this();
        }
    }

    private static final class NFCMode
    extends Mode {
        private NFCMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return (n & 0x20) != 0 ? ModeImpl.access$300(NFC32ModeImpl.access$700()) : ModeImpl.access$300(NFCModeImpl.access$800());
        }

        NFCMode(1 var1_1) {
            this();
        }
    }

    private static final class NFKDMode
    extends Mode {
        private NFKDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return (n & 0x20) != 0 ? ModeImpl.access$300(NFKD32ModeImpl.access$500()) : ModeImpl.access$300(NFKDModeImpl.access$600());
        }

        NFKDMode(1 var1_1) {
            this();
        }
    }

    private static final class NFDMode
    extends Mode {
        private NFDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return (n & 0x20) != 0 ? ModeImpl.access$300(NFD32ModeImpl.access$200()) : ModeImpl.access$300(NFDModeImpl.access$400());
        }

        NFDMode(1 var1_1) {
            this();
        }
    }

    private static final class NONEMode
    extends Mode {
        private NONEMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }

        NONEMode(1 var1_1) {
            this();
        }
    }

    @Deprecated
    public static abstract class Mode {
        @Deprecated
        protected Mode() {
        }

        @Deprecated
        protected abstract Normalizer2 getNormalizer2(int var1);
    }

    private static final class FCD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.access$100()), null);

        private FCD32ModeImpl() {
        }

        static ModeImpl access$1100() {
            return INSTANCE;
        }
    }

    private static final class NFKC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Unicode32.access$100()), null);

        private NFKC32ModeImpl() {
        }

        static ModeImpl access$900() {
            return INSTANCE;
        }
    }

    private static final class NFC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Unicode32.access$100()), null);

        private NFC32ModeImpl() {
        }

        static ModeImpl access$700() {
            return INSTANCE;
        }
    }

    private static final class NFKD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Unicode32.access$100()), null);

        private NFKD32ModeImpl() {
        }

        static ModeImpl access$500() {
            return INSTANCE;
        }
    }

    private static final class NFD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Unicode32.access$100()), null);

        private NFD32ModeImpl() {
        }

        static ModeImpl access$200() {
            return INSTANCE;
        }
    }

    private static final class Unicode32 {
        private static final UnicodeSet INSTANCE = new UnicodeSet("[:age=3.2:]").freeze();

        private Unicode32() {
        }

        static UnicodeSet access$100() {
            return INSTANCE;
        }
    }

    private static final class FCDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2(), null);

        private FCDModeImpl() {
        }

        static ModeImpl access$1200() {
            return INSTANCE;
        }
    }

    private static final class NFKCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance(), null);

        private NFKCModeImpl() {
        }

        static ModeImpl access$1000() {
            return INSTANCE;
        }
    }

    private static final class NFCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance(), null);

        private NFCModeImpl() {
        }

        static ModeImpl access$800() {
            return INSTANCE;
        }
    }

    private static final class NFKDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance(), null);

        private NFKDModeImpl() {
        }

        static ModeImpl access$600() {
            return INSTANCE;
        }
    }

    private static final class NFDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance(), null);

        private NFDModeImpl() {
        }

        static ModeImpl access$400() {
            return INSTANCE;
        }
    }

    private static final class ModeImpl {
        private final Normalizer2 normalizer2;

        private ModeImpl(Normalizer2 normalizer2) {
            this.normalizer2 = normalizer2;
        }

        ModeImpl(Normalizer2 normalizer2, 1 var2_2) {
            this(normalizer2);
        }

        static Normalizer2 access$300(ModeImpl modeImpl) {
            return modeImpl.normalizer2;
        }
    }
}

