/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;

class RequestEntityProxy
implements HttpEntity {
    private final HttpEntity original;
    private boolean consumed = false;

    static void enhance(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        HttpEntity httpEntity = httpEntityEnclosingRequest.getEntity();
        if (httpEntity != null && !httpEntity.isRepeatable() && !RequestEntityProxy.isEnhanced(httpEntity)) {
            httpEntityEnclosingRequest.setEntity(new RequestEntityProxy(httpEntity));
        }
    }

    static boolean isEnhanced(HttpEntity httpEntity) {
        return httpEntity instanceof RequestEntityProxy;
    }

    static boolean isRepeatable(HttpRequest httpRequest) {
        HttpEntity httpEntity;
        if (httpRequest instanceof HttpEntityEnclosingRequest && (httpEntity = ((HttpEntityEnclosingRequest)httpRequest).getEntity()) != null) {
            RequestEntityProxy requestEntityProxy;
            if (RequestEntityProxy.isEnhanced(httpEntity) && !(requestEntityProxy = (RequestEntityProxy)httpEntity).isConsumed()) {
                return false;
            }
            return httpEntity.isRepeatable();
        }
        return false;
    }

    RequestEntityProxy(HttpEntity httpEntity) {
        this.original = httpEntity;
    }

    public HttpEntity getOriginal() {
        return this.original;
    }

    public boolean isConsumed() {
        return this.consumed;
    }

    @Override
    public boolean isRepeatable() {
        return this.original.isRepeatable();
    }

    @Override
    public boolean isChunked() {
        return this.original.isChunked();
    }

    @Override
    public long getContentLength() {
        return this.original.getContentLength();
    }

    @Override
    public Header getContentType() {
        return this.original.getContentType();
    }

    @Override
    public Header getContentEncoding() {
        return this.original.getContentEncoding();
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return this.original.getContent();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        this.consumed = true;
        this.original.writeTo(outputStream);
    }

    @Override
    public boolean isStreaming() {
        return this.original.isStreaming();
    }

    @Override
    public void consumeContent() throws IOException {
        this.consumed = true;
        this.original.consumeContent();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RequestEntityProxy{");
        stringBuilder.append(this.original);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

