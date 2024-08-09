/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.entity;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;

public class EntityBuilder {
    private String text;
    private byte[] binary;
    private InputStream stream;
    private List<NameValuePair> parameters;
    private Serializable serializable;
    private File file;
    private ContentType contentType;
    private String contentEncoding;
    private boolean chunked;
    private boolean gzipCompress;

    EntityBuilder() {
    }

    public static EntityBuilder create() {
        return new EntityBuilder();
    }

    private void clearContent() {
        this.text = null;
        this.binary = null;
        this.stream = null;
        this.parameters = null;
        this.serializable = null;
        this.file = null;
    }

    public String getText() {
        return this.text;
    }

    public EntityBuilder setText(String string) {
        this.clearContent();
        this.text = string;
        return this;
    }

    public byte[] getBinary() {
        return this.binary;
    }

    public EntityBuilder setBinary(byte[] byArray) {
        this.clearContent();
        this.binary = byArray;
        return this;
    }

    public InputStream getStream() {
        return this.stream;
    }

    public EntityBuilder setStream(InputStream inputStream) {
        this.clearContent();
        this.stream = inputStream;
        return this;
    }

    public List<NameValuePair> getParameters() {
        return this.parameters;
    }

    public EntityBuilder setParameters(List<NameValuePair> list) {
        this.clearContent();
        this.parameters = list;
        return this;
    }

    public EntityBuilder setParameters(NameValuePair ... nameValuePairArray) {
        return this.setParameters(Arrays.asList(nameValuePairArray));
    }

    public Serializable getSerializable() {
        return this.serializable;
    }

    public EntityBuilder setSerializable(Serializable serializable) {
        this.clearContent();
        this.serializable = serializable;
        return this;
    }

    public File getFile() {
        return this.file;
    }

    public EntityBuilder setFile(File file) {
        this.clearContent();
        this.file = file;
        return this;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public EntityBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public EntityBuilder setContentEncoding(String string) {
        this.contentEncoding = string;
        return this;
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public EntityBuilder chunked() {
        this.chunked = true;
        return this;
    }

    public boolean isGzipCompress() {
        return this.gzipCompress;
    }

    public EntityBuilder gzipCompress() {
        this.gzipCompress = true;
        return this;
    }

    private ContentType getContentOrDefault(ContentType contentType) {
        return this.contentType != null ? this.contentType : contentType;
    }

    public HttpEntity build() {
        AbstractHttpEntity abstractHttpEntity;
        if (this.text != null) {
            abstractHttpEntity = new StringEntity(this.text, this.getContentOrDefault(ContentType.DEFAULT_TEXT));
        } else if (this.binary != null) {
            abstractHttpEntity = new ByteArrayEntity(this.binary, this.getContentOrDefault(ContentType.DEFAULT_BINARY));
        } else if (this.stream != null) {
            abstractHttpEntity = new InputStreamEntity(this.stream, -1L, this.getContentOrDefault(ContentType.DEFAULT_BINARY));
        } else if (this.parameters != null) {
            abstractHttpEntity = new UrlEncodedFormEntity(this.parameters, this.contentType != null ? this.contentType.getCharset() : null);
        } else if (this.serializable != null) {
            abstractHttpEntity = new SerializableEntity(this.serializable);
            abstractHttpEntity.setContentType(ContentType.DEFAULT_BINARY.toString());
        } else {
            abstractHttpEntity = this.file != null ? new FileEntity(this.file, this.getContentOrDefault(ContentType.DEFAULT_BINARY)) : new BasicHttpEntity();
        }
        if (abstractHttpEntity.getContentType() != null && this.contentType != null) {
            abstractHttpEntity.setContentType(this.contentType.toString());
        }
        abstractHttpEntity.setContentEncoding(this.contentEncoding);
        abstractHttpEntity.setChunked(this.chunked);
        if (this.gzipCompress) {
            return new GzipCompressingEntity(abstractHttpEntity);
        }
        return abstractHttpEntity;
    }
}

