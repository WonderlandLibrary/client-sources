/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheEncoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;

public class BinaryMemcacheResponseEncoder
extends AbstractBinaryMemcacheEncoder<BinaryMemcacheResponse> {
    @Override
    protected void encodeHeader(ByteBuf byteBuf, BinaryMemcacheResponse binaryMemcacheResponse) {
        byteBuf.writeByte(binaryMemcacheResponse.magic());
        byteBuf.writeByte(binaryMemcacheResponse.opcode());
        byteBuf.writeShort(binaryMemcacheResponse.keyLength());
        byteBuf.writeByte(binaryMemcacheResponse.extrasLength());
        byteBuf.writeByte(binaryMemcacheResponse.dataType());
        byteBuf.writeShort(binaryMemcacheResponse.status());
        byteBuf.writeInt(binaryMemcacheResponse.totalBodyLength());
        byteBuf.writeInt(binaryMemcacheResponse.opaque());
        byteBuf.writeLong(binaryMemcacheResponse.cas());
    }

    @Override
    protected void encodeHeader(ByteBuf byteBuf, BinaryMemcacheMessage binaryMemcacheMessage) {
        this.encodeHeader(byteBuf, (BinaryMemcacheResponse)binaryMemcacheMessage);
    }
}

