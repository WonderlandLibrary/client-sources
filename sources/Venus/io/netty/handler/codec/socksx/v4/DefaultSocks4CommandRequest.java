/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v4.AbstractSocks4Message;
import io.netty.handler.codec.socksx.v4.Socks4CommandRequest;
import io.netty.handler.codec.socksx.v4.Socks4CommandType;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public class DefaultSocks4CommandRequest
extends AbstractSocks4Message
implements Socks4CommandRequest {
    private final Socks4CommandType type;
    private final String dstAddr;
    private final int dstPort;
    private final String userId;

    public DefaultSocks4CommandRequest(Socks4CommandType socks4CommandType, String string, int n) {
        this(socks4CommandType, string, n, "");
    }

    public DefaultSocks4CommandRequest(Socks4CommandType socks4CommandType, String string, int n, String string2) {
        if (socks4CommandType == null) {
            throw new NullPointerException("type");
        }
        if (string == null) {
            throw new NullPointerException("dstAddr");
        }
        if (n <= 0 || n >= 65536) {
            throw new IllegalArgumentException("dstPort: " + n + " (expected: 1~65535)");
        }
        if (string2 == null) {
            throw new NullPointerException("userId");
        }
        this.userId = string2;
        this.type = socks4CommandType;
        this.dstAddr = IDN.toASCII(string);
        this.dstPort = n;
    }

    @Override
    public Socks4CommandType type() {
        return this.type;
    }

    @Override
    public String dstAddr() {
        return this.dstAddr;
    }

    @Override
    public int dstPort() {
        return this.dstPort;
    }

    @Override
    public String userId() {
        return this.userId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", type: ");
        } else {
            stringBuilder.append("(type: ");
        }
        stringBuilder.append(this.type());
        stringBuilder.append(", dstAddr: ");
        stringBuilder.append(this.dstAddr());
        stringBuilder.append(", dstPort: ");
        stringBuilder.append(this.dstPort());
        stringBuilder.append(", userId: ");
        stringBuilder.append(this.userId());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

