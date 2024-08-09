/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract class AbstractClientConnAdapter
implements ManagedClientConnection,
HttpContext {
    private final ClientConnectionManager connManager;
    private volatile OperatedClientConnection wrappedConnection;
    private volatile boolean markedReusable;
    private volatile boolean released;
    private volatile long duration;

    protected AbstractClientConnAdapter(ClientConnectionManager clientConnectionManager, OperatedClientConnection operatedClientConnection) {
        this.connManager = clientConnectionManager;
        this.wrappedConnection = operatedClientConnection;
        this.markedReusable = false;
        this.released = false;
        this.duration = Long.MAX_VALUE;
    }

    protected synchronized void detach() {
        this.wrappedConnection = null;
        this.duration = Long.MAX_VALUE;
    }

    protected OperatedClientConnection getWrappedConnection() {
        return this.wrappedConnection;
    }

    protected ClientConnectionManager getManager() {
        return this.connManager;
    }

    @Deprecated
    protected final void assertNotAborted() throws InterruptedIOException {
        if (this.isReleased()) {
            throw new InterruptedIOException("Connection has been shut down");
        }
    }

    protected boolean isReleased() {
        return this.released;
    }

    protected final void assertValid(OperatedClientConnection operatedClientConnection) throws ConnectionShutdownException {
        if (this.isReleased() || operatedClientConnection == null) {
            throw new ConnectionShutdownException();
        }
    }

    @Override
    public boolean isOpen() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        if (operatedClientConnection == null) {
            return true;
        }
        return operatedClientConnection.isOpen();
    }

    @Override
    public boolean isStale() {
        if (this.isReleased()) {
            return false;
        }
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        if (operatedClientConnection == null) {
            return false;
        }
        return operatedClientConnection.isStale();
    }

    @Override
    public void setSocketTimeout(int n) {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        operatedClientConnection.setSocketTimeout(n);
    }

    @Override
    public int getSocketTimeout() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getSocketTimeout();
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getMetrics();
    }

    @Override
    public void flush() throws IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        operatedClientConnection.flush();
    }

    @Override
    public boolean isResponseAvailable(int n) throws IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.isResponseAvailable(n);
    }

    @Override
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.receiveResponseEntity(httpResponse);
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        return operatedClientConnection.receiveResponseHeader();
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.sendRequestEntity(httpEntityEnclosingRequest);
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.sendRequestHeader(httpRequest);
    }

    @Override
    public InetAddress getLocalAddress() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getLocalPort();
    }

    @Override
    public InetAddress getRemoteAddress() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getRemoteAddress();
    }

    @Override
    public int getRemotePort() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getRemotePort();
    }

    @Override
    public boolean isSecure() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.isSecure();
    }

    @Override
    public void bind(Socket socket) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket getSocket() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        if (!this.isOpen()) {
            return null;
        }
        return operatedClientConnection.getSocket();
    }

    @Override
    public SSLSession getSSLSession() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        if (!this.isOpen()) {
            return null;
        }
        SSLSession sSLSession = null;
        Socket socket = operatedClientConnection.getSocket();
        if (socket instanceof SSLSocket) {
            sSLSession = ((SSLSocket)socket).getSession();
        }
        return sSLSession;
    }

    @Override
    public void markReusable() {
        this.markedReusable = true;
    }

    @Override
    public void unmarkReusable() {
        this.markedReusable = false;
    }

    @Override
    public boolean isMarkedReusable() {
        return this.markedReusable;
    }

    @Override
    public void setIdleDuration(long l, TimeUnit timeUnit) {
        this.duration = l > 0L ? timeUnit.toMillis(l) : -1L;
    }

    @Override
    public synchronized void releaseConnection() {
        if (this.released) {
            return;
        }
        this.released = true;
        this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized void abortConnection() {
        if (this.released) {
            return;
        }
        this.released = true;
        this.unmarkReusable();
        try {
            this.shutdown();
        } catch (IOException iOException) {
            // empty catch block
        }
        this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
    }

    @Override
    public Object getAttribute(String string) {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        if (operatedClientConnection instanceof HttpContext) {
            return ((HttpContext)((Object)operatedClientConnection)).getAttribute(string);
        }
        return null;
    }

    @Override
    public Object removeAttribute(String string) {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        if (operatedClientConnection instanceof HttpContext) {
            return ((HttpContext)((Object)operatedClientConnection)).removeAttribute(string);
        }
        return null;
    }

    @Override
    public void setAttribute(String string, Object object) {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        if (operatedClientConnection instanceof HttpContext) {
            ((HttpContext)((Object)operatedClientConnection)).setAttribute(string, object);
        }
    }
}

