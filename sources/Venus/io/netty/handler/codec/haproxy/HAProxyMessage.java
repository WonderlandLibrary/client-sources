/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.haproxy.HAProxyCommand;
import io.netty.handler.codec.haproxy.HAProxyProtocolException;
import io.netty.handler.codec.haproxy.HAProxyProtocolVersion;
import io.netty.handler.codec.haproxy.HAProxyProxiedProtocol;
import io.netty.handler.codec.haproxy.HAProxySSLTLV;
import io.netty.handler.codec.haproxy.HAProxyTLV;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HAProxyMessage {
    private static final HAProxyMessage V1_UNKNOWN_MSG = new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private static final HAProxyMessage V2_UNKNOWN_MSG = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private static final HAProxyMessage V2_LOCAL_MSG = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.LOCAL, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private final HAProxyProtocolVersion protocolVersion;
    private final HAProxyCommand command;
    private final HAProxyProxiedProtocol proxiedProtocol;
    private final String sourceAddress;
    private final String destinationAddress;
    private final int sourcePort;
    private final int destinationPort;
    private final List<HAProxyTLV> tlvs;

    private HAProxyMessage(HAProxyProtocolVersion hAProxyProtocolVersion, HAProxyCommand hAProxyCommand, HAProxyProxiedProtocol hAProxyProxiedProtocol, String string, String string2, String string3, String string4) {
        this(hAProxyProtocolVersion, hAProxyCommand, hAProxyProxiedProtocol, string, string2, HAProxyMessage.portStringToInt(string3), HAProxyMessage.portStringToInt(string4));
    }

    private HAProxyMessage(HAProxyProtocolVersion hAProxyProtocolVersion, HAProxyCommand hAProxyCommand, HAProxyProxiedProtocol hAProxyProxiedProtocol, String string, String string2, int n, int n2) {
        this(hAProxyProtocolVersion, hAProxyCommand, hAProxyProxiedProtocol, string, string2, n, n2, Collections.emptyList());
    }

    private HAProxyMessage(HAProxyProtocolVersion hAProxyProtocolVersion, HAProxyCommand hAProxyCommand, HAProxyProxiedProtocol hAProxyProxiedProtocol, String string, String string2, int n, int n2, List<HAProxyTLV> list) {
        if (hAProxyProxiedProtocol == null) {
            throw new NullPointerException("proxiedProtocol");
        }
        HAProxyProxiedProtocol.AddressFamily addressFamily = hAProxyProxiedProtocol.addressFamily();
        HAProxyMessage.checkAddress(string, addressFamily);
        HAProxyMessage.checkAddress(string2, addressFamily);
        HAProxyMessage.checkPort(n);
        HAProxyMessage.checkPort(n2);
        this.protocolVersion = hAProxyProtocolVersion;
        this.command = hAProxyCommand;
        this.proxiedProtocol = hAProxyProxiedProtocol;
        this.sourceAddress = string;
        this.destinationAddress = string2;
        this.sourcePort = n;
        this.destinationPort = n2;
        this.tlvs = Collections.unmodifiableList(list);
    }

    static HAProxyMessage decodeHeader(ByteBuf byteBuf) {
        String string;
        String string2;
        HAProxyProxiedProtocol hAProxyProxiedProtocol;
        HAProxyCommand hAProxyCommand;
        HAProxyProtocolVersion hAProxyProtocolVersion;
        if (byteBuf == null) {
            throw new NullPointerException("header");
        }
        if (byteBuf.readableBytes() < 16) {
            throw new HAProxyProtocolException("incomplete header: " + byteBuf.readableBytes() + " bytes (expected: 16+ bytes)");
        }
        byteBuf.skipBytes(12);
        byte by = byteBuf.readByte();
        try {
            hAProxyProtocolVersion = HAProxyProtocolVersion.valueOf(by);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new HAProxyProtocolException(illegalArgumentException);
        }
        if (hAProxyProtocolVersion != HAProxyProtocolVersion.V2) {
            throw new HAProxyProtocolException("version 1 unsupported: 0x" + Integer.toHexString(by));
        }
        try {
            hAProxyCommand = HAProxyCommand.valueOf(by);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new HAProxyProtocolException(illegalArgumentException);
        }
        if (hAProxyCommand == HAProxyCommand.LOCAL) {
            return V2_LOCAL_MSG;
        }
        try {
            hAProxyProxiedProtocol = HAProxyProxiedProtocol.valueOf(byteBuf.readByte());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new HAProxyProtocolException(illegalArgumentException);
        }
        if (hAProxyProxiedProtocol == HAProxyProxiedProtocol.UNKNOWN) {
            return V2_UNKNOWN_MSG;
        }
        int n = byteBuf.readUnsignedShort();
        int n2 = 0;
        int n3 = 0;
        HAProxyProxiedProtocol.AddressFamily addressFamily = hAProxyProxiedProtocol.addressFamily();
        if (addressFamily == HAProxyProxiedProtocol.AddressFamily.AF_UNIX) {
            if (n < 216 || byteBuf.readableBytes() < 216) {
                throw new HAProxyProtocolException("incomplete UNIX socket address information: " + Math.min(n, byteBuf.readableBytes()) + " bytes (expected: 216+ bytes)");
            }
            int n4 = byteBuf.readerIndex();
            int n5 = byteBuf.forEachByte(n4, 108, ByteProcessor.FIND_NUL);
            int n6 = n5 == -1 ? 108 : n5 - n4;
            string2 = byteBuf.toString(n4, n6, CharsetUtil.US_ASCII);
            n5 = byteBuf.forEachByte(n4 += 108, 108, ByteProcessor.FIND_NUL);
            n6 = n5 == -1 ? 108 : n5 - n4;
            string = byteBuf.toString(n4, n6, CharsetUtil.US_ASCII);
            byteBuf.readerIndex(n4 + 108);
        } else {
            int n7;
            if (addressFamily == HAProxyProxiedProtocol.AddressFamily.AF_IPv4) {
                if (n < 12 || byteBuf.readableBytes() < 12) {
                    throw new HAProxyProtocolException("incomplete IPv4 address information: " + Math.min(n, byteBuf.readableBytes()) + " bytes (expected: 12+ bytes)");
                }
                n7 = 4;
            } else if (addressFamily == HAProxyProxiedProtocol.AddressFamily.AF_IPv6) {
                if (n < 36 || byteBuf.readableBytes() < 36) {
                    throw new HAProxyProtocolException("incomplete IPv6 address information: " + Math.min(n, byteBuf.readableBytes()) + " bytes (expected: 36+ bytes)");
                }
                n7 = 16;
            } else {
                throw new HAProxyProtocolException("unable to parse address information (unknown address family: " + (Object)((Object)addressFamily) + ')');
            }
            string2 = HAProxyMessage.ipBytesToString(byteBuf, n7);
            string = HAProxyMessage.ipBytesToString(byteBuf, n7);
            n2 = byteBuf.readUnsignedShort();
            n3 = byteBuf.readUnsignedShort();
        }
        List<HAProxyTLV> list = HAProxyMessage.readTlvs(byteBuf);
        return new HAProxyMessage(hAProxyProtocolVersion, hAProxyCommand, hAProxyProxiedProtocol, string2, string, n2, n3, list);
    }

    private static List<HAProxyTLV> readTlvs(ByteBuf byteBuf) {
        HAProxyTLV hAProxyTLV = HAProxyMessage.readNextTLV(byteBuf);
        if (hAProxyTLV == null) {
            return Collections.emptyList();
        }
        ArrayList<HAProxyTLV> arrayList = new ArrayList<HAProxyTLV>(4);
        do {
            arrayList.add(hAProxyTLV);
            if (!(hAProxyTLV instanceof HAProxySSLTLV)) continue;
            arrayList.addAll(((HAProxySSLTLV)hAProxyTLV).encapsulatedTLVs());
        } while ((hAProxyTLV = HAProxyMessage.readNextTLV(byteBuf)) != null);
        return arrayList;
    }

    private static HAProxyTLV readNextTLV(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 4) {
            return null;
        }
        byte by = byteBuf.readByte();
        HAProxyTLV.Type type = HAProxyTLV.Type.typeForByteValue(by);
        int n = byteBuf.readUnsignedShort();
        switch (type) {
            case PP2_TYPE_SSL: {
                ByteBuf byteBuf2 = byteBuf.retainedSlice(byteBuf.readerIndex(), n);
                ByteBuf byteBuf3 = byteBuf.readSlice(n);
                byte by2 = byteBuf3.readByte();
                int n2 = byteBuf3.readInt();
                if (byteBuf3.readableBytes() >= 4) {
                    HAProxyTLV hAProxyTLV;
                    ArrayList<HAProxyTLV> arrayList = new ArrayList<HAProxyTLV>(4);
                    while ((hAProxyTLV = HAProxyMessage.readNextTLV(byteBuf3)) != null) {
                        arrayList.add(hAProxyTLV);
                        if (byteBuf3.readableBytes() >= 4) continue;
                    }
                    return new HAProxySSLTLV(n2, by2, arrayList, byteBuf2);
                }
                return new HAProxySSLTLV(n2, by2, Collections.<HAProxyTLV>emptyList(), byteBuf2);
            }
            case PP2_TYPE_ALPN: 
            case PP2_TYPE_AUTHORITY: 
            case PP2_TYPE_SSL_VERSION: 
            case PP2_TYPE_SSL_CN: 
            case PP2_TYPE_NETNS: 
            case OTHER: {
                return new HAProxyTLV(type, by, byteBuf.readRetainedSlice(n));
            }
        }
        return null;
    }

    static HAProxyMessage decodeHeader(String string) {
        HAProxyProxiedProtocol hAProxyProxiedProtocol;
        if (string == null) {
            throw new HAProxyProtocolException("header");
        }
        String[] stringArray = string.split(" ");
        int n = stringArray.length;
        if (n < 2) {
            throw new HAProxyProtocolException("invalid header: " + string + " (expected: 'PROXY' and proxied protocol values)");
        }
        if (!"PROXY".equals(stringArray[0])) {
            throw new HAProxyProtocolException("unknown identifier: " + stringArray[0]);
        }
        try {
            hAProxyProxiedProtocol = HAProxyProxiedProtocol.valueOf(stringArray[5]);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new HAProxyProtocolException(illegalArgumentException);
        }
        if (hAProxyProxiedProtocol != HAProxyProxiedProtocol.TCP4 && hAProxyProxiedProtocol != HAProxyProxiedProtocol.TCP6 && hAProxyProxiedProtocol != HAProxyProxiedProtocol.UNKNOWN) {
            throw new HAProxyProtocolException("unsupported v1 proxied protocol: " + stringArray[5]);
        }
        if (hAProxyProxiedProtocol == HAProxyProxiedProtocol.UNKNOWN) {
            return V1_UNKNOWN_MSG;
        }
        if (n != 6) {
            throw new HAProxyProtocolException("invalid TCP4/6 header: " + string + " (expected: 6 parts)");
        }
        return new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, hAProxyProxiedProtocol, stringArray[5], stringArray[5], stringArray[5], stringArray[5]);
    }

    private static String ipBytesToString(ByteBuf byteBuf, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 4) {
            stringBuilder.append(byteBuf.readByte() & 0xFF);
            stringBuilder.append('.');
            stringBuilder.append(byteBuf.readByte() & 0xFF);
            stringBuilder.append('.');
            stringBuilder.append(byteBuf.readByte() & 0xFF);
            stringBuilder.append('.');
            stringBuilder.append(byteBuf.readByte() & 0xFF);
        } else {
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
            stringBuilder.append(':');
            stringBuilder.append(Integer.toHexString(byteBuf.readUnsignedShort()));
        }
        return stringBuilder.toString();
    }

    private static int portStringToInt(String string) {
        int n;
        try {
            n = Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            throw new HAProxyProtocolException("invalid port: " + string, numberFormatException);
        }
        if (n <= 0 || n > 65535) {
            throw new HAProxyProtocolException("invalid port: " + string + " (expected: 1 ~ 65535)");
        }
        return n;
    }

    private static void checkAddress(String string, HAProxyProxiedProtocol.AddressFamily addressFamily) {
        if (addressFamily == null) {
            throw new NullPointerException("addrFamily");
        }
        switch (addressFamily) {
            case AF_UNSPEC: {
                if (string != null) {
                    throw new HAProxyProtocolException("unable to validate an AF_UNSPEC address: " + string);
                }
                return;
            }
            case AF_UNIX: {
                return;
            }
        }
        if (string == null) {
            throw new NullPointerException("address");
        }
        switch (addressFamily) {
            case AF_IPv4: {
                if (NetUtil.isValidIpV4Address(string)) break;
                throw new HAProxyProtocolException("invalid IPv4 address: " + string);
            }
            case AF_IPv6: {
                if (NetUtil.isValidIpV6Address(string)) break;
                throw new HAProxyProtocolException("invalid IPv6 address: " + string);
            }
            default: {
                throw new Error();
            }
        }
    }

    private static void checkPort(int n) {
        if (n < 0 || n > 65535) {
            throw new HAProxyProtocolException("invalid port: " + n + " (expected: 1 ~ 65535)");
        }
    }

    public HAProxyProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    public HAProxyCommand command() {
        return this.command;
    }

    public HAProxyProxiedProtocol proxiedProtocol() {
        return this.proxiedProtocol;
    }

    public String sourceAddress() {
        return this.sourceAddress;
    }

    public String destinationAddress() {
        return this.destinationAddress;
    }

    public int sourcePort() {
        return this.sourcePort;
    }

    public int destinationPort() {
        return this.destinationPort;
    }

    public List<HAProxyTLV> tlvs() {
        return this.tlvs;
    }
}

