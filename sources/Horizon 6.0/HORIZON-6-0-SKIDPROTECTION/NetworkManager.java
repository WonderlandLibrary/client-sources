package HORIZON-6-0-SKIDPROTECTION;

import java.security.Key;
import javax.crypto.SecretKey;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.Bootstrap;
import java.net.InetAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.lang3.ArrayUtils;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.commons.lang3.Validate;
import io.netty.channel.ChannelHandlerContext;
import com.google.common.collect.Queues;
import io.netty.channel.local.LocalEventLoopGroup;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.LogManager;
import java.net.SocketAddress;
import io.netty.channel.Channel;
import java.util.Queue;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Logger;
import io.netty.channel.SimpleChannelInboundHandler;

public class NetworkManager extends SimpleChannelInboundHandler
{
    private static final Logger Ó;
    public static final Marker HorizonCode_Horizon_È;
    public static final Marker Â;
    public static final AttributeKey Ý;
    public static final LazyLoadBase Ø­áŒŠá;
    public static final LazyLoadBase Âµá€;
    private final EnumPacketDirection à;
    private final Queue Ø;
    private Channel áŒŠÆ;
    private SocketAddress áˆºÑ¢Õ;
    private INetHandler ÂµÈ;
    private IChatComponent á;
    private boolean ˆÏ­;
    private boolean £á;
    private static final String Å = "CL_00001240";
    
    static {
        Ó = LogManager.getLogger();
        HorizonCode_Horizon_È = MarkerManager.getMarker("NETWORK");
        Â = MarkerManager.getMarker("NETWORK_PACKETS", NetworkManager.HorizonCode_Horizon_È);
        Ý = AttributeKey.valueOf("protocol");
        Ø­áŒŠá = new LazyLoadBase() {
            private static final String HorizonCode_Horizon_È = "CL_00001241";
            
            protected NioEventLoopGroup HorizonCode_Horizon_È() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object Â() {
                return this.HorizonCode_Horizon_È();
            }
        };
        Âµá€ = new LazyLoadBase() {
            private static final String HorizonCode_Horizon_È = "CL_00001242";
            
            protected LocalEventLoopGroup HorizonCode_Horizon_È() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object Â() {
                return this.HorizonCode_Horizon_È();
            }
        };
    }
    
    public NetworkManager(final EnumPacketDirection packetDirection) {
        this.Ø = Queues.newConcurrentLinkedQueue();
        this.à = packetDirection;
    }
    
    public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
        super.channelActive(p_channelActive_1_);
        this.áŒŠÆ = p_channelActive_1_.channel();
        this.áˆºÑ¢Õ = this.áŒŠÆ.remoteAddress();
        try {
            this.HorizonCode_Horizon_È(EnumConnectionState.HorizonCode_Horizon_È);
        }
        catch (Throwable var3) {
            NetworkManager.Ó.fatal((Object)var3);
        }
    }
    
    public void HorizonCode_Horizon_È(final EnumConnectionState newState) {
        this.áŒŠÆ.attr(NetworkManager.Ý).set((Object)newState);
        this.áŒŠÆ.config().setAutoRead(true);
        NetworkManager.Ó.debug("Enabled auto read");
    }
    
    public void channelInactive(final ChannelHandlerContext p_channelInactive_1_) {
        this.HorizonCode_Horizon_È(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }
    
    public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) {
        NetworkManager.Ó.debug("Disconnecting " + this.Â(), p_exceptionCaught_2_);
        this.HorizonCode_Horizon_È(new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ }));
    }
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_channelRead0_1_, final Packet p_channelRead0_2_) {
        if (this.áŒŠÆ.isOpen()) {
            try {
                p_channelRead0_2_.HorizonCode_Horizon_È(this.ÂµÈ);
            }
            catch (ThreadQuickExitException ex) {}
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        Validate.notNull((Object)handler, "packetListener", new Object[0]);
        NetworkManager.Ó.debug("Set listener of {} to {}", new Object[] { this, handler });
        this.ÂµÈ = handler;
    }
    
    public void HorizonCode_Horizon_È(final Packet packetIn) {
        if (this.áŒŠÆ != null && this.áŒŠÆ.isOpen()) {
            this.ÂµÈ();
            this.HorizonCode_Horizon_È(packetIn, null);
        }
        else {
            this.Ø.add(new HorizonCode_Horizon_È(packetIn, (GenericFutureListener[])null));
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet packetIn, final GenericFutureListener listener, final GenericFutureListener... listeners) {
        if (this.áŒŠÆ != null && this.áŒŠÆ.isOpen()) {
            this.ÂµÈ();
            this.HorizonCode_Horizon_È(packetIn, (GenericFutureListener[])ArrayUtils.add((Object[])listeners, 0, (Object)listener));
        }
        else {
            this.Ø.add(new HorizonCode_Horizon_È(packetIn, (GenericFutureListener[])ArrayUtils.add((Object[])listeners, 0, (Object)listener)));
        }
    }
    
    private void HorizonCode_Horizon_È(final Packet inPacket, final GenericFutureListener[] futureListeners) {
        final EnumConnectionState var3 = EnumConnectionState.HorizonCode_Horizon_È(inPacket);
        final EnumConnectionState var4 = (EnumConnectionState)this.áŒŠÆ.attr(NetworkManager.Ý).get();
        if (var4 != var3) {
            NetworkManager.Ó.debug("Disabled auto read");
            this.áŒŠÆ.config().setAutoRead(false);
        }
        if (this.áŒŠÆ.eventLoop().inEventLoop()) {
            if (var3 != var4) {
                this.HorizonCode_Horizon_È(var3);
            }
            final ChannelFuture var5 = this.áŒŠÆ.writeAndFlush((Object)inPacket);
            if (futureListeners != null) {
                var5.addListeners(futureListeners);
            }
            var5.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.áŒŠÆ.eventLoop().execute((Runnable)new Runnable() {
                private static final String Â = "CL_00001243";
                
                @Override
                public void run() {
                    if (var3 != var4) {
                        NetworkManager.this.HorizonCode_Horizon_È(var3);
                    }
                    final ChannelFuture var1 = NetworkManager.this.áŒŠÆ.writeAndFlush((Object)inPacket);
                    if (futureListeners != null) {
                        var1.addListeners(futureListeners);
                    }
                    var1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            });
        }
    }
    
    private void ÂµÈ() {
        if (this.áŒŠÆ != null && this.áŒŠÆ.isOpen()) {
            while (!this.Ø.isEmpty()) {
                final HorizonCode_Horizon_È var1 = this.Ø.poll();
                this.HorizonCode_Horizon_È(var1.HorizonCode_Horizon_È, var1.Â);
            }
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.ÂµÈ();
        if (this.ÂµÈ instanceof IUpdatePlayerListBox) {
            ((IUpdatePlayerListBox)this.ÂµÈ).HorizonCode_Horizon_È();
        }
        this.áŒŠÆ.flush();
    }
    
    public SocketAddress Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent message) {
        if (this.áŒŠÆ.isOpen()) {
            this.áŒŠÆ.close().awaitUninterruptibly();
            this.á = message;
        }
    }
    
    public boolean Ý() {
        return this.áŒŠÆ instanceof LocalChannel || this.áŒŠÆ instanceof LocalServerChannel;
    }
    
    public static NetworkManager HorizonCode_Horizon_È(final InetAddress p_150726_0_, final int p_150726_1_) {
        final NetworkManager var2 = new NetworkManager(EnumPacketDirection.Â);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.Ø­áŒŠá.Ý())).handler((ChannelHandler)new ChannelInitializer() {
            private static final String HorizonCode_Horizon_È = "CL_00002312";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                }
                catch (ChannelException ex) {}
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
                }
                catch (ChannelException ex2) {}
                p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(20)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.Â)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.HorizonCode_Horizon_È)).addLast("packet_handler", (ChannelHandler)var2);
            }
        })).channel((Class)NioSocketChannel.class)).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
        return var2;
    }
    
    public static NetworkManager HorizonCode_Horizon_È(final SocketAddress p_150722_0_) {
        final NetworkManager var1 = new NetworkManager(EnumPacketDirection.Â);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.Âµá€.Ý())).handler((ChannelHandler)new ChannelInitializer() {
            private static final String HorizonCode_Horizon_È = "CL_00002311";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var1);
            }
        })).channel((Class)LocalChannel.class)).connect(p_150722_0_).syncUninterruptibly();
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final SecretKey key) {
        this.ˆÏ­ = true;
        this.áŒŠÆ.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.HorizonCode_Horizon_È(2, key)));
        this.áŒŠÆ.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.HorizonCode_Horizon_È(1, key)));
    }
    
    public boolean Ø­áŒŠá() {
        return this.ˆÏ­;
    }
    
    public boolean Âµá€() {
        return this.áŒŠÆ != null && this.áŒŠÆ.isOpen();
    }
    
    public boolean Ó() {
        return this.áŒŠÆ == null;
    }
    
    public INetHandler à() {
        return this.ÂµÈ;
    }
    
    public IChatComponent Ø() {
        return this.á;
    }
    
    public void áŒŠÆ() {
        this.áŒŠÆ.config().setAutoRead(false);
    }
    
    public void HorizonCode_Horizon_È(final int treshold) {
        if (treshold >= 0) {
            if (this.áŒŠÆ.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.áŒŠÆ.pipeline().get("decompress")).HorizonCode_Horizon_È(treshold);
            }
            else {
                this.áŒŠÆ.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(treshold));
            }
            if (this.áŒŠÆ.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.áŒŠÆ.pipeline().get("decompress")).HorizonCode_Horizon_È(treshold);
            }
            else {
                this.áŒŠÆ.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(treshold));
            }
        }
        else {
            if (this.áŒŠÆ.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.áŒŠÆ.pipeline().remove("decompress");
            }
            if (this.áŒŠÆ.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.áŒŠÆ.pipeline().remove("compress");
            }
        }
    }
    
    public void áˆºÑ¢Õ() {
        if (!this.Ó() && !this.Âµá€() && !this.£á) {
            this.£á = true;
            if (this.Ø() != null) {
                this.à().HorizonCode_Horizon_È(this.Ø());
            }
            else if (this.à() != null) {
                this.à().HorizonCode_Horizon_È(new ChatComponentText("Disconnected"));
            }
        }
    }
    
    protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
        this.HorizonCode_Horizon_È(p_channelRead0_1_, (Packet)p_channelRead0_2_);
    }
    
    static class HorizonCode_Horizon_È
    {
        private final Packet HorizonCode_Horizon_È;
        private final GenericFutureListener[] Â;
        private static final String Ý = "CL_00001244";
        
        public HorizonCode_Horizon_È(final Packet inPacket, final GenericFutureListener... inFutureListeners) {
            this.HorizonCode_Horizon_È = inPacket;
            this.Â = inFutureListeners;
        }
    }
}
