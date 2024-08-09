/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

public interface Socks5AddressEncoder {
    public static final Socks5AddressEncoder DEFAULT = new Socks5AddressEncoder(){

        @Override
        public void encodeAddress(Socks5AddressType socks5AddressType, String string, ByteBuf byteBuf) throws Exception {
            byte by = socks5AddressType.byteValue();
            if (by == Socks5AddressType.IPv4.byteValue()) {
                if (string != null) {
                    byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(string));
                } else {
                    byteBuf.writeInt(0);
                }
            } else if (by == Socks5AddressType.DOMAIN.byteValue()) {
                if (string != null) {
                    byteBuf.writeByte(string.length());
                    byteBuf.writeCharSequence(string, CharsetUtil.US_ASCII);
                } else {
                    byteBuf.writeByte(1);
                    byteBuf.writeByte(0);
                }
            } else if (by == Socks5AddressType.IPv6.byteValue()) {
                if (string != null) {
                    byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(string));
                } else {
                    byteBuf.writeLong(0L);
                    byteBuf.writeLong(0L);
                }
            } else {
                throw new EncoderException("unsupported addrType: " + (socks5AddressType.byteValue() & 0xFF));
            }
        }
    };

    public void encodeAddress(Socks5AddressType var1, String var2, ByteBuf var3) throws Exception;
}

