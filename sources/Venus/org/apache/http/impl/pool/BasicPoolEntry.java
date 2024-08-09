/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.pool;

import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.pool.PoolEntry;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class BasicPoolEntry
extends PoolEntry<HttpHost, HttpClientConnection> {
    public BasicPoolEntry(String string, HttpHost httpHost, HttpClientConnection httpClientConnection) {
        super(string, httpHost, httpClientConnection);
    }

    @Override
    public void close() {
        try {
            HttpClientConnection httpClientConnection = (HttpClientConnection)this.getConnection();
            try {
                int n = httpClientConnection.getSocketTimeout();
                if (n <= 0 || n > 1000) {
                    httpClientConnection.setSocketTimeout(1000);
                }
                httpClientConnection.close();
            } catch (IOException iOException) {
                httpClientConnection.shutdown();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public boolean isClosed() {
        return !((HttpClientConnection)this.getConnection()).isOpen();
    }
}

