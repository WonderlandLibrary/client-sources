package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import com.google.common.base.Charsets;
import java.net.InetSocketAddress;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PingResponseHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger HorizonCode_Horizon_È;
    private NetworkSystem Â;
    private static final String Ý = "CL_00001444";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public PingResponseHandler(final NetworkSystem networkSystemIn) {
        this.Â = networkSystemIn;
    }
    
    public void channelRead(final ChannelHandlerContext p_channelRead_1_, final Object p_channelRead_2_) {
        final ByteBuf var3 = (ByteBuf)p_channelRead_2_;
        var3.markReaderIndex();
        boolean var4 = true;
        try {
            if (var3.readUnsignedByte() != 254) {
                return;
            }
            final InetSocketAddress var5 = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
            final MinecraftServer var6 = this.Â.Ø­áŒŠá();
            final int var7 = var3.readableBytes();
            switch (var7) {
                case 0: {
                    PingResponseHandler.HorizonCode_Horizon_È.debug("Ping: (<1.3.x) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                    final String var8 = String.format("%s§%d§%d", var6.áˆºÏ(), var6.Ê(), var6.ÇŽÉ());
                    this.HorizonCode_Horizon_È(p_channelRead_1_, this.HorizonCode_Horizon_È(var8));
                    break;
                }
                case 1: {
                    if (var3.readUnsignedByte() != 1) {
                        return;
                    }
                    PingResponseHandler.HorizonCode_Horizon_È.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                    final String var8 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.Çªà¢(), var6.áˆºÏ(), var6.Ê(), var6.ÇŽÉ());
                    this.HorizonCode_Horizon_È(p_channelRead_1_, this.HorizonCode_Horizon_È(var8));
                    break;
                }
                default: {
                    boolean var9 = var3.readUnsignedByte() == 1;
                    var9 &= (var3.readUnsignedByte() == 250);
                    var9 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
                    final int var10 = var3.readUnsignedShort();
                    var9 &= (var3.readUnsignedByte() >= 73);
                    var9 &= (3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var10);
                    var9 &= (var3.readInt() <= 65535);
                    var9 &= (var3.readableBytes() == 0);
                    if (!var9) {
                        return;
                    }
                    PingResponseHandler.HorizonCode_Horizon_È.debug("Ping: (1.6) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                    final String var11 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.Çªà¢(), var6.áˆºÏ(), var6.Ê(), var6.ÇŽÉ());
                    final ByteBuf var12 = this.HorizonCode_Horizon_È(var11);
                    try {
                        this.HorizonCode_Horizon_È(p_channelRead_1_, var12);
                    }
                    finally {
                        var12.release();
                    }
                    var12.release();
                    break;
                }
            }
            var3.release();
            var4 = false;
        }
        catch (RuntimeException ex) {
            return;
        }
        finally {
            if (var4) {
                var3.resetReaderIndex();
                p_channelRead_1_.channel().pipeline().remove("legacy_query");
                p_channelRead_1_.fireChannelRead(p_channelRead_2_);
            }
        }
        if (var4) {
            var3.resetReaderIndex();
            p_channelRead_1_.channel().pipeline().remove("legacy_query");
            p_channelRead_1_.fireChannelRead(p_channelRead_2_);
        }
    }
    
    private void HorizonCode_Horizon_È(final ChannelHandlerContext ctx, final ByteBuf data) {
        ctx.pipeline().firstContext().writeAndFlush((Object)data).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    private ByteBuf HorizonCode_Horizon_È(final String string) {
        final ByteBuf var2 = Unpooled.buffer();
        var2.writeByte(255);
        final char[] var3 = string.toCharArray();
        var2.writeShort(var3.length);
        final char[] var4 = var3;
        for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
            final char var7 = var4[var6];
            var2.writeChar((int)var7);
        }
        return var2;
    }
}
