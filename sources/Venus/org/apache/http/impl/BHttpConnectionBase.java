/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpMessage;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.HttpConnectionMetricsImpl;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.ContentLengthOutputStream;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.impl.io.IdentityOutputStream;
import org.apache.http.impl.io.SessionInputBufferImpl;
import org.apache.http.impl.io.SessionOutputBufferImpl;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.Args;
import org.apache.http.util.NetUtils;

public class BHttpConnectionBase
implements HttpInetConnection {
    private final SessionInputBufferImpl inBuffer;
    private final SessionOutputBufferImpl outbuffer;
    private final MessageConstraints messageConstraints;
    private final HttpConnectionMetricsImpl connMetrics;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private final AtomicReference<Socket> socketHolder;

    protected BHttpConnectionBase(int n, int n2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2) {
        Args.positive(n, "Buffer size");
        HttpTransportMetricsImpl httpTransportMetricsImpl = new HttpTransportMetricsImpl();
        HttpTransportMetricsImpl httpTransportMetricsImpl2 = new HttpTransportMetricsImpl();
        this.inBuffer = new SessionInputBufferImpl(httpTransportMetricsImpl, n, -1, messageConstraints != null ? messageConstraints : MessageConstraints.DEFAULT, charsetDecoder);
        this.outbuffer = new SessionOutputBufferImpl(httpTransportMetricsImpl2, n, n2, charsetEncoder);
        this.messageConstraints = messageConstraints;
        this.connMetrics = new HttpConnectionMetricsImpl(httpTransportMetricsImpl, httpTransportMetricsImpl2);
        this.incomingContentStrategy = contentLengthStrategy != null ? contentLengthStrategy : LaxContentLengthStrategy.INSTANCE;
        this.outgoingContentStrategy = contentLengthStrategy2 != null ? contentLengthStrategy2 : StrictContentLengthStrategy.INSTANCE;
        this.socketHolder = new AtomicReference();
    }

    protected void ensureOpen() throws IOException {
        Socket socket = this.socketHolder.get();
        if (socket == null) {
            throw new ConnectionClosedException();
        }
        if (!this.inBuffer.isBound()) {
            this.inBuffer.bind(this.getSocketInputStream(socket));
        }
        if (!this.outbuffer.isBound()) {
            this.outbuffer.bind(this.getSocketOutputStream(socket));
        }
    }

    protected InputStream getSocketInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    protected OutputStream getSocketOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    protected void bind(Socket socket) throws IOException {
        Args.notNull(socket, "Socket");
        this.socketHolder.set(socket);
        this.inBuffer.bind(null);
        this.outbuffer.bind(null);
    }

    protected SessionInputBuffer getSessionInputBuffer() {
        return this.inBuffer;
    }

    protected SessionOutputBuffer getSessionOutputBuffer() {
        return this.outbuffer;
    }

    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }

    @Override
    public boolean isOpen() {
        return this.socketHolder.get() != null;
    }

    protected Socket getSocket() {
        return this.socketHolder.get();
    }

    protected OutputStream createOutputStream(long l, SessionOutputBuffer sessionOutputBuffer) {
        if (l == -2L) {
            return new ChunkedOutputStream(2048, sessionOutputBuffer);
        }
        if (l == -1L) {
            return new IdentityOutputStream(sessionOutputBuffer);
        }
        return new ContentLengthOutputStream(sessionOutputBuffer, l);
    }

    protected OutputStream prepareOutput(HttpMessage httpMessage) throws HttpException {
        long l = this.outgoingContentStrategy.determineLength(httpMessage);
        return this.createOutputStream(l, this.outbuffer);
    }

    protected InputStream createInputStream(long l, SessionInputBuffer sessionInputBuffer) {
        if (l == -2L) {
            return new ChunkedInputStream(sessionInputBuffer, this.messageConstraints);
        }
        if (l == -1L) {
            return new IdentityInputStream(sessionInputBuffer);
        }
        if (l == 0L) {
            return EmptyInputStream.INSTANCE;
        }
        return new ContentLengthInputStream(sessionInputBuffer, l);
    }

    protected HttpEntity prepareInput(HttpMessage httpMessage) throws HttpException {
        Header header;
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        long l = this.incomingContentStrategy.determineLength(httpMessage);
        InputStream inputStream = this.createInputStream(l, this.inBuffer);
        if (l == -2L) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(inputStream);
        } else if (l == -1L) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(inputStream);
        } else {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(l);
            basicHttpEntity.setContent(inputStream);
        }
        Header header2 = httpMessage.getFirstHeader("Content-Type");
        if (header2 != null) {
            basicHttpEntity.setContentType(header2);
        }
        if ((header = httpMessage.getFirstHeader("Content-Encoding")) != null) {
            basicHttpEntity.setContentEncoding(header);
        }
        return basicHttpEntity;
    }

    @Override
    public InetAddress getLocalAddress() {
        Socket socket = this.socketHolder.get();
        return socket != null ? socket.getLocalAddress() : null;
    }

    @Override
    public int getLocalPort() {
        Socket socket = this.socketHolder.get();
        return socket != null ? socket.getLocalPort() : -1;
    }

    @Override
    public InetAddress getRemoteAddress() {
        Socket socket = this.socketHolder.get();
        return socket != null ? socket.getInetAddress() : null;
    }

    @Override
    public int getRemotePort() {
        Socket socket = this.socketHolder.get();
        return socket != null ? socket.getPort() : -1;
    }

    @Override
    public void setSocketTimeout(int n) {
        Socket socket = this.socketHolder.get();
        if (socket != null) {
            try {
                socket.setSoTimeout(n);
            } catch (SocketException socketException) {
                // empty catch block
            }
        }
    }

    @Override
    public int getSocketTimeout() {
        Socket socket = this.socketHolder.get();
        if (socket != null) {
            try {
                return socket.getSoTimeout();
            } catch (SocketException socketException) {
                // empty catch block
            }
        }
        return 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() throws IOException {
        Socket socket = this.socketHolder.getAndSet(null);
        if (socket != null) {
            try {
                socket.setSoLinger(true, 1);
            } catch (IOException iOException) {
            } finally {
                socket.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        Socket socket = this.socketHolder.getAndSet(null);
        if (socket != null) {
            try {
                this.inBuffer.clear();
                this.outbuffer.flush();
            } finally {
                socket.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int fillInputBuffer(int n) throws IOException {
        Socket socket = this.socketHolder.get();
        int n2 = socket.getSoTimeout();
        try {
            socket.setSoTimeout(n);
            int n3 = this.inBuffer.fillBuffer();
            return n3;
        } finally {
            socket.setSoTimeout(n2);
        }
    }

    protected boolean awaitInput(int n) throws IOException {
        if (this.inBuffer.hasBufferedData()) {
            return false;
        }
        this.fillInputBuffer(n);
        return this.inBuffer.hasBufferedData();
    }

    @Override
    public boolean isStale() {
        if (!this.isOpen()) {
            return false;
        }
        try {
            int n = this.fillInputBuffer(1);
            return n < 0;
        } catch (SocketTimeoutException socketTimeoutException) {
            return true;
        } catch (IOException iOException) {
            return false;
        }
    }

    protected void incrementRequestCount() {
        this.connMetrics.incrementRequestCount();
    }

    protected void incrementResponseCount() {
        this.connMetrics.incrementResponseCount();
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
        return this.connMetrics;
    }

    public String toString() {
        Socket socket = this.socketHolder.get();
        if (socket != null) {
            StringBuilder stringBuilder = new StringBuilder();
            SocketAddress socketAddress = socket.getRemoteSocketAddress();
            SocketAddress socketAddress2 = socket.getLocalSocketAddress();
            if (socketAddress != null && socketAddress2 != null) {
                NetUtils.formatAddress(stringBuilder, socketAddress2);
                stringBuilder.append("<->");
                NetUtils.formatAddress(stringBuilder, socketAddress);
            }
            return stringBuilder.toString();
        }
        return "[Not bound]";
    }
}

