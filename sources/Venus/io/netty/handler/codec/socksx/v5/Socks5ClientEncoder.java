/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.socksx.v5.Socks5AddressEncoder;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequest;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5Message;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequest;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.RandomAccess;

@ChannelHandler.Sharable
public class Socks5ClientEncoder
extends MessageToByteEncoder<Socks5Message> {
    public static final Socks5ClientEncoder DEFAULT = new Socks5ClientEncoder();
    private final Socks5AddressEncoder addressEncoder;

    protected Socks5ClientEncoder() {
        this(Socks5AddressEncoder.DEFAULT);
    }

    public Socks5ClientEncoder(Socks5AddressEncoder socks5AddressEncoder) {
        if (socks5AddressEncoder == null) {
            throw new NullPointerException("addressEncoder");
        }
        this.addressEncoder = socks5AddressEncoder;
    }

    protected final Socks5AddressEncoder addressEncoder() {
        return this.addressEncoder;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Socks5Message socks5Message, ByteBuf byteBuf) throws Exception {
        if (socks5Message instanceof Socks5InitialRequest) {
            Socks5ClientEncoder.encodeAuthMethodRequest((Socks5InitialRequest)socks5Message, byteBuf);
        } else if (socks5Message instanceof Socks5PasswordAuthRequest) {
            Socks5ClientEncoder.encodePasswordAuthRequest((Socks5PasswordAuthRequest)socks5Message, byteBuf);
        } else if (socks5Message instanceof Socks5CommandRequest) {
            this.encodeCommandRequest((Socks5CommandRequest)socks5Message, byteBuf);
        } else {
            throw new EncoderException("unsupported message type: " + StringUtil.simpleClassName(socks5Message));
        }
    }

    private static void encodeAuthMethodRequest(Socks5InitialRequest socks5InitialRequest, ByteBuf byteBuf) {
        byteBuf.writeByte(socks5InitialRequest.version().byteValue());
        List<Socks5AuthMethod> list = socks5InitialRequest.authMethods();
        int n = list.size();
        byteBuf.writeByte(n);
        if (list instanceof RandomAccess) {
            for (int i = 0; i < n; ++i) {
                byteBuf.writeByte(list.get(i).byteValue());
            }
        } else {
            for (Socks5AuthMethod socks5AuthMethod : list) {
                byteBuf.writeByte(socks5AuthMethod.byteValue());
            }
        }
    }

    private static void encodePasswordAuthRequest(Socks5PasswordAuthRequest socks5PasswordAuthRequest, ByteBuf byteBuf) {
        byteBuf.writeByte(1);
        String string = socks5PasswordAuthRequest.username();
        byteBuf.writeByte(string.length());
        ByteBufUtil.writeAscii(byteBuf, (CharSequence)string);
        String string2 = socks5PasswordAuthRequest.password();
        byteBuf.writeByte(string2.length());
        ByteBufUtil.writeAscii(byteBuf, (CharSequence)string2);
    }

    private void encodeCommandRequest(Socks5CommandRequest socks5CommandRequest, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(socks5CommandRequest.version().byteValue());
        byteBuf.writeByte(socks5CommandRequest.type().byteValue());
        byteBuf.writeByte(0);
        Socks5AddressType socks5AddressType = socks5CommandRequest.dstAddrType();
        byteBuf.writeByte(socks5AddressType.byteValue());
        this.addressEncoder.encodeAddress(socks5AddressType, socks5CommandRequest.dstAddr(), byteBuf);
        byteBuf.writeShort(socks5CommandRequest.dstPort());
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Socks5Message)object, byteBuf);
    }
}

