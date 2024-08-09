/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;

public class StringSegment
implements CharSequence {
    private final String str;
    private int start;
    private int end;
    private boolean foldCase;
    static final boolean $assertionsDisabled = !StringSegment.class.desiredAssertionStatus();

    public StringSegment(String string, boolean bl) {
        this.str = string;
        this.start = 0;
        this.end = string.length();
        this.foldCase = bl;
    }

    public int getOffset() {
        return this.start;
    }

    public void setOffset(int n) {
        if (!$assertionsDisabled && n > this.end) {
            throw new AssertionError();
        }
        this.start = n;
    }

    public void adjustOffset(int n) {
        if (!$assertionsDisabled && this.start + n < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.start + n > this.end) {
            throw new AssertionError();
        }
        this.start += n;
    }

    public void adjustOffsetByCodePoint() {
        this.start += Character.charCount(this.getCodePoint());
    }

    public void setLength(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.start + n > this.str.length()) {
            throw new AssertionError();
        }
        this.end = this.start + n;
    }

    public void resetLength() {
        this.end = this.str.length();
    }

    @Override
    public int length() {
        return this.end - this.start;
    }

    @Override
    public char charAt(int n) {
        return this.str.charAt(n + this.start);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.str.subSequence(n + this.start, n2 + this.start);
    }

    public int getCodePoint() {
        char c;
        if (!$assertionsDisabled && this.start >= this.end) {
            throw new AssertionError();
        }
        char c2 = this.str.charAt(this.start);
        if (Character.isHighSurrogate(c2) && this.start + 1 < this.end && Character.isLowSurrogate(c = this.str.charAt(this.start + 1))) {
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    public int codePointAt(int n) {
        return this.str.codePointAt(this.start + n);
    }

    public boolean startsWith(int n) {
        return StringSegment.codePointsEqual(this.getCodePoint(), n, this.foldCase);
    }

    public boolean startsWith(UnicodeSet unicodeSet) {
        int n = this.getCodePoint();
        if (n == -1) {
            return true;
        }
        return unicodeSet.contains(n);
    }

    public boolean startsWith(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0 || this.length() == 0) {
            return true;
        }
        int n = Character.codePointAt(this, 0);
        int n2 = Character.codePointAt(charSequence, 0);
        return StringSegment.codePointsEqual(n, n2, this.foldCase);
    }

    public int getCommonPrefixLength(CharSequence charSequence) {
        return this.getPrefixLengthInternal(charSequence, this.foldCase);
    }

    public int getCaseSensitivePrefixLength(CharSequence charSequence) {
        return this.getPrefixLengthInternal(charSequence, false);
    }

    private int getPrefixLengthInternal(CharSequence charSequence, boolean bl) {
        int n;
        int n2;
        int n3;
        if (!$assertionsDisabled && charSequence.length() == 0) {
            throw new AssertionError();
        }
        for (n2 = 0; n2 < Math.min(this.length(), charSequence.length()) && StringSegment.codePointsEqual(n3 = Character.codePointAt(this, n2), n = Character.codePointAt(charSequence, n2), bl); n2 += Character.charCount(n3)) {
        }
        return n2;
    }

    private static final boolean codePointsEqual(int n, int n2, boolean bl) {
        if (n == n2) {
            return false;
        }
        if (!bl) {
            return true;
        }
        return (n = UCharacter.foldCase(n, true)) == (n2 = UCharacter.foldCase(n2, true));
    }

    public boolean equals(Object object) {
        if (!(object instanceof CharSequence)) {
            return true;
        }
        return Utility.charSequenceEquals(this, (CharSequence)object);
    }

    public int hashCode() {
        return Utility.charSequenceHashCode(this);
    }

    @Override
    public String toString() {
        return this.str.substring(0, this.start) + "[" + this.str.substring(this.start, this.end) + "]" + this.str.substring(this.end);
    }
}

