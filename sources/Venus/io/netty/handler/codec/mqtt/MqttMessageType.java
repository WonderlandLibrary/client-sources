/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

public enum MqttMessageType {
    CONNECT(1),
    CONNACK(2),
    PUBLISH(3),
    PUBACK(4),
    PUBREC(5),
    PUBREL(6),
    PUBCOMP(7),
    SUBSCRIBE(8),
    SUBACK(9),
    UNSUBSCRIBE(10),
    UNSUBACK(11),
    PINGREQ(12),
    PINGRESP(13),
    DISCONNECT(14);

    private final int value;

    private MqttMessageType(int n2) {
        this.value = n2;
    }

    public int value() {
        return this.value;
    }

    public static MqttMessageType valueOf(int n) {
        for (MqttMessageType mqttMessageType : MqttMessageType.values()) {
            if (mqttMessageType.value != n) continue;
            return mqttMessageType;
        }
        throw new IllegalArgumentException("unknown message type: " + n);
    }
}

