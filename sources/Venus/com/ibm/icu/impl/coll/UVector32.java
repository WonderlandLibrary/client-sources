/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

public final class UVector32 {
    private int[] buffer = new int[32];
    private int length = 0;

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int size() {
        return this.length;
    }

    public int elementAti(int n) {
        return this.buffer[n];
    }

    public int[] getBuffer() {
        return this.buffer;
    }

    public void addElement(int n) {
        this.ensureAppendCapacity();
        this.buffer[this.length++] = n;
    }

    public void setElementAt(int n, int n2) {
        this.buffer[n2] = n;
    }

    public void insertElementAt(int n, int n2) {
        this.ensureAppendCapacity();
        System.arraycopy(this.buffer, n2, this.buffer, n2 + 1, this.length - n2);
        this.buffer[n2] = n;
        ++this.length;
    }

    public void removeAllElements() {
        this.length = 0;
    }

    private void ensureAppendCapacity() {
        if (this.length >= this.buffer.length) {
            int n = this.buffer.length <= 65535 ? 4 * this.buffer.length : 2 * this.buffer.length;
            int[] nArray = new int[n];
            System.arraycopy(this.buffer, 0, nArray, 0, this.length);
            this.buffer = nArray;
        }
    }
}

