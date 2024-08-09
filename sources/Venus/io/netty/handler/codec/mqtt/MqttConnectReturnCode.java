/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MqttConnectReturnCode {
    CONNECTION_ACCEPTED(0),
    CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION(1),
    CONNECTION_REFUSED_IDENTIFIER_REJECTED(2),
    CONNECTION_REFUSED_SERVER_UNAVAILABLE(3),
    CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD(4),
    CONNECTION_REFUSED_NOT_AUTHORIZED(5);

    private static final Map<Byte, MqttConnectReturnCode> VALUE_TO_CODE_MAP;
    private final byte byteValue;

    private MqttConnectReturnCode(byte by) {
        this.byteValue = by;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public static MqttConnectReturnCode valueOf(byte by) {
        if (VALUE_TO_CODE_MAP.containsKey(by)) {
            return VALUE_TO_CODE_MAP.get(by);
        }
        throw new IllegalArgumentException("unknown connect return code: " + (by & 0xFF));
    }

    static {
        HashMap<Byte, MqttConnectReturnCode> hashMap = new HashMap<Byte, MqttConnectReturnCode>();
        for (MqttConnectReturnCode mqttConnectReturnCode : MqttConnectReturnCode.values()) {
            hashMap.put(mqttConnectReturnCode.byteValue, mqttConnectReturnCode);
        }
        VALUE_TO_CODE_MAP = Collections.unmodifiableMap(hashMap);
    }
}

