/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.BMPSet;
import com.ibm.icu.impl.CharacterPropertiesImpl;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.RuleCharacterIterator;
import com.ibm.icu.impl.SortedSetRelation;
import com.ibm.icu.impl.StringRange;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.impl.UPropertyAliases;
import com.ibm.icu.impl.UnicodeSetStringSpan;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.lang.CharacterProperties;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.OutputInt;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UnicodeSet
extends UnicodeFilter
implements Iterable<String>,
Comparable<UnicodeSet>,
Freezable<UnicodeSet> {
    private static final SortedSet<String> EMPTY_STRINGS;
    public static final UnicodeSet EMPTY;
    public static final UnicodeSet ALL_CODE_POINTS;
    private static XSymbolTable XSYMBOL_TABLE;
    private static final int LOW = 0;
    private static final int HIGH = 0x110000;
    private static final int INITIAL_CAPACITY = 25;
    private static final int MAX_LENGTH = 0x110001;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 0x10FFFF;
    private int len;
    private int[] list;
    private int[] rangeList;
    private int[] buffer;
    SortedSet<String> strings = EMPTY_STRINGS;
    private String pat = null;
    private static final String ANY_ID = "ANY";
    private static final String ASCII_ID = "ASCII";
    private static final String ASSIGNED = "Assigned";
    private volatile BMPSet bmpSet;
    private volatile UnicodeSetStringSpan stringSpan;
    private static final int LAST0_START = 0;
    private static final int LAST1_RANGE = 1;
    private static final int LAST2_SET = 2;
    private static final int MODE0_NONE = 0;
    private static final int MODE1_INBRACKET = 1;
    private static final int MODE2_OUTBRACKET = 2;
    private static final int SETMODE0_NONE = 0;
    private static final int SETMODE1_UNICODESET = 1;
    private static final int SETMODE2_PROPERTYPAT = 2;
    private static final int SETMODE3_PREPARSED = 3;
    private static final int MAX_DEPTH = 100;
    private static final VersionInfo NO_VERSION;
    public static final int IGNORE_SPACE = 1;
    public static final int CASE = 2;
    public static final int CASE_INSENSITIVE = 2;
    public static final int ADD_CASE_MAPPINGS = 4;
    static final boolean $assertionsDisabled;

    public UnicodeSet() {
        this.list = new int[25];
        this.list[0] = 0x110000;
        this.len = 1;
    }

    public UnicodeSet(UnicodeSet unicodeSet) {
        this.set(unicodeSet);
    }

    public UnicodeSet(int n, int n2) {
        this();
        this.add(n, n2);
    }

    public UnicodeSet(int ... nArray) {
        if ((nArray.length & 1) != 0) {
            throw new IllegalArgumentException("Must have even number of integers");
        }
        this.list = new int[nArray.length + 1];
        this.len = this.list.length;
        int n = -1;
        int n2 = 0;
        while (n2 < nArray.length) {
            int n3 = nArray[n2];
            if (n >= n3) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            this.list[n2++] = n3;
            int n4 = nArray[n2] + 1;
            if (n3 >= n4) {
                throw new IllegalArgumentException("Must be monotonically increasing.");
            }
            this.list[n2++] = n = n4;
        }
        this.list[n2] = 0x110000;
    }

    public UnicodeSet(String string) {
        this();
        this.applyPattern(string, null, null, 1);
    }

    public UnicodeSet(String string, boolean bl) {
        this();
        this.applyPattern(string, null, null, bl ? 1 : 0);
    }

    public UnicodeSet(String string, int n) {
        this();
        this.applyPattern(string, null, null, n);
    }

    public UnicodeSet(String string, ParsePosition parsePosition, SymbolTable symbolTable) {
        this();
        this.applyPattern(string, parsePosition, symbolTable, 1);
    }

    public UnicodeSet(String string, ParsePosition parsePosition, SymbolTable symbolTable, int n) {
        this();
        this.applyPattern(string, parsePosition, symbolTable, n);
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return new UnicodeSet(this);
    }

    public UnicodeSet set(int n, int n2) {
        this.checkFrozen();
        this.clear();
        this.complement(n, n2);
        return this;
    }

    public UnicodeSet set(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.list = Arrays.copyOf(unicodeSet.list, unicodeSet.len);
        this.len = unicodeSet.len;
        this.pat = unicodeSet.pat;
        this.strings = unicodeSet.hasStrings() ? new TreeSet<String>(unicodeSet.strings) : EMPTY_STRINGS;
        return this;
    }

    public final UnicodeSet applyPattern(String string) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, 1);
    }

    public UnicodeSet applyPattern(String string, boolean bl) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, bl ? 1 : 0);
    }

    public UnicodeSet applyPattern(String string, int n) {
        this.checkFrozen();
        return this.applyPattern(string, null, null, n);
    }

    public static boolean resemblesPattern(String string, int n) {
        return n + 1 < string.length() && string.charAt(n) == '[' || UnicodeSet.resemblesPropertyPattern(string, n);
    }

    private static void appendCodePoint(Appendable appendable, int n) {
        if (!($assertionsDisabled || 0 <= n && n <= 0x10FFFF)) {
            throw new AssertionError();
        }
        try {
            if (n <= 65535) {
                appendable.append((char)n);
            } else {
                appendable.append(UTF16.getLeadSurrogate(n)).append(UTF16.getTrailSurrogate(n));
            }
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private static void append(Appendable appendable, CharSequence charSequence) {
        try {
            appendable.append(charSequence);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private static <T extends Appendable> T _appendToPat(T t, String string, boolean bl) {
        int n;
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = string.codePointAt(i);
            UnicodeSet._appendToPat(t, n, bl);
        }
        return t;
    }

    private static <T extends Appendable> T _appendToPat(T t, int n, boolean bl) {
        try {
            if (bl && Utility.isUnprintable(n) && Utility.escapeUnprintable(t, n)) {
                return t;
            }
            switch (n) {
                case 36: 
                case 38: 
                case 45: 
                case 58: 
                case 91: 
                case 92: 
                case 93: 
                case 94: 
                case 123: 
                case 125: {
                    t.append('\\');
                    break;
                }
                default: {
                    if (!PatternProps.isWhiteSpace(n)) break;
                    t.append('\\');
                }
            }
            UnicodeSet.appendCodePoint(t, n);
            return t;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    @Override
    public String toPattern(boolean bl) {
        if (this.pat != null && !bl) {
            return this.pat;
        }
        StringBuilder stringBuilder = new StringBuilder();
        return this._toPattern(stringBuilder, bl).toString();
    }

    private <T extends Appendable> T _toPattern(T t, boolean bl) {
        if (this.pat == null) {
            return this.appendNewPattern(t, bl, true);
        }
        try {
            if (!bl) {
                t.append(this.pat);
                return t;
            }
            boolean bl2 = false;
            int n = 0;
            while (n < this.pat.length()) {
                int n2 = this.pat.codePointAt(n);
                n += Character.charCount(n2);
                if (Utility.isUnprintable(n2)) {
                    Utility.escapeUnprintable(t, n2);
                    bl2 = false;
                    continue;
                }
                if (!bl2 && n2 == 92) {
                    bl2 = true;
                    continue;
                }
                if (bl2) {
                    t.append('\\');
                }
                UnicodeSet.appendCodePoint(t, n2);
                bl2 = false;
            }
            if (bl2) {
                t.append('\\');
            }
            return t;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean bl) {
        return this._generatePattern(stringBuffer, bl, false);
    }

    public StringBuffer _generatePattern(StringBuffer stringBuffer, boolean bl, boolean bl2) {
        return this.appendNewPattern(stringBuffer, bl, bl2);
    }

    private <T extends Appendable> T appendNewPattern(T t, boolean bl, boolean bl2) {
        try {
            int n;
            int n2;
            int n3;
            t.append('[');
            int n4 = this.getRangeCount();
            if (n4 > 1 && this.getRangeStart(0) == 0 && this.getRangeEnd(n4 - 1) == 0x10FFFF) {
                t.append('^');
                for (n3 = 1; n3 < n4; ++n3) {
                    n2 = this.getRangeEnd(n3 - 1) + 1;
                    n = this.getRangeStart(n3) - 1;
                    UnicodeSet._appendToPat(t, n2, bl);
                    if (n2 == n) continue;
                    if (n2 + 1 != n) {
                        t.append('-');
                    }
                    UnicodeSet._appendToPat(t, n, bl);
                }
            } else {
                for (n3 = 0; n3 < n4; ++n3) {
                    n2 = this.getRangeStart(n3);
                    n = this.getRangeEnd(n3);
                    UnicodeSet._appendToPat(t, n2, bl);
                    if (n2 == n) continue;
                    if (n2 + 1 != n) {
                        t.append('-');
                    }
                    UnicodeSet._appendToPat(t, n, bl);
                }
            }
            if (bl2 && this.hasStrings()) {
                for (String string : this.strings) {
                    t.append('{');
                    UnicodeSet._appendToPat(t, string, bl);
                    t.append('}');
                }
            }
            t.append(']');
            return t;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    boolean hasStrings() {
        return !this.strings.isEmpty();
    }

    public int size() {
        int n = 0;
        int n2 = this.getRangeCount();
        for (int i = 0; i < n2; ++i) {
            n += this.getRangeEnd(i) - this.getRangeStart(i) + 1;
        }
        return n + this.strings.size();
    }

    public boolean isEmpty() {
        return this.len == 1 && !this.hasStrings();
    }

    @Override
    public boolean matchesIndexValue(int n) {
        int n2;
        for (int i = 0; i < this.getRangeCount(); ++i) {
            int n3 = this.getRangeStart(i);
            if (!((n3 & 0xFFFFFF00) == ((n2 = this.getRangeEnd(i)) & 0xFFFFFF00) ? (n3 & 0xFF) <= n && n <= (n2 & 0xFF) : (n3 & 0xFF) <= n || n <= (n2 & 0xFF))) continue;
            return false;
        }
        if (this.hasStrings()) {
            for (String string : this.strings) {
                n2 = UTF16.charAt(string, 0);
                if ((n2 & 0xFF) != n) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public int matches(Replaceable replaceable, int[] nArray, int n, boolean bl) {
        if (nArray[0] == n) {
            if (this.contains(0)) {
                return bl ? 1 : 2;
            }
            return 1;
        }
        if (this.hasStrings()) {
            boolean bl2 = nArray[0] < n;
            char c = replaceable.charAt(nArray[0]);
            int n2 = 0;
            for (String string : this.strings) {
                char c2 = string.charAt(bl2 ? 0 : string.length() - 1);
                if (bl2 && c2 > c) break;
                if (c2 != c) continue;
                int n3 = UnicodeSet.matchRest(replaceable, nArray[0], n, string);
                if (bl) {
                    int n4;
                    int n5 = n4 = bl2 ? n - nArray[0] : nArray[0] - n;
                    if (n3 == n4) {
                        return 0;
                    }
                }
                if (n3 != string.length()) continue;
                if (n3 > n2) {
                    n2 = n3;
                }
                if (!bl2 || n3 >= n2) continue;
                break;
            }
            if (n2 != 0) {
                nArray[0] = nArray[0] + (bl2 ? n2 : -n2);
                return 1;
            }
        }
        return super.matches(replaceable, nArray, n, bl);
    }

    private static int matchRest(Replaceable replaceable, int n, int n2, String string) {
        int n3;
        int n4 = string.length();
        if (n < n2) {
            n3 = n2 - n;
            if (n3 > n4) {
                n3 = n4;
            }
            for (int i = 1; i < n3; ++i) {
                if (replaceable.charAt(n + i) == string.charAt(i)) continue;
                return 1;
            }
        } else {
            n3 = n - n2;
            if (n3 > n4) {
                n3 = n4;
            }
            --n4;
            for (int i = 1; i < n3; ++i) {
                if (replaceable.charAt(n - i) == string.charAt(n4 - i)) continue;
                return 1;
            }
        }
        return n3;
    }

    @Deprecated
    public int matchesAt(CharSequence charSequence, int n) {
        int n2;
        int n3;
        block4: {
            n3 = -1;
            if (this.hasStrings()) {
                int n4;
                n2 = charSequence.charAt(n);
                String string = null;
                Iterator iterator2 = this.strings.iterator();
                while (iterator2.hasNext()) {
                    string = (String)iterator2.next();
                    n4 = string.charAt(0);
                    if (n4 < n2 || n4 <= n2) continue;
                    break block4;
                }
                while (n3 <= (n4 = UnicodeSet.matchesAt(charSequence, n, string))) {
                    n3 = n4;
                    if (!iterator2.hasNext()) break;
                    string = (String)iterator2.next();
                }
            }
        }
        if (n3 < 2 && this.contains(n2 = UTF16.charAt(charSequence, n))) {
            n3 = UTF16.getCharCount(n2);
        }
        return n + n3;
    }

    private static int matchesAt(CharSequence charSequence, int n, CharSequence charSequence2) {
        int n2 = charSequence2.length();
        int n3 = charSequence.length();
        if (n3 + n > n2) {
            return 1;
        }
        int n4 = 0;
        int n5 = n;
        while (n4 < n2) {
            char c;
            char c2 = charSequence2.charAt(n4);
            if (c2 != (c = charSequence.charAt(n5))) {
                return 1;
            }
            ++n4;
            ++n5;
        }
        return n4;
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        unicodeSet.addAll(this);
    }

    public int indexOf(int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        int n2 = 0;
        int n3 = 0;
        int n4;
        while (n >= (n4 = this.list[n2++])) {
            int n5;
            if (n < (n5 = this.list[n2++])) {
                return n3 + n - n4;
            }
            n3 += n5 - n4;
        }
        return 1;
    }

    public int charAt(int n) {
        if (n >= 0) {
            int n2 = this.len & 0xFFFFFFFE;
            int n3 = 0;
            while (n3 < n2) {
                int n4;
                int n5;
                if (n < (n5 = this.list[n3++] - (n4 = this.list[n3++]))) {
                    return n4 + n;
                }
                n -= n5;
            }
        }
        return 1;
    }

    public UnicodeSet add(int n, int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }

    public UnicodeSet addAll(int n, int n2) {
        this.checkFrozen();
        return this.add_unchecked(n, n2);
    }

    private UnicodeSet add_unchecked(int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n < n2) {
            int n3 = n2 + 1;
            if ((this.len & 1) != 0) {
                int n4;
                int n5 = n4 = this.len == 1 ? -2 : this.list[this.len - 2];
                if (n4 <= n) {
                    this.checkFrozen();
                    if (n4 == n) {
                        this.list[this.len - 2] = n3;
                        if (n3 == 0x110000) {
                            --this.len;
                        }
                    } else {
                        this.list[this.len - 1] = n;
                        if (n3 < 0x110000) {
                            this.ensureCapacity(this.len + 2);
                            this.list[this.len++] = n3;
                            this.list[this.len++] = 0x110000;
                        } else {
                            this.ensureCapacity(this.len + 1);
                            this.list[this.len++] = 0x110000;
                        }
                    }
                    this.pat = null;
                    return this;
                }
            }
            this.add(this.range(n, n2), 2, 0);
        } else if (n == n2) {
            this.add(n);
        }
        return this;
    }

    public final UnicodeSet add(int n) {
        this.checkFrozen();
        return this.add_unchecked(n);
    }

    private final UnicodeSet add_unchecked(int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        int n2 = this.findCodePoint(n);
        if ((n2 & 1) != 0) {
            return this;
        }
        if (n == this.list[n2] - 1) {
            this.list[n2] = n;
            if (n == 0x10FFFF) {
                this.ensureCapacity(this.len + 1);
                this.list[this.len++] = 0x110000;
            }
            if (n2 > 0 && n == this.list[n2 - 1]) {
                System.arraycopy(this.list, n2 + 1, this.list, n2 - 1, this.len - n2 - 1);
                this.len -= 2;
            }
        } else if (n2 > 0 && n == this.list[n2 - 1]) {
            int n3 = n2 - 1;
            this.list[n3] = this.list[n3] + 1;
        } else {
            if (this.len + 2 > this.list.length) {
                int[] nArray = new int[this.nextCapacity(this.len + 2)];
                if (n2 != 0) {
                    System.arraycopy(this.list, 0, nArray, 0, n2);
                }
                System.arraycopy(this.list, n2, nArray, n2 + 2, this.len - n2);
                this.list = nArray;
            } else {
                System.arraycopy(this.list, n2, this.list, n2 + 2, this.len - n2);
            }
            this.list[n2] = n;
            this.list[n2 + 1] = n + 1;
            this.len += 2;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet add(CharSequence charSequence) {
        this.checkFrozen();
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            String string = charSequence.toString();
            if (!this.strings.contains(string)) {
                this.addString(string);
                this.pat = null;
            }
        } else {
            this.add_unchecked(n, n);
        }
        return this;
    }

    private void addString(CharSequence charSequence) {
        if (this.strings == EMPTY_STRINGS) {
            this.strings = new TreeSet<String>();
        }
        this.strings.add(charSequence.toString());
    }

    private static int getSingleCP(CharSequence charSequence) {
        if (charSequence.length() < 1) {
            throw new IllegalArgumentException("Can't use zero-length strings in UnicodeSet");
        }
        if (charSequence.length() > 2) {
            return 1;
        }
        if (charSequence.length() == 1) {
            return charSequence.charAt(0);
        }
        int n = UTF16.charAt(charSequence, 0);
        if (n > 65535) {
            return n;
        }
        return 1;
    }

    public final UnicodeSet addAll(CharSequence charSequence) {
        int n;
        this.checkFrozen();
        for (int i = 0; i < charSequence.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(charSequence, i);
            this.add_unchecked(n, n);
        }
        return this;
    }

    public final UnicodeSet retainAll(CharSequence charSequence) {
        return this.retainAll(UnicodeSet.fromAll(charSequence));
    }

    public final UnicodeSet complementAll(CharSequence charSequence) {
        return this.complementAll(UnicodeSet.fromAll(charSequence));
    }

    public final UnicodeSet removeAll(CharSequence charSequence) {
        return this.removeAll(UnicodeSet.fromAll(charSequence));
    }

    public final UnicodeSet removeAllStrings() {
        this.checkFrozen();
        if (this.hasStrings()) {
            this.strings.clear();
            this.pat = null;
        }
        return this;
    }

    public static UnicodeSet from(CharSequence charSequence) {
        return new UnicodeSet().add(charSequence);
    }

    public static UnicodeSet fromAll(CharSequence charSequence) {
        return new UnicodeSet().addAll(charSequence);
    }

    public UnicodeSet retain(int n, int n2) {
        this.checkFrozen();
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.retain(this.range(n, n2), 2, 0);
        } else {
            this.clear();
        }
        return this;
    }

    public final UnicodeSet retain(int n) {
        return this.retain(n, n);
    }

    public final UnicodeSet retain(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            this.checkFrozen();
            String string = charSequence.toString();
            boolean bl = this.strings.contains(string);
            if (bl && this.size() == 1) {
                return this;
            }
            this.clear();
            this.addString(string);
            this.pat = null;
        } else {
            this.retain(n, n);
        }
        return this;
    }

    public UnicodeSet remove(int n, int n2) {
        this.checkFrozen();
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.retain(this.range(n, n2), 2, 2);
        }
        return this;
    }

    public final UnicodeSet remove(int n) {
        return this.remove(n, n);
    }

    public final UnicodeSet remove(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            this.checkFrozen();
            String string = charSequence.toString();
            if (this.strings.contains(string)) {
                this.strings.remove(string);
                this.pat = null;
            }
        } else {
            this.remove(n, n);
        }
        return this;
    }

    public UnicodeSet complement(int n, int n2) {
        this.checkFrozen();
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        if (n <= n2) {
            this.xor(this.range(n, n2), 2, 0);
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(int n) {
        return this.complement(n, n);
    }

    public UnicodeSet complement() {
        this.checkFrozen();
        if (this.list[0] == 0) {
            System.arraycopy(this.list, 1, this.list, 0, this.len - 1);
            --this.len;
        } else {
            this.ensureCapacity(this.len + 1);
            System.arraycopy(this.list, 0, this.list, 1, this.len);
            this.list[0] = 0;
            ++this.len;
        }
        this.pat = null;
        return this;
    }

    public final UnicodeSet complement(CharSequence charSequence) {
        this.checkFrozen();
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            String string = charSequence.toString();
            if (this.strings.contains(string)) {
                this.strings.remove(string);
            } else {
                this.addString(string);
            }
            this.pat = null;
        } else {
            this.complement(n, n);
        }
        return this;
    }

    @Override
    public boolean contains(int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (this.bmpSet != null) {
            return this.bmpSet.contains(n);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.contains(n);
        }
        int n2 = this.findCodePoint(n);
        return (n2 & 1) != 0;
    }

    private final int findCodePoint(int n) {
        if (n < this.list[0]) {
            return 1;
        }
        if (this.len >= 2 && n >= this.list[this.len - 2]) {
            return this.len - 1;
        }
        int n2 = 0;
        int n3 = this.len - 1;
        int n4;
        while ((n4 = n2 + n3 >>> 1) != n2) {
            if (n < this.list[n4]) {
                n3 = n4;
                continue;
            }
            n2 = n4;
        }
        return n3;
    }

    public boolean contains(int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        int n3 = this.findCodePoint(n);
        return (n3 & 1) != 0 && n2 < this.list[n3];
    }

    public final boolean contains(CharSequence charSequence) {
        int n = UnicodeSet.getSingleCP(charSequence);
        if (n < 0) {
            return this.strings.contains(charSequence.toString());
        }
        return this.contains(n);
    }

    public boolean containsAll(UnicodeSet unicodeSet) {
        block6: {
            int[] nArray = unicodeSet.list;
            boolean bl = true;
            boolean bl2 = true;
            int n = 0;
            int n2 = 0;
            int n3 = this.len - 1;
            int n4 = unicodeSet.len - 1;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            while (true) {
                if (bl) {
                    if (n >= n3) {
                        if (!bl2 || n2 < n4) {
                            return true;
                        }
                        break block6;
                    }
                    n5 = this.list[n++];
                    n7 = this.list[n++];
                }
                if (bl2) {
                    if (n2 >= n4) break block6;
                    n6 = nArray[n2++];
                    n8 = nArray[n2++];
                }
                if (n6 >= n7) {
                    bl = true;
                    bl2 = false;
                    continue;
                }
                if (n6 < n5 || n8 > n7) break;
                bl = false;
                bl2 = true;
            }
            return true;
        }
        return !this.strings.containsAll(unicodeSet.strings);
    }

    public boolean containsAll(String string) {
        int n;
        for (int i = 0; i < string.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(string, i);
            if (this.contains(n)) continue;
            if (!this.hasStrings()) {
                return true;
            }
            return this.containsAll(string, 0);
        }
        return false;
    }

    private boolean containsAll(String string, int n) {
        if (n >= string.length()) {
            return false;
        }
        int n2 = UTF16.charAt(string, n);
        if (this.contains(n2) && this.containsAll(string, n + UTF16.getCharCount(n2))) {
            return false;
        }
        for (String string2 : this.strings) {
            if (!string.startsWith(string2, n) || !this.containsAll(string, n + string2.length())) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    public String getRegexEquivalent() {
        if (!this.hasStrings()) {
            return this.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("(?:");
        this.appendNewPattern(stringBuilder, true, false);
        for (String string : this.strings) {
            stringBuilder.append('|');
            UnicodeSet._appendToPat(stringBuilder, string, true);
        }
        return stringBuilder.append(")").toString();
    }

    public boolean containsNone(int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n, 6));
        }
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point U+" + Utility.hex(n2, 6));
        }
        int n3 = -1;
        while (n >= this.list[++n3]) {
        }
        return (n3 & 1) == 0 && n2 < this.list[n3];
    }

    public boolean containsNone(UnicodeSet unicodeSet) {
        block4: {
            int[] nArray = unicodeSet.list;
            boolean bl = true;
            boolean bl2 = true;
            int n = 0;
            int n2 = 0;
            int n3 = this.len - 1;
            int n4 = unicodeSet.len - 1;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            while (true) {
                if (bl) {
                    if (n >= n3) break block4;
                    n5 = this.list[n++];
                    n7 = this.list[n++];
                }
                if (bl2) {
                    if (n2 >= n4) break block4;
                    n6 = nArray[n2++];
                    n8 = nArray[n2++];
                }
                if (n6 >= n7) {
                    bl = true;
                    bl2 = false;
                    continue;
                }
                if (n5 < n8) break;
                bl = false;
                bl2 = true;
            }
            return true;
        }
        return !SortedSetRelation.hasRelation(this.strings, 5, unicodeSet.strings);
    }

    public boolean containsNone(CharSequence charSequence) {
        return this.span(charSequence, SpanCondition.NOT_CONTAINED) == charSequence.length();
    }

    public final boolean containsSome(int n, int n2) {
        return !this.containsNone(n, n2);
    }

    public final boolean containsSome(UnicodeSet unicodeSet) {
        return !this.containsNone(unicodeSet);
    }

    public final boolean containsSome(CharSequence charSequence) {
        return !this.containsNone(charSequence);
    }

    public UnicodeSet addAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.add(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            if (this.strings == EMPTY_STRINGS) {
                this.strings = new TreeSet<String>(unicodeSet.strings);
            } else {
                this.strings.addAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet retainAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.retain(unicodeSet.list, unicodeSet.len, 0);
        if (this.hasStrings()) {
            if (!unicodeSet.hasStrings()) {
                this.strings.clear();
            } else {
                this.strings.retainAll(unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet removeAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.retain(unicodeSet.list, unicodeSet.len, 2);
        if (this.hasStrings() && unicodeSet.hasStrings()) {
            this.strings.removeAll(unicodeSet.strings);
        }
        return this;
    }

    public UnicodeSet complementAll(UnicodeSet unicodeSet) {
        this.checkFrozen();
        this.xor(unicodeSet.list, unicodeSet.len, 0);
        if (unicodeSet.hasStrings()) {
            if (this.strings == EMPTY_STRINGS) {
                this.strings = new TreeSet<String>(unicodeSet.strings);
            } else {
                SortedSetRelation.doOperation(this.strings, 5, unicodeSet.strings);
            }
        }
        return this;
    }

    public UnicodeSet clear() {
        this.checkFrozen();
        this.list[0] = 0x110000;
        this.len = 1;
        this.pat = null;
        if (this.hasStrings()) {
            this.strings.clear();
        }
        return this;
    }

    public int getRangeCount() {
        return this.len / 2;
    }

    public int getRangeStart(int n) {
        return this.list[n * 2];
    }

    public int getRangeEnd(int n) {
        return this.list[n * 2 + 1] - 1;
    }

    public UnicodeSet compact() {
        this.checkFrozen();
        if (this.len + 7 < this.list.length) {
            this.list = Arrays.copyOf(this.list, this.len);
        }
        this.rangeList = null;
        this.buffer = null;
        if (this.strings != EMPTY_STRINGS && this.strings.isEmpty()) {
            this.strings = EMPTY_STRINGS;
        }
        return this;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        try {
            UnicodeSet unicodeSet = (UnicodeSet)object;
            if (this.len != unicodeSet.len) {
                return false;
            }
            for (int i = 0; i < this.len; ++i) {
                if (this.list[i] == unicodeSet.list[i]) continue;
                return false;
            }
            if (!this.strings.equals(unicodeSet.strings)) {
                return false;
            }
        } catch (Exception exception) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n = this.len;
        for (int i = 0; i < this.len; ++i) {
            n *= 1000003;
            n += this.list[i];
        }
        return n;
    }

    public String toString() {
        return this.toPattern(false);
    }

    @Deprecated
    public UnicodeSet applyPattern(String string, ParsePosition parsePosition, SymbolTable symbolTable, int n) {
        boolean bl;
        boolean bl2 = bl = parsePosition == null;
        if (bl) {
            parsePosition = new ParsePosition(0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        RuleCharacterIterator ruleCharacterIterator = new RuleCharacterIterator(string, symbolTable, parsePosition);
        this.applyPattern(ruleCharacterIterator, symbolTable, stringBuilder, n, 0);
        if (ruleCharacterIterator.inVariable()) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Extra chars in variable value");
        }
        this.pat = stringBuilder.toString();
        if (bl) {
            int n2 = parsePosition.getIndex();
            if ((n & 1) != 0) {
                n2 = PatternProps.skipWhiteSpace(string, n2);
            }
            if (n2 != string.length()) {
                throw new IllegalArgumentException("Parse of \"" + string + "\" failed at " + n2);
            }
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyPattern(RuleCharacterIterator ruleCharacterIterator, SymbolTable symbolTable, Appendable appendable, int n, int n2) {
        if (n2 > 100) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Pattern nested too deeply");
        }
        int n3 = 3;
        if ((n & 1) != 0) {
            n3 |= 4;
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = null;
        boolean bl = false;
        UnicodeSet unicodeSet = null;
        Object object = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        char c = '\u0000';
        boolean bl2 = false;
        this.clear();
        String string = null;
        block27: while (n6 != 2 && !ruleCharacterIterator.atEnd()) {
            int n7;
            UnicodeSet unicodeSet2;
            boolean bl3;
            int n8;
            block78: {
                UnicodeMatcher unicodeMatcher;
                block80: {
                    block79: {
                        n8 = 0;
                        bl3 = false;
                        unicodeSet2 = null;
                        n7 = 0;
                        if (!UnicodeSet.resemblesPropertyPattern(ruleCharacterIterator, n3)) break block79;
                        n7 = 2;
                        break block78;
                    }
                    object = ruleCharacterIterator.getPos(object);
                    n8 = ruleCharacterIterator.next(n3);
                    bl3 = ruleCharacterIterator.isEscaped();
                    if (n8 != 91 || bl3) break block80;
                    if (n6 == 1) {
                        ruleCharacterIterator.setPos(object);
                        n7 = 1;
                        break block78;
                    } else {
                        n6 = 1;
                        stringBuilder.append('[');
                        object = ruleCharacterIterator.getPos(object);
                        n8 = ruleCharacterIterator.next(n3);
                        bl3 = ruleCharacterIterator.isEscaped();
                        if (n8 == 94 && !bl3) {
                            bl2 = true;
                            stringBuilder.append('^');
                            object = ruleCharacterIterator.getPos(object);
                            n8 = ruleCharacterIterator.next(n3);
                            bl3 = ruleCharacterIterator.isEscaped();
                        }
                        if (n8 == 45) {
                            bl3 = true;
                            break block78;
                        } else {
                            ruleCharacterIterator.setPos(object);
                            continue;
                        }
                    }
                }
                if (symbolTable != null && (unicodeMatcher = symbolTable.lookupMatcher(n8)) != null) {
                    try {
                        unicodeSet2 = (UnicodeSet)unicodeMatcher;
                        n7 = 3;
                    } catch (ClassCastException classCastException) {
                        UnicodeSet.syntaxError(ruleCharacterIterator, "Syntax error");
                    }
                }
            }
            if (n7 != 0) {
                if (n4 == 1) {
                    if (c != '\u0000') {
                        UnicodeSet.syntaxError(ruleCharacterIterator, "Char expected after operator");
                    }
                    this.add_unchecked(n5, n5);
                    UnicodeSet._appendToPat(stringBuilder, n5, false);
                    n4 = 0;
                    c = '\u0000';
                }
                if (c == '-' || c == '&') {
                    stringBuilder.append(c);
                }
                if (unicodeSet2 == null) {
                    if (unicodeSet == null) {
                        unicodeSet = new UnicodeSet();
                    }
                    unicodeSet2 = unicodeSet;
                }
                switch (n7) {
                    case 1: {
                        unicodeSet2.applyPattern(ruleCharacterIterator, symbolTable, stringBuilder, n, n2 + 1);
                        break;
                    }
                    case 2: {
                        ruleCharacterIterator.skipIgnored(n3);
                        unicodeSet2.applyPropertyPattern(ruleCharacterIterator, stringBuilder, symbolTable);
                        break;
                    }
                    case 3: {
                        unicodeSet2._toPattern(stringBuilder, false);
                        break;
                    }
                }
                bl = true;
                if (n6 == 0) {
                    this.set(unicodeSet2);
                    n6 = 2;
                    break;
                }
                switch (c) {
                    case '-': {
                        this.removeAll(unicodeSet2);
                        break;
                    }
                    case '&': {
                        this.retainAll(unicodeSet2);
                        break;
                    }
                    case '\u0000': {
                        this.addAll(unicodeSet2);
                        break;
                    }
                }
                c = '\u0000';
                n4 = 2;
                continue;
            }
            if (n6 == 0) {
                UnicodeSet.syntaxError(ruleCharacterIterator, "Missing '['");
            }
            if (!bl3) {
                switch (n8) {
                    case 93: {
                        if (n4 == 1) {
                            this.add_unchecked(n5, n5);
                            UnicodeSet._appendToPat(stringBuilder, n5, false);
                        }
                        if (c == '-') {
                            this.add_unchecked(c, c);
                            stringBuilder.append(c);
                        } else if (c == '&') {
                            UnicodeSet.syntaxError(ruleCharacterIterator, "Trailing '&'");
                        }
                        stringBuilder.append(']');
                        n6 = 2;
                        continue block27;
                    }
                    case 45: {
                        if (c == '\u0000') {
                            if (n4 != 0) {
                                c = (char)n8;
                                continue block27;
                            }
                            if (string != null) {
                                c = (char)n8;
                                continue block27;
                            }
                            this.add_unchecked(n8, n8);
                            n8 = ruleCharacterIterator.next(n3);
                            bl3 = ruleCharacterIterator.isEscaped();
                            if (n8 == 93 && !bl3) {
                                stringBuilder.append("-]");
                                n6 = 2;
                                continue block27;
                            }
                        }
                        UnicodeSet.syntaxError(ruleCharacterIterator, "'-' not after char, string, or set");
                        break;
                    }
                    case 38: {
                        if (n4 == 2 && c == '\u0000') {
                            c = (char)n8;
                            continue block27;
                        }
                        UnicodeSet.syntaxError(ruleCharacterIterator, "'&' not after set");
                        break;
                    }
                    case 94: {
                        UnicodeSet.syntaxError(ruleCharacterIterator, "'^' not after '['");
                        break;
                    }
                    case 123: {
                        int n9;
                        if (c != '\u0000' && c != '-') {
                            UnicodeSet.syntaxError(ruleCharacterIterator, "Missing operand after operator");
                        }
                        if (n4 == 1) {
                            this.add_unchecked(n5, n5);
                            UnicodeSet._appendToPat(stringBuilder, n5, false);
                        }
                        n4 = 0;
                        if (stringBuilder2 == null) {
                            stringBuilder2 = new StringBuilder();
                        } else {
                            stringBuilder2.setLength(0);
                        }
                        boolean bl4 = false;
                        while (!ruleCharacterIterator.atEnd()) {
                            n8 = ruleCharacterIterator.next(n3);
                            bl3 = ruleCharacterIterator.isEscaped();
                            if (n8 == 125 && !bl3) {
                                bl4 = true;
                                break;
                            }
                            UnicodeSet.appendCodePoint(stringBuilder2, n8);
                        }
                        if (stringBuilder2.length() < 1 || !bl4) {
                            UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid multicharacter string");
                        }
                        String string2 = stringBuilder2.toString();
                        if (c == '-') {
                            n9 = CharSequences.getSingleCodePoint(string == null ? "" : string);
                            int n10 = CharSequences.getSingleCodePoint(string2);
                            if (n9 != Integer.MAX_VALUE && n10 != Integer.MAX_VALUE) {
                                this.add(n9, n10);
                            } else {
                                if (this.strings == EMPTY_STRINGS) {
                                    this.strings = new TreeSet<String>();
                                }
                                try {
                                    StringRange.expand(string, string2, true, this.strings);
                                } catch (Exception exception) {
                                    UnicodeSet.syntaxError(ruleCharacterIterator, exception.getMessage());
                                }
                            }
                            string = null;
                            c = '\u0000';
                        } else {
                            this.add(string2);
                            string = string2;
                        }
                        stringBuilder.append('{');
                        UnicodeSet._appendToPat(stringBuilder, string2, false);
                        stringBuilder.append('}');
                        continue block27;
                    }
                    case 36: {
                        int n9;
                        object = ruleCharacterIterator.getPos(object);
                        n8 = ruleCharacterIterator.next(n3);
                        bl3 = ruleCharacterIterator.isEscaped();
                        int n11 = n9 = n8 == 93 && !bl3 ? 1 : 0;
                        if (symbolTable == null && n9 == 0) {
                            n8 = 36;
                            ruleCharacterIterator.setPos(object);
                            break;
                        }
                        if (n9 != 0 && c == '\u0000') {
                            if (n4 == 1) {
                                this.add_unchecked(n5, n5);
                                UnicodeSet._appendToPat(stringBuilder, n5, false);
                            }
                            this.add_unchecked(65535);
                            bl = true;
                            stringBuilder.append('$').append(']');
                            n6 = 2;
                            continue block27;
                        }
                        UnicodeSet.syntaxError(ruleCharacterIterator, "Unquoted '$'");
                        break;
                    }
                }
            }
            switch (n4) {
                case 0: {
                    if (c == '-' && string != null) {
                        UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid range");
                    }
                    n4 = 1;
                    n5 = n8;
                    string = null;
                    break;
                }
                case 1: {
                    if (c == '-') {
                        if (string != null) {
                            UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid range");
                        }
                        if (n5 >= n8) {
                            UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid range");
                        }
                        this.add_unchecked(n5, n8);
                        UnicodeSet._appendToPat(stringBuilder, n5, false);
                        stringBuilder.append(c);
                        UnicodeSet._appendToPat(stringBuilder, n8, false);
                        n4 = 0;
                        c = '\u0000';
                        break;
                    }
                    this.add_unchecked(n5, n5);
                    UnicodeSet._appendToPat(stringBuilder, n5, false);
                    n5 = n8;
                    break;
                }
                case 2: {
                    if (c != '\u0000') {
                        UnicodeSet.syntaxError(ruleCharacterIterator, "Set expected after operator");
                    }
                    n5 = n8;
                    n4 = 1;
                    continue block27;
                }
            }
        }
        if (n6 != 2) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Missing ']'");
        }
        ruleCharacterIterator.skipIgnored(n3);
        if ((n & 2) != 0) {
            this.closeOver(2);
        }
        if (bl2) {
            this.complement();
        }
        if (bl) {
            UnicodeSet.append(appendable, stringBuilder.toString());
            return;
        }
        this.appendNewPattern(appendable, false, true);
    }

    private static void syntaxError(RuleCharacterIterator ruleCharacterIterator, String string) {
        throw new IllegalArgumentException("Error: " + string + " at \"" + Utility.escape(ruleCharacterIterator.toString()) + '\"');
    }

    public <T extends Collection<String>> T addAllTo(T t) {
        return UnicodeSet.addAllTo(this, t);
    }

    public String[] addAllTo(String[] stringArray) {
        return UnicodeSet.addAllTo(this, stringArray);
    }

    public static String[] toArray(UnicodeSet unicodeSet) {
        return UnicodeSet.addAllTo(unicodeSet, new String[unicodeSet.size()]);
    }

    public UnicodeSet add(Iterable<?> iterable) {
        return this.addAll(iterable);
    }

    public UnicodeSet addAll(Iterable<?> iterable) {
        this.checkFrozen();
        for (Object obj : iterable) {
            this.add(obj.toString());
        }
        return this;
    }

    private int nextCapacity(int n) {
        if (n < 25) {
            return n + 25;
        }
        if (n <= 2500) {
            return 5 * n;
        }
        int n2 = 2 * n;
        if (n2 > 0x110001) {
            n2 = 0x110001;
        }
        return n2;
    }

    private void ensureCapacity(int n) {
        if (n > 0x110001) {
            n = 0x110001;
        }
        if (n <= this.list.length) {
            return;
        }
        int n2 = this.nextCapacity(n);
        int[] nArray = new int[n2];
        System.arraycopy(this.list, 0, nArray, 0, this.len);
        this.list = nArray;
    }

    private void ensureBufferCapacity(int n) {
        if (n > 0x110001) {
            n = 0x110001;
        }
        if (this.buffer != null && n <= this.buffer.length) {
            return;
        }
        int n2 = this.nextCapacity(n);
        this.buffer = new int[n2];
    }

    private int[] range(int n, int n2) {
        if (this.rangeList == null) {
            this.rangeList = new int[]{n, n2 + 1, 0x110000};
        } else {
            this.rangeList[0] = n;
            this.rangeList[1] = n2 + 1;
        }
        return this.rangeList;
    }

    private UnicodeSet xor(int[] nArray, int n, int n2) {
        int n3;
        this.ensureBufferCapacity(this.len + n);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = this.list[n4++];
        if (n2 == 1 || n2 == 2) {
            n3 = 0;
            if (nArray[n5] == 0) {
                n3 = nArray[++n5];
            }
        } else {
            n3 = nArray[n5++];
        }
        while (true) {
            if (n7 < n3) {
                this.buffer[n6++] = n7;
                n7 = this.list[n4++];
                continue;
            }
            if (n3 < n7) {
                this.buffer[n6++] = n3;
                n3 = nArray[n5++];
                continue;
            }
            if (n7 == 0x110000) break;
            n7 = this.list[n4++];
            n3 = nArray[n5++];
        }
        this.buffer[n6++] = 0x110000;
        this.len = n6;
        int[] nArray2 = this.list;
        this.list = this.buffer;
        this.buffer = nArray2;
        this.pat = null;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    private UnicodeSet add(int[] nArray, int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = this.list[n3++];
        int n7 = nArray[n4++];
        block6: while (true) {
            switch (n2) {
                case 0: {
                    if (n6 < n7) {
                        if (n5 > 0 && n6 <= this.buffer[n5 - 1]) {
                            n6 = UnicodeSet.max(this.list[n3], this.buffer[--n5]);
                        } else {
                            this.buffer[n5++] = n6;
                            n6 = this.list[n3];
                        }
                        ++n3;
                        n2 ^= 1;
                        break;
                    }
                    if (n7 < n6) {
                        if (n5 > 0 && n7 <= this.buffer[n5 - 1]) {
                            n7 = UnicodeSet.max(nArray[n4], this.buffer[--n5]);
                        } else {
                            this.buffer[n5++] = n7;
                            n7 = nArray[n4];
                        }
                        ++n4;
                        n2 ^= 2;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    if (n5 > 0 && n6 <= this.buffer[n5 - 1]) {
                        n6 = UnicodeSet.max(this.list[n3], this.buffer[--n5]);
                    } else {
                        this.buffer[n5++] = n6;
                        n6 = this.list[n3];
                    }
                    ++n3;
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 3: {
                    if (n7 <= n6) {
                        if (n6 == 0x110000) break block6;
                        this.buffer[n5++] = n6;
                    } else {
                        if (n7 == 0x110000) break block6;
                        this.buffer[n5++] = n7;
                    }
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 1: {
                    if (n6 < n7) {
                        this.buffer[n5++] = n6;
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n7 < n6) {
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 2: {
                    if (n7 < n6) {
                        this.buffer[n5++] = n7;
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 < n7) {
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                }
            }
        }
        this.buffer[n5++] = 0x110000;
        this.len = n5;
        int[] nArray2 = this.list;
        this.list = this.buffer;
        this.buffer = nArray2;
        this.pat = null;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    private UnicodeSet retain(int[] nArray, int n, int n2) {
        this.ensureBufferCapacity(this.len + n);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = this.list[n3++];
        int n7 = nArray[n4++];
        block6: while (true) {
            switch (n2) {
                case 0: {
                    if (n6 < n7) {
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n7 < n6) {
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    this.buffer[n5++] = n6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 3: {
                    if (n6 < n7) {
                        this.buffer[n5++] = n6;
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n7 < n6) {
                        this.buffer[n5++] = n7;
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    this.buffer[n5++] = n6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 1: {
                    if (n6 < n7) {
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n7 < n6) {
                        this.buffer[n5++] = n7;
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                    break;
                }
                case 2: {
                    if (n7 < n6) {
                        n7 = nArray[n4++];
                        n2 ^= 2;
                        break;
                    }
                    if (n6 < n7) {
                        this.buffer[n5++] = n6;
                        n6 = this.list[n3++];
                        n2 ^= 1;
                        break;
                    }
                    if (n6 == 0x110000) break block6;
                    n6 = this.list[n3++];
                    n2 ^= 1;
                    n7 = nArray[n4++];
                    n2 ^= 2;
                }
            }
        }
        this.buffer[n5++] = 0x110000;
        this.len = n5;
        int[] nArray2 = this.list;
        this.list = this.buffer;
        this.buffer = nArray2;
        this.pat = null;
        return this;
    }

    private static final int max(int n, int n2) {
        return n > n2 ? n : n2;
    }

    private void applyFilter(Filter filter, UnicodeSet unicodeSet) {
        this.clear();
        int n = -1;
        int n2 = unicodeSet.getRangeCount();
        for (int i = 0; i < n2; ++i) {
            int n3 = unicodeSet.getRangeStart(i);
            int n4 = unicodeSet.getRangeEnd(i);
            for (int j = n3; j <= n4; ++j) {
                if (filter.contains(j)) {
                    if (n >= 0) continue;
                    n = j;
                    continue;
                }
                if (n < 0) continue;
                this.add_unchecked(n, j - 1);
                n = -1;
            }
        }
        if (n >= 0) {
            this.add_unchecked(n, 0x10FFFF);
        }
    }

    private static String mungeCharName(String string) {
        string = PatternProps.trimWhiteSpace(string);
        StringBuilder stringBuilder = null;
        for (int i = 0; i < string.length(); ++i) {
            int n = string.charAt(i);
            if (PatternProps.isWhiteSpace(n)) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder().append(string, 0, i);
                } else if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') continue;
                n = 32;
            }
            if (stringBuilder == null) continue;
            stringBuilder.append((char)n);
        }
        return stringBuilder == null ? string : stringBuilder.toString();
    }

    public UnicodeSet applyIntPropertyValue(int n, int n2) {
        if (n == 8192) {
            UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
            this.applyFilter(new GeneralCategoryMaskFilter(n2), unicodeSet);
        } else if (n == 28672) {
            UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
            this.applyFilter(new ScriptExtensionsFilter(n2), unicodeSet);
        } else if (0 <= n && n < 65) {
            if (n2 == 0 || n2 == 1) {
                this.set(CharacterProperties.getBinaryPropertySet(n));
                if (n2 == 0) {
                    this.complement();
                }
            } else {
                this.clear();
            }
        } else if (4096 <= n && n < 4121) {
            UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
            this.applyFilter(new IntPropertyFilter(n, n2), unicodeSet);
        } else {
            throw new IllegalArgumentException("unsupported property " + n);
        }
        return this;
    }

    public UnicodeSet applyPropertyAlias(String string, String string2) {
        return this.applyPropertyAlias(string, string2, null);
    }

    /*
     * Unable to fully structure code
     */
    public UnicodeSet applyPropertyAlias(String var1_1, String var2_2, SymbolTable var3_3) {
        block22: {
            block23: {
                block20: {
                    block21: {
                        this.checkFrozen();
                        var6_4 = false;
                        if (var3_3 != null && var3_3 instanceof XSymbolTable && ((XSymbolTable)var3_3).applyPropertyAlias(var1_1, var2_2, this)) {
                            return this;
                        }
                        if (UnicodeSet.XSYMBOL_TABLE != null && UnicodeSet.XSYMBOL_TABLE.applyPropertyAlias(var1_1, var2_2, this)) {
                            return this;
                        }
                        if (var2_2.length() <= 0) break block20;
                        var4_5 = UCharacter.getPropertyEnum(var1_1);
                        if (var4_5 == 4101) {
                            var4_5 = 8192;
                        }
                        if (!(var4_5 >= 0 && var4_5 < 65 || var4_5 >= 4096 && var4_5 < 4121) && (var4_5 < 8192 || var4_5 >= 8193)) break block21;
                        try {
                            var5_6 = UCharacter.getPropertyValueEnum(var4_5, var2_2);
                        } catch (IllegalArgumentException var7_7) {
                            if (var4_5 == 4098 || var4_5 == 4112 || var4_5 == 4113) {
                                var5_6 = Integer.parseInt(PatternProps.trimWhiteSpace(var2_2));
                                if (var5_6 >= 0 && var5_6 <= 255) ** GOTO lbl76
                                throw var7_7;
                            }
                            throw var7_7;
                        }
                    }
                    switch (var4_5) {
                        case 12288: {
                            var7_8 = Double.parseDouble(PatternProps.trimWhiteSpace(var2_2));
                            this.applyFilter(new NumericValueFilter(var7_8), CharacterPropertiesImpl.getInclusionsForProperty(var4_5));
                            return this;
                        }
                        case 16389: {
                            var7_9 = UnicodeSet.mungeCharName(var2_2);
                            var8_12 = UCharacter.getCharFromExtendedName(var7_9);
                            if (var8_12 == -1) {
                                throw new IllegalArgumentException("Invalid character name");
                            }
                            this.clear();
                            this.add_unchecked(var8_12);
                            return this;
                        }
                        case 16395: {
                            throw new IllegalArgumentException("Unicode_1_Name (na1) not supported");
                        }
                        case 16384: {
                            var7_10 = VersionInfo.getInstance(UnicodeSet.mungeCharName(var2_2));
                            this.applyFilter(new VersionFilter(var7_10), CharacterPropertiesImpl.getInclusionsForProperty(var4_5));
                            return this;
                        }
                        case 28672: {
                            var5_6 = UCharacter.getPropertyValueEnum(4106, var2_2);
                            break block22;
                        }
                        default: {
                            throw new IllegalArgumentException("Unsupported property");
                        }
                    }
                }
                var7_11 = UPropertyAliases.INSTANCE;
                var4_5 = 8192;
                var5_6 = var7_11.getPropertyValueEnum(var4_5, var1_1);
                if (var5_6 != -1 || (var5_6 = var7_11.getPropertyValueEnum(var4_5 = 4106, var1_1)) != -1) break block22;
                var4_5 = var7_11.getPropertyEnum(var1_1);
                if (var4_5 == -1) {
                    var4_5 = -1;
                }
                if (var4_5 < 0 || var4_5 >= 65) break block23;
                var5_6 = 1;
                break block22;
            }
            if (var4_5 != -1) ** GOTO lbl75
            if (0 == UPropertyAliases.compare("ANY", var1_1)) {
                this.set(0, 0x10FFFF);
                return this;
            }
            if (0 == UPropertyAliases.compare("ASCII", var1_1)) {
                this.set(0, 127);
                return this;
            }
            if (0 == UPropertyAliases.compare("Assigned", var1_1)) {
                var4_5 = 8192;
                var5_6 = 1;
                var6_4 = true;
            } else {
                throw new IllegalArgumentException("Invalid property alias: " + var1_1 + "=" + var2_2);
lbl75:
                // 1 sources

                throw new IllegalArgumentException("Missing property value");
            }
        }
        this.applyIntPropertyValue(var4_5, var5_6);
        if (var6_4) {
            this.complement();
        }
        return this;
    }

    private static boolean resemblesPropertyPattern(String string, int n) {
        if (n + 5 > string.length()) {
            return true;
        }
        return string.regionMatches(n, "[:", 0, 1) || string.regionMatches(true, n, "\\p", 0, 1) || string.regionMatches(n, "\\N", 0, 1);
    }

    private static boolean resemblesPropertyPattern(RuleCharacterIterator ruleCharacterIterator, int n) {
        boolean bl = false;
        Object object = ruleCharacterIterator.getPos(null);
        int n2 = ruleCharacterIterator.next(n &= 0xFFFFFFFD);
        if (n2 == 91 || n2 == 92) {
            int n3 = ruleCharacterIterator.next(n & 0xFFFFFFFB);
            bl = n2 == 91 ? n3 == 58 : n3 == 78 || n3 == 112 || n3 == 80;
        }
        ruleCharacterIterator.setPos(object);
        return bl;
    }

    private UnicodeSet applyPropertyPattern(String string, ParsePosition parsePosition, SymbolTable symbolTable) {
        String string2;
        String string3;
        int n;
        int n2 = parsePosition.getIndex();
        if (n2 + 5 > string.length()) {
            return null;
        }
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (string.regionMatches(n2, "[:", 0, 1)) {
            bl = true;
            if ((n2 = PatternProps.skipWhiteSpace(string, n2 + 2)) < string.length() && string.charAt(n2) == '^') {
                ++n2;
                bl3 = true;
            }
        } else if (string.regionMatches(true, n2, "\\p", 0, 1) || string.regionMatches(n2, "\\N", 0, 1)) {
            n = string.charAt(n2 + 1);
            bl3 = n == 80;
            bl2 = n == 78;
            n2 = PatternProps.skipWhiteSpace(string, n2 + 2);
            if (n2 == string.length() || string.charAt(n2++) != '{') {
                return null;
            }
        } else {
            return null;
        }
        if ((n = string.indexOf(bl ? ":]" : "}", n2)) < 0) {
            return null;
        }
        int n3 = string.indexOf(61, n2);
        if (n3 >= 0 && n3 < n && !bl2) {
            string3 = string.substring(n2, n3);
            string2 = string.substring(n3 + 1, n);
        } else {
            string3 = string.substring(n2, n);
            string2 = "";
            if (bl2) {
                string2 = string3;
                string3 = "na";
            }
        }
        this.applyPropertyAlias(string3, string2, symbolTable);
        if (bl3) {
            this.complement();
        }
        parsePosition.setIndex(n + (bl ? 2 : 1));
        return this;
    }

    private void applyPropertyPattern(RuleCharacterIterator ruleCharacterIterator, Appendable appendable, SymbolTable symbolTable) {
        String string = ruleCharacterIterator.lookahead();
        ParsePosition parsePosition = new ParsePosition(0);
        this.applyPropertyPattern(string, parsePosition, symbolTable);
        if (parsePosition.getIndex() == 0) {
            UnicodeSet.syntaxError(ruleCharacterIterator, "Invalid property pattern");
        }
        ruleCharacterIterator.jumpahead(parsePosition.getIndex());
        UnicodeSet.append(appendable, string.substring(0, parsePosition.getIndex()));
    }

    private static final void addCaseMapping(UnicodeSet unicodeSet, int n, StringBuilder stringBuilder) {
        if (n >= 0) {
            if (n > 31) {
                unicodeSet.add(n);
            } else {
                unicodeSet.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    }

    public UnicodeSet closeOver(int n) {
        this.checkFrozen();
        if ((n & 6) != 0) {
            UCaseProps uCaseProps = UCaseProps.INSTANCE;
            UnicodeSet unicodeSet = new UnicodeSet(this);
            ULocale uLocale = ULocale.ROOT;
            if ((n & 2) != 0 && unicodeSet.hasStrings()) {
                unicodeSet.strings.clear();
            }
            int n2 = this.getRangeCount();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < n2; ++i) {
                int n3;
                int n4 = this.getRangeStart(i);
                int n5 = this.getRangeEnd(i);
                if ((n & 2) != 0) {
                    for (n3 = n4; n3 <= n5; ++n3) {
                        uCaseProps.addCaseClosure(n3, unicodeSet);
                    }
                    continue;
                }
                for (n3 = n4; n3 <= n5; ++n3) {
                    int n6 = uCaseProps.toFullLower(n3, null, stringBuilder, 1);
                    UnicodeSet.addCaseMapping(unicodeSet, n6, stringBuilder);
                    n6 = uCaseProps.toFullTitle(n3, null, stringBuilder, 1);
                    UnicodeSet.addCaseMapping(unicodeSet, n6, stringBuilder);
                    n6 = uCaseProps.toFullUpper(n3, null, stringBuilder, 1);
                    UnicodeSet.addCaseMapping(unicodeSet, n6, stringBuilder);
                    n6 = uCaseProps.toFullFolding(n3, stringBuilder, 0);
                    UnicodeSet.addCaseMapping(unicodeSet, n6, stringBuilder);
                }
            }
            if (this.hasStrings()) {
                if ((n & 2) != 0) {
                    for (String string : this.strings) {
                        String string2 = UCharacter.foldCase(string, 0);
                        if (uCaseProps.addStringCaseClosure(string2, unicodeSet)) continue;
                        unicodeSet.add(string2);
                    }
                } else {
                    BreakIterator breakIterator = BreakIterator.getWordInstance(uLocale);
                    for (String string : this.strings) {
                        unicodeSet.add(UCharacter.toLowerCase(uLocale, string));
                        unicodeSet.add(UCharacter.toTitleCase(uLocale, string, breakIterator));
                        unicodeSet.add(UCharacter.toUpperCase(uLocale, string));
                        unicodeSet.add(UCharacter.foldCase(string, 0));
                    }
                }
            }
            this.set(unicodeSet);
        }
        return this;
    }

    @Override
    public boolean isFrozen() {
        return this.bmpSet != null || this.stringSpan != null;
    }

    @Override
    public UnicodeSet freeze() {
        if (!this.isFrozen()) {
            this.compact();
            if (this.hasStrings()) {
                this.stringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), 127);
            }
            if (this.stringSpan == null || !this.stringSpan.needsStringSpanUTF16()) {
                this.bmpSet = new BMPSet(this.list, this.len);
            }
        }
        return this;
    }

    public int span(CharSequence charSequence, SpanCondition spanCondition) {
        return this.span(charSequence, 0, spanCondition);
    }

    public int span(CharSequence charSequence, int n, SpanCondition spanCondition) {
        int n2;
        UnicodeSetStringSpan unicodeSetStringSpan;
        int n3 = charSequence.length();
        if (n < 0) {
            n = 0;
        } else if (n >= n3) {
            return n3;
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(charSequence, n, spanCondition, null);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.span(charSequence, n, spanCondition);
        }
        if (this.hasStrings() && (unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n2 = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34)).needsStringSpanUTF16()) {
            return unicodeSetStringSpan.span(charSequence, n, spanCondition);
        }
        return this.spanCodePointsAndCount(charSequence, n, spanCondition, null);
    }

    @Deprecated
    public int spanAndCount(CharSequence charSequence, int n, SpanCondition spanCondition, OutputInt outputInt) {
        if (outputInt == null) {
            throw new IllegalArgumentException("outCount must not be null");
        }
        int n2 = charSequence.length();
        if (n < 0) {
            n = 0;
        } else if (n >= n2) {
            return n2;
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanAndCount(charSequence, n, spanCondition, outputInt);
        }
        if (this.bmpSet != null) {
            return this.bmpSet.span(charSequence, n, spanCondition, outputInt);
        }
        if (this.hasStrings()) {
            int n3 = spanCondition == SpanCondition.NOT_CONTAINED ? 33 : 34;
            UnicodeSetStringSpan unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n3 |= 0x40);
            return unicodeSetStringSpan.spanAndCount(charSequence, n, spanCondition, outputInt);
        }
        return this.spanCodePointsAndCount(charSequence, n, spanCondition, outputInt);
    }

    private int spanCodePointsAndCount(CharSequence charSequence, int n, SpanCondition spanCondition, OutputInt outputInt) {
        int n2;
        boolean bl = spanCondition != SpanCondition.NOT_CONTAINED;
        int n3 = n;
        int n4 = charSequence.length();
        int n5 = 0;
        while (bl == this.contains(n2 = Character.codePointAt(charSequence, n3))) {
            ++n5;
            if ((n3 += Character.charCount(n2)) < n4) continue;
        }
        if (outputInt != null) {
            outputInt.value = n5;
        }
        return n3;
    }

    public int spanBack(CharSequence charSequence, SpanCondition spanCondition) {
        return this.spanBack(charSequence, charSequence.length(), spanCondition);
    }

    public int spanBack(CharSequence charSequence, int n, SpanCondition spanCondition) {
        int n2;
        int n3;
        UnicodeSetStringSpan unicodeSetStringSpan;
        if (n <= 0) {
            return 1;
        }
        if (n > charSequence.length()) {
            n = charSequence.length();
        }
        if (this.bmpSet != null) {
            return this.bmpSet.spanBack(charSequence, n, spanCondition);
        }
        if (this.stringSpan != null) {
            return this.stringSpan.spanBack(charSequence, n, spanCondition);
        }
        if (this.hasStrings() && (unicodeSetStringSpan = new UnicodeSetStringSpan(this, new ArrayList<String>(this.strings), n3 = spanCondition == SpanCondition.NOT_CONTAINED ? 17 : 18)).needsStringSpanUTF16()) {
            return unicodeSetStringSpan.spanBack(charSequence, n, spanCondition);
        }
        n3 = spanCondition != SpanCondition.NOT_CONTAINED ? 1 : 0;
        int n4 = n;
        while (n3 == this.contains(n2 = Character.codePointBefore(charSequence, n4)) && (n4 -= Character.charCount(n2)) > 0) {
        }
        return n4;
    }

    @Override
    public UnicodeSet cloneAsThawed() {
        UnicodeSet unicodeSet = new UnicodeSet(this);
        if (!$assertionsDisabled && unicodeSet.isFrozen()) {
            throw new AssertionError();
        }
        return unicodeSet;
    }

    private void checkFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
    }

    public Iterable<EntryRange> ranges() {
        return new EntryRangeIterable(this, null);
    }

    @Override
    public Iterator<String> iterator() {
        return new UnicodeSetIterator2(this);
    }

    public <T extends CharSequence> boolean containsAll(Iterable<T> iterable) {
        for (CharSequence charSequence : iterable) {
            if (this.contains(charSequence)) continue;
            return true;
        }
        return false;
    }

    public <T extends CharSequence> boolean containsNone(Iterable<T> iterable) {
        for (CharSequence charSequence : iterable) {
            if (!this.contains(charSequence)) continue;
            return true;
        }
        return false;
    }

    public final <T extends CharSequence> boolean containsSome(Iterable<T> iterable) {
        return !this.containsNone(iterable);
    }

    public <T extends CharSequence> UnicodeSet addAll(T ... TArray) {
        this.checkFrozen();
        for (T t : TArray) {
            this.add((CharSequence)t);
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet removeAll(Iterable<T> iterable) {
        this.checkFrozen();
        for (CharSequence charSequence : iterable) {
            this.remove(charSequence);
        }
        return this;
    }

    public <T extends CharSequence> UnicodeSet retainAll(Iterable<T> iterable) {
        this.checkFrozen();
        UnicodeSet unicodeSet = new UnicodeSet();
        unicodeSet.addAll(iterable);
        this.retainAll(unicodeSet);
        return this;
    }

    @Override
    public int compareTo(UnicodeSet unicodeSet) {
        return this.compareTo(unicodeSet, ComparisonStyle.SHORTER_FIRST);
    }

    public int compareTo(UnicodeSet unicodeSet, ComparisonStyle comparisonStyle) {
        int n;
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC && (n = this.size() - unicodeSet.size()) != 0) {
            return n < 0 == (comparisonStyle == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
        }
        int n2 = 0;
        while (true) {
            if (0 != (n = this.list[n2] - unicodeSet.list[n2])) {
                if (this.list[n2] == 0x110000) {
                    if (!this.hasStrings()) {
                        return 0;
                    }
                    String string = this.strings.first();
                    return UnicodeSet.compare(string, unicodeSet.list[n2]);
                }
                if (unicodeSet.list[n2] == 0x110000) {
                    if (!unicodeSet.hasStrings()) {
                        return 1;
                    }
                    String string = unicodeSet.strings.first();
                    int n3 = UnicodeSet.compare(string, this.list[n2]);
                    return n3 > 0 ? -1 : (n3 < 0 ? 1 : 0);
                }
                return (n2 & 1) == 0 ? n : -n;
            }
            if (this.list[n2] == 0x110000) break;
            ++n2;
        }
        return UnicodeSet.compare(this.strings, unicodeSet.strings);
    }

    @Override
    public int compareTo(Iterable<String> iterable) {
        return UnicodeSet.compare(this, iterable);
    }

    public static int compare(CharSequence charSequence, int n) {
        return CharSequences.compare(charSequence, n);
    }

    public static int compare(int n, CharSequence charSequence) {
        return -CharSequences.compare(charSequence, n);
    }

    public static <T extends Comparable<T>> int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        return UnicodeSet.compare(iterable.iterator(), iterable2.iterator());
    }

    @Deprecated
    public static <T extends Comparable<T>> int compare(Iterator<T> iterator2, Iterator<T> iterator3) {
        Comparable comparable;
        Comparable comparable2;
        int n;
        do {
            if (!iterator2.hasNext()) {
                return iterator3.hasNext() ? -1 : 0;
            }
            if (iterator3.hasNext()) continue;
            return 0;
        } while ((n = (comparable2 = (Comparable)iterator2.next()).compareTo(comparable = (Comparable)iterator3.next())) == 0);
        return n;
    }

    public static <T extends Comparable<T>> int compare(Collection<T> collection, Collection<T> collection2, ComparisonStyle comparisonStyle) {
        int n;
        if (comparisonStyle != ComparisonStyle.LEXICOGRAPHIC && (n = collection.size() - collection2.size()) != 0) {
            return n < 0 == (comparisonStyle == ComparisonStyle.SHORTER_FIRST) ? -1 : 1;
        }
        return UnicodeSet.compare(collection, collection2);
    }

    public static <T, U extends Collection<T>> U addAllTo(Iterable<T> iterable, U u) {
        for (T t : iterable) {
            u.add(t);
        }
        return u;
    }

    public static <T> T[] addAllTo(Iterable<T> iterable, T[] TArray) {
        int n = 0;
        for (T t : iterable) {
            TArray[n++] = t;
        }
        return TArray;
    }

    public Collection<String> strings() {
        if (this.hasStrings()) {
            return Collections.unmodifiableSortedSet(this.strings);
        }
        return EMPTY_STRINGS;
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        return CharSequences.getSingleCodePoint(charSequence);
    }

    @Deprecated
    public UnicodeSet addBridges(UnicodeSet unicodeSet) {
        UnicodeSet unicodeSet2 = new UnicodeSet(this).complement();
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet2);
        while (unicodeSetIterator.nextRange()) {
            if (unicodeSetIterator.codepoint == 0 || unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING || unicodeSetIterator.codepointEnd == 0x10FFFF || !unicodeSet.contains(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd)) continue;
            this.add(unicodeSetIterator.codepoint, unicodeSetIterator.codepointEnd);
        }
        return this;
    }

    @Deprecated
    public int findIn(CharSequence charSequence, int n, boolean bl) {
        int n2;
        while (n < charSequence.length() && this.contains(n2 = UTF16.charAt(charSequence, n)) == bl) {
            n += UTF16.getCharCount(n2);
        }
        return n;
    }

    @Deprecated
    public int findLastIn(CharSequence charSequence, int n, boolean bl) {
        int n2;
        --n;
        while (n >= 0 && this.contains(n2 = UTF16.charAt(charSequence, n)) == bl) {
            n -= UTF16.getCharCount(n2);
        }
        return n < 0 ? -1 : n;
    }

    @Deprecated
    public String stripFrom(CharSequence charSequence, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < charSequence.length()) {
            int n2 = this.findIn(charSequence, n, !bl);
            stringBuilder.append(charSequence.subSequence(n, n2));
            n = this.findIn(charSequence, n2, bl);
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static XSymbolTable getDefaultXSymbolTable() {
        return XSYMBOL_TABLE;
    }

    @Deprecated
    public static void setDefaultXSymbolTable(XSymbolTable xSymbolTable) {
        CharacterPropertiesImpl.clear();
        XSYMBOL_TABLE = xSymbolTable;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((UnicodeSet)object);
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static VersionInfo access$000() {
        return NO_VERSION;
    }

    static Appendable access$100(Appendable appendable, int n, boolean bl) {
        return UnicodeSet._appendToPat(appendable, n, bl);
    }

    static int access$400(UnicodeSet unicodeSet) {
        return unicodeSet.len;
    }

    static int[] access$500(UnicodeSet unicodeSet) {
        return unicodeSet.list;
    }

    static {
        $assertionsDisabled = !UnicodeSet.class.desiredAssertionStatus();
        EMPTY_STRINGS = Collections.unmodifiableSortedSet(new TreeSet());
        EMPTY = new UnicodeSet().freeze();
        ALL_CODE_POINTS = new UnicodeSet(0, 0x10FFFF).freeze();
        XSYMBOL_TABLE = null;
        NO_VERSION = VersionInfo.getInstance(0, 0, 0, 0);
    }

    public static enum SpanCondition {
        NOT_CONTAINED,
        CONTAINED,
        SIMPLE,
        CONDITION_COUNT;

    }

    public static enum ComparisonStyle {
        SHORTER_FIRST,
        LEXICOGRAPHIC,
        LONGER_FIRST;

    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class UnicodeSetIterator2
    implements Iterator<String> {
        private int[] sourceList;
        private int len;
        private int item;
        private int current;
        private int limit;
        private SortedSet<String> sourceStrings;
        private Iterator<String> stringIterator;
        private char[] buffer;

        UnicodeSetIterator2(UnicodeSet unicodeSet) {
            this.len = UnicodeSet.access$400(unicodeSet) - 1;
            if (this.len > 0) {
                this.sourceStrings = unicodeSet.strings;
                this.sourceList = UnicodeSet.access$500(unicodeSet);
                this.current = this.sourceList[this.item++];
                this.limit = this.sourceList[this.item++];
            } else {
                this.stringIterator = unicodeSet.strings.iterator();
                this.sourceList = null;
            }
        }

        @Override
        public boolean hasNext() {
            return this.sourceList != null || this.stringIterator.hasNext();
        }

        @Override
        public String next() {
            if (this.sourceList == null) {
                return this.stringIterator.next();
            }
            int n = this.current++;
            if (this.current >= this.limit) {
                if (this.item >= this.len) {
                    this.stringIterator = this.sourceStrings.iterator();
                    this.sourceList = null;
                } else {
                    this.current = this.sourceList[this.item++];
                    this.limit = this.sourceList[this.item++];
                }
            }
            if (n <= 65535) {
                return String.valueOf((char)n);
            }
            if (this.buffer == null) {
                this.buffer = new char[2];
            }
            int n2 = n - 65536;
            this.buffer[0] = (char)((n2 >>> 10) + 55296);
            this.buffer[1] = (char)((n2 & 0x3FF) + 56320);
            return String.valueOf(this.buffer);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryRangeIterator
    implements Iterator<EntryRange> {
        int pos;
        EntryRange result;
        final UnicodeSet this$0;

        private EntryRangeIterator(UnicodeSet unicodeSet) {
            this.this$0 = unicodeSet;
            this.result = new EntryRange();
        }

        @Override
        public boolean hasNext() {
            return this.pos < UnicodeSet.access$400(this.this$0) - 1;
        }

        @Override
        public EntryRange next() {
            if (this.pos >= UnicodeSet.access$400(this.this$0) - 1) {
                throw new NoSuchElementException();
            }
            this.result.codepoint = UnicodeSet.access$500(this.this$0)[this.pos++];
            this.result.codepointEnd = UnicodeSet.access$500(this.this$0)[this.pos++] - 1;
            return this.result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryRangeIterator(UnicodeSet unicodeSet, 1 var2_2) {
            this(unicodeSet);
        }
    }

    private class EntryRangeIterable
    implements Iterable<EntryRange> {
        final UnicodeSet this$0;

        private EntryRangeIterable(UnicodeSet unicodeSet) {
            this.this$0 = unicodeSet;
        }

        @Override
        public Iterator<EntryRange> iterator() {
            return new EntryRangeIterator(this.this$0, null);
        }

        EntryRangeIterable(UnicodeSet unicodeSet, 1 var2_2) {
            this(unicodeSet);
        }
    }

    public static class EntryRange {
        public int codepoint;
        public int codepointEnd;

        EntryRange() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            return (this.codepoint == this.codepointEnd ? (StringBuilder)UnicodeSet.access$100(stringBuilder, this.codepoint, false) : (StringBuilder)UnicodeSet.access$100(((StringBuilder)UnicodeSet.access$100(stringBuilder, this.codepoint, false)).append('-'), this.codepointEnd, false)).toString();
        }
    }

    public static abstract class XSymbolTable
    implements SymbolTable {
        @Override
        public UnicodeMatcher lookupMatcher(int n) {
            return null;
        }

        public boolean applyPropertyAlias(String string, String string2, UnicodeSet unicodeSet) {
            return true;
        }

        @Override
        public char[] lookup(String string) {
            return null;
        }

        @Override
        public String parseReference(String string, ParsePosition parsePosition, int n) {
            return null;
        }
    }

    private static final class VersionFilter
    implements Filter {
        VersionInfo version;

        VersionFilter(VersionInfo versionInfo) {
            this.version = versionInfo;
        }

        @Override
        public boolean contains(int n) {
            VersionInfo versionInfo = UCharacter.getAge(n);
            return !Utility.sameObjects(versionInfo, UnicodeSet.access$000()) && versionInfo.compareTo(this.version) <= 0;
        }
    }

    private static final class ScriptExtensionsFilter
    implements Filter {
        int script;

        ScriptExtensionsFilter(int n) {
            this.script = n;
        }

        @Override
        public boolean contains(int n) {
            return UScript.hasScript(n, this.script);
        }
    }

    private static final class IntPropertyFilter
    implements Filter {
        int prop;
        int value;

        IntPropertyFilter(int n, int n2) {
            this.prop = n;
            this.value = n2;
        }

        @Override
        public boolean contains(int n) {
            return UCharacter.getIntPropertyValue(n, this.prop) == this.value;
        }
    }

    private static final class GeneralCategoryMaskFilter
    implements Filter {
        int mask;

        GeneralCategoryMaskFilter(int n) {
            this.mask = n;
        }

        @Override
        public boolean contains(int n) {
            return (1 << UCharacter.getType(n) & this.mask) != 0;
        }
    }

    private static final class NumericValueFilter
    implements Filter {
        double value;

        NumericValueFilter(double d) {
            this.value = d;
        }

        @Override
        public boolean contains(int n) {
            return UCharacter.getUnicodeNumericValue(n) == this.value;
        }
    }

    private static interface Filter {
        public boolean contains(int var1);
    }
}

