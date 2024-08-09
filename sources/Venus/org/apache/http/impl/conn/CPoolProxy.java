/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.impl.conn.CPoolEntry;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.protocol.HttpContext;

class CPoolProxy
implements ManagedHttpClientConnection,
HttpContext {
    private volatile CPoolEntry poolEntry;

    CPoolProxy(CPoolEntry cPoolEntry) {
        this.poolEntry = cPoolEntry;
    }

    CPoolEntry getPoolEntry() {
        return this.poolEntry;
    }

    CPoolEntry detach() {
        CPoolEntry cPoolEntry = this.poolEntry;
        this.poolEntry = null;
        return cPoolEntry;
    }

    ManagedHttpClientConnection getConnection() {
        CPoolEntry cPoolEntry = this.poolEntry;
        if (cPoolEntry == null) {
            return null;
        }
        return (ManagedHttpClientConnection)cPoolEntry.getConnection();
    }

    ManagedHttpClientConnection getValidConnection() {
        ManagedHttpClientConnection managedHttpClientConnection = this.getConnection();
        if (managedHttpClientConnection == null) {
            throw new ConnectionShutdownException();
        }
        return managedHttpClientConnection;
    }

    @Override
    public void close() throws IOException {
        CPoolEntry cPoolEntry = this.poolEntry;
        if (cPoolEntry != null) {
            cPoolEntry.closeConnection();
        }
    }

    @Override
    public void shutdown() throws IOException {
        CPoolEntry cPoolEntry = this.poolEntry;
        if (cPoolEntry != null) {
            cPoolEntry.shutdownConnection();
        }
    }

    @Override
    public boolean isOpen() {
        CPoolEntry cPoolEntry = this.poolEntry;
        return cPoolEntry != null ? !cPoolEntry.isClosed() : false;
    }

    @Override
    public boolean isStale() {
        ManagedHttpClientConnection managedHttpClientConnection = this.getConnection();
        return managedHttpClientConnection != null ? managedHttpClientConnection.isStale() : true;
    }

    @Override
    public void setSocketTimeout(int n) {
        this.getValidConnection().setSocketTimeout(n);
    }

    @Override
    public int getSocketTimeout() {
        return this.getValidConnection().getSocketTimeout();
    }

    @Override
    public String getId() {
        return this.getValidConnection().getId();
    }

    @Override
    public void bind(Socket socket) throws IOException {
        this.getValidConnection().bind(socket);
    }

    @Override
    public Socket getSocket() {
        return this.getValidConnection().getSocket();
    }

    @Override
    public SSLSession getSSLSession() {
        return this.getValidConnection().getSSLSession();
    }

    @Override
    public boolean isResponseAvailable(int n) throws IOException {
        return this.getValidConnection().isResponseAvailable(n);
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        this.getValidConnection().sendRequestHeader(httpRequest);
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        this.getValidConnection().sendRequestEntity(httpEntityEnclosingRequest);
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        return this.getValidConnection().receiveResponseHeader();
    }

    @Override
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        this.getValidConnection().receiveResponseEntity(httpResponse);
    }

    @Override
    public void flush() throws IOException {
        this.getValidConnection().flush();
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
        return this.getValidConnection().getMetrics();
    }

    @Override
    public InetAddress getLocalAddress() {
        return this.getValidConnection().getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return this.getValidConnection().getLocalPort();
    }

    @Override
    public InetAddress getRemoteAddress() {
        return this.getValidConnection().getRemoteAddress();
    }

    @Override
    public int getRemotePort() {
        return this.getValidConnection().getRemotePort();
    }

    @Override
    public Object getAttribute(String string) {
        ManagedHttpClientConnection managedHttpClientConnection = this.getValidConnection();
        return managedHttpClientConnection instanceof HttpContext ? ((HttpContext)((Object)managedHttpClientConnection)).getAttribute(string) : null;
    }

    @Override
    public void setAttribute(String string, Object object) {
        ManagedHttpClientConnection managedHttpClientConnection = this.getValidConnection();
        if (managedHttpClientConnection instanceof HttpContext) {
            ((HttpContext)((Object)managedHttpClientConnection)).setAttribute(string, object);
        }
    }

    @Override
    public Object removeAttribute(String string) {
        ManagedHttpClientConnection managedHttpClientConnection = this.getValidConnection();
        return managedHttpClientConnection instanceof HttpContext ? ((HttpContext)((Object)managedHttpClientConnection)).removeAttribute(string) : null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("CPoolProxy{");
        ManagedHttpClientConnection managedHttpClientConnection = this.getConnection();
        if (managedHttpClientConnection != null) {
            stringBuilder.append(managedHttpClientConnection);
        } else {
            stringBuilder.append("detached");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static HttpClientConnection newProxy(CPoolEntry cPoolEntry) {
        return new CPoolProxy(cPoolEntry);
    }

    private static CPoolProxy getProxy(HttpClientConnection httpClientConnection) {
        if (!CPoolProxy.class.isInstance(httpClientConnection)) {
            throw new IllegalStateException("Unexpected connection proxy class: " + httpClientConnection.getClass());
        }
        return (CPoolProxy)CPoolProxy.class.cast(httpClientConnection);
    }

    public static CPoolEntry getPoolEntry(HttpClientConnection httpClientConnection) {
        CPoolEntry cPoolEntry = CPoolProxy.getProxy(httpClientConnection).getPoolEntry();
        if (cPoolEntry == null) {
            throw new ConnectionShutdownException();
        }
        return cPoolEntry;
    }

    public static CPoolEntry detach(HttpClientConnection httpClientConnection) {
        return CPoolProxy.getProxy(httpClientConnection).detach();
    }
}

