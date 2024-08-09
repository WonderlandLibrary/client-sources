/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectDecoder;
import io.netty.handler.codec.memcache.DefaultLastMemcacheContent;
import io.netty.handler.codec.memcache.DefaultMemcacheContent;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import java.util.List;

public abstract class AbstractBinaryMemcacheDecoder<M extends BinaryMemcacheMessage>
extends AbstractMemcacheObjectDecoder {
    public static final int DEFAULT_MAX_CHUNK_SIZE = 8192;
    private final int chunkSize;
    private M currentMessage;
    private int alreadyReadChunkSize;
    private State state = State.READ_HEADER;

    protected AbstractBinaryMemcacheDecoder() {
        this(8192);
    }

    protected AbstractBinaryMemcacheDecoder(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("chunkSize must be a positive integer: " + n);
        }
        this.chunkSize = n;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$memcache$binary$AbstractBinaryMemcacheDecoder$State[this.state.ordinal()]) {
            case 1: {
                try {
                    if (byteBuf.readableBytes() < 24) {
                        return;
                    }
                    this.resetDecoder();
                    this.currentMessage = this.decodeHeader(byteBuf);
                    this.state = State.READ_EXTRAS;
                } catch (Exception exception) {
                    this.resetDecoder();
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case 2: {
                int n;
                try {
                    n = this.currentMessage.extrasLength();
                    if (n > 0) {
                        if (byteBuf.readableBytes() < n) {
                            return;
                        }
                        this.currentMessage.setExtras(byteBuf.readRetainedSlice(n));
                    }
                    this.state = State.READ_KEY;
                } catch (Exception exception) {
                    this.resetDecoder();
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case 3: {
                int n;
                try {
                    n = this.currentMessage.keyLength();
                    if (n > 0) {
                        if (byteBuf.readableBytes() < n) {
                            return;
                        }
                        this.currentMessage.setKey(byteBuf.readRetainedSlice(n));
                    }
                    list.add(this.currentMessage.retain());
                    this.state = State.READ_CONTENT;
                } catch (Exception exception) {
                    this.resetDecoder();
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case 4: {
                int n;
                try {
                    n = this.currentMessage.totalBodyLength() - this.currentMessage.keyLength() - this.currentMessage.extrasLength();
                    int n2 = byteBuf.readableBytes();
                    if (n > 0) {
                        int n3;
                        if (n2 == 0) {
                            return;
                        }
                        if (n2 > this.chunkSize) {
                            n2 = this.chunkSize;
                        }
                        if (n2 > (n3 = n - this.alreadyReadChunkSize)) {
                            n2 = n3;
                        }
                        ByteBuf byteBuf2 = byteBuf.readRetainedSlice(n2);
                        DefaultMemcacheContent defaultMemcacheContent = (this.alreadyReadChunkSize += n2) >= n ? new DefaultLastMemcacheContent(byteBuf2) : new DefaultMemcacheContent(byteBuf2);
                        list.add(defaultMemcacheContent);
                        if (this.alreadyReadChunkSize < n) {
                            return;
                        }
                    } else {
                        list.add(LastMemcacheContent.EMPTY_LAST_CONTENT);
                    }
                    this.resetDecoder();
                    this.state = State.READ_HEADER;
                    return;
                } catch (Exception exception) {
                    this.resetDecoder();
                    list.add(this.invalidChunk(exception));
                    return;
                }
            }
            case 5: {
                byteBuf.skipBytes(this.actualReadableBytes());
                return;
            }
        }
        throw new Error("Unknown state reached: " + (Object)((Object)this.state));
    }

    private M invalidMessage(Exception exception) {
        this.state = State.BAD_MESSAGE;
        M m = this.buildInvalidMessage();
        m.setDecoderResult(DecoderResult.failure(exception));
        return m;
    }

    private MemcacheContent invalidChunk(Exception exception) {
        this.state = State.BAD_MESSAGE;
        DefaultLastMemcacheContent defaultLastMemcacheContent = new DefaultLastMemcacheContent(Unpooled.EMPTY_BUFFER);
        defaultLastMemcacheContent.setDecoderResult(DecoderResult.failure(exception));
        return defaultLastMemcacheContent;
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        this.resetDecoder();
    }

    protected void resetDecoder() {
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
        this.alreadyReadChunkSize = 0;
    }

    protected abstract M decodeHeader(ByteBuf var1);

    protected abstract M buildInvalidMessage();

    static enum State {
        READ_HEADER,
        READ_EXTRAS,
        READ_KEY,
        READ_CONTENT,
        BAD_MESSAGE;

    }
}

