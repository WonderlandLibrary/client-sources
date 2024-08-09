/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.bootstrap;

import java.io.IOException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpService;

class Worker
implements Runnable {
    private final HttpService httpservice;
    private final HttpServerConnection conn;
    private final ExceptionLogger exceptionLogger;

    Worker(HttpService httpService, HttpServerConnection httpServerConnection, ExceptionLogger exceptionLogger) {
        this.httpservice = httpService;
        this.conn = httpServerConnection;
        this.exceptionLogger = exceptionLogger;
    }

    public HttpServerConnection getConnection() {
        return this.conn;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            BasicHttpContext basicHttpContext = new BasicHttpContext();
            HttpCoreContext httpCoreContext = HttpCoreContext.adapt(basicHttpContext);
            while (!Thread.interrupted() && this.conn.isOpen()) {
                this.httpservice.handleRequest(this.conn, httpCoreContext);
                basicHttpContext.clear();
            }
            this.conn.close();
        } catch (Exception exception) {
            this.exceptionLogger.log(exception);
        } finally {
            try {
                this.conn.shutdown();
            } catch (IOException iOException) {
                this.exceptionLogger.log(iOException);
            }
        }
    }
}

