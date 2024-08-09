/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

public final class MqttSubscribePayload {
    private final List<MqttTopicSubscription> topicSubscriptions;

    public MqttSubscribePayload(List<MqttTopicSubscription> list) {
        this.topicSubscriptions = Collections.unmodifiableList(list);
    }

    public List<MqttTopicSubscription> topicSubscriptions() {
        return this.topicSubscriptions;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this)).append('[');
        for (int i = 0; i < this.topicSubscriptions.size() - 1; ++i) {
            stringBuilder.append(this.topicSubscriptions.get(i)).append(", ");
        }
        stringBuilder.append(this.topicSubscriptions.get(this.topicSubscriptions.size() - 1));
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

