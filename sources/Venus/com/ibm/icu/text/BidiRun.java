/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

public class BidiRun {
    int start;
    int limit;
    int insertRemove;
    byte level;

    BidiRun() {
        this(0, 0, 0);
    }

    BidiRun(int n, int n2, byte by) {
        this.start = n;
        this.limit = n2;
        this.level = by;
    }

    void copyFrom(BidiRun bidiRun) {
        this.start = bidiRun.start;
        this.limit = bidiRun.limit;
        this.level = bidiRun.level;
        this.insertRemove = bidiRun.insertRemove;
    }

    public int getStart() {
        return this.start;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getLength() {
        return this.limit - this.start;
    }

    public byte getEmbeddingLevel() {
        return this.level;
    }

    public boolean isOddRun() {
        return (this.level & 1) == 1;
    }

    public boolean isEvenRun() {
        return (this.level & 1) == 0;
    }

    public byte getDirection() {
        return (byte)(this.level & 1);
    }

    public String toString() {
        return "BidiRun " + this.start + " - " + this.limit + " @ " + this.level;
    }
}

