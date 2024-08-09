/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

class CircularBuffer {
    private final int size;
    private final byte[] buffer;
    private int readIndex;
    private int writeIndex;

    CircularBuffer(int n) {
        this.size = n;
        this.buffer = new byte[n];
    }

    public boolean available() {
        return this.readIndex != this.writeIndex;
    }

    public void put(int n) {
        this.buffer[this.writeIndex] = (byte)n;
        this.writeIndex = (this.writeIndex + 1) % this.size;
    }

    public int get() {
        if (this.available()) {
            byte by = this.buffer[this.readIndex];
            this.readIndex = (this.readIndex + 1) % this.size;
            return by & 0xFF;
        }
        return 1;
    }

    public void copy(int n, int n2) {
        int n3 = this.writeIndex - n;
        int n4 = n3 + n2;
        for (int i = n3; i < n4; ++i) {
            this.buffer[this.writeIndex] = this.buffer[(i + this.size) % this.size];
            this.writeIndex = (this.writeIndex + 1) % this.size;
        }
    }
}

