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

    public MqttUnacceptableProtocolVersionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MqttUnacceptableProtocolVersionException(String string) {
        super(string);
    }

    public MqttUnacceptableProtocolVersionException(Throwable throwable) {
        super(throwable);
    }
}

