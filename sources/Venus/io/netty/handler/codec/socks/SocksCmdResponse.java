/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.socks.SocksAddressType;
import io.netty.handler.codec.socks.SocksCmdStatus;
import io.netty.handler.codec.socks.SocksResponse;
import io.netty.handler.codec.socks.SocksResponseType;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

public final class SocksCmdResponse
extends SocksResponse {
    private final SocksCmdStatus cmdStatus;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;
    private static final byte[] DOMAIN_ZEROED = new byte[]{0};
    private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0};
    private static final byte[] IPv6_HOSTNAME_ZEROED = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public SocksCmdResponse(SocksCmdStatus socksCmdStatus, SocksAddressType socksAddressType) {
        this(socksCmdStatus, socksAddressType, null, 0);
    }

    public SocksCmdResponse(SocksCmdStatus socksCmdStatus, SocksAddressType socksAddressType, String string, int n) {
        super(SocksResponseType.CMD);
        if (socksCmdStatus == null) {
            throw new NullPointerException("cmdStatus");
        }
        if (socksAddressType == null) {
            throw new NullPointerException("addressType");
        }
        if (string != null) {
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
        }
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException(n + " is not in bounds 0 <= x <= 65535");
        }
        this.cmdStatus = socksCmdStatus;
        this.addressType = socksAddressType;
        this.host = string;
        this.port = n;
    }

    public SocksCmdStatus cmdStatus() {
        return this.cmdStatus;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        return this.host != null && this.addressType == SocksAddressType.DOMAIN ? IDN.toUnicode(this.host) : this.host;
    }

    public int port() {
        return this.port;
    }

    @Override
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdStatus.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[this.addressType.ordinal()]) {
            case 1: {
                byte[] byArray = this.host == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
                byteBuf.writeBytes(byArray);
                byteBuf.writeShort(this.port);
                break;
            }
            case 2: {
                if (this.host != null) {
                    byteBuf.writeByte(this.host.length());
                    byteBuf.writeCharSequence(this.host, CharsetUtil.US_ASCII);
                } else {
                    byteBuf.writeByte(DOMAIN_ZEROED.length);
                    byteBuf.writeBytes(DOMAIN_ZEROED);
                }
                byteBuf.writeShort(this.port);
                break;
            }
            case 3: {
                byte[] byArray = this.host == null ? IPv6_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
                byteBuf.writeBytes(byArray);
                byteBuf.writeShort(this.port);
                break;
            }
        }
    }
}

