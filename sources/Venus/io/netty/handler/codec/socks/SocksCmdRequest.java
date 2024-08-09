/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.socks.SocksAddressType;
import io.netty.handler.codec.socks.SocksCmdType;
import io.netty.handler.codec.socks.SocksRequest;
import io.netty.handler.codec.socks.SocksRequestType;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

public final class SocksCmdRequest
extends SocksRequest {
    private final SocksCmdType cmdType;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;

    public SocksCmdRequest(SocksCmdType socksCmdType, SocksAddressType socksAddressType, String string, int n) {
        super(SocksRequestType.CMD);
        if (socksCmdType == null) {
            throw new NullPointerException("cmdType");
        }
        if (socksAddressType == null) {
            throw new NullPointerException("addressType");
        }
        if (string == null) {
            throw new NullPointerException("host");
        }
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[socksAddressType.ordinal()]) {
            case 1: {
                if (NetUtil.isValidIpV4Address(string)) break;
                throw new IllegalArgumentException(string + " is not a valid IPv4 address");
            }
            case 2: {
                String string2 = IDN.toASCII(string);
                if (string2.length() > 255) {
                    throw new IllegalArgumentException(string + " IDN: " + string2 + " exceeds 255 char limit");
                }
                string = string2;
                break;
            }
            case 3: {
                if (NetUtil.isValidIpV6Address(string)) break;
                throw new IllegalArgumentException(string + " is not a valid IPv6 address");
            }
        }
        if (n <= 0 || n >= 65536) {
            throw new IllegalArgumentException(n + " is not in bounds 0 < x < 65536");
        }
        this.cmdType = socksCmdType;
        this.addressType = socksAddressType;
        this.host = string;
        this.port = n;
    }

    public SocksCmdType cmdType() {
        return this.cmdType;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        return this.addressType == SocksAddressType.DOMAIN ? IDN.toUnicode(this.host) : this.host;
    }

    public int port() {
        return this.port;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdType.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[this.addressType.ordinal()]) {
            case 1: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                break;
            }
            case 2: {
                byteBuf.writeByte(this.host.length());
                byteBuf.writeCharSequence(this.host, CharsetUtil.US_ASCII);
                byteBuf.writeShort(this.port);
                break;
            }
            case 3: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
            }
        }
    }
}

