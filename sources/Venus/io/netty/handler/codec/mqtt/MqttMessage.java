/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.util.internal.StringUtil;

public class MqttMessage {
    private final MqttFixedHeader mqttFixedHeader;
    private final Object variableHeader;
    private final Object payload;
    private final DecoderResult decoderResult;

    public MqttMessage(MqttFixedHeader mqttFixedHeader) {
        this(mqttFixedHeader, null, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object object) {
        this(mqttFixedHeader, object, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object object, Object object2) {
        this(mqttFixedHeader, object, object2, DecoderResult.SUCCESS);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object object, Object object2, DecoderResult decoderResult) {
        this.mqttFixedHeader = mqttFixedHeader;
        this.variableHeader = object;
        this.payload = object2;
        this.decoderResult = decoderResult;
    }

    public MqttFixedHeader fixedHeader() {
        return this.mqttFixedHeader;
    }

    public Object variableHeader() {
        return this.variableHeader;
    }

    public Object payload() {
        return this.payload;
    }

    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "fixedHeader=" + (this.fixedHeader() != null ? this.fixedHeader().toString() : "") + ", variableHeader=" + (this.variableHeader() != null ? this.variableHeader.toString() : "") + ", payload=" + (this.payload() != null ? this.payload.toString() : "") + ']';
    }
}

