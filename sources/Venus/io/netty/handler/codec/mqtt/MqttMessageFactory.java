/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;
import io.netty.handler.codec.mqtt.MqttUnsubAckMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribePayload;

public final class MqttMessageFactory {
    public static MqttMessage newMessage(MqttFixedHeader mqttFixedHeader, Object object, Object object2) {
        switch (1.$SwitchMap$io$netty$handler$codec$mqtt$MqttMessageType[mqttFixedHeader.messageType().ordinal()]) {
            case 1: {
                return new MqttConnectMessage(mqttFixedHeader, (MqttConnectVariableHeader)object, (MqttConnectPayload)object2);
            }
            case 2: {
                return new MqttConnAckMessage(mqttFixedHeader, (MqttConnAckVariableHeader)object);
            }
            case 3: {
                return new MqttSubscribeMessage(mqttFixedHeader, (MqttMessageIdVariableHeader)object, (MqttSubscribePayload)object2);
            }
            case 4: {
                return new MqttSubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader)object, (MqttSubAckPayload)object2);
            }
            case 5: {
                return new MqttUnsubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader)object);
            }
            case 6: {
                return new MqttUnsubscribeMessage(mqttFixedHeader, (MqttMessageIdVariableHeader)object, (MqttUnsubscribePayload)object2);
            }
            case 7: {
                return new MqttPublishMessage(mqttFixedHeader, (MqttPublishVariableHeader)object, (ByteBuf)object2);
            }
            case 8: {
                return new MqttPubAckMessage(mqttFixedHeader, (MqttMessageIdVariableHeader)object);
            }
            case 9: 
            case 10: 
            case 11: {
                return new MqttMessage(mqttFixedHeader, object);
            }
            case 12: 
            case 13: 
            case 14: {
                return new MqttMessage(mqttFixedHeader);
            }
        }
        throw new IllegalArgumentException("unknown message type: " + (Object)((Object)mqttFixedHeader.messageType()));
    }

    public static MqttMessage newInvalidMessage(Throwable throwable) {
        return new MqttMessage(null, null, null, DecoderResult.failure(throwable));
    }

    private MqttMessageFactory() {
    }
}

