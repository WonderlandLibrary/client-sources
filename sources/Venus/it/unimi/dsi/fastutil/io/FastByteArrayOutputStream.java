/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.io.MeasurableOutputStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;
import java.io.IOException;

public class FastByteArrayOutputStream
extends MeasurableOutputStream
implements RepositionableStream {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public byte[] array;
    public int length;
    private int position;

    public FastByteArrayOutputStream() {
        this(16);
    }

    public FastByteArrayOutputStream(int n) {
        this.array = new byte[n];
    }

    public FastByteArrayOutputStream(byte[] byArray) {
        this.array = byArray;
    }

    public void reset() {
        this.length = 0;
        this.position = 0;
    }

    public void trim() {
        this.array = ByteArrays.trim(this.array, this.length);
    }

    @Override
    public void write(int n) {
        if (this.position >= this.array.length) {
            this.array = ByteArrays.grow(this.array, this.position + 1, this.length);
        }
        this.array[this.position++] = (byte)n;
        if (this.length < this.position) {
            this.length = this.position;
        }
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        if (this.position + n2 > this.array.length) {
            this.array = ByteArrays.grow(this.array, this.position + n2, this.position);
        }
        System.arraycopy(byArray, n, this.array, this.position, n2);
        if (this.position + n2 > this.length) {
            this.length = this.position += n2;
        }
    }

    @Override
    public void position(long l) {
        if (this.position > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Position too large: " + l);
        }
        this.position = (int)l;
    }

    @Override
    public long position() {
        return this.position;
    }

    @Override
    public long length() throws IOException {
        return this.length;
    }
}

