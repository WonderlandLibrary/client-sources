/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.AbstractHttpData;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
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
public abstract class AbstractDiskHttpData
extends AbstractHttpData {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
    private File file;
    private boolean isRenamed;
    private FileChannel fileChannel;

    protected AbstractDiskHttpData(String string, Charset charset, long l) {
        super(string, charset, l);
    }

    protected abstract String getDiskFilename();

    protected abstract String getPrefix();

    protected abstract String getBaseDirectory();

    protected abstract String getPostfix();

    protected abstract boolean deleteOnExit();

    private File tempFile() throws IOException {
        String string = this.getDiskFilename();
        String string2 = string != null ? '_' + string : this.getPostfix();
        File file = this.getBaseDirectory() == null ? File.createTempFile(this.getPrefix(), string2) : File.createTempFile(this.getPrefix(), string2, new File(this.getBaseDirectory()));
        if (this.deleteOnExit()) {
            file.deleteOnExit();
        }
        return file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setContent(ByteBuf byteBuf) throws IOException {
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        try {
            this.size = byteBuf.readableBytes();
            this.checkSize(this.size);
            if (this.definedSize > 0L && this.definedSize < this.size) {
                throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
            }
            if (this.file == null) {
                this.file = this.tempFile();
            }
            if (byteBuf.readableBytes() == 0) {
                if (!this.file.createNewFile()) {
                    if (this.file.length() == 0L) {
                        return;
                    }
                    if (!this.file.delete() || !this.file.createNewFile()) {
                        throw new IOException("file exists already: " + this.file);
                    }
                }
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            try {
                FileChannel fileChannel = fileOutputStream.getChannel();
                ByteBuffer byteBuffer = byteBuf.nioBuffer();
                int n = 0;
                while ((long)n < this.size) {
                    n += fileChannel.write(byteBuffer);
                }
                byteBuf.readerIndex(byteBuf.readerIndex() + n);
                fileChannel.force(true);
            } finally {
                fileOutputStream.close();
            }
            this.setCompleted();
        } finally {
            byteBuf.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addContent(ByteBuf byteBuf, boolean bl) throws IOException {
        if (byteBuf != null) {
            try {
                int n = byteBuf.readableBytes();
                this.checkSize(this.size + (long)n);
                if (this.definedSize > 0L && this.definedSize < this.size + (long)n) {
                    throw new IOException("Out of size: " + (this.size + (long)n) + " > " + this.definedSize);
                }
                ByteBuffer byteBuffer = byteBuf.nioBufferCount() == 1 ? byteBuf.nioBuffer() : byteBuf.copy().nioBuffer();
                int n2 = 0;
                if (this.file == null) {
                    this.file = this.tempFile();
                }
                if (this.fileChannel == null) {
                    FileOutputStream fileOutputStream = new FileOutputStream(this.file);
                    this.fileChannel = fileOutputStream.getChannel();
                }
                while (n2 < n) {
                    n2 += this.fileChannel.write(byteBuffer);
                }
                this.size += (long)n;
                byteBuf.readerIndex(byteBuf.readerIndex() + n2);
            } finally {
                byteBuf.release();
            }
        }
        if (bl) {
            if (this.file == null) {
                this.file = this.tempFile();
            }
            if (this.fileChannel == null) {
                FileOutputStream fileOutputStream = new FileOutputStream(this.file);
                this.fileChannel = fileOutputStream.getChannel();
            }
            this.fileChannel.force(true);
            this.fileChannel.close();
            this.fileChannel = null;
            this.setCompleted();
        } else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }

    @Override
    public void setContent(File file) throws IOException {
        if (this.file != null) {
            this.delete();
        }
        this.file = file;
        this.size = file.length();
        this.checkSize(this.size);
        this.isRenamed = true;
        this.setCompleted();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        if (this.file != null) {
            this.delete();
        }
        this.file = this.tempFile();
        FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        int n = 0;
        try {
            FileChannel fileChannel = fileOutputStream.getChannel();
            byte[] byArray = new byte[16384];
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n2 = inputStream.read(byArray);
            while (n2 > 0) {
                byteBuffer.position(n2).flip();
                this.checkSize(n += fileChannel.write(byteBuffer));
                n2 = inputStream.read(byArray);
            }
            fileChannel.force(true);
        } finally {
            fileOutputStream.close();
        }
        this.size = n;
        if (this.definedSize > 0L && this.definedSize < this.size) {
            if (!this.file.delete()) {
                logger.warn("Failed to delete: {}", (Object)this.file);
            }
            this.file = null;
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        this.isRenamed = true;
        this.setCompleted();
    }

    @Override
    public void delete() {
        if (this.fileChannel != null) {
            try {
                this.fileChannel.force(true);
                this.fileChannel.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close a file.", iOException);
            }
            this.fileChannel = null;
        }
        if (!this.isRenamed) {
            if (this.file != null && this.file.exists() && !this.file.delete()) {
                logger.warn("Failed to delete: {}", (Object)this.file);
            }
            this.file = null;
        }
    }

    @Override
    public byte[] get() throws IOException {
        if (this.file == null) {
            return EmptyArrays.EMPTY_BYTES;
        }
        return AbstractDiskHttpData.readFrom(this.file);
    }

    @Override
    public ByteBuf getByteBuf() throws IOException {
        if (this.file == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        byte[] byArray = AbstractDiskHttpData.readFrom(this.file);
        return Unpooled.wrappedBuffer(byArray);
    }

    @Override
    public ByteBuf getChunk(int n) throws IOException {
        int n2;
        int n3;
        if (this.file == null || n == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (this.fileChannel == null) {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            this.fileChannel = fileInputStream.getChannel();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        for (n2 = 0; n2 < n; n2 += n3) {
            n3 = this.fileChannel.read(byteBuffer);
            if (n3 != -1) continue;
            this.fileChannel.close();
            this.fileChannel = null;
            break;
        }
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        byteBuffer.flip();
        ByteBuf byteBuf = Unpooled.wrappedBuffer(byteBuffer);
        byteBuf.readerIndex(0);
        byteBuf.writerIndex(n2);
        return byteBuf;
    }

    @Override
    public String getString() throws IOException {
        return this.getString(HttpConstants.DEFAULT_CHARSET);
    }

    @Override
    public String getString(Charset charset) throws IOException {
        if (this.file == null) {
            return "";
        }
        if (charset == null) {
            byte[] byArray = AbstractDiskHttpData.readFrom(this.file);
            return new String(byArray, HttpConstants.DEFAULT_CHARSET.name());
        }
        byte[] byArray = AbstractDiskHttpData.readFrom(this.file);
        return new String(byArray, charset.name());
    }

    @Override
    public boolean isInMemory() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean renameTo(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("dest");
        }
        if (this.file == null) {
            throw new IOException("No file defined so cannot be renamed");
        }
        if (!this.file.renameTo(file)) {
            long l;
            IOException iOException = null;
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            long l2 = 8196L;
            try {
                fileInputStream = new FileInputStream(this.file);
                fileOutputStream = new FileOutputStream(file);
                FileChannel fileChannel = fileInputStream.getChannel();
                FileChannel fileChannel2 = fileOutputStream.getChannel();
                for (l = 0L; l < this.size; l += fileChannel.transferTo(l, l2, fileChannel2)) {
                    if (l2 >= this.size - l) continue;
                    l2 = this.size - l;
                }
            } catch (IOException iOException2) {
                iOException = iOException2;
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException iOException3) {
                        if (iOException == null) {
                            iOException = iOException3;
                        }
                        logger.warn("Multiple exceptions detected, the following will be suppressed {}", iOException3);
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException iOException4) {
                        if (iOException == null) {
                            iOException = iOException4;
                        }
                        logger.warn("Multiple exceptions detected, the following will be suppressed {}", iOException4);
                    }
                }
            }
            if (iOException != null) {
                throw iOException;
            }
            if (l == this.size) {
                if (!this.file.delete()) {
                    logger.warn("Failed to delete: {}", (Object)this.file);
                }
                this.file = file;
                this.isRenamed = true;
                return false;
            }
            if (!file.delete()) {
                logger.warn("Failed to delete: {}", (Object)file);
            }
            return true;
        }
        this.file = file;
        this.isRenamed = true;
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] readFrom(File file) throws IOException {
        long l = file.length();
        if (l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] byArray = new byte[(int)l];
        try {
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n = 0;
            while ((long)n < l) {
                n += fileChannel.read(byteBuffer);
            }
        } finally {
            fileInputStream.close();
        }
        return byArray;
    }

    @Override
    public File getFile() throws IOException {
        return this.file;
    }

    @Override
    public HttpData touch() {
        return this;
    }

    @Override
    public HttpData touch(Object object) {
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

