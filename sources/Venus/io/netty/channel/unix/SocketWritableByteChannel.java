/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.unix.FileDescriptor;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public abstract class SocketWritableByteChannel
implements WritableByteChannel {
    private final FileDescriptor fd;

    protected SocketWritableByteChannel(FileDescriptor fileDescriptor) {
        this.fd = ObjectUtil.checkNotNull(fileDescriptor, "fd");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final int write(ByteBuffer byteBuffer) throws IOException {
        int n;
        int n2 = byteBuffer.position();
        int n3 = byteBuffer.limit();
        if (byteBuffer.isDirect()) {
            n = this.fd.write(byteBuffer, n2, byteBuffer.limit());
        } else {
            int n4 = n3 - n2;
            ByteBuf byteBuf = null;
            try {
                Object object;
                if (n4 == 0) {
                    byteBuf = Unpooled.EMPTY_BUFFER;
                } else {
                    object = this.alloc();
                    if (object.isDirectBufferPooled()) {
                        byteBuf = object.directBuffer(n4);
                    } else {
                        byteBuf = ByteBufUtil.threadLocalDirectBuffer();
                        if (byteBuf == null) {
                            byteBuf = Unpooled.directBuffer(n4);
                        }
                    }
                }
                byteBuf.writeBytes(byteBuffer.duplicate());
                object = byteBuf.internalNioBuffer(byteBuf.readerIndex(), n4);
                n = this.fd.write((ByteBuffer)object, ((Buffer)object).position(), ((Buffer)object).limit());
            } finally {
                if (byteBuf != null) {
                    byteBuf.release();
                }
            }
        }
        if (n > 0) {
            byteBuffer.position(n2 + n);
        }
        return n;
    }

    @Override
    public final boolean isOpen() {
        return this.fd.isOpen();
    }

    @Override
    public final void close() throws IOException {
        this.fd.close();
    }

    protected abstract ByteBufAllocator alloc();
}

