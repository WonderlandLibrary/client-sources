/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttUnacceptableProtocolVersionException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;

public enum MqttVersion {
    MQTT_3_1("MQIsdp", 3),
    MQTT_3_1_1("MQTT", 4);

    private final String name;
    private final byte level;

    private MqttVersion(String string2, byte by) {
        this.name = ObjectUtil.checkNotNull(string2, "protocolName");
        this.level = by;
    }

    public String protocolName() {
        return this.name;
    }

    public byte[] protocolNameBytes() {
        return this.name.getBytes(CharsetUtil.UTF_8);
    }

    public byte protocolLevel() {
        return this.level;
    }

    public static MqttVersion fromProtocolNameAndLevel(String string, byte by) {
        for (MqttVersion mqttVersion : MqttVersion.values()) {
            if (!mqttVersion.name.equals(string)) continue;
            if (mqttVersion.level == by) {
                return mqttVersion;
            }
            throw new MqttUnacceptableProtocolVersionException(string + " and " + by + " are not match");
        }
        throw new MqttUnacceptableProtocolVersionException(string + "is unknown protocol name");
    }
}

