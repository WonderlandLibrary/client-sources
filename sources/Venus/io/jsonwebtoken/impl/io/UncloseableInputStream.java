/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.ClosedInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

public final class UncloseableInputStream
extends FilterInputStream {
    public UncloseableInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public void close() {
        this.in = ClosedInputStream.INSTANCE;
    }
}

