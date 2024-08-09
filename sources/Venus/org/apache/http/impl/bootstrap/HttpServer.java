/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.bootstrap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.bootstrap.RequestListener;
import org.apache.http.impl.bootstrap.SSLServerSetupHandler;
import org.apache.http.impl.bootstrap.ThreadFactoryImpl;
import org.apache.http.impl.bootstrap.Worker;
import org.apache.http.impl.bootstrap.WorkerPoolExecutor;
import org.apache.http.protocol.HttpService;

public class HttpServer {
    private final int port;
    private final InetAddress ifAddress;
    private final SocketConfig socketConfig;
    private final ServerSocketFactory serverSocketFactory;
    private final HttpService httpService;
    private final HttpConnectionFactory<? extends DefaultBHttpServerConnection> connectionFactory;
    private final SSLServerSetupHandler sslSetupHandler;
    private final ExceptionLogger exceptionLogger;
    private final ThreadPoolExecutor listenerExecutorService;
    private final ThreadGroup workerThreads;
    private final WorkerPoolExecutor workerExecutorService;
    private final AtomicReference<Status> status;
    private volatile ServerSocket serverSocket;
    private volatile RequestListener requestListener;

    HttpServer(int n, InetAddress inetAddress, SocketConfig socketConfig, ServerSocketFactory serverSocketFactory, HttpService httpService, HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory, SSLServerSetupHandler sSLServerSetupHandler, ExceptionLogger exceptionLogger) {
        this.port = n;
        this.ifAddress = inetAddress;
        this.socketConfig = socketConfig;
        this.serverSocketFactory = serverSocketFactory;
        this.httpService = httpService;
        this.connectionFactory = httpConnectionFactory;
        this.sslSetupHandler = sSLServerSetupHandler;
        this.exceptionLogger = exceptionLogger;
        this.listenerExecutorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryImpl("HTTP-listener-" + this.port));
        this.workerThreads = new ThreadGroup("HTTP-workers");
        this.workerExecutorService = new WorkerPoolExecutor(0, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactoryImpl("HTTP-worker", this.workerThreads));
        this.status = new AtomicReference<Status>(Status.READY);
    }

    public InetAddress getInetAddress() {
        ServerSocket serverSocket = this.serverSocket;
        return serverSocket != null ? serverSocket.getInetAddress() : null;
    }

    public int getLocalPort() {
        ServerSocket serverSocket = this.serverSocket;
        return serverSocket != null ? serverSocket.getLocalPort() : -1;
    }

    public void start() throws IOException {
        if (this.status.compareAndSet(Status.READY, Status.ACTIVE)) {
            this.serverSocket = this.serverSocketFactory.createServerSocket(this.port, this.socketConfig.getBacklogSize(), this.ifAddress);
            this.serverSocket.setReuseAddress(this.socketConfig.isSoReuseAddress());
            if (this.socketConfig.getRcvBufSize() > 0) {
                this.serverSocket.setReceiveBufferSize(this.socketConfig.getRcvBufSize());
            }
            if (this.sslSetupHandler != null && this.serverSocket instanceof SSLServerSocket) {
                this.sslSetupHandler.initialize((SSLServerSocket)this.serverSocket);
            }
            this.requestListener = new RequestListener(this.socketConfig, this.serverSocket, this.httpService, this.connectionFactory, this.exceptionLogger, this.workerExecutorService);
            this.listenerExecutorService.execute(this.requestListener);
        }
    }

    public void stop() {
        if (this.status.compareAndSet(Status.ACTIVE, Status.STOPPING)) {
            this.listenerExecutorService.shutdown();
            this.workerExecutorService.shutdown();
            RequestListener requestListener = this.requestListener;
            if (requestListener != null) {
                try {
                    requestListener.terminate();
                } catch (IOException iOException) {
                    this.exceptionLogger.log(iOException);
                }
            }
            this.workerThreads.interrupt();
        }
    }

    public void awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        this.workerExecutorService.awaitTermination(l, timeUnit);
    }

    public void shutdown(long l, TimeUnit timeUnit) {
        this.stop();
        if (l > 0L) {
            try {
                this.awaitTermination(l, timeUnit);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        Set<Worker> set = this.workerExecutorService.getWorkers();
        for (Worker worker : set) {
            HttpServerConnection httpServerConnection = worker.getConnection();
            try {
                httpServerConnection.shutdown();
            } catch (IOException iOException) {
                this.exceptionLogger.log(iOException);
            }
        }
    }

    static enum Status {
        READY,
        ACTIVE,
        STOPPING;

    }
}

