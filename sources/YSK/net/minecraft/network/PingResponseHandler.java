package net.minecraft.network;

import java.net.*;
import com.google.common.base.*;
import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

public class PingResponseHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger logger;
    private NetworkSystem networkSystem;
    private static final String[] I;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        final ByteBuf byteBuf = (ByteBuf)o;
        byteBuf.markReaderIndex();
        int n = " ".length();
        try {
            if (byteBuf.readUnsignedByte() == 201 + 146 - 197 + 104) {
                final InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
                final MinecraftServer server = this.networkSystem.getServer();
                switch (byteBuf.readableBytes()) {
                    case 0: {
                        final Logger logger = PingResponseHandler.logger;
                        final String s = PingResponseHandler.I["".length()];
                        final Object[] array = new Object["  ".length()];
                        array["".length()] = inetSocketAddress.getAddress();
                        array[" ".length()] = inetSocketAddress.getPort();
                        logger.debug(s, array);
                        final String s2 = PingResponseHandler.I[" ".length()];
                        final Object[] array2 = new Object["   ".length()];
                        array2["".length()] = server.getMOTD();
                        array2[" ".length()] = server.getCurrentPlayerCount();
                        array2["  ".length()] = server.getMaxPlayers();
                        this.writeAndFlush(channelHandlerContext, this.getStringBuffer(String.format(s2, array2)));
                        "".length();
                        if (2 < 0) {
                            throw null;
                        }
                        break;
                    }
                    case 1: {
                        if (byteBuf.readUnsignedByte() != " ".length()) {
                            if (n != 0) {
                                byteBuf.resetReaderIndex();
                                channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I["  ".length()]);
                                channelHandlerContext.fireChannelRead(o);
                            }
                            return;
                        }
                        final Logger logger2 = PingResponseHandler.logger;
                        final String s3 = PingResponseHandler.I["   ".length()];
                        final Object[] array3 = new Object["  ".length()];
                        array3["".length()] = inetSocketAddress.getAddress();
                        array3[" ".length()] = inetSocketAddress.getPort();
                        logger2.debug(s3, array3);
                        final String s4 = PingResponseHandler.I[0xC ^ 0x8];
                        final Object[] array4 = new Object[0x2A ^ 0x2F];
                        array4["".length()] = 63 + 97 - 112 + 79;
                        array4[" ".length()] = server.getMinecraftVersion();
                        array4["  ".length()] = server.getMOTD();
                        array4["   ".length()] = server.getCurrentPlayerCount();
                        array4[0xAC ^ 0xA8] = server.getMaxPlayers();
                        this.writeAndFlush(channelHandlerContext, this.getStringBuffer(String.format(s4, array4)));
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                        break;
                    }
                    default: {
                        int n2;
                        if (byteBuf.readUnsignedByte() == " ".length()) {
                            n2 = " ".length();
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                        }
                        else {
                            n2 = "".length();
                        }
                        final int n3 = n2;
                        int n4;
                        if (byteBuf.readUnsignedByte() == 102 + 127 + 1 + 20) {
                            n4 = " ".length();
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n4 = "".length();
                        }
                        final int n5 = n3 & n4 & (PingResponseHandler.I[0x16 ^ 0x13].equals(new String(byteBuf.readBytes(byteBuf.readShort() * "  ".length()).array(), Charsets.UTF_16BE)) ? 1 : 0);
                        final int unsignedShort = byteBuf.readUnsignedShort();
                        final int n6 = n5;
                        int n7;
                        if (byteBuf.readUnsignedByte() >= (0xFF ^ 0xB6)) {
                            n7 = " ".length();
                            "".length();
                            if (-1 >= 0) {
                                throw null;
                            }
                        }
                        else {
                            n7 = "".length();
                        }
                        final int n8 = n6 & n7;
                        int n9;
                        if ("   ".length() + byteBuf.readBytes(byteBuf.readShort() * "  ".length()).array().length + (0x74 ^ 0x70) == unsignedShort) {
                            n9 = " ".length();
                            "".length();
                            if (1 < 0) {
                                throw null;
                            }
                        }
                        else {
                            n9 = "".length();
                        }
                        final int n10 = n8 & n9;
                        int n11;
                        if (byteBuf.readInt() <= 62250 + 52655 - 63735 + 14365) {
                            n11 = " ".length();
                            "".length();
                            if (4 < 4) {
                                throw null;
                            }
                        }
                        else {
                            n11 = "".length();
                        }
                        final int n12 = n10 & n11;
                        int n13;
                        if (byteBuf.readableBytes() == 0) {
                            n13 = " ".length();
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                        }
                        else {
                            n13 = "".length();
                        }
                        if ((n12 & n13) == 0x0) {
                            if (n != 0) {
                                byteBuf.resetReaderIndex();
                                channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I[0x2C ^ 0x2A]);
                                channelHandlerContext.fireChannelRead(o);
                            }
                            return;
                        }
                        final Logger logger3 = PingResponseHandler.logger;
                        final String s5 = PingResponseHandler.I[0xF ^ 0x8];
                        final Object[] array5 = new Object["  ".length()];
                        array5["".length()] = inetSocketAddress.getAddress();
                        array5[" ".length()] = inetSocketAddress.getPort();
                        logger3.debug(s5, array5);
                        final String s6 = PingResponseHandler.I[0x16 ^ 0x1E];
                        final Object[] array6 = new Object[0x30 ^ 0x35];
                        array6["".length()] = 125 + 47 - 150 + 105;
                        array6[" ".length()] = server.getMinecraftVersion();
                        array6["  ".length()] = server.getMOTD();
                        array6["   ".length()] = server.getCurrentPlayerCount();
                        array6[0x3C ^ 0x38] = server.getMaxPlayers();
                        final ByteBuf stringBuffer = this.getStringBuffer(String.format(s6, array6));
                        try {
                            this.writeAndFlush(channelHandlerContext, stringBuffer);
                            "".length();
                            if (2 == 1) {
                                throw null;
                            }
                        }
                        finally {
                            stringBuffer.release();
                        }
                        stringBuffer.release();
                        break;
                    }
                }
                byteBuf.release();
                n = "".length();
                if (n != 0) {
                    byteBuf.resetReaderIndex();
                    channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I[0x1F ^ 0x16]);
                    channelHandlerContext.fireChannelRead(o);
                }
                return;
            }
        }
        catch (RuntimeException ex) {
            if (n != 0) {
                byteBuf.resetReaderIndex();
                channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I[0xAF ^ 0xA5]);
                channelHandlerContext.fireChannelRead(o);
            }
            return;
        }
        finally {
            if (n != 0) {
                byteBuf.resetReaderIndex();
                channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I[0x61 ^ 0x6A]);
                channelHandlerContext.fireChannelRead(o);
            }
        }
        if (n != 0) {
            byteBuf.resetReaderIndex();
            channelHandlerContext.channel().pipeline().remove(PingResponseHandler.I[0x16 ^ 0x1A]);
            channelHandlerContext.fireChannelRead(o);
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public PingResponseHandler(final NetworkSystem networkSystem) {
        this.networkSystem = networkSystem;
    }
    
    private ByteBuf getStringBuffer(final String s) {
        final ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(58 + 68 - 36 + 165);
        final char[] charArray = s.toCharArray();
        buffer.writeShort(charArray.length);
        final char[] array;
        final int length = (array = charArray).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length) {
            buffer.writeChar((int)array[i]);
            ++i;
        }
        return buffer;
    }
    
    private void writeAndFlush(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        channelHandlerContext.pipeline().firstContext().writeAndFlush((Object)byteBuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x32])["".length()] = I(">(\u0001?oNiSi{]o\u0017qu\b3\u00005u\u0015<U#(", "nAoXU");
        PingResponseHandler.I[" ".length()] = I("\u007f\u0005\u00e6A\u000b\u00fdS%", "ZvAdo");
        PingResponseHandler.I["  ".length()] = I("\u0001+ -\u0010\u0014\u001169\u0016\u001f7", "mNGLs");
        PingResponseHandler.I["   ".length()] = I("5\b>\u0011}EIaXsHP~Ci\u001dHp\u00105\n\fp\r:_\u001a-", "eaPvG");
        PingResponseHandler.I[0x4 ^ 0x0] = I("\u00f5aU@ Ru&ea!Pp\u0001Dw4", "RPUeD");
        PingResponseHandler.I[0x8B ^ 0x8E] = I("*\u001a*=#\t>\u001e\u00029\u0013", "gYVmJ");
        PingResponseHandler.I[0x8C ^ 0x8A] = I(" \"\n)35\u0018\u001c=5>>", "LGmHP");
        PingResponseHandler.I[0x80 ^ 0x87] = I("\u0003(\u001b(RsiDa^za\u0013=\u0007>a\u000e2R(<", "SAuOh");
        PingResponseHandler.I[0xC ^ 0x4] = I("\u00d7\\tp\u0014pH\u0007UU\u0003mQ1pU\t", "pmtUp");
        PingResponseHandler.I[0x3E ^ 0x37] = I(")=\u0013(\u0007<\u0007\u0005<\u00017!", "EXtId");
        PingResponseHandler.I[0x38 ^ 0x32] = I("#2\u0005356\b\u0013'3=.", "OWbRV");
        PingResponseHandler.I[0x2C ^ 0x27] = I("* \u000e\u0011!?\u001a\u0018\u0005'4<", "FEipB");
        PingResponseHandler.I[0x74 ^ 0x78] = I(">\u000e-\u0007.+4;\u0013( \u0012", "RkJfM");
    }
}
