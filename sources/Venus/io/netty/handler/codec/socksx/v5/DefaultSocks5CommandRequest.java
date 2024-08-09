/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequest;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public final class DefaultSocks5CommandRequest
extends AbstractSocks5Message
implements Socks5CommandRequest {
    private final Socks5CommandType type;
    private final Socks5AddressType dstAddrType;
    private final String dstAddr;
    private final int dstPort;

    public DefaultSocks5CommandRequest(Socks5CommandType socks5CommandType, Socks5AddressType socks5AddressType, String string, int n) {
        if (socks5CommandType == null) {
            throw new NullPointerException("type");
        }
        if (socks5AddressType == null) {
            throw new NullPointerException("dstAddrType");
        }
        if (string == null) {
            throw new NullPointerException("dstAddr");
        }
        if (socks5AddressType == Socks5AddressType.IPv4) {
            if (!NetUtil.isValidIpV4Address(string)) {
                throw new IllegalArgumentException("dstAddr: " + string + " (expected: a valid IPv4 address)");
            }
        } else if (socks5AddressType == Socks5AddressType.DOMAIN) {
            if ((string = IDN.toASCII(string)).length() > 255) {
                throw new IllegalArgumentException("dstAddr: " + string + " (expected: less than 256 chars)");
            }
        } else if (socks5AddressType == Socks5AddressType.IPv6 && !NetUtil.isValidIpV6Address(string)) {
            throw new IllegalArgumentException("dstAddr: " + string + " (expected: a valid IPv6 address");
        }
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("dstPort: " + n + " (expected: 0~65535)");
        }
        this.type = socks5CommandType;
        this.dstAddrType = socks5AddressType;
        this.dstAddr = string;
        this.dstPort = n;
    }

    @Override
    public Socks5CommandType type() {
        return this.type;
    }

    @Override
    public Socks5AddressType dstAddrType() {
        return this.dstAddrType;
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
        stringBuilder.append(", dstAddrType: ");
        stringBuilder.append(this.dstAddrType());
        stringBuilder.append(", dstAddr: ");
        stringBuilder.append(this.dstAddr());
        stringBuilder.append(", dstPort: ");
        stringBuilder.append(this.dstPort());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

