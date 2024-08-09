/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.DecompressionException;
import io.netty.handler.codec.compression.FastLz;
import io.netty.util.internal.EmptyArrays;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FastLzFrameDecoder
extends ByteToMessageDecoder {
    private State currentState = State.INIT_BLOCK;
    private final Checksum checksum;
    private int chunkLength;
    private int originalLength;
    private boolean isCompressed;
    private boolean hasChecksum;
    private int currentChecksum;

    public FastLzFrameDecoder() {
        this(false);
    }

    public FastLzFrameDecoder(boolean bl) {
        this(bl ? new Adler32() : null);
    }

    public FastLzFrameDecoder(Checksum checksum) {
        this.checksum = checksum;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State[this.currentState.ordinal()]) {
                case 1: {
                    if (byteBuf.readableBytes() < 4) break;
                    int n = byteBuf.readUnsignedMedium();
                    if (n != 4607066) {
                        throw new DecompressionException("unexpected block identifier");
                    }
                    byte by = byteBuf.readByte();
                    this.isCompressed = (by & 1) == 1;
                    this.hasChecksum = (by & 0x10) == 16;
                    this.currentState = State.INIT_BLOCK_PARAMS;
                }
                case 2: {
                    if (byteBuf.readableBytes() < 2 + (this.isCompressed ? 2 : 0) + (this.hasChecksum ? 4 : 0)) break;
                    this.currentChecksum = this.hasChecksum ? byteBuf.readInt() : 0;
                    this.chunkLength = byteBuf.readUnsignedShort();
                    this.originalLength = this.isCompressed ? byteBuf.readUnsignedShort() : this.chunkLength;
                    this.currentState = State.DECOMPRESS_DATA;
                }
                case 3: {
                    int n;
                    byte[] byArray;
                    ByteBuf byteBuf2;
                    int n2 = this.chunkLength;
                    if (byteBuf.readableBytes() < n2) break;
                    int n3 = byteBuf.readerIndex();
                    int n4 = this.originalLength;
                    if (n4 != 0) {
                        byteBuf2 = channelHandlerContext.alloc().heapBuffer(n4, n4);
                        byArray = byteBuf2.array();
                        n = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
                    } else {
                        byteBuf2 = null;
                        byArray = EmptyArrays.EMPTY_BYTES;
                        n = 0;
                    }
                    boolean bl = false;
                    try {
                        int n5;
                        Object object;
                        if (this.isCompressed) {
                            if (byteBuf.hasArray()) {
                                object = byteBuf.array();
                                n5 = byteBuf.arrayOffset() + n3;
                            } else {
                                object = new byte[n2];
                                byteBuf.getBytes(n3, (byte[])object);
                                n5 = 0;
                            }
                            int n6 = FastLz.decompress((byte[])object, n5, n2, byArray, n, n4);
                            if (n4 != n6) {
                                throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", n4, n6));
                            }
                        } else {
                            byteBuf.getBytes(n3, byArray, n, n2);
                        }
                        object = this.checksum;
                        if (this.hasChecksum && object != null) {
                            object.reset();
                            object.update(byArray, n, n4);
                            n5 = (int)object.getValue();
                            if (n5 != this.currentChecksum) {
                                throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", n5, this.currentChecksum));
                            }
                        }
                        if (byteBuf2 != null) {
                            byteBuf2.writerIndex(byteBuf2.writerIndex() + n4);
                            list.add(byteBuf2);
                        }
                        byteBuf.skipBytes(n2);
                        this.currentState = State.INIT_BLOCK;
                        bl = true;
                        break;
                    } finally {
                        if (!bl && byteBuf2 != null) {
                            byteBuf2.release();
                        }
                    }
                }
                case 4: {
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        } catch (Exception exception) {
            this.currentState = State.CORRUPTED;
            throw exception;
        }
    }

    private static enum State {
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        DECOMPRESS_DATA,
        CORRUPTED;

    }
}

