/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectAggregator;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.handler.codec.memcache.MemcacheObject;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;

public class BinaryMemcacheObjectAggregator
extends AbstractMemcacheObjectAggregator<BinaryMemcacheMessage> {
    public BinaryMemcacheObjectAggregator(int n) {
        super(n);
    }

    @Override
    protected boolean isStartMessage(MemcacheObject memcacheObject) throws Exception {
        return memcacheObject instanceof BinaryMemcacheMessage;
    }

    @Override
    protected FullMemcacheMessage beginAggregation(BinaryMemcacheMessage binaryMemcacheMessage, ByteBuf byteBuf) throws Exception {
        if (binaryMemcacheMessage instanceof BinaryMemcacheRequest) {
            return BinaryMemcacheObjectAggregator.toFullRequest((BinaryMemcacheRequest)binaryMemcacheMessage, byteBuf);
        }
        if (binaryMemcacheMessage instanceof BinaryMemcacheResponse) {
            return BinaryMemcacheObjectAggregator.toFullResponse((BinaryMemcacheResponse)binaryMemcacheMessage, byteBuf);
        }
        throw new Error();
    }

    private static FullBinaryMemcacheRequest toFullRequest(BinaryMemcacheRequest binaryMemcacheRequest, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = binaryMemcacheRequest.key() == null ? null : binaryMemcacheRequest.key().retain();
        ByteBuf byteBuf3 = binaryMemcacheRequest.extras() == null ? null : binaryMemcacheRequest.extras().retain();
        DefaultFullBinaryMemcacheRequest defaultFullBinaryMemcacheRequest = new DefaultFullBinaryMemcacheRequest(byteBuf2, byteBuf3, byteBuf);
        defaultFullBinaryMemcacheRequest.setMagic(binaryMemcacheRequest.magic());
        defaultFullBinaryMemcacheRequest.setOpcode(binaryMemcacheRequest.opcode());
        defaultFullBinaryMemcacheRequest.setKeyLength(binaryMemcacheRequest.keyLength());
        defaultFullBinaryMemcacheRequest.setExtrasLength(binaryMemcacheRequest.extrasLength());
        defaultFullBinaryMemcacheRequest.setDataType(binaryMemcacheRequest.dataType());
        defaultFullBinaryMemcacheRequest.setTotalBodyLength(binaryMemcacheRequest.totalBodyLength());
        defaultFullBinaryMemcacheRequest.setOpaque(binaryMemcacheRequest.opaque());
        defaultFullBinaryMemcacheRequest.setCas(binaryMemcacheRequest.cas());
        defaultFullBinaryMemcacheRequest.setReserved(binaryMemcacheRequest.reserved());
        return defaultFullBinaryMemcacheRequest;
    }

    private static FullBinaryMemcacheResponse toFullResponse(BinaryMemcacheResponse binaryMemcacheResponse, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = binaryMemcacheResponse.key() == null ? null : binaryMemcacheResponse.key().retain();
        ByteBuf byteBuf3 = binaryMemcacheResponse.extras() == null ? null : binaryMemcacheResponse.extras().retain();
        DefaultFullBinaryMemcacheResponse defaultFullBinaryMemcacheResponse = new DefaultFullBinaryMemcacheResponse(byteBuf2, byteBuf3, byteBuf);
        defaultFullBinaryMemcacheResponse.setMagic(binaryMemcacheResponse.magic());
        defaultFullBinaryMemcacheResponse.setOpcode(binaryMemcacheResponse.opcode());
        defaultFullBinaryMemcacheResponse.setKeyLength(binaryMemcacheResponse.keyLength());
        defaultFullBinaryMemcacheResponse.setExtrasLength(binaryMemcacheResponse.extrasLength());
        defaultFullBinaryMemcacheResponse.setDataType(binaryMemcacheResponse.dataType());
        defaultFullBinaryMemcacheResponse.setTotalBodyLength(binaryMemcacheResponse.totalBodyLength());
        defaultFullBinaryMemcacheResponse.setOpaque(binaryMemcacheResponse.opaque());
        defaultFullBinaryMemcacheResponse.setCas(binaryMemcacheResponse.cas());
        defaultFullBinaryMemcacheResponse.setStatus(binaryMemcacheResponse.status());
        return defaultFullBinaryMemcacheResponse;
    }

    @Override
    protected ByteBufHolder beginAggregation(Object object, ByteBuf byteBuf) throws Exception {
        return this.beginAggregation((BinaryMemcacheMessage)object, byteBuf);
    }

    @Override
    protected boolean isStartMessage(Object object) throws Exception {
        return this.isStartMessage((MemcacheObject)object);
    }
}

