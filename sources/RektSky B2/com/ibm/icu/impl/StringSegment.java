package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.lang.*;

public class StringSegment implements CharSequence
{
    private final String str;
    private int start;
    private int end;
    private boolean foldCase;
    
    public StringSegment(final String str, final boolean foldCase) {
        this.str = str;
        this.start = 0;
        this.end = str.length();
        this.foldCase = foldCase;
    }
    
    public int getOffset() {
        return this.start;
    }
    
    public void setOffset(final int start) {
        assert start <= this.end;
        this.start = start;
    }
    
    public void adjustOffset(final int delta) {
        assert this.start + delta >= 0;
        assert this.start + delta <= this.end;
        this.start += delta;
    }
    
    public void adjustOffsetByCodePoint() {
        this.start += Character.charCount(this.getCodePoint());
    }
    
    public void setLength(final int length) {
        assert length >= 0;
        assert this.start + length <= this.str.length();
        this.end = this.start + length;
    }
    
    public void resetLength() {
        this.end = this.str.length();
    }
    
    @Override
    public int length() {
        return this.end - this.start;
    }
    
    @Override
    public char charAt(final int index) {
        return this.str.charAt(index + this.start);
    }
    
    @Override
    public CharSequence subSequence(final int start, final int end) {
        return this.str.subSequence(start + this.start, end + this.start);
    }
    
    public int getCodePoint() {
        assert this.start < this.end;
        final char lead = this.str.charAt(this.start);
        final char trail;
        if (Character.isHighSurrogate(lead) && this.start + 1 < this.end && Character.isLowSurrogate(trail = this.str.charAt(this.start + 1))) {
            return Character.toCodePoint(lead, trail);
        }
        return lead;
    }
    
    public int codePointAt(final int index) {
        return this.str.codePointAt(this.start + index);
    }
    
    public boolean startsWith(final int otherCp) {
        return codePointsEqual(this.getCodePoint(), otherCp, this.foldCase);
    }
    
    public boolean startsWith(final UnicodeSet uniset) {
        final int cp = this.getCodePoint();
        return cp != -1 && uniset.contains(cp);
    }
    
    public boolean startsWith(final CharSequence other) {
        if (other == null || other.length() == 0 || this.length() == 0) {
            return false;
        }
        final int cp1 = Character.codePointAt(this, 0);
        final int cp2 = Character.codePointAt(other, 0);
        return codePointsEqual(cp1, cp2, this.foldCase);
    }
    
    public int getCommonPrefixLength(final CharSequence other) {
        return this.getPrefixLengthInternal(other, this.foldCase);
    }
    
    public int getCaseSensitivePrefixLength(final CharSequence other) {
        return this.getPrefixLengthInternal(other, false);
    }
    
    private int getPrefixLengthInternal(final CharSequence other, final boolean foldCase) {
        assert other.length() != 0;
        int offset;
        int cp1;
        for (offset = 0; offset < Math.min(this.length(), other.length()); offset += Character.charCount(cp1)) {
            cp1 = Character.codePointAt(this, offset);
            final int cp2 = Character.codePointAt(other, offset);
            if (!codePointsEqual(cp1, cp2, foldCase)) {
                break;
            }
        }
        return offset;
    }
    
    private static final boolean codePointsEqual(int cp1, int cp2, final boolean foldCase) {
        if (cp1 == cp2) {
            return true;
        }
        if (!foldCase) {
            return false;
        }
        cp1 = UCharacter.foldCase(cp1, true);
        cp2 = UCharacter.foldCase(cp2, true);
        return cp1 == cp2;
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof CharSequence && Utility.charSequenceEquals(this, (CharSequence)other);
    }
    
    @Override
    public int hashCode() {
        return Utility.charSequenceHashCode(this);
    }
    
    @Override
    public String toString() {
        return this.str.substring(0, this.start) + "[" + this.str.substring(this.start, this.end) + "]" + this.str.substring(this.end);
    }
}
