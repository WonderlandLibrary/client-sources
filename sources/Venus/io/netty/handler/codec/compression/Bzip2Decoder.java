/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.Bzip2BitReader;
import io.netty.handler.codec.compression.Bzip2BlockDecompressor;
import io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder;
import io.netty.handler.codec.compression.Bzip2MoveToFrontTable;
import io.netty.handler.codec.compression.DecompressionException;
import java.util.List;

public class Bzip2Decoder
extends ByteToMessageDecoder {
    private State currentState = State.INIT;
    private final Bzip2BitReader reader = new Bzip2BitReader();
    private Bzip2BlockDecompressor blockDecompressor;
    private Bzip2HuffmanStageDecoder huffmanStageDecoder;
    private int blockSize;
    private int blockCRC;
    private int streamCRC;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        Bzip2BitReader bzip2BitReader = this.reader;
        bzip2BitReader.setByteBuf(byteBuf);
        block15: while (true) {
            switch (1.$SwitchMap$io$netty$handler$codec$compression$Bzip2Decoder$State[this.currentState.ordinal()]) {
                case 1: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    int n = byteBuf.readUnsignedMedium();
                    if (n != 4348520) {
                        throw new DecompressionException("Unexpected stream identifier contents. Mismatched bzip2 protocol version?");
                    }
                    int n2 = byteBuf.readByte() - 48;
                    if (n2 < 1 || n2 > 9) {
                        throw new DecompressionException("block size is invalid");
                    }
                    this.blockSize = n2 * 100000;
                    this.streamCRC = 0;
                    this.currentState = State.INIT_BLOCK;
                }
                case 2: {
                    int n;
                    if (!bzip2BitReader.hasReadableBytes(1)) {
                        return;
                    }
                    int n3 = bzip2BitReader.readBits(24);
                    int n4 = bzip2BitReader.readBits(24);
                    if (n3 == 1536581 && n4 == 3690640) {
                        n = bzip2BitReader.readInt();
                        if (n != this.streamCRC) {
                            throw new DecompressionException("stream CRC error");
                        }
                        this.currentState = State.EOF;
                        continue block15;
                    }
                    if (n3 != 3227993 || n4 != 2511705) {
                        throw new DecompressionException("bad block header");
                    }
                    this.blockCRC = bzip2BitReader.readInt();
                    this.currentState = State.INIT_BLOCK_PARAMS;
                }
                case 3: {
                    if (!bzip2BitReader.hasReadableBits(0)) {
                        return;
                    }
                    int n = bzip2BitReader.readBoolean();
                    int n5 = bzip2BitReader.readBits(24);
                    this.blockDecompressor = new Bzip2BlockDecompressor(this.blockSize, this.blockCRC, n != 0, n5, bzip2BitReader);
                    this.currentState = State.RECEIVE_HUFFMAN_USED_MAP;
                }
                case 4: {
                    if (!bzip2BitReader.hasReadableBits(1)) {
                        return;
                    }
                    this.blockDecompressor.huffmanInUse16 = bzip2BitReader.readBits(16);
                    this.currentState = State.RECEIVE_HUFFMAN_USED_BITMAPS;
                }
                case 5: {
                    int n;
                    int n6;
                    int n7;
                    Bzip2BlockDecompressor bzip2BlockDecompressor = this.blockDecompressor;
                    int n8 = bzip2BlockDecompressor.huffmanInUse16;
                    int n9 = Integer.bitCount(n8);
                    byte[] byArray = bzip2BlockDecompressor.huffmanSymbolMap;
                    if (!bzip2BitReader.hasReadableBits(n9 * 16 + 3)) {
                        return;
                    }
                    int n10 = 0;
                    if (n9 > 0) {
                        for (n7 = 0; n7 < 16; ++n7) {
                            if ((n8 & 32768 >>> n7) == 0) continue;
                            n6 = 0;
                            n = n7 << 4;
                            while (n6 < 16) {
                                if (bzip2BitReader.readBoolean()) {
                                    byArray[n10++] = (byte)n;
                                }
                                ++n6;
                                ++n;
                            }
                        }
                    }
                    bzip2BlockDecompressor.huffmanEndOfBlockSymbol = n10 + 1;
                    n7 = bzip2BitReader.readBits(3);
                    if (n7 < 2 || n7 > 6) {
                        throw new DecompressionException("incorrect huffman groups number");
                    }
                    n6 = n10 + 2;
                    if (n6 > 258) {
                        throw new DecompressionException("incorrect alphabet size");
                    }
                    this.huffmanStageDecoder = new Bzip2HuffmanStageDecoder(bzip2BitReader, n7, n6);
                    this.currentState = State.RECEIVE_SELECTORS_NUMBER;
                }
                case 6: {
                    if (!bzip2BitReader.hasReadableBits(0)) {
                        return;
                    }
                    int n = bzip2BitReader.readBits(15);
                    if (n < 1 || n > 18002) {
                        throw new DecompressionException("incorrect selectors number");
                    }
                    this.huffmanStageDecoder.selectors = new byte[n];
                    this.currentState = State.RECEIVE_SELECTORS;
                }
                case 7: {
                    Bzip2HuffmanStageDecoder bzip2HuffmanStageDecoder = this.huffmanStageDecoder;
                    byte[] byArray = bzip2HuffmanStageDecoder.selectors;
                    int n = byArray.length;
                    Bzip2MoveToFrontTable bzip2MoveToFrontTable = bzip2HuffmanStageDecoder.tableMTF;
                    for (int i = bzip2HuffmanStageDecoder.currentSelector; i < n; ++i) {
                        if (!bzip2BitReader.hasReadableBits(1)) {
                            bzip2HuffmanStageDecoder.currentSelector = i;
                            return;
                        }
                        int n11 = 0;
                        while (bzip2BitReader.readBoolean()) {
                            ++n11;
                        }
                        byArray[i] = bzip2MoveToFrontTable.indexToFront(n11);
                    }
                    this.currentState = State.RECEIVE_HUFFMAN_LENGTH;
                }
                case 8: {
                    int n;
                    Bzip2HuffmanStageDecoder bzip2HuffmanStageDecoder = this.huffmanStageDecoder;
                    int n7 = bzip2HuffmanStageDecoder.totalTables;
                    byte[][] byArray = bzip2HuffmanStageDecoder.tableCodeLengths;
                    int n6 = bzip2HuffmanStageDecoder.alphabetSize;
                    int n12 = bzip2HuffmanStageDecoder.currentLength;
                    int n13 = 0;
                    boolean bl = bzip2HuffmanStageDecoder.modifyLength;
                    boolean bl2 = false;
                    block20: for (n = bzip2HuffmanStageDecoder.currentGroup; n < n7; ++n) {
                        if (!bzip2BitReader.hasReadableBits(0)) {
                            bl2 = true;
                            break;
                        }
                        if (n12 < 0) {
                            n12 = bzip2BitReader.readBits(5);
                        }
                        for (n13 = bzip2HuffmanStageDecoder.currentAlpha; n13 < n6; ++n13) {
                            if (!bzip2BitReader.isReadable()) {
                                bl2 = true;
                                break block20;
                            }
                            while (bl || bzip2BitReader.readBoolean()) {
                                if (!bzip2BitReader.isReadable()) {
                                    bl = true;
                                    bl2 = true;
                                    break block20;
                                }
                                n12 += bzip2BitReader.readBoolean() ? -1 : 1;
                                bl = false;
                                if (bzip2BitReader.isReadable()) continue;
                                bl2 = true;
                                break block20;
                            }
                            byArray[n][n13] = (byte)n12;
                        }
                        n12 = -1;
                        bzip2HuffmanStageDecoder.currentAlpha = 0;
                        n13 = 0;
                        bl = false;
                    }
                    if (bl2) {
                        bzip2HuffmanStageDecoder.currentGroup = n;
                        bzip2HuffmanStageDecoder.currentLength = n12;
                        bzip2HuffmanStageDecoder.currentAlpha = n13;
                        bzip2HuffmanStageDecoder.modifyLength = bl;
                        return;
                    }
                    bzip2HuffmanStageDecoder.createHuffmanDecodingTables();
                    this.currentState = State.DECODE_HUFFMAN_DATA;
                }
                case 9: {
                    Bzip2BlockDecompressor bzip2BlockDecompressor = this.blockDecompressor;
                    int n = byteBuf.readerIndex();
                    boolean bl = bzip2BlockDecompressor.decodeHuffmanData(this.huffmanStageDecoder);
                    if (!bl) {
                        return;
                    }
                    if (byteBuf.readerIndex() == n && byteBuf.isReadable()) {
                        bzip2BitReader.refill();
                    }
                    int n14 = bzip2BlockDecompressor.blockLength();
                    ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(n14);
                    boolean bl3 = false;
                    try {
                        int n15;
                        while ((n15 = bzip2BlockDecompressor.read()) >= 0) {
                            byteBuf2.writeByte(n15);
                        }
                        int n16 = bzip2BlockDecompressor.checkCRC();
                        this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ n16;
                        list.add(byteBuf2);
                        bl3 = true;
                    } finally {
                        if (!bl3) {
                            byteBuf2.release();
                        }
                    }
                    this.currentState = State.INIT_BLOCK;
                    continue block15;
                }
                case 10: {
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    return;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    public boolean isClosed() {
        return this.currentState == State.EOF;
    }

    private static enum State {
        INIT,
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        RECEIVE_HUFFMAN_USED_MAP,
        RECEIVE_HUFFMAN_USED_BITMAPS,
        RECEIVE_SELECTORS_NUMBER,
        RECEIVE_SELECTORS,
        RECEIVE_HUFFMAN_LENGTH,
        DECODE_HUFFMAN_DATA,
        EOF;

    }
}

