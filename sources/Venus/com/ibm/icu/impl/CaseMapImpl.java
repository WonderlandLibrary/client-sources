/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.impl.UCharacterProperty;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Edits;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.text.CharacterIterator;
import java.util.Locale;

public final class CaseMapImpl {
    public static final int TITLECASE_WHOLE_STRING = 32;
    public static final int TITLECASE_SENTENCES = 64;
    private static final int TITLECASE_ITERATOR_MASK = 224;
    public static final int TITLECASE_ADJUST_TO_CASED = 1024;
    private static final int TITLECASE_ADJUSTMENT_MASK = 1536;
    private static final int LNS = 251792942;
    public static final int OMIT_UNCHANGED_TEXT = 16384;
    private static final Trie2_16 CASE_TRIE;
    static final boolean $assertionsDisabled;

    public static int addTitleAdjustmentOption(int n, int n2) {
        int n3 = n & 0x600;
        if (n3 != 0 && n3 != n2) {
            throw new IllegalArgumentException("multiple titlecasing index adjustment options");
        }
        return n | n2;
    }

    private static boolean isLNS(int n) {
        int n2 = UCharacterProperty.INSTANCE.getType(n);
        return (1 << n2 & 0xF020E2E) != 0 || n2 == 4 && UCaseProps.INSTANCE.getType(n) != 0;
    }

    public static int addTitleIteratorOption(int n, int n2) {
        int n3 = n & 0xE0;
        if (n3 != 0 && n3 != n2) {
            throw new IllegalArgumentException("multiple titlecasing iterator options");
        }
        return n | n2;
    }

    public static BreakIterator getTitleBreakIterator(Locale locale, int n, BreakIterator breakIterator) {
        if ((n &= 0xE0) != 0 && breakIterator != null) {
            throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        if (breakIterator == null) {
            switch (n) {
                case 0: {
                    breakIterator = BreakIterator.getWordInstance(locale);
                    break;
                }
                case 32: {
                    breakIterator = new WholeStringBreakIterator(null);
                    break;
                }
                case 64: {
                    breakIterator = BreakIterator.getSentenceInstance(locale);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown titlecasing iterator option");
                }
            }
        }
        return breakIterator;
    }

    public static BreakIterator getTitleBreakIterator(ULocale uLocale, int n, BreakIterator breakIterator) {
        if ((n &= 0xE0) != 0 && breakIterator != null) {
            throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        if (breakIterator == null) {
            switch (n) {
                case 0: {
                    breakIterator = BreakIterator.getWordInstance(uLocale);
                    break;
                }
                case 32: {
                    breakIterator = new WholeStringBreakIterator(null);
                    break;
                }
                case 64: {
                    breakIterator = BreakIterator.getSentenceInstance(uLocale);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown titlecasing iterator option");
                }
            }
        }
        return breakIterator;
    }

    private static int appendCodePoint(Appendable appendable, int n) throws IOException {
        if (n <= 65535) {
            appendable.append((char)n);
            return 0;
        }
        appendable.append((char)(55232 + (n >> 10)));
        appendable.append((char)(56320 + (n & 0x3FF)));
        return 1;
    }

    private static void appendResult(int n, Appendable appendable, int n2, int n3, Edits edits) throws IOException {
        if (n < 0) {
            if (edits != null) {
                edits.addUnchanged(n2);
            }
            if ((n3 & 0x4000) != 0) {
                return;
            }
            CaseMapImpl.appendCodePoint(appendable, ~n);
        } else if (n <= 31) {
            if (edits != null) {
                edits.addReplace(n2, n);
            }
        } else {
            int n4 = CaseMapImpl.appendCodePoint(appendable, n);
            if (edits != null) {
                edits.addReplace(n2, n4);
            }
        }
    }

    private static final void appendUnchanged(CharSequence charSequence, int n, int n2, Appendable appendable, int n3, Edits edits) throws IOException {
        if (n2 > 0) {
            if (edits != null) {
                edits.addUnchanged(n2);
            }
            if ((n3 & 0x4000) != 0) {
                return;
            }
            appendable.append(charSequence, n, n + n2);
        }
    }

    private static String applyEdits(CharSequence charSequence, StringBuilder stringBuilder, Edits edits) {
        if (!edits.hasChanges()) {
            return charSequence.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder(charSequence.length() + edits.lengthDelta());
        Edits.Iterator iterator2 = edits.getCoarseIterator();
        while (iterator2.next()) {
            int n;
            if (iterator2.hasChange()) {
                n = iterator2.replacementIndex();
                stringBuilder2.append(stringBuilder, n, n + iterator2.newLength());
                continue;
            }
            n = iterator2.sourceIndex();
            stringBuilder2.append(charSequence, n, n + iterator2.oldLength());
        }
        return stringBuilder2.toString();
    }

    /*
     * Unable to fully structure code
     */
    private static void internalToLower(int var0, int var1_1, CharSequence var2_2, int var3_3, int var4_4, StringContextIterator var5_5, Appendable var6_6, Edits var7_7) throws IOException {
        var8_8 = var0 == 1 || (var0 >= 0 ? var0 != 2 && var0 != 3 : (var1_1 & 7) == 0) ? UCaseProps.LatinCase.TO_LOWER_NORMAL : UCaseProps.LatinCase.TO_LOWER_TR_LT;
        var9_9 = var3_3;
        var10_10 = var3_3;
        while (var10_10 < var4_4) {
            block10: {
                block11: {
                    block9: {
                        var11_11 = var2_2.charAt(var10_10);
                        if (var11_11 >= 383) break block9;
                        var13_13 = var8_8[var11_11];
                        if (var13_13 == '\uffffff80') break block10;
                        ++var10_10;
                        if (var13_13 == '\u0000') continue;
                        var12_12 = var13_13;
                        break block11;
                    }
                    if (var11_11 < 55296 && !UCaseProps.propsHasException(var13_13 = CaseMapImpl.CASE_TRIE.getFromU16SingleLead((char)var11_11))) {
                        ++var10_10;
                        if (!UCaseProps.isUpperOrTitleFromProps(var13_13) || (var12_12 = UCaseProps.getDelta(var13_13)) == 0) continue;
                    }
                    break block10;
                }
                var11_11 = (char)(var11_11 + var12_12);
                CaseMapImpl.appendUnchanged(var2_2, var9_9, var10_10 - 1 - var9_9, var6_6, var1_1, var7_7);
                var6_6.append((char)var11_11);
                if (var7_7 != null) {
                    var7_7.addReplace(1, 1);
                }
                var9_9 = var10_10;
                continue;
            }
            var12_12 = var10_10++;
            if (!Character.isHighSurrogate((char)var11_11) || var10_10 >= var4_4) ** GOTO lbl-1000
            v0 = var2_2.charAt(var10_10);
            var13_13 = v0;
            if (Character.isLowSurrogate(v0)) {
                var14_14 = Character.toCodePoint((char)var11_11, var13_13);
                ++var10_10;
            } else lbl-1000:
            // 2 sources

            {
                var14_14 = var11_11;
            }
            CaseMapImpl.appendUnchanged(var2_2, var9_9, var12_12 - var9_9, var6_6, var1_1, var7_7);
            var9_9 = var12_12;
            if (var0 >= 0) {
                if (var5_5 == null) {
                    var5_5 = new StringContextIterator(var2_2, var12_12, var10_10);
                } else {
                    var5_5.setCPStartAndLimit(var12_12, var10_10);
                }
                var14_14 = UCaseProps.INSTANCE.toFullLower(var14_14, var5_5, var6_6, var0);
            } else {
                var14_14 = UCaseProps.INSTANCE.toFullFolding(var14_14, var6_6, var1_1);
            }
            if (var14_14 < 0) continue;
            CaseMapImpl.appendResult(var14_14, var6_6, var10_10 - var12_12, var1_1, var7_7);
            var9_9 = var10_10;
        }
        CaseMapImpl.appendUnchanged(var2_2, var9_9, var10_10 - var9_9, var6_6, var1_1, var7_7);
    }

    /*
     * Unable to fully structure code
     */
    private static void internalToUpper(int var0, int var1_1, CharSequence var2_2, Appendable var3_3, Edits var4_4) throws IOException {
        var5_5 = null;
        var6_6 = var0 == 2 ? UCaseProps.LatinCase.TO_UPPER_TR : UCaseProps.LatinCase.TO_UPPER_NORMAL;
        var7_7 = 0;
        var8_8 = 0;
        var9_9 = var2_2.length();
        while (var8_8 < var9_9) {
            block8: {
                block9: {
                    block7: {
                        var10_10 = var2_2.charAt(var8_8);
                        if (var10_10 >= 383) break block7;
                        var12_12 = var6_6[var10_10];
                        if (var12_12 == '\uffffff80') break block8;
                        ++var8_8;
                        if (var12_12 == '\u0000') continue;
                        var11_11 = var12_12;
                        break block9;
                    }
                    if (var10_10 < 55296 && !UCaseProps.propsHasException(var12_12 = CaseMapImpl.CASE_TRIE.getFromU16SingleLead((char)var10_10))) {
                        ++var8_8;
                        if (UCaseProps.getTypeFromProps(var12_12) != 1 || (var11_11 = UCaseProps.getDelta(var12_12)) == 0) continue;
                    }
                    break block8;
                }
                var10_10 = (char)(var10_10 + var11_11);
                CaseMapImpl.appendUnchanged(var2_2, var7_7, var8_8 - 1 - var7_7, var3_3, var1_1, var4_4);
                var3_3.append((char)var10_10);
                if (var4_4 != null) {
                    var4_4.addReplace(1, 1);
                }
                var7_7 = var8_8;
                continue;
            }
            var11_11 = var8_8++;
            if (!Character.isHighSurrogate((char)var10_10) || var8_8 >= var9_9) ** GOTO lbl-1000
            v0 = var2_2.charAt(var8_8);
            var12_12 = v0;
            if (Character.isLowSurrogate(v0)) {
                var13_13 = Character.toCodePoint((char)var10_10, var12_12);
                ++var8_8;
            } else lbl-1000:
            // 2 sources

            {
                var13_13 = var10_10;
            }
            if (var5_5 == null) {
                var5_5 = new StringContextIterator(var2_2, var11_11, var8_8);
            } else {
                var5_5.setCPStartAndLimit(var11_11, var8_8);
            }
            CaseMapImpl.appendUnchanged(var2_2, var7_7, var11_11 - var7_7, var3_3, var1_1, var4_4);
            var7_7 = var11_11;
            if ((var13_13 = UCaseProps.INSTANCE.toFullUpper(var13_13, var5_5, var3_3, var0)) < 0) continue;
            CaseMapImpl.appendResult(var13_13, var3_3, var8_8 - var11_11, var1_1, var4_4);
            var7_7 = var8_8;
        }
        CaseMapImpl.appendUnchanged(var2_2, var7_7, var8_8 - var7_7, var3_3, var1_1, var4_4);
    }

    public static String toLower(int n, int n2, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 0x4000) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            StringBuilder stringBuilder = CaseMapImpl.toLower(n, n2 | 0x4000, charSequence, new StringBuilder(), edits);
            return CaseMapImpl.applyEdits(charSequence, stringBuilder, edits);
        }
        return CaseMapImpl.toLower(n, n2, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    public static <A extends Appendable> A toLower(int n, int n2, CharSequence charSequence, A a, Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            CaseMapImpl.internalToLower(n, n2, charSequence, 0, charSequence.length(), null, a, edits);
            return a;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public static String toUpper(int n, int n2, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 0x4000) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            StringBuilder stringBuilder = CaseMapImpl.toUpper(n, n2 | 0x4000, charSequence, new StringBuilder(), edits);
            return CaseMapImpl.applyEdits(charSequence, stringBuilder, edits);
        }
        return CaseMapImpl.toUpper(n, n2, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    public static <A extends Appendable> A toUpper(int n, int n2, CharSequence charSequence, A a, Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            if (n == 4) {
                return (A)GreekUpper.access$100(n2, charSequence, a, edits);
            }
            CaseMapImpl.internalToUpper(n, n2, charSequence, a, edits);
            return a;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public static String toTitle(int n, int n2, BreakIterator breakIterator, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 0x4000) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            StringBuilder stringBuilder = CaseMapImpl.toTitle(n, n2 | 0x4000, breakIterator, charSequence, new StringBuilder(), edits);
            return CaseMapImpl.applyEdits(charSequence, stringBuilder, edits);
        }
        return CaseMapImpl.toTitle(n, n2, breakIterator, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    public static <A extends Appendable> A toTitle(int n, int n2, BreakIterator breakIterator, CharSequence charSequence, A a, Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            StringContextIterator stringContextIterator = new StringContextIterator(charSequence);
            int n3 = charSequence.length();
            int n4 = 0;
            boolean bl = true;
            while (n4 < n3) {
                int n5;
                if (bl) {
                    bl = false;
                    n5 = breakIterator.first();
                } else {
                    n5 = breakIterator.next();
                }
                if (n5 == -1 || n5 > n3) {
                    n5 = n3;
                }
                if (n4 < n5) {
                    int n6;
                    int n7 = n4;
                    stringContextIterator.setLimit(n5);
                    int n8 = stringContextIterator.nextCaseMapCP();
                    if ((n2 & 0x200) == 0) {
                        int n9 = n6 = (n2 & 0x400) != 0 ? 1 : 0;
                        while ((n6 != 0 ? 0 == UCaseProps.INSTANCE.getType(n8) : !CaseMapImpl.isLNS(n8)) && (n8 = stringContextIterator.nextCaseMapCP()) >= 0) {
                        }
                        n7 = stringContextIterator.getCPStart();
                        if (n4 < n7) {
                            CaseMapImpl.appendUnchanged(charSequence, n4, n7 - n4, a, n2, edits);
                        }
                    }
                    if (n7 < n5) {
                        char c;
                        n6 = stringContextIterator.getCPLimit();
                        n8 = UCaseProps.INSTANCE.toFullTitle(n8, stringContextIterator, a, n);
                        CaseMapImpl.appendResult(n8, a, stringContextIterator.getCPLength(), n2, edits);
                        if (n7 + 1 < n5 && n == 5 && ((c = charSequence.charAt(n7)) == 'i' || c == 'I')) {
                            char c2 = charSequence.charAt(n7 + 1);
                            if (c2 == 'j') {
                                a.append('J');
                                if (edits != null) {
                                    edits.addReplace(1, 1);
                                }
                                n8 = stringContextIterator.nextCaseMapCP();
                                ++n6;
                                if (!$assertionsDisabled && n8 != c2) {
                                    throw new AssertionError();
                                }
                                if (!$assertionsDisabled && n6 != stringContextIterator.getCPLimit()) {
                                    throw new AssertionError();
                                }
                            } else if (c2 == 'J') {
                                CaseMapImpl.appendUnchanged(charSequence, n7 + 1, 1, a, n2, edits);
                                n8 = stringContextIterator.nextCaseMapCP();
                                ++n6;
                                if (!$assertionsDisabled && n8 != c2) {
                                    throw new AssertionError();
                                }
                                if (!$assertionsDisabled && n6 != stringContextIterator.getCPLimit()) {
                                    throw new AssertionError();
                                }
                            }
                        }
                        if (n6 < n5) {
                            if ((n2 & 0x100) == 0) {
                                CaseMapImpl.internalToLower(n, n2, charSequence, n6, n5, stringContextIterator, a, edits);
                            } else {
                                CaseMapImpl.appendUnchanged(charSequence, n6, n5 - n6, a, n2, edits);
                            }
                            stringContextIterator.moveToLimit();
                        }
                    }
                }
                n4 = n5;
            }
            return a;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public static String fold(int n, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n & 0x4000) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            StringBuilder stringBuilder = CaseMapImpl.fold(n | 0x4000, charSequence, new StringBuilder(), edits);
            return CaseMapImpl.applyEdits(charSequence, stringBuilder, edits);
        }
        return CaseMapImpl.fold(n, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    public static <A extends Appendable> A fold(int n, CharSequence charSequence, A a, Edits edits) {
        try {
            if (edits != null) {
                edits.reset();
            }
            CaseMapImpl.internalToLower(-1, n, charSequence, 0, charSequence.length(), null, a, edits);
            return a;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    static void access$200(int n, Appendable appendable, int n2, int n3, Edits edits) throws IOException {
        CaseMapImpl.appendResult(n, appendable, n2, n3, edits);
    }

    static {
        $assertionsDisabled = !CaseMapImpl.class.desiredAssertionStatus();
        CASE_TRIE = UCaseProps.getTrie();
    }

    private static final class GreekUpper {
        private static final int UPPER_MASK = 1023;
        private static final int HAS_VOWEL = 4096;
        private static final int HAS_YPOGEGRAMMENI = 8192;
        private static final int HAS_ACCENT = 16384;
        private static final int HAS_DIALYTIKA = 32768;
        private static final int HAS_COMBINING_DIALYTIKA = 65536;
        private static final int HAS_OTHER_GREEK_DIACRITIC = 131072;
        private static final int HAS_VOWEL_AND_ACCENT = 20480;
        private static final int HAS_VOWEL_AND_ACCENT_AND_DIALYTIKA = 53248;
        private static final int HAS_EITHER_DIALYTIKA = 98304;
        private static final int AFTER_CASED = 1;
        private static final int AFTER_VOWEL_WITH_ACCENT = 2;
        private static final char[] data0370 = new char[]{'\u0370', '\u0370', '\u0372', '\u0372', '\u0000', '\u0000', '\u0376', '\u0376', '\u0000', '\u0000', '\u037a', '\u03fd', '\u03fe', '\u03ff', '\u0000', '\u037f', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u5391', '\u0000', '\u5395', '\u5397', '\u5399', '\u0000', '\u539f', '\u0000', '\u53a5', '\u53a9', '\ud399', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\u0000', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\u9399', '\u93a5', '\u5391', '\u5395', '\u5397', '\u5399', '\ud3a5', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\u03a3', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\u9399', '\u93a5', '\u539f', '\u53a5', '\u53a9', '\u03cf', '\u0392', '\u0398', '\u03d2', '\u43d2', '\u83d2', '\u03a6', '\u03a0', '\u03cf', '\u03d8', '\u03d8', '\u03da', '\u03da', '\u03dc', '\u03dc', '\u03de', '\u03de', '\u03e0', '\u03e0', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u039a', '\u03a1', '\u03f9', '\u037f', '\u03f4', '\u1395', '\u0000', '\u03f7', '\u03f7', '\u03f9', '\u03fa', '\u03fa', '\u03fc', '\u03fd', '\u03fe', '\u03ff'};
        private static final char[] data1F00 = new char[]{'\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\u0000', '\u0000', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\u0000', '\u0000', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\u0000', '\u0000', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\u0000', '\u0000', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u0000', '\u13a5', '\u0000', '\u53a5', '\u0000', '\u53a5', '\u0000', '\u53a5', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u5391', '\u5391', '\u5395', '\u5395', '\u5397', '\u5397', '\u5399', '\u5399', '\u539f', '\u539f', '\u53a5', '\u53a5', '\u53a9', '\u53a9', '\u0000', '\u0000', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u1391', '\u1391', '\u7391', '\u3391', '\u7391', '\u0000', '\u5391', '\u7391', '\u1391', '\u1391', '\u5391', '\u5391', '\u3391', '\u0000', '\u1399', '\u0000', '\u0000', '\u0000', '\u7397', '\u3397', '\u7397', '\u0000', '\u5397', '\u7397', '\u5395', '\u5395', '\u5397', '\u5397', '\u3397', '\u0000', '\u0000', '\u0000', '\u1399', '\u1399', '\ud399', '\ud399', '\u0000', '\u0000', '\u5399', '\ud399', '\u1399', '\u1399', '\u5399', '\u5399', '\u0000', '\u0000', '\u0000', '\u0000', '\u13a5', '\u13a5', '\ud3a5', '\ud3a5', '\u03a1', '\u03a1', '\u53a5', '\ud3a5', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u03a1', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u73a9', '\u33a9', '\u73a9', '\u0000', '\u53a9', '\u73a9', '\u539f', '\u539f', '\u53a9', '\u53a9', '\u33a9', '\u0000', '\u0000', '\u0000'};
        private static final char data2126 = '\u13a9';

        private GreekUpper() {
        }

        private static final int getLetterData(int n) {
            if (n < 880 || 8486 < n || 1023 < n && n < 7936) {
                return 1;
            }
            if (n <= 1023) {
                return data0370[n - 880];
            }
            if (n <= 8191) {
                return data1F00[n - 7936];
            }
            if (n == 8486) {
                return 0;
            }
            return 1;
        }

        private static final int getDiacriticData(int n) {
            switch (n) {
                case 768: 
                case 769: 
                case 770: 
                case 771: 
                case 785: 
                case 834: {
                    return 1;
                }
                case 776: {
                    return 1;
                }
                case 836: {
                    return 1;
                }
                case 837: {
                    return 1;
                }
                case 772: 
                case 774: 
                case 787: 
                case 788: 
                case 835: {
                    return 1;
                }
            }
            return 1;
        }

        private static boolean isFollowedByCasedLetter(CharSequence charSequence, int n) {
            while (n < charSequence.length()) {
                int n2 = Character.codePointAt(charSequence, n);
                int n3 = UCaseProps.INSTANCE.getTypeOrIgnorable(n2);
                if ((n3 & 4) != 0) {
                    n += Character.charCount(n2);
                    continue;
                }
                return n3 == 0;
            }
            return true;
        }

        private static <A extends Appendable> A toUpper(int n, CharSequence charSequence, A a, Edits edits) throws IOException {
            int n2 = 0;
            int n3 = 0;
            while (n3 < charSequence.length()) {
                int n4 = Character.codePointAt(charSequence, n3);
                int n5 = n3 + Character.charCount(n4);
                int n6 = 0;
                int n7 = UCaseProps.INSTANCE.getTypeOrIgnorable(n4);
                if ((n7 & 4) != 0) {
                    n6 |= n2 & true;
                } else if (n7 != 0) {
                    n6 |= 1;
                }
                int n8 = GreekUpper.getLetterData(n4);
                if (n8 > 0) {
                    boolean bl;
                    int n9;
                    int n10 = n8 & 0x3FF;
                    if ((n8 & 0x1000) != 0 && (n2 & 2) != 0 && (n10 == 921 || n10 == 933)) {
                        n8 |= 0x8000;
                    }
                    int n11 = 0;
                    if ((n8 & 0x2000) != 0) {
                        n11 = 1;
                    }
                    while (n5 < charSequence.length() && (n9 = GreekUpper.getDiacriticData(charSequence.charAt(n5))) != 0) {
                        n8 |= n9;
                        if ((n9 & 0x2000) != 0) {
                            ++n11;
                        }
                        ++n5;
                    }
                    if ((n8 & 0xD000) == 20480) {
                        n6 |= 2;
                    }
                    n9 = 0;
                    if (n10 == 919 && (n8 & 0x4000) != 0 && n11 == 0 && (n2 & 1) == 0 && !GreekUpper.isFollowedByCasedLetter(charSequence, n5)) {
                        if (n3 == n5) {
                            n10 = 905;
                        } else {
                            n9 = 1;
                        }
                    } else if ((n8 & 0x8000) != 0) {
                        if (n10 == 921) {
                            n10 = 938;
                            n8 &= 0xFFFE7FFF;
                        } else if (n10 == 933) {
                            n10 = 939;
                            n8 &= 0xFFFE7FFF;
                        }
                    }
                    if (edits == null && (n & 0x4000) == 0) {
                        bl = true;
                    } else {
                        int n12;
                        int n13;
                        bl = charSequence.charAt(n3) != n10 || n11 > 0;
                        int n14 = n3 + 1;
                        if ((n8 & 0x18000) != 0) {
                            bl |= n14 >= n5 || charSequence.charAt(n14) != '\u0308';
                            ++n14;
                        }
                        if (n9 != 0) {
                            bl |= n14 >= n5 || charSequence.charAt(n14) != '\u0301';
                            ++n14;
                        }
                        if (bl |= (n13 = n5 - n3) != (n12 = n14 - n3 + n11)) {
                            if (edits != null) {
                                edits.addReplace(n13, n12);
                            }
                        } else {
                            if (edits != null) {
                                edits.addUnchanged(n13);
                            }
                            boolean bl2 = bl = (n & 0x4000) == 0;
                        }
                    }
                    if (bl) {
                        a.append((char)n10);
                        if ((n8 & 0x18000) != 0) {
                            a.append('\u0308');
                        }
                        if (n9 != 0) {
                            a.append('\u0301');
                        }
                        while (n11 > 0) {
                            a.append('\u0399');
                            --n11;
                        }
                    }
                } else {
                    n4 = UCaseProps.INSTANCE.toFullUpper(n4, null, a, 4);
                    CaseMapImpl.access$200(n4, a, n5 - n3, n, edits);
                }
                n3 = n5;
                n2 = n6;
            }
            return a;
        }

        static Appendable access$100(int n, CharSequence charSequence, Appendable appendable, Edits edits) throws IOException {
            return GreekUpper.toUpper(n, charSequence, appendable, edits);
        }
    }

    private static final class WholeStringBreakIterator
    extends BreakIterator {
        private int length;

        private WholeStringBreakIterator() {
        }

        private static void notImplemented() {
            throw new UnsupportedOperationException("should not occur");
        }

        @Override
        public int first() {
            return 1;
        }

        @Override
        public int last() {
            WholeStringBreakIterator.notImplemented();
            return 1;
        }

        @Override
        public int next(int n) {
            WholeStringBreakIterator.notImplemented();
            return 1;
        }

        @Override
        public int next() {
            return this.length;
        }

        @Override
        public int previous() {
            WholeStringBreakIterator.notImplemented();
            return 1;
        }

        @Override
        public int following(int n) {
            WholeStringBreakIterator.notImplemented();
            return 1;
        }

        @Override
        public int current() {
            WholeStringBreakIterator.notImplemented();
            return 1;
        }

        @Override
        public CharacterIterator getText() {
            WholeStringBreakIterator.notImplemented();
            return null;
        }

        @Override
        public void setText(CharacterIterator characterIterator) {
            this.length = characterIterator.getEndIndex();
        }

        @Override
        public void setText(CharSequence charSequence) {
            this.length = charSequence.length();
        }

        @Override
        public void setText(String string) {
            this.length = string.length();
        }

        WholeStringBreakIterator(1 var1_1) {
            this();
        }
    }

    public static final class StringContextIterator
    implements UCaseProps.ContextIterator {
        protected CharSequence s;
        protected int index;
        protected int limit;
        protected int cpStart;
        protected int cpLimit;
        protected int dir;

        public StringContextIterator(CharSequence charSequence) {
            this.s = charSequence;
            this.limit = charSequence.length();
            this.index = 0;
            this.cpLimit = 0;
            this.cpStart = 0;
            this.dir = 0;
        }

        public StringContextIterator(CharSequence charSequence, int n, int n2) {
            this.s = charSequence;
            this.index = 0;
            this.limit = charSequence.length();
            this.cpStart = n;
            this.cpLimit = n2;
            this.dir = 0;
        }

        public void setLimit(int n) {
            this.limit = 0 <= n && n <= this.s.length() ? n : this.s.length();
        }

        public void moveToLimit() {
            this.cpStart = this.cpLimit = this.limit;
        }

        public int nextCaseMapCP() {
            this.cpStart = this.cpLimit;
            if (this.cpLimit < this.limit) {
                int n = Character.codePointAt(this.s, this.cpLimit);
                this.cpLimit += Character.charCount(n);
                return n;
            }
            return 1;
        }

        public void setCPStartAndLimit(int n, int n2) {
            this.cpStart = n;
            this.cpLimit = n2;
            this.dir = 0;
        }

        public int getCPStart() {
            return this.cpStart;
        }

        public int getCPLimit() {
            return this.cpLimit;
        }

        public int getCPLength() {
            return this.cpLimit - this.cpStart;
        }

        @Override
        public void reset(int n) {
            if (n > 0) {
                this.dir = 1;
                this.index = this.cpLimit;
            } else if (n < 0) {
                this.dir = -1;
                this.index = this.cpStart;
            } else {
                this.dir = 0;
                this.index = 0;
            }
        }

        @Override
        public int next() {
            if (this.dir > 0 && this.index < this.s.length()) {
                int n = Character.codePointAt(this.s, this.index);
                this.index += Character.charCount(n);
                return n;
            }
            if (this.dir < 0 && this.index > 0) {
                int n = Character.codePointBefore(this.s, this.index);
                this.index -= Character.charCount(n);
                return n;
            }
            return 1;
        }
    }
}

