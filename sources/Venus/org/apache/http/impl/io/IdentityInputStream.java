/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

public class IdentityInputStream
extends InputStream {
    private final SessionInputBuffer in;
    private boolean closed = false;

    public IdentityInputStream(SessionInputBuffer sessionInputBuffer) {
        this.in = Args.notNull(sessionInputBuffer, "Session input buffer");
    }

    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return ((BufferInfo)((Object)this.in)).length();
        }
        return 1;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    @Override
    public int read() throws IOException {
        return this.closed ? -1 : this.in.read();
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        return this.closed ? -1 : this.in.read(byArray, n, n2);
    }
}

