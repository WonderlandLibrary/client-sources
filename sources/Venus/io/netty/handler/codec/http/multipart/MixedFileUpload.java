/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryFileUpload;
import io.netty.util.ReferenceCounted;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MixedFileUpload
implements FileUpload {
    private FileUpload fileUpload;
    private final long limitSize;
    private final long definedSize;
    private long maxSize = -1L;

    public MixedFileUpload(String string, String string2, String string3, String string4, Charset charset, long l, long l2) {
        this.limitSize = l2;
        this.fileUpload = l > this.limitSize ? new DiskFileUpload(string, string2, string3, string4, charset, l) : new MemoryFileUpload(string, string2, string3, string4, charset, l);
        this.definedSize = l;
    }

    @Override
    public long getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void setMaxSize(long l) {
        this.maxSize = l;
        this.fileUpload.setMaxSize(l);
    }

    @Override
    public void checkSize(long l) throws IOException {
        if (this.maxSize >= 0L && l > this.maxSize) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override
    public void addContent(ByteBuf byteBuf, boolean bl) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload) {
            this.checkSize(this.fileUpload.length() + (long)byteBuf.readableBytes());
            if (this.fileUpload.length() + (long)byteBuf.readableBytes() > this.limitSize) {
                DiskFileUpload diskFileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
                diskFileUpload.setMaxSize(this.maxSize);
                ByteBuf byteBuf2 = this.fileUpload.getByteBuf();
                if (byteBuf2 != null && byteBuf2.isReadable()) {
                    diskFileUpload.addContent(byteBuf2.retain(), true);
                }
                this.fileUpload.release();
                this.fileUpload = diskFileUpload;
            }
        }
        this.fileUpload.addContent(byteBuf, bl);
    }

    @Override
    public void delete() {
        this.fileUpload.delete();
    }

    @Override
    public byte[] get() throws IOException {
        return this.fileUpload.get();
    }

    @Override
    public ByteBuf getByteBuf() throws IOException {
        return this.fileUpload.getByteBuf();
    }

    @Override
    public Charset getCharset() {
        return this.fileUpload.getCharset();
    }

    @Override
    public String getContentType() {
        return this.fileUpload.getContentType();
    }

    @Override
    public String getContentTransferEncoding() {
        return this.fileUpload.getContentTransferEncoding();
    }

    @Override
    public String getFilename() {
        return this.fileUpload.getFilename();
    }

    @Override
    public String getString() throws IOException {
        return this.fileUpload.getString();
    }

    @Override
    public String getString(Charset charset) throws IOException {
        return this.fileUpload.getString(charset);
    }

    @Override
    public boolean isCompleted() {
        return this.fileUpload.isCompleted();
    }

    @Override
    public boolean isInMemory() {
        return this.fileUpload.isInMemory();
    }

    @Override
    public long length() {
        return this.fileUpload.length();
    }

    @Override
    public long definedLength() {
        return this.fileUpload.definedLength();
    }

    @Override
    public boolean renameTo(File file) throws IOException {
        return this.fileUpload.renameTo(file);
    }

    @Override
    public void setCharset(Charset charset) {
        this.fileUpload.setCharset(charset);
    }

    @Override
    public void setContent(ByteBuf byteBuf) throws IOException {
        this.checkSize(byteBuf.readableBytes());
        if ((long)byteBuf.readableBytes() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.definedSize);
            this.fileUpload.setMaxSize(this.maxSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(byteBuf);
    }

    @Override
    public void setContent(File file) throws IOException {
        this.checkSize(file.length());
        if (file.length() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.definedSize);
            this.fileUpload.setMaxSize(this.maxSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(file);
    }

    @Override
    public void setContent(InputStream inputStream) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload) {
            FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
            this.fileUpload.setMaxSize(this.maxSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(inputStream);
    }

    @Override
    public void setContentType(String string) {
        this.fileUpload.setContentType(string);
    }

    @Override
    public void setContentTransferEncoding(String string) {
        this.fileUpload.setContentTransferEncoding(string);
    }

    @Override
    public void setFilename(String string) {
        this.fileUpload.setFilename(string);
    }

    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.fileUpload.getHttpDataType();
    }

    @Override
    public String getName() {
        return this.fileUpload.getName();
    }

    public int hashCode() {
        return this.fileUpload.hashCode();
    }

    public boolean equals(Object object) {
        return this.fileUpload.equals(object);
    }

    @Override
    public int compareTo(InterfaceHttpData interfaceHttpData) {
        return this.fileUpload.compareTo(interfaceHttpData);
    }

    public String toString() {
        return "Mixed: " + this.fileUpload;
    }

    @Override
    public ByteBuf getChunk(int n) throws IOException {
        return this.fileUpload.getChunk(n);
    }

    @Override
    public File getFile() throws IOException {
        return this.fileUpload.getFile();
    }

    @Override
    public FileUpload copy() {
        return this.fileUpload.copy();
    }

    @Override
    public FileUpload duplicate() {
        return this.fileUpload.duplicate();
    }

    @Override
    public FileUpload retainedDuplicate() {
        return this.fileUpload.retainedDuplicate();
    }

    @Override
    public FileUpload replace(ByteBuf byteBuf) {
        return this.fileUpload.replace(byteBuf);
    }

    @Override
    public ByteBuf content() {
        return this.fileUpload.content();
    }

    @Override
    public int refCnt() {
        return this.fileUpload.refCnt();
    }

    @Override
    public FileUpload retain() {
        this.fileUpload.retain();
        return this;
    }

    @Override
    public FileUpload retain(int n) {
        this.fileUpload.retain(n);
        return this;
    }

    @Override
    public FileUpload touch() {
        this.fileUpload.touch();
        return this;
    }

    @Override
    public FileUpload touch(Object object) {
        this.fileUpload.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.fileUpload.release();
    }

    @Override
    public boolean release(int n) {
        return this.fileUpload.release(n);
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

