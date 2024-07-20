/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface SmtpContent
extends ByteBufHolder {
    @Override
    public SmtpContent copy();

    @Override
    public SmtpContent duplicate();

    @Override
    public SmtpContent retainedDuplicate();

    @Override
    public SmtpContent replace(ByteBuf var1);

    @Override
    public SmtpContent retain();

    @Override
    public SmtpContent retain(int var1);

    @Override
    public SmtpContent touch();

    @Override
    public SmtpContent touch(Object var1);
}

