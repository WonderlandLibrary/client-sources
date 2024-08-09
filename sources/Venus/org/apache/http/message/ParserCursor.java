/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

public class ParserCursor {
    private final int lowerBound;
    private final int upperBound;
    private int pos;

    public ParserCursor(int n, int n2) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        }
        if (n > n2) {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
        this.lowerBound = n;
        this.upperBound = n2;
        this.pos = n;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public int getPos() {
        return this.pos;
    }

    public void updatePos(int n) {
        if (n < this.lowerBound) {
            throw new IndexOutOfBoundsException("pos: " + n + " < lowerBound: " + this.lowerBound);
        }
        if (n > this.upperBound) {
            throw new IndexOutOfBoundsException("pos: " + n + " > upperBound: " + this.upperBound);
        }
        this.pos = n;
    }

    public boolean atEnd() {
        return this.pos >= this.upperBound;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(Integer.toString(this.lowerBound));
        stringBuilder.append('>');
        stringBuilder.append(Integer.toString(this.pos));
        stringBuilder.append('>');
        stringBuilder.append(Integer.toString(this.upperBound));
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

