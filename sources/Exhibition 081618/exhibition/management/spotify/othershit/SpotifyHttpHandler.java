package exhibition.management.spotify.othershit;

import exhibition.management.spotify.HttpResponseCallback;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpotifyHttpHandler
extends SimpleChannelInboundHandler<HttpObject> {
    private final HttpResponseCallback callback;
    private final StringBuilder buffer = new StringBuilder();
    private int responseCode = 500;
    private AtomicBoolean hasResponded = new AtomicBoolean(false);

    public SpotifyHttpHandler(HttpResponseCallback callback) {
        this.callback = callback;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            if (this.hasResponded.compareAndSet(false, true)) {
                this.callback.call(this.buffer.toString(), this.responseCode, cause);
            }
        }
        finally {
            ctx.channel().close();
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse)msg;
            this.responseCode = response.getStatus().code();
            if (this.responseCode == HttpResponseStatus.NO_CONTENT.code()) {
                this.done(ctx);
                return;
            }
            if (this.responseCode != HttpResponseStatus.OK.code()) {
                throw new IllegalStateException("Expected HTTP response 200 OK, got " + (Object)response.getStatus());
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            this.buffer.append(content.content().toString(Charset.forName("UTF-8")));
            if (msg instanceof LastHttpContent) {
                this.done(ctx);
            }
        }
    }

    private void done(ChannelHandlerContext ctx) {
        try {
            if (this.hasResponded.compareAndSet(false, true)) {
                this.callback.call(this.buffer.toString(), this.responseCode, null);
            }
        }
        finally {
            ctx.channel().close();
        }
    }
}

