/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

public class CountingInputStream
extends FilterInputStream {
    private final AtomicLong count = new AtomicLong(0L);

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public long getCount() {
        return this.count.get();
    }

    private void add(long l) {
        if (l > 0L) {
            this.count.addAndGet(l);
        }
    }

    @Override
    public int read() throws IOException {
        int n = super.read();
        this.add(n == -1 ? -1L : 1L);
        return n;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        int n = super.read(byArray);
        this.add(n);
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = super.read(byArray, n, n2);
        this.add(n3);
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        long l2 = super.skip(l);
        this.add(l2);
        return l2;
    }
}

