/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.ning.compress.BufferRecycler
 *  com.ning.compress.lzf.ChunkDecoder
 *  com.ning.compress.lzf.util.ChunkDecoderFactory
 */
package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.DecompressionException;
import java.util.List;

public class LzfDecoder
extends ByteToMessageDecoder {
    private State currentState = State.INIT_BLOCK;
    private static final short MAGIC_NUMBER = 23126;
    private ChunkDecoder decoder;
    private BufferRecycler recycler;
    private int chunkLength;
    private int originalLength;
    private boolean isCompressed;

    public LzfDecoder() {
        this(false);
    }

    public LzfDecoder(boolean bl) {
        this.decoder = bl ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
        this.recycler = BufferRecycler.instance();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext var1_1, ByteBuf var2_2, List<Object> var3_3) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[this.currentState.ordinal()]) {
                case 1: {
                    if (var2_2.readableBytes() < 5) break;
                    var4_4 = var2_2.readUnsignedShort();
                    if (var4_4 != 23126) {
                        throw new DecompressionException("unexpected block identifier");
                    }
                    var5_6 = var2_2.readByte();
                    switch (var5_6) {
                        case 0: {
                            this.isCompressed = false;
                            this.currentState = State.DECOMPRESS_DATA;
                            break;
                        }
                        case 1: {
                            this.isCompressed = true;
                            this.currentState = State.INIT_ORIGINAL_LENGTH;
                            break;
                        }
                        default: {
                            throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", new Object[]{(int)var5_6, 0, 1}));
                        }
                    }
                    this.chunkLength = var2_2.readUnsignedShort();
                    if (var5_6 != 1) break;
                }
                case 2: {
                    if (var2_2.readableBytes() < 2) break;
                    this.originalLength = var2_2.readUnsignedShort();
                    this.currentState = State.DECOMPRESS_DATA;
                }
                case 3: {
                    var6_7 = this.chunkLength;
                    if (var2_2.readableBytes() < var6_7) break;
                    var7_8 = this.originalLength;
                    if (!this.isCompressed) ** GOTO lbl62
                    var8_9 = var2_2.readerIndex();
                    if (var2_2.hasArray()) {
                        var9_10 = var2_2.array();
                        var10_11 = var2_2.arrayOffset() + var8_9;
                    } else {
                        var9_10 = this.recycler.allocInputBuffer(var6_7);
                        var2_2.getBytes(var8_9, var9_10, 0, var6_7);
                        var10_11 = 0;
                    }
                    var11_12 = var1_1.alloc().heapBuffer(var7_8, var7_8);
                    var12_13 = var11_12.array();
                    var13_14 = var11_12.arrayOffset() + var11_12.writerIndex();
                    var14_15 = false;
                    try {
                        this.decoder.decodeChunk(var9_10, var10_11, var12_13, var13_14, var13_14 + var7_8);
                        var11_12.writerIndex(var11_12.writerIndex() + var7_8);
                        var3_3.add(var11_12);
                        var2_2.skipBytes(var6_7);
                        var14_15 = true;
                    } finally {
                        if (!var14_15) {
                            var11_12.release();
                        }
                    }
                    if (!var2_2.hasArray()) {
                        this.recycler.releaseInputBuffer(var9_10);
                    }
                    ** GOTO lbl65
lbl62:
                    // 1 sources

                    if (var6_7 > 0) {
                        var3_3.add(var2_2.readRetainedSlice(var6_7));
                    }
lbl65:
                    // 4 sources

                    this.currentState = State.INIT_BLOCK;
                    break;
                }
                case 4: {
                    var2_2.skipBytes(var2_2.readableBytes());
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        } catch (Exception var4_5) {
            this.currentState = State.CORRUPTED;
            this.decoder = null;
            this.recycler = null;
            throw var4_5;
        }
    }

    private static enum State {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED;

    }
}

