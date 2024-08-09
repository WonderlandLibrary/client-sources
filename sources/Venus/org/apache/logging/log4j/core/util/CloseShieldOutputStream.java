/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.OutputStream;

public class CloseShieldOutputStream
extends OutputStream {
    private final OutputStream delegate;

    public CloseShieldOutputStream(OutputStream outputStream) {
        this.delegate = outputStream;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.delegate.write(byArray);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.delegate.write(byArray, n, n2);
    }

    @Override
    public void write(int n) throws IOException {
        this.delegate.write(n);
    }
}

