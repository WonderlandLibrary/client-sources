/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Headers;

public interface Http2PromisedRequestVerifier {
    public static final Http2PromisedRequestVerifier ALWAYS_VERIFY = new Http2PromisedRequestVerifier(){

        @Override
        public boolean isAuthoritative(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers) {
            return false;
        }

        @Override
        public boolean isCacheable(Http2Headers http2Headers) {
            return false;
        }

        @Override
        public boolean isSafe(Http2Headers http2Headers) {
            return false;
        }
    };

    public boolean isAuthoritative(ChannelHandlerContext var1, Http2Headers var2);

    public boolean isCacheable(Http2Headers var1);

    public boolean isSafe(Http2Headers var1);
}

