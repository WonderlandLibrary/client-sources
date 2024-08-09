/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttMessageIdVariableHeader {
    private final int messageId;

    public static MqttMessageIdVariableHeader from(int n) {
        if (n < 1 || n > 65535) {
            throw new IllegalArgumentException("messageId: " + n + " (expected: 1 ~ 65535)");
        }
        return new MqttMessageIdVariableHeader(n);
    }

    private MqttMessageIdVariableHeader(int n) {
        this.messageId = n;
    }

    public int messageId() {
        return this.messageId;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "messageId=" + this.messageId + ']';
    }
}

