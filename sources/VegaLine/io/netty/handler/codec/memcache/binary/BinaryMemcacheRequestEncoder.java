/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheEncoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;

public class BinaryMemcacheRequestEncoder
extends AbstractBinaryMemcacheEncoder<BinaryMemcacheRequest> {
    @Override
    protected void encodeHeader(ByteBuf buf, BinaryMemcacheRequest msg) {
        buf.writeByte(msg.magic());
        buf.writeByte(msg.opcode());
        buf.writeShort(msg.keyLength());
        buf.writeByte(msg.extrasLength());
        buf.writeByte(msg.dataType());
        buf.writeShort(msg.reserved());
        buf.writeInt(msg.totalBodyLength());
        buf.writeInt(msg.opaque());
        buf.writeLong(msg.cas());
    }
}

