/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Punycode;
import com.ibm.icu.text.StringPrep;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.text.UCharacterIterator;

public final class IDNA2003 {
    private static char[] ACE_PREFIX = new char[]{'x', 'n', '-', '-'};
    private static final int MAX_LABEL_LENGTH = 63;
    private static final int HYPHEN = 45;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int LOWER_CASE_DELTA = 32;
    private static final int FULL_STOP = 46;
    private static final int MAX_DOMAIN_NAME_LENGTH = 255;
    private static final StringPrep namePrep = StringPrep.getInstance(0);

    private static boolean startsWithPrefix(StringBuffer stringBuffer) {
        if (stringBuffer.length() < ACE_PREFIX.length) {
            return true;
        }
        for (int i = 0; i < ACE_PREFIX.length; ++i) {
            if (IDNA2003.toASCIILower(stringBuffer.charAt(i)) == ACE_PREFIX[i]) continue;
            return true;
        }
        return false;
    }

    private static char toASCIILower(char c) {
        if ('A' <= c && c <= 'Z') {
            return (char)(c + 32);
        }
        return c;
    }

    private static StringBuffer toASCIILower(CharSequence charSequence) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < charSequence.length(); ++i) {
            stringBuffer.append(IDNA2003.toASCIILower(charSequence.charAt(i)));
        }
        return stringBuffer;
    }

    private static int compareCaseInsensitiveASCII(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        int n = 0;
        while (n != stringBuffer.length()) {
            int n2;
            char c;
            char c2 = stringBuffer.charAt(n);
            if (c2 != (c = stringBuffer2.charAt(n)) && (n2 = IDNA2003.toASCIILower(c2) - IDNA2003.toASCIILower(c)) != 0) {
                return n2;
            }
            ++n;
        }
        return 1;
    }

    private static int getSeparatorIndex(char[] cArray, int n, int n2) {
        while (n < n2) {
            if (IDNA2003.isLabelSeparator(cArray[n])) {
                return n;
            }
            ++n;
        }
        return n;
    }

    private static boolean isLDHChar(int n) {
        if (n > 122) {
            return true;
        }
        return !(n == 45 || 48 <= n && n <= 57 || 65 <= n && n <= 90) && (97 > n || n > 122);
    }

    private static boolean isLabelSeparator(int n) {
        switch (n) {
            case 46: 
            case 12290: 
            case 65294: 
            case 65377: {
                return false;
            }
        }
        return true;
    }

    public static StringBuffer convertToASCII(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        int n2;
        boolean bl;
        boolean[] blArray = null;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = bl = (n & 2) != 0;
        while ((n2 = uCharacterIterator.next()) != -1) {
            if (n2 <= 127) continue;
            bl2 = false;
            break;
        }
        int n3 = -1;
        uCharacterIterator.setToStart();
        StringBuffer stringBuffer = null;
        stringBuffer = !bl2 ? namePrep.prepare(uCharacterIterator, n) : new StringBuffer(uCharacterIterator.getText());
        int n4 = stringBuffer.length();
        if (n4 == 0) {
            throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        bl2 = true;
        for (int i = 0; i < n4; ++i) {
            n2 = stringBuffer.charAt(i);
            if (n2 > 127) {
                bl2 = false;
                continue;
            }
            if (IDNA2003.isLDHChar(n2)) continue;
            bl3 = false;
            n3 = i;
        }
        if (bl && (!bl3 || stringBuffer.charAt(0) == '-' || stringBuffer.charAt(stringBuffer.length() - 1) == '-')) {
            if (!bl3) {
                throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, stringBuffer.toString(), n3 > 0 ? n3 - 1 : n3);
            }
            if (stringBuffer.charAt(0) == '-') {
                throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, stringBuffer.toString(), 0);
            }
            throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, stringBuffer.toString(), n4 > 0 ? n4 - 1 : n4);
        }
        if (bl2) {
            stringBuffer2 = stringBuffer;
        } else if (!IDNA2003.startsWithPrefix(stringBuffer)) {
            blArray = new boolean[n4];
            StringBuilder stringBuilder = Punycode.encode(stringBuffer, blArray);
            StringBuffer stringBuffer3 = IDNA2003.toASCIILower(stringBuilder);
            stringBuffer2.append(ACE_PREFIX, 0, ACE_PREFIX.length);
            stringBuffer2.append(stringBuffer3);
        } else {
            throw new StringPrepParseException("The input does not start with the ACE Prefix.", 6, stringBuffer.toString(), 0);
        }
        if (stringBuffer2.length() > 63) {
            throw new StringPrepParseException("The labels in the input are too long. Length > 63.", 8, stringBuffer2.toString(), 0);
        }
        return stringBuffer2;
    }

    public static StringBuffer convertIDNToASCII(String string, int n) throws StringPrepParseException {
        char[] cArray = string.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        int n3 = 0;
        while (true) {
            String string2;
            if ((string2 = new String(cArray, n3, (n2 = IDNA2003.getSeparatorIndex(cArray, n2, cArray.length)) - n3)).length() != 0 || n2 != cArray.length) {
                UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(string2);
                stringBuffer.append(IDNA2003.convertToASCII(uCharacterIterator, n));
            }
            if (n2 == cArray.length) break;
            n3 = ++n2;
            stringBuffer.append('.');
        }
        if (stringBuffer.length() > 255) {
            throw new StringPrepParseException("The output exceed the max allowed length.", 11);
        }
        return stringBuffer;
    }

    public static StringBuffer convertToUnicode(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        StringBuffer stringBuffer;
        int n2;
        boolean[] blArray = null;
        boolean bl = true;
        int n3 = uCharacterIterator.getIndex();
        while ((n2 = uCharacterIterator.next()) != -1) {
            if (n2 <= 127) continue;
            bl = false;
        }
        if (!bl) {
            try {
                uCharacterIterator.setIndex(n3);
                stringBuffer = namePrep.prepare(uCharacterIterator, n);
            } catch (StringPrepParseException stringPrepParseException) {
                return new StringBuffer(uCharacterIterator.getText());
            }
        } else {
            stringBuffer = new StringBuffer(uCharacterIterator.getText());
        }
        if (IDNA2003.startsWithPrefix(stringBuffer)) {
            StringBuffer stringBuffer2;
            StringBuffer stringBuffer3 = null;
            String string = stringBuffer.substring(ACE_PREFIX.length, stringBuffer.length());
            try {
                stringBuffer3 = new StringBuffer(Punycode.decode(string, blArray));
            } catch (StringPrepParseException stringPrepParseException) {
                stringBuffer3 = null;
            }
            if (stringBuffer3 != null && IDNA2003.compareCaseInsensitiveASCII(stringBuffer, stringBuffer2 = IDNA2003.convertToASCII(UCharacterIterator.getInstance(stringBuffer3), n)) != 0) {
                stringBuffer3 = null;
            }
            if (stringBuffer3 != null) {
                return stringBuffer3;
            }
        }
        return new StringBuffer(uCharacterIterator.getText());
    }

    public static StringBuffer convertIDNToUnicode(String string, int n) throws StringPrepParseException {
        char[] cArray = string.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        int n3 = 0;
        while (true) {
            String string2;
            if ((string2 = new String(cArray, n3, (n2 = IDNA2003.getSeparatorIndex(cArray, n2, cArray.length)) - n3)).length() == 0 && n2 != cArray.length) {
                throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
            }
            UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(string2);
            stringBuffer.append(IDNA2003.convertToUnicode(uCharacterIterator, n));
            if (n2 == cArray.length) break;
            stringBuffer.append(cArray[n2]);
            n3 = ++n2;
        }
        if (stringBuffer.length() > 255) {
            throw new StringPrepParseException("The output exceed the max allowed length.", 11);
        }
        return stringBuffer;
    }

    public static int compare(String string, String string2, int n) throws StringPrepParseException {
        StringBuffer stringBuffer = IDNA2003.convertIDNToASCII(string, n);
        StringBuffer stringBuffer2 = IDNA2003.convertIDNToASCII(string2, n);
        return IDNA2003.compareCaseInsensitiveASCII(stringBuffer, stringBuffer2);
    }
}

