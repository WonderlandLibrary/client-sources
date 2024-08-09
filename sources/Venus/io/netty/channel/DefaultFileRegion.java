/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.FileRegion;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultFileRegion
extends AbstractReferenceCounted
implements FileRegion {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
    private final File f;
    private final long position;
    private final long count;
    private long transferred;
    private FileChannel file;

    public DefaultFileRegion(FileChannel fileChannel, long l, long l2) {
        if (fileChannel == null) {
            throw new NullPointerException("file");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("position must be >= 0 but was " + l);
        }
        if (l2 < 0L) {
            throw new IllegalArgumentException("count must be >= 0 but was " + l2);
        }
        this.file = fileChannel;
        this.position = l;
        this.count = l2;
        this.f = null;
    }

    public DefaultFileRegion(File file, long l, long l2) {
        if (file == null) {
            throw new NullPointerException("f");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("position must be >= 0 but was " + l);
        }
        if (l2 < 0L) {
            throw new IllegalArgumentException("count must be >= 0 but was " + l2);
        }
        this.position = l;
        this.count = l2;
        this.f = file;
    }

    public boolean isOpen() {
        return this.file != null;
    }

    public void open() throws IOException {
        if (!this.isOpen() && this.refCnt() > 0) {
            this.file = new RandomAccessFile(this.f, "r").getChannel();
        }
    }

    @Override
    public long position() {
        return this.position;
    }

    @Override
    public long count() {
        return this.count;
    }

    @Override
    @Deprecated
    public long transfered() {
        return this.transferred;
    }

    @Override
    public long transferred() {
        return this.transferred;
    }

    @Override
    public long transferTo(WritableByteChannel writableByteChannel, long l) throws IOException {
        long l2 = this.count - l;
        if (l2 < 0L || l < 0L) {
            throw new IllegalArgumentException("position out of range: " + l + " (expected: 0 - " + (this.count - 1L) + ')');
        }
        if (l2 == 0L) {
            return 0L;
        }
        if (this.refCnt() == 0) {
            throw new IllegalReferenceCountException(0);
        }
        this.open();
        long l3 = this.file.transferTo(this.position + l, l2, writableByteChannel);
        if (l3 > 0L) {
            this.transferred += l3;
        }
        return l3;
    }

    @Override
    protected void deallocate() {
        block3: {
            FileChannel fileChannel = this.file;
            if (fileChannel == null) {
                return;
            }
            this.file = null;
            try {
                fileChannel.close();
            } catch (IOException iOException) {
                if (!logger.isWarnEnabled()) break block3;
                logger.warn("Failed to close a file.", iOException);
            }
        }
    }

    @Override
    public FileRegion retain() {
        super.retain();
        return this;
    }

    @Override
    public FileRegion retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public FileRegion touch() {
        return this;
    }

    @Override
    public FileRegion touch(Object object) {
        return this;
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
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }
}

