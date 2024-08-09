/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public final class SimpleFormatterImpl {
    private static final int ARG_NUM_LIMIT = 256;
    private static final char LEN1_CHAR = '\u0101';
    private static final char LEN2_CHAR = '\u0102';
    private static final char LEN3_CHAR = '\u0103';
    private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = '\uffff';
    private static final int MAX_SEGMENT_LENGTH = 65279;
    private static final String[][] COMMON_PATTERNS;
    static final boolean $assertionsDisabled;

    private SimpleFormatterImpl() {
    }

    public static String compileToStringMinMaxArguments(CharSequence charSequence, StringBuilder stringBuilder, int n, int n2) {
        if (n <= 2 && 2 <= n2) {
            for (String[] stringArray : COMMON_PATTERNS) {
                if (!stringArray[0].contentEquals(charSequence)) continue;
                if (!$assertionsDisabled && stringArray[5].charAt(0) != '\u0002') {
                    throw new AssertionError();
                }
                return stringArray[5];
            }
        }
        int n3 = charSequence.length();
        stringBuilder.ensureCapacity(n3);
        stringBuilder.setLength(1);
        int n4 = 0;
        int n5 = -1;
        boolean bl = false;
        int n6 = 0;
        while (n6 < n3) {
            int n7;
            if ((n7 = charSequence.charAt(n6++)) == 39) {
                if (n6 < n3 && (n7 = charSequence.charAt(n6)) == 39) {
                    ++n6;
                } else {
                    if (bl) {
                        bl = false;
                        continue;
                    }
                    if (n7 == 123 || n7 == 125) {
                        ++n6;
                        bl = true;
                    } else {
                        n7 = 39;
                    }
                }
            } else if (!bl && n7 == 123) {
                int n8;
                if (n4 > 0) {
                    stringBuilder.setCharAt(stringBuilder.length() - n4 - 1, (char)(256 + n4));
                    n4 = 0;
                }
                if (n6 + 1 < n3 && 0 <= (n8 = charSequence.charAt(n6) - 48) && n8 <= 9 && charSequence.charAt(n6 + 1) == '}') {
                    n6 += 2;
                } else {
                    int n9 = n6 - 1;
                    n8 = -1;
                    if (n6 < n3) {
                        char c = charSequence.charAt(n6++);
                        n7 = c;
                        if ('1' <= c && n7 <= 57) {
                            n8 = n7 - 48;
                            while (n6 < n3) {
                                char c2 = charSequence.charAt(n6++);
                                n7 = c2;
                                if ('0' <= c2 && n7 <= 57 && (n8 = n8 * 10 + (n7 - 48)) < 256) continue;
                            }
                        }
                    }
                    if (n8 < 0 || n7 != 125) {
                        throw new IllegalArgumentException("Argument syntax error in pattern \"" + charSequence + "\" at index " + n9 + ": " + charSequence.subSequence(n9, n6));
                    }
                }
                if (n8 > n5) {
                    n5 = n8;
                }
                stringBuilder.append((char)n8);
                continue;
            }
            if (n4 == 0) {
                stringBuilder.append('\uffff');
            }
            stringBuilder.append((char)n7);
            if (++n4 != 65279) continue;
            n4 = 0;
        }
        if (n4 > 0) {
            stringBuilder.setCharAt(stringBuilder.length() - n4 - 1, (char)(256 + n4));
        }
        if ((n6 = n5 + 1) < n) {
            throw new IllegalArgumentException("Fewer than minimum " + n + " arguments in pattern \"" + charSequence + "\"");
        }
        if (n6 > n2) {
            throw new IllegalArgumentException("More than maximum " + n2 + " arguments in pattern \"" + charSequence + "\"");
        }
        stringBuilder.setCharAt(0, (char)n6);
        return stringBuilder.toString();
    }

    public static int getArgumentLimit(String string) {
        return string.charAt(0);
    }

    public static String formatCompiledPattern(String string, CharSequence ... charSequenceArray) {
        return SimpleFormatterImpl.formatAndAppend(string, new StringBuilder(), null, charSequenceArray).toString();
    }

    public static String formatRawPattern(String string, int n, int n2, CharSequence ... charSequenceArray) {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, n, n2);
        stringBuilder.setLength(0);
        return SimpleFormatterImpl.formatAndAppend(string2, stringBuilder, null, charSequenceArray).toString();
    }

    public static StringBuilder formatAndAppend(String string, StringBuilder stringBuilder, int[] nArray, CharSequence ... charSequenceArray) {
        int n;
        int n2 = n = charSequenceArray != null ? charSequenceArray.length : 0;
        if (n < SimpleFormatterImpl.getArgumentLimit(string)) {
            throw new IllegalArgumentException("Too few values.");
        }
        return SimpleFormatterImpl.format(string, charSequenceArray, stringBuilder, null, true, nArray);
    }

    public static StringBuilder formatAndReplace(String string, StringBuilder stringBuilder, int[] nArray, CharSequence ... charSequenceArray) {
        int n;
        int n2 = n = charSequenceArray != null ? charSequenceArray.length : 0;
        if (n < SimpleFormatterImpl.getArgumentLimit(string)) {
            throw new IllegalArgumentException("Too few values.");
        }
        int n3 = -1;
        String string2 = null;
        if (SimpleFormatterImpl.getArgumentLimit(string) > 0) {
            int n4 = 1;
            while (n4 < string.length()) {
                char c;
                if ((c = string.charAt(n4++)) < '\u0100') {
                    if (charSequenceArray[c] != stringBuilder) continue;
                    if (n4 == 2) {
                        n3 = c;
                        continue;
                    }
                    if (string2 != null) continue;
                    string2 = stringBuilder.toString();
                    continue;
                }
                n4 += c - 256;
            }
        }
        if (n3 < 0) {
            stringBuilder.setLength(0);
        }
        return SimpleFormatterImpl.format(string, charSequenceArray, stringBuilder, string2, false, nArray);
    }

    public static String getTextWithNoArguments(String string) {
        int n = string.length() - 1 - SimpleFormatterImpl.getArgumentLimit(string);
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = 1;
        while (n2 < string.length()) {
            int n3;
            if ((n3 = string.charAt(n2++) - 256) <= 0) continue;
            int n4 = n2 + n3;
            stringBuilder.append(string, n2, n4);
            n2 = n4;
        }
        return stringBuilder.toString();
    }

    private static StringBuilder format(String string, CharSequence[] charSequenceArray, StringBuilder stringBuilder, String string2, boolean bl, int[] nArray) {
        int n;
        int n2;
        if (nArray == null) {
            n2 = 0;
        } else {
            n2 = nArray.length;
            for (n = 0; n < n2; ++n) {
                nArray[n] = -1;
            }
        }
        n = 1;
        while (n < string.length()) {
            char c;
            if ((c = string.charAt(n++)) < '\u0100') {
                CharSequence charSequence = charSequenceArray[c];
                if (charSequence == stringBuilder) {
                    if (bl) {
                        throw new IllegalArgumentException("Value must not be same object as result");
                    }
                    if (n == 2) {
                        if (c >= n2) continue;
                        nArray[c] = 0;
                        continue;
                    }
                    if (c < n2) {
                        nArray[c] = stringBuilder.length();
                    }
                    stringBuilder.append(string2);
                    continue;
                }
                if (c < n2) {
                    nArray[c] = stringBuilder.length();
                }
                stringBuilder.append(charSequence);
                continue;
            }
            int n3 = n + (c - 256);
            stringBuilder.append(string, n, n3);
            n = n3;
        }
        return stringBuilder;
    }

    static {
        $assertionsDisabled = !SimpleFormatterImpl.class.desiredAssertionStatus();
        COMMON_PATTERNS = new String[][]{{"{0} {1}", "\u0002\u0000\u0101 \u0001"}, {"{0} ({1})", "\u0002\u0000\u0102 (\u0001\u0101)"}, {"{0}, {1}", "\u0002\u0000\u0102, \u0001"}, {"{0} \u2013 {1}", "\u0002\u0000\u0103 \u2013 \u0001"}};
    }

    public static class Int64Iterator {
        public static final long DONE = -1L;
        static final boolean $assertionsDisabled = !SimpleFormatterImpl.class.desiredAssertionStatus();

        public static long step(CharSequence charSequence, long l, StringBuffer stringBuffer) {
            int n = (int)(l >>> 32);
            if (!$assertionsDisabled && n >= charSequence.length()) {
                throw new AssertionError();
            }
            ++n;
            while (n < charSequence.length() && charSequence.charAt(n) > '\u0100') {
                int n2 = n + charSequence.charAt(n) + 1 - 256;
                stringBuffer.append(charSequence, n + 1, n2);
                n = n2;
            }
            if (n == charSequence.length()) {
                return -1L;
            }
            return (long)n << 32 | (long)charSequence.charAt(n);
        }

        public static int getArgIndex(long l) {
            return (int)l;
        }
    }
}

