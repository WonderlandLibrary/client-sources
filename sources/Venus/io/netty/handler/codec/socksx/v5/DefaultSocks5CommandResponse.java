/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponse;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public final class DefaultSocks5CommandResponse
extends AbstractSocks5Message
implements Socks5CommandResponse {
    private final Socks5CommandStatus status;
    private final Socks5AddressType bndAddrType;
    private final String bndAddr;
    private final int bndPort;

    public DefaultSocks5CommandResponse(Socks5CommandStatus socks5CommandStatus, Socks5AddressType socks5AddressType) {
        this(socks5CommandStatus, socks5AddressType, null, 0);
    }

    public DefaultSocks5CommandResponse(Socks5CommandStatus socks5CommandStatus, Socks5AddressType socks5AddressType, String string, int n) {
        if (socks5CommandStatus == null) {
            throw new NullPointerException("status");
        }
        if (socks5AddressType == null) {
            throw new NullPointerException("bndAddrType");
        }
        if (string != null) {
            if (socks5AddressType == Socks5AddressType.IPv4) {
                if (!NetUtil.isValidIpV4Address(string)) {
                    throw new IllegalArgumentException("bndAddr: " + string + " (expected: a valid IPv4 address)");
                }
            } else if (socks5AddressType == Socks5AddressType.DOMAIN) {
                if ((string = IDN.toASCII(string)).length() > 255) {
                    throw new IllegalArgumentException("bndAddr: " + string + " (expected: less than 256 chars)");
                }
            } else if (socks5AddressType == Socks5AddressType.IPv6 && !NetUtil.isValidIpV6Address(string)) {
                throw new IllegalArgumentException("bndAddr: " + string + " (expected: a valid IPv6 address)");
            }
        }
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("bndPort: " + n + " (expected: 0~65535)");
        }
        this.status = socks5CommandStatus;
        this.bndAddrType = socks5AddressType;
        this.bndAddr = string;
        this.bndPort = n;
    }

    @Override
    public Socks5CommandStatus status() {
        return this.status;
    }

    @Override
    public Socks5AddressType bndAddrType() {
        return this.bndAddrType;
    }

    @Override
    public String bndAddr() {
        return this.bndAddr;
    }

    @Override
    public int bndPort() {
        return this.bndPort;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", status: ");
        } else {
            stringBuilder.append("(status: ");
        }
        stringBuilder.append(this.status());
        stringBuilder.append(", bndAddrType: ");
        stringBuilder.append(this.bndAddrType());
        stringBuilder.append(", bndAddr: ");
        stringBuilder.append(this.bndAddr());
        stringBuilder.append(", bndPort: ");
        stringBuilder.append(this.bndPort());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

