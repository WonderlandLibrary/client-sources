/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.ByteBufChecksum;
import io.netty.handler.codec.compression.CompressionUtil;
import io.netty.handler.codec.compression.DecompressionException;
import io.netty.util.ReferenceCounted;
import java.util.List;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.xxhash.XXHashFactory;

public class Lz4FrameDecoder
extends ByteToMessageDecoder {
    private State currentState = State.INIT_BLOCK;
    private LZ4FastDecompressor decompressor;
    private ByteBufChecksum checksum;
    private int blockType;
    private int compressedLength;
    private int decompressedLength;
    private int currentChecksum;

    public Lz4FrameDecoder() {
        this(false);
    }

    public Lz4FrameDecoder(boolean bl) {
        this(LZ4Factory.fastestInstance(), bl);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, boolean bl) {
        this(lZ4Factory, bl ? XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum() : null);
    }

    public Lz4FrameDecoder(LZ4Factory lZ4Factory, Checksum checksum) {
        if (lZ4Factory == null) {
            throw new NullPointerException("factory");
        }
        this.decompressor = lZ4Factory.fastDecompressor();
        this.checksum = checksum == null ? null : ByteBufChecksum.wrapChecksum(checksum);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$compression$Lz4FrameDecoder$State[this.currentState.ordinal()]) {
                case 1: {
                    if (byteBuf.readableBytes() < 21) break;
                    long l = byteBuf.readLong();
                    if (l != 5501767354678207339L) {
                        throw new DecompressionException("unexpected block identifier");
                    }
                    byte by = byteBuf.readByte();
                    int n = (by & 0xF) + 10;
                    int n2 = by & 0xF0;
                    int n3 = Integer.reverseBytes(byteBuf.readInt());
                    if (n3 < 0 || n3 > 0x2000000) {
                        throw new DecompressionException(String.format("invalid compressedLength: %d (expected: 0-%d)", n3, 0x2000000));
                    }
                    int n4 = Integer.reverseBytes(byteBuf.readInt());
                    int n5 = 1 << n;
                    if (n4 < 0 || n4 > n5) {
                        throw new DecompressionException(String.format("invalid decompressedLength: %d (expected: 0-%d)", n4, n5));
                    }
                    if (n4 == 0 && n3 != 0 || n4 != 0 && n3 == 0 || n2 == 16 && n4 != n3) {
                        throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch", n3, n4));
                    }
                    int n6 = Integer.reverseBytes(byteBuf.readInt());
                    if (n4 == 0 && n3 == 0) {
                        if (n6 != 0) {
                            throw new DecompressionException("stream corrupted: checksum error");
                        }
                        this.currentState = State.FINISHED;
                        this.decompressor = null;
                        this.checksum = null;
                        break;
                    }
                    this.blockType = n2;
                    this.compressedLength = n3;
                    this.decompressedLength = n4;
                    this.currentChecksum = n6;
                    this.currentState = State.DECOMPRESS_DATA;
                }
                case 2: {
                    int n2 = this.blockType;
                    int n3 = this.compressedLength;
                    int n4 = this.decompressedLength;
                    int n6 = this.currentChecksum;
                    if (byteBuf.readableBytes() < n3) break;
                    ByteBufChecksum byteBufChecksum = this.checksum;
                    ReferenceCounted referenceCounted = null;
                    try {
                        switch (n2) {
                            case 16: {
                                referenceCounted = byteBuf.retainedSlice(byteBuf.readerIndex(), n4);
                                break;
                            }
                            case 32: {
                                referenceCounted = channelHandlerContext.alloc().buffer(n4, n4);
                                this.decompressor.decompress(CompressionUtil.safeNioBuffer(byteBuf), ((ByteBuf)referenceCounted).internalNioBuffer(((ByteBuf)referenceCounted).writerIndex(), n4));
                                ((ByteBuf)referenceCounted).writerIndex(((ByteBuf)referenceCounted).writerIndex() + n4);
                                break;
                            }
                            default: {
                                throw new DecompressionException(String.format("unexpected blockType: %d (expected: %d or %d)", n2, 16, 32));
                            }
                        }
                        byteBuf.skipBytes(n3);
                        if (byteBufChecksum != null) {
                            CompressionUtil.checkChecksum(byteBufChecksum, (ByteBuf)referenceCounted, n6);
                        }
                        list.add(referenceCounted);
                        referenceCounted = null;
                        this.currentState = State.INIT_BLOCK;
                        break;
                    } catch (LZ4Exception lZ4Exception) {
                        throw new DecompressionException(lZ4Exception);
                    } finally {
                        if (referenceCounted != null) {
                            referenceCounted.release();
                        }
                    }
                }
                case 3: 
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

    public boolean isClosed() {
        return this.currentState == State.FINISHED;
    }

    private static enum State {
        INIT_BLOCK,
        DECOMPRESS_DATA,
        FINISHED,
        CORRUPTED;

    }
}

