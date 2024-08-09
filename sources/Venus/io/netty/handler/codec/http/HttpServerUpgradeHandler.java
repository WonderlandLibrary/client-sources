/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HttpServerUpgradeHandler
extends HttpObjectAggregator {
    private final SourceCodec sourceCodec;
    private final UpgradeCodecFactory upgradeCodecFactory;
    private boolean handlingUpgrade;
    static final boolean $assertionsDisabled = !HttpServerUpgradeHandler.class.desiredAssertionStatus();

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory) {
        this(sourceCodec, upgradeCodecFactory, 0);
    }

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int n) {
        super(n);
        this.sourceCodec = ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
        this.upgradeCodecFactory = ObjectUtil.checkNotNull(upgradeCodecFactory, "upgradeCodecFactory");
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        FullHttpRequest fullHttpRequest;
        this.handlingUpgrade |= HttpServerUpgradeHandler.isUpgradeRequest(httpObject);
        if (!this.handlingUpgrade) {
            ReferenceCountUtil.retain(httpObject);
            list.add(httpObject);
            return;
        }
        if (httpObject instanceof FullHttpRequest) {
            fullHttpRequest = (FullHttpRequest)httpObject;
            ReferenceCountUtil.retain(httpObject);
            list.add(httpObject);
        } else {
            super.decode(channelHandlerContext, httpObject, list);
            if (list.isEmpty()) {
                return;
            }
            if (!$assertionsDisabled && list.size() != 1) {
                throw new AssertionError();
            }
            this.handlingUpgrade = false;
            fullHttpRequest = (FullHttpRequest)list.get(0);
        }
        if (this.upgrade(channelHandlerContext, fullHttpRequest)) {
            list.clear();
        }
    }

    private static boolean isUpgradeRequest(HttpObject httpObject) {
        return httpObject instanceof HttpRequest && ((HttpRequest)httpObject).headers().get(HttpHeaderNames.UPGRADE) != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean upgrade(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        Object object;
        List<CharSequence> list;
        Object object2;
        List<CharSequence> list2 = HttpServerUpgradeHandler.splitHeader(fullHttpRequest.headers().get(HttpHeaderNames.UPGRADE));
        int n = list2.size();
        UpgradeCodec upgradeCodec = null;
        Object object3 = null;
        for (int i = 0; i < n; ++i) {
            object2 = list2.get(i);
            list = this.upgradeCodecFactory.newUpgradeCodec((CharSequence)object2);
            if (list == null) continue;
            object3 = object2;
            upgradeCodec = list;
            break;
        }
        if (upgradeCodec == null) {
            return true;
        }
        String string = fullHttpRequest.headers().get(HttpHeaderNames.CONNECTION);
        if (string == null) {
            return true;
        }
        object2 = upgradeCodec.requiredUpgradeHeaders();
        list = HttpServerUpgradeHandler.splitHeader(string);
        if (!AsciiString.containsContentEqualsIgnoreCase((Collection<CharSequence>)list, HttpHeaderNames.UPGRADE) || !AsciiString.containsAllContentEqualsIgnoreCase((Collection<CharSequence>)list, (Collection<CharSequence>)object2)) {
            return true;
        }
        Object object4 = object2.iterator();
        while (object4.hasNext()) {
            object = (CharSequence)object4.next();
            if (fullHttpRequest.headers().contains((CharSequence)object)) continue;
            return true;
        }
        object4 = HttpServerUpgradeHandler.createUpgradeResponse((CharSequence)object3);
        if (!upgradeCodec.prepareUpgradeResponse(channelHandlerContext, fullHttpRequest, object4.headers())) {
            return true;
        }
        object = new UpgradeEvent((CharSequence)object3, fullHttpRequest);
        try {
            ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(object4);
            this.sourceCodec.upgradeFrom(channelHandlerContext);
            upgradeCodec.upgradeTo(channelHandlerContext, fullHttpRequest);
            channelHandlerContext.pipeline().remove(this);
            channelHandlerContext.fireUserEventTriggered(((UpgradeEvent)object).retain());
            channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } finally {
            ((UpgradeEvent)object).release();
        }
        return false;
    }

    private static FullHttpResponse createUpgradeResponse(CharSequence charSequence) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER, false);
        defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.UPGRADE);
        defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.UPGRADE, (Object)charSequence);
        return defaultFullHttpResponse;
    }

    private static List<CharSequence> splitHeader(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>(4);
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            if (Character.isWhitespace(c)) continue;
            if (c == ',') {
                arrayList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
                continue;
            }
            stringBuilder.append(c);
        }
        if (stringBuilder.length() > 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class UpgradeEvent
    implements ReferenceCounted {
        private final CharSequence protocol;
        private final FullHttpRequest upgradeRequest;

        UpgradeEvent(CharSequence charSequence, FullHttpRequest fullHttpRequest) {
            this.protocol = charSequence;
            this.upgradeRequest = fullHttpRequest;
        }

        public CharSequence protocol() {
            return this.protocol;
        }

        public FullHttpRequest upgradeRequest() {
            return this.upgradeRequest;
        }

        @Override
        public int refCnt() {
            return this.upgradeRequest.refCnt();
        }

        @Override
        public UpgradeEvent retain() {
            this.upgradeRequest.retain();
            return this;
        }

        @Override
        public UpgradeEvent retain(int n) {
            this.upgradeRequest.retain(n);
            return this;
        }

        @Override
        public UpgradeEvent touch() {
            this.upgradeRequest.touch();
            return this;
        }

        @Override
        public UpgradeEvent touch(Object object) {
            this.upgradeRequest.touch(object);
            return this;
        }

        @Override
        public boolean release() {
            return this.upgradeRequest.release();
        }

        @Override
        public boolean release(int n) {
            return this.upgradeRequest.release(n);
        }

        public String toString() {
            return "UpgradeEvent [protocol=" + this.protocol + ", upgradeRequest=" + this.upgradeRequest + ']';
        }

        @Override
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ReferenceCounted touch() {
            return this.touch();
        }

        @Override
        public ReferenceCounted retain(int n) {
            return this.retain(n);
        }

        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
    }

    public static interface UpgradeCodecFactory {
        public UpgradeCodec newUpgradeCodec(CharSequence var1);
    }

    public static interface UpgradeCodec {
        public Collection<CharSequence> requiredUpgradeHeaders();

        public boolean prepareUpgradeResponse(ChannelHandlerContext var1, FullHttpRequest var2, HttpHeaders var3);

        public void upgradeTo(ChannelHandlerContext var1, FullHttpRequest var2);
    }

    public static interface SourceCodec {
        public void upgradeFrom(ChannelHandlerContext var1);
    }
}

