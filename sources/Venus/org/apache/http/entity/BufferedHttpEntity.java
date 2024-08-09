/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.Args;

public class BufferedHttpEntity
extends HttpEntityWrapper {
    private final byte[] buffer;

    public BufferedHttpEntity(HttpEntity httpEntity) throws IOException {
        super(httpEntity);
        if (!httpEntity.isRepeatable() || httpEntity.getContentLength() < 0L) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            httpEntity.writeTo(byteArrayOutputStream);
            byteArrayOutputStream.flush();
            this.buffer = byteArrayOutputStream.toByteArray();
        } else {
            this.buffer = null;
        }
    }

    @Override
    public long getContentLength() {
        return this.buffer != null ? (long)this.buffer.length : super.getContentLength();
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.buffer != null ? new ByteArrayInputStream(this.buffer) : super.getContent();
    }

    @Override
    public boolean isChunked() {
        return this.buffer == null && super.isChunked();
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        if (this.buffer != null) {
            outputStream.write(this.buffer);
        } else {
            super.writeTo(outputStream);
        }
    }

    @Override
    public boolean isStreaming() {
        return this.buffer == null && super.isStreaming();
    }
}

