/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.sevenz.Coder;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
abstract class CoderBase {
    private final Class<?>[] acceptableOptions;
    private static final byte[] NONE = new byte[0];

    protected CoderBase(Class<?> ... classArray) {
        this.acceptableOptions = classArray;
    }

    boolean canAcceptOptions(Object object) {
        for (Class<?> clazz : this.acceptableOptions) {
            if (!clazz.isInstance(object)) continue;
            return false;
        }
        return true;
    }

    byte[] getOptionsAsProperties(Object object) {
        return NONE;
    }

    Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return null;
    }

    abstract InputStream decode(InputStream var1, Coder var2, byte[] var3) throws IOException;

    OutputStream encode(OutputStream outputStream, Object object) throws IOException {
        throw new UnsupportedOperationException("method doesn't support writing");
    }

    protected static int numberOptionOrDefault(Object object, int n) {
        return object instanceof Number ? ((Number)object).intValue() : n;
    }
}

