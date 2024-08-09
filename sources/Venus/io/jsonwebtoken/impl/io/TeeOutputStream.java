/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.FilteredOutputStream;
import io.jsonwebtoken.lang.Assert;
import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream
extends FilteredOutputStream {
    private final OutputStream other;

    public TeeOutputStream(OutputStream outputStream, OutputStream outputStream2) {
        super(outputStream);
        this.other = Assert.notNull(outputStream2, "Second OutputStream cannot be null.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            this.other.close();
        }
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        this.other.flush();
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        super.write(byArray);
        this.other.write(byArray);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        super.write(byArray, n, n2);
        this.other.write(byArray, n, n2);
    }

    @Override
    public void write(int n) throws IOException {
        super.write(n);
        this.other.write(n);
    }
}

