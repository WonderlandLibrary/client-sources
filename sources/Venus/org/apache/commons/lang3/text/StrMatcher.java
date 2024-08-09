/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public abstract class StrMatcher {
    private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
    private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
    private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
    private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
    private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
    private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
    private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('\"');
    private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
    private static final StrMatcher NONE_MATCHER = new NoMatcher();

    public static StrMatcher commaMatcher() {
        return COMMA_MATCHER;
    }

    public static StrMatcher tabMatcher() {
        return TAB_MATCHER;
    }

    public static StrMatcher spaceMatcher() {
        return SPACE_MATCHER;
    }

    public static StrMatcher splitMatcher() {
        return SPLIT_MATCHER;
    }

    public static StrMatcher trimMatcher() {
        return TRIM_MATCHER;
    }

    public static StrMatcher singleQuoteMatcher() {
        return SINGLE_QUOTE_MATCHER;
    }

    public static StrMatcher doubleQuoteMatcher() {
        return DOUBLE_QUOTE_MATCHER;
    }

    public static StrMatcher quoteMatcher() {
        return QUOTE_MATCHER;
    }

    public static StrMatcher noneMatcher() {
        return NONE_MATCHER;
    }

    public static StrMatcher charMatcher(char c) {
        return new CharMatcher(c);
    }

    public static StrMatcher charSetMatcher(char ... cArray) {
        if (cArray == null || cArray.length == 0) {
            return NONE_MATCHER;
        }
        if (cArray.length == 1) {
            return new CharMatcher(cArray[0]);
        }
        return new CharSetMatcher(cArray);
    }

    public static StrMatcher charSetMatcher(String string) {
        if (StringUtils.isEmpty(string)) {
            return NONE_MATCHER;
        }
        if (string.length() == 1) {
            return new CharMatcher(string.charAt(0));
        }
        return new CharSetMatcher(string.toCharArray());
    }

    public static StrMatcher stringMatcher(String string) {
        if (StringUtils.isEmpty(string)) {
            return NONE_MATCHER;
        }
        return new StringMatcher(string);
    }

    protected StrMatcher() {
    }

    public abstract int isMatch(char[] var1, int var2, int var3, int var4);

    public int isMatch(char[] cArray, int n) {
        return this.isMatch(cArray, n, 0, cArray.length);
    }

    static final class TrimMatcher
    extends StrMatcher {
        TrimMatcher() {
        }

        @Override
        public int isMatch(char[] cArray, int n, int n2, int n3) {
            return cArray[n] <= ' ' ? 1 : 0;
        }
    }

    static final class NoMatcher
    extends StrMatcher {
        NoMatcher() {
        }

        @Override
        public int isMatch(char[] cArray, int n, int n2, int n3) {
            return 1;
        }
    }

    static final class StringMatcher
    extends StrMatcher {
        private final char[] chars;

        StringMatcher(String string) {
            this.chars = string.toCharArray();
        }

        @Override
        public int isMatch(char[] cArray, int n, int n2, int n3) {
            int n4 = this.chars.length;
            if (n + n4 > n3) {
                return 1;
            }
            int n5 = 0;
            while (n5 < this.chars.length) {
                if (this.chars[n5] != cArray[n]) {
                    return 1;
                }
                ++n5;
                ++n;
            }
            return n4;
        }

        public String toString() {
            return super.toString() + ' ' + Arrays.toString(this.chars);
        }
    }

    static final class CharMatcher
    extends StrMatcher {
        private final char ch;

        CharMatcher(char c) {
            this.ch = c;
        }

        @Override
        public int isMatch(char[] cArray, int n, int n2, int n3) {
            return this.ch == cArray[n] ? 1 : 0;
        }
    }

    static final class CharSetMatcher
    extends StrMatcher {
        private final char[] chars;

        CharSetMatcher(char[] cArray) {
            this.chars = (char[])cArray.clone();
            Arrays.sort(this.chars);
        }

        @Override
        public int isMatch(char[] cArray, int n, int n2, int n3) {
            return Arrays.binarySearch(this.chars, cArray[n]) >= 0 ? 1 : 0;
        }
    }
}

