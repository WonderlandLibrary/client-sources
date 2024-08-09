/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteratorWrapper;
import com.ibm.icu.impl.ReplaceableUCharacterIterator;
import com.ibm.icu.impl.UCharArrayIterator;
import com.ibm.icu.impl.UCharacterIteratorWrapper;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UForwardCharacterIterator;
import com.ibm.icu.text.UTF16;
import java.text.CharacterIterator;

public abstract class UCharacterIterator
implements Cloneable,
UForwardCharacterIterator {
    protected UCharacterIterator() {
    }

    public static final UCharacterIterator getInstance(Replaceable replaceable) {
        return new ReplaceableUCharacterIterator(replaceable);
    }

    public static final UCharacterIterator getInstance(String string) {
        return new ReplaceableUCharacterIterator(string);
    }

    public static final UCharacterIterator getInstance(char[] cArray) {
        return UCharacterIterator.getInstance(cArray, 0, cArray.length);
    }

    public static final UCharacterIterator getInstance(char[] cArray, int n, int n2) {
        return new UCharArrayIterator(cArray, n, n2);
    }

    public static final UCharacterIterator getInstance(StringBuffer stringBuffer) {
        return new ReplaceableUCharacterIterator(stringBuffer);
    }

    public static final UCharacterIterator getInstance(CharacterIterator characterIterator) {
        return new CharacterIteratorWrapper(characterIterator);
    }

    public CharacterIterator getCharacterIterator() {
        return new UCharacterIteratorWrapper(this);
    }

    public abstract int current();

    public int currentCodePoint() {
        int n = this.current();
        if (UTF16.isLeadSurrogate((char)n)) {
            this.next();
            int n2 = this.current();
            this.previous();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
        }
        return n;
    }

    public abstract int getLength();

    public abstract int getIndex();

    @Override
    public abstract int next();

    @Override
    public int nextCodePoint() {
        int n = this.next();
        if (UTF16.isLeadSurrogate((char)n)) {
            int n2 = this.next();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
            if (n2 != -1) {
                this.previous();
            }
        }
        return n;
    }

    public abstract int previous();

    public int previousCodePoint() {
        int n = this.previous();
        if (UTF16.isTrailSurrogate((char)n)) {
            int n2 = this.previous();
            if (UTF16.isLeadSurrogate((char)n2)) {
                return Character.toCodePoint((char)n2, (char)n);
            }
            if (n2 != -1) {
                this.next();
            }
        }
        return n;
    }

    public abstract void setIndex(int var1);

    public void setToLimit() {
        this.setIndex(this.getLength());
    }

    public void setToStart() {
        this.setIndex(0);
    }

    public abstract int getText(char[] var1, int var2);

    public final int getText(char[] cArray) {
        return this.getText(cArray, 0);
    }

    public String getText() {
        char[] cArray = new char[this.getLength()];
        this.getText(cArray);
        return new String(cArray);
    }

    public int moveIndex(int n) {
        int n2 = Math.max(0, Math.min(this.getIndex() + n, this.getLength()));
        this.setIndex(n2);
        return n2;
    }

    public int moveCodePointIndex(int n) {
        if (n > 0) {
            while (n > 0 && this.nextCodePoint() != -1) {
                --n;
            }
        } else {
            while (n < 0 && this.previousCodePoint() != -1) {
                ++n;
            }
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.getIndex();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

