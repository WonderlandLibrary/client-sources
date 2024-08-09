/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponse;
import io.netty.util.internal.StringUtil;

public class DefaultSocks5InitialResponse
extends AbstractSocks5Message
implements Socks5InitialResponse {
    private final Socks5AuthMethod authMethod;

    public DefaultSocks5InitialResponse(Socks5AuthMethod socks5AuthMethod) {
        if (socks5AuthMethod == null) {
            throw new NullPointerException("authMethod");
        }
        this.authMethod = socks5AuthMethod;
    }

    @Override
    public Socks5AuthMethod authMethod() {
        return this.authMethod;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", authMethod: ");
        } else {
            stringBuilder.append("(authMethod: ");
        }
        stringBuilder.append(this.authMethod());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

