/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;

public final class MqttConnAckMessage
extends MqttMessage {
    public MqttConnAckMessage(MqttFixedHeader mqttFixedHeader, MqttConnAckVariableHeader variableHeader) {
        super(mqttFixedHeader, variableHeader);
    }

    @Override
    public MqttConnAckVariableHeader variableHeader() {
        return (MqttConnAckVariableHeader)super.variableHeader();
    }
}

