/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;

final class DnsAddressDecoder {
    private static final int INADDRSZ4 = 4;
    private static final int INADDRSZ6 = 16;

    static InetAddress decodeAddress(DnsRecord dnsRecord, String string, boolean bl) {
        if (!(dnsRecord instanceof DnsRawRecord)) {
            return null;
        }
        ByteBuf byteBuf = ((ByteBufHolder)((Object)dnsRecord)).content();
        int n = byteBuf.readableBytes();
        if (n != 4 && n != 16) {
            return null;
        }
        byte[] byArray = new byte[n];
        byteBuf.getBytes(byteBuf.readerIndex(), byArray);
        try {
            return InetAddress.getByAddress(bl ? IDN.toUnicode(string) : string, byArray);
        } catch (UnknownHostException unknownHostException) {
            throw new Error(unknownHostException);
        }
    }

    private DnsAddressDecoder() {
    }
}

