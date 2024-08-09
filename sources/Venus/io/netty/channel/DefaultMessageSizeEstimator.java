/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.FileRegion;
import io.netty.channel.MessageSizeEstimator;

public final class DefaultMessageSizeEstimator
implements MessageSizeEstimator {
    public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(8);
    private final MessageSizeEstimator.Handle handle;

    public DefaultMessageSizeEstimator(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("unknownSize: " + n + " (expected: >= 0)");
        }
        this.handle = new HandleImpl(n, null);
    }

    @Override
    public MessageSizeEstimator.Handle newHandle() {
        return this.handle;
    }

    private static final class HandleImpl
    implements MessageSizeEstimator.Handle {
        private final int unknownSize;

        private HandleImpl(int n) {
            this.unknownSize = n;
        }

        @Override
        public int size(Object object) {
            if (object instanceof ByteBuf) {
                return ((ByteBuf)object).readableBytes();
            }
            if (object instanceof ByteBufHolder) {
                return ((ByteBufHolder)object).content().readableBytes();
            }
            if (object instanceof FileRegion) {
                return 1;
            }
            return this.unknownSize;
        }

        HandleImpl(int n, 1 var2_2) {
            this(n);
        }
    }
}

