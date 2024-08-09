/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.io.MeasurableInputStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;

public class FastByteArrayInputStream
extends MeasurableInputStream
implements RepositionableStream {
    public byte[] array;
    public int offset;
    public int length;
    private int position;
    private int mark;

    public FastByteArrayInputStream(byte[] byArray, int n, int n2) {
        this.array = byArray;
        this.offset = n;
        this.length = n2;
    }

    public FastByteArrayInputStream(byte[] byArray) {
        this(byArray, 0, byArray.length);
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void reset() {
        this.position = this.mark;
    }

    @Override
    public void close() {
    }

    @Override
    public void mark(int n) {
        this.mark = this.position;
    }

    @Override
    public int available() {
        return this.length - this.position;
    }

    @Override
    public long skip(long l) {
        if (l <= (long)(this.length - this.position)) {
            this.position += (int)l;
            return l;
        }
        l = this.length - this.position;
        this.position = this.length;
        return l;
    }

    @Override
    public int read() {
        if (this.length == this.position) {
            return 1;
        }
        return this.array[this.offset + this.position++] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) {
        if (this.length == this.position) {
            return n2 == 0 ? 0 : -1;
        }
        int n3 = Math.min(n2, this.length - this.position);
        System.arraycopy(this.array, this.offset + this.position, byArray, n, n3);
        this.position += n3;
        return n3;
    }

    @Override
    public long position() {
        return this.position;
    }

    @Override
    public void position(long l) {
        this.position = (int)Math.min(l, (long)this.length);
    }

    @Override
    public long length() {
        return this.length;
    }
}

