/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.socks.SocksRequest;
import io.netty.handler.codec.socks.SocksRequestType;
import io.netty.handler.codec.socks.SocksSubnegotiationVersion;
import io.netty.util.CharsetUtil;
import java.nio.charset.CharsetEncoder;

public final class SocksAuthRequest
extends SocksRequest {
    private static final CharsetEncoder asciiEncoder = CharsetUtil.encoder(CharsetUtil.US_ASCII);
    private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
    private final String username;
    private final String password;

    public SocksAuthRequest(String string, String string2) {
        super(SocksRequestType.AUTH);
        if (string == null) {
            throw new NullPointerException("username");
        }
        if (string2 == null) {
            throw new NullPointerException("username");
        }
        if (!asciiEncoder.canEncode(string) || !asciiEncoder.canEncode(string2)) {
            throw new IllegalArgumentException("username: " + string + " or password: **** values should be in pure ascii");
        }
        if (string.length() > 255) {
            throw new IllegalArgumentException("username: " + string + " exceeds 255 char limit");
        }
        if (string2.length() > 255) {
            throw new IllegalArgumentException("password: **** exceeds 255 char limit");
        }
        this.username = string;
        this.password = string2;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
        byteBuf.writeByte(this.username.length());
        byteBuf.writeCharSequence(this.username, CharsetUtil.US_ASCII);
        byteBuf.writeByte(this.password.length());
        byteBuf.writeCharSequence(this.password, CharsetUtil.US_ASCII);
    }
}

