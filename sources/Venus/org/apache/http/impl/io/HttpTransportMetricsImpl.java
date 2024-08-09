/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import org.apache.http.io.HttpTransportMetrics;

public class HttpTransportMetricsImpl
implements HttpTransportMetrics {
    private long bytesTransferred = 0L;

    @Override
    public long getBytesTransferred() {
        return this.bytesTransferred;
    }

    public void setBytesTransferred(long l) {
        this.bytesTransferred = l;
    }

    public void incrementBytesTransferred(long l) {
        this.bytesTransferred += l;
    }

    @Override
    public void reset() {
        this.bytesTransferred = 0L;
    }
}

