package HORIZON-6-0-SKIDPROTECTION;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import io.netty.channel.socket.nio.NioSocketChannel;
import com.google.common.collect.Iterables;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.Bootstrap;
import java.net.UnknownHostException;
import com.mojang.authlib.GameProfile;
import org.apache.commons.lang3.ArrayUtils;
import java.net.InetAddress;
import java.util.Collections;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;
import com.google.common.base.Splitter;

public class OldServerPinger
{
    private static final Splitter HorizonCode_Horizon_È;
    private static final Logger Â;
    private final List Ý;
    private static final String Ø­áŒŠá = "CL_00000892";
    
    static {
        HorizonCode_Horizon_È = Splitter.on('\0').limit(6);
        Â = LogManager.getLogger();
    }
    
    public OldServerPinger() {
        this.Ý = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void HorizonCode_Horizon_È(final ServerData server) throws UnknownHostException {
        final ServerAddress var2 = ServerAddress.HorizonCode_Horizon_È(server.Â);
        final NetworkManager var3 = NetworkManager.HorizonCode_Horizon_È(InetAddress.getByName(var2.HorizonCode_Horizon_È()), var2.Â());
        this.Ý.add(var3);
        server.Ø­áŒŠá = "Pinging...";
        server.Âµá€ = -1L;
        server.áŒŠÆ = null;
        var3.HorizonCode_Horizon_È(new INetHandlerStatusClient() {
            private boolean Â = false;
            private long Ý = 0L;
            private static final String Ø­áŒŠá = "CL_00000893";
            
            @Override
            public void HorizonCode_Horizon_È(final S00PacketServerInfo packetIn) {
                final ServerStatusResponse var2 = packetIn.HorizonCode_Horizon_È();
                if (var2.HorizonCode_Horizon_È() != null) {
                    server.Ø­áŒŠá = var2.HorizonCode_Horizon_È().áŒŠÆ();
                }
                else {
                    server.Ø­áŒŠá = "";
                }
                if (var2.Ý() != null) {
                    server.à = var2.Ý().HorizonCode_Horizon_È();
                    server.Ó = var2.Ý().Â();
                }
                else {
                    server.à = "Old";
                    server.Ó = 0;
                }
                if (var2.Â() != null) {
                    server.Ý = new StringBuilder().append(EnumChatFormatting.Ø).append(var2.Â().Â()).append(EnumChatFormatting.áŒŠÆ).append("/").append(EnumChatFormatting.Ø).append(var2.Â().HorizonCode_Horizon_È()).toString();
                    if (ArrayUtils.isNotEmpty((Object[])var2.Â().Ý())) {
                        final StringBuilder var3x = new StringBuilder();
                        for (final GameProfile var6 : var2.Â().Ý()) {
                            if (var3x.length() > 0) {
                                var3x.append("\n");
                            }
                            var3x.append(var6.getName());
                        }
                        if (var2.Â().Ý().length < var2.Â().Â()) {
                            if (var3x.length() > 0) {
                                var3x.append("\n");
                            }
                            var3x.append("... and ").append(var2.Â().Â() - var2.Â().Ý().length).append(" more ...");
                        }
                        server.áŒŠÆ = var3x.toString();
                    }
                }
                else {
                    server.Ý = EnumChatFormatting.áŒŠÆ + "???";
                }
                if (var2.Ø­áŒŠá() != null) {
                    final String var7 = var2.Ø­áŒŠá();
                    if (var7.startsWith("data:image/png;base64,")) {
                        server.HorizonCode_Horizon_È(var7.substring("data:image/png;base64,".length()));
                    }
                    else {
                        OldServerPinger.Â.error("Invalid server icon (unknown format)");
                    }
                }
                else {
                    server.HorizonCode_Horizon_È((String)null);
                }
                this.Ý = Minecraft.áƒ();
                var3.HorizonCode_Horizon_È(new C01PacketPing(this.Ý));
                this.Â = true;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final S01PacketPong packetIn) {
                final long var2 = this.Ý;
                final long var3 = Minecraft.áƒ();
                server.Âµá€ = var3 - var2;
                var3.HorizonCode_Horizon_È(new ChatComponentText("Finished"));
            }
            
            @Override
            public void HorizonCode_Horizon_È(final IChatComponent reason) {
                if (!this.Â) {
                    OldServerPinger.Â.error("Can't ping " + server.Â + ": " + reason.Ø());
                    server.Ø­áŒŠá = EnumChatFormatting.Âµá€ + "Can't connect to server.";
                    server.Ý = "";
                    OldServerPinger.this.Â(server);
                }
            }
        });
        try {
            var3.HorizonCode_Horizon_È(new C00Handshake(47, var2.HorizonCode_Horizon_È(), var2.Â(), EnumConnectionState.Ý));
            var3.HorizonCode_Horizon_È(new C00PacketServerQuery());
        }
        catch (Throwable var4) {
            OldServerPinger.Â.error((Object)var4);
        }
    }
    
    private void Â(final ServerData server) {
        final ServerAddress var2 = ServerAddress.HorizonCode_Horizon_È(server.Â);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.Ø­áŒŠá.Ý())).handler((ChannelHandler)new ChannelInitializer() {
            private static final String Â = "CL_00000894";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                }
                catch (ChannelException ex) {}
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
                }
                catch (ChannelException ex2) {}
                p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { new SimpleChannelInboundHandler() {
                        private static final String Â = "CL_00000895";
                        
                        public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
                            super.channelActive(p_channelActive_1_);
                            final ByteBuf var2x = Unpooled.buffer();
                            try {
                                var2x.writeByte(254);
                                var2x.writeByte(1);
                                var2x.writeByte(250);
                                char[] var3 = "MC|PingHost".toCharArray();
                                var2x.writeShort(var3.length);
                                char[] var4 = var3;
                                for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                                    final char var7 = var4[var6];
                                    var2x.writeChar((int)var7);
                                }
                                var2x.writeShort(7 + 2 * var2.HorizonCode_Horizon_È().length());
                                var2x.writeByte(127);
                                var3 = var2.HorizonCode_Horizon_È().toCharArray();
                                var2x.writeShort(var3.length);
                                var4 = var3;
                                for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                                    final char var7 = var4[var6];
                                    var2x.writeChar((int)var7);
                                }
                                var2x.writeInt(var2.Â());
                                p_channelActive_1_.channel().writeAndFlush((Object)var2x).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                            }
                            finally {
                                var2x.release();
                            }
                            var2x.release();
                        }
                        
                        protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_channelRead0_1_, final ByteBuf p_channelRead0_2_) {
                            final short var3 = p_channelRead0_2_.readUnsignedByte();
                            if (var3 == 255) {
                                final String var4 = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
                                final String[] var5 = (String[])Iterables.toArray(OldServerPinger.HorizonCode_Horizon_È.split((CharSequence)var4), (Class)String.class);
                                if ("§1".equals(var5[0])) {
                                    final int var6 = MathHelper.HorizonCode_Horizon_È(var5[1], 0);
                                    final String var7 = var5[2];
                                    final String var8 = var5[3];
                                    final int var9 = MathHelper.HorizonCode_Horizon_È(var5[4], -1);
                                    final int var10 = MathHelper.HorizonCode_Horizon_È(var5[5], -1);
                                    server.Ó = -1;
                                    server.à = var7;
                                    server.Ø­áŒŠá = var8;
                                    server.Ý = new StringBuilder().append(EnumChatFormatting.Ø).append(var9).append(EnumChatFormatting.áŒŠÆ).append("/").append(EnumChatFormatting.Ø).append(var10).toString();
                                }
                            }
                            p_channelRead0_1_.close();
                        }
                        
                        public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) {
                            p_exceptionCaught_1_.close();
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
                            this.HorizonCode_Horizon_È(p_channelRead0_1_, (ByteBuf)p_channelRead0_2_);
                        }
                    } });
            }
        })).channel((Class)NioSocketChannel.class)).connect(var2.HorizonCode_Horizon_È(), var2.Â());
    }
    
    public void HorizonCode_Horizon_È() {
        final List var1 = this.Ý;
        synchronized (this.Ý) {
            final Iterator var2 = this.Ý.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.Âµá€()) {
                    var3.HorizonCode_Horizon_È();
                }
                else {
                    var2.remove();
                    var3.áˆºÑ¢Õ();
                }
            }
        }
        // monitorexit(this.\u00dd)
    }
    
    public void Â() {
        final List var1 = this.Ý;
        synchronized (this.Ý) {
            final Iterator var2 = this.Ý.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.Âµá€()) {
                    var2.remove();
                    var3.HorizonCode_Horizon_È(new ChatComponentText("Cancelled"));
                }
            }
        }
        // monitorexit(this.\u00dd)
    }
}
