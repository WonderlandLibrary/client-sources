/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.net.SocketOptions;
import org.apache.logging.log4j.core.net.TcpSocketManager;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.util.Strings;

public class SslSocketManager
extends TcpSocketManager {
    public static final int DEFAULT_PORT = 6514;
    private static final SslSocketManagerFactory FACTORY = new SslSocketManagerFactory();
    private final SslConfiguration sslConfig;

    public SslSocketManager(String name, OutputStream os, Socket sock, SslConfiguration sslConfig, InetAddress inetAddress, String host, int port, int connectTimeoutMillis, int delay, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize) {
        super(name, os, sock, inetAddress, host, port, connectTimeoutMillis, delay, immediateFail, layout, bufferSize, null);
        this.sslConfig = sslConfig;
    }

    public SslSocketManager(String name, OutputStream os, Socket sock, SslConfiguration sslConfig, InetAddress inetAddress, String host, int port, int connectTimeoutMillis, int delay, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize, SocketOptions socketOptions) {
        super(name, os, sock, inetAddress, host, port, connectTimeoutMillis, delay, immediateFail, layout, bufferSize, socketOptions);
        this.sslConfig = sslConfig;
    }

    @Deprecated
    public static SslSocketManager getSocketManager(SslConfiguration sslConfig, String host, int port, int connectTimeoutMillis, int reconnectDelayMillis, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize) {
        return SslSocketManager.getSocketManager(sslConfig, host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, null);
    }

    public static SslSocketManager getSocketManager(SslConfiguration sslConfig, String host, int port, int connectTimeoutMillis, int reconnectDelayMillis, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize, SocketOptions socketOptions) {
        if (Strings.isEmpty(host)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (port <= 0) {
            port = 6514;
        }
        if (reconnectDelayMillis == 0) {
            reconnectDelayMillis = 30000;
        }
        return (SslSocketManager)SslSocketManager.getManager("TLS:" + host + ':' + port, new SslFactoryData(sslConfig, host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, socketOptions), FACTORY);
    }

    @Override
    protected Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory socketFactory = SslSocketManager.createSslSocketFactory(this.sslConfig);
        InetSocketAddress address = new InetSocketAddress(host, port);
        Socket newSocket = socketFactory.createSocket();
        newSocket.connect(address, this.getConnectTimeoutMillis());
        return newSocket;
    }

    private static SSLSocketFactory createSslSocketFactory(SslConfiguration sslConf) {
        SSLSocketFactory socketFactory = sslConf != null ? sslConf.getSslSocketFactory() : (SSLSocketFactory)SSLSocketFactory.getDefault();
        return socketFactory;
    }

    private static class SslSocketManagerFactory
    implements ManagerFactory<SslSocketManager, SslFactoryData> {
        private SslSocketManagerFactory() {
        }

        @Override
        public SslSocketManager createManager(String name, SslFactoryData data) {
            InetAddress inetAddress = null;
            OutputStream os = null;
            Socket socket = null;
            try {
                inetAddress = this.resolveAddress(data.host);
                socket = this.createSocket(data);
                os = socket.getOutputStream();
                this.checkDelay(data.delayMillis, os);
            } catch (IOException e) {
                LOGGER.error("SslSocketManager ({})", (Object)name, (Object)e);
                os = new ByteArrayOutputStream();
            } catch (TlsSocketManagerFactoryException e) {
                LOGGER.catching(Level.DEBUG, e);
                Closer.closeSilently(socket);
                return null;
            }
            return new SslSocketManager(name, os, socket, data.sslConfiguration, inetAddress, data.host, data.port, data.connectTimeoutMillis, data.delayMillis, data.immediateFail, data.layout, data.bufferSize, data.socketOptions);
        }

        private InetAddress resolveAddress(String hostName) throws TlsSocketManagerFactoryException {
            InetAddress address;
            try {
                address = InetAddress.getByName(hostName);
            } catch (UnknownHostException ex) {
                LOGGER.error("Could not find address of {}", (Object)hostName, (Object)ex);
                throw new TlsSocketManagerFactoryException();
            }
            return address;
        }

        private void checkDelay(int delay, OutputStream os) throws TlsSocketManagerFactoryException {
            if (delay == 0 && os == null) {
                throw new TlsSocketManagerFactoryException();
            }
        }

        private Socket createSocket(SslFactoryData data) throws IOException {
            SSLSocketFactory socketFactory = SslSocketManager.createSslSocketFactory(data.sslConfiguration);
            SSLSocket socket = (SSLSocket)socketFactory.createSocket();
            SocketOptions socketOptions = data.socketOptions;
            if (socketOptions != null) {
                socketOptions.apply(socket);
            }
            socket.connect(new InetSocketAddress(data.host, data.port), data.connectTimeoutMillis);
            if (socketOptions != null) {
                socketOptions.apply(socket);
            }
            return socket;
        }

        private static class TlsSocketManagerFactoryException
        extends Exception {
            private static final long serialVersionUID = 1L;

            private TlsSocketManagerFactoryException() {
            }
        }
    }

    private static class SslFactoryData {
        protected SslConfiguration sslConfiguration;
        private final String host;
        private final int port;
        private final int connectTimeoutMillis;
        private final int delayMillis;
        private final boolean immediateFail;
        private final Layout<? extends Serializable> layout;
        private final int bufferSize;
        private final SocketOptions socketOptions;

        public SslFactoryData(SslConfiguration sslConfiguration, String host, int port, int connectTimeoutMillis, int delayMillis, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize, SocketOptions socketOptions) {
            this.host = host;
            this.port = port;
            this.connectTimeoutMillis = connectTimeoutMillis;
            this.delayMillis = delayMillis;
            this.immediateFail = immediateFail;
            this.layout = layout;
            this.sslConfiguration = sslConfiguration;
            this.bufferSize = bufferSize;
            this.socketOptions = socketOptions;
        }
    }
}

