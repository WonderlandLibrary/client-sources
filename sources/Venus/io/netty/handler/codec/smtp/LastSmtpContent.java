/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.smtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.smtp.DefaultLastSmtpContent;
import io.netty.handler.codec.smtp.SmtpContent;
import io.netty.util.ReferenceCounted;

public interface LastSmtpContent
extends SmtpContent {
    public static final LastSmtpContent EMPTY_LAST_CONTENT = new LastSmtpContent(){

        @Override
        public LastSmtpContent copy() {
            return this;
        }

        @Override
        public LastSmtpContent duplicate() {
            return this;
        }

        @Override
        public LastSmtpContent retainedDuplicate() {
            return this;
        }

        @Override
        public LastSmtpContent replace(ByteBuf byteBuf) {
            return new DefaultLastSmtpContent(byteBuf);
        }

        @Override
        public LastSmtpContent retain() {
            return this;
        }

        @Override
        public LastSmtpContent retain(int n) {
            return this;
        }

        @Override
        public LastSmtpContent touch() {
            return this;
        }

        @Override
        public LastSmtpContent touch(Object object) {
            return this;
        }

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public int refCnt() {
            return 0;
        }

        @Override
        public boolean release() {
            return true;
        }

        @Override
        public boolean release(int n) {
            return true;
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
    };

    @Override
    public LastSmtpContent copy();

    @Override
    public LastSmtpContent duplicate();

    @Override
    public LastSmtpContent retainedDuplicate();

    @Override
    public LastSmtpContent replace(ByteBuf var1);

    @Override
    public LastSmtpContent retain();

    @Override
    public LastSmtpContent retain(int var1);

    @Override
    public LastSmtpContent touch();

    @Override
    public LastSmtpContent touch(Object var1);
}

