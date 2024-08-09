/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribePayload;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.List;

public final class MqttMessageBuilders {
    public static ConnectBuilder connect() {
        return new ConnectBuilder();
    }

    public static ConnAckBuilder connAck() {
        return new ConnAckBuilder();
    }

    public static PublishBuilder publish() {
        return new PublishBuilder();
    }

    public static SubscribeBuilder subscribe() {
        return new SubscribeBuilder();
    }

    public static UnsubscribeBuilder unsubscribe() {
        return new UnsubscribeBuilder();
    }

    private MqttMessageBuilders() {
    }

    public static final class ConnAckBuilder {
        private MqttConnectReturnCode returnCode;
        private boolean sessionPresent;

        ConnAckBuilder() {
        }

        public ConnAckBuilder returnCode(MqttConnectReturnCode mqttConnectReturnCode) {
            this.returnCode = mqttConnectReturnCode;
            return this;
        }

        public ConnAckBuilder sessionPresent(boolean bl) {
            this.sessionPresent = bl;
            return this;
        }

        public MqttConnAckMessage build() {
            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
            MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(this.returnCode, this.sessionPresent);
            return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
        }
    }

    public static final class UnsubscribeBuilder {
        private List<String> topicFilters;
        private int messageId;

        UnsubscribeBuilder() {
        }

        public UnsubscribeBuilder addTopicFilter(String string) {
            if (this.topicFilters == null) {
                this.topicFilters = new ArrayList<String>(5);
            }
            this.topicFilters.add(string);
            return this;
        }

        public UnsubscribeBuilder messageId(int n) {
            this.messageId = n;
            return this;
        }

        public MqttUnsubscribeMessage build() {
            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
            MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(this.messageId);
            MqttUnsubscribePayload mqttUnsubscribePayload = new MqttUnsubscribePayload(this.topicFilters);
            return new MqttUnsubscribeMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttUnsubscribePayload);
        }
    }

    public static final class SubscribeBuilder {
        private List<MqttTopicSubscription> subscriptions;
        private int messageId;

        SubscribeBuilder() {
        }

        public SubscribeBuilder addSubscription(MqttQoS mqttQoS, String string) {
            if (this.subscriptions == null) {
                this.subscriptions = new ArrayList<MqttTopicSubscription>(5);
            }
            this.subscriptions.add(new MqttTopicSubscription(string, mqttQoS));
            return this;
        }

        public SubscribeBuilder messageId(int n) {
            this.messageId = n;
            return this;
        }

        public MqttSubscribeMessage build() {
            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.SUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
            MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(this.messageId);
            MqttSubscribePayload mqttSubscribePayload = new MqttSubscribePayload(this.subscriptions);
            return new MqttSubscribeMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubscribePayload);
        }
    }

    public static final class ConnectBuilder {
        private MqttVersion version = MqttVersion.MQTT_3_1_1;
        private String clientId;
        private boolean cleanSession;
        private boolean hasUser;
        private boolean hasPassword;
        private int keepAliveSecs;
        private boolean willFlag;
        private boolean willRetain;
        private MqttQoS willQos = MqttQoS.AT_MOST_ONCE;
        private String willTopic;
        private byte[] willMessage;
        private String username;
        private byte[] password;

        ConnectBuilder() {
        }

        public ConnectBuilder protocolVersion(MqttVersion mqttVersion) {
            this.version = mqttVersion;
            return this;
        }

        public ConnectBuilder clientId(String string) {
            this.clientId = string;
            return this;
        }

        public ConnectBuilder cleanSession(boolean bl) {
            this.cleanSession = bl;
            return this;
        }

        public ConnectBuilder keepAlive(int n) {
            this.keepAliveSecs = n;
            return this;
        }

        public ConnectBuilder willFlag(boolean bl) {
            this.willFlag = bl;
            return this;
        }

        public ConnectBuilder willQoS(MqttQoS mqttQoS) {
            this.willQos = mqttQoS;
            return this;
        }

        public ConnectBuilder willTopic(String string) {
            this.willTopic = string;
            return this;
        }

        @Deprecated
        public ConnectBuilder willMessage(String string) {
            this.willMessage(string == null ? null : string.getBytes(CharsetUtil.UTF_8));
            return this;
        }

        public ConnectBuilder willMessage(byte[] byArray) {
            this.willMessage = byArray;
            return this;
        }

        public ConnectBuilder willRetain(boolean bl) {
            this.willRetain = bl;
            return this;
        }

        public ConnectBuilder hasUser(boolean bl) {
            this.hasUser = bl;
            return this;
        }

        public ConnectBuilder hasPassword(boolean bl) {
            this.hasPassword = bl;
            return this;
        }

        public ConnectBuilder username(String string) {
            this.hasUser = string != null;
            this.username = string;
            return this;
        }

        @Deprecated
        public ConnectBuilder password(String string) {
            this.password(string == null ? null : string.getBytes(CharsetUtil.UTF_8));
            return this;
        }

        public ConnectBuilder password(byte[] byArray) {
            this.hasPassword = byArray != null;
            this.password = byArray;
            return this;
        }

        public MqttConnectMessage build() {
            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
            MqttConnectVariableHeader mqttConnectVariableHeader = new MqttConnectVariableHeader(this.version.protocolName(), this.version.protocolLevel(), this.hasUser, this.hasPassword, this.willRetain, this.willQos.value(), this.willFlag, this.cleanSession, this.keepAliveSecs);
            MqttConnectPayload mqttConnectPayload = new MqttConnectPayload(this.clientId, this.willTopic, this.willMessage, this.username, this.password);
            return new MqttConnectMessage(mqttFixedHeader, mqttConnectVariableHeader, mqttConnectPayload);
        }
    }

    public static final class PublishBuilder {
        private String topic;
        private boolean retained;
        private MqttQoS qos;
        private ByteBuf payload;
        private int messageId;

        PublishBuilder() {
        }

        public PublishBuilder topicName(String string) {
            this.topic = string;
            return this;
        }

        public PublishBuilder retained(boolean bl) {
            this.retained = bl;
            return this;
        }

        public PublishBuilder qos(MqttQoS mqttQoS) {
            this.qos = mqttQoS;
            return this;
        }

        public PublishBuilder payload(ByteBuf byteBuf) {
            this.payload = byteBuf;
            return this;
        }

        public PublishBuilder messageId(int n) {
            this.messageId = n;
            return this;
        }

        public MqttPublishMessage build() {
            MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH, false, this.qos, this.retained, 0);
            MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(this.topic, this.messageId);
            return new MqttPublishMessage(mqttFixedHeader, mqttPublishVariableHeader, Unpooled.buffer().writeBytes(this.payload));
        }
    }
}

