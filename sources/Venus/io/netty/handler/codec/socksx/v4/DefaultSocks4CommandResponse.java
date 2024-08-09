/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v4.AbstractSocks4Message;
import io.netty.handler.codec.socksx.v4.Socks4CommandResponse;
import io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;

public class DefaultSocks4CommandResponse
extends AbstractSocks4Message
implements Socks4CommandResponse {
    private final Socks4CommandStatus status;
    private final String dstAddr;
    private final int dstPort;

    public DefaultSocks4CommandResponse(Socks4CommandStatus socks4CommandStatus) {
        this(socks4CommandStatus, null, 0);
    }

    public DefaultSocks4CommandResponse(Socks4CommandStatus socks4CommandStatus, String string, int n) {
        if (socks4CommandStatus == null) {
            throw new NullPointerException("cmdStatus");
        }
        if (string != null && !NetUtil.isValidIpV4Address(string)) {
            throw new IllegalArgumentException("dstAddr: " + string + " (expected: a valid IPv4 address)");
        }
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("dstPort: " + n + " (expected: 0~65535)");
        }
        this.status = socks4CommandStatus;
        this.dstAddr = string;
        this.dstPort = n;
    }

    @Override
    public Socks4CommandStatus status() {
        return this.status;
    }

    @Override
    public String dstAddr() {
        return this.dstAddr;
    }

    @Override
    public int dstPort() {
        return this.dstPort;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(96);
        stringBuilder.append(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", dstAddr: ");
        } else {
            stringBuilder.append("(dstAddr: ");
        }
        stringBuilder.append(this.dstAddr());
        stringBuilder.append(", dstPort: ");
        stringBuilder.append(this.dstPort());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

