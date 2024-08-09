/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponse;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;
import io.netty.util.internal.StringUtil;

public class DefaultSocks5PasswordAuthResponse
extends AbstractSocks5Message
implements Socks5PasswordAuthResponse {
    private final Socks5PasswordAuthStatus status;

    public DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus socks5PasswordAuthStatus) {
        if (socks5PasswordAuthStatus == null) {
            throw new NullPointerException("status");
        }
        this.status = socks5PasswordAuthStatus;
    }

    @Override
    public Socks5PasswordAuthStatus status() {
        return this.status;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", status: ");
        } else {
            stringBuilder.append("(status: ");
        }
        stringBuilder.append(this.status());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

