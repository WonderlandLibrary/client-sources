/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequestEncoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseDecoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class BinaryMemcacheClientCodec
extends CombinedChannelDuplexHandler<BinaryMemcacheResponseDecoder, BinaryMemcacheRequestEncoder> {
    private final boolean failOnMissingResponse;
    private final AtomicLong requestResponseCounter = new AtomicLong();

    public BinaryMemcacheClientCodec() {
        this(8192);
    }

    public BinaryMemcacheClientCodec(int n) {
        this(n, false);
    }

    public BinaryMemcacheClientCodec(int n, boolean bl) {
        this.failOnMissingResponse = bl;
        this.init(new Decoder(this, n), new Encoder(this, null));
    }

    static boolean access$100(BinaryMemcacheClientCodec binaryMemcacheClientCodec) {
        return binaryMemcacheClientCodec.failOnMissingResponse;
    }

    static AtomicLong access$200(BinaryMemcacheClientCodec binaryMemcacheClientCodec) {
        return binaryMemcacheClientCodec.requestResponseCounter;
    }

    private final class Decoder
    extends BinaryMemcacheResponseDecoder {
        final BinaryMemcacheClientCodec this$0;

        Decoder(BinaryMemcacheClientCodec binaryMemcacheClientCodec, int n) {
            this.this$0 = binaryMemcacheClientCodec;
            super(n);
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            int n = list.size();
            super.decode(channelHandlerContext, byteBuf, list);
            if (BinaryMemcacheClientCodec.access$100(this.this$0)) {
                int n2 = list.size();
                for (int i = n; i < n2; ++i) {
                    Object object = list.get(i);
                    if (!(object instanceof LastMemcacheContent)) continue;
                    BinaryMemcacheClientCodec.access$200(this.this$0).decrementAndGet();
                }
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            long l;
            super.channelInactive(channelHandlerContext);
            if (BinaryMemcacheClientCodec.access$100(this.this$0) && (l = BinaryMemcacheClientCodec.access$200(this.this$0).get()) > 0L) {
                channelHandlerContext.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + l + " missing response(s)"));
            }
        }
    }

    private final class Encoder
    extends BinaryMemcacheRequestEncoder {
        final BinaryMemcacheClientCodec this$0;

        private Encoder(BinaryMemcacheClientCodec binaryMemcacheClientCodec) {
            this.this$0 = binaryMemcacheClientCodec;
        }

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
            super.encode(channelHandlerContext, object, list);
            if (BinaryMemcacheClientCodec.access$100(this.this$0) && object instanceof LastMemcacheContent) {
                BinaryMemcacheClientCodec.access$200(this.this$0).incrementAndGet();
            }
        }

        Encoder(BinaryMemcacheClientCodec binaryMemcacheClientCodec, 1 var2_2) {
            this(binaryMemcacheClientCodec);
        }
    }
}

