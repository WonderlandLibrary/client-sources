package HORIZON-6-0-SKIDPROTECTION;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.Callable;
import java.util.Iterator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import java.net.SocketAddress;
import java.io.IOException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import java.net.InetAddress;
import java.util.Collections;
import com.google.common.collect.Lists;
import io.netty.channel.local.LocalEventLoopGroup;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class NetworkSystem
{
    private static final Logger Ø­áŒŠá;
    public static final LazyLoadBase HorizonCode_Horizon_È;
    public static final LazyLoadBase Â;
    private final MinecraftServer Âµá€;
    public volatile boolean Ý;
    private final List Ó;
    private final List à;
    private static final String Ø = "CL_00001447";
    
    static {
        Ø­áŒŠá = LogManager.getLogger();
        HorizonCode_Horizon_È = new LazyLoadBase() {
            private static final String HorizonCode_Horizon_È = "CL_00001448";
            
            protected NioEventLoopGroup HorizonCode_Horizon_È() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object Â() {
                return this.HorizonCode_Horizon_È();
            }
        };
        Â = new LazyLoadBase() {
            private static final String HorizonCode_Horizon_È = "CL_00001449";
            
            protected LocalEventLoopGroup HorizonCode_Horizon_È() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object Â() {
                return this.HorizonCode_Horizon_È();
            }
        };
    }
    
    public NetworkSystem(final MinecraftServer server) {
        this.Ó = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.à = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.Âµá€ = server;
        this.Ý = true;
    }
    
    public void HorizonCode_Horizon_È(final InetAddress address, final int port) throws IOException {
        final List var3 = this.Ó;
        synchronized (this.Ó) {
            this.Ó.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)NioServerSocketChannel.class)).childHandler((ChannelHandler)new ChannelInitializer() {
                private static final String Â = "CL_00001450";
                
                protected void initChannel(final Channel p_initChannel_1_) {
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                    }
                    catch (ChannelException ex) {}
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
                    }
                    catch (ChannelException ex2) {}
                    p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.HorizonCode_Horizon_È)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.Â));
                    final NetworkManager var2 = new NetworkManager(EnumPacketDirection.HorizonCode_Horizon_È);
                    NetworkSystem.this.à.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                    var2.HorizonCode_Horizon_È(new NetHandlerHandshakeTCP(NetworkSystem.this.Âµá€, var2));
                }
            }).group((EventLoopGroup)NetworkSystem.HorizonCode_Horizon_È.Ý()).localAddress(address, port)).bind().syncUninterruptibly());
        }
        // monitorexit(this.\u00d3)
    }
    
    public SocketAddress HorizonCode_Horizon_È() {
        final List var2 = this.Ó;
        final ChannelFuture var3;
        synchronized (this.Ó) {
            var3 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer() {
                private static final String Â = "CL_00001451";
                
                protected void initChannel(final Channel p_initChannel_1_) {
                    final NetworkManager var2 = new NetworkManager(EnumPacketDirection.HorizonCode_Horizon_È);
                    var2.HorizonCode_Horizon_È(new NetHandlerHandshakeMemory(NetworkSystem.this.Âµá€, var2));
                    NetworkSystem.this.à.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                }
            }).group((EventLoopGroup)NetworkSystem.HorizonCode_Horizon_È.Ý()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
            this.Ó.add(var3);
        }
        // monitorexit(this.\u00d3)
        return var3.channel().localAddress();
    }
    
    public void Â() {
        this.Ý = false;
        for (final ChannelFuture var2 : this.Ó) {
            try {
                var2.channel().close().sync();
            }
            catch (InterruptedException var3) {
                NetworkSystem.Ø­áŒŠá.error("Interrupted whilst closing channel");
            }
        }
    }
    
    public void Ý() {
        final List var1 = this.à;
        synchronized (this.à) {
            final Iterator var2 = this.à.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (!var3.Ó()) {
                    if (!var3.Âµá€()) {
                        var2.remove();
                        var3.áˆºÑ¢Õ();
                    }
                    else {
                        try {
                            var3.HorizonCode_Horizon_È();
                        }
                        catch (Exception var5) {
                            if (var3.Ý()) {
                                final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(var5, "Ticking memory connection");
                                final CrashReportCategory var6 = var4.HorizonCode_Horizon_È("Ticking connection");
                                var6.HorizonCode_Horizon_È("Connection", new Callable() {
                                    private static final String Â = "CL_00002272";
                                    
                                    public String HorizonCode_Horizon_È() {
                                        return var3.toString();
                                    }
                                    
                                    @Override
                                    public Object call() {
                                        return this.HorizonCode_Horizon_È();
                                    }
                                });
                                throw new ReportedException(var4);
                            }
                            NetworkSystem.Ø­áŒŠá.warn("Failed to handle packet for " + var3.Â(), (Throwable)var5);
                            final ChatComponentText var7 = new ChatComponentText("Internal server error");
                            var3.HorizonCode_Horizon_È(new S40PacketDisconnect(var7), (GenericFutureListener)new GenericFutureListener() {
                                private static final String Â = "CL_00002271";
                                
                                public void operationComplete(final Future p_operationComplete_1_) {
                                    var3.HorizonCode_Horizon_È(var7);
                                }
                            }, new GenericFutureListener[0]);
                            var3.áŒŠÆ();
                        }
                    }
                }
            }
        }
        // monitorexit(this.\u00e0)
    }
    
    public MinecraftServer Ø­áŒŠá() {
        return this.Âµá€;
    }
}
