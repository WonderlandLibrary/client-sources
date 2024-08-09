/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.SmallCharMatcher;
import java.util.Arrays;
import java.util.BitSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(emulated=true)
public abstract class CharMatcher
implements Predicate<Character> {
    @Deprecated
    public static final CharMatcher WHITESPACE = CharMatcher.whitespace();
    @Deprecated
    public static final CharMatcher BREAKING_WHITESPACE = CharMatcher.breakingWhitespace();
    @Deprecated
    public static final CharMatcher ASCII = CharMatcher.ascii();
    @Deprecated
    public static final CharMatcher DIGIT = CharMatcher.digit();
    @Deprecated
    public static final CharMatcher JAVA_DIGIT = CharMatcher.javaDigit();
    @Deprecated
    public static final CharMatcher JAVA_LETTER = CharMatcher.javaLetter();
    @Deprecated
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = CharMatcher.javaLetterOrDigit();
    @Deprecated
    public static final CharMatcher JAVA_UPPER_CASE = CharMatcher.javaUpperCase();
    @Deprecated
    public static final CharMatcher JAVA_LOWER_CASE = CharMatcher.javaLowerCase();
    @Deprecated
    public static final CharMatcher JAVA_ISO_CONTROL = CharMatcher.javaIsoControl();
    @Deprecated
    public static final CharMatcher INVISIBLE = CharMatcher.invisible();
    @Deprecated
    public static final CharMatcher SINGLE_WIDTH = CharMatcher.singleWidth();
    @Deprecated
    public static final CharMatcher ANY = CharMatcher.any();
    @Deprecated
    public static final CharMatcher NONE = CharMatcher.none();
    private static final int DISTINCT_CHARS = 65536;

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.INSTANCE;
    }

    public static CharMatcher ascii() {
        return Ascii.INSTANCE;
    }

    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }

    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }

    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }

    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }

    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }

    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.INSTANCE;
    }

    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }

    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }

    public static CharMatcher is(char c) {
        return new Is(c);
    }

    public static CharMatcher isNot(char c) {
        return new IsNot(c);
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        switch (charSequence.length()) {
            case 0: {
                return CharMatcher.none();
            }
            case 1: {
                return CharMatcher.is(charSequence.charAt(0));
            }
            case 2: {
                return CharMatcher.isEither(charSequence.charAt(0), charSequence.charAt(1));
            }
        }
        return new AnyOf(charSequence);
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return CharMatcher.anyOf(charSequence).negate();
    }

    public static CharMatcher inRange(char c, char c2) {
        return new InRange(c, c2);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher)predicate : new ForPredicate(predicate);
    }

    protected CharMatcher() {
    }

    public abstract boolean matches(char var1);

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new And(this, charMatcher);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new Or(this, charMatcher);
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    @GwtIncompatible
    CharMatcher precomputedInternal() {
        BitSet bitSet = new BitSet();
        this.setBits(bitSet);
        int n = bitSet.cardinality();
        if (n * 2 <= 65536) {
            return CharMatcher.precomputedPositive(n, bitSet, this.toString());
        }
        bitSet.flip(0, 65536);
        int n2 = 65536 - n;
        String string = ".negate()";
        String string2 = this.toString();
        String string3 = string2.endsWith(string) ? string2.substring(0, string2.length() - string.length()) : string2 + string;
        return new NegatedFastMatcher(this, CharMatcher.precomputedPositive(n2, bitSet, string3), string2){
            final String val$description;
            final CharMatcher this$0;
            {
                this.this$0 = charMatcher;
                this.val$description = string;
                super(charMatcher2);
            }

            @Override
            public String toString() {
                return this.val$description;
            }
        };
    }

    @GwtIncompatible
    private static CharMatcher precomputedPositive(int n, BitSet bitSet, String string) {
        switch (n) {
            case 0: {
                return CharMatcher.none();
            }
            case 1: {
                return CharMatcher.is((char)bitSet.nextSetBit(0));
            }
            case 2: {
                char c = (char)bitSet.nextSetBit(0);
                char c2 = (char)bitSet.nextSetBit(c + '\u0001');
                return CharMatcher.isEither(c, c2);
            }
        }
        return CharMatcher.isSmall(n, bitSet.length()) ? SmallCharMatcher.from(bitSet, string) : new BitSetMatcher(bitSet, string, null);
    }

    @GwtIncompatible
    private static boolean isSmall(int n, int n2) {
        return n <= 1023 && n2 > n * 4 * 16;
    }

    @GwtIncompatible
    void setBits(BitSet bitSet) {
        for (int i = 65535; i >= 0; --i) {
            if (!this.matches((char)i)) continue;
            bitSet.set(i);
        }
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return !this.matchesNoneOf(charSequence);
    }

    public boolean matchesAllOf(CharSequence charSequence) {
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            if (this.matches(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return this.indexIn(charSequence) == -1;
    }

    public int indexIn(CharSequence charSequence) {
        return this.indexIn(charSequence, 0);
    }

    public int indexIn(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        Preconditions.checkPositionIndex(n, n2);
        for (int i = n; i < n2; ++i) {
            if (!this.matches(charSequence.charAt(i))) continue;
            return i;
        }
        return 1;
    }

    public int lastIndexIn(CharSequence charSequence) {
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            if (!this.matches(charSequence.charAt(i))) continue;
            return i;
        }
        return 1;
    }

    public int countIn(CharSequence charSequence) {
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            if (!this.matches(charSequence.charAt(i))) continue;
            ++n;
        }
        return n;
    }

    public String removeFrom(CharSequence charSequence) {
        String string = charSequence.toString();
        int n = this.indexIn(string);
        if (n == -1) {
            return string;
        }
        char[] cArray = string.toCharArray();
        int n2 = 1;
        block0: while (true) {
            ++n;
            while (n != cArray.length) {
                if (!this.matches(cArray[n])) {
                    cArray[n - n2] = cArray[n];
                    ++n;
                    continue;
                }
                ++n2;
                continue block0;
            }
            break;
        }
        return new String(cArray, 0, n - n2);
    }

    public String retainFrom(CharSequence charSequence) {
        return this.negate().removeFrom(charSequence);
    }

    public String replaceFrom(CharSequence charSequence, char c) {
        String string = charSequence.toString();
        int n = this.indexIn(string);
        if (n == -1) {
            return string;
        }
        char[] cArray = string.toCharArray();
        cArray[n] = c;
        for (int i = n + 1; i < cArray.length; ++i) {
            if (!this.matches(cArray[i])) continue;
            cArray[i] = c;
        }
        return new String(cArray);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int n = charSequence2.length();
        if (n == 0) {
            return this.removeFrom(charSequence);
        }
        if (n == 1) {
            return this.replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String string = charSequence.toString();
        int n2 = this.indexIn(string);
        if (n2 == -1) {
            return string;
        }
        int n3 = string.length();
        StringBuilder stringBuilder = new StringBuilder(n3 * 3 / 2 + 16);
        int n4 = 0;
        do {
            stringBuilder.append(string, n4, n2);
            stringBuilder.append(charSequence2);
        } while ((n2 = this.indexIn(string, n4 = n2 + 1)) != -1);
        stringBuilder.append(string, n4, n3);
        return stringBuilder.toString();
    }

    public String trimFrom(CharSequence charSequence) {
        int n;
        int n2;
        int n3 = charSequence.length();
        for (n2 = 0; n2 < n3 && this.matches(charSequence.charAt(n2)); ++n2) {
        }
        for (n = n3 - 1; n > n2 && this.matches(charSequence.charAt(n)); --n) {
        }
        return charSequence.subSequence(n2, n + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (this.matches(charSequence.charAt(i))) continue;
            return charSequence.subSequence(i, n).toString();
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = n - 1; i >= 0; --i) {
            if (this.matches(charSequence.charAt(i))) continue;
            return charSequence.subSequence(0, i + 1).toString();
        }
        return "";
    }

    public String collapseFrom(CharSequence charSequence, char c) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            char c2 = charSequence.charAt(i);
            if (!this.matches(c2)) continue;
            if (!(c2 != c || i != n - 1 && this.matches(charSequence.charAt(i + 1)))) {
                ++i;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder(n).append(charSequence, 0, i).append(c);
            return this.finishCollapseFrom(charSequence, i + 1, n, c, stringBuilder, true);
        }
        return charSequence.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c) {
        int n;
        int n2 = charSequence.length();
        int n3 = n2 - 1;
        for (n = 0; n < n2 && this.matches(charSequence.charAt(n)); ++n) {
        }
        while (n3 > n && this.matches(charSequence.charAt(n3))) {
            --n3;
        }
        return n == 0 && n3 == n2 - 1 ? this.collapseFrom(charSequence, c) : this.finishCollapseFrom(charSequence, n, n3 + 1, c, new StringBuilder(n3 + 1 - n), false);
    }

    private String finishCollapseFrom(CharSequence charSequence, int n, int n2, char c, StringBuilder stringBuilder, boolean bl) {
        for (int i = n; i < n2; ++i) {
            char c2 = charSequence.charAt(i);
            if (this.matches(c2)) {
                if (bl) continue;
                stringBuilder.append(c);
                bl = true;
                continue;
            }
            stringBuilder.append(c2);
            bl = false;
        }
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean apply(Character c) {
        return this.matches(c.charValue());
    }

    public String toString() {
        return super.toString();
    }

    private static String showCharacter(char c) {
        String string = "0123456789ABCDEF";
        char[] cArray = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
        for (int i = 0; i < 4; ++i) {
            cArray[5 - i] = string.charAt(c & 0xF);
            c = (char)(c >> 4);
        }
        return String.copyValueOf(cArray);
    }

    private static IsEither isEither(char c, char c2) {
        return new IsEither(c, c2);
    }

    @Override
    @Deprecated
    public boolean apply(Object object) {
        return this.apply((Character)object);
    }

    @Override
    public java.util.function.Predicate negate() {
        return this.negate();
    }

    static String access$100(char c) {
        return CharMatcher.showCharacter(c);
    }

    private static final class ForPredicate
    extends CharMatcher {
        private final Predicate<? super Character> predicate;

        ForPredicate(Predicate<? super Character> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public boolean matches(char c) {
            return this.predicate.apply(Character.valueOf(c));
        }

        @Override
        public boolean apply(Character c) {
            return this.predicate.apply(Preconditions.checkNotNull(c));
        }

        @Override
        public String toString() {
            return "CharMatcher.forPredicate(" + this.predicate + ")";
        }

        @Override
        public boolean apply(Object object) {
            return this.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class InRange
    extends FastMatcher {
        private final char startInclusive;
        private final char endInclusive;

        InRange(char c, char c2) {
            Preconditions.checkArgument(c2 >= c);
            this.startInclusive = c;
            this.endInclusive = c2;
        }

        @Override
        public boolean matches(char c) {
            return this.startInclusive <= c && c <= this.endInclusive;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            bitSet.set((int)this.startInclusive, this.endInclusive + '\u0001');
        }

        @Override
        public String toString() {
            return "CharMatcher.inRange('" + CharMatcher.access$100(this.startInclusive) + "', '" + CharMatcher.access$100(this.endInclusive) + "')";
        }
    }

    private static final class AnyOf
    extends CharMatcher {
        private final char[] chars;

        public AnyOf(CharSequence charSequence) {
            this.chars = charSequence.toString().toCharArray();
            Arrays.sort(this.chars);
        }

        @Override
        public boolean matches(char c) {
            return Arrays.binarySearch(this.chars, c) >= 0;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            for (char c : this.chars) {
                bitSet.set(c);
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c : this.chars) {
                stringBuilder.append(CharMatcher.access$100(c));
            }
            stringBuilder.append("\")");
            return stringBuilder.toString();
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class IsEither
    extends FastMatcher {
        private final char match1;
        private final char match2;

        IsEither(char c, char c2) {
            this.match1 = c;
            this.match2 = c2;
        }

        @Override
        public boolean matches(char c) {
            return c == this.match1 || c == this.match2;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            bitSet.set(this.match1);
            bitSet.set(this.match2);
        }

        @Override
        public String toString() {
            return "CharMatcher.anyOf(\"" + CharMatcher.access$100(this.match1) + CharMatcher.access$100(this.match2) + "\")";
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class IsNot
    extends FastMatcher {
        private final char match;

        IsNot(char c) {
            this.match = c;
        }

        @Override
        public boolean matches(char c) {
            return c != this.match;
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? super.and(charMatcher) : charMatcher;
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? IsNot.any() : this;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            bitSet.set(0, this.match);
            bitSet.set(this.match + '\u0001', 65536);
        }

        @Override
        public CharMatcher negate() {
            return IsNot.is(this.match);
        }

        @Override
        public String toString() {
            return "CharMatcher.isNot('" + CharMatcher.access$100(this.match) + "')";
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Is
    extends FastMatcher {
        private final char match;

        Is(char c) {
            this.match = c;
        }

        @Override
        public boolean matches(char c) {
            return c == this.match;
        }

        @Override
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString().replace(this.match, c);
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? this : Is.none();
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? charMatcher : super.or(charMatcher);
        }

        @Override
        public CharMatcher negate() {
            return Is.isNot(this.match);
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            bitSet.set(this.match);
        }

        @Override
        public String toString() {
            return "CharMatcher.is('" + CharMatcher.access$100(this.match) + "')";
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }

    private static final class Or
    extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = Preconditions.checkNotNull(charMatcher);
            this.second = Preconditions.checkNotNull(charMatcher2);
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            this.first.setBits(bitSet);
            this.second.setBits(bitSet);
        }

        @Override
        public boolean matches(char c) {
            return this.first.matches(c) || this.second.matches(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.or(" + this.first + ", " + this.second + ")";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class And
    extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        And(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = Preconditions.checkNotNull(charMatcher);
            this.second = Preconditions.checkNotNull(charMatcher2);
        }

        @Override
        public boolean matches(char c) {
            return this.first.matches(c) && this.second.matches(c);
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.first.setBits(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.second.setBits(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        @Override
        public String toString() {
            return "CharMatcher.and(" + this.first + ", " + this.second + ")";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class Negated
    extends CharMatcher {
        final CharMatcher original;

        Negated(CharMatcher charMatcher) {
            this.original = Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public boolean matches(char c) {
            return !this.original.matches(c);
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            return this.original.matchesNoneOf(charSequence);
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.original.matchesAllOf(charSequence);
        }

        @Override
        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.original.countIn(charSequence);
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.original.setBits(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        @Override
        public CharMatcher negate() {
            return this.original;
        }

        @Override
        public String toString() {
            return this.original + ".negate()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }

    private static final class SingleWidth
    extends RangesMatcher {
        static final SingleWidth INSTANCE = new SingleWidth();

        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
        }
    }

    private static final class Invisible
    extends RangesMatcher {
        private static final String RANGE_STARTS = "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u2066\u2067\u2068\u2069\u206a\u3000\ud800\ufeff\ufff9\ufffa";
        private static final String RANGE_ENDS = " \u00a0\u00ad\u0604\u061c\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u2066\u2067\u2068\u2069\u206f\u3000\uf8ff\ufeff\ufff9\ufffb";
        static final Invisible INSTANCE = new Invisible();

        private Invisible() {
            super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
        }
    }

    private static final class JavaIsoControl
    extends NamedFastMatcher {
        static final JavaIsoControl INSTANCE = new JavaIsoControl();

        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }

        @Override
        public boolean matches(char c) {
            return c <= '\u001f' || c >= '\u007f' && c <= '\u009f';
        }
    }

    private static final class JavaLowerCase
    extends CharMatcher {
        static final JavaLowerCase INSTANCE = new JavaLowerCase();

        private JavaLowerCase() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class JavaUpperCase
    extends CharMatcher {
        static final JavaUpperCase INSTANCE = new JavaUpperCase();

        private JavaUpperCase() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class JavaLetterOrDigit
    extends CharMatcher {
        static final JavaLetterOrDigit INSTANCE = new JavaLetterOrDigit();

        private JavaLetterOrDigit() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class JavaLetter
    extends CharMatcher {
        static final JavaLetter INSTANCE = new JavaLetter();

        private JavaLetter() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLetter(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetter()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class JavaDigit
    extends CharMatcher {
        static final JavaDigit INSTANCE = new JavaDigit();

        private JavaDigit() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaDigit()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class Digit
    extends RangesMatcher {
        private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
        static final Digit INSTANCE = new Digit();

        private static char[] zeroes() {
            return ZEROES.toCharArray();
        }

        private static char[] nines() {
            char[] cArray = new char[31];
            for (int i = 0; i < 31; ++i) {
                cArray[i] = (char)(ZEROES.charAt(i) + 9);
            }
            return cArray;
        }

        private Digit() {
            super("CharMatcher.digit()", Digit.zeroes(), Digit.nines());
        }
    }

    private static class RangesMatcher
    extends CharMatcher {
        private final String description;
        private final char[] rangeStarts;
        private final char[] rangeEnds;

        RangesMatcher(String string, char[] cArray, char[] cArray2) {
            this.description = string;
            this.rangeStarts = cArray;
            this.rangeEnds = cArray2;
            Preconditions.checkArgument(cArray.length == cArray2.length);
            for (int i = 0; i < cArray.length; ++i) {
                Preconditions.checkArgument(cArray[i] <= cArray2[i]);
                if (i + 1 >= cArray.length) continue;
                Preconditions.checkArgument(cArray2[i] < cArray[i + 1]);
            }
        }

        @Override
        public boolean matches(char c) {
            int n = Arrays.binarySearch(this.rangeStarts, c);
            if (n >= 0) {
                return false;
            }
            return (n = ~n - 1) >= 0 && c <= this.rangeEnds[n];
        }

        @Override
        public String toString() {
            return this.description;
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    private static final class Ascii
    extends NamedFastMatcher {
        static final Ascii INSTANCE = new Ascii();

        Ascii() {
            super("CharMatcher.ascii()");
        }

        @Override
        public boolean matches(char c) {
            return c <= '\u007f';
        }
    }

    private static final class BreakingWhitespace
    extends CharMatcher {
        static final CharMatcher INSTANCE = new BreakingWhitespace();

        private BreakingWhitespace() {
        }

        @Override
        public boolean matches(char c) {
            switch (c) {
                case '\t': 
                case '\n': 
                case '\u000b': 
                case '\f': 
                case '\r': 
                case ' ': 
                case '\u0085': 
                case '\u1680': 
                case '\u2028': 
                case '\u2029': 
                case '\u205f': 
                case '\u3000': {
                    return false;
                }
                case '\u2007': {
                    return true;
                }
            }
            return c >= '\u2000' && c <= '\u200a';
        }

        @Override
        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return super.negate();
        }
    }

    @VisibleForTesting
    static final class Whitespace
    extends NamedFastMatcher {
        static final String TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f\u00a0\f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
        static final int MULTIPLIER = 1682554634;
        static final int SHIFT = Integer.numberOfLeadingZeros(31);
        static final Whitespace INSTANCE = new Whitespace();

        Whitespace() {
            super("CharMatcher.whitespace()");
        }

        @Override
        public boolean matches(char c) {
            return TABLE.charAt(1682554634 * c >>> SHIFT) == c;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet bitSet) {
            for (int i = 0; i < 32; ++i) {
                bitSet.set(TABLE.charAt(i));
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class None
    extends NamedFastMatcher {
        static final None INSTANCE = new None();

        private None() {
            super("CharMatcher.none()");
        }

        @Override
        public boolean matches(char c) {
            return true;
        }

        @Override
        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 1;
        }

        @Override
        public int indexIn(CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            Preconditions.checkPositionIndex(n, n2);
            return 1;
        }

        @Override
        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 1;
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return false;
        }

        @Override
        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }

        @Override
        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override
        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 1;
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            return Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public CharMatcher negate() {
            return None.any();
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Any
    extends NamedFastMatcher {
        static final Any INSTANCE = new Any();

        private Any() {
            super("CharMatcher.any()");
        }

        @Override
        public boolean matches(char c) {
            return false;
        }

        @Override
        public int indexIn(CharSequence charSequence) {
            return charSequence.length() == 0 ? -1 : 0;
        }

        @Override
        public int indexIn(CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            Preconditions.checkPositionIndex(n, n2);
            return n == n2 ? -1 : n;
        }

        @Override
        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return false;
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override
        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override
        public String replaceFrom(CharSequence charSequence, char c) {
            char[] cArray = new char[charSequence.length()];
            Arrays.fill(cArray, c);
            return new String(cArray);
        }

        @Override
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder stringBuilder = new StringBuilder(charSequence.length() * charSequence2.length());
            for (int i = 0; i < charSequence.length(); ++i) {
                stringBuilder.append(charSequence2);
            }
            return stringBuilder.toString();
        }

        @Override
        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.length() == 0 ? "" : String.valueOf(c);
        }

        @Override
        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override
        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            return Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override
        public CharMatcher negate() {
            return Any.none();
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }

    @GwtIncompatible
    private static final class BitSetMatcher
    extends NamedFastMatcher {
        private final BitSet table;

        private BitSetMatcher(BitSet bitSet, String string) {
            super(string);
            if (bitSet.length() + 64 < bitSet.size()) {
                bitSet = (BitSet)bitSet.clone();
            }
            this.table = bitSet;
        }

        @Override
        public boolean matches(char c) {
            return this.table.get(c);
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }

        BitSetMatcher(BitSet bitSet, String string, 1 var3_3) {
            this(bitSet, string);
        }
    }

    static class NegatedFastMatcher
    extends Negated {
        NegatedFastMatcher(CharMatcher charMatcher) {
            super(charMatcher);
        }

        @Override
        public final CharMatcher precomputed() {
            return this;
        }
    }

    static abstract class NamedFastMatcher
    extends FastMatcher {
        private final String description;

        NamedFastMatcher(String string) {
            this.description = Preconditions.checkNotNull(string);
        }

        @Override
        public final String toString() {
            return this.description;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static abstract class FastMatcher
    extends CharMatcher {
        FastMatcher() {
        }

        @Override
        public final CharMatcher precomputed() {
            return this;
        }

        @Override
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }

        @Override
        @Deprecated
        public boolean apply(Object object) {
            return super.apply((Character)object);
        }

        @Override
        public java.util.function.Predicate negate() {
            return this.negate();
        }
    }
}

