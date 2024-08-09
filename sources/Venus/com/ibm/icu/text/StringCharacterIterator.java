/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.text.CharacterIterator;

@Deprecated
public final class StringCharacterIterator
implements CharacterIterator {
    private String text;
    private int begin;
    private int end;
    private int pos;

    @Deprecated
    public StringCharacterIterator(String string) {
        this(string, 0);
    }

    @Deprecated
    public StringCharacterIterator(String string, int n) {
        this(string, 0, string.length(), n);
    }

    @Deprecated
    public StringCharacterIterator(String string, int n, int n2, int n3) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.text = string;
        if (n < 0 || n > n2 || n2 > string.length()) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        if (n3 < n || n3 > n2) {
            throw new IllegalArgumentException("Invalid position");
        }
        this.begin = n;
        this.end = n2;
        this.pos = n3;
    }

    @Deprecated
    public void setText(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.text = string;
        this.begin = 0;
        this.end = string.length();
        this.pos = 0;
    }

    @Override
    @Deprecated
    public char first() {
        this.pos = this.begin;
        return this.current();
    }

    @Override
    @Deprecated
    public char last() {
        this.pos = this.end != this.begin ? this.end - 1 : this.end;
        return this.current();
    }

    @Override
    @Deprecated
    public char setIndex(int n) {
        if (n < this.begin || n > this.end) {
            throw new IllegalArgumentException("Invalid index");
        }
        this.pos = n;
        return this.current();
    }

    @Override
    @Deprecated
    public char current() {
        if (this.pos >= this.begin && this.pos < this.end) {
            return this.text.charAt(this.pos);
        }
        return '\u0000';
    }

    @Override
    @Deprecated
    public char next() {
        if (this.pos < this.end - 1) {
            ++this.pos;
            return this.text.charAt(this.pos);
        }
        this.pos = this.end;
        return '\u0000';
    }

    @Override
    @Deprecated
    public char previous() {
        if (this.pos > this.begin) {
            --this.pos;
            return this.text.charAt(this.pos);
        }
        return '\u0000';
    }

    @Override
    @Deprecated
    public int getBeginIndex() {
        return this.begin;
    }

    @Override
    @Deprecated
    public int getEndIndex() {
        return this.end;
    }

    @Override
    @Deprecated
    public int getIndex() {
        return this.pos;
    }

    @Deprecated
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof StringCharacterIterator)) {
            return true;
        }
        StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)object;
        if (this.hashCode() != stringCharacterIterator.hashCode()) {
            return true;
        }
        if (!this.text.equals(stringCharacterIterator.text)) {
            return true;
        }
        return this.pos != stringCharacterIterator.pos || this.begin != stringCharacterIterator.begin || this.end != stringCharacterIterator.end;
    }

    @Deprecated
    public int hashCode() {
        return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
    }

    @Override
    @Deprecated
    public Object clone() {
        try {
            StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)super.clone();
            return stringCharacterIterator;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }
}

