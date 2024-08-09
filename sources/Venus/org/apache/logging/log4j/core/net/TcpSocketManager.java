/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.SocketOptions;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.core.util.NullOutputStream;
import org.apache.logging.log4j.util.Strings;

public class TcpSocketManager
extends AbstractSocketManager {
    public static final int DEFAULT_RECONNECTION_DELAY_MILLIS = 30000;
    private static final int DEFAULT_PORT = 4560;
    private static final TcpSocketManagerFactory FACTORY = new TcpSocketManagerFactory();
    private final int reconnectionDelay;
    private Reconnector reconnector;
    private Socket socket;
    private final SocketOptions socketOptions;
    private final boolean retry;
    private final boolean immediateFail;
    private final int connectTimeoutMillis;

    @Deprecated
    public TcpSocketManager(String string, OutputStream outputStream, Socket socket, InetAddress inetAddress, String string2, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4) {
        this(string, outputStream, socket, inetAddress, string2, n, n2, n3, bl, layout, n4, null);
    }

    public TcpSocketManager(String string, OutputStream outputStream, Socket socket, InetAddress inetAddress, String string2, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
        super(string, outputStream, inetAddress, string2, n, layout, true, n4);
        this.connectTimeoutMillis = n2;
        this.reconnectionDelay = n3;
        this.socket = socket;
        this.immediateFail = bl;
        boolean bl2 = this.retry = n3 > 0;
        if (socket == null) {
            this.reconnector = this.createReconnector();
            this.reconnector.start();
        }
        this.socketOptions = socketOptions;
    }

    @Deprecated
    public static TcpSocketManager getSocketManager(String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4) {
        return TcpSocketManager.getSocketManager(string, n, n2, n3, bl, layout, n4, null);
    }

    public static TcpSocketManager getSocketManager(String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
        if (Strings.isEmpty(string)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (n <= 0) {
            n = 4560;
        }
        if (n3 == 0) {
            n3 = 30000;
        }
        return (TcpSocketManager)TcpSocketManager.getManager("TCP:" + string + ':' + n, new FactoryData(string, n, n2, n3, bl, layout, n4, socketOptions), FACTORY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void write(byte[] byArray, int n, int n2, boolean bl) {
        if (this.socket == null) {
            if (this.reconnector != null && !this.immediateFail) {
                this.reconnector.latch();
            }
            if (this.socket == null) {
                String string = "Error writing to " + this.getName() + " socket not available";
                throw new AppenderLoggingException(string);
            }
        }
        TcpSocketManager tcpSocketManager = this;
        synchronized (tcpSocketManager) {
            try {
                OutputStream outputStream = this.getOutputStream();
                outputStream.write(byArray, n, n2);
                if (bl) {
                    outputStream.flush();
                }
            } catch (IOException iOException) {
                if (this.retry && this.reconnector == null) {
                    this.reconnector = this.createReconnector();
                    this.reconnector.start();
                }
                String string = "Error writing to " + this.getName();
                throw new AppenderLoggingException(string, iOException);
            }
        }
    }

    @Override
    protected synchronized boolean closeOutputStream() {
        boolean bl = super.closeOutputStream();
        if (this.reconnector != null) {
            this.reconnector.shutdown();
            this.reconnector.interrupt();
            this.reconnector = null;
        }
        Socket socket = this.socket;
        this.socket = null;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException iOException) {
                LOGGER.error("Could not close socket {}", (Object)this.socket);
                return true;
            }
        }
        return bl;
    }

    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("protocol", "tcp");
        hashMap.put("direction", "out");
        return hashMap;
    }

    private Reconnector createReconnector() {
        Reconnector reconnector = new Reconnector(this, this);
        reconnector.setDaemon(false);
        reconnector.setPriority(1);
        return reconnector;
    }

    protected Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        return this.createSocket(inetAddress.getHostName(), n);
    }

    protected Socket createSocket(String string, int n) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(string, n), this.connectTimeoutMillis);
        if (this.socketOptions != null) {
            this.socketOptions.apply(socket);
        }
        return socket;
    }

    public SocketOptions getSocketOptions() {
        return this.socketOptions;
    }

    public Socket getSocket() {
        return this.socket;
    }

    static int access$000(TcpSocketManager tcpSocketManager) {
        return tcpSocketManager.reconnectionDelay;
    }

    static OutputStream access$100(TcpSocketManager tcpSocketManager) throws IOException {
        return tcpSocketManager.getOutputStream();
    }

    static void access$200(TcpSocketManager tcpSocketManager, OutputStream outputStream) {
        tcpSocketManager.setOutputStream(outputStream);
    }

    static Socket access$302(TcpSocketManager tcpSocketManager, Socket socket) {
        tcpSocketManager.socket = socket;
        return tcpSocketManager.socket;
    }

    static Reconnector access$402(TcpSocketManager tcpSocketManager, Reconnector reconnector) {
        tcpSocketManager.reconnector = reconnector;
        return tcpSocketManager.reconnector;
    }

    static Logger access$500() {
        return LOGGER;
    }

    static Logger access$600() {
        return LOGGER;
    }

    static Logger access$700() {
        return LOGGER;
    }

    static Logger access$800() {
        return LOGGER;
    }

    static Logger access$1000() {
        return LOGGER;
    }

    static Logger access$1800() {
        return LOGGER;
    }

    protected static class TcpSocketManagerFactory
    implements ManagerFactory<TcpSocketManager, FactoryData> {
        protected TcpSocketManagerFactory() {
        }

        @Override
        public TcpSocketManager createManager(String string, FactoryData factoryData) {
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(FactoryData.access$900(factoryData));
            } catch (UnknownHostException unknownHostException) {
                TcpSocketManager.access$1000().error("Could not find address of " + FactoryData.access$900(factoryData), (Object)unknownHostException, (Object)unknownHostException);
                return null;
            }
            Socket socket = null;
            try {
                socket = TcpSocketManagerFactory.createSocket(factoryData);
                OutputStream outputStream = socket.getOutputStream();
                return new TcpSocketManager(string, outputStream, socket, inetAddress, FactoryData.access$900(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), FactoryData.access$1300(factoryData), FactoryData.access$1400(factoryData), FactoryData.access$1500(factoryData), FactoryData.access$1600(factoryData), FactoryData.access$1700(factoryData));
            } catch (IOException iOException) {
                TcpSocketManager.access$1800().error("TcpSocketManager (" + string + ") " + iOException, (Throwable)iOException);
                NullOutputStream nullOutputStream = NullOutputStream.getInstance();
                if (FactoryData.access$1300(factoryData) == 0) {
                    Closer.closeSilently(socket);
                    return null;
                }
                return new TcpSocketManager(string, nullOutputStream, null, inetAddress, FactoryData.access$900(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), FactoryData.access$1300(factoryData), FactoryData.access$1400(factoryData), FactoryData.access$1500(factoryData), FactoryData.access$1600(factoryData), FactoryData.access$1700(factoryData));
            }
        }

        static Socket createSocket(FactoryData factoryData) throws IOException, SocketException {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(FactoryData.access$900(factoryData), FactoryData.access$1100(factoryData)), FactoryData.access$1200(factoryData));
            SocketOptions socketOptions = FactoryData.access$1700(factoryData);
            if (socketOptions != null) {
                socketOptions.apply(socket);
            }
            return socket;
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }
    }

    private static class FactoryData {
        private final String host;
        private final int port;
        private final int connectTimeoutMillis;
        private final int reconnectDelayMillis;
        private final boolean immediateFail;
        private final Layout<? extends Serializable> layout;
        private final int bufferSize;
        private final SocketOptions socketOptions;

        public FactoryData(String string, int n, int n2, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
            this.host = string;
            this.port = n;
            this.connectTimeoutMillis = n2;
            this.reconnectDelayMillis = n3;
            this.immediateFail = bl;
            this.layout = layout;
            this.bufferSize = n4;
            this.socketOptions = socketOptions;
        }

        static String access$900(FactoryData factoryData) {
            return factoryData.host;
        }

        static int access$1100(FactoryData factoryData) {
            return factoryData.port;
        }

        static int access$1200(FactoryData factoryData) {
            return factoryData.connectTimeoutMillis;
        }

        static int access$1300(FactoryData factoryData) {
            return factoryData.reconnectDelayMillis;
        }

        static boolean access$1400(FactoryData factoryData) {
            return factoryData.immediateFail;
        }

        static Layout access$1500(FactoryData factoryData) {
            return factoryData.layout;
        }

        static int access$1600(FactoryData factoryData) {
            return factoryData.bufferSize;
        }

        static SocketOptions access$1700(FactoryData factoryData) {
            return factoryData.socketOptions;
        }
    }

    private class Reconnector
    extends Log4jThread {
        private final CountDownLatch latch;
        private boolean shutdown;
        private final Object owner;
        final TcpSocketManager this$0;

        public Reconnector(TcpSocketManager tcpSocketManager, OutputStreamManager outputStreamManager) {
            this.this$0 = tcpSocketManager;
            super("TcpSocketManager-Reconnector");
            this.latch = new CountDownLatch(1);
            this.shutdown = false;
            this.owner = outputStreamManager;
        }

        public void latch() {
            try {
                this.latch.await();
            } catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }

        public void shutdown() {
            this.shutdown = true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (!this.shutdown) {
                try {
                    Reconnector.sleep(TcpSocketManager.access$000(this.this$0));
                    Socket socket = this.this$0.createSocket(this.this$0.inetAddress, this.this$0.port);
                    OutputStream outputStream = socket.getOutputStream();
                    Object object = this.owner;
                    synchronized (object) {
                        try {
                            TcpSocketManager.access$100(this.this$0).close();
                        } catch (IOException iOException) {
                            // empty catch block
                        }
                        TcpSocketManager.access$200(this.this$0, outputStream);
                        TcpSocketManager.access$302(this.this$0, socket);
                        TcpSocketManager.access$402(this.this$0, null);
                        this.shutdown = true;
                    }
                    TcpSocketManager.access$500().debug("Connection to " + this.this$0.host + ':' + this.this$0.port + " reestablished.");
                } catch (InterruptedException interruptedException) {
                    TcpSocketManager.access$600().debug("Reconnection interrupted.");
                } catch (ConnectException connectException) {
                    TcpSocketManager.access$700().debug(this.this$0.host + ':' + this.this$0.port + " refused connection");
                } catch (IOException iOException) {
                    TcpSocketManager.access$800().debug("Unable to reconnect to " + this.this$0.host + ':' + this.this$0.port);
                } finally {
                    this.latch.countDown();
                }
            }
        }
    }
}

