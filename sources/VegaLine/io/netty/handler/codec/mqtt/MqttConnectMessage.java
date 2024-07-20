/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;

public final class MqttConnectMessage
extends MqttMessage {
    public MqttConnectMessage(MqttFixedHeader mqttFixedHeader, MqttConnectVariableHeader variableHeader, MqttConnectPayload payload) {
        super(mqttFixedHeader, variableHeader, payload);
    }

    @Override
    public MqttConnectVariableHeader variableHeader() {
        return (MqttConnectVariableHeader)super.variableHeader();
    }

    @Override
    public MqttConnectPayload payload() {
        return (MqttConnectPayload)super.payload();
    }
}

