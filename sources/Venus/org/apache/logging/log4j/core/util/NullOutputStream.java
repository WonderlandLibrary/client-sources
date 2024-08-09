/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream
extends OutputStream {
    private static final NullOutputStream INSTANCE;
    @Deprecated
    public static final NullOutputStream NULL_OUTPUT_STREAM;

    public static NullOutputStream getInstance() {
        return INSTANCE;
    }

    private NullOutputStream() {
    }

    @Override
    public void write(byte[] byArray, int n, int n2) {
    }

    @Override
    public void write(int n) {
    }

    @Override
    public void write(byte[] byArray) throws IOException {
    }

    static {
        NULL_OUTPUT_STREAM = INSTANCE = new NullOutputStream();
    }
}

