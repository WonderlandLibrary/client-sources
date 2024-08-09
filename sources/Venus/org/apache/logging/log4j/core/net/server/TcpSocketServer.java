/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.beust.jcommander.Parameter
 *  com.beust.jcommander.validators.PositiveInteger
 */
package org.apache.logging.log4j.core.net.server;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.net.server.AbstractSocketServer;
import org.apache.logging.log4j.core.net.server.JsonInputStreamLogEventBridge;
import org.apache.logging.log4j.core.net.server.LogEventBridge;
import org.apache.logging.log4j.core.net.server.ObjectInputStreamLogEventBridge;
import org.apache.logging.log4j.core.net.server.XmlInputStreamLogEventBridge;
import org.apache.logging.log4j.core.util.BasicCommandLineArguments;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.message.EntryMessage;

public class TcpSocketServer<T extends InputStream>
extends AbstractSocketServer<T> {
    private final ConcurrentMap<Long, SocketHandler> handlers = new ConcurrentHashMap<Long, SocketHandler>();
    private final ServerSocket serverSocket;

    public static TcpSocketServer<InputStream> createJsonSocketServer(int n) throws IOException {
        LOGGER.entry(new Object[]{"createJsonSocketServer", n});
        TcpSocketServer<InputStream> tcpSocketServer = new TcpSocketServer<InputStream>(n, new JsonInputStreamLogEventBridge());
        return LOGGER.exit(tcpSocketServer);
    }

    public static TcpSocketServer<ObjectInputStream> createSerializedSocketServer(int n) throws IOException {
        LOGGER.entry(n);
        TcpSocketServer<ObjectInputStream> tcpSocketServer = new TcpSocketServer<ObjectInputStream>(n, new ObjectInputStreamLogEventBridge());
        return LOGGER.exit(tcpSocketServer);
    }

    public static TcpSocketServer<ObjectInputStream> createSerializedSocketServer(int n, int n2, InetAddress inetAddress) throws IOException {
        LOGGER.entry(n);
        TcpSocketServer<ObjectInputStream> tcpSocketServer = new TcpSocketServer<ObjectInputStream>(n, n2, inetAddress, new ObjectInputStreamLogEventBridge());
        return LOGGER.exit(tcpSocketServer);
    }

    public static TcpSocketServer<InputStream> createXmlSocketServer(int n) throws IOException {
        LOGGER.entry(n);
        TcpSocketServer<InputStream> tcpSocketServer = new TcpSocketServer<InputStream>(n, new XmlInputStreamLogEventBridge());
        return LOGGER.exit(tcpSocketServer);
    }

    public static void main(String[] stringArray) throws Exception {
        CommandLineArguments commandLineArguments = BasicCommandLineArguments.parseCommandLine(stringArray, TcpSocketServer.class, new CommandLineArguments());
        if (commandLineArguments.isHelp()) {
            return;
        }
        if (commandLineArguments.getConfigLocation() != null) {
            ConfigurationFactory.setConfigurationFactory(new AbstractSocketServer.ServerConfigurationFactory(commandLineArguments.getConfigLocation()));
        }
        TcpSocketServer<ObjectInputStream> tcpSocketServer = TcpSocketServer.createSerializedSocketServer(commandLineArguments.getPort(), commandLineArguments.getBacklog(), commandLineArguments.getLocalBindAddress());
        Thread thread2 = tcpSocketServer.startNewThread();
        if (commandLineArguments.isInteractive()) {
            tcpSocketServer.awaitTermination(thread2);
        }
    }

    public TcpSocketServer(int n, int n2, InetAddress inetAddress, LogEventBridge<T> logEventBridge) throws IOException {
        this(n, logEventBridge, new ServerSocket(n, n2, inetAddress));
    }

    public TcpSocketServer(int n, LogEventBridge<T> logEventBridge) throws IOException {
        this(n, logEventBridge, TcpSocketServer.extracted(n));
    }

    private static ServerSocket extracted(int n) throws IOException {
        return new ServerSocket(n);
    }

    public TcpSocketServer(int n, LogEventBridge<T> logEventBridge, ServerSocket serverSocket) throws IOException {
        super(n, logEventBridge);
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        EntryMessage entryMessage = this.logger.traceEntry();
        while (this.isActive()) {
            if (this.serverSocket.isClosed()) {
                return;
            }
            try {
                this.logger.debug("Listening for a connection {}...", (Object)this.serverSocket);
                Socket socket = this.serverSocket.accept();
                this.logger.debug("Acepted connection on {}...", (Object)this.serverSocket);
                this.logger.debug("Socket accepted: {}", (Object)socket);
                socket.setSoLinger(true, 1);
                SocketHandler object = new SocketHandler(this, socket);
                this.handlers.put(object.getId(), object);
                object.start();
            } catch (IOException iOException) {
                if (this.serverSocket.isClosed()) {
                    this.logger.traceExit(entryMessage);
                    return;
                }
                this.logger.error("Exception encountered on accept. Ignoring. Stack trace :", (Throwable)iOException);
            }
        }
        for (Map.Entry entry : this.handlers.entrySet()) {
            SocketHandler socketHandler = (SocketHandler)entry.getValue();
            socketHandler.shutdown();
            try {
                socketHandler.join();
            } catch (InterruptedException interruptedException) {}
        }
        this.logger.traceExit(entryMessage);
    }

    @Override
    public void shutdown() throws IOException {
        EntryMessage entryMessage = this.logger.traceEntry();
        this.setActive(true);
        Thread.currentThread().interrupt();
        this.serverSocket.close();
        this.logger.traceExit(entryMessage);
    }

    static ConcurrentMap access$000(TcpSocketServer tcpSocketServer) {
        return tcpSocketServer.handlers;
    }

    private class SocketHandler
    extends Log4jThread {
        private final T inputStream;
        private volatile boolean shutdown;
        final TcpSocketServer this$0;

        public SocketHandler(TcpSocketServer tcpSocketServer, Socket socket) throws IOException {
            this.this$0 = tcpSocketServer;
            this.shutdown = false;
            this.inputStream = tcpSocketServer.logEventInput.wrapStream(socket.getInputStream());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            EntryMessage entryMessage = this.this$0.logger.traceEntry();
            boolean bl = false;
            try {
                try {
                    while (!this.shutdown) {
                        this.this$0.logEventInput.logEvents(this.inputStream, this.this$0);
                    }
                } catch (EOFException eOFException) {
                    bl = true;
                } catch (OptionalDataException optionalDataException) {
                    this.this$0.logger.error("OptionalDataException eof=" + optionalDataException.eof + " length=" + optionalDataException.length, (Throwable)optionalDataException);
                } catch (IOException iOException) {
                    this.this$0.logger.error("IOException encountered while reading from socket", (Throwable)iOException);
                }
                if (!bl) {
                    Closer.closeSilently(this.inputStream);
                }
            } finally {
                TcpSocketServer.access$000(this.this$0).remove(this.getId());
            }
            this.this$0.logger.traceExit(entryMessage);
        }

        public void shutdown() {
            this.shutdown = true;
            this.interrupt();
        }
    }

    protected static class CommandLineArguments
    extends AbstractSocketServer.CommandLineArguments {
        @Parameter(names={"--backlog", "-b"}, validateWith=PositiveInteger.class, description="Server socket backlog.")
        private int backlog = 50;

        protected CommandLineArguments() {
        }

        int getBacklog() {
            return this.backlog;
        }

        void setBacklog(int n) {
            this.backlog = n;
        }
    }
}

