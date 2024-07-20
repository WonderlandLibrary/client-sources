/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;

public class BinaryMemcacheResponseDecoder
extends AbstractBinaryMemcacheDecoder<BinaryMemcacheResponse> {
    public BinaryMemcacheResponseDecoder() {
        this(8192);
    }

    public BinaryMemcacheResponseDecoder(int chunkSize) {
        super(chunkSize);
    }

    @Override
    protected BinaryMemcacheResponse decodeHeader(ByteBuf in) {
        DefaultBinaryMemcacheResponse header = new DefaultBinaryMemcacheResponse();
        header.setMagic(in.readByte());
        header.setOpcode(in.readByte());
        header.setKeyLength(in.readShort());
        header.setExtrasLength(in.readByte());
        header.setDataType(in.readByte());
        header.setStatus(in.readShort());
        header.setTotalBodyLength(in.readInt());
        header.setOpaque(in.readInt());
        header.setCas(in.readLong());
        return header;
    }

    @Override
    protected BinaryMemcacheResponse buildInvalidMessage() {
        return new DefaultBinaryMemcacheResponse(Unpooled.EMPTY_BUFFER, Unpooled.EMPTY_BUFFER);
    }
}

