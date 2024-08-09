/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BinaryMemcacheRequestDecoder
extends AbstractBinaryMemcacheDecoder<BinaryMemcacheRequest> {
    public BinaryMemcacheRequestDecoder() {
        this(8192);
    }

    public BinaryMemcacheRequestDecoder(int n) {
        super(n);
    }

    @Override
    protected BinaryMemcacheRequest decodeHeader(ByteBuf byteBuf) {
        DefaultBinaryMemcacheRequest defaultBinaryMemcacheRequest = new DefaultBinaryMemcacheRequest();
        defaultBinaryMemcacheRequest.setMagic(byteBuf.readByte());
        defaultBinaryMemcacheRequest.setOpcode(byteBuf.readByte());
        defaultBinaryMemcacheRequest.setKeyLength(byteBuf.readShort());
        defaultBinaryMemcacheRequest.setExtrasLength(byteBuf.readByte());
        defaultBinaryMemcacheRequest.setDataType(byteBuf.readByte());
        defaultBinaryMemcacheRequest.setReserved(byteBuf.readShort());
        defaultBinaryMemcacheRequest.setTotalBodyLength(byteBuf.readInt());
        defaultBinaryMemcacheRequest.setOpaque(byteBuf.readInt());
        defaultBinaryMemcacheRequest.setCas(byteBuf.readLong());
        return defaultBinaryMemcacheRequest;
    }

    @Override
    protected BinaryMemcacheRequest buildInvalidMessage() {
        return new DefaultBinaryMemcacheRequest(Unpooled.EMPTY_BUFFER, Unpooled.EMPTY_BUFFER);
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

