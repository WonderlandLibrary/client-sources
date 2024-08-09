/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.Inflater
 *  com.jcraft.jzlib.JZlib
 */
package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.compression.ZlibDecoder;
import io.netty.handler.codec.compression.ZlibUtil;
import io.netty.handler.codec.compression.ZlibWrapper;
import java.util.List;

public class JZlibDecoder
extends ZlibDecoder {
    private final Inflater z = new Inflater();
    private byte[] dictionary;
    private volatile boolean finished;

    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public JZlibDecoder(ZlibWrapper zlibWrapper) {
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        int n = this.z.init(ZlibUtil.convertWrapperType(zlibWrapper));
        if (n != 0) {
            ZlibUtil.fail(this.z, "initialization failure", n);
        }
    }

    public JZlibDecoder(byte[] byArray) {
        if (byArray == null) {
            throw new NullPointerException("dictionary");
        }
        this.dictionary = byArray;
        int n = this.z.inflateInit(JZlib.W_ZLIB);
        if (n != 0) {
            ZlibUtil.fail(this.z, "initialization failure", n);
        }
    }

    @Override
    public boolean isClosed() {
        return this.finished;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return;
        }
        try {
            this.z.avail_in = n;
            if (byteBuf.hasArray()) {
                this.z.next_in = byteBuf.array();
                this.z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
            } else {
                byte[] byArray = new byte[n];
                byteBuf.getBytes(byteBuf.readerIndex(), byArray);
                this.z.next_in = byArray;
                this.z.next_in_index = 0;
            }
            int n2 = this.z.next_in_index;
            ByteBuf byteBuf2 = channelHandlerContext.alloc().heapBuffer(n << 1);
            block12: while (true) {
                block13: while (true) {
                    byteBuf2.ensureWritable(this.z.avail_in << 1);
                    this.z.avail_out = byteBuf2.writableBytes();
                    this.z.next_out = byteBuf2.array();
                    int n3 = this.z.next_out_index = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
                    int n4 = this.z.inflate(2);
                    int n5 = this.z.next_out_index - n3;
                    if (n5 > 0) {
                        byteBuf2.writerIndex(byteBuf2.writerIndex() + n5);
                    }
                    switch (n4) {
                        case 2: {
                            if (this.dictionary == null) {
                                ZlibUtil.fail(this.z, "decompression failure", n4);
                                continue block12;
                            }
                            n4 = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                            if (n4 == 0) continue block12;
                            ZlibUtil.fail(this.z, "failed to set the dictionary", n4);
                            continue block12;
                        }
                        case 1: {
                            this.finished = true;
                            this.z.inflateEnd();
                            return;
                        }
                        case 0: {
                            continue block12;
                        }
                        case -5: {
                            if (this.z.avail_in <= 0) return;
                            continue block12;
                        }
                        default: {
                            ZlibUtil.fail(this.z, "decompression failure", n4);
                            continue block13;
                        }
                    }
                    break;
                }
                break;
            }
            finally {
                byteBuf.skipBytes(this.z.next_in_index - n2);
                if (byteBuf2.isReadable()) {
                    list.add(byteBuf2);
                } else {
                    byteBuf2.release();
                }
            }
        } finally {
            this.z.next_in = null;
            this.z.next_out = null;
        }
    }
}

