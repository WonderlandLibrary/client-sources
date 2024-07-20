/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.Writer;

public class CloseShieldWriter
extends Writer {
    private final Writer delegate;

    public CloseShieldWriter(Writer delegate) {
        this.delegate = delegate;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        this.delegate.write(cbuf, off, len);
    }
}

