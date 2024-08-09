/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaderBlockDecoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyVersion;

public class SpdyHeaderBlockRawDecoder
extends SpdyHeaderBlockDecoder {
    private static final int LENGTH_FIELD_SIZE = 4;
    private final int maxHeaderSize;
    private State state;
    private ByteBuf cumulation;
    private int headerSize;
    private int numHeaders;
    private int length;
    private String name;

    public SpdyHeaderBlockRawDecoder(SpdyVersion spdyVersion, int n) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        }
        this.maxHeaderSize = n;
        this.state = State.READ_NUM_HEADERS;
    }

    private static int readLengthField(ByteBuf byteBuf) {
        int n = SpdyCodecUtil.getSignedInt(byteBuf, byteBuf.readerIndex());
        byteBuf.skipBytes(4);
        return n;
    }

    @Override
    void decode(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (byteBuf == null) {
            throw new NullPointerException("headerBlock");
        }
        if (spdyHeadersFrame == null) {
            throw new NullPointerException("frame");
        }
        if (this.cumulation == null) {
            this.decodeHeaderBlock(byteBuf, spdyHeadersFrame);
            if (byteBuf.isReadable()) {
                this.cumulation = byteBufAllocator.buffer(byteBuf.readableBytes());
                this.cumulation.writeBytes(byteBuf);
            }
        } else {
            this.cumulation.writeBytes(byteBuf);
            this.decodeHeaderBlock(this.cumulation, spdyHeadersFrame);
            if (this.cumulation.isReadable()) {
                this.cumulation.discardReadBytes();
            } else {
                this.releaseBuffer();
            }
        }
    }

    protected void decodeHeaderBlock(ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        block13: while (byteBuf.isReadable()) {
            switch (1.$SwitchMap$io$netty$handler$codec$spdy$SpdyHeaderBlockRawDecoder$State[this.state.ordinal()]) {
                case 1: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.numHeaders = SpdyHeaderBlockRawDecoder.readLengthField(byteBuf);
                    if (this.numHeaders < 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue block13;
                    }
                    if (this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue block13;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue block13;
                }
                case 2: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.length = SpdyHeaderBlockRawDecoder.readLengthField(byteBuf);
                    if (this.length <= 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue block13;
                    }
                    if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
                        this.headerSize = this.maxHeaderSize + 1;
                        this.state = State.SKIP_NAME;
                        spdyHeadersFrame.setTruncated();
                        continue block13;
                    }
                    this.headerSize += this.length;
                    this.state = State.READ_NAME;
                    continue block13;
                }
                case 3: {
                    if (byteBuf.readableBytes() < this.length) {
                        return;
                    }
                    byte[] byArray = new byte[this.length];
                    byteBuf.readBytes(byArray);
                    this.name = new String(byArray, "UTF-8");
                    if (spdyHeadersFrame.headers().contains(this.name)) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue block13;
                    }
                    this.state = State.READ_VALUE_LENGTH;
                    continue block13;
                }
                case 4: {
                    int n = Math.min(byteBuf.readableBytes(), this.length);
                    byteBuf.skipBytes(n);
                    this.length -= n;
                    if (this.length != 0) continue block13;
                    this.state = State.READ_VALUE_LENGTH;
                    continue block13;
                }
                case 5: {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    this.length = SpdyHeaderBlockRawDecoder.readLengthField(byteBuf);
                    if (this.length < 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue block13;
                    }
                    if (this.length == 0) {
                        if (!spdyHeadersFrame.isTruncated()) {
                            spdyHeadersFrame.headers().add(this.name, "");
                        }
                        this.name = null;
                        if (--this.numHeaders == 0) {
                            this.state = State.END_HEADER_BLOCK;
                            continue block13;
                        }
                        this.state = State.READ_NAME_LENGTH;
                        continue block13;
                    }
                    if (this.length > this.maxHeaderSize || this.headerSize > this.maxHeaderSize - this.length) {
                        this.headerSize = this.maxHeaderSize + 1;
                        this.name = null;
                        this.state = State.SKIP_VALUE;
                        spdyHeadersFrame.setTruncated();
                        continue block13;
                    }
                    this.headerSize += this.length;
                    this.state = State.READ_VALUE;
                    continue block13;
                }
                case 6: {
                    if (byteBuf.readableBytes() < this.length) {
                        return;
                    }
                    byte[] byArray = new byte[this.length];
                    byteBuf.readBytes(byArray);
                    int n = 0;
                    int n2 = 0;
                    if (byArray[0] == 0) {
                        this.state = State.ERROR;
                        spdyHeadersFrame.setInvalid();
                        continue block13;
                    }
                    while (n < this.length) {
                        while (n < byArray.length && byArray[n] != 0) {
                            ++n;
                        }
                        if (n < byArray.length && (n + 1 == byArray.length || byArray[n + 1] == 0)) {
                            this.state = State.ERROR;
                            spdyHeadersFrame.setInvalid();
                            break;
                        }
                        String string = new String(byArray, n2, n - n2, "UTF-8");
                        try {
                            spdyHeadersFrame.headers().add(this.name, string);
                        } catch (IllegalArgumentException illegalArgumentException) {
                            this.state = State.ERROR;
                            spdyHeadersFrame.setInvalid();
                            break;
                        }
                        n2 = ++n;
                    }
                    this.name = null;
                    if (this.state == State.ERROR) continue block13;
                    if (--this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue block13;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue block13;
                }
                case 7: {
                    int n = Math.min(byteBuf.readableBytes(), this.length);
                    byteBuf.skipBytes(n);
                    this.length -= n;
                    if (this.length != 0) continue block13;
                    if (--this.numHeaders == 0) {
                        this.state = State.END_HEADER_BLOCK;
                        continue block13;
                    }
                    this.state = State.READ_NAME_LENGTH;
                    continue block13;
                }
                case 8: {
                    this.state = State.ERROR;
                    spdyHeadersFrame.setInvalid();
                    continue block13;
                }
                case 9: {
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    return;
                }
            }
            throw new Error("Shouldn't reach here.");
        }
    }

    @Override
    void endHeaderBlock(SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (this.state != State.END_HEADER_BLOCK) {
            spdyHeadersFrame.setInvalid();
        }
        this.releaseBuffer();
        this.headerSize = 0;
        this.name = null;
        this.state = State.READ_NUM_HEADERS;
    }

    @Override
    void end() {
        this.releaseBuffer();
    }

    private void releaseBuffer() {
        if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
        }
    }

    private static enum State {
        READ_NUM_HEADERS,
        READ_NAME_LENGTH,
        READ_NAME,
        SKIP_NAME,
        READ_VALUE_LENGTH,
        READ_VALUE,
        SKIP_VALUE,
        END_HEADER_BLOCK,
        ERROR;

    }
}

