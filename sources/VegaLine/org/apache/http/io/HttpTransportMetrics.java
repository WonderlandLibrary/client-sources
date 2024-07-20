/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.io;

public interface HttpTransportMetrics {
    public long getBytesTransferred();

    public void reset();
}

