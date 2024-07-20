/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.mqtt.MqttCodecUtil;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttIdentifierRejectedException;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageFactory;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttSubscribePayload;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribePayload;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.List;

public final class MqttDecoder
extends ReplayingDecoder<DecoderState> {
    private static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;
    private MqttFixedHeader mqttFixedHeader;
    private Object variableHeader;
    private int bytesRemainingInVariablePart;
    private final int maxBytesInMessage;

    public MqttDecoder() {
        this(8092);
    }

    public MqttDecoder(int maxBytesInMessage) {
        super(DecoderState.READ_FIXED_HEADER);
        this.maxBytesInMessage = maxBytesInMessage;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        switch ((DecoderState)((Object)this.state())) {
            case READ_FIXED_HEADER: {
                this.mqttFixedHeader = MqttDecoder.decodeFixedHeader(buffer);
                this.bytesRemainingInVariablePart = this.mqttFixedHeader.remainingLength();
                this.checkpoint(DecoderState.READ_VARIABLE_HEADER);
            }
            case READ_VARIABLE_HEADER: {
                try {
                    if (this.bytesRemainingInVariablePart > this.maxBytesInMessage) {
                        throw new DecoderException("too large message: " + this.bytesRemainingInVariablePart + " bytes");
                    }
                    Result<?> decodedVariableHeader = MqttDecoder.decodeVariableHeader(buffer, this.mqttFixedHeader);
                    this.variableHeader = ((Result)decodedVariableHeader).value;
                    this.bytesRemainingInVariablePart -= ((Result)decodedVariableHeader).numberOfBytesConsumed;
                    this.checkpoint(DecoderState.READ_PAYLOAD);
                } catch (Exception cause) {
                    out.add(this.invalidMessage(cause));
                    return;
                }
            }
            case READ_PAYLOAD: {
                try {
                    Result<?> decodedPayload = MqttDecoder.decodePayload(buffer, this.mqttFixedHeader.messageType(), this.bytesRemainingInVariablePart, this.variableHeader);
                    this.bytesRemainingInVariablePart -= ((Result)decodedPayload).numberOfBytesConsumed;
                    if (this.bytesRemainingInVariablePart != 0) {
                        throw new DecoderException("non-zero remaining payload bytes: " + this.bytesRemainingInVariablePart + " (" + (Object)((Object)this.mqttFixedHeader.messageType()) + ')');
                    }
                    this.checkpoint(DecoderState.READ_FIXED_HEADER);
                    MqttMessage message = MqttMessageFactory.newMessage(this.mqttFixedHeader, this.variableHeader, ((Result)decodedPayload).value);
                    this.mqttFixedHeader = null;
                    this.variableHeader = null;
                    out.add(message);
                    break;
                } catch (Exception cause) {
                    out.add(this.invalidMessage(cause));
                    return;
                }
            }
            case BAD_MESSAGE: {
                buffer.skipBytes(this.actualReadableBytes());
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    private MqttMessage invalidMessage(Throwable cause) {
        this.checkpoint(DecoderState.BAD_MESSAGE);
        return MqttMessageFactory.newInvalidMessage(cause);
    }

    private static MqttFixedHeader decodeFixedHeader(ByteBuf buffer) {
        short digit;
        short b1 = buffer.readUnsignedByte();
        MqttMessageType messageType = MqttMessageType.valueOf(b1 >> 4);
        boolean dupFlag = (b1 & 8) == 8;
        int qosLevel = (b1 & 6) >> 1;
        boolean retain = (b1 & 1) != 0;
        int remainingLength = 0;
        int multiplier = 1;
        int loops = 0;
        do {
            digit = buffer.readUnsignedByte();
            remainingLength += (digit & 0x7F) * multiplier;
            multiplier *= 128;
        } while ((digit & 0x80) != 0 && ++loops < 4);
        if (loops == 4 && (digit & 0x80) != 0) {
            throw new DecoderException("remaining length exceeds 4 digits (" + (Object)((Object)messageType) + ')');
        }
        MqttFixedHeader decodedFixedHeader = new MqttFixedHeader(messageType, dupFlag, MqttQoS.valueOf(qosLevel), retain, remainingLength);
        return MqttCodecUtil.validateFixedHeader(MqttCodecUtil.resetUnusedFields(decodedFixedHeader));
    }

    private static Result<?> decodeVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case CONNECT: {
                return MqttDecoder.decodeConnectionVariableHeader(buffer);
            }
            case CONNACK: {
                return MqttDecoder.decodeConnAckVariableHeader(buffer);
            }
            case SUBSCRIBE: 
            case UNSUBSCRIBE: 
            case SUBACK: 
            case UNSUBACK: 
            case PUBACK: 
            case PUBREC: 
            case PUBCOMP: 
            case PUBREL: {
                return MqttDecoder.decodeMessageIdVariableHeader(buffer);
            }
            case PUBLISH: {
                return MqttDecoder.decodePublishVariableHeader(buffer, mqttFixedHeader);
            }
            case PINGREQ: 
            case PINGRESP: 
            case DISCONNECT: {
                return new Result<Object>(null, 0);
            }
        }
        return new Result<Object>(null, 0);
    }

    private static Result<MqttConnectVariableHeader> decodeConnectionVariableHeader(ByteBuf buffer) {
        boolean cleanSession;
        Result<String> protoString = MqttDecoder.decodeString(buffer);
        int numberOfBytesConsumed = ((Result)protoString).numberOfBytesConsumed;
        byte protocolLevel = buffer.readByte();
        ++numberOfBytesConsumed;
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel((String)((Result)protoString).value, protocolLevel);
        short b1 = buffer.readUnsignedByte();
        ++numberOfBytesConsumed;
        Result<Integer> keepAlive = MqttDecoder.decodeMsbLsb(buffer);
        numberOfBytesConsumed += ((Result)keepAlive).numberOfBytesConsumed;
        boolean hasUserName = (b1 & 0x80) == 128;
        boolean hasPassword = (b1 & 0x40) == 64;
        boolean willRetain = (b1 & 0x20) == 32;
        int willQos = (b1 & 0x18) >> 3;
        boolean willFlag = (b1 & 4) == 4;
        boolean bl = cleanSession = (b1 & 2) == 2;
        if (mqttVersion == MqttVersion.MQTT_3_1_1) {
            boolean zeroReservedFlag;
            boolean bl2 = zeroReservedFlag = (b1 & 1) == 0;
            if (!zeroReservedFlag) {
                throw new DecoderException("non-zero reserved flag");
            }
        }
        MqttConnectVariableHeader mqttConnectVariableHeader = new MqttConnectVariableHeader(mqttVersion.protocolName(), mqttVersion.protocolLevel(), hasUserName, hasPassword, willRetain, willQos, willFlag, cleanSession, (Integer)((Result)keepAlive).value);
        return new Result<MqttConnectVariableHeader>(mqttConnectVariableHeader, numberOfBytesConsumed);
    }

    private static Result<MqttConnAckVariableHeader> decodeConnAckVariableHeader(ByteBuf buffer) {
        boolean sessionPresent = (buffer.readUnsignedByte() & 1) == 1;
        byte returnCode = buffer.readByte();
        int numberOfBytesConsumed = 2;
        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.valueOf(returnCode), sessionPresent);
        return new Result<MqttConnAckVariableHeader>(mqttConnAckVariableHeader, 2);
    }

    private static Result<MqttMessageIdVariableHeader> decodeMessageIdVariableHeader(ByteBuf buffer) {
        Result<Integer> messageId = MqttDecoder.decodeMessageId(buffer);
        return new Result<MqttMessageIdVariableHeader>(MqttMessageIdVariableHeader.from((Integer)((Result)messageId).value), ((Result)messageId).numberOfBytesConsumed);
    }

    private static Result<MqttPublishVariableHeader> decodePublishVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
        Result<String> decodedTopic = MqttDecoder.decodeString(buffer);
        if (!MqttCodecUtil.isValidPublishTopicName((String)((Result)decodedTopic).value)) {
            throw new DecoderException("invalid publish topic name: " + (String)((Result)decodedTopic).value + " (contains wildcards)");
        }
        int numberOfBytesConsumed = ((Result)decodedTopic).numberOfBytesConsumed;
        int messageId = -1;
        if (mqttFixedHeader.qosLevel().value() > 0) {
            Result<Integer> decodedMessageId = MqttDecoder.decodeMessageId(buffer);
            messageId = (Integer)((Result)decodedMessageId).value;
            numberOfBytesConsumed += ((Result)decodedMessageId).numberOfBytesConsumed;
        }
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader((String)((Result)decodedTopic).value, messageId);
        return new Result<MqttPublishVariableHeader>(mqttPublishVariableHeader, numberOfBytesConsumed);
    }

    private static Result<Integer> decodeMessageId(ByteBuf buffer) {
        Result<Integer> messageId = MqttDecoder.decodeMsbLsb(buffer);
        if (!MqttCodecUtil.isValidMessageId((Integer)((Result)messageId).value)) {
            throw new DecoderException("invalid messageId: " + ((Result)messageId).value);
        }
        return messageId;
    }

    private static Result<?> decodePayload(ByteBuf buffer, MqttMessageType messageType, int bytesRemainingInVariablePart, Object variableHeader) {
        switch (messageType) {
            case CONNECT: {
                return MqttDecoder.decodeConnectionPayload(buffer, (MqttConnectVariableHeader)variableHeader);
            }
            case SUBSCRIBE: {
                return MqttDecoder.decodeSubscribePayload(buffer, bytesRemainingInVariablePart);
            }
            case SUBACK: {
                return MqttDecoder.decodeSubackPayload(buffer, bytesRemainingInVariablePart);
            }
            case UNSUBSCRIBE: {
                return MqttDecoder.decodeUnsubscribePayload(buffer, bytesRemainingInVariablePart);
            }
            case PUBLISH: {
                return MqttDecoder.decodePublishPayload(buffer, bytesRemainingInVariablePart);
            }
        }
        return new Result<Object>(null, 0);
    }

    private static Result<MqttConnectPayload> decodeConnectionPayload(ByteBuf buffer, MqttConnectVariableHeader mqttConnectVariableHeader) {
        Result<String> decodedClientId = MqttDecoder.decodeString(buffer);
        String decodedClientIdValue = (String)((Result)decodedClientId).value;
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte)mqttConnectVariableHeader.version());
        if (!MqttCodecUtil.isValidClientId(mqttVersion, decodedClientIdValue)) {
            throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + decodedClientIdValue);
        }
        int numberOfBytesConsumed = ((Result)decodedClientId).numberOfBytesConsumed;
        Result<String> decodedWillTopic = null;
        Result<String> decodedWillMessage = null;
        if (mqttConnectVariableHeader.isWillFlag()) {
            decodedWillTopic = MqttDecoder.decodeString(buffer, 0, Short.MAX_VALUE);
            numberOfBytesConsumed += ((Result)decodedWillTopic).numberOfBytesConsumed;
            decodedWillMessage = MqttDecoder.decodeAsciiString(buffer);
            numberOfBytesConsumed += ((Result)decodedWillMessage).numberOfBytesConsumed;
        }
        Result<String> decodedUserName = null;
        Result<String> decodedPassword = null;
        if (mqttConnectVariableHeader.hasUserName()) {
            decodedUserName = MqttDecoder.decodeString(buffer);
            numberOfBytesConsumed += ((Result)decodedUserName).numberOfBytesConsumed;
        }
        if (mqttConnectVariableHeader.hasPassword()) {
            decodedPassword = MqttDecoder.decodeString(buffer);
            numberOfBytesConsumed += ((Result)decodedPassword).numberOfBytesConsumed;
        }
        MqttConnectPayload mqttConnectPayload = new MqttConnectPayload((String)((Result)decodedClientId).value, decodedWillTopic != null ? (String)((Result)decodedWillTopic).value : null, decodedWillMessage != null ? (String)((Result)decodedWillMessage).value : null, decodedUserName != null ? (String)((Result)decodedUserName).value : null, decodedPassword != null ? (String)((Result)decodedPassword).value : null);
        return new Result<MqttConnectPayload>(mqttConnectPayload, numberOfBytesConsumed);
    }

    private static Result<MqttSubscribePayload> decodeSubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        int numberOfBytesConsumed;
        ArrayList<MqttTopicSubscription> subscribeTopics = new ArrayList<MqttTopicSubscription>();
        for (numberOfBytesConsumed = 0; numberOfBytesConsumed < bytesRemainingInVariablePart; ++numberOfBytesConsumed) {
            Result<String> decodedTopicName = MqttDecoder.decodeString(buffer);
            numberOfBytesConsumed += ((Result)decodedTopicName).numberOfBytesConsumed;
            int qos = buffer.readUnsignedByte() & 3;
            subscribeTopics.add(new MqttTopicSubscription((String)((Result)decodedTopicName).value, MqttQoS.valueOf(qos)));
        }
        return new Result<MqttSubscribePayload>(new MqttSubscribePayload(subscribeTopics), numberOfBytesConsumed);
    }

    private static Result<MqttSubAckPayload> decodeSubackPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        int numberOfBytesConsumed;
        ArrayList<Integer> grantedQos = new ArrayList<Integer>();
        for (numberOfBytesConsumed = 0; numberOfBytesConsumed < bytesRemainingInVariablePart; ++numberOfBytesConsumed) {
            int qos = buffer.readUnsignedByte() & 3;
            grantedQos.add(qos);
        }
        return new Result<MqttSubAckPayload>(new MqttSubAckPayload(grantedQos), numberOfBytesConsumed);
    }

    private static Result<MqttUnsubscribePayload> decodeUnsubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        int numberOfBytesConsumed;
        Result<String> decodedTopicName;
        ArrayList<String> unsubscribeTopics = new ArrayList<String>();
        for (numberOfBytesConsumed = 0; numberOfBytesConsumed < bytesRemainingInVariablePart; numberOfBytesConsumed += ((Result)decodedTopicName).numberOfBytesConsumed) {
            decodedTopicName = MqttDecoder.decodeString(buffer);
            unsubscribeTopics.add((String)((Result)decodedTopicName).value);
        }
        return new Result<MqttUnsubscribePayload>(new MqttUnsubscribePayload(unsubscribeTopics), numberOfBytesConsumed);
    }

    private static Result<ByteBuf> decodePublishPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        ByteBuf b = buffer.readRetainedSlice(bytesRemainingInVariablePart);
        return new Result<ByteBuf>(b, bytesRemainingInVariablePart);
    }

    private static Result<String> decodeString(ByteBuf buffer) {
        return MqttDecoder.decodeString(buffer, 0, Integer.MAX_VALUE);
    }

    private static Result<String> decodeAsciiString(ByteBuf buffer) {
        Result<String> result = MqttDecoder.decodeString(buffer, 0, Integer.MAX_VALUE);
        String s = (String)((Result)result).value;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) <= '\u007f') continue;
            return new Result<Object>(null, ((Result)result).numberOfBytesConsumed);
        }
        return new Result<String>(s, ((Result)result).numberOfBytesConsumed);
    }

    private static Result<String> decodeString(ByteBuf buffer, int minBytes, int maxBytes) {
        Result<Integer> decodedSize = MqttDecoder.decodeMsbLsb(buffer);
        int size = (Integer)((Result)decodedSize).value;
        int numberOfBytesConsumed = ((Result)decodedSize).numberOfBytesConsumed;
        if (size < minBytes || size > maxBytes) {
            buffer.skipBytes(size);
            return new Result<Object>(null, numberOfBytesConsumed += size);
        }
        String s = buffer.toString(buffer.readerIndex(), size, CharsetUtil.UTF_8);
        buffer.skipBytes(size);
        return new Result<String>(s, numberOfBytesConsumed += size);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf buffer) {
        return MqttDecoder.decodeMsbLsb(buffer, 0, 65535);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf buffer, int min, int max) {
        short msbSize = buffer.readUnsignedByte();
        short lsbSize = buffer.readUnsignedByte();
        int numberOfBytesConsumed = 2;
        int result = msbSize << 8 | lsbSize;
        if (result < min || result > max) {
            result = -1;
        }
        return new Result<Integer>(result, 2);
    }

    private static final class Result<T> {
        private final T value;
        private final int numberOfBytesConsumed;

        Result(T value, int numberOfBytesConsumed) {
            this.value = value;
            this.numberOfBytesConsumed = numberOfBytesConsumed;
        }
    }

    static enum DecoderState {
        READ_FIXED_HEADER,
        READ_VARIABLE_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE;

    }
}

