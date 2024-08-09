/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public class BasicHttpEntity
extends AbstractHttpEntity {
    private InputStream content;
    private long length = -1L;

    @Override
    public long getContentLength() {
        return this.length;
    }

    @Override
    public InputStream getContent() throws IllegalStateException {
        Asserts.check(this.content != null, "Content has not been provided");
        return this.content;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    public void setContentLength(long l) {
        this.length = l;
    }

    public void setContent(InputStream inputStream) {
        this.content = inputStream;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        InputStream inputStream = this.getContent();
        try {
            int n;
            byte[] byArray = new byte[4096];
            while ((n = inputStream.read(byArray)) != -1) {
                outputStream.write(byArray, 0, n);
            }
        } finally {
            inputStream.close();
        }
    }

    @Override
    public boolean isStreaming() {
        return this.content != null && this.content != EmptyInputStream.INSTANCE;
    }
}

