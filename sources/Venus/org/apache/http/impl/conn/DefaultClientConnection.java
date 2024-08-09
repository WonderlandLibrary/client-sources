/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.LoggingSessionInputBuffer;
import org.apache.http.impl.conn.LoggingSessionOutputBuffer;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
public class DefaultClientConnection
extends SocketHttpClientConnection
implements OperatedClientConnection,
ManagedHttpClientConnection,
HttpContext {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
    private final Log wireLog = LogFactory.getLog("org.apache.http.wire");
    private volatile Socket socket;
    private HttpHost targetHost;
    private boolean connSecure;
    private volatile boolean shutdown;
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    @Override
    public String getId() {
        return null;
    }

    @Override
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override
    public final boolean isSecure() {
        return this.connSecure;
    }

    @Override
    public final Socket getSocket() {
        return this.socket;
    }

    @Override
    public SSLSession getSSLSession() {
        if (this.socket instanceof SSLSocket) {
            return ((SSLSocket)this.socket).getSession();
        }
        return null;
    }

    @Override
    public void opening(Socket socket, HttpHost httpHost) throws IOException {
        this.assertNotOpen();
        this.socket = socket;
        this.targetHost = httpHost;
        if (this.shutdown) {
            socket.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
    }

    @Override
    public void openCompleted(boolean bl, HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "Parameters");
        this.assertNotOpen();
        this.connSecure = bl;
        this.bind(this.socket, httpParams);
    }

    @Override
    public void shutdown() throws IOException {
        this.shutdown = true;
        try {
            Socket socket;
            super.shutdown();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection " + this + " shut down");
            }
            if ((socket = this.socket) != null) {
                socket.close();
            }
        } catch (IOException iOException) {
            this.log.debug("I/O error shutting down connection", iOException);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connection " + this + " closed");
            }
        } catch (IOException iOException) {
            this.log.debug("I/O error closing connection", iOException);
        }
    }

    @Override
    protected SessionInputBuffer createSessionInputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        SessionInputBuffer sessionInputBuffer = super.createSessionInputBuffer(socket, n > 0 ? n : 8192, httpParams);
        if (this.wireLog.isDebugEnabled()) {
            sessionInputBuffer = new LoggingSessionInputBuffer(sessionInputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
        }
        return sessionInputBuffer;
    }

    @Override
    protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        SessionOutputBuffer sessionOutputBuffer = super.createSessionOutputBuffer(socket, n > 0 ? n : 8192, httpParams);
        if (this.wireLog.isDebugEnabled()) {
            sessionOutputBuffer = new LoggingSessionOutputBuffer(sessionOutputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
        }
        return sessionOutputBuffer;
    }

    @Override
    protected HttpMessageParser<HttpResponse> createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultHttpResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    @Override
    public void bind(Socket socket) throws IOException {
        this.bind(socket, new BasicHttpParams());
    }

    @Override
    public void update(Socket socket, HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        this.assertOpen();
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        if (socket != null) {
            this.socket = socket;
            this.bind(socket, httpParams);
        }
        this.targetHost = httpHost;
        this.connSecure = bl;
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        HttpResponse httpResponse = super.receiveResponseHeader();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Receiving response: " + httpResponse.getStatusLine());
        }
        if (this.headerLog.isDebugEnabled()) {
            Header[] headerArray;
            this.headerLog.debug("<< " + httpResponse.getStatusLine().toString());
            for (Header header : headerArray = httpResponse.getAllHeaders()) {
                this.headerLog.debug("<< " + header.toString());
            }
        }
        return httpResponse;
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Sending request: " + httpRequest.getRequestLine());
        }
        super.sendRequestHeader(httpRequest);
        if (this.headerLog.isDebugEnabled()) {
            Header[] headerArray;
            this.headerLog.debug(">> " + httpRequest.getRequestLine().toString());
            for (Header header : headerArray = httpRequest.getAllHeaders()) {
                this.headerLog.debug(">> " + header.toString());
            }
        }
    }

    @Override
    public Object getAttribute(String string) {
        return this.attributes.get(string);
    }

    @Override
    public Object removeAttribute(String string) {
        return this.attributes.remove(string);
    }

    @Override
    public void setAttribute(String string, Object object) {
        this.attributes.put(string, object);
    }
}

