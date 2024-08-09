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
import org.apache.logging.log4j.Logger;
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
    private static final SslSocketManagerFactory FACTORY = new SslSocketManagerFactory(null);
    private final SslConfiguration sslConfig;

    public SslSocketManager(String string, OutputStream outputStream, Socket socket, SslConfiguration sslConfiguration, InetAddress inetAddress, String string2, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4) {
        super(string, outputStream, socket, inetAddress, string2, n, n2, n3, bl, layout, n4, null);
        this.sslConfig = sslConfiguration;
    }

    public SslSocketManager(String string, OutputStream outputStream, Socket socket, SslConfiguration sslConfiguration, InetAddress inetAddress, String string2, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
        super(string, outputStream, socket, inetAddress, string2, n, n2, n3, bl, layout, n4, socketOptions);
        this.sslConfig = sslConfiguration;
    }

    @Deprecated
    public static SslSocketManager getSocketManager(SslConfiguration sslConfiguration, String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4) {
        return SslSocketManager.getSocketManager(sslConfiguration, string, n, n2, n3, bl, layout, n4, null);
    }

    public static SslSocketManager getSocketManager(SslConfiguration sslConfiguration, String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
        if (Strings.isEmpty(string)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (n <= 0) {
            n = 6514;
        }
        if (n3 == 0) {
            n3 = 30000;
        }
        return (SslSocketManager)SslSocketManager.getManager("TLS:" + string + ':' + n, new SslFactoryData(sslConfiguration, string, n, n2, n3, bl, layout, n4, socketOptions), FACTORY);
    }

    @Override
    protected Socket createSocket(String string, int n) throws IOException {
        SSLSocketFactory sSLSocketFactory = SslSocketManager.createSslSocketFactory(this.sslConfig);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(string, n);
        Socket socket = sSLSocketFactory.createSocket();
        socket.connect(inetSocketAddress, this.getConnectTimeoutMillis());
        return socket;
    }

    private static SSLSocketFactory createSslSocketFactory(SslConfiguration sslConfiguration) {
        SSLSocketFactory sSLSocketFactory = sslConfiguration != null ? sslConfiguration.getSslSocketFactory() : (SSLSocketFactory)SSLSocketFactory.getDefault();
        return sSLSocketFactory;
    }

    static Logger access$300() {
        return LOGGER;
    }

    static Logger access$400() {
        return LOGGER;
    }

    static Logger access$1100() {
        return LOGGER;
    }

    static SSLSocketFactory access$1300(SslConfiguration sslConfiguration) {
        return SslSocketManager.createSslSocketFactory(sslConfiguration);
    }

    static class 1 {
    }

    private static class SslSocketManagerFactory
    implements ManagerFactory<SslSocketManager, SslFactoryData> {
        private SslSocketManagerFactory() {
        }

        @Override
        public SslSocketManager createManager(String string, SslFactoryData sslFactoryData) {
            InetAddress inetAddress = null;
            OutputStream outputStream = null;
            Socket socket = null;
            try {
                inetAddress = this.resolveAddress(SslFactoryData.access$100(sslFactoryData));
                socket = this.createSocket(sslFactoryData);
                outputStream = socket.getOutputStream();
                this.checkDelay(SslFactoryData.access$200(sslFactoryData), outputStream);
            } catch (IOException iOException) {
                SslSocketManager.access$300().error("SslSocketManager ({})", (Object)string, (Object)iOException);
                outputStream = new ByteArrayOutputStream();
            } catch (TlsSocketManagerFactoryException tlsSocketManagerFactoryException) {
                SslSocketManager.access$400().catching(Level.DEBUG, tlsSocketManagerFactoryException);
                Closer.closeSilently(socket);
                return null;
            }
            return new SslSocketManager(string, outputStream, socket, sslFactoryData.sslConfiguration, inetAddress, SslFactoryData.access$100(sslFactoryData), SslFactoryData.access$500(sslFactoryData), SslFactoryData.access$600(sslFactoryData), SslFactoryData.access$200(sslFactoryData), SslFactoryData.access$700(sslFactoryData), SslFactoryData.access$800(sslFactoryData), SslFactoryData.access$900(sslFactoryData), SslFactoryData.access$1000(sslFactoryData));
        }

        private InetAddress resolveAddress(String string) throws TlsSocketManagerFactoryException {
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(string);
            } catch (UnknownHostException unknownHostException) {
                SslSocketManager.access$1100().error("Could not find address of {}", (Object)string, (Object)unknownHostException);
                throw new TlsSocketManagerFactoryException(null);
            }
            return inetAddress;
        }

        private void checkDelay(int n, OutputStream outputStream) throws TlsSocketManagerFactoryException {
            if (n == 0 && outputStream == null) {
                throw new TlsSocketManagerFactoryException(null);
            }
        }

        private Socket createSocket(SslFactoryData sslFactoryData) throws IOException {
            SSLSocketFactory sSLSocketFactory = SslSocketManager.access$1300(sslFactoryData.sslConfiguration);
            SSLSocket sSLSocket = (SSLSocket)sSLSocketFactory.createSocket();
            SocketOptions socketOptions = SslFactoryData.access$1000(sslFactoryData);
            if (socketOptions != null) {
                socketOptions.apply(sSLSocket);
            }
            sSLSocket.connect(new InetSocketAddress(SslFactoryData.access$100(sslFactoryData), SslFactoryData.access$500(sslFactoryData)), SslFactoryData.access$600(sslFactoryData));
            if (socketOptions != null) {
                socketOptions.apply(sSLSocket);
            }
            return sSLSocket;
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (SslFactoryData)object);
        }

        SslSocketManagerFactory(1 var1_1) {
            this();
        }

        private static class TlsSocketManagerFactoryException
        extends Exception {
            private static final long serialVersionUID = 1L;

            private TlsSocketManagerFactoryException() {
            }

            TlsSocketManagerFactoryException(1 var1_1) {
                this();
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

        public SslFactoryData(SslConfiguration sslConfiguration, String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
            this.host = string;
            this.port = n;
            this.connectTimeoutMillis = n2;
            this.delayMillis = n3;
            this.immediateFail = bl;
            this.layout = layout;
            this.sslConfiguration = sslConfiguration;
            this.bufferSize = n4;
            this.socketOptions = socketOptions;
        }

        static String access$100(SslFactoryData sslFactoryData) {
            return sslFactoryData.host;
        }

        static int access$200(SslFactoryData sslFactoryData) {
            return sslFactoryData.delayMillis;
        }

        static int access$500(SslFactoryData sslFactoryData) {
            return sslFactoryData.port;
        }

        static int access$600(SslFactoryData sslFactoryData) {
            return sslFactoryData.connectTimeoutMillis;
        }

        static boolean access$700(SslFactoryData sslFactoryData) {
            return sslFactoryData.immediateFail;
        }

        static Layout access$800(SslFactoryData sslFactoryData) {
            return sslFactoryData.layout;
        }

        static int access$900(SslFactoryData sslFactoryData) {
            return sslFactoryData.bufferSize;
        }

        static SocketOptions access$1000(SslFactoryData sslFactoryData) {
            return sslFactoryData.socketOptions;
        }
    }
}

