/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Beta
@GwtIncompatible
public final class CountingOutputStream
extends FilterOutputStream {
    private long count;

    public CountingOutputStream(OutputStream outputStream) {
        super(Preconditions.checkNotNull(outputStream));
    }

    public long getCount() {
        return this.count;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
        this.count += (long)n2;
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        ++this.count;
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}

