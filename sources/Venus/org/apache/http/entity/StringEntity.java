/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

public class StringEntity
extends AbstractHttpEntity
implements Cloneable {
    protected final byte[] content;

    public StringEntity(String string, ContentType contentType) throws UnsupportedCharsetException {
        Charset charset;
        Args.notNull(string, "Source string");
        Charset charset2 = charset = contentType != null ? contentType.getCharset() : null;
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }
        this.content = string.getBytes(charset);
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }

    @Deprecated
    public StringEntity(String string, String string2, String string3) throws UnsupportedEncodingException {
        Args.notNull(string, "Source string");
        String string4 = string2 != null ? string2 : "text/plain";
        String string5 = string3 != null ? string3 : "ISO-8859-1";
        this.content = string.getBytes(string5);
        this.setContentType(string4 + "; charset=" + string5);
    }

    public StringEntity(String string, String string2) throws UnsupportedCharsetException {
        this(string, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), string2));
    }

    public StringEntity(String string, Charset charset) {
        this(string, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset));
    }

    public StringEntity(String string) throws UnsupportedEncodingException {
        this(string, ContentType.DEFAULT_TEXT);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public long getContentLength() {
        return this.content.length;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        outputStream.write(this.content);
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

