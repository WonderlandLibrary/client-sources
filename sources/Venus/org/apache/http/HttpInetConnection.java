/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.net.InetAddress;
import org.apache.http.HttpConnection;

public interface HttpInetConnection
extends HttpConnection {
    public InetAddress getLocalAddress();

    public int getLocalPort();

    public InetAddress getRemoteAddress();

    public int getRemotePort();
}

