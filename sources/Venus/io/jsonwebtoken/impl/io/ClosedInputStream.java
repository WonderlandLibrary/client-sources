/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import java.io.IOException;
import java.io.InputStream;

public final class ClosedInputStream
extends InputStream {
    public static final ClosedInputStream INSTANCE = new ClosedInputStream();

    private ClosedInputStream() {
    }

    @Override
    public int read() throws IOException {
        return 1;
    }
}

