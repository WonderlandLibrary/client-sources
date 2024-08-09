/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public final class NativeInetAddress {
    private static final byte[] IPV4_MAPPED_IPV6_PREFIX = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1};
    final byte[] address;
    final int scopeId;

    public static NativeInetAddress newInstance(InetAddress inetAddress) {
        byte[] byArray = inetAddress.getAddress();
        if (inetAddress instanceof Inet6Address) {
            return new NativeInetAddress(byArray, ((Inet6Address)inetAddress).getScopeId());
        }
        return new NativeInetAddress(NativeInetAddress.ipv4MappedIpv6Address(byArray));
    }

    public NativeInetAddress(byte[] byArray, int n) {
        this.address = byArray;
        this.scopeId = n;
    }

    public NativeInetAddress(byte[] byArray) {
        this(byArray, 0);
    }

    public byte[] address() {
        return this.address;
    }

    public int scopeId() {
        return this.scopeId;
    }

    public static byte[] ipv4MappedIpv6Address(byte[] byArray) {
        byte[] byArray2 = new byte[16];
        System.arraycopy(IPV4_MAPPED_IPV6_PREFIX, 0, byArray2, 0, IPV4_MAPPED_IPV6_PREFIX.length);
        System.arraycopy(byArray, 0, byArray2, 12, byArray.length);
        return byArray2;
    }

    public static InetSocketAddress address(byte[] byArray, int n, int n2) {
        int n3 = NativeInetAddress.decodeInt(byArray, n + n2 - 4);
        try {
            InetAddress inetAddress;
            switch (n2) {
                case 8: {
                    byte[] byArray2 = new byte[4];
                    System.arraycopy(byArray, n, byArray2, 0, 4);
                    inetAddress = InetAddress.getByAddress(byArray2);
                    break;
                }
                case 24: {
                    byte[] byArray3 = new byte[16];
                    System.arraycopy(byArray, n, byArray3, 0, 16);
                    int n4 = NativeInetAddress.decodeInt(byArray, n + n2 - 8);
                    inetAddress = Inet6Address.getByAddress(null, byArray3, n4);
                    break;
                }
                default: {
                    throw new Error();
                }
            }
            return new InetSocketAddress(inetAddress, n3);
        } catch (UnknownHostException unknownHostException) {
            throw new Error("Should never happen", unknownHostException);
        }
    }

    static int decodeInt(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }
}

