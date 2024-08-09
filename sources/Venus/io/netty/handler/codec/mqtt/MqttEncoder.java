/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.mqtt.MqttCodecUtil;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttIdentifierRejectedException;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribePayload;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.EmptyArrays;
import java.util.List;

@ChannelHandler.Sharable
public final class MqttEncoder
extends MessageToMessageEncoder<MqttMessage> {
    public static final MqttEncoder INSTANCE = new MqttEncoder();

    private MqttEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage, List<Object> list) throws Exception {
        list.add(MqttEncoder.doEncode(channelHandlerContext.alloc(), mqttMessage));
    }

    static ByteBuf doEncode(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        switch (1.$SwitchMap$io$netty$handler$codec$mqtt$MqttMessageType[mqttMessage.fixedHeader().messageType().ordinal()]) {
            case 1: {
                return MqttEncoder.encodeConnectMessage(byteBufAllocator, (MqttConnectMessage)mqttMessage);
            }
            case 2: {
                return MqttEncoder.encodeConnAckMessage(byteBufAllocator, (MqttConnAckMessage)mqttMessage);
            }
            case 3: {
                return MqttEncoder.encodePublishMessage(byteBufAllocator, (MqttPublishMessage)mqttMessage);
            }
            case 4: {
                return MqttEncoder.encodeSubscribeMessage(byteBufAllocator, (MqttSubscribeMessage)mqttMessage);
            }
            case 5: {
                return MqttEncoder.encodeUnsubscribeMessage(byteBufAllocator, (MqttUnsubscribeMessage)mqttMessage);
            }
            case 6: {
                return MqttEncoder.encodeSubAckMessage(byteBufAllocator, (MqttSubAckMessage)mqttMessage);
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return MqttEncoder.encodeMessageWithOnlySingleByteFixedHeaderAndMessageId(byteBufAllocator, mqttMessage);
            }
            case 12: 
            case 13: 
            case 14: {
                return MqttEncoder.encodeMessageWithOnlySingleByteFixedHeader(byteBufAllocator, mqttMessage);
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + mqttMessage.fixedHeader().messageType().value());
    }

    private static ByteBuf encodeConnectMessage(ByteBufAllocator byteBufAllocator, MqttConnectMessage mqttConnectMessage) {
        byte[] byArray;
        byte[] byArray2;
        String string;
        byte[] byArray3;
        byte[] byArray4;
        int n = 0;
        MqttFixedHeader mqttFixedHeader = mqttConnectMessage.fixedHeader();
        MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();
        MqttConnectPayload mqttConnectPayload = mqttConnectMessage.payload();
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte)mqttConnectVariableHeader.version());
        if (!mqttConnectVariableHeader.hasUserName() && mqttConnectVariableHeader.hasPassword()) {
            throw new DecoderException("Without a username, the password MUST be not set");
        }
        String string2 = mqttConnectPayload.clientIdentifier();
        if (!MqttCodecUtil.isValidClientId(mqttVersion, string2)) {
            throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + string2);
        }
        byte[] byArray5 = MqttEncoder.encodeStringUtf8(string2);
        n += 2 + byArray5.length;
        String string3 = mqttConnectPayload.willTopic();
        byte[] byArray6 = string3 != null ? MqttEncoder.encodeStringUtf8(string3) : EmptyArrays.EMPTY_BYTES;
        byte[] byArray7 = mqttConnectPayload.willMessageInBytes();
        byte[] byArray8 = byArray4 = byArray7 != null ? byArray7 : EmptyArrays.EMPTY_BYTES;
        if (mqttConnectVariableHeader.isWillFlag()) {
            n += 2 + byArray6.length;
            n += 2 + byArray4.length;
        }
        byte[] byArray9 = byArray3 = (string = mqttConnectPayload.userName()) != null ? MqttEncoder.encodeStringUtf8(string) : EmptyArrays.EMPTY_BYTES;
        if (mqttConnectVariableHeader.hasUserName()) {
            n += 2 + byArray3.length;
        }
        byte[] byArray10 = byArray2 = (byArray = mqttConnectPayload.passwordInBytes()) != null ? byArray : EmptyArrays.EMPTY_BYTES;
        if (mqttConnectVariableHeader.hasPassword()) {
            n += 2 + byArray2.length;
        }
        byte[] byArray11 = mqttVersion.protocolNameBytes();
        int n2 = 2 + byArray11.length + 4;
        int n3 = n2 + n;
        int n4 = 1 + MqttEncoder.getVariableLengthInt(n3);
        ByteBuf byteBuf = byteBufAllocator.buffer(n4 + n3);
        byteBuf.writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        MqttEncoder.writeVariableLengthInt(byteBuf, n3);
        byteBuf.writeShort(byArray11.length);
        byteBuf.writeBytes(byArray11);
        byteBuf.writeByte(mqttConnectVariableHeader.version());
        byteBuf.writeByte(MqttEncoder.getConnVariableHeaderFlag(mqttConnectVariableHeader));
        byteBuf.writeShort(mqttConnectVariableHeader.keepAliveTimeSeconds());
        byteBuf.writeShort(byArray5.length);
        byteBuf.writeBytes(byArray5, 0, byArray5.length);
        if (mqttConnectVariableHeader.isWillFlag()) {
            byteBuf.writeShort(byArray6.length);
            byteBuf.writeBytes(byArray6, 0, byArray6.length);
            byteBuf.writeShort(byArray4.length);
            byteBuf.writeBytes(byArray4, 0, byArray4.length);
        }
        if (mqttConnectVariableHeader.hasUserName()) {
            byteBuf.writeShort(byArray3.length);
            byteBuf.writeBytes(byArray3, 0, byArray3.length);
        }
        if (mqttConnectVariableHeader.hasPassword()) {
            byteBuf.writeShort(byArray2.length);
            byteBuf.writeBytes(byArray2, 0, byArray2.length);
        }
        return byteBuf;
    }

    private static int getConnVariableHeaderFlag(MqttConnectVariableHeader mqttConnectVariableHeader) {
        int n = 0;
        if (mqttConnectVariableHeader.hasUserName()) {
            n |= 0x80;
        }
        if (mqttConnectVariableHeader.hasPassword()) {
            n |= 0x40;
        }
        if (mqttConnectVariableHeader.isWillRetain()) {
            n |= 0x20;
        }
        n |= (mqttConnectVariableHeader.willQos() & 3) << 3;
        if (mqttConnectVariableHeader.isWillFlag()) {
            n |= 4;
        }
        if (mqttConnectVariableHeader.isCleanSession()) {
            n |= 2;
        }
        return n;
    }

    private static ByteBuf encodeConnAckMessage(ByteBufAllocator byteBufAllocator, MqttConnAckMessage mqttConnAckMessage) {
        ByteBuf byteBuf = byteBufAllocator.buffer(4);
        byteBuf.writeByte(MqttEncoder.getFixedHeaderByte1(mqttConnAckMessage.fixedHeader()));
        byteBuf.writeByte(2);
        byteBuf.writeByte(mqttConnAckMessage.variableHeader().isSessionPresent() ? 1 : 0);
        byteBuf.writeByte(mqttConnAckMessage.variableHeader().connectReturnCode().byteValue());
        return byteBuf;
    }

    private static ByteBuf encodeSubscribeMessage(ByteBufAllocator byteBufAllocator, MqttSubscribeMessage mqttSubscribeMessage) {
        Object object;
        int n = 2;
        int n2 = 0;
        MqttFixedHeader mqttFixedHeader = mqttSubscribeMessage.fixedHeader();
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = mqttSubscribeMessage.variableHeader();
        MqttSubscribePayload mqttSubscribePayload = mqttSubscribeMessage.payload();
        for (MqttTopicSubscription mqttTopicSubscription : mqttSubscribePayload.topicSubscriptions()) {
            object = mqttTopicSubscription.topicName();
            byte[] byArray = MqttEncoder.encodeStringUtf8((String)object);
            n2 += 2 + byArray.length;
            ++n2;
        }
        int n3 = n + n2;
        int n4 = 1 + MqttEncoder.getVariableLengthInt(n3);
        object = byteBufAllocator.buffer(n4 + n3);
        ((ByteBuf)object).writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        MqttEncoder.writeVariableLengthInt((ByteBuf)object, n3);
        int n5 = mqttMessageIdVariableHeader.messageId();
        ((ByteBuf)object).writeShort(n5);
        for (MqttTopicSubscription mqttTopicSubscription : mqttSubscribePayload.topicSubscriptions()) {
            String string = mqttTopicSubscription.topicName();
            byte[] byArray = MqttEncoder.encodeStringUtf8(string);
            ((ByteBuf)object).writeShort(byArray.length);
            ((ByteBuf)object).writeBytes(byArray, 0, byArray.length);
            ((ByteBuf)object).writeByte(mqttTopicSubscription.qualityOfService().value());
        }
        return object;
    }

    private static ByteBuf encodeUnsubscribeMessage(ByteBufAllocator byteBufAllocator, MqttUnsubscribeMessage mqttUnsubscribeMessage) {
        Object object;
        int n = 2;
        int n2 = 0;
        MqttFixedHeader mqttFixedHeader = mqttUnsubscribeMessage.fixedHeader();
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = mqttUnsubscribeMessage.variableHeader();
        MqttUnsubscribePayload mqttUnsubscribePayload = mqttUnsubscribeMessage.payload();
        for (String string : mqttUnsubscribePayload.topics()) {
            object = MqttEncoder.encodeStringUtf8(string);
            n2 += 2 + ((byte[])object).length;
        }
        int n3 = n + n2;
        int n4 = 1 + MqttEncoder.getVariableLengthInt(n3);
        object = byteBufAllocator.buffer(n4 + n3);
        ((ByteBuf)object).writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        MqttEncoder.writeVariableLengthInt((ByteBuf)object, n3);
        int n5 = mqttMessageIdVariableHeader.messageId();
        ((ByteBuf)object).writeShort(n5);
        for (String string : mqttUnsubscribePayload.topics()) {
            byte[] byArray = MqttEncoder.encodeStringUtf8(string);
            ((ByteBuf)object).writeShort(byArray.length);
            ((ByteBuf)object).writeBytes(byArray, 0, byArray.length);
        }
        return object;
    }

    private static ByteBuf encodeSubAckMessage(ByteBufAllocator byteBufAllocator, MqttSubAckMessage mqttSubAckMessage) {
        int n = 2;
        int n2 = mqttSubAckMessage.payload().grantedQoSLevels().size();
        int n3 = n + n2;
        int n4 = 1 + MqttEncoder.getVariableLengthInt(n3);
        ByteBuf byteBuf = byteBufAllocator.buffer(n4 + n3);
        byteBuf.writeByte(MqttEncoder.getFixedHeaderByte1(mqttSubAckMessage.fixedHeader()));
        MqttEncoder.writeVariableLengthInt(byteBuf, n3);
        byteBuf.writeShort(mqttSubAckMessage.variableHeader().messageId());
        for (int n5 : mqttSubAckMessage.payload().grantedQoSLevels()) {
            byteBuf.writeByte(n5);
        }
        return byteBuf;
    }

    private static ByteBuf encodePublishMessage(ByteBufAllocator byteBufAllocator, MqttPublishMessage mqttPublishMessage) {
        MqttFixedHeader mqttFixedHeader = mqttPublishMessage.fixedHeader();
        MqttPublishVariableHeader mqttPublishVariableHeader = mqttPublishMessage.variableHeader();
        ByteBuf byteBuf = mqttPublishMessage.payload().duplicate();
        String string = mqttPublishVariableHeader.topicName();
        byte[] byArray = MqttEncoder.encodeStringUtf8(string);
        int n = 2 + byArray.length + (mqttFixedHeader.qosLevel().value() > 0 ? 2 : 0);
        int n2 = byteBuf.readableBytes();
        int n3 = n + n2;
        int n4 = 1 + MqttEncoder.getVariableLengthInt(n3);
        ByteBuf byteBuf2 = byteBufAllocator.buffer(n4 + n3);
        byteBuf2.writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        MqttEncoder.writeVariableLengthInt(byteBuf2, n3);
        byteBuf2.writeShort(byArray.length);
        byteBuf2.writeBytes(byArray);
        if (mqttFixedHeader.qosLevel().value() > 0) {
            byteBuf2.writeShort(mqttPublishVariableHeader.messageId());
        }
        byteBuf2.writeBytes(byteBuf);
        return byteBuf2;
    }

    private static ByteBuf encodeMessageWithOnlySingleByteFixedHeaderAndMessageId(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = (MqttMessageIdVariableHeader)mqttMessage.variableHeader();
        int n = mqttMessageIdVariableHeader.messageId();
        int n2 = 2;
        int n3 = 1 + MqttEncoder.getVariableLengthInt(n2);
        ByteBuf byteBuf = byteBufAllocator.buffer(n3 + n2);
        byteBuf.writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        MqttEncoder.writeVariableLengthInt(byteBuf, n2);
        byteBuf.writeShort(n);
        return byteBuf;
    }

    private static ByteBuf encodeMessageWithOnlySingleByteFixedHeader(ByteBufAllocator byteBufAllocator, MqttMessage mqttMessage) {
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        ByteBuf byteBuf = byteBufAllocator.buffer(2);
        byteBuf.writeByte(MqttEncoder.getFixedHeaderByte1(mqttFixedHeader));
        byteBuf.writeByte(0);
        return byteBuf;
    }

    private static int getFixedHeaderByte1(MqttFixedHeader mqttFixedHeader) {
        int n = 0;
        n |= mqttFixedHeader.messageType().value() << 4;
        if (mqttFixedHeader.isDup()) {
            n |= 8;
        }
        n |= mqttFixedHeader.qosLevel().value() << 1;
        if (mqttFixedHeader.isRetain()) {
            n |= 1;
        }
        return n;
    }

    private static void writeVariableLengthInt(ByteBuf byteBuf, int n) {
        do {
            int n2 = n % 128;
            if ((n /= 128) > 0) {
                n2 |= 0x80;
            }
            byteBuf.writeByte(n2);
        } while (n > 0);
    }

    private static int getVariableLengthInt(int n) {
        int n2 = 0;
        do {
            ++n2;
        } while ((n /= 128) > 0);
        return n2;
    }

    private static byte[] encodeStringUtf8(String string) {
        return string.getBytes(CharsetUtil.UTF_8);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (MqttMessage)object, (List<Object>)list);
    }
}

