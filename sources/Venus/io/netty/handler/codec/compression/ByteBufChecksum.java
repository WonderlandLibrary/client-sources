/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.compression.CompressionUtil;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

abstract class ByteBufChecksum
implements Checksum {
    private static final Method ADLER32_UPDATE_METHOD = ByteBufChecksum.updateByteBuffer(new Adler32());
    private static final Method CRC32_UPDATE_METHOD = ByteBufChecksum.updateByteBuffer(new CRC32());
    private final ByteProcessor updateProcessor = new ByteProcessor(this){
        final ByteBufChecksum this$0;
        {
            this.this$0 = byteBufChecksum;
        }

        @Override
        public boolean process(byte by) throws Exception {
            this.this$0.update(by);
            return false;
        }
    };

    ByteBufChecksum() {
    }

    private static Method updateByteBuffer(Checksum checksum) {
        if (PlatformDependent.javaVersion() >= 8) {
            try {
                Method method = checksum.getClass().getDeclaredMethod("update", ByteBuffer.class);
                method.invoke(method, ByteBuffer.allocate(1));
                return method;
            } catch (Throwable throwable) {
                return null;
            }
        }
        return null;
    }

    static ByteBufChecksum wrapChecksum(Checksum checksum) {
        ObjectUtil.checkNotNull(checksum, "checksum");
        if (checksum instanceof Adler32 && ADLER32_UPDATE_METHOD != null) {
            return new ReflectiveByteBufChecksum(checksum, ADLER32_UPDATE_METHOD);
        }
        if (checksum instanceof CRC32 && CRC32_UPDATE_METHOD != null) {
            return new ReflectiveByteBufChecksum(checksum, CRC32_UPDATE_METHOD);
        }
        return new SlowByteBufChecksum(checksum);
    }

    public void update(ByteBuf byteBuf, int n, int n2) {
        if (byteBuf.hasArray()) {
            this.update(byteBuf.array(), byteBuf.arrayOffset() + n, n2);
        } else {
            byteBuf.forEachByte(n, n2, this.updateProcessor);
        }
    }

    private static class SlowByteBufChecksum
    extends ByteBufChecksum {
        protected final Checksum checksum;

        SlowByteBufChecksum(Checksum checksum) {
            this.checksum = checksum;
        }

        @Override
        public void update(int n) {
            this.checksum.update(n);
        }

        @Override
        public void update(byte[] byArray, int n, int n2) {
            this.checksum.update(byArray, n, n2);
        }

        @Override
        public long getValue() {
            return this.checksum.getValue();
        }

        @Override
        public void reset() {
            this.checksum.reset();
        }
    }

    private static final class ReflectiveByteBufChecksum
    extends SlowByteBufChecksum {
        private final Method method;

        ReflectiveByteBufChecksum(Checksum checksum, Method method) {
            super(checksum);
            this.method = method;
        }

        @Override
        public void update(ByteBuf byteBuf, int n, int n2) {
            if (byteBuf.hasArray()) {
                this.update(byteBuf.array(), byteBuf.arrayOffset() + n, n2);
            } else {
                try {
                    this.method.invoke(this.checksum, CompressionUtil.safeNioBuffer(byteBuf));
                } catch (Throwable throwable) {
                    throw new Error();
                }
            }
        }
    }
}

