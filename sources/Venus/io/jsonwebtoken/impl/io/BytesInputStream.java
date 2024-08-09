/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.lang.Bytes;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public final class BytesInputStream
extends ByteArrayInputStream {
    BytesInputStream(byte[] byArray) {
        super(Bytes.isEmpty(byArray) ? Bytes.EMPTY : byArray);
    }

    public byte[] getBytes() {
        return this.buf;
    }

    @Override
    public void close() throws IOException {
        this.reset();
    }
}

