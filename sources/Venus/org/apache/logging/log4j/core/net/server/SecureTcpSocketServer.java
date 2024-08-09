/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.core.net.server.LogEventBridge;
import org.apache.logging.log4j.core.net.server.TcpSocketServer;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;

public class SecureTcpSocketServer<T extends InputStream>
extends TcpSocketServer<T> {
    public SecureTcpSocketServer(int n, LogEventBridge<T> logEventBridge, SslConfiguration sslConfiguration) throws IOException {
        super(n, logEventBridge, sslConfiguration.getSslServerSocketFactory().createServerSocket(n));
    }
}

