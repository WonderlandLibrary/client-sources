/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.smtp.DefaultSmtpContent;
import io.netty.handler.codec.smtp.LastSmtpContent;
import io.netty.handler.codec.smtp.SmtpContent;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultLastSmtpContent
extends DefaultSmtpContent
implements LastSmtpContent {
    public DefaultLastSmtpContent(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public LastSmtpContent copy() {
        return (LastSmtpContent)super.copy();
    }

    @Override
    public LastSmtpContent duplicate() {
        return (LastSmtpContent)super.duplicate();
    }

    @Override
    public LastSmtpContent retainedDuplicate() {
        return (LastSmtpContent)super.retainedDuplicate();
    }

    @Override
    public LastSmtpContent replace(ByteBuf byteBuf) {
        return new DefaultLastSmtpContent(byteBuf);
    }

    @Override
    public DefaultLastSmtpContent retain() {
        super.retain();
        return this;
    }

    @Override
    public DefaultLastSmtpContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public DefaultLastSmtpContent touch() {
        super.touch();
        return this;
    }

    @Override
    public DefaultLastSmtpContent touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public SmtpContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public SmtpContent touch() {
        return this.touch();
    }

    @Override
    public SmtpContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public SmtpContent retain() {
        return this.retain();
    }

    @Override
    public SmtpContent replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public SmtpContent retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public SmtpContent duplicate() {
        return this.duplicate();
    }

    @Override
    public SmtpContent copy() {
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

    @Override
    public LastSmtpContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public LastSmtpContent touch() {
        return this.touch();
    }

    @Override
    public LastSmtpContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public LastSmtpContent retain() {
        return this.retain();
    }
}

