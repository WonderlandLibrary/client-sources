/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Punycode;
import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.IDNA;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.util.ICUException;
import java.util.EnumSet;

public final class UTS46
extends IDNA {
    private static final Normalizer2 uts46Norm2 = Normalizer2.getInstance(null, "uts46", Normalizer2.Mode.COMPOSE);
    final int options;
    private static final EnumSet<IDNA.Error> severeErrors = EnumSet.of(IDNA.Error.LEADING_COMBINING_MARK, IDNA.Error.DISALLOWED, IDNA.Error.PUNYCODE, IDNA.Error.LABEL_HAS_DOT, IDNA.Error.INVALID_ACE_LABEL);
    private static final byte[] asciiData = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1};
    private static final int L_MASK = UTS46.U_MASK(0);
    private static final int R_AL_MASK = UTS46.U_MASK(1) | UTS46.U_MASK(13);
    private static final int L_R_AL_MASK = L_MASK | R_AL_MASK;
    private static final int R_AL_AN_MASK = R_AL_MASK | UTS46.U_MASK(5);
    private static final int EN_AN_MASK = UTS46.U_MASK(2) | UTS46.U_MASK(5);
    private static final int R_AL_EN_AN_MASK = R_AL_MASK | EN_AN_MASK;
    private static final int L_EN_MASK = L_MASK | UTS46.U_MASK(2);
    private static final int ES_CS_ET_ON_BN_NSM_MASK = UTS46.U_MASK(3) | UTS46.U_MASK(6) | UTS46.U_MASK(4) | UTS46.U_MASK(10) | UTS46.U_MASK(18) | UTS46.U_MASK(17);
    private static final int L_EN_ES_CS_ET_ON_BN_NSM_MASK = L_EN_MASK | ES_CS_ET_ON_BN_NSM_MASK;
    private static final int R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK = R_AL_MASK | EN_AN_MASK | ES_CS_ET_ON_BN_NSM_MASK;
    private static int U_GC_M_MASK = UTS46.U_MASK(6) | UTS46.U_MASK(7) | UTS46.U_MASK(8);

    public UTS46(int n) {
        this.options = n;
    }

    @Override
    public StringBuilder labelToASCII(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, true, true, stringBuilder, info);
    }

    @Override
    public StringBuilder labelToUnicode(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, true, false, stringBuilder, info);
    }

    @Override
    public StringBuilder nameToASCII(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        this.process(charSequence, false, true, stringBuilder, info);
        if (stringBuilder.length() >= 254 && !info.getErrors().contains((Object)IDNA.Error.DOMAIN_NAME_TOO_LONG) && UTS46.isASCIIString(stringBuilder) && (stringBuilder.length() > 254 || stringBuilder.charAt(253) != '.')) {
            UTS46.addError(info, IDNA.Error.DOMAIN_NAME_TOO_LONG);
        }
        return stringBuilder;
    }

    @Override
    public StringBuilder nameToUnicode(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, false, false, stringBuilder, info);
    }

    private static boolean isASCIIString(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (charSequence.charAt(i) <= '\u007f') continue;
            return true;
        }
        return false;
    }

    private StringBuilder process(CharSequence charSequence, boolean bl, boolean bl2, StringBuilder stringBuilder, IDNA.Info info) {
        if (stringBuilder == charSequence) {
            throw new IllegalArgumentException();
        }
        stringBuilder.delete(0, Integer.MAX_VALUE);
        UTS46.resetInfo(info);
        int n = charSequence.length();
        if (n == 0) {
            UTS46.addError(info, IDNA.Error.EMPTY_LABEL);
            return stringBuilder;
        }
        boolean bl3 = (this.options & 2) != 0;
        int n2 = 0;
        int n3 = 0;
        while (true) {
            if (n3 == n) {
                if (bl2) {
                    if (n3 - n2 > 63) {
                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                    }
                    if (!(bl || n3 < 254 || n3 <= 254 && n2 >= n3)) {
                        UTS46.addError(info, IDNA.Error.DOMAIN_NAME_TOO_LONG);
                    }
                }
                UTS46.promoteAndResetLabelErrors(info);
                return stringBuilder;
            }
            char c = charSequence.charAt(n3);
            if (c > '\u007f') break;
            byte by = asciiData[c];
            if (by > 0) {
                stringBuilder.append((char)(c + 32));
            } else {
                if (by < 0 && bl3) break;
                stringBuilder.append(c);
                if (c == '-') {
                    if (n3 == n2 + 3 && charSequence.charAt(n3 - 1) == '-') {
                        ++n3;
                        break;
                    }
                    if (n3 == n2) {
                        UTS46.addLabelError(info, IDNA.Error.LEADING_HYPHEN);
                    }
                    if (n3 + 1 == n || charSequence.charAt(n3 + 1) == '.') {
                        UTS46.addLabelError(info, IDNA.Error.TRAILING_HYPHEN);
                    }
                } else if (c == '.') {
                    if (bl) {
                        ++n3;
                        break;
                    }
                    if (n3 == n2) {
                        UTS46.addLabelError(info, IDNA.Error.EMPTY_LABEL);
                    }
                    if (bl2 && n3 - n2 > 63) {
                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                    }
                    UTS46.promoteAndResetLabelErrors(info);
                    n2 = n3 + 1;
                }
            }
            ++n3;
        }
        UTS46.promoteAndResetLabelErrors(info);
        this.processUnicode(charSequence, n2, n3, bl, bl2, stringBuilder, info);
        if (UTS46.isBiDi(info) && !UTS46.hasCertainErrors(info, severeErrors) && (!UTS46.isOkBiDi(info) || n2 > 0 && !UTS46.isASCIIOkBiDi(stringBuilder, n2))) {
            UTS46.addError(info, IDNA.Error.BIDI);
        }
        return stringBuilder;
    }

    private StringBuilder processUnicode(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2, StringBuilder stringBuilder, IDNA.Info info) {
        if (n2 == 0) {
            uts46Norm2.normalize(charSequence, stringBuilder);
        } else {
            uts46Norm2.normalizeSecondAndAppend(stringBuilder, charSequence.subSequence(n2, charSequence.length()));
        }
        boolean bl3 = bl2 ? (this.options & 0x10) == 0 : (this.options & 0x20) == 0;
        int n3 = stringBuilder.length();
        int n4 = n;
        while (n4 < n3) {
            char c = stringBuilder.charAt(n4);
            if (c == '.' && !bl) {
                int n5 = n4 - n;
                int n6 = this.processLabel(stringBuilder, n, n5, bl2, info);
                UTS46.promoteAndResetLabelErrors(info);
                n3 += n6 - n5;
                n4 = n += n6 + 1;
                continue;
            }
            if (c >= '\u00df') {
                if (c <= '\u200d' && (c == '\u00df' || c == '\u03c2' || c >= '\u200c')) {
                    UTS46.setTransitionalDifferent(info);
                    if (bl3) {
                        n3 = this.mapDevChars(stringBuilder, n, n4);
                        bl3 = false;
                        continue;
                    }
                } else if (Character.isSurrogate(c) && (Normalizer2Impl.UTF16Plus.isSurrogateLead(c) ? n4 + 1 == n3 || !Character.isLowSurrogate(stringBuilder.charAt(n4 + 1)) : n4 == n || !Character.isHighSurrogate(stringBuilder.charAt(n4 - 1)))) {
                    UTS46.addLabelError(info, IDNA.Error.DISALLOWED);
                    stringBuilder.setCharAt(n4, '\ufffd');
                }
            }
            ++n4;
        }
        if (0 == n || n < n4) {
            this.processLabel(stringBuilder, n, n4 - n, bl2, info);
            UTS46.promoteAndResetLabelErrors(info);
        }
        return stringBuilder;
    }

    private int mapDevChars(StringBuilder stringBuilder, int n, int n2) {
        int n3 = stringBuilder.length();
        boolean bl = false;
        int n4 = n2;
        block5: while (n4 < n3) {
            char c = stringBuilder.charAt(n4);
            switch (c) {
                case '\u00df': {
                    bl = true;
                    stringBuilder.setCharAt(n4++, 's');
                    stringBuilder.insert(n4++, 's');
                    ++n3;
                    continue block5;
                }
                case '\u03c2': {
                    bl = true;
                    stringBuilder.setCharAt(n4++, '\u03c3');
                    continue block5;
                }
                case '\u200c': 
                case '\u200d': {
                    bl = true;
                    stringBuilder.delete(n4, n4 + 1);
                    --n3;
                    continue block5;
                }
            }
            ++n4;
        }
        if (bl) {
            String string = uts46Norm2.normalize(stringBuilder.subSequence(n, stringBuilder.length()));
            stringBuilder.replace(n, Integer.MAX_VALUE, string);
            return stringBuilder.length();
        }
        return n3;
    }

    private static boolean isNonASCIIDisallowedSTD3Valid(int n) {
        return n == 8800 || n == 8814 || n == 8815;
    }

    private static int replaceLabel(StringBuilder stringBuilder, int n, int n2, CharSequence charSequence, int n3) {
        if (charSequence != stringBuilder) {
            stringBuilder.delete(n, n + n2).insert(n, charSequence);
        }
        return n3;
    }

    private int processLabel(StringBuilder stringBuilder, int n, int n2, boolean bl, IDNA.Info info) {
        int n3;
        boolean bl2;
        StringBuilder stringBuilder2;
        int n4;
        boolean bl3;
        int n5 = n;
        int n6 = n2;
        if (n2 >= 4 && stringBuilder.charAt(n) == 'x' && stringBuilder.charAt(n + 1) == 'n' && stringBuilder.charAt(n + 2) == '-' && stringBuilder.charAt(n + 3) == '-') {
            StringBuilder stringBuilder3;
            bl3 = true;
            try {
                stringBuilder3 = Punycode.decode(stringBuilder.subSequence(n + 4, n + n2), null);
            } catch (StringPrepParseException stringPrepParseException) {
                UTS46.addLabelError(info, IDNA.Error.PUNYCODE);
                return this.markBadACELabel(stringBuilder, n, n2, bl, info);
            }
            n4 = uts46Norm2.isNormalized(stringBuilder3) ? 1 : 0;
            if (n4 == 0) {
                UTS46.addLabelError(info, IDNA.Error.INVALID_ACE_LABEL);
                return this.markBadACELabel(stringBuilder, n, n2, bl, info);
            }
            stringBuilder2 = stringBuilder3;
            n = 0;
            n2 = stringBuilder3.length();
        } else {
            bl3 = false;
            stringBuilder2 = stringBuilder;
        }
        if (n2 == 0) {
            UTS46.addLabelError(info, IDNA.Error.EMPTY_LABEL);
            return UTS46.replaceLabel(stringBuilder, n5, n6, stringBuilder2, n2);
        }
        if (n2 >= 4 && stringBuilder2.charAt(n + 2) == '-' && stringBuilder2.charAt(n + 3) == '-') {
            UTS46.addLabelError(info, IDNA.Error.HYPHEN_3_4);
        }
        if (stringBuilder2.charAt(n) == '-') {
            UTS46.addLabelError(info, IDNA.Error.LEADING_HYPHEN);
        }
        if (stringBuilder2.charAt(n + n2 - 1) == '-') {
            UTS46.addLabelError(info, IDNA.Error.TRAILING_HYPHEN);
        }
        n4 = n;
        int n7 = n + n2;
        char c = '\u0000';
        boolean bl4 = bl2 = (this.options & 2) != 0;
        do {
            if ((n3 = stringBuilder2.charAt(n4)) <= 127) {
                if (n3 == 46) {
                    UTS46.addLabelError(info, IDNA.Error.LABEL_HAS_DOT);
                    stringBuilder2.setCharAt(n4, '\ufffd');
                    continue;
                }
                if (!bl2 || asciiData[n3] >= 0) continue;
                UTS46.addLabelError(info, IDNA.Error.DISALLOWED);
                stringBuilder2.setCharAt(n4, '\ufffd');
                continue;
            }
            c = (char)(c | n3);
            if (bl2 && UTS46.isNonASCIIDisallowedSTD3Valid(n3)) {
                UTS46.addLabelError(info, IDNA.Error.DISALLOWED);
                stringBuilder2.setCharAt(n4, '\ufffd');
                continue;
            }
            if (n3 != 65533) continue;
            UTS46.addLabelError(info, IDNA.Error.DISALLOWED);
        } while (++n4 < n7);
        n3 = stringBuilder2.codePointAt(n);
        if ((UTS46.U_GET_GC_MASK(n3) & U_GC_M_MASK) != 0) {
            UTS46.addLabelError(info, IDNA.Error.LEADING_COMBINING_MARK);
            stringBuilder2.setCharAt(n, '\ufffd');
            if (n3 > 65535) {
                stringBuilder2.deleteCharAt(n + 1);
                --n2;
                if (stringBuilder2 == stringBuilder) {
                    --n6;
                }
            }
        }
        if (!UTS46.hasCertainLabelErrors(info, severeErrors)) {
            if ((this.options & 4) != 0 && (!UTS46.isBiDi(info) || UTS46.isOkBiDi(info))) {
                this.checkLabelBiDi(stringBuilder2, n, n2, info);
            }
            if ((this.options & 8) != 0 && (c & 0x200C) == 8204 && !this.isLabelOkContextJ(stringBuilder2, n, n2)) {
                UTS46.addLabelError(info, IDNA.Error.CONTEXTJ);
            }
            if ((this.options & 0x40) != 0 && c >= '\u00b7') {
                this.checkLabelContextO(stringBuilder2, n, n2, info);
            }
            if (bl) {
                if (bl3) {
                    if (n6 > 63) {
                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                    }
                    return n6;
                }
                if (c >= '\u0080') {
                    StringBuilder stringBuilder4;
                    try {
                        stringBuilder4 = Punycode.encode(stringBuilder2.subSequence(n, n + n2), null);
                    } catch (StringPrepParseException stringPrepParseException) {
                        throw new ICUException(stringPrepParseException);
                    }
                    stringBuilder4.insert(0, "xn--");
                    if (stringBuilder4.length() > 63) {
                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                    }
                    return UTS46.replaceLabel(stringBuilder, n5, n6, stringBuilder4, stringBuilder4.length());
                }
                if (n2 > 63) {
                    UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                }
            }
        } else if (bl3) {
            UTS46.addLabelError(info, IDNA.Error.INVALID_ACE_LABEL);
            return this.markBadACELabel(stringBuilder, n5, n6, bl, info);
        }
        return UTS46.replaceLabel(stringBuilder, n5, n6, stringBuilder2, n2);
    }

    private int markBadACELabel(StringBuilder stringBuilder, int n, int n2, boolean bl, IDNA.Info info) {
        boolean bl2 = (this.options & 2) != 0;
        boolean bl3 = true;
        boolean bl4 = true;
        int n3 = n + 4;
        int n4 = n + n2;
        do {
            char c;
            if ((c = stringBuilder.charAt(n3)) <= '\u007f') {
                if (c == '.') {
                    UTS46.addLabelError(info, IDNA.Error.LABEL_HAS_DOT);
                    stringBuilder.setCharAt(n3, '\ufffd');
                    bl4 = false;
                    bl3 = false;
                    continue;
                }
                if (asciiData[c] >= 0) continue;
                bl4 = false;
                if (!bl2) continue;
                stringBuilder.setCharAt(n3, '\ufffd');
                bl3 = false;
                continue;
            }
            bl4 = false;
            bl3 = false;
        } while (++n3 < n4);
        if (bl4) {
            stringBuilder.insert(n + n2, '\ufffd');
            ++n2;
        } else if (bl && bl3 && n2 > 63) {
            UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
        }
        return n2;
    }

    private void checkLabelBiDi(CharSequence charSequence, int n, int n2, IDNA.Info info) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        block11: {
            n8 = n;
            n7 = Character.codePointAt(charSequence, n8);
            n8 += Character.charCount(n7);
            n6 = UTS46.U_MASK(UBiDiProps.INSTANCE.getClass(n7));
            if ((n6 & ~L_R_AL_MASK) != 0) {
                UTS46.setNotOkBiDi(info);
            }
            n5 = n + n2;
            do {
                if (n8 >= n5) {
                    n3 = n6;
                    break block11;
                }
                n7 = Character.codePointBefore(charSequence, n5);
                n5 -= Character.charCount(n7);
            } while ((n4 = UBiDiProps.INSTANCE.getClass(n7)) == 17);
            n3 = UTS46.U_MASK(n4);
        }
        if ((n6 & L_MASK) != 0 ? (n3 & ~L_EN_MASK) != 0 : (n3 & ~R_AL_EN_AN_MASK) != 0) {
            UTS46.setNotOkBiDi(info);
        }
        n4 = n6 | n3;
        while (n8 < n5) {
            n7 = Character.codePointAt(charSequence, n8);
            n8 += Character.charCount(n7);
            n4 |= UTS46.U_MASK(UBiDiProps.INSTANCE.getClass(n7));
        }
        if ((n6 & L_MASK) != 0) {
            if ((n4 & ~L_EN_ES_CS_ET_ON_BN_NSM_MASK) != 0) {
                UTS46.setNotOkBiDi(info);
            }
        } else {
            if ((n4 & ~R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK) != 0) {
                UTS46.setNotOkBiDi(info);
            }
            if ((n4 & EN_AN_MASK) == EN_AN_MASK) {
                UTS46.setNotOkBiDi(info);
            }
        }
        if ((n4 & R_AL_AN_MASK) != 0) {
            UTS46.setBiDi(info);
        }
    }

    private static boolean isASCIIOkBiDi(CharSequence charSequence, int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            if (c == '.') {
                if (!(i <= n2 || 'a' <= (c = charSequence.charAt(i - 1)) && c <= 'z' || '0' <= c && c <= '9')) {
                    return true;
                }
                n2 = i + 1;
                continue;
            }
            if (!(i == n2 ? 'a' > c || c > 'z' : c <= ' ' && (c >= '\u001c' || '\t' <= c && c <= '\r'))) continue;
            return true;
        }
        return false;
    }

    private boolean isLabelOkContextJ(CharSequence charSequence, int n, int n2) {
        int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            int n4;
            if (charSequence.charAt(i) == '\u200c') {
                int n5;
                if (i == n) {
                    return true;
                }
                int n6 = i;
                n4 = Character.codePointBefore(charSequence, n6);
                n6 -= Character.charCount(n4);
                if (uts46Norm2.getCombiningClass(n4) == 9) continue;
                while ((n5 = UBiDiProps.INSTANCE.getJoiningType(n4)) == 5) {
                    if (n6 == 0) {
                        return true;
                    }
                    n4 = Character.codePointBefore(charSequence, n6);
                    n6 -= Character.charCount(n4);
                }
                if (n5 != 3 && n5 != 2) {
                    return true;
                }
                n6 = i + 1;
                do {
                    if (n6 == n3) {
                        return true;
                    }
                    n4 = Character.codePointAt(charSequence, n6);
                    n6 += Character.charCount(n4);
                } while ((n5 = UBiDiProps.INSTANCE.getJoiningType(n4)) == 5);
                if (n5 == 4 || n5 == 2) continue;
                return true;
            }
            if (charSequence.charAt(i) != '\u200d') continue;
            if (i == n) {
                return true;
            }
            n4 = Character.codePointBefore(charSequence, i);
            if (uts46Norm2.getCombiningClass(n4) == 9) continue;
            return true;
        }
        return false;
    }

    private void checkLabelContextO(CharSequence charSequence, int n, int n2, IDNA.Info info) {
        int n3 = n + n2 - 1;
        int n4 = 0;
        block0: for (int i = n; i <= n3; ++i) {
            int n5 = charSequence.charAt(i);
            if (n5 < 183) continue;
            if (n5 <= 1785) {
                if (n5 == 183) {
                    if (n < i && charSequence.charAt(i - 1) == 'l' && i < n3 && charSequence.charAt(i + 1) == 'l') continue;
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                    continue;
                }
                if (n5 == 885) {
                    if (i < n3 && 14 == UScript.getScript(Character.codePointAt(charSequence, i + 1))) continue;
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                    continue;
                }
                if (n5 == 1523 || n5 == 1524) {
                    if (n < i && 19 == UScript.getScript(Character.codePointBefore(charSequence, i))) continue;
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                    continue;
                }
                if (1632 > n5) continue;
                if (n5 <= 1641) {
                    if (n4 > 0) {
                        UTS46.addLabelError(info, IDNA.Error.CONTEXTO_DIGITS);
                    }
                    n4 = -1;
                    continue;
                }
                if (1776 > n5) continue;
                if (n4 < 0) {
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_DIGITS);
                }
                n4 = 1;
                continue;
            }
            if (n5 != 12539) continue;
            int n6 = n;
            while (true) {
                if (n6 > n3) {
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                    continue block0;
                }
                n5 = Character.codePointAt(charSequence, n6);
                int n7 = UScript.getScript(n5);
                if (n7 == 20 || n7 == 22 || n7 == 17) continue block0;
                n6 += Character.charCount(n5);
            }
        }
    }

    private static int U_MASK(int n) {
        return 1 << n;
    }

    private static int U_GET_GC_MASK(int n) {
        return 1 << UCharacter.getType(n);
    }
}

