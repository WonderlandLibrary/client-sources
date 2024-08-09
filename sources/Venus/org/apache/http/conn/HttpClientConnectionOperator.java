/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.http.HttpHost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.protocol.HttpContext;

public interface HttpClientConnectionOperator {
    public void connect(ManagedHttpClientConnection var1, HttpHost var2, InetSocketAddress var3, int var4, SocketConfig var5, HttpContext var6) throws IOException;

    public void upgrade(ManagedHttpClientConnection var1, HttpHost var2, HttpContext var3) throws IOException;
}

