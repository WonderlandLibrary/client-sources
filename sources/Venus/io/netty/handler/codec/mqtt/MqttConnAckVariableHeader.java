/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.util.internal.StringUtil;

public final class MqttConnAckVariableHeader {
    private final MqttConnectReturnCode connectReturnCode;
    private final boolean sessionPresent;

    public MqttConnAckVariableHeader(MqttConnectReturnCode mqttConnectReturnCode, boolean bl) {
        this.connectReturnCode = mqttConnectReturnCode;
        this.sessionPresent = bl;
    }

    public MqttConnectReturnCode connectReturnCode() {
        return this.connectReturnCode;
    }

    public boolean isSessionPresent() {
        return this.sessionPresent;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "connectReturnCode=" + (Object)((Object)this.connectReturnCode) + ", sessionPresent=" + this.sessionPresent + ']';
    }
}

