/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class CountingInputStream
extends ProxyInputStream {
    private long count;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public synchronized long skip(long l) throws IOException {
        long l2 = super.skip(l);
        this.count += l2;
        return l2;
    }

    @Override
    protected synchronized void afterRead(int n) {
        if (n != -1) {
            this.count += (long)n;
        }
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

