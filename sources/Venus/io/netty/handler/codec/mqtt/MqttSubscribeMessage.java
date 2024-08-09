/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class MqttSubscribeMessage
extends MqttMessage {
    public MqttSubscribeMessage(MqttFixedHeader mqttFixedHeader, MqttMessageIdVariableHeader mqttMessageIdVariableHeader, MqttSubscribePayload mqttSubscribePayload) {
        super(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubscribePayload);
    }

    @Override
    public MqttMessageIdVariableHeader variableHeader() {
        return (MqttMessageIdVariableHeader)super.variableHeader();
    }

    @Override
    public MqttSubscribePayload payload() {
        return (MqttSubscribePayload)super.payload();
    }

    @Override
    public Object payload() {
        return this.payload();
    }

    @Override
    public Object variableHeader() {
        return this.variableHeader();
    }
}

