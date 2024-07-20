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

    public LzfDecoder(boolean safeInstance) {
        this.decoder = safeInstance ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
        this.recycler = BufferRecycler.instance();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[this.currentState.ordinal()]) {
                case 1: {
                    if (in.readableBytes() < 5) break;
                    magic = in.readUnsignedShort();
                    if (magic != 23126) {
                        throw new DecompressionException("unexpected block identifier");
                    }
                    type = in.readByte();
                    switch (type) {
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
                            throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", new Object[]{(int)type, 0, 1}));
                        }
                    }
                    this.chunkLength = in.readUnsignedShort();
                    if (type != 1) break;
                }
                case 2: {
                    if (in.readableBytes() < 2) break;
                    this.originalLength = in.readUnsignedShort();
                    this.currentState = State.DECOMPRESS_DATA;
                }
                case 3: {
                    chunkLength = this.chunkLength;
                    if (in.readableBytes() < chunkLength) break;
                    originalLength = this.originalLength;
                    if (!this.isCompressed) ** GOTO lbl62
                    idx = in.readerIndex();
                    if (in.hasArray()) {
                        inputArray = in.array();
                        inPos = in.arrayOffset() + idx;
                    } else {
                        inputArray = this.recycler.allocInputBuffer(chunkLength);
                        in.getBytes(idx, inputArray, 0, chunkLength);
                        inPos = 0;
                    }
                    uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
                    outputArray = uncompressed.array();
                    outPos = uncompressed.arrayOffset() + uncompressed.writerIndex();
                    success = false;
                    try {
                        this.decoder.decodeChunk(inputArray, inPos, outputArray, outPos, outPos + originalLength);
                        uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
                        out.add(uncompressed);
                        in.skipBytes(chunkLength);
                        success = true;
                    } finally {
                        if (!success) {
                            uncompressed.release();
                        }
                    }
                    if (!in.hasArray()) {
                        this.recycler.releaseInputBuffer(inputArray);
                    }
                    ** GOTO lbl65
lbl62:
                    // 1 sources

                    if (chunkLength > 0) {
                        out.add(in.readRetainedSlice(chunkLength));
                    }
lbl65:
                    // 4 sources

                    this.currentState = State.INIT_BLOCK;
                    break;
                }
                case 4: {
                    in.skipBytes(in.readableBytes());
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            this.decoder = null;
            this.recycler = null;
            throw e;
        }
    }

    private static enum State {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED;

    }
}

