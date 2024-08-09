/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MqttPublishMessage
extends MqttMessage
implements ByteBufHolder {
    public MqttPublishMessage(MqttFixedHeader mqttFixedHeader, MqttPublishVariableHeader mqttPublishVariableHeader, ByteBuf byteBuf) {
        super(mqttFixedHeader, mqttPublishVariableHeader, byteBuf);
    }

    @Override
    public MqttPublishVariableHeader variableHeader() {
        return (MqttPublishVariableHeader)super.variableHeader();
    }

    @Override
    public ByteBuf payload() {
        return this.content();
    }

    @Override
    public ByteBuf content() {
        ByteBuf byteBuf = (ByteBuf)super.payload();
        if (byteBuf.refCnt() <= 0) {
            throw new IllegalReferenceCountException(byteBuf.refCnt());
        }
        return byteBuf;
    }

    @Override
    public MqttPublishMessage copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public MqttPublishMessage duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public MqttPublishMessage retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public MqttPublishMessage replace(ByteBuf byteBuf) {
        return new MqttPublishMessage(this.fixedHeader(), this.variableHeader(), byteBuf);
    }

    @Override
    public int refCnt() {
        return this.content().refCnt();
    }

    @Override
    public MqttPublishMessage retain() {
        this.content().retain();
        return this;
    }

    @Override
    public MqttPublishMessage retain(int n) {
        this.content().retain(n);
        return this;
    }

    @Override
    public MqttPublishMessage touch() {
        this.content().touch();
        return this;
    }

    @Override
    public MqttPublishMessage touch(Object object) {
        this.content().touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.content().release();
    }

    @Override
    public boolean release(int n) {
        return this.content().release(n);
    }

    @Override
    public Object payload() {
        return this.payload();
    }

    @Override
    public Object variableHeader() {
        return this.variableHeader();
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

