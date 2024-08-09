/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttVersion;

final class MqttCodecUtil {
    private static final char[] TOPIC_WILDCARDS = new char[]{'#', '+'};
    private static final int MIN_CLIENT_ID_LENGTH = 1;
    private static final int MAX_CLIENT_ID_LENGTH = 23;

    static boolean isValidPublishTopicName(String string) {
        for (char c : TOPIC_WILDCARDS) {
            if (string.indexOf(c) < 0) continue;
            return true;
        }
        return false;
    }

    static boolean isValidMessageId(int n) {
        return n != 0;
    }

    static boolean isValidClientId(MqttVersion mqttVersion, String string) {
        if (mqttVersion == MqttVersion.MQTT_3_1) {
            return string != null && string.length() >= 1 && string.length() <= 23;
        }
        if (mqttVersion == MqttVersion.MQTT_3_1_1) {
            return string != null;
        }
        throw new IllegalArgumentException((Object)((Object)mqttVersion) + " is unknown mqtt version");
    }

    static MqttFixedHeader validateFixedHeader(MqttFixedHeader mqttFixedHeader) {
        switch (1.$SwitchMap$io$netty$handler$codec$mqtt$MqttMessageType[mqttFixedHeader.messageType().ordinal()]) {
            case 1: 
            case 2: 
            case 3: {
                if (mqttFixedHeader.qosLevel() == MqttQoS.AT_LEAST_ONCE) break;
                throw new DecoderException(mqttFixedHeader.messageType().name() + " message must have QoS 1");
            }
        }
        return mqttFixedHeader;
    }

    static MqttFixedHeader resetUnusedFields(MqttFixedHeader mqttFixedHeader) {
        switch (1.$SwitchMap$io$netty$handler$codec$mqtt$MqttMessageType[mqttFixedHeader.messageType().ordinal()]) {
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                if (mqttFixedHeader.isDup() || mqttFixedHeader.qosLevel() != MqttQoS.AT_MOST_ONCE || mqttFixedHeader.isRetain()) {
                    return new MqttFixedHeader(mqttFixedHeader.messageType(), false, MqttQoS.AT_MOST_ONCE, false, mqttFixedHeader.remainingLength());
                }
                return mqttFixedHeader;
            }
            case 1: 
            case 2: 
            case 3: {
                if (mqttFixedHeader.isRetain()) {
                    return new MqttFixedHeader(mqttFixedHeader.messageType(), mqttFixedHeader.isDup(), mqttFixedHeader.qosLevel(), false, mqttFixedHeader.remainingLength());
                }
                return mqttFixedHeader;
            }
        }
        return mqttFixedHeader;
    }

    private MqttCodecUtil() {
    }
}

