/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.stomp.DefaultStompHeaders;
import io.netty.handler.codec.stomp.DefaultStompHeadersSubframe;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultStompFrame
extends DefaultStompHeadersSubframe
implements StompFrame {
    private final ByteBuf content;

    public DefaultStompFrame(StompCommand stompCommand) {
        this(stompCommand, Unpooled.buffer(0));
    }

    public DefaultStompFrame(StompCommand stompCommand, ByteBuf byteBuf) {
        this(stompCommand, byteBuf, null);
    }

    DefaultStompFrame(StompCommand stompCommand, ByteBuf byteBuf, DefaultStompHeaders defaultStompHeaders) {
        super(stompCommand, defaultStompHeaders);
        if (byteBuf == null) {
            throw new NullPointerException("content");
        }
        this.content = byteBuf;
    }

    @Override
    public ByteBuf content() {
        return this.content;
    }

    @Override
    public StompFrame copy() {
        return this.replace(this.content.copy());
    }

    @Override
    public StompFrame duplicate() {
        return this.replace(this.content.duplicate());
    }

    @Override
    public StompFrame retainedDuplicate() {
        return this.replace(this.content.retainedDuplicate());
    }

    @Override
    public StompFrame replace(ByteBuf byteBuf) {
        return new DefaultStompFrame(this.command, byteBuf, this.headers.copy());
    }

    @Override
    public int refCnt() {
        return this.content.refCnt();
    }

    @Override
    public StompFrame retain() {
        this.content.retain();
        return this;
    }

    @Override
    public StompFrame retain(int n) {
        this.content.retain(n);
        return this;
    }

    @Override
    public StompFrame touch() {
        this.content.touch();
        return this;
    }

    @Override
    public StompFrame touch(Object object) {
        this.content.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.content.release();
    }

    @Override
    public boolean release(int n) {
        return this.content.release(n);
    }

    @Override
    public String toString() {
        return "DefaultStompFrame{command=" + (Object)((Object)this.command) + ", headers=" + this.headers + ", content=" + this.content.toString(CharsetUtil.UTF_8) + '}';
    }

    @Override
    public LastStompContentSubframe touch(Object object) {
        return this.touch(object);
    }

    @Override
    public LastStompContentSubframe touch() {
        return this.touch();
    }

    @Override
    public LastStompContentSubframe retain(int n) {
        return this.retain(n);
    }

    @Override
    public LastStompContentSubframe retain() {
        return this.retain();
    }

    @Override
    public LastStompContentSubframe replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public LastStompContentSubframe retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public LastStompContentSubframe duplicate() {
        return this.duplicate();
    }

    @Override
    public LastStompContentSubframe copy() {
        return this.copy();
    }

    @Override
    public StompContentSubframe touch(Object object) {
        return this.touch(object);
    }

    @Override
    public StompContentSubframe touch() {
        return this.touch();
    }

    @Override
    public StompContentSubframe retain(int n) {
        return this.retain(n);
    }

    @Override
    public StompContentSubframe retain() {
        return this.retain();
    }

    @Override
    public StompContentSubframe replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public StompContentSubframe retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public StompContentSubframe duplicate() {
        return this.duplicate();
    }

    @Override
    public StompContentSubframe copy() {
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

