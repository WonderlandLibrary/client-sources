/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequest;
import io.netty.util.internal.StringUtil;

public class DefaultSocks5PasswordAuthRequest
extends AbstractSocks5Message
implements Socks5PasswordAuthRequest {
    private final String username;
    private final String password;

    public DefaultSocks5PasswordAuthRequest(String string, String string2) {
        if (string == null) {
            throw new NullPointerException("username");
        }
        if (string2 == null) {
            throw new NullPointerException("password");
        }
        if (string.length() > 255) {
            throw new IllegalArgumentException("username: **** (expected: less than 256 chars)");
        }
        if (string2.length() > 255) {
            throw new IllegalArgumentException("password: **** (expected: less than 256 chars)");
        }
        this.username = string;
        this.password = string2;
    }

    @Override
    public String username() {
        return this.username;
    }

    @Override
    public String password() {
        return this.password;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", username: ");
        } else {
            stringBuilder.append("(username: ");
        }
        stringBuilder.append(this.username());
        stringBuilder.append(", password: ****)");
        return stringBuilder.toString();
    }
}

