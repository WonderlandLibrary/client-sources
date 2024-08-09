/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

public class InputStreamEntity
extends AbstractHttpEntity {
    private final InputStream content;
    private final long length;

    public InputStreamEntity(InputStream inputStream) {
        this(inputStream, -1L);
    }

    public InputStreamEntity(InputStream inputStream, long l) {
        this(inputStream, l, null);
    }

    public InputStreamEntity(InputStream inputStream, ContentType contentType) {
        this(inputStream, -1L, contentType);
    }

    public InputStreamEntity(InputStream inputStream, long l, ContentType contentType) {
        this.content = Args.notNull(inputStream, "Source input stream");
        this.length = l;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public long getContentLength() {
        return this.length;
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.content;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        block7: {
            Args.notNull(outputStream, "Output stream");
            InputStream inputStream = this.content;
            try {
                int n;
                byte[] byArray = new byte[4096];
                if (this.length < 0L) {
                    int n2;
                    while ((n2 = inputStream.read(byArray)) != -1) {
                        outputStream.write(byArray, 0, n2);
                    }
                    break block7;
                }
                for (long i = this.length; i > 0L; i -= (long)n) {
                    n = inputStream.read(byArray, 0, (int)Math.min(4096L, i));
                    if (n == -1) {
                        break;
                    }
                    outputStream.write(byArray, 0, n);
                }
            } finally {
                inputStream.close();
            }
        }
    }

    @Override
    public boolean isStreaming() {
        return false;
    }
}

