/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

public final class UVector64 {
    private long[] buffer = new long[32];
    private int length = 0;

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int size() {
        return this.length;
    }

    public long elementAti(int n) {
        return this.buffer[n];
    }

    public long[] getBuffer() {
        return this.buffer;
    }

    public void addElement(long l) {
        this.ensureAppendCapacity();
        this.buffer[this.length++] = l;
    }

    public void setElementAt(long l, int n) {
        this.buffer[n] = l;
    }

    public void insertElementAt(long l, int n) {
        this.ensureAppendCapacity();
        System.arraycopy(this.buffer, n, this.buffer, n + 1, this.length - n);
        this.buffer[n] = l;
        ++this.length;
    }

    public void removeAllElements() {
        this.length = 0;
    }

    private void ensureAppendCapacity() {
        if (this.length >= this.buffer.length) {
            int n = this.buffer.length <= 65535 ? 4 * this.buffer.length : 2 * this.buffer.length;
            long[] lArray = new long[n];
            System.arraycopy(this.buffer, 0, lArray, 0, this.length);
            this.buffer = lArray;
        }
    }
}

