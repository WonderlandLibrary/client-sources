/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.handler.codec.smtp.SmtpContent;

public class DefaultSmtpContent
extends DefaultByteBufHolder
implements SmtpContent {
    public DefaultSmtpContent(ByteBuf data) {
        super(data);
    }

    @Override
    public SmtpContent copy() {
        return (SmtpContent)super.copy();
    }

    @Override
    public SmtpContent duplicate() {
        return (SmtpContent)super.duplicate();
    }

    @Override
    public SmtpContent retainedDuplicate() {
        return (SmtpContent)super.retainedDuplicate();
    }

    @Override
    public SmtpContent replace(ByteBuf content) {
        return new DefaultSmtpContent(content);
    }

    @Override
    public SmtpContent retain() {
        super.retain();
        return this;
    }

    @Override
    public SmtpContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public SmtpContent touch() {
        super.touch();
        return this;
    }

    @Override
    public SmtpContent touch(Object hint) {
        super.touch(hint);
        return this;
    }
}

