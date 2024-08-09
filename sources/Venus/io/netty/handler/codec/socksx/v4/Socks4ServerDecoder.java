/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v4.DefaultSocks4CommandRequest;
import io.netty.handler.codec.socksx.v4.Socks4CommandType;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.util.List;

public class Socks4ServerDecoder
extends ReplayingDecoder<State> {
    private static final int MAX_FIELD_LENGTH = 255;
    private Socks4CommandType type;
    private String dstAddr;
    private int dstPort;
    private String userId;

    public Socks4ServerDecoder() {
        super(State.START);
        this.setSingleDecode(false);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v4$Socks4ServerDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readUnsignedByte();
                    if (n != SocksVersion.SOCKS4a.byteValue()) {
                        throw new DecoderException("unsupported protocol version: " + n);
                    }
                    this.type = Socks4CommandType.valueOf(byteBuf.readByte());
                    this.dstPort = byteBuf.readUnsignedShort();
                    this.dstAddr = NetUtil.intToIpAddress(byteBuf.readInt());
                    this.checkpoint(State.READ_USERID);
                }
                case 2: {
                    this.userId = Socks4ServerDecoder.readString("userid", byteBuf);
                    this.checkpoint(State.READ_DOMAIN);
                }
                case 3: {
                    if (!"0.0.0.0".equals(this.dstAddr) && this.dstAddr.startsWith("0.0.0.")) {
                        this.dstAddr = Socks4ServerDecoder.readString("dstAddr", byteBuf);
                    }
                    list.add(new DefaultSocks4CommandRequest(this.type, this.dstAddr, this.dstPort, this.userId));
                    this.checkpoint(State.SUCCESS);
                }
                case 4: {
                    int n = this.actualReadableBytes();
                    if (n <= 0) break;
                    list.add(byteBuf.readRetainedSlice(n));
                    break;
                }
                case 5: {
                    byteBuf.skipBytes(this.actualReadableBytes());
                }
            }
        } catch (Exception exception) {
            this.fail(list, exception);
        }
    }

    private void fail(List<Object> list, Exception exception) {
        if (!(exception instanceof DecoderException)) {
            exception = new DecoderException(exception);
        }
        DefaultSocks4CommandRequest defaultSocks4CommandRequest = new DefaultSocks4CommandRequest(this.type != null ? this.type : Socks4CommandType.CONNECT, this.dstAddr != null ? this.dstAddr : "", this.dstPort != 0 ? this.dstPort : 65535, this.userId != null ? this.userId : "");
        defaultSocks4CommandRequest.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks4CommandRequest);
        this.checkpoint(State.FAILURE);
    }

    private static String readString(String string, ByteBuf byteBuf) {
        int n = byteBuf.bytesBefore(256, (byte)0);
        if (n < 0) {
            throw new DecoderException("field '" + string + "' longer than " + 255 + " chars");
        }
        String string2 = byteBuf.readSlice(n).toString(CharsetUtil.US_ASCII);
        byteBuf.skipBytes(1);
        return string2;
    }

    static enum State {
        START,
        READ_USERID,
        READ_DOMAIN,
        SUCCESS,
        FAILURE;

    }
}

