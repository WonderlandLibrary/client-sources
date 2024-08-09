/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ThresholdingOutputStream
extends OutputStream {
    private final int threshold;
    private long written;
    private boolean thresholdExceeded;

    public ThresholdingOutputStream(int n) {
        this.threshold = n;
    }

    @Override
    public void write(int n) throws IOException {
        this.checkThreshold(1);
        this.getStream().write(n);
        ++this.written;
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.checkThreshold(byArray.length);
        this.getStream().write(byArray);
        this.written += (long)byArray.length;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.checkThreshold(n2);
        this.getStream().write(byArray, n, n2);
        this.written += (long)n2;
    }

    @Override
    public void flush() throws IOException {
        this.getStream().flush();
    }

    @Override
    public void close() throws IOException {
        try {
            this.flush();
        } catch (IOException iOException) {
            // empty catch block
        }
        this.getStream().close();
    }

    public int getThreshold() {
        return this.threshold;
    }

    public long getByteCount() {
        return this.written;
    }

    public boolean isThresholdExceeded() {
        return this.written > (long)this.threshold;
    }

    protected void checkThreshold(int n) throws IOException {
        if (!this.thresholdExceeded && this.written + (long)n > (long)this.threshold) {
            this.thresholdExceeded = true;
            this.thresholdReached();
        }
    }

    protected void resetByteCount() {
        this.thresholdExceeded = false;
        this.written = 0L;
    }

    protected void setByteCount(long l) {
        this.written = l;
    }

    protected abstract OutputStream getStream() throws IOException;

    protected abstract void thresholdReached() throws IOException;
}

