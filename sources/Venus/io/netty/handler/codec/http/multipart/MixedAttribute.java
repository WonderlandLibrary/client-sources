/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.ReferenceCounted;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MixedAttribute
implements Attribute {
    private Attribute attribute;
    private final long limitSize;
    private long maxSize = -1L;

    public MixedAttribute(String string, long l) {
        this(string, l, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String string, long l, long l2) {
        this(string, l, l2, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String string, long l, Charset charset) {
        this.limitSize = l;
        this.attribute = new MemoryAttribute(string, charset);
    }

    public MixedAttribute(String string, long l, long l2, Charset charset) {
        this.limitSize = l2;
        this.attribute = new MemoryAttribute(string, l, charset);
    }

    public MixedAttribute(String string, String string2, long l) {
        this(string, string2, l, HttpConstants.DEFAULT_CHARSET);
    }

    public MixedAttribute(String string, String string2, long l, Charset charset) {
        this.limitSize = l;
        if ((long)string2.length() > this.limitSize) {
            try {
                this.attribute = new DiskAttribute(string, string2, charset);
            } catch (IOException iOException) {
                try {
                    this.attribute = new MemoryAttribute(string, string2, charset);
                } catch (IOException iOException2) {
                    throw new IllegalArgumentException(iOException);
                }
            }
        } else {
            try {
                this.attribute = new MemoryAttribute(string, string2, charset);
            } catch (IOException iOException) {
                throw new IllegalArgumentException(iOException);
            }
        }
    }

    @Override
    public long getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void setMaxSize(long l) {
        this.maxSize = l;
        this.attribute.setMaxSize(l);
    }

    @Override
    public void checkSize(long l) throws IOException {
        if (this.maxSize >= 0L && l > this.maxSize) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override
    public void addContent(ByteBuf byteBuf, boolean bl) throws IOException {
        if (this.attribute instanceof MemoryAttribute) {
            this.checkSize(this.attribute.length() + (long)byteBuf.readableBytes());
            if (this.attribute.length() + (long)byteBuf.readableBytes() > this.limitSize) {
                DiskAttribute diskAttribute = new DiskAttribute(this.attribute.getName(), this.attribute.definedLength());
                diskAttribute.setMaxSize(this.maxSize);
                if (((MemoryAttribute)this.attribute).getByteBuf() != null) {
                    diskAttribute.addContent(((MemoryAttribute)this.attribute).getByteBuf(), true);
                }
                this.attribute = diskAttribute;
            }
        }
        this.attribute.addContent(byteBuf, bl);
    }

    @Override
    public void delete() {
        this.attribute.delete();
    }

    @Override
    public byte[] get() throws IOException {
        return this.attribute.get();
    }

    @Override
    public ByteBuf getByteBuf() throws IOException {
        return this.attribute.getByteBuf();
    }

    @Override
    public Charset getCharset() {
        return this.attribute.getCharset();
    }

    @Override
    public String getString() throws IOException {
        return this.attribute.getString();
    }

    @Override
    public String getString(Charset charset) throws IOException {
        return this.attribute.getString(charset);
    }

    @Override
    public boolean isCompleted() {
        return this.attribute.isCompleted();
    }

    @Override
    public boolean isInMemory() {
        return this.attribute.isInMemory();
    }

    @Override
    public long length() {
        return this.attribute.length();
    }

    @Override
    public long definedLength() {
        return this.attribute.definedLength();
    }

    @Override
    public boolean renameTo(File file) throws IOException {
        return this.attribute.renameTo(file);
    }

    @Override
    public void setCharset(Charset charset) {
        this.attribute.setCharset(charset);
    }

    @Override
    public void setContent(ByteBuf byteBuf) throws IOException {
        this.checkSize(byteBuf.readableBytes());
        if ((long)byteBuf.readableBytes() > this.limitSize && this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName(), this.attribute.definedLength());
            this.attribute.setMaxSize(this.maxSize);
        }
        this.attribute.setContent(byteBuf);
    }

    @Override
    public void setContent(File file) throws IOException {
        this.checkSize(file.length());
        if (file.length() > this.limitSize && this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName(), this.attribute.definedLength());
            this.attribute.setMaxSize(this.maxSize);
        }
        this.attribute.setContent(file);
    }

    @Override
    public void setContent(InputStream inputStream) throws IOException {
        if (this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName(), this.attribute.definedLength());
            this.attribute.setMaxSize(this.maxSize);
        }
        this.attribute.setContent(inputStream);
    }

    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.attribute.getHttpDataType();
    }

    @Override
    public String getName() {
        return this.attribute.getName();
    }

    public int hashCode() {
        return this.attribute.hashCode();
    }

    public boolean equals(Object object) {
        return this.attribute.equals(object);
    }

    @Override
    public int compareTo(InterfaceHttpData interfaceHttpData) {
        return this.attribute.compareTo(interfaceHttpData);
    }

    public String toString() {
        return "Mixed: " + this.attribute;
    }

    @Override
    public String getValue() throws IOException {
        return this.attribute.getValue();
    }

    @Override
    public void setValue(String string) throws IOException {
        if (string != null) {
            this.checkSize(string.getBytes().length);
        }
        this.attribute.setValue(string);
    }

    @Override
    public ByteBuf getChunk(int n) throws IOException {
        return this.attribute.getChunk(n);
    }

    @Override
    public File getFile() throws IOException {
        return this.attribute.getFile();
    }

    @Override
    public Attribute copy() {
        return this.attribute.copy();
    }

    @Override
    public Attribute duplicate() {
        return this.attribute.duplicate();
    }

    @Override
    public Attribute retainedDuplicate() {
        return this.attribute.retainedDuplicate();
    }

    @Override
    public Attribute replace(ByteBuf byteBuf) {
        return this.attribute.replace(byteBuf);
    }

    @Override
    public ByteBuf content() {
        return this.attribute.content();
    }

    @Override
    public int refCnt() {
        return this.attribute.refCnt();
    }

    @Override
    public Attribute retain() {
        this.attribute.retain();
        return this;
    }

    @Override
    public Attribute retain(int n) {
        this.attribute.retain(n);
        return this;
    }

    @Override
    public Attribute touch() {
        this.attribute.touch();
        return this;
    }

    @Override
    public Attribute touch(Object object) {
        this.attribute.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.attribute.release();
    }

    @Override
    public boolean release(int n) {
        return this.attribute.release(n);
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

