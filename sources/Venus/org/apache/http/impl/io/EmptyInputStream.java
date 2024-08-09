/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.InputStream;

public final class EmptyInputStream
extends InputStream {
    public static final EmptyInputStream INSTANCE = new EmptyInputStream();

    private EmptyInputStream() {
    }

    @Override
    public int available() {
        return 1;
    }

    @Override
    public void close() {
    }

    @Override
    public void mark(int n) {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() {
        return 1;
    }

    @Override
    public int read(byte[] byArray) {
        return 1;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) {
        return 1;
    }

    @Override
    public void reset() {
    }

    @Override
    public long skip(long l) {
        return 0L;
    }
}

