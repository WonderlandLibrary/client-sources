package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.crypto.SecretKey;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import yung.purity.api.EventBus;
import yung.purity.api.events.EventPacket;

public class NetworkManager
  extends SimpleChannelInboundHandler
{
  private static final Logger logger = ;
  public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
  public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
  public static final AttributeKey attrKeyConnectionState = AttributeKey.valueOf("protocol");
  public static final LazyLoadBase CLIENT_NIO_EVENTLOOP = new LazyLoadBase()
  {
    private static final String __OBFID = "CL_00001241";
    
    protected NioEventLoopGroup genericLoad() {
      return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
    }
    
    protected Object load() {
      return genericLoad();
    }
  };
  public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase()
  {
    private static final String __OBFID = "CL_00001242";
    
    protected LocalEventLoopGroup genericLoad() {
      return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
    }
    
    protected Object load() {
      return genericLoad();
    }
  };
  
  private final EnumPacketDirection direction;
  
  private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
  
  private Channel channel;
  
  private SocketAddress socketAddress;
  
  private INetHandler packetListener;
  
  private IChatComponent terminationReason;
  
  private boolean isEncrypted;
  
  private boolean disconnected;
  
  private static final String __OBFID = "CL_00001240";
  

  public NetworkManager(EnumPacketDirection packetDirection)
  {
    direction = packetDirection;
  }
  
  public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception
  {
    super.channelActive(p_channelActive_1_);
    channel = p_channelActive_1_.channel();
    socketAddress = channel.remoteAddress();
    
    try
    {
      setConnectionState(EnumConnectionState.HANDSHAKING);
    }
    catch (Throwable var3)
    {
      logger.fatal(var3);
    }
  }
  



  public void setConnectionState(EnumConnectionState newState)
  {
    channel.attr(attrKeyConnectionState).set(newState);
    channel.config().setAutoRead(true);
    logger.debug("Enabled auto read");
  }
  
  public void channelInactive(ChannelHandlerContext p_channelInactive_1_)
  {
    closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
  }
  
  public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_)
  {
    logger.debug("Disconnecting " + getRemoteAddress(), p_exceptionCaught_2_);
    closeChannel(new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ }));
  }
  

  protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_)
  {
    if (channel.isOpen()) {
      try {
        EventPacket event = (EventPacket)EventBus.getDefault().call(new EventPacket(p_channelRead0_2_, (byte)0));
        if (event.isCancelled()) {
          return;
        }
        p_channelRead0_2_ = event.getPacket();
        p_channelRead0_2_.processPacket(packetListener);
        EventBus.getDefault().call(new EventPacket(p_channelRead0_2_, (byte)1));
      }
      catch (ThreadQuickExitException localThreadQuickExitException) {}
    }
  }
  




  public void setNetHandler(INetHandler handler)
  {
    Validate.notNull(handler, "packetListener", new Object[0]);
    logger.debug("Set listener of {} to {}", new Object[] { this, handler });
    packetListener = handler;
  }
  
  public void sendPacket(Packet packetIn)
  {
    if ((channel != null) && (channel.isOpen()))
    {
      flushOutboundQueue();
      dispatchPacket(packetIn, null);
    }
    else
    {
      outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, null));
    }
  }
  
  public void sendPacket(Packet packetIn, GenericFutureListener listener, GenericFutureListener... listeners)
  {
    if ((channel != null) && (channel.isOpen()))
    {
      flushOutboundQueue();
      dispatchPacket(packetIn, (GenericFutureListener[])ArrayUtils.add(listeners, 0, listener));
    }
    else
    {
      outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener[])ArrayUtils.add(listeners, 0, listener)));
    }
  }
  





  private void dispatchPacket(final Packet inPacket, final GenericFutureListener[] futureListeners)
  {
    EventPacket e = (EventPacket)EventBus.getDefault().call(new EventPacket(inPacket, (byte)2));
    if (e.isCancelled()) {
      return;
    }
    
    final EnumConnectionState var3 = EnumConnectionState.getFromPacket(inPacket);
    final EnumConnectionState var4 = (EnumConnectionState)channel.attr(attrKeyConnectionState).get();
    
    if (var4 != var3)
    {
      logger.debug("Disabled auto read");
      channel.config().setAutoRead(false);
    }
    
    if (channel.eventLoop().inEventLoop())
    {
      if (var3 != var4)
      {
        setConnectionState(var3);
      }
      
      ChannelFuture var5 = channel.writeAndFlush(inPacket);
      
      if (futureListeners != null)
      {
        var5.addListeners(futureListeners);
      }
      
      var5.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
    else
    {
      channel.eventLoop().execute(new Runnable()
      {
        private static final String __OBFID = "CL_00001243";
        
        public void run() {
          if (var3 != var4)
          {
            setConnectionState(var3);
          }
          
          ChannelFuture var1 = channel.writeAndFlush(inPacket);
          
          if (futureListeners != null)
          {
            var1.addListeners(futureListeners);
          }
          
          var1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
      });
    }
  }
  



  private void flushOutboundQueue()
  {
    if ((channel != null) && (channel.isOpen()))
    {
      while (!outboundPacketsQueue.isEmpty())
      {
        InboundHandlerTuplePacketListener var1 = (InboundHandlerTuplePacketListener)outboundPacketsQueue.poll();
        dispatchPacket(packet, futureListeners);
      }
    }
  }
  



  public void processReceivedPackets()
  {
    flushOutboundQueue();
    
    if ((packetListener instanceof IUpdatePlayerListBox))
    {
      ((IUpdatePlayerListBox)packetListener).update();
    }
    
    channel.flush();
  }
  



  public SocketAddress getRemoteAddress()
  {
    return socketAddress;
  }
  



  public void closeChannel(IChatComponent message)
  {
    if (channel.isOpen())
    {
      channel.close().awaitUninterruptibly();
      terminationReason = message;
    }
  }
  




  public boolean isLocalChannel()
  {
    return ((channel instanceof LocalChannel)) || ((channel instanceof LocalServerChannel));
  }
  




  public static NetworkManager provideLanClient(InetAddress p_150726_0_, int p_150726_1_)
  {
    NetworkManager var2 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
    
























    ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer()
    {
      private static final String __OBFID = "CL_00002312";
      
      protected void initChannel(Channel p_initChannel_1_)
      {
        try {
          p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
        }
        catch (ChannelException localChannelException) {}
        



        try
        {
          p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
        }
        catch (ChannelException localChannelException1) {}
        



        p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", NetworkManager.this);
      }
    })).channel(NioSocketChannel.class)).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
    return var2;
  }
  




  public static NetworkManager provideLocalClient(SocketAddress p_150722_0_)
  {
    NetworkManager var1 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
    






    ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new ChannelInitializer()
    {
      private static final String __OBFID = "CL_00002311";
      
      protected void initChannel(Channel p_initChannel_1_) {
        p_initChannel_1_.pipeline().addLast("packet_handler", NetworkManager.this);
      }
    })).channel(LocalChannel.class)).connect(p_150722_0_).syncUninterruptibly();
    return var1;
  }
  



  public void enableEncryption(SecretKey key)
  {
    isEncrypted = true;
    channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.func_151229_a(2, key)));
    channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.func_151229_a(1, key)));
  }
  
  public boolean func_179292_f()
  {
    return isEncrypted;
  }
  



  public boolean isChannelOpen()
  {
    return (channel != null) && (channel.isOpen());
  }
  
  public boolean hasNoChannel()
  {
    return channel == null;
  }
  



  public INetHandler getNetHandler()
  {
    return packetListener;
  }
  



  public IChatComponent getExitMessage()
  {
    return terminationReason;
  }
  



  public void disableAutoRead()
  {
    channel.config().setAutoRead(false);
  }
  
  public void setCompressionTreshold(int treshold)
  {
    if (treshold >= 0)
    {
      if ((channel.pipeline().get("decompress") instanceof NettyCompressionDecoder))
      {
        ((NettyCompressionDecoder)channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
      }
      else
      {
        channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(treshold));
      }
      
      if ((channel.pipeline().get("compress") instanceof NettyCompressionEncoder))
      {
        ((NettyCompressionEncoder)channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
      }
      else
      {
        channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(treshold));
      }
    }
    else
    {
      if ((channel.pipeline().get("decompress") instanceof NettyCompressionDecoder))
      {
        channel.pipeline().remove("decompress");
      }
      
      if ((channel.pipeline().get("compress") instanceof NettyCompressionEncoder))
      {
        channel.pipeline().remove("compress");
      }
    }
  }
  
  public void checkDisconnected()
  {
    if ((!hasNoChannel()) && (!isChannelOpen()) && (!disconnected))
    {
      disconnected = true;
      
      if (getExitMessage() != null)
      {
        getNetHandler().onDisconnect(getExitMessage());
      }
      else if (getNetHandler() != null)
      {
        getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
      }
    }
  }
  
  protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Object p_channelRead0_2_)
  {
    channelRead0(p_channelRead0_1_, (Packet)p_channelRead0_2_);
  }
  
  static class InboundHandlerTuplePacketListener
  {
    private final Packet packet;
    private final GenericFutureListener[] futureListeners;
    private static final String __OBFID = "CL_00001244";
    
    public InboundHandlerTuplePacketListener(Packet inPacket, GenericFutureListener... inFutureListeners)
    {
      packet = inPacket;
      futureListeners = inFutureListeners;
    }
  }
}
