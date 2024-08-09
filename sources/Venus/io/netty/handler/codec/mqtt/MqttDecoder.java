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

    public MqttDecoder(int n) {
        super(DecoderState.READ_FIXED_HEADER);
        this.maxBytesInMessage = n;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch ((DecoderState)((Object)this.state())) {
            case READ_FIXED_HEADER: {
                try {
                    this.mqttFixedHeader = MqttDecoder.decodeFixedHeader(byteBuf);
                    this.bytesRemainingInVariablePart = this.mqttFixedHeader.remainingLength();
                    this.checkpoint(DecoderState.READ_VARIABLE_HEADER);
                } catch (Exception exception) {
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case READ_VARIABLE_HEADER: {
                Result<?> result;
                try {
                    if (this.bytesRemainingInVariablePart > this.maxBytesInMessage) {
                        throw new DecoderException("too large message: " + this.bytesRemainingInVariablePart + " bytes");
                    }
                    result = MqttDecoder.decodeVariableHeader(byteBuf, this.mqttFixedHeader);
                    this.variableHeader = Result.access$000(result);
                    this.bytesRemainingInVariablePart -= Result.access$100(result);
                    this.checkpoint(DecoderState.READ_PAYLOAD);
                } catch (Exception exception) {
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case READ_PAYLOAD: {
                Result<?> result;
                try {
                    result = MqttDecoder.decodePayload(byteBuf, this.mqttFixedHeader.messageType(), this.bytesRemainingInVariablePart, this.variableHeader);
                    this.bytesRemainingInVariablePart -= Result.access$100(result);
                    if (this.bytesRemainingInVariablePart != 0) {
                        throw new DecoderException("non-zero remaining payload bytes: " + this.bytesRemainingInVariablePart + " (" + (Object)((Object)this.mqttFixedHeader.messageType()) + ')');
                    }
                    this.checkpoint(DecoderState.READ_FIXED_HEADER);
                    MqttMessage mqttMessage = MqttMessageFactory.newMessage(this.mqttFixedHeader, this.variableHeader, Result.access$000(result));
                    this.mqttFixedHeader = null;
                    this.variableHeader = null;
                    list.add(mqttMessage);
                    break;
                } catch (Exception exception) {
                    list.add(this.invalidMessage(exception));
                    return;
                }
            }
            case BAD_MESSAGE: {
                byteBuf.skipBytes(this.actualReadableBytes());
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    private MqttMessage invalidMessage(Throwable throwable) {
        this.checkpoint(DecoderState.BAD_MESSAGE);
        return MqttMessageFactory.newInvalidMessage(throwable);
    }

    private static MqttFixedHeader decodeFixedHeader(ByteBuf byteBuf) {
        short s;
        short s2 = byteBuf.readUnsignedByte();
        MqttMessageType mqttMessageType = MqttMessageType.valueOf(s2 >> 4);
        boolean bl = (s2 & 8) == 8;
        int n = (s2 & 6) >> 1;
        boolean bl2 = (s2 & 1) != 0;
        int n2 = 0;
        int n3 = 1;
        int n4 = 0;
        do {
            s = byteBuf.readUnsignedByte();
            n2 += (s & 0x7F) * n3;
            n3 *= 128;
        } while ((s & 0x80) != 0 && ++n4 < 4);
        if (n4 == 4 && (s & 0x80) != 0) {
            throw new DecoderException("remaining length exceeds 4 digits (" + (Object)((Object)mqttMessageType) + ')');
        }
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(mqttMessageType, bl, MqttQoS.valueOf(n), bl2, n2);
        return MqttCodecUtil.validateFixedHeader(MqttCodecUtil.resetUnusedFields(mqttFixedHeader));
    }

    private static Result<?> decodeVariableHeader(ByteBuf byteBuf, MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case CONNECT: {
                return MqttDecoder.decodeConnectionVariableHeader(byteBuf);
            }
            case CONNACK: {
                return MqttDecoder.decodeConnAckVariableHeader(byteBuf);
            }
            case SUBSCRIBE: 
            case UNSUBSCRIBE: 
            case SUBACK: 
            case UNSUBACK: 
            case PUBACK: 
            case PUBREC: 
            case PUBCOMP: 
            case PUBREL: {
                return MqttDecoder.decodeMessageIdVariableHeader(byteBuf);
            }
            case PUBLISH: {
                return MqttDecoder.decodePublishVariableHeader(byteBuf, mqttFixedHeader);
            }
            case PINGREQ: 
            case PINGRESP: 
            case DISCONNECT: {
                return new Result<Object>(null, 0);
            }
        }
        return new Result<Object>(null, 0);
    }

    private static Result<MqttConnectVariableHeader> decodeConnectionVariableHeader(ByteBuf byteBuf) {
        boolean bl;
        Result<String> result = MqttDecoder.decodeString(byteBuf);
        int n = Result.access$100(result);
        byte by = byteBuf.readByte();
        ++n;
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel((String)Result.access$000(result), by);
        short s = byteBuf.readUnsignedByte();
        ++n;
        Result<Integer> result2 = MqttDecoder.decodeMsbLsb(byteBuf);
        n += Result.access$100(result2);
        boolean bl2 = (s & 0x80) == 128;
        boolean bl3 = (s & 0x40) == 64;
        boolean bl4 = (s & 0x20) == 32;
        int n2 = (s & 0x18) >> 3;
        boolean bl5 = (s & 4) == 4;
        boolean bl6 = bl = (s & 2) == 2;
        if (mqttVersion == MqttVersion.MQTT_3_1_1) {
            boolean bl7;
            boolean bl8 = bl7 = (s & 1) == 0;
            if (!bl7) {
                throw new DecoderException("non-zero reserved flag");
            }
        }
        MqttConnectVariableHeader mqttConnectVariableHeader = new MqttConnectVariableHeader(mqttVersion.protocolName(), mqttVersion.protocolLevel(), bl2, bl3, bl4, n2, bl5, bl, (Integer)Result.access$000(result2));
        return new Result<MqttConnectVariableHeader>(mqttConnectVariableHeader, n);
    }

    private static Result<MqttConnAckVariableHeader> decodeConnAckVariableHeader(ByteBuf byteBuf) {
        boolean bl = (byteBuf.readUnsignedByte() & 1) == 1;
        byte by = byteBuf.readByte();
        int n = 2;
        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.valueOf(by), bl);
        return new Result<MqttConnAckVariableHeader>(mqttConnAckVariableHeader, 2);
    }

    private static Result<MqttMessageIdVariableHeader> decodeMessageIdVariableHeader(ByteBuf byteBuf) {
        Result<Integer> result = MqttDecoder.decodeMessageId(byteBuf);
        return new Result<MqttMessageIdVariableHeader>(MqttMessageIdVariableHeader.from((Integer)Result.access$000(result)), Result.access$100(result));
    }

    private static Result<MqttPublishVariableHeader> decodePublishVariableHeader(ByteBuf byteBuf, MqttFixedHeader mqttFixedHeader) {
        Object object;
        Result<String> result = MqttDecoder.decodeString(byteBuf);
        if (!MqttCodecUtil.isValidPublishTopicName((String)Result.access$000(result))) {
            throw new DecoderException("invalid publish topic name: " + (String)Result.access$000(result) + " (contains wildcards)");
        }
        int n = Result.access$100(result);
        int n2 = -1;
        if (mqttFixedHeader.qosLevel().value() > 0) {
            object = MqttDecoder.decodeMessageId(byteBuf);
            n2 = (Integer)Result.access$000(object);
            n += Result.access$100((Result)object);
        }
        object = new MqttPublishVariableHeader((String)Result.access$000(result), n2);
        return new Result<Object>(object, n);
    }

    private static Result<Integer> decodeMessageId(ByteBuf byteBuf) {
        Result<Integer> result = MqttDecoder.decodeMsbLsb(byteBuf);
        if (!MqttCodecUtil.isValidMessageId((Integer)Result.access$000(result))) {
            throw new DecoderException("invalid messageId: " + Result.access$000(result));
        }
        return result;
    }

    private static Result<?> decodePayload(ByteBuf byteBuf, MqttMessageType mqttMessageType, int n, Object object) {
        switch (mqttMessageType) {
            case CONNECT: {
                return MqttDecoder.decodeConnectionPayload(byteBuf, (MqttConnectVariableHeader)object);
            }
            case SUBSCRIBE: {
                return MqttDecoder.decodeSubscribePayload(byteBuf, n);
            }
            case SUBACK: {
                return MqttDecoder.decodeSubackPayload(byteBuf, n);
            }
            case UNSUBSCRIBE: {
                return MqttDecoder.decodeUnsubscribePayload(byteBuf, n);
            }
            case PUBLISH: {
                return MqttDecoder.decodePublishPayload(byteBuf, n);
            }
        }
        return new Result<Object>(null, 0);
    }

    private static Result<MqttConnectPayload> decodeConnectionPayload(ByteBuf byteBuf, MqttConnectVariableHeader mqttConnectVariableHeader) {
        Result<String> result = MqttDecoder.decodeString(byteBuf);
        String string = (String)Result.access$000(result);
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte)mqttConnectVariableHeader.version());
        if (!MqttCodecUtil.isValidClientId(mqttVersion, string)) {
            throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + string);
        }
        int n = Result.access$100(result);
        Result<String> result2 = null;
        Result<byte[]> result3 = null;
        if (mqttConnectVariableHeader.isWillFlag()) {
            result2 = MqttDecoder.decodeString(byteBuf, 0, Short.MAX_VALUE);
            n += Result.access$100(result2);
            result3 = MqttDecoder.decodeByteArray(byteBuf);
            n += Result.access$100(result3);
        }
        Result<String> result4 = null;
        Result<byte[]> result5 = null;
        if (mqttConnectVariableHeader.hasUserName()) {
            result4 = MqttDecoder.decodeString(byteBuf);
            n += Result.access$100(result4);
        }
        if (mqttConnectVariableHeader.hasPassword()) {
            result5 = MqttDecoder.decodeByteArray(byteBuf);
            n += Result.access$100(result5);
        }
        MqttConnectPayload mqttConnectPayload = new MqttConnectPayload((String)Result.access$000(result), result2 != null ? (String)Result.access$000(result2) : null, result3 != null ? (byte[])Result.access$000(result3) : null, result4 != null ? (String)Result.access$000(result4) : null, result5 != null ? (byte[])Result.access$000(result5) : null);
        return new Result<MqttConnectPayload>(mqttConnectPayload, n);
    }

    private static Result<MqttSubscribePayload> decodeSubscribePayload(ByteBuf byteBuf, int n) {
        int n2;
        ArrayList<MqttTopicSubscription> arrayList = new ArrayList<MqttTopicSubscription>();
        for (n2 = 0; n2 < n; ++n2) {
            Result<String> result = MqttDecoder.decodeString(byteBuf);
            n2 += Result.access$100(result);
            int n3 = byteBuf.readUnsignedByte() & 3;
            arrayList.add(new MqttTopicSubscription((String)Result.access$000(result), MqttQoS.valueOf(n3)));
        }
        return new Result<MqttSubscribePayload>(new MqttSubscribePayload(arrayList), n2);
    }

    private static Result<MqttSubAckPayload> decodeSubackPayload(ByteBuf byteBuf, int n) {
        int n2;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (n2 = 0; n2 < n; ++n2) {
            int n3 = byteBuf.readUnsignedByte();
            if (n3 != MqttQoS.FAILURE.value()) {
                n3 &= 3;
            }
            arrayList.add(n3);
        }
        return new Result<MqttSubAckPayload>(new MqttSubAckPayload(arrayList), n2);
    }

    private static Result<MqttUnsubscribePayload> decodeUnsubscribePayload(ByteBuf byteBuf, int n) {
        int n2;
        Result<String> result;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n2 = 0; n2 < n; n2 += Result.access$100(result)) {
            result = MqttDecoder.decodeString(byteBuf);
            arrayList.add((String)Result.access$000(result));
        }
        return new Result<MqttUnsubscribePayload>(new MqttUnsubscribePayload(arrayList), n2);
    }

    private static Result<ByteBuf> decodePublishPayload(ByteBuf byteBuf, int n) {
        ByteBuf byteBuf2 = byteBuf.readRetainedSlice(n);
        return new Result<ByteBuf>(byteBuf2, n);
    }

    private static Result<String> decodeString(ByteBuf byteBuf) {
        return MqttDecoder.decodeString(byteBuf, 0, Integer.MAX_VALUE);
    }

    private static Result<String> decodeString(ByteBuf byteBuf, int n, int n2) {
        Result<Integer> result = MqttDecoder.decodeMsbLsb(byteBuf);
        int n3 = (Integer)Result.access$000(result);
        int n4 = Result.access$100(result);
        if (n3 < n || n3 > n2) {
            byteBuf.skipBytes(n3);
            return new Result<Object>(null, n4 += n3);
        }
        String string = byteBuf.toString(byteBuf.readerIndex(), n3, CharsetUtil.UTF_8);
        byteBuf.skipBytes(n3);
        return new Result<String>(string, n4 += n3);
    }

    private static Result<byte[]> decodeByteArray(ByteBuf byteBuf) {
        Result<Integer> result = MqttDecoder.decodeMsbLsb(byteBuf);
        int n = (Integer)Result.access$000(result);
        byte[] byArray = new byte[n];
        byteBuf.readBytes(byArray);
        return new Result<byte[]>(byArray, Result.access$100(result) + n);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf byteBuf) {
        return MqttDecoder.decodeMsbLsb(byteBuf, 0, 65535);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf byteBuf, int n, int n2) {
        short s = byteBuf.readUnsignedByte();
        short s2 = byteBuf.readUnsignedByte();
        int n3 = 2;
        int n4 = s << 8 | s2;
        if (n4 < n || n4 > n2) {
            n4 = -1;
        }
        return new Result<Integer>(n4, 2);
    }

    private static final class Result<T> {
        private final T value;
        private final int numberOfBytesConsumed;

        Result(T t, int n) {
            this.value = t;
            this.numberOfBytesConsumed = n;
        }

        static Object access$000(Result result) {
            return result.value;
        }

        static int access$100(Result result) {
            return result.numberOfBytesConsumed;
        }
    }

    static enum DecoderState {
        READ_FIXED_HEADER,
        READ_VARIABLE_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE;

    }
}

