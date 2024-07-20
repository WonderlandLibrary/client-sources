/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderException;

public final class MqttUnacceptableProtocolVersionException
extends DecoderException {
    private static final long serialVersionUID = 4914652213232455749L;

    public MqttUnacceptableProtocolVersionException() {
    }

    public MqttUnacceptableProtocolVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MqttUnacceptableProtocolVersionException(String message) {
        super(message);
    }

    public MqttUnacceptableProtocolVersionException(Throwable cause) {
        super(cause);
    }
}

