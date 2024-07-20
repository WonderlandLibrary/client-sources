/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttConnectPayload {
    private final String clientIdentifier;
    private final String willTopic;
    private final String willMessage;
    private final String userName;
    private final String password;

    public MqttConnectPayload(String clientIdentifier, String willTopic, String willMessage, String userName, String password) {
        this.clientIdentifier = clientIdentifier;
        this.willTopic = willTopic;
        this.willMessage = willMessage;
        this.userName = userName;
        this.password = password;
    }

    public String clientIdentifier() {
        return this.clientIdentifier;
    }

    public String willTopic() {
        return this.willTopic;
    }

    public String willMessage() {
        return this.willMessage;
    }

    public String userName() {
        return this.userName;
    }

    public String password() {
        return this.password;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "clientIdentifier=" + this.clientIdentifier + ", willTopic=" + this.willTopic + ", willMessage=" + this.willMessage + ", userName=" + this.userName + ", password=" + this.password + ']';
    }
}

