/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.AbstractHttpData;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCounted;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractMemoryHttpData
extends AbstractHttpData {
    private ByteBuf byteBuf;
    private int chunkPosition;

    protected AbstractMemoryHttpData(String string, Charset charset, long l) {
        super(string, charset, l);
    }

    @Override
    public void setContent(ByteBuf byteBuf) throws IOException {
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        long l = byteBuf.readableBytes();
        this.checkSize(l);
        if (this.definedSize > 0L && this.definedSize < l) {
            throw new IOException("Out of size: " + l + " > " + this.definedSize);
        }
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = byteBuf;
        this.size = l;
        this.setCompleted();
    }

    @Override
    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        ByteBuf byteBuf = Unpooled.buffer();
        byte[] byArray = new byte[16384];
        int n = inputStream.read(byArray);
        int n2 = 0;
        while (n > 0) {
            byteBuf.writeBytes(byArray, 0, n);
            this.checkSize(n2 += n);
            n = inputStream.read(byArray);
        }
        this.size = n2;
        if (this.definedSize > 0L && this.definedSize < this.size) {
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = byteBuf;
        this.setCompleted();
    }

    @Override
    public void addContent(ByteBuf byteBuf, boolean bl) throws IOException {
        if (byteBuf != null) {
            long l = byteBuf.readableBytes();
            this.checkSize(this.size + l);
            if (this.definedSize > 0L && this.definedSize < this.size + l) {
                throw new IOException("Out of size: " + (this.size + l) + " > " + this.definedSize);
            }
            this.size += l;
            if (this.byteBuf == null) {
                this.byteBuf = byteBuf;
            } else if (this.byteBuf instanceof CompositeByteBuf) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf)this.byteBuf;
                compositeByteBuf.addComponent(true, byteBuf);
            } else {
                CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer(Integer.MAX_VALUE);
                compositeByteBuf.addComponents(true, this.byteBuf, byteBuf);
                this.byteBuf = compositeByteBuf;
            }
        }
        if (bl) {
            this.setCompleted();
        } else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }

    @Override
    public void setContent(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
        }
        long l = file.length();
        if (l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        this.checkSize(l);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        byte[] byArray = new byte[(int)l];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n = 0;
        while ((long)n < l) {
            n += fileChannel.read(byteBuffer);
        }
        fileChannel.close();
        fileInputStream.close();
        byteBuffer.flip();
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = Unpooled.wrappedBuffer(Integer.MAX_VALUE, byteBuffer);
        this.size = l;
        this.setCompleted();
    }

    @Override
    public void delete() {
        if (this.byteBuf != null) {
            this.byteBuf.release();
            this.byteBuf = null;
        }
    }

    @Override
    public byte[] get() {
        if (this.byteBuf == null) {
            return Unpooled.EMPTY_BUFFER.array();
        }
        byte[] byArray = new byte[this.byteBuf.readableBytes()];
        this.byteBuf.getBytes(this.byteBuf.readerIndex(), byArray);
        return byArray;
    }

    @Override
    public String getString() {
        return this.getString(HttpConstants.DEFAULT_CHARSET);
    }

    @Override
    public String getString(Charset charset) {
        if (this.byteBuf == null) {
            return "";
        }
        if (charset == null) {
            charset = HttpConstants.DEFAULT_CHARSET;
        }
        return this.byteBuf.toString(charset);
    }

    @Override
    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }

    @Override
    public ByteBuf getChunk(int n) throws IOException {
        if (this.byteBuf == null || n == 0 || this.byteBuf.readableBytes() == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int n2 = this.byteBuf.readableBytes() - this.chunkPosition;
        if (n2 == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int n3 = n;
        if (n2 < n) {
            n3 = n2;
        }
        ByteBuf byteBuf = this.byteBuf.retainedSlice(this.chunkPosition, n3);
        this.chunkPosition += n3;
        return byteBuf;
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public boolean renameTo(File file) throws IOException {
        int n;
        if (file == null) {
            throw new NullPointerException("dest");
        }
        if (this.byteBuf == null) {
            if (!file.createNewFile()) {
                throw new IOException("file exists already: " + file);
            }
            return false;
        }
        int n2 = this.byteBuf.readableBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        if (this.byteBuf.nioBufferCount() == 1) {
            ByteBuffer byteBuffer = this.byteBuf.nioBuffer();
            for (n = 0; n < n2; n += fileChannel.write(byteBuffer)) {
            }
        } else {
            ByteBuffer[] byteBufferArray = this.byteBuf.nioBuffers();
            while (n < n2) {
                n = (int)((long)n + fileChannel.write(byteBufferArray));
            }
        }
        fileChannel.force(true);
        fileChannel.close();
        fileOutputStream.close();
        return n == n2;
    }

    @Override
    public File getFile() throws IOException {
        throw new IOException("Not represented by a file");
    }

    @Override
    public HttpData touch() {
        return this.touch(null);
    }

    @Override
    public HttpData touch(Object object) {
        if (this.byteBuf != null) {
            this.byteBuf.touch(object);
        }
        return this;
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
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }
}

