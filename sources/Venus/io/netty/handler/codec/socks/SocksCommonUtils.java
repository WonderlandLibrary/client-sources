/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.socks.SocksRequest;
import io.netty.handler.codec.socks.SocksResponse;
import io.netty.handler.codec.socks.UnknownSocksRequest;
import io.netty.handler.codec.socks.UnknownSocksResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

final class SocksCommonUtils {
    public static final SocksRequest UNKNOWN_SOCKS_REQUEST;
    public static final SocksResponse UNKNOWN_SOCKS_RESPONSE;
    private static final char ipv6hextetSeparator = ':';
    static final boolean $assertionsDisabled;

    private SocksCommonUtils() {
    }

    public static String ipv6toStr(byte[] byArray) {
        if (!$assertionsDisabled && byArray.length != 16) {
            throw new AssertionError();
        }
        StringBuilder stringBuilder = new StringBuilder(39);
        SocksCommonUtils.ipv6toStr(stringBuilder, byArray, 0, 8);
        return stringBuilder.toString();
    }

    private static void ipv6toStr(StringBuilder stringBuilder, byte[] byArray, int n, int n2) {
        int n3;
        --n2;
        for (n3 = n; n3 < n2; ++n3) {
            SocksCommonUtils.appendHextet(stringBuilder, byArray, n3);
            stringBuilder.append(':');
        }
        SocksCommonUtils.appendHextet(stringBuilder, byArray, n3);
    }

    private static void appendHextet(StringBuilder stringBuilder, byte[] byArray, int n) {
        StringUtil.toHexString(stringBuilder, byArray, n << 1, 2);
    }

    static String readUsAscii(ByteBuf byteBuf, int n) {
        String string = byteBuf.toString(byteBuf.readerIndex(), n, CharsetUtil.US_ASCII);
        byteBuf.skipBytes(n);
        return string;
    }

    static {
        $assertionsDisabled = !SocksCommonUtils.class.desiredAssertionStatus();
        UNKNOWN_SOCKS_REQUEST = new UnknownSocksRequest();
        UNKNOWN_SOCKS_RESPONSE = new UnknownSocksResponse();
    }
}

