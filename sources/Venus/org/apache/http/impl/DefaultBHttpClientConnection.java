/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.BHttpConnectionBase;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.util.Args;

public class DefaultBHttpClientConnection
extends BHttpConnectionBase
implements HttpClientConnection {
    private final HttpMessageParser<HttpResponse> responseParser;
    private final HttpMessageWriter<HttpRequest> requestWriter;

    public DefaultBHttpClientConnection(int n, int n2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2, HttpMessageWriterFactory<HttpRequest> defaultHttpRequestWriterFactory, HttpMessageParserFactory<HttpResponse> defaultHttpResponseParserFactory) {
        super(n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2);
        this.requestWriter = (defaultHttpRequestWriterFactory != null ? defaultHttpRequestWriterFactory : DefaultHttpRequestWriterFactory.INSTANCE).create(this.getSessionOutputBuffer());
        this.responseParser = (defaultHttpResponseParserFactory != null ? defaultHttpResponseParserFactory : DefaultHttpResponseParserFactory.INSTANCE).create(this.getSessionInputBuffer(), messageConstraints);
    }

    public DefaultBHttpClientConnection(int n, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints) {
        this(n, n, charsetDecoder, charsetEncoder, messageConstraints, null, null, null, null);
    }

    public DefaultBHttpClientConnection(int n) {
        this(n, n, null, null, null, null, null, null, null);
    }

    protected void onResponseReceived(HttpResponse httpResponse) {
    }

    protected void onRequestSubmitted(HttpRequest httpRequest) {
    }

    @Override
    public void bind(Socket socket) throws IOException {
        super.bind(socket);
    }

    @Override
    public boolean isResponseAvailable(int n) throws IOException {
        this.ensureOpen();
        try {
            return this.awaitInput(n);
        } catch (SocketTimeoutException socketTimeoutException) {
            return true;
        }
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        this.ensureOpen();
        this.requestWriter.write(httpRequest);
        this.onRequestSubmitted(httpRequest);
        this.incrementRequestCount();
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.ensureOpen();
        HttpEntity httpEntity = httpEntityEnclosingRequest.getEntity();
        if (httpEntity == null) {
            return;
        }
        OutputStream outputStream = this.prepareOutput(httpEntityEnclosingRequest);
        httpEntity.writeTo(outputStream);
        outputStream.close();
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        this.ensureOpen();
        HttpResponse httpResponse = this.responseParser.parse();
        this.onResponseReceived(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.incrementResponseCount();
        }
        return httpResponse;
    }

    @Override
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        HttpEntity httpEntity = this.prepareInput(httpResponse);
        httpResponse.setEntity(httpEntity);
    }

    @Override
    public void flush() throws IOException {
        this.ensureOpen();
        this.doFlush();
    }
}

