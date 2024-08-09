/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttConnectVariableHeader {
    private final String name;
    private final int version;
    private final boolean hasUserName;
    private final boolean hasPassword;
    private final boolean isWillRetain;
    private final int willQos;
    private final boolean isWillFlag;
    private final boolean isCleanSession;
    private final int keepAliveTimeSeconds;

    public MqttConnectVariableHeader(String string, int n, boolean bl, boolean bl2, boolean bl3, int n2, boolean bl4, boolean bl5, int n3) {
        this.name = string;
        this.version = n;
        this.hasUserName = bl;
        this.hasPassword = bl2;
        this.isWillRetain = bl3;
        this.willQos = n2;
        this.isWillFlag = bl4;
        this.isCleanSession = bl5;
        this.keepAliveTimeSeconds = n3;
    }

    public String name() {
        return this.name;
    }

    public int version() {
        return this.version;
    }

    public boolean hasUserName() {
        return this.hasUserName;
    }

    public boolean hasPassword() {
        return this.hasPassword;
    }

    public boolean isWillRetain() {
        return this.isWillRetain;
    }

    public int willQos() {
        return this.willQos;
    }

    public boolean isWillFlag() {
        return this.isWillFlag;
    }

    public boolean isCleanSession() {
        return this.isCleanSession;
    }

    public int keepAliveTimeSeconds() {
        return this.keepAliveTimeSeconds;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "name=" + this.name + ", version=" + this.version + ", hasUserName=" + this.hasUserName + ", hasPassword=" + this.hasPassword + ", isWillRetain=" + this.isWillRetain + ", isWillFlag=" + this.isWillFlag + ", isCleanSession=" + this.isCleanSession + ", keepAliveTimeSeconds=" + this.keepAliveTimeSeconds + ']';
    }
}

