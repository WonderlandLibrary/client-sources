/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.CodecOutputList;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageEncoder<I>
extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> clazz) {
        this.matcher = TypeParameterMatcher.get(clazz);
    }

    public boolean acceptOutboundMessage(Object object) throws Exception {
        return this.matcher.match(object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        CodecOutputList codecOutputList = null;
        try {
            if (this.acceptOutboundMessage(object)) {
                codecOutputList = CodecOutputList.newInstance();
                Object object2 = object;
                try {
                    this.encode(channelHandlerContext, object2, codecOutputList);
                } finally {
                    ReferenceCountUtil.release(object2);
                }
                if (codecOutputList.isEmpty()) {
                    codecOutputList.recycle();
                    codecOutputList = null;
                    throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
                }
            } else {
                channelHandlerContext.write(object, channelPromise);
            }
            if (codecOutputList == null) return;
        } catch (EncoderException encoderException) {
            try {
                throw encoderException;
                catch (Throwable throwable) {
                    throw new EncoderException(throwable);
                }
            } catch (Throwable throwable) {
                if (codecOutputList == null) throw throwable;
                int n = codecOutputList.size() - 1;
                if (n == 0) {
                    channelHandlerContext.write(codecOutputList.get(0), channelPromise);
                } else if (n > 0) {
                    ChannelPromise channelPromise2 = channelHandlerContext.voidPromise();
                    boolean bl = channelPromise == channelPromise2;
                    for (int i = 0; i < n; ++i) {
                        ChannelPromise channelPromise3 = bl ? channelPromise2 : channelHandlerContext.newPromise();
                        channelHandlerContext.write(codecOutputList.getUnsafe(i), channelPromise3);
                    }
                    channelHandlerContext.write(codecOutputList.getUnsafe(n), channelPromise);
                }
                codecOutputList.recycle();
                throw throwable;
            }
        }
        int n = codecOutputList.size() - 1;
        if (n == 0) {
            channelHandlerContext.write(codecOutputList.get(0), channelPromise);
        } else if (n > 0) {
            ChannelPromise channelPromise4 = channelHandlerContext.voidPromise();
            boolean bl = channelPromise == channelPromise4;
            for (int i = 0; i < n; ++i) {
                ChannelPromise channelPromise5 = bl ? channelPromise4 : channelHandlerContext.newPromise();
                channelHandlerContext.write(codecOutputList.getUnsafe(i), channelPromise5);
            }
            channelHandlerContext.write(codecOutputList.getUnsafe(n), channelPromise);
        }
        codecOutputList.recycle();
    }

    protected abstract void encode(ChannelHandlerContext var1, I var2, List<Object> var3) throws Exception;
}

