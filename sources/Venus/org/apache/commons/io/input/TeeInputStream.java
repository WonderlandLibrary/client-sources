/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class TeeInputStream
extends ProxyInputStream {
    private final OutputStream branch;
    private final boolean closeBranch;

    public TeeInputStream(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, outputStream, false);
    }

    public TeeInputStream(InputStream inputStream, OutputStream outputStream, boolean bl) {
        super(inputStream);
        this.branch = outputStream;
        this.closeBranch = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (this.closeBranch) {
                this.branch.close();
            }
        }
    }

    @Override
    public int read() throws IOException {
        int n = super.read();
        if (n != -1) {
            this.branch.write(n);
        }
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = super.read(byArray, n, n2);
        if (n3 != -1) {
            this.branch.write(byArray, n, n3);
        }
        return n3;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        int n = super.read(byArray);
        if (n != -1) {
            this.branch.write(byArray, 0, n);
        }
        return n;
    }
}

