/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.OutputStream;
import org.apache.commons.io.output.ProxyOutputStream;

public class CountingOutputStream
extends ProxyOutputStream {
    private long count = 0L;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected synchronized void beforeWrite(int n) {
        this.count += (long)n;
    }

    public int getCount() {
        long l = this.getByteCount();
        if (l > Integer.MAX_VALUE) {
            throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
        }
        return (int)l;
    }

    public int resetCount() {
        long l = this.resetByteCount();
        if (l > Integer.MAX_VALUE) {
            throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
        }
        return (int)l;
    }

    public synchronized long getByteCount() {
        return this.count;
    }

    public synchronized long resetByteCount() {
        long l = this.count;
        this.count = 0L;
        return l;
    }
}

