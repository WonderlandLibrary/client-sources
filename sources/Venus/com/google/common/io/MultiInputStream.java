/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
final class MultiInputStream
extends InputStream {
    private Iterator<? extends ByteSource> it;
    private InputStream in;

    public MultiInputStream(Iterator<? extends ByteSource> iterator2) throws IOException {
        this.it = Preconditions.checkNotNull(iterator2);
        this.advance();
    }

    @Override
    public void close() throws IOException {
        if (this.in != null) {
            try {
                this.in.close();
            } finally {
                this.in = null;
            }
        }
    }

    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.in = this.it.next().openStream();
        }
    }

    @Override
    public int available() throws IOException {
        if (this.in == null) {
            return 1;
        }
        return this.in.available();
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        if (this.in == null) {
            return 1;
        }
        int n = this.in.read();
        if (n == -1) {
            this.advance();
            return this.read();
        }
        return n;
    }

    @Override
    public int read(@Nullable byte[] byArray, int n, int n2) throws IOException {
        if (this.in == null) {
            return 1;
        }
        int n3 = this.in.read(byArray, n, n2);
        if (n3 == -1) {
            this.advance();
            return this.read(byArray, n, n2);
        }
        return n3;
    }

    @Override
    public long skip(long l) throws IOException {
        if (this.in == null || l <= 0L) {
            return 0L;
        }
        long l2 = this.in.skip(l);
        if (l2 != 0L) {
            return l2;
        }
        if (this.read() == -1) {
            return 0L;
        }
        return 1L + this.in.skip(l - 1L);
    }
}

