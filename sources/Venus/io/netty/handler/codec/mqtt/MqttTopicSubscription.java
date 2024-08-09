/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.internal.StringUtil;

public final class MqttTopicSubscription {
    private final String topicFilter;
    private final MqttQoS qualityOfService;

    public MqttTopicSubscription(String string, MqttQoS mqttQoS) {
        this.topicFilter = string;
        this.qualityOfService = mqttQoS;
    }

    public String topicName() {
        return this.topicFilter;
    }

    public MqttQoS qualityOfService() {
        return this.qualityOfService;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "topicFilter=" + this.topicFilter + ", qualityOfService=" + (Object)((Object)this.qualityOfService) + ']';
    }
}

