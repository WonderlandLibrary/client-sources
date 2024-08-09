/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionProvider
implements Provider {
    public void handlePlayCompression(UserConnection userConnection, int n) {
        if (!userConnection.isClientSide()) {
            throw new IllegalStateException("PLAY state Compression packet is unsupported");
        }
        ChannelPipeline channelPipeline = userConnection.getChannel().pipeline();
        if (n < 0) {
            if (channelPipeline.get("compress") != null) {
                channelPipeline.remove("compress");
                channelPipeline.remove("decompress");
            }
        } else if (channelPipeline.get("compress") == null) {
            channelPipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(n));
            channelPipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(n));
        } else {
            ((CompressionHandler)channelPipeline.get("compress")).setCompressionThreshold(n);
            ((CompressionHandler)channelPipeline.get("decompress")).setCompressionThreshold(n);
        }
    }

    protected CompressionHandler getEncoder(int n) {
        return new Compressor(n);
    }

    protected CompressionHandler getDecoder(int n) {
        return new Decompressor(n);
    }

    private static class Compressor
    extends MessageToByteEncoder<ByteBuf>
    implements CompressionHandler {
        private final Deflater deflater;
        private int threshold;

        public Compressor(int n) {
            this.threshold = n;
            this.deflater = new Deflater();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
            int n = byteBuf.readableBytes();
            if (n < this.threshold) {
                byteBuf2.writeByte(0);
                byteBuf2.writeBytes(byteBuf);
                return;
            }
            Type.VAR_INT.writePrimitive(byteBuf2, n);
            ByteBuf byteBuf3 = byteBuf;
            if (!byteBuf.hasArray()) {
                byteBuf3 = channelHandlerContext.alloc().heapBuffer().writeBytes(byteBuf);
            } else {
                byteBuf.retain();
            }
            ByteBuf byteBuf4 = channelHandlerContext.alloc().heapBuffer();
            try {
                this.deflater.setInput(byteBuf3.array(), byteBuf3.arrayOffset() + byteBuf3.readerIndex(), byteBuf3.readableBytes());
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    byteBuf4.ensureWritable(4096);
                    byteBuf4.writerIndex(byteBuf4.writerIndex() + this.deflater.deflate(byteBuf4.array(), byteBuf4.arrayOffset() + byteBuf4.writerIndex(), byteBuf4.writableBytes()));
                }
                byteBuf2.writeBytes(byteBuf4);
            } finally {
                byteBuf4.release();
                byteBuf3.release();
                this.deflater.reset();
            }
        }

        @Override
        public void setCompressionThreshold(int n) {
            this.threshold = n;
        }

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
            this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
        }
    }

    private static class Decompressor
    extends MessageToMessageDecoder<ByteBuf>
    implements CompressionHandler {
        private final Inflater inflater;
        private int threshold;

        public Decompressor(int n) {
            this.threshold = n;
            this.inflater = new Inflater();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            if (!byteBuf.isReadable()) {
                return;
            }
            int n = Type.VAR_INT.readPrimitive(byteBuf);
            if (n == 0) {
                list.add(byteBuf.readBytes(byteBuf.readableBytes()));
                return;
            }
            if (n < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + n + " is below server threshold of " + this.threshold);
            }
            if (n > 0x200000) {
                throw new DecoderException("Badly compressed packet - size of " + n + " is larger than protocol maximum of " + 0x200000);
            }
            ByteBuf byteBuf2 = byteBuf;
            if (!byteBuf.hasArray()) {
                byteBuf2 = channelHandlerContext.alloc().heapBuffer().writeBytes(byteBuf);
            } else {
                byteBuf.retain();
            }
            ByteBuf byteBuf3 = channelHandlerContext.alloc().heapBuffer(n, n);
            try {
                this.inflater.setInput(byteBuf2.array(), byteBuf2.arrayOffset() + byteBuf2.readerIndex(), byteBuf2.readableBytes());
                byteBuf3.writerIndex(byteBuf3.writerIndex() + this.inflater.inflate(byteBuf3.array(), byteBuf3.arrayOffset(), n));
                list.add(byteBuf3.retain());
            } finally {
                byteBuf3.release();
                byteBuf2.release();
                this.inflater.reset();
            }
        }

        @Override
        public void setCompressionThreshold(int n) {
            this.threshold = n;
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
            this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
        }
    }

    public static interface CompressionHandler
    extends ChannelHandler {
        public void setCompressionThreshold(int var1);
    }
}

