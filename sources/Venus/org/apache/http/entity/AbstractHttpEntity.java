/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

public abstract class AbstractHttpEntity
implements HttpEntity {
    protected static final int OUTPUT_BUFFER_SIZE = 4096;
    protected Header contentType;
    protected Header contentEncoding;
    protected boolean chunked;

    protected AbstractHttpEntity() {
    }

    @Override
    public Header getContentType() {
        return this.contentType;
    }

    @Override
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    @Override
    public boolean isChunked() {
        return this.chunked;
    }

    public void setContentType(Header header) {
        this.contentType = header;
    }

    public void setContentType(String string) {
        BasicHeader basicHeader = null;
        if (string != null) {
            basicHeader = new BasicHeader("Content-Type", string);
        }
        this.setContentType(basicHeader);
    }

    public void setContentEncoding(Header header) {
        this.contentEncoding = header;
    }

    public void setContentEncoding(String string) {
        BasicHeader basicHeader = null;
        if (string != null) {
            basicHeader = new BasicHeader("Content-Encoding", string);
        }
        this.setContentEncoding(basicHeader);
    }

    public void setChunked(boolean bl) {
        this.chunked = bl;
    }

    @Override
    @Deprecated
    public void consumeContent() throws IOException {
    }

    public String toString() {
        long l;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        if (this.contentType != null) {
            stringBuilder.append("Content-Type: ");
            stringBuilder.append(this.contentType.getValue());
            stringBuilder.append(',');
        }
        if (this.contentEncoding != null) {
            stringBuilder.append("Content-Encoding: ");
            stringBuilder.append(this.contentEncoding.getValue());
            stringBuilder.append(',');
        }
        if ((l = this.getContentLength()) >= 0L) {
            stringBuilder.append("Content-Length: ");
            stringBuilder.append(l);
            stringBuilder.append(',');
        }
        stringBuilder.append("Chunked: ");
        stringBuilder.append(this.chunked);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

