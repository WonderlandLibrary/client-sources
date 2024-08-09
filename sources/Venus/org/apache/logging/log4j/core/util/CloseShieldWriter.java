/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.Writer;

public class CloseShieldWriter
extends Writer {
    private final Writer delegate;

    public CloseShieldWriter(Writer writer) {
        this.delegate = writer;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        this.delegate.write(cArray, n, n2);
    }
}

