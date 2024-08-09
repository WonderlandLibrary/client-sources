/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

public final class MqttUnsubscribePayload {
    private final List<String> topics;

    public MqttUnsubscribePayload(List<String> list) {
        this.topics = Collections.unmodifiableList(list);
    }

    public List<String> topics() {
        return this.topics;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this)).append('[');
        for (int i = 0; i < this.topics.size() - 1; ++i) {
            stringBuilder.append("topicName = ").append(this.topics.get(i)).append(", ");
        }
        stringBuilder.append("topicName = ").append(this.topics.get(this.topics.size() - 1)).append(']');
        return stringBuilder.toString();
    }
}

