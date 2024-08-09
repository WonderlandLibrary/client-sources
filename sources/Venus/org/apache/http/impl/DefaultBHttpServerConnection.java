/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.BHttpConnectionBase;
import org.apache.http.impl.entity.DisallowIdentityContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestParserFactory;
import org.apache.http.impl.io.DefaultHttpResponseWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.util.Args;

public class DefaultBHttpServerConnection
extends BHttpConnectionBase
implements HttpServerConnection {
    private final HttpMessageParser<HttpRequest> requestParser;
    private final HttpMessageWriter<HttpResponse> responseWriter;

    public DefaultBHttpServerConnection(int n, int n2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2, HttpMessageParserFactory<HttpRequest> defaultHttpRequestParserFactory, HttpMessageWriterFactory<HttpResponse> defaultHttpResponseWriterFactory) {
        super(n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy != null ? contentLengthStrategy : DisallowIdentityContentLengthStrategy.INSTANCE, contentLengthStrategy2);
        this.requestParser = (defaultHttpRequestParserFactory != null ? defaultHttpRequestParserFactory : DefaultHttpRequestParserFactory.INSTANCE).create(this.getSessionInputBuffer(), messageConstraints);
        this.responseWriter = (defaultHttpResponseWriterFactory != null ? defaultHttpResponseWriterFactory : DefaultHttpResponseWriterFactory.INSTANCE).create(this.getSessionOutputBuffer());
    }

    public DefaultBHttpServerConnection(int n, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints) {
        this(n, n, charsetDecoder, charsetEncoder, messageConstraints, null, null, null, null);
    }

    public DefaultBHttpServerConnection(int n) {
        this(n, n, null, null, null, null, null, null, null);
    }

    protected void onRequestReceived(HttpRequest httpRequest) {
    }

    protected void onResponseSubmitted(HttpResponse httpResponse) {
    }

    @Override
    public void bind(Socket socket) throws IOException {
        super.bind(socket);
    }

    @Override
    public HttpRequest receiveRequestHeader() throws HttpException, IOException {
        this.ensureOpen();
        HttpRequest httpRequest = this.requestParser.parse();
        this.onRequestReceived(httpRequest);
        this.incrementRequestCount();
        return httpRequest;
    }

    @Override
    public void receiveRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        this.ensureOpen();
        HttpEntity httpEntity = this.prepareInput(httpEntityEnclosingRequest);
        httpEntityEnclosingRequest.setEntity(httpEntity);
    }

    @Override
    public void sendResponseHeader(HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        this.responseWriter.write(httpResponse);
        this.onResponseSubmitted(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.incrementResponseCount();
        }
    }

    @Override
    public void sendResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        this.ensureOpen();
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            return;
        }
        OutputStream outputStream = this.prepareOutput(httpResponse);
        httpEntity.writeTo(outputStream);
        outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        this.ensureOpen();
        this.doFlush();
    }
}

