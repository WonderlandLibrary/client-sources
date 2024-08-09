/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.multipart.AbstractMemoryHttpData;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.FileUploadUtil;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MemoryFileUpload
extends AbstractMemoryHttpData
implements FileUpload {
    private String filename;
    private String contentType;
    private String contentTransferEncoding;

    public MemoryFileUpload(String string, String string2, String string3, String string4, Charset charset, long l) {
        super(string, charset, l);
        this.setFilename(string2);
        this.setContentType(string3);
        this.setContentTransferEncoding(string4);
    }

    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.FileUpload;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public void setFilename(String string) {
        if (string == null) {
            throw new NullPointerException("filename");
        }
        this.filename = string;
    }

    public int hashCode() {
        return FileUploadUtil.hashCode(this);
    }

    public boolean equals(Object object) {
        return object instanceof FileUpload && FileUploadUtil.equals(this, (FileUpload)object);
    }

    @Override
    public int compareTo(InterfaceHttpData interfaceHttpData) {
        if (!(interfaceHttpData instanceof FileUpload)) {
            throw new ClassCastException("Cannot compare " + (Object)((Object)this.getHttpDataType()) + " with " + (Object)((Object)interfaceHttpData.getHttpDataType()));
        }
        return this.compareTo((FileUpload)interfaceHttpData);
    }

    @Override
    public int compareTo(FileUpload fileUpload) {
        return FileUploadUtil.compareTo(this, fileUpload);
    }

    @Override
    public void setContentType(String string) {
        if (string == null) {
            throw new NullPointerException("contentType");
        }
        this.contentType = string;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getContentTransferEncoding() {
        return this.contentTransferEncoding;
    }

    @Override
    public void setContentTransferEncoding(String string) {
        this.contentTransferEncoding = string;
    }

    public String toString() {
        return HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + this.getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + this.filename + "\"\r\n" + HttpHeaderNames.CONTENT_TYPE + ": " + this.contentType + (this.getCharset() != null ? "; " + HttpHeaderValues.CHARSET + '=' + this.getCharset().name() + "\r\n" : "\r\n") + HttpHeaderNames.CONTENT_LENGTH + ": " + this.length() + "\r\nCompleted: " + this.isCompleted() + "\r\nIsInMemory: " + this.isInMemory();
    }

    @Override
    public FileUpload copy() {
        ByteBuf byteBuf = this.content();
        return this.replace(byteBuf != null ? byteBuf.copy() : byteBuf);
    }

    @Override
    public FileUpload duplicate() {
        ByteBuf byteBuf = this.content();
        return this.replace(byteBuf != null ? byteBuf.duplicate() : byteBuf);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FileUpload retainedDuplicate() {
        ByteBuf byteBuf = this.content();
        if (byteBuf != null) {
            byteBuf = byteBuf.retainedDuplicate();
            boolean bl = false;
            try {
                FileUpload fileUpload = this.replace(byteBuf);
                bl = true;
                FileUpload fileUpload2 = fileUpload;
                return fileUpload2;
            } finally {
                if (!bl) {
                    byteBuf.release();
                }
            }
        }
        return this.replace(null);
    }

    @Override
    public FileUpload replace(ByteBuf byteBuf) {
        MemoryFileUpload memoryFileUpload = new MemoryFileUpload(this.getName(), this.getFilename(), this.getContentType(), this.getContentTransferEncoding(), this.getCharset(), this.size);
        if (byteBuf != null) {
            try {
                memoryFileUpload.setContent(byteBuf);
                return memoryFileUpload;
            } catch (IOException iOException) {
                throw new ChannelException(iOException);
            }
        }
        return memoryFileUpload;
    }

    @Override
    public FileUpload retain() {
        super.retain();
        return this;
    }

    @Override
    public FileUpload retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public FileUpload touch() {
        super.touch();
        return this;
    }

    @Override
    public FileUpload touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public HttpData touch(Object object) {
        return this.touch(object);
    }

    @Override
    public HttpData touch() {
        return this.touch();
    }

    @Override
    public HttpData retain(int n) {
        return this.retain(n);
    }

    @Override
    public HttpData retain() {
        return this.retain();
    }

    @Override
    public HttpData replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public HttpData retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public HttpData duplicate() {
        return this.duplicate();
    }

    @Override
    public HttpData copy() {
        return this.copy();
    }

    @Override
    public InterfaceHttpData touch(Object object) {
        return this.touch(object);
    }

    @Override
    public InterfaceHttpData touch() {
        return this.touch();
    }

    @Override
    public InterfaceHttpData retain(int n) {
        return this.retain(n);
    }

    @Override
    public InterfaceHttpData retain() {
        return this.retain();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((InterfaceHttpData)object);
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }
}

