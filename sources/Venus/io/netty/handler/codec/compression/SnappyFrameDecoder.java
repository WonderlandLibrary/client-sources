/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.DecompressionException;
import io.netty.handler.codec.compression.Snappy;
import java.util.List;

public class SnappyFrameDecoder
extends ByteToMessageDecoder {
    private static final int SNAPPY_IDENTIFIER_LEN = 6;
    private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
    private final Snappy snappy = new Snappy();
    private final boolean validateChecksums;
    private boolean started;
    private boolean corrupted;

    public SnappyFrameDecoder() {
        this(false);
    }

    public SnappyFrameDecoder(boolean bl) {
        this.validateChecksums = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext var1_1, ByteBuf var2_2, List<Object> var3_3) throws Exception {
        if (this.corrupted) {
            var2_2.skipBytes(var2_2.readableBytes());
            return;
        }
        try {
            var4_4 = var2_2.readerIndex();
            var5_6 = var2_2.readableBytes();
            if (var5_6 < 4) {
                return;
            }
            var6_7 = var2_2.getUnsignedByte(var4_4);
            var7_8 = SnappyFrameDecoder.mapChunkType((byte)var6_7);
            var8_9 = var2_2.getUnsignedMediumLE(var4_4 + 1);
            switch (1.$SwitchMap$io$netty$handler$codec$compression$SnappyFrameDecoder$ChunkType[var7_8.ordinal()]) {
                case 1: {
                    if (var8_9 != 6) {
                        throw new DecompressionException("Unexpected length of stream identifier: " + var8_9);
                    }
                    if (var5_6 < 10) break;
                    var2_2.skipBytes(4);
                    var9_10 = var2_2.readerIndex();
                    var2_2.skipBytes(6);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10++), (byte)115);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10++), (byte)78);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10++), (byte)97);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10++), (byte)80);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10++), (byte)112);
                    SnappyFrameDecoder.checkByte(var2_2.getByte(var9_10), (byte)89);
                    this.started = true;
                    break;
                }
                case 2: {
                    if (!this.started) {
                        throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                    }
                    if (var5_6 < 4 + var8_9) {
                        return;
                    }
                    var2_2.skipBytes(4 + var8_9);
                    break;
                }
                case 3: {
                    throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(var6_7));
                }
                case 4: {
                    if (!this.started) {
                        throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                    }
                    if (var8_9 > 65540) {
                        throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                    }
                    if (var5_6 < 4 + var8_9) {
                        return;
                    }
                    var2_2.skipBytes(4);
                    if (this.validateChecksums) {
                        var10_11 = var2_2.readIntLE();
                        Snappy.validateChecksum(var10_11, var2_2, var2_2.readerIndex(), var8_9 - 4);
                    } else {
                        var2_2.skipBytes(4);
                    }
                    var3_3.add(var2_2.readRetainedSlice(var8_9 - 4));
                    break;
                }
                case 5: {
                    if (!this.started) {
                        throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                    }
                    if (var5_6 < 4 + var8_9) {
                        return;
                    }
                    var2_2.skipBytes(4);
                    var10_12 = var2_2.readIntLE();
                    var11_13 = var1_1.alloc().buffer();
                    try {
                        if (!this.validateChecksums) ** GOTO lbl82
                        var12_14 = var2_2.writerIndex();
                        try {
                            var2_2.writerIndex(var2_2.readerIndex() + var8_9 - 4);
                            this.snappy.decode(var2_2, var11_13);
                        } finally {
                            var2_2.writerIndex(var12_14);
                        }
                        Snappy.validateChecksum(var10_12, var11_13, 0, var11_13.writerIndex());
                        ** GOTO lbl83
lbl82:
                        // 1 sources

                        this.snappy.decode(var2_2.readSlice(var8_9 - 4), var11_13);
lbl83:
                        // 2 sources

                        var3_3.add(var11_13);
                        var11_13 = null;
                    } finally {
                        if (var11_13 != null) {
                            var11_13.release();
                        }
                    }
                    this.snappy.reset();
                }
            }
        } catch (Exception var4_5) {
            this.corrupted = true;
            throw var4_5;
        }
    }

    private static void checkByte(byte by, byte by2) {
        if (by != by2) {
            throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
        }
    }

    private static ChunkType mapChunkType(byte by) {
        if (by == 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (by == 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (by == -1) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((by & 0x80) == 128) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }

    private static enum ChunkType {
        STREAM_IDENTIFIER,
        COMPRESSED_DATA,
        UNCOMPRESSED_DATA,
        RESERVED_UNSKIPPABLE,
        RESERVED_SKIPPABLE;

    }
}

