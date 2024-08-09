/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socks.SocksAddressType;
import io.netty.handler.codec.socks.SocksCmdResponse;
import io.netty.handler.codec.socks.SocksCmdStatus;
import io.netty.handler.codec.socks.SocksCommonUtils;
import io.netty.handler.codec.socks.SocksProtocolVersion;
import io.netty.util.NetUtil;
import java.util.List;

public class SocksCmdResponseDecoder
extends ReplayingDecoder<State> {
    private SocksCmdStatus cmdStatus;
    private SocksAddressType addressType;

    public SocksCmdResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        block0 : switch ((State)((Object)this.state())) {
            case CHECK_PROTOCOL_VERSION: {
                if (byteBuf.readByte() != SocksProtocolVersion.SOCKS5.byteValue()) {
                    list.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                    break;
                }
                this.checkpoint(State.READ_CMD_HEADER);
            }
            case READ_CMD_HEADER: {
                this.cmdStatus = SocksCmdStatus.valueOf(byteBuf.readByte());
                byteBuf.skipBytes(1);
                this.addressType = SocksAddressType.valueOf(byteBuf.readByte());
                this.checkpoint(State.READ_CMD_ADDRESS);
            }
            case READ_CMD_ADDRESS: {
                switch (this.addressType) {
                    case IPv4: {
                        String string = NetUtil.intToIpAddress(byteBuf.readInt());
                        int n = byteBuf.readUnsignedShort();
                        list.add(new SocksCmdResponse(this.cmdStatus, this.addressType, string, n));
                        break block0;
                    }
                    case DOMAIN: {
                        byte by = byteBuf.readByte();
                        String string = SocksCommonUtils.readUsAscii(byteBuf, by);
                        int n = byteBuf.readUnsignedShort();
                        list.add(new SocksCmdResponse(this.cmdStatus, this.addressType, string, n));
                        break block0;
                    }
                    case IPv6: {
                        byte[] byArray = new byte[16];
                        byteBuf.readBytes(byArray);
                        String string = SocksCommonUtils.ipv6toStr(byArray);
                        int n = byteBuf.readUnsignedShort();
                        list.add(new SocksCmdResponse(this.cmdStatus, this.addressType, string, n));
                        break block0;
                    }
                    case UNKNOWN: {
                        list.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                        break block0;
                    }
                }
                throw new Error();
            }
            default: {
                throw new Error();
            }
        }
        channelHandlerContext.pipeline().remove(this);
    }

    static enum State {
        CHECK_PROTOCOL_VERSION,
        READ_CMD_HEADER,
        READ_CMD_ADDRESS;

    }
}

