/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.logging.Log;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.impl.conn.LoggingInputStream;
import org.apache.http.impl.conn.LoggingOutputStream;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;

class LoggingManagedHttpClientConnection
extends DefaultManagedHttpClientConnection {
    private final Log log;
    private final Log headerLog;
    private final Wire wire;

    public LoggingManagedHttpClientConnection(String string, Log log2, Log log3, Log log4, int n, int n2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2, HttpMessageWriterFactory<HttpRequest> httpMessageWriterFactory, HttpMessageParserFactory<HttpResponse> httpMessageParserFactory) {
        super(string, n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2, httpMessageWriterFactory, httpMessageParserFactory);
        this.log = log2;
        this.headerLog = log3;
        this.wire = new Wire(log4, string);
    }

    @Override
    public void close() throws IOException {
        if (super.isOpen()) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(this.getId() + ": Close connection");
            }
            super.close();
        }
    }

    @Override
    public void setSocketTimeout(int n) {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getId() + ": set socket timeout to " + n);
        }
        super.setSocketTimeout(n);
    }

    @Override
    public void shutdown() throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug(this.getId() + ": Shutdown connection");
        }
        super.shutdown();
    }

    @Override
    protected InputStream getSocketInputStream(Socket socket) throws IOException {
        InputStream inputStream = super.getSocketInputStream(socket);
        if (this.wire.enabled()) {
            inputStream = new LoggingInputStream(inputStream, this.wire);
        }
        return inputStream;
    }

    @Override
    protected OutputStream getSocketOutputStream(Socket socket) throws IOException {
        OutputStream outputStream = super.getSocketOutputStream(socket);
        if (this.wire.enabled()) {
            outputStream = new LoggingOutputStream(outputStream, this.wire);
        }
        return outputStream;
    }

    @Override
    protected void onResponseReceived(HttpResponse httpResponse) {
        if (httpResponse != null && this.headerLog.isDebugEnabled()) {
            Header[] headerArray;
            this.headerLog.debug(this.getId() + " << " + httpResponse.getStatusLine().toString());
            for (Header header : headerArray = httpResponse.getAllHeaders()) {
                this.headerLog.debug(this.getId() + " << " + header.toString());
            }
        }
    }

    @Override
    protected void onRequestSubmitted(HttpRequest httpRequest) {
        if (httpRequest != null && this.headerLog.isDebugEnabled()) {
            Header[] headerArray;
            this.headerLog.debug(this.getId() + " >> " + httpRequest.getRequestLine().toString());
            for (Header header : headerArray = httpRequest.getAllHeaders()) {
                this.headerLog.debug(this.getId() + " >> " + header.toString());
            }
        }
    }
}

