/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.ning.compress.BufferRecycler
 *  com.ning.compress.lzf.ChunkEncoder
 *  com.ning.compress.lzf.LZFEncoder
 *  com.ning.compress.lzf.util.ChunkEncoderFactory
 */
package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LzfEncoder
extends MessageToByteEncoder<ByteBuf> {
    private static final int MIN_BLOCK_TO_COMPRESS = 16;
    private final ChunkEncoder encoder;
    private final BufferRecycler recycler;

    public LzfEncoder() {
        this(false, 65535);
    }

    public LzfEncoder(boolean bl) {
        this(bl, 65535);
    }

    public LzfEncoder(int n) {
        this(false, n);
    }

    public LzfEncoder(boolean bl, int n) {
        super(false);
        if (n < 16 || n > 65535) {
            throw new IllegalArgumentException("totalLength: " + n + " (expected: " + 16 + '-' + 65535 + ')');
        }
        this.encoder = bl ? ChunkEncoderFactory.safeNonAllocatingInstance((int)n) : ChunkEncoderFactory.optimalNonAllocatingInstance((int)n);
        this.recycler = BufferRecycler.instance();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n;
        byte[] byArray;
        int n2 = byteBuf.readableBytes();
        int n3 = byteBuf.readerIndex();
        if (byteBuf.hasArray()) {
            byArray = byteBuf.array();
            n = byteBuf.arrayOffset() + n3;
        } else {
            byArray = this.recycler.allocInputBuffer(n2);
            byteBuf.getBytes(n3, byArray, 0, n2);
            n = 0;
        }
        int n4 = LZFEncoder.estimateMaxWorkspaceSize((int)n2);
        byteBuf2.ensureWritable(n4);
        byte[] byArray2 = byteBuf2.array();
        int n5 = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
        int n6 = LZFEncoder.appendEncoded((ChunkEncoder)this.encoder, (byte[])byArray, (int)n, (int)n2, (byte[])byArray2, (int)n5) - n5;
        byteBuf2.writerIndex(byteBuf2.writerIndex() + n6);
        byteBuf.skipBytes(n2);
        if (!byteBuf.hasArray()) {
            this.recycler.releaseInputBuffer(byArray);
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

