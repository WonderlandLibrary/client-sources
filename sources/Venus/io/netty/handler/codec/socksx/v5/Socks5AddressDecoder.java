/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

public interface Socks5AddressDecoder {
    public static final Socks5AddressDecoder DEFAULT = new Socks5AddressDecoder(){
        private static final int IPv6_LEN = 16;

        @Override
        public String decodeAddress(Socks5AddressType socks5AddressType, ByteBuf byteBuf) throws Exception {
            if (socks5AddressType == Socks5AddressType.IPv4) {
                return NetUtil.intToIpAddress(byteBuf.readInt());
            }
            if (socks5AddressType == Socks5AddressType.DOMAIN) {
                short s = byteBuf.readUnsignedByte();
                String string = byteBuf.toString(byteBuf.readerIndex(), s, CharsetUtil.US_ASCII);
                byteBuf.skipBytes(s);
                return string;
            }
            if (socks5AddressType == Socks5AddressType.IPv6) {
                if (byteBuf.hasArray()) {
                    int n = byteBuf.readerIndex();
                    byteBuf.readerIndex(n + 16);
                    return NetUtil.bytesToIpAddress(byteBuf.array(), byteBuf.arrayOffset() + n, 16);
                }
                byte[] byArray = new byte[16];
                byteBuf.readBytes(byArray);
                return NetUtil.bytesToIpAddress(byArray);
            }
            throw new DecoderException("unsupported address type: " + (socks5AddressType.byteValue() & 0xFF));
        }
    };

    public String decodeAddress(Socks5AddressType var1, ByteBuf var2) throws Exception;
}

