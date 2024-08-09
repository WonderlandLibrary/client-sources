/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BinaryMemcacheResponseDecoder
extends AbstractBinaryMemcacheDecoder<BinaryMemcacheResponse> {
    public BinaryMemcacheResponseDecoder() {
        this(8192);
    }

    public BinaryMemcacheResponseDecoder(int n) {
        super(n);
    }

    @Override
    protected BinaryMemcacheResponse decodeHeader(ByteBuf byteBuf) {
        DefaultBinaryMemcacheResponse defaultBinaryMemcacheResponse = new DefaultBinaryMemcacheResponse();
        defaultBinaryMemcacheResponse.setMagic(byteBuf.readByte());
        defaultBinaryMemcacheResponse.setOpcode(byteBuf.readByte());
        defaultBinaryMemcacheResponse.setKeyLength(byteBuf.readShort());
        defaultBinaryMemcacheResponse.setExtrasLength(byteBuf.readByte());
        defaultBinaryMemcacheResponse.setDataType(byteBuf.readByte());
        defaultBinaryMemcacheResponse.setStatus(byteBuf.readShort());
        defaultBinaryMemcacheResponse.setTotalBodyLength(byteBuf.readInt());
        defaultBinaryMemcacheResponse.setOpaque(byteBuf.readInt());
        defaultBinaryMemcacheResponse.setCas(byteBuf.readLong());
        return defaultBinaryMemcacheResponse;
    }

    @Override
    protected BinaryMemcacheResponse buildInvalidMessage() {
        return new DefaultBinaryMemcacheResponse(Unpooled.EMPTY_BUFFER, Unpooled.EMPTY_BUFFER);
    }

    @Override
    protected BinaryMemcacheMessage buildInvalidMessage() {
        return this.buildInvalidMessage();
    }

    @Override
    protected BinaryMemcacheMessage decodeHeader(ByteBuf byteBuf) {
        return this.decodeHeader(byteBuf);
    }
}

