/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CloseWebSocketFrame
extends WebSocketFrame {
    public CloseWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    public CloseWebSocketFrame(int n, String string) {
        this(true, 0, n, string);
    }

    public CloseWebSocketFrame(boolean bl, int n) {
        this(bl, n, Unpooled.buffer(0));
    }

    public CloseWebSocketFrame(boolean bl, int n, int n2, String string) {
        super(bl, n, CloseWebSocketFrame.newBinaryData(n2, string));
    }

    private static ByteBuf newBinaryData(int n, String string) {
        if (string == null) {
            string = "";
        }
        ByteBuf byteBuf = Unpooled.buffer(2 + string.length());
        byteBuf.writeShort(n);
        if (!string.isEmpty()) {
            byteBuf.writeCharSequence(string, CharsetUtil.UTF_8);
        }
        byteBuf.readerIndex(0);
        return byteBuf;
    }

    public CloseWebSocketFrame(boolean bl, int n, ByteBuf byteBuf) {
        super(bl, n, byteBuf);
    }

    public int statusCode() {
        ByteBuf byteBuf = this.content();
        if (byteBuf == null || byteBuf.capacity() == 0) {
            return 1;
        }
        byteBuf.readerIndex(0);
        short s = byteBuf.readShort();
        byteBuf.readerIndex(0);
        return s;
    }

    public String reasonText() {
        ByteBuf byteBuf = this.content();
        if (byteBuf == null || byteBuf.capacity() <= 2) {
            return "";
        }
        byteBuf.readerIndex(2);
        String string = byteBuf.toString(CharsetUtil.UTF_8);
        byteBuf.readerIndex(0);
        return string;
    }

    @Override
    public CloseWebSocketFrame copy() {
        return (CloseWebSocketFrame)super.copy();
    }

    @Override
    public CloseWebSocketFrame duplicate() {
        return (CloseWebSocketFrame)super.duplicate();
    }

    @Override
    public CloseWebSocketFrame retainedDuplicate() {
        return (CloseWebSocketFrame)super.retainedDuplicate();
    }

    @Override
    public CloseWebSocketFrame replace(ByteBuf byteBuf) {
        return new CloseWebSocketFrame(this.isFinalFragment(), this.rsv(), byteBuf);
    }

    @Override
    public CloseWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public CloseWebSocketFrame retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public CloseWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public CloseWebSocketFrame touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public WebSocketFrame touch(Object object) {
        return this.touch(object);
    }

    @Override
    public WebSocketFrame touch() {
        return this.touch();
    }

    @Override
    public WebSocketFrame retain(int n) {
        return this.retain(n);
    }

    @Override
    public WebSocketFrame retain() {
        return this.retain();
    }

    @Override
    public WebSocketFrame replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public WebSocketFrame retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public WebSocketFrame duplicate() {
        return this.duplicate();
    }

    @Override
    public WebSocketFrame copy() {
        return this.copy();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
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

