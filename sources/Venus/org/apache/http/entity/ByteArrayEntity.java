/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

public class ByteArrayEntity
extends AbstractHttpEntity
implements Cloneable {
    @Deprecated
    protected final byte[] content;
    private final byte[] b;
    private final int off;
    private final int len;

    public ByteArrayEntity(byte[] byArray, ContentType contentType) {
        Args.notNull(byArray, "Source byte array");
        this.content = byArray;
        this.b = byArray;
        this.off = 0;
        this.len = this.b.length;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }

    public ByteArrayEntity(byte[] byArray, int n, int n2, ContentType contentType) {
        Args.notNull(byArray, "Source byte array");
        if (n < 0 || n > byArray.length || n2 < 0 || n + n2 < 0 || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("off: " + n + " len: " + n2 + " b.length: " + byArray.length);
        }
        this.content = byArray;
        this.b = byArray;
        this.off = n;
        this.len = n2;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }

    public ByteArrayEntity(byte[] byArray) {
        this(byArray, null);
    }

    public ByteArrayEntity(byte[] byArray, int n, int n2) {
        this(byArray, n, n2, null);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public long getContentLength() {
        return this.len;
    }

    @Override
    public InputStream getContent() {
        return new ByteArrayInputStream(this.b, this.off, this.len);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        outputStream.write(this.b, this.off, this.len);
        outputStream.flush();
    }

    @Override
    public boolean isStreaming() {
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

