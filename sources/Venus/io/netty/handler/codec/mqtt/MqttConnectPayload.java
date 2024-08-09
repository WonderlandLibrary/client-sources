/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

public final class MqttConnectPayload {
    private final String clientIdentifier;
    private final String willTopic;
    private final byte[] willMessage;
    private final String userName;
    private final byte[] password;

    @Deprecated
    public MqttConnectPayload(String string, String string2, String string3, String string4, String string5) {
        this(string, string2, string3.getBytes(CharsetUtil.UTF_8), string4, string5.getBytes(CharsetUtil.UTF_8));
    }

    public MqttConnectPayload(String string, String string2, byte[] byArray, String string3, byte[] byArray2) {
        this.clientIdentifier = string;
        this.willTopic = string2;
        this.willMessage = byArray;
        this.userName = string3;
        this.password = byArray2;
    }

    public String clientIdentifier() {
        return this.clientIdentifier;
    }

    public String willTopic() {
        return this.willTopic;
    }

    @Deprecated
    public String willMessage() {
        return this.willMessage == null ? null : new String(this.willMessage, CharsetUtil.UTF_8);
    }

    public byte[] willMessageInBytes() {
        return this.willMessage;
    }

    public String userName() {
        return this.userName;
    }

    @Deprecated
    public String password() {
        return this.password == null ? null : new String(this.password, CharsetUtil.UTF_8);
    }

    public byte[] passwordInBytes() {
        return this.password;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "clientIdentifier=" + this.clientIdentifier + ", willTopic=" + this.willTopic + ", willMessage=" + this.willMessage + ", userName=" + this.userName + ", password=" + this.password + ']';
    }
}

