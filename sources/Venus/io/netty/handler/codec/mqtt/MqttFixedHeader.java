/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public final class MqttFixedHeader {
    private final MqttMessageType messageType;
    private final boolean isDup;
    private final MqttQoS qosLevel;
    private final boolean isRetain;
    private final int remainingLength;

    public MqttFixedHeader(MqttMessageType mqttMessageType, boolean bl, MqttQoS mqttQoS, boolean bl2, int n) {
        this.messageType = ObjectUtil.checkNotNull(mqttMessageType, "messageType");
        this.isDup = bl;
        this.qosLevel = ObjectUtil.checkNotNull(mqttQoS, "qosLevel");
        this.isRetain = bl2;
        this.remainingLength = n;
    }

    public MqttMessageType messageType() {
        return this.messageType;
    }

    public boolean isDup() {
        return this.isDup;
    }

    public MqttQoS qosLevel() {
        return this.qosLevel;
    }

    public boolean isRetain() {
        return this.isRetain;
    }

    public int remainingLength() {
        return this.remainingLength;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "messageType=" + (Object)((Object)this.messageType) + ", isDup=" + this.isDup + ", qosLevel=" + (Object)((Object)this.qosLevel) + ", isRetain=" + this.isRetain + ", remainingLength=" + this.remainingLength + ']';
    }
}

