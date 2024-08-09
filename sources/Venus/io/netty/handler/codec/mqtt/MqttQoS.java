/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

public enum MqttQoS {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2),
    FAILURE(128);

    private final int value;

    private MqttQoS(int n2) {
        this.value = n2;
    }

    public int value() {
        return this.value;
    }

    public static MqttQoS valueOf(int n) {
        for (MqttQoS mqttQoS : MqttQoS.values()) {
            if (mqttQoS.value != n) continue;
            return mqttQoS;
        }
        throw new IllegalArgumentException("invalid QoS: " + n);
    }
}

