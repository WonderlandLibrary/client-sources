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

    public Lz4FrameDecoder(boolean validateChecksums) {
        this(LZ4Factory.fastestInstance(), validateChecksums);
    }

    public Lz4FrameDecoder(LZ4Factory factory, boolean validateChecksums) {
        this(factory, validateChecksums ? XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum() : null);
    }

    public Lz4FrameDecoder(LZ4Factory factory, Checksum checksum) {
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        this.decompressor = factory.fastDecompressor();
        this.checksum = checksum == null ? null : ByteBufChecksum.wrapChecksum(checksum);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            switch (this.currentState) {
                case INIT_BLOCK: {
                    if (in.readableBytes() < 21) break;
                    long magic = in.readLong();
                    if (magic != 5501767354678207339L) {
                        throw new DecompressionException("unexpected block identifier");
                    }
                    byte token = in.readByte();
                    int compressionLevel = (token & 0xF) + 10;
                    int blockType = token & 0xF0;
                    int compressedLength = Integer.reverseBytes(in.readInt());
                    if (compressedLength < 0 || compressedLength > 0x2000000) {
                        throw new DecompressionException(String.format("invalid compressedLength: %d (expected: 0-%d)", compressedLength, 0x2000000));
                    }
                    int decompressedLength = Integer.reverseBytes(in.readInt());
                    int maxDecompressedLength = 1 << compressionLevel;
                    if (decompressedLength < 0 || decompressedLength > maxDecompressedLength) {
                        throw new DecompressionException(String.format("invalid decompressedLength: %d (expected: 0-%d)", decompressedLength, maxDecompressedLength));
                    }
                    if (decompressedLength == 0 && compressedLength != 0 || decompressedLength != 0 && compressedLength == 0 || blockType == 16 && decompressedLength != compressedLength) {
                        throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch", compressedLength, decompressedLength));
                    }
                    int currentChecksum = Integer.reverseBytes(in.readInt());
                    if (decompressedLength == 0 && compressedLength == 0) {
                        if (currentChecksum != 0) {
                            throw new DecompressionException("stream corrupted: checksum error");
                        }
                        this.currentState = State.FINISHED;
                        this.decompressor = null;
                        this.checksum = null;
                        break;
                    }
                    this.blockType = blockType;
                    this.compressedLength = compressedLength;
                    this.decompressedLength = decompressedLength;
                    this.currentChecksum = currentChecksum;
                    this.currentState = State.DECOMPRESS_DATA;
                }
                case DECOMPRESS_DATA: {
                    int blockType = this.blockType;
                    int compressedLength = this.compressedLength;
                    int decompressedLength = this.decompressedLength;
                    int currentChecksum = this.currentChecksum;
                    if (in.readableBytes() < compressedLength) break;
                    ByteBufChecksum checksum = this.checksum;
                    ReferenceCounted uncompressed = null;
                    try {
                        switch (blockType) {
                            case 16: {
                                uncompressed = in.retainedSlice(in.readerIndex(), decompressedLength);
                                break;
                            }
                            case 32: {
                                uncompressed = ctx.alloc().buffer(decompressedLength, decompressedLength);
                                this.decompressor.decompress(CompressionUtil.safeNioBuffer(in), ((ByteBuf)uncompressed).internalNioBuffer(((ByteBuf)uncompressed).writerIndex(), decompressedLength));
                                ((ByteBuf)uncompressed).writerIndex(((ByteBuf)uncompressed).writerIndex() + decompressedLength);
                                break;
                            }
                            default: {
                                throw new DecompressionException(String.format("unexpected blockType: %d (expected: %d or %d)", blockType, 16, 32));
                            }
                        }
                        in.skipBytes(compressedLength);
                        if (checksum != null) {
                            CompressionUtil.checkChecksum(checksum, (ByteBuf)uncompressed, currentChecksum);
                        }
                        out.add(uncompressed);
                        uncompressed = null;
                        this.currentState = State.INIT_BLOCK;
                        break;
                    } catch (LZ4Exception e) {
                        throw new DecompressionException(e);
                    } finally {
                        if (uncompressed != null) {
                            uncompressed.release();
                        }
                    }
                }
                case FINISHED: 
                case CORRUPTED: {
                    in.skipBytes(in.readableBytes());
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            throw e;
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

