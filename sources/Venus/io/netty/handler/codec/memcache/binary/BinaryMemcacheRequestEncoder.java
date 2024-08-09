/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheEncoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;

public class BinaryMemcacheRequestEncoder
extends AbstractBinaryMemcacheEncoder<BinaryMemcacheRequest> {
    @Override
    protected void encodeHeader(ByteBuf byteBuf, BinaryMemcacheRequest binaryMemcacheRequest) {
        byteBuf.writeByte(binaryMemcacheRequest.magic());
        byteBuf.writeByte(binaryMemcacheRequest.opcode());
        byteBuf.writeShort(binaryMemcacheRequest.keyLength());
        byteBuf.writeByte(binaryMemcacheRequest.extrasLength());
        byteBuf.writeByte(binaryMemcacheRequest.dataType());
        byteBuf.writeShort(binaryMemcacheRequest.reserved());
        byteBuf.writeInt(binaryMemcacheRequest.totalBodyLength());
        byteBuf.writeInt(binaryMemcacheRequest.opaque());
        byteBuf.writeLong(binaryMemcacheRequest.cas());
    }

    @Override
    protected void encodeHeader(ByteBuf byteBuf, BinaryMemcacheMessage binaryMemcacheMessage) {
        this.encodeHeader(byteBuf, (BinaryMemcacheRequest)binaryMemcacheMessage);
    }
}

